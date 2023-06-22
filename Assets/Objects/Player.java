package com.com.com.Assets.Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.sql.Time;

import com.com.com.Engine.Utils.StdBehaviour;
import com.com.com.Engine.Utils.Clock.ClockEvent;
import com.com.com.Engine.Utils.Clock.ClockType;
import com.com.com.Engine.Entity.Object;
import java.awt.Graphics2D;

import com.com.com.Engine.HimawariCore;
import com.com.com.Engine.Components.*;
import com.com.com.Engine.Gfx.*;
import com.com.com.Engine.Input.*;
import com.com.com.Engine.Input.Input.Keys;
import com.com.com.Engine.Map.*;
import com.com.com.Engine.Physics.*;
import com.com.com.Engine.Sound.*;
import com.com.com.Engine.Utils.*;
import com.com.com.Engine.Utils.Geom.*;

import javafx.scene.shape.DrawMode;

public class Player extends Object implements StdBehaviour {

    public Player() {
        super("Player");
    }

    ImageRenderer renderer;
    RectCollider collider;
    Body b;
    Animator animator;

    Sprite normal, jump;

    Clock clock = new Clock(255, ClockType.COUNTDOWN);
    Fonts f;

    public int lives = 3;
    public static int coins = 0;

    float speed = 300, jumpPower = 14;

    boolean dead = false, timeup = false;

    /**
     * Called once the object is initialized
     */
    @Override
    public void Start() {

        setTag("Player");

        normal = new Sprite("mario.png", 0, 0, 32, 32).getScaledSprite(new Vec2(32, 32));
        jump = new Sprite("mario.png", 32 * 2, 0, 32, 32).getScaledSprite(new Vec2(32, 32));

        renderer = new ImageRenderer(normal, this);

        Sprite s = new Sprite("mario.png");
        Animation walk = Sprite.createAnimation(s.getScaledSprite(new Vec2(s.width, s.height)), 32, 32, 0, 32, true, 2);
        animator = new Animator(new Animation[] { walk }, renderer);
        addComponent(animator);

        collider = new RectCollider(transform, new Vec2(16, 16));
        collider.offset = new Vec2(8, 16);
        b = new Body(transform, collider, 1);

        addComponent(b);
        addComponent(collider);
        addComponent(renderer);

        clock.startClock();
        clock.addClockEventListener(new ClockEvent() {

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

                timeup = true;
                die();
            }

        });

        setStatic(true);

        f = new Fonts(0, 0, true, new FontMap(new TileSet(new Sprite("tile.png"), 16, 16)));
    }

    /**
     * Called once every frame
     */
    @Override
    public void Update(float deltaTime) {

        if (dead == true) {

            if (transform.position.y > 600) {

                RoomHandler.unLoad();
                Renderer.clearColor = Color.black;

                removeComponent(renderer);
                removeComponent(collider);
                removeComponent(b);
            }
            return;
        }

        Vec2 movement = new Vec2(Input.axisX, 0);
        boolean grounded = (collider
                .willCollide(transform.position.sumWith(Vec2.DOWN)));

        if (Input.isKeyJustPressed(Keys.Z) && grounded) {

            b.ApplyForce(Vec2.UP.times(jumpPower));
            renderer.setImage(jump);
            animator.pause();
            grounded = false;
        }

        if (!movement.equals(Vec2.ZERO) && grounded && !animator.playing) {

            animator.play(0, 0);
        }

        if (movement.equals(Vec2.ZERO) && renderer.getSprite() != jump) {

            animator.pause();
            renderer.setImage(normal);
        }

        if (grounded && renderer.getSprite() == jump) {
            renderer.setImage(normal);
        }

        if ((movement.x > 0 && !renderer.isFlippedX) || (movement.x < 0 && renderer.isFlippedX))
            renderer.flipX();

        if (transform.position.y > 300) {

            die();
        }

        transform.translate(movement.times(speed * deltaTime), collider);
    }

    /**
     * Draw your own graphics here
     */
    @Override
    public void DrawGUI(Graphics2D g) {

        if (Renderer.clearColor != Color.BLACK) {

            Widget.setColor(Color.BLACK);
            Widget.setDrawType(Debugging.type.FILLED);
            Widget.drawRectangle(new Rectangle(0, 0, Window.width, 42), g);

            f.drawText(String.valueOf(clock.getCurrentClockValue()), g, new Vec2(5, 5));
            f.drawText("Mario x" + lives, g, new Vec2(120, 5));
            f.drawText("World 1-1", g, new Vec2(290, 5));
            f.drawText("Coins " + coins, g, new Vec2(460, 5));

            Widget.setColor(Color.WHITE);
            Widget.drawText("Fps: " + Renderer.getFPS(), 5, 33, g);
        } else {

            f.drawText(timeup ? "TIME OUT" : "YOU DIEDDDD", g, new Vec2(5, 5));
        }
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

    @Override
    public void RoomLoaded(Room room) {

    }

    public void die() {

        dead = true;
        b.resetForce();
        animator.pause();

        renderer.setImage(new Sprite("mario.png", 32 * 3, 0, 32, 32).getScaledSprite(Vec2.fromValue(32)));
        collider.solid = false;
        b.solid = false;
        b.ApplyForce(Vec2.UP.times(20));
    }
}
