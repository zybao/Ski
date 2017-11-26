package com.oab.socketconnection.network;

import android.os.SystemClock;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class PacketTask {
    private static final String TAG = "PacketTask";
    private Packet packet;
    static final long TIME_OUT = 20_000;
    private Timer timer = new Timer();
    private PacketManager packetManager;
    long startTime;
    private long timeout = TIME_OUT;

    void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public PacketTask(PacketManager packetManager, Packet packet) {
        this.packetManager = packetManager;
        this.packet = packet;
    }

    public void execute() {
        if (packet == null) {
            return ;
        }
        startTime = SystemClock.elapsedRealtime();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "send packet time out, packetId = " + packet.getPacketId());
                packet.setIsExpired(true);
                packetManager.handleTimeOutPacket(packet);
            }
        };
        timer.schedule(task, timeout);
    }

    public void cancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public String getTaskId() {
        return packet == null ? null : packet.getPacketId();
    }
}
