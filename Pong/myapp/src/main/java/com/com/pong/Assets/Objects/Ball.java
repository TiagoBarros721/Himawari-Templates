package com.com.pong.Assets.Objects;

import com.com.pong.Engine.Entity.*;
import com.com.pong.Engine.Entity.Object;

import java.awt.Color;
import java.awt.Graphics2D;

import com.com.pong.Engine.Components.*;
import com.com.pong.Engine.Gfx.*;
import com.com.pong.Engine.Gfx.Debugging.type;
import com.com.pong.Engine.Input.*;
import com.com.pong.Engine.Map.*;
import com.com.pong.Engine.Physics.*;
import com.com.pong.Engine.Sound.*;
import com.com.pong.Engine.Utils.*;
import com.com.pong.Engine.Utils.Geom.*;

public class Ball extends Object implements StdBehaviour{

    public Vec2 dir;
    public int speed = 250;
    public RectCollider collider;

    public Ball() {
        super("Ball");
    }

    /**
     * Called once the object is initialized
     */
    @Override
    public void Start() {
        
        dir = Object.FindObject("Paddle").transform.position.subtractWith(transform.position);
        dir = dir.normalize();

        collider = new RectCollider(transform, new Vec2(10,10));
        addComponent(collider);
    }

    /**
     * Called once every frame
     */
    @Override
    public void Update(float deltaTime) {
        
        //Check for collision
        Object c = collider.isColliding();
        if(c != null) {
            
            dir = c.transform.position.subtractWith(transform.position.sumWith(new Vec2(0, -50))).normalize().invertX();
            Player.score++;
        }
        if(transform.position.y < 0 || transform.position.y > Window.height - 50) dir = dir.invertY();

        transform.translate(dir.times(deltaTime).times(speed));

        if(transform.position.x < 0){

            Object.DestroyObject(Object.FindObject("Paddle"));
            Object.DestroyObject(Object.FindObject("Enemy"));
            
            DestroyInstance();
        }
    }

    /**
     * Draw your own graphics here
     */
    @Override
    public void DrawGUI(Graphics2D g) {
        
        Widget.setDrawType(type.FILLED);
        Widget.setColor(Color.WHITE);
        Widget.drawCircle(new Circle(transform.position.x, transform.position.y, 10), g);
    }

    /**
     * Called from a different object, receives the object that sent the message
     */
    @Override
    public void ReceiveMessage(String origin) {
        
    }

    /**
     * DO NOT ERASE THIS METHOD
     */
    @Override
    public StdBehaviour getBehaviour() {
        return (StdBehaviour) this;
    }
    
}
