package com.com.com.Engine;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import com.com.com.Engine.Map.Room;
import com.com.com.Engine.Map.RoomHandler;
import com.com.com.Engine.Utils.ObjectLoader;
import com.com.com.Engine.Entity.Object;
import com.com.com.Engine.Utils.Window;
import com.com.com.Engine.Utils.Geom.Vec2;

public class HimawariCore {

    protected static Window window;
    private static Vec2 savedWindowSize = new Vec2();

    public static enum Windowmode {

        WINDOWED,
        FULLSCREEN
    }

    public static Window CreateWindow(int width, int height, String name) {

        window = new Window();
        window.initWindow(width, height, name);

        savedWindowSize = new Vec2(window.getWidth(), window.getHeight());

        return window;
    }

    public static void LoadRoom(Room room) {

        if (RoomHandler.startRoom == null) {

            RoomHandler.startRoom = room;
            RoomHandler.currentRoom = room;

            room.loadObjects();

            RoomHandler.callRoomLoaded();
        } else {

            // Move to room
            RoomHandler.gotoRoom(room);
        }

    }

    public static void CloseWindow() {

        window.dispose();
        window = null;
    }

    public static void EndProgram() {

        window.closeWindow();
        window = null;

        System.out.println("[Execution Finished]");
        System.exit(0);
    }

    public static void ChangeWindowMode(Windowmode mode) {

        if (mode == Windowmode.WINDOWED) {

            window.setSize((int) savedWindowSize.x, (int) savedWindowSize.y);
        } else {
            savedWindowSize = new Vec2(window.getWidth(), window.getHeight());

            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }

    public static Object CreateObject(String name, Vec2 position, float angle, Vec2 scale) {

        Object newObj = ObjectLoader.LoadObjectOfName(name, position, angle, scale);
        return newObj;
    }

    public static Object CreateObject(Class c, Vec2 position, float angle, Vec2 scale) {

        if (c.getSuperclass() == Object.class) {

            Object o;
            try {

                o = (Object) c.getDeclaredConstructor().newInstance();
                o.transform.position.setValues(position);
                o.transform.angle = angle;
                o.transform.scale.setValues(scale);

                return o;

            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {

                System.out.println("[ERROR] The input class does not have an accessible constructor");
                e.printStackTrace();
                return null;
            }
        }

        System.out.println("[ERROR] The input class does not match an object type");
        return null;
    }

    public static Object createObject(String name) {

        Object newObj = ObjectLoader.LoadObjectOfName(name, new Vec2(0, 0), 0, new Vec2(0, 0));
        return newObj;
    }
}
