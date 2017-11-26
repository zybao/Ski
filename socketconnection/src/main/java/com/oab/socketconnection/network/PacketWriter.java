package com.oab.socketconnection.network;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import okio.BufferedSink;

public class PacketWriter {
    private static final String  TAG = "PacketWriter";
    private BufferedSink writer;
    private PacketManager packetManager;
    private BlockingQueue<Packet> packetQueue = new ArrayBlockingQueue<Packet>(500, true);
    private static AtomicInteger integer = new AtomicInteger();

    private Thread writerThread;
    private boolean isStop = false;

    public PacketWriter(PacketManager packetManager) {
        this.packetManager = packetManager;
    }

    public void stop() {
        isStop = true;
        synchronized (packetQueue) {
            packetQueue.notifyAll();
        }
    }

    public void start() {
        writer = packetManager.getWriter();
        isStop = false;
        if (writerThread != null && writerThread.isAlive()) {
            Log.i(TAG, "WriterThread: " + writerThread.getName() + " is alive");
            return ;
        }

        writerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                write();
            }
        });
        writerThread.setName("Packet write thread_" + integer.incrementAndGet());
        writerThread.setDaemon(true);
        writerThread.start();
        Log.i(TAG, "start writerThread: " + writerThread.getName());
    }

    private Packet nextPacket() {
        Packet packet = null;
        while (!isStop && (packet = packetQueue.poll()) == null) {
            try {
                synchronized (packetQueue) {
                    packetQueue.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return packet;
    }

    public void writerPacket(Packet packet) {
        try {
            if (packet != null && (!isStop || !packet.isHeartBeatPacket())) {
                packetQueue.put(packet);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i(TAG, "writerPacket InterruptedException");
            packetManager.handleFailedPacket(packet);
        }
        synchronized (packetQueue) {
            packetQueue.notifyAll();
        }
    }

    public void clearCachePacket() {
        Packet packet = null;
        while ((packet = packetQueue.poll()) != null) {
            packetManager.handleClearPacket(packet);
        }
        Log.i(TAG, "-----clearCachePacket");
    }

    private void write() {
        while (!isStop) {
            Packet packet = nextPacket();
            if (packet == null || packet.isExpired()) {
                continue;
            }
            if (!packet.isHeartBeatPacket() && packet.isShouldWaitAuth() && !packetManager.isAuthed()) {
                if (!packetManager.isAutoAuth()) {
                    packetManager.handleFailedPacket(packet);
                } else {
                    if (packetManager.handleWaitAuth(packet)) {
                        writerPacket(packet);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        packetManager.handleFailedPacket(packet);
                    }
                }
                continue;
            }
            if (packet != null && !packet.isExpired()) {
                try {
                    packetManager.handleWrite(packet);
                    if (!packet.isExpired()) {
                        writer.write(packet.toBytes());
                        writer.flush();
                        Log.i(TAG, "send packet content: " + packet.toString());
                    } else {
                        Log.i(TAG, "send packet isExpread true, packetId = " + packet.getPacketId());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    packetManager.handleWriteException(e);
                    packetManager.handleFailedPacket(packet);
                }
            }
        }
    }
}
