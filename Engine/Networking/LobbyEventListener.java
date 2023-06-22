package com.com.com.Engine.Networking;

public interface LobbyEventListener {

    public void onConnectionEstablished();

    public void onDisconnection();

    public void clientJoined(String id);

    public void clientLeft(String id);

    public void receivedMessage(String originID, String message);
}
