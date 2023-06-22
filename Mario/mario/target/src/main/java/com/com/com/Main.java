package com.com.com;

import java.awt.Color;

import com.com.com.Engine.HimawariCore;
import com.com.com.Engine.Components.Camera;
import com.com.com.Engine.Components.RectCollider;
import com.com.com.Engine.Database.Storage;
import com.com.com.Engine.Entity.Object;
import com.com.com.Engine.Gfx.Debugging;
import com.com.com.Engine.Gfx.Sprite;
import com.com.com.Engine.Map.Room;
import com.com.com.Engine.Map.RoomData;
import com.com.com.Engine.Map.Space;
import com.com.com.Engine.Map.TileSet;
import com.com.com.Engine.Physics.Physics;
import com.com.com.Engine.Sound.Sound;
import com.com.com.Engine.Utils.Alarm;
import com.com.com.Engine.Utils.AlarmPack;
import com.com.com.Engine.Utils.Renderer;
import com.com.com.Engine.Utils.Window;
import com.com.com.Engine.Utils.Geom.Vec2;

public class Main extends HimawariCore {

    public static void main(String[] args) {

        Window w = CreateWindow(800, 600, "Nice Game");
        w.changeBackground(new Color(134, 196, 217));
        Room r = new Room(new RoomData("1-1"));

        CreateObject("Player", new Vec2(48, 144), 0, new Vec2(1, 1));
        LoadRoom(r);
        CreateObject("GameCamera", Vec2.ZERO, 0, Vec2.ZERO);
        Debugging.drawColliders = true;

        Camera.setViewport(2, 2);

        Physics.accelearion_capped = true;
        Physics.acceleration_treshold = 20;

        // Place coin blocks
        for (Vec2 pos : r.getAllPositionsWhere(672, Space.WORLD)) {

            CreateObject("CoinBlock", pos, 0, Vec2.ONE);
        }
    }
}
