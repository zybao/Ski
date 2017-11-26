package com.oab.socketconnection.listener;


import com.oab.socketconnection.network.SocketConnection;

public interface ConnectionListener {
    void connected(SocketConnection connection);

    void connectionClosed();

    void connectionError(Exception exception);

    void reconnectingIn(int time);
}