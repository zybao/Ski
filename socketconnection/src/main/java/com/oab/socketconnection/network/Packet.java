package com.oab.socketconnection.network;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Packet<T> {

    private AtomicInteger reSendTimes = new AtomicInteger();
//    protected Header header;
//    protected String body;
    int flag;
    private boolean isExpired = false;
    private long sendTime;
    public abstract String getBody();
    public abstract void setBody(String body);
    public abstract byte[] toBytes();
//    public byte[] toBytes() {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        BufferedSink sink = null;
//        try {
//            sink = Okio.buffer(Okio.sink(out));
//            if (header != null) {
//                header.write(sink);
//            }
//            sink.write(ByteString.encodeUtf8(body));
//            sink.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if (sink != null) {
//                try {
//                    sink.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return out.toByteArray();
//    }

    final int getReSendTimes() {
        return reSendTimes.getAndIncrement();
    }

    public abstract String getPacketId();

    public abstract boolean isHeartBeatPacket();

    public abstract boolean isAuthPacket();

    public abstract boolean isShouldWaitAuth();

    public final synchronized boolean isExpired() {
        return isExpired;
    }

    final synchronized void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    final long getSendTime() {
        return sendTime;
    }

    final void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    final int getFlag() {
        return flag;
    }

    final void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean hasNextSubPacket() {
        return false;
    }
}
