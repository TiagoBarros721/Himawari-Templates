package com.com.pong.Engine.Map;

import java.util.ArrayList;
import java.util.List;

import com.com.pong.Engine.Components.Camera;
import com.com.pong.Engine.Components.ImageRenderer.scaleAlgorithm;
import com.com.pong.Engine.Gfx.ImageUtil;
import com.com.pong.Engine.Gfx.Sprite;
import com.com.pong.Engine.Utils.Window;
import com.com.pong.Engine.Utils.Geom.Vec2;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class RoomHandler {
    
    public static Room currentRoom;
    public static Room startRoom = null;

    //Room list
    private static List<Room> rooms = new ArrayList<Room>();

    public static void addRoom(Room room) { rooms.add(room);}
    public static boolean hasRooms(){return startRoom != null;}

    public static Room getCurrentRoom(){return currentRoom;}
    public static Room getRoom(int index){return rooms.get(index);}

    public static int roomCount(){return rooms.size();}

    public static Vec2 viewportOffset = new Vec2(2,0);

    private static boolean isStretched(){

        boolean w = Window.width <= currentRoom.background.width;
        boolean h = Window.height <= currentRoom.background.height;

        return w && h;
    }

    public static void render(Graphics2D g) {

        if(hasRooms()){

            //Draw background if exists 
            
            if(currentRoom.hasBackground()){

                BufferedImage background = currentRoom.getBackground();
                boolean[] displaySettings = currentRoom.getTiling();

                if(displaySettings==null && !isStretched()){
                    
                    //Stretch = true
                    currentRoom.background = new Sprite(ImageUtil.toBufferedImage(ImageUtil.resizeImage(Window.width, Window.height, scaleAlgorithm.SMOOTH, currentRoom.background.sprite)));
                    g.drawImage(background, 0, 0, null);
                }else if(displaySettings!=null){

                    if(displaySettings[0] && !displaySettings[1]){

                        //Tile horizontaly 
                        int tileNumber = Window.width / currentRoom.background.width;

                        if(tileNumber>1){
                            for(int i =0; i < tileNumber; i++){

                                g.drawImage(currentRoom.background.sprite, i * currentRoom.background.width, 0, null);
                            }
                        }
                    }else if(displaySettings[1] && !displaySettings[0]){

                        //Tile verticaly 
                        int tileNumber = Window.height / currentRoom.background.height;

                        if(tileNumber>1){
                            for(int i =0; i < tileNumber; i++){

                                g.drawImage(currentRoom.background.sprite, 0, i * currentRoom.background.width, null);
                            }
                        }
                    }else{

                        //Tile complete
                        int tileNumberW = Window.width / currentRoom.background.width;
                        int tileNumberH = Window.height / currentRoom.background.height;

                        if(tileNumberW>1 || tileNumberH>1){
                            for(int i =0; i < tileNumberW; i++){
                                for(int j = 0; j < tileNumberH; j++)
                                    g.drawImage(currentRoom.background.sprite, i * currentRoom.background.width, j * currentRoom.background.height, null);
                            }
                        }
                    }
                }
            }

            if(Camera.getInstance() == null){

                for(int i = 0; i < currentRoom.roomData.getHeight(); i++){
                    for(int j = 0; j < currentRoom.roomData.getWidth(); j++) {

                        g.drawImage(currentRoom.tileset.getFrame(currentRoom.roomData.getTile(j, i)), 
                        j * currentRoom.tileset.width, 
                        i * currentRoom.tileset.height,
                        null);

                    }
                }
            }else{

                /**
                 * To save a lot of memory, we need to just draw the camera viewport and not the entire map
                 

                for(int i = 0; i < (int)((Window.width / currentRoom.tileset.width)); i++){
                    for(int j = 0; i < currentRoom.roomData.getHeight(); j++){

                    if(j > currentRoom.roomData.getWidth()){
                        break;
                    }

                    g.drawImage(currentRoom.tileset.getFrame(currentRoom.roomData.getTile(i, j)), 
                        (int)(i * currentRoom.tileset.width + (Camera.getOffset().x - Camera.position.position.x)), 
                        (int)(j * currentRoom.tileset.height + (Camera.getOffset().y - Camera.position.position.y)),
                        currentRoom.tileset.width,
                        currentRoom.tileset.height, 
                null);
                    }
                }
                */

                //System.out.println(Camera.ViewPort.y + (int) Camera.getViewPortOffset().y + viewportOffset.x);
                for(int i = (int) Camera.getViewPortOffset().x; i < Camera.ViewPort.y + (int) Camera.getViewPortOffset().x + viewportOffset.y; i++){
                    for(int j = (int) Camera.getViewPortOffset().y; j < Camera.ViewPort.x + (int) Camera.getViewPortOffset().y + viewportOffset.x; j++) {

                        if(j > currentRoom.roomData.getWidth()){
                            break;
                        }

                        g.drawImage(currentRoom.tileset.getFrame(currentRoom.roomData.getTile(j, i)), 
                        (int) (j * currentRoom.tileset.width - Camera.position.position.x + Camera.getOffset().x) * Camera.getSize(), 
                        (int) (i * currentRoom.tileset.height - Camera.position.position.y + Camera.getOffset().y) * Camera.getSize(), 
                        Camera.getSize() * currentRoom.tileset.width,
                        Camera.getSize() * currentRoom.tileset.height, 
                null);
                    }

                    if(i > currentRoom.roomData.getHeight()){
                        break;
                    }
                }
            }
        }
    }
}

