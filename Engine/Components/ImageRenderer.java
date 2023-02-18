package com.com.pong.Engine.Components;

import com.com.pong.Engine.Gfx.ImageUtil;
import com.com.pong.Engine.Gfx.Sprite;
import com.com.pong.Engine.Utils.GameMaths;
import com.com.pong.Engine.Utils.Geom.Vec2;

import com.com.pong.Engine.Entity.*;
import com.com.pong.Engine.Entity.Object;

import java.awt.image.BufferedImage;
import java.awt.Image;

public class ImageRenderer extends Component{

    public enum scaleAlgorithm{
        SMOOTH,
        FAST
    }

    private float alpha = 1;
    public boolean isFlippedX = false, isFlippedY = false;
    public boolean visible = true;
    public void setVisible(boolean visible) { this.visible = visible;}

    private Object object;
    private Sprite sprite = null;
    private Sprite currentSprite = null;

    public boolean hasImage() { return sprite.sprite != null; }
    public BufferedImage getImage() {return currentSprite.sprite; }

    public void setImage(Sprite img) { currentSprite = img; }

    public ImageRenderer() {visible = false;}

    public ImageRenderer(Sprite img, Object object) 
    { 
        this.sprite = img; 
        this.currentSprite = this.sprite; 
        this.object = object;
    }

    public Vec2 getDimensions() { return new Vec2(sprite.width, sprite.height); }

    public void scaleSprite(int width, int height, scaleAlgorithm al){
        
        Image scaled = ImageUtil.resizeImage(width, height, al, sprite.sprite);
        BufferedImage img = ImageUtil.toBufferedImage(scaled);

        sprite.sprite = img;
        this.currentSprite = sprite;

        this.sprite.width = width;
        this.sprite.height = height;

        this.currentSprite.width = width;
        this.currentSprite.height = height;
    }

    public void flipX(){

        isFlippedX = !isFlippedX;
    }

    public void flipY(){

        isFlippedY = !isFlippedY;
    }

    public void setAlpha(float alpha) {
     
        alpha = GameMaths.clamp(alpha, 0, 1);
        this.alpha = alpha;

        //Set alpha of childern
        for (Node n : object.node.children) {
            
            ImageRenderer renderer = (ImageRenderer) n.object.getComponent("ImageRenderer");
            if(renderer != null) {

                renderer.setAlpha(alpha);
            }
        }
    }

    public float getAlpha(){
        return alpha;
    }
}
