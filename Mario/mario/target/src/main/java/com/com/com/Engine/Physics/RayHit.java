package com.com.com.Engine.Physics;

import java.io.Serializable;

import com.com.com.Engine.Components.RectCollider;
import com.com.com.Engine.Entity.Object;
import com.com.com.Engine.Utils.Geom.Vec2;

public class RayHit implements Serializable {

    public Object collider = null;
    public RectCollider colliderComponent = null;
    public Vec2 point = null;
    public Vec2 normal = null;

    private Vec2 original = null;

    public Vec2 getOrigin() {
        return original;
    }

    protected RayHit() {
    }

    protected RayHit(Object collider, RectCollider component, Vec2 point, Vec2 original) {

        this.original = original;
        this.collider = collider;
        this.colliderComponent = component;
        this.point = point;

        this.normal = calculateNormalVector();
    }

    private Vec2 calculateNormalVector() {

        return null;
    }

    public boolean isEmpty() {
        return (collider == null || colliderComponent == null || point == null);
    }
}
