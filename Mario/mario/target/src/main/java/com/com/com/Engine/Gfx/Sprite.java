package com.com.com.Engine.Gfx;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.com.com.Engine.Components.ImageRenderer.scaleAlgorithm;
import com.com.com.Engine.Utils.Window;
import com.com.com.Engine.Utils.Geom.Vec2;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Sprite implements Serializable {

    public static final String RelativeEngineResourcePath = System.getProperty("user.dir")
            + "/src/main/java/com/com/com/Engine/Assets/";
    // Image data
    public int width, height;

    // The image
    transient public BufferedImage sprite;
    public File imageFile = null;

    public Sprite(BufferedImage image) {
        sprite = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public Sprite(String path, int x, int y, int w, int h) {

        imageFile = new File(Window.RelativeResourcePath + "Sprites/" + path);
        BufferedImage image = getBufferedImageFromFile(path);
        sprite = image.getSubimage(x, y, w, h);
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    public Sprite(String path) {
        imageFile = new File(Window.RelativeResourcePath + "Sprites/" + path);
        sprite = getBufferedImageFromFile(path);
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    public Vec2 getSpriteBounds() {
        return new Vec2(width, height);
    }

    public Sprite(int i) {

        if (i < 0 || i > 3) {

            System.out.println("[ERROR] Invalid sprite id\n0: square\n1: circle\n2: triangle\n3: round square");
            sprite = null;
            return;
        }

        sprite = getBufferedImageFromEngineFile("default-sprites.png").getSubimage(i * 32, 0, 32, 32);
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    public Sprite() {

        sprite = getBufferedImageFromEngineFile("empty.png");
        width = 0;
        height = 0;
    }

    public static Sprite getImageFromEngineFile(String path) {

        try {

            File src = new File(Window.RelativeResourcePath + "/" + path);
            BufferedImage img = ImageIO.read(src);

            return new Sprite(img);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }

    public static BufferedImage getBufferedImageFromFile(String path) {

        try {

            File src = new File(Window.RelativeResourcePath + "Sprites/" + path);

            BufferedImage img = ImageIO.read(src);

            return img;

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }

    private BufferedImage getBufferedImageFromEngineFile(String path) {

        try {

            File src = new File(RelativeEngineResourcePath + path);
            imageFile = src;
            BufferedImage img = ImageIO.read(src);

            return img;

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }

    public static Sprite[] getFramesOfHorizontal(BufferedImage image, int width, int height, int x, int y) {

        Sprite[] frames = new Sprite[(image.getWidth() / width) - (x * width)];

        for (int i = 0; i < frames.length; i++) {

            frames[i] = new Sprite(image.getSubimage(x + (i * width), y, width, height));
        }

        return frames;
    }

    public static Sprite[] getFramesOfVertical(BufferedImage image, int width, int height, int x, int y) {

        Sprite[] frames = new Sprite[(image.getWidth() / width) - (x * width)];

        for (int i = 0; i < frames.length; i++) {

            frames[i] = new Sprite(image.getSubimage(x, y + (i * height), width, height));
        }

        return frames;
    }

    public static Sprite[] getFramesOfHorizontal(BufferedImage image, int width, int height, int x, int y, int count) {

        Sprite[] frames = count <= 0 ? new Sprite[(image.getWidth() / width) - (x * width)] : new Sprite[count];

        for (int i = 0; i < frames.length; i++) {

            frames[i] = new Sprite(image.getSubimage(x + (i * width), y, width, height));
        }

        return frames;
    }

    public static Sprite[] getFramesOfVertical(BufferedImage image, int width, int height, int x, int y, int count) {

        Sprite[] frames = count <= 0 ? new Sprite[(image.getHeight() / height) - (y * height)] : new Sprite[count];

        for (int i = 0; i < frames.length; i++) {

            frames[i] = new Sprite(image.getSubimage(x, y + (i * height), width, height));
        }

        return frames;
    }

    public static Animation createAnimation(Sprite spriteSheet, int width, int height, int startX, int startY,
            boolean horizontal) {

        Sprite[] frames = null;

        if (horizontal)
            frames = getFramesOfHorizontal(spriteSheet.sprite, width, height, startX, startY, 0);
        else
            frames = getFramesOfVertical(spriteSheet.sprite, width, height, startX, startY, 0);

        return new Animation(frames, startX, startY, width, height);
    }

    public static Animation createAnimation(Sprite spriteSheet, int width, int height, int startX, int startY,
            boolean horizontal, int frameCount) {

        Sprite[] frames = null;

        if (horizontal)
            frames = getFramesOfHorizontal(spriteSheet.sprite, width, height, startX, startY, frameCount);
        else
            frames = getFramesOfVertical(spriteSheet.sprite, width, height, startX, startY, frameCount);

        return new Animation(frames, startX, startY, width, height);
    }

    public Sprite getScaledSprite(Vec2 dimensions) {

        width = (int) dimensions.x;
        height = (int) dimensions.y;
        sprite = ImageUtil.resizeImage((int) dimensions.x, (int) dimensions.y, scaleAlgorithm.SMOOTH, sprite);
        return this;
    }

    public void reloadSprites() throws IOException {
        System.out.println(imageFile.getAbsolutePath());
        sprite = ImageIO.read(imageFile);
    }
}
