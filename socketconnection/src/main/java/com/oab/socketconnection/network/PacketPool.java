package com.oab.socketconnection.network;

import java.util.HashMap;

public class PacketPool {

    private HashMap<String, Packet> packetPool = new HashMap<>();

    public void add(Packet packet) {
        String id = packet.getPacketId();
        Packet tempPacket = packetPool.get(id);
        if (tempPacket != null) {
            packet.setBody(tempPacket.getBody() + packet.getBody());
        }
        packetPool.put(id, packet);
    }

    public Packet get(Packet packet) {
        String id = packet.getPacketId();
        if (packetPool.containsKey(id)) {
            add(packet);
            Packet p = packetPool.get(id);
            packetPool.remove(id);
            return p;
        }
        return packet;
    }

    public void clear() {
        packetPool.clear();
    }
}
