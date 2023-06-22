package com.com.com.Assets.Objects;

import java.awt.Graphics2D;

import com.com.com.Engine.Components.Camera;
import com.com.com.Engine.Components.ImageRenderer;
import com.com.com.Engine.Components.Transform;
import com.com.com.Engine.Utils.StdBehaviour;
import com.com.com.Engine.Utils.Window;
import com.com.com.Engine.Utils.Geom.Vec2;
import com.com.com.Engine.Entity.Object;
import com.com.com.Engine.Map.Room;

public class GameCamera extends Object implements StdBehaviour {

    public GameCamera() {
        super("Camera");
    }

    @Override
    public StdBehaviour getBehaviour() {
        return this;
    }

    Transform transformCamera;
    Transform targetTransform;

    @Override
    public void Start() {
        transformCamera = new Transform(this);

        Object player = Object.FindObject("Player");
        setStatic(true);
        Camera camera = new Camera(transformCamera, player);

        addComponent(transform);
        addComponent(camera);

        targetTransform = (Transform) player.getComponent(Transform.class);

        ImageRenderer sprite = (ImageRenderer) player.getComponent(ImageRenderer.class);

        if (sprite != null)
            Camera.setOffset(Window.getViewportCenter()
                    .subtractWith(new Vec2(sprite.getImage().getWidth() / 2, sprite.getImage().getHeight() / 2)));
    }

    @Override
    public void Update(float deltaTime) {

        if (targetTransform != null)
            transformCamera.position.setValues(targetTransform.position);
    }

    @Override
    public void DrawGUI(Graphics2D g) {

    }

    @Override
    public void ReceiveMessage(String origin) {
    }

    @Override
    public void RoomLoaded(Room room) {
    }

}
