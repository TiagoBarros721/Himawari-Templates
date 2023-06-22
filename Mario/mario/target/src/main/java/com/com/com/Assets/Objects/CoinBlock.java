package com.com.com.Assets.Objects;

import java.awt.Graphics2D;

import com.com.com.Engine.Components.RectCollider;
import com.com.com.Engine.Entity.Object;
import com.com.com.Engine.Gfx.ParticleEmitter;
import com.com.com.Engine.Gfx.Sprite;
import com.com.com.Engine.Map.Room;
import com.com.com.Engine.Map.RoomHandler;
import com.com.com.Engine.Utils.StdBehaviour;
import com.com.com.Engine.Utils.Geom.Vec2;

public class CoinBlock extends Object implements StdBehaviour {

    RectCollider collider, player;
    ParticleEmitter emitter;
    int filled = 1;

    boolean flag = false;

    public CoinBlock() {
        super("CoinBlock");
    }

    @Override
    public void Start() {

        player = (RectCollider) Object.FindObject("Player").getComponent(RectCollider.class);
        collider = new RectCollider(transform, new Vec2(16, 24));
        collider.solid = false;

        addComponent(collider);
        filled = RoomHandler.currentRoom.getIdFromTileset(27, 0);
        emitter = new ParticleEmitter(
                new Sprite(RoomHandler.currentRoom.tileset.getFrame(1, 24)).getScaledSprite(new Vec2(8, 8)),
                transform.position.sumWith(new Vec2((collider.bounds.x / 2) - 4, -8)),
                new Vec2(0, 1));

        emitter.lifetime = 100;
    }

    @Override
    public void Update(float deltaTime) {

        if (collider.isCollidingWith(player) && !flag) {

            Vec2 myPos = RoomHandler.currentRoom.convertWorldToTilePosition(transform.position);
            RoomHandler.currentRoom.changeTileAt((int) myPos.x, (int) myPos.y, filled);

            Player.coins++;
            emitter.startEmitting();
            flag = true;
            return;
        }

        if (flag && emitter.particleCount() >= emitter.maxParticles) {

            emitter.stopEmitting();
            return;
        }

        if (flag && emitter.particleCount() < 1)
            DestroyInstance();
    }

    @Override
    public void RoomLoaded(Room room) {

    }

    @Override
    public void DrawGUI(Graphics2D g) {

        emitter.render(g);
    }

    @Override
    public void ReceiveMessage(String message) {

    }

    @Override
    public StdBehaviour getBehaviour() {
        return (StdBehaviour) this;
    }
}
