package com.com.com.Engine.Components;

import com.com.com.Engine.Entity.Object;
import com.com.com.Engine.Input.Input;
import com.com.com.Engine.Physics.Physics;
import com.com.com.Engine.Utils.Renderer;
import com.com.com.Engine.Utils.Geom.Vec2;

public class Body extends Component {

    // Physics variables
    private Transform transform;
    private RectCollider collider;

    private Vec2 gravity = new Vec2();
    private Vec2 totalForce = new Vec2();

    public float drag = 0.1f;
    public float mass = 1;

    public boolean solid = true;

    public Body(Transform transform, RectCollider collider, float mass) {

        this.transform = transform;
        this.mass = mass;

        this.collider = collider;
    }

    public void PhysicsUpdate(float deltaTime) {

        if (transform == null || mass <= 0 || collider == null)
            return;

        gravity = Vec2.DOWN.times(Physics.G * mass);

        ApplyForce(gravity);

        // Cap acceleration
        if (Physics.accelearion_capped)
            totalForce = totalForce.clampY(-Physics.acceleration_treshold, Physics.acceleration_treshold);

        if (solid) {

            boolean willCollide = collider.willCollide(transform.position.sumWith(totalForce));
            if (willCollide) {

                ApplyForce(totalForce.inverse());
            }
            transform.translate(totalForce.times(deltaTime).times(mass * Renderer.getFPS()), collider);
        } else
            transform.translate(totalForce.times(deltaTime).times(mass * Renderer.getFPS()));
        // System.out.println(totalForce.toString());
    }

    public float calculateAcceleration() {

        Vec2 a = totalForce.divide(mass);
        return a.x + a.y;
    }

    public void ApplyForce(Vec2 force) {

        totalForce = totalForce.sumWith(force);
    }

    public void resetForce() {

        totalForce = Vec2.ZERO;
    }
}
