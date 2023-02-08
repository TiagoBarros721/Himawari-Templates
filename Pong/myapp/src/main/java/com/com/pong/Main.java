package com.com.pong;

import java.awt.Color;

import com.com.pong.Engine.HimawariCore;
import com.com.pong.Engine.Entity.Object;
import com.com.pong.Engine.Gfx.Debugging;
import com.com.pong.Engine.Gfx.Sprite;
import com.com.pong.Engine.Map.Room;
import com.com.pong.Engine.Map.RoomData;
import com.com.pong.Engine.Map.TileSet;
import com.com.pong.Engine.Utils.AlarmPack;
import com.com.pong.Engine.Utils.ObjectLoader;
import com.com.pong.Engine.Utils.Renderer;
import com.com.pong.Engine.Utils.Window;
import com.com.pong.Engine.Utils.Geom.Vec2;

public class Main extends HimawariCore{

    public static void main(String[] args) {

        CreateWindow(800, 600, "Pong!!");

        CreateObject("Player");
        CreateObject("Paddle", new Vec2(20, Window.height/2 - 50), 0, new Vec2(1,1));
        CreateObject("Ball", new Vec2(Window.width/2, Window.height/2), 0, new Vec2(1,1));
        
        CreateObject("Enemy", new Vec2(Window.width - 50, Window.height/2 - 50), 0, new Vec2(1,1));


        Debugging.drawColliders = true;
    }
}
