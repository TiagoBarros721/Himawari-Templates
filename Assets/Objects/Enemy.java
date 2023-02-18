package Assets.Objects;

import Engine.Entity.*;
import Engine.Entity.Object;

import java.awt.Color;
import java.awt.Graphics2D;

import Engine.Components.*;
import Engine.Gfx.*;
import Engine.Gfx.Debugging.type;
import Engine.Input.*;
import Engine.Map.*;
import Engine.Physics.*;
import Engine.Sound.*;
import Engine.Utils.*;
import Engine.Utils.Geom.*;

public class Enemy extends Object implements StdBehaviour {

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

        collider = new RectCollider(transform, new Vec2(10, 100));
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
