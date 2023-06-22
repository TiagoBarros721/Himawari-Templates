package com.com.com.Assets.Objects;

import com.com.com.Engine.Utils.Clock;
import com.com.com.Engine.Utils.StdBehaviour;
import com.com.com.Engine.Utils.Clock.ClockEvent;
import com.com.com.Engine.Utils.Clock.ClockType;
import com.com.com.Engine.Utils.Geom.Vec2;

import java.awt.Graphics2D;

import com.com.com.Engine.Components.Animator;
import com.com.com.Engine.Components.Body;
import com.com.com.Engine.Components.ImageRenderer;
import com.com.com.Engine.Components.RectCollider;
import com.com.com.Engine.Entity.Object;
import com.com.com.Engine.Gfx.Animation;
import com.com.com.Engine.Gfx.Sprite;
import com.com.com.Engine.Map.Room;

public class Enemy extends Object implements StdBehaviour {

    Vec2 dir = Vec2.LEFT;
    RectCollider player;
    RectCollider head, collider;
    Animator animator;
    ImageRenderer renderer;

    boolean dead = false;

    public Enemy() {
        super("Enemy");
    }

    @Override
    public void Start() {

        renderer = new ImageRenderer(
                new Sprite("enemies.png", 0, 0, 32, 32), this);

        collider = new RectCollider(transform, new Vec2(16, 16));
        collider.offset = new Vec2(8, 16);
        collider.solid = false;
        collider.ignoreTag("Player");

        Animation walk = Sprite.createAnimation(new Sprite("enemies.png"), 32, 32, 0, 0, true, 2);
        animator = new Animator(new Animation[] { walk }, renderer);

        Body body = new Body(transform, collider, 1);

        addComponent(body);
        addComponent(collider);
        addComponent(renderer);
        addComponent(animator);

        animator.play(0, 0);

        // Head collider
        head = new RectCollider(transform, new Vec2(8, 10));
        head.solid = false;
        head.offset = new Vec2(12, 10);

        addComponent(head);

        player = (RectCollider) Object.FindObject("Player").getComponent(RectCollider.class);
    }

    @Override
    public void Update(float deltaTime) {

        boolean collide = (collider.willCollide(transform.position.sumWith(dir.sumWith(new Vec2(0, -2)))));
        if (collide)
            dir = dir.inverse();

        transform.translate(dir.times(deltaTime).times(30));

        if (head.isCollidingWith(player) && !dead) {

            dead = true;
            animator.pause();

            renderer.setImage(new Sprite("enemies.png", 64, 0, 32, 32));
            dir = Vec2.ZERO;

            Body p = (Body) Object.FindObject("Player").getComponent(Body.class);
            p.resetForce();
            p.ApplyForce(new Vec2(0, -10));

            Clock c = new Clock(2, ClockType.COUNTDOWN);
            c.addClockEventListener(new ClockEvent() {

                @Override
                public void clockStarted() {
                }

                @Override
                public void clockStopped() {
                }

                @Override
                public void clockTicked() {
                }

                @Override
                public void countdownZero() {

                    DestroyInstance();
                }

            });

            c.startClock();
        } else if (collider.isCollidingWith(player) && !dead) {

            Player p = (Player) Object.FindObject("Player");
            p.die();
        }
    }

    @Override
    public void RoomLoaded(Room room) {

    }

    @Override
    public void DrawGUI(Graphics2D g) {

    }

    @Override
    public void ReceiveMessage(String message) {

    }

    @Override
    public StdBehaviour getBehaviour() {
        return (StdBehaviour) this;
    }
}
