package com.com.com.Engine.Utils;

import java.awt.Graphics2D;

import com.com.com.Engine.Map.Room;

public interface StdBehaviour {

    public void Start();

    public void Update(float deltaTime);

    public void RoomLoaded(Room room);

    public void DrawGUI(Graphics2D g);

    public void ReceiveMessage(String message);
}
