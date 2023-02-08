package com.com.pong.Engine.Gfx;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.com.pong.Engine.Utils.Window;

import java.io.File;

public class Sprite {
    
    public static final String RelativeEngineResourcePath = System.getProperty("user.dir") + "/src/main/java/Engine/Assets/";
    //Image data
    public int width, height;
    
    //The image
    public BufferedImage sprite;

    public Sprite(BufferedImage image) { sprite = image; width = image.getWidth(); height = image.getHeight();}

    public Sprite(String path, int x, int y, int w, int h) {BufferedImage image = getBufferedImageFromFile(path); sprite = image.getSubimage(x, y, w, h); width = sprite.getWidth(); height = sprite.getHeight(); }

    public Sprite(String path) { sprite = getBufferedImageFromFile(path); width = sprite.getWidth(); height = sprite.getHeight(); }

    public Sprite(int i) { 
        
        if(i < 0 || i > 3){

            System.out.println("[ERROR] Invalid sprite id\n0: square\n1: circle\n2: triangle\n3: round square");
            sprite = null;
            return;
        }

        sprite = getBufferedImageFromEngineFile("default-sprites.png").getSubimage(i * 32, 0, 32, 32);
        width = sprite.getWidth(); height = sprite.getHeight();
    }

    public static Sprite getImageFromEngineFile(String path){

        try{

            File src = new File(Window.RelativeResourcePath + "/" + path);
            BufferedImage img = ImageIO.read(src);

            return new Sprite(img);

        }catch(Exception e){ System.out.println(e.toString());}

        return null;
    }

    public static BufferedImage getBufferedImageFromFile(String path){

        try{

            File src = new File(Window.RelativeResourcePath + "Sprites/" + path);

            BufferedImage img = ImageIO.read(src);

            return img;

        }catch(Exception e){ System.out.println(e.toString());}

        return null;
    }

    private BufferedImage getBufferedImageFromEngineFile(String path){

        try{

            File src = new File(RelativeEngineResourcePath + path);
            BufferedImage img = ImageIO.read(src);

            return img;

        }catch(Exception e){ System.out.println(e.toString());}

        return null;
    }

    public static BufferedImage[] getFramesOfHorizontal(BufferedImage image, int width, int height, int x, int y) {

        BufferedImage[] frames = new BufferedImage[(image.getWidth() / width) - (x * width)];

        for(int i = 0; i < frames.length; i++){

            frames[i] = image.getSubimage(x + (i * width), y, width, height);
        }

        return frames;
    }

    public static BufferedImage[] getFramesOfVertical(BufferedImage image, int width, int height, int x, int y) {

        BufferedImage[] frames = new BufferedImage[(image.getWidth() / width) - (x * width)];

        for(int i = 0; i < frames.length; i++){

            frames[i] = image.getSubimage(x, y + (i * height), width, height);
        }

        return frames;
    }

    public static Animation createAnimation(Sprite spriteSheet, int width, int height, int startX, int startY, boolean horizontal){

        BufferedImage[] frames = null;

        if(horizontal)
            frames = getFramesOfHorizontal(spriteSheet.sprite, width, height, startX, startY);
        else
            frames = getFramesOfVertical(spriteSheet.sprite, width,height, startX, startY);
        
        return new Animation(frames, startX, startY, width, height);
    }
}
