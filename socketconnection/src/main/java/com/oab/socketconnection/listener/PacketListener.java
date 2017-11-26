package com.oab.socketconnection.listener;


import com.oab.socketconnection.network.Packet;
import com.oab.socketconnection.network.SocketConnection;

public interface PacketListener<T> {
    boolean shouldProcess(Packet<T> packet);
    void processReceiverPacket(Packet<T> packet, SocketConnection socketConnection);
    void processSendFailPacket(Packet<T> packet, SocketConnection socketConnection);
}