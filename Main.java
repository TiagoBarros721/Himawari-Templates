package com.com.pong;

import java.awt.Color;

import Engine.HimawariCore;
import Engine.Entity.Object;
import Engine.Gfx.Debugging;
import Engine.Gfx.Sprite;
import Engine.Map.Room;
import Engine.Map.RoomData;
import Engine.Map.TileSet;
import Engine.Utils.AlarmPack;
import Engine.Utils.ObjectLoader;
import Engine.Utils.Renderer;
import Engine.Utils.Window;
import Engine.Utils.Geom.Vec2;

public class Main extends HimawariCore {

    public static void main(String[] args) {

        CreateWindow(800, 600, "Pong!!");

        CreateObject("Player");
        CreateObject("Paddle", new Vec2(20, Window.height / 2 - 50), 0, new Vec2(1, 1));
        CreateObject("Ball", new Vec2(Window.width / 2, Window.height / 2), 0, new Vec2(1, 1));

        CreateObject("Enemy", new Vec2(Window.width - 50, Window.height / 2 - 50), 0, new Vec2(1, 1));

    }
}
