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

public class Enemy extends Object implements StdBehaviour{

    public RectCollider collider;
    private Object ball;

    public Enemy() {
        super("Enemy");
    }

    /**
     * Called once the object is initialized
     */
    @Override
    public void Start() {
        
        ball = Object.FindObject("Ball");

        collider = new RectCollider(transform, new Vec2(10,100));
        addComponent(collider);
    }

    /**
     * Called once every frame
     */
    @Override
    public void Update(float deltaTime) {
        
        transform.position.y = ball.transform.position.y - 50;
    }

    /**
     * Draw your own graphics here
     */
    @Override
    public void DrawGUI(Graphics2D g) {
        
        Widget.setDrawType(type.FILLED);
        Widget.setColor(Color.WHITE);
        Widget.drawRectangle(new Rectangle(transform.position.x, transform.position.y, 10, 100), g);
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
