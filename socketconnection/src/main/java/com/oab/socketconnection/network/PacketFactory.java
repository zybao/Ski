package com.oab.socketconnection.network;

import java.io.IOException;

import okio.BufferedSource;

public interface PacketFactory {
    Packet getHeartBeat();
    Packet buildPacket(BufferedSource source) throws IOException;
    Packet getAuthPacket();
}
