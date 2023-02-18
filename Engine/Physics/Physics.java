package com.com.pong.Engine.Physics;

import com.com.pong.Engine.Components.RectCollider;
import com.com.pong.Engine.Entity.Object;
import com.com.pong.Engine.Utils.Geom.Rectangle;
import com.com.pong.Engine.Utils.Geom.Vec2;

public class Physics {
    
    public static final float G = 1f;
    public static final boolean accelearion_capped = true;
    public static final float acceleration_treshold = 60f;
    public static final float raycast_detail = 1f;

    public static RayHit CastRay(Vec2 position, Vec2 direction, float size, Object self){

        if(direction.thisMagnitude() < raycast_detail) return new RayHit();

        Vec2 testPoint = position;
        direction = direction.normalize();

        while(testPoint.subtractWith(position).thisMagnitude() < size){

            //Check for collision
            Object[] objs = Object.objects.toArray(new Object[Object.objects.size()]);
            for(Object o : objs) {

                if(o==self) continue;

                RectCollider collider = (RectCollider) o.getComponent(RectCollider.class);
                if(collider != null) {

                    Rectangle rectangle = Rectangle.CreateRectFromCollider(collider);
                    //System.out.println(rectangle.toString());
                    if(rectangle.Contains(testPoint)){ return new RayHit(o, collider, testPoint, position); }
                }
            }

            testPoint = testPoint.sumWith(direction.times(raycast_detail));
        }

        return new RayHit();
    }

    public static RayHit CastRay(Vec2 position, Vec2 direction, float size, Object self, int myLayer){

        if(direction.thisMagnitude() < raycast_detail) return new RayHit();

        Vec2 testPoint = position;
        direction = direction.normalize();

        while(testPoint.subtractWith(position).thisMagnitude() < size){

            //Check for collision
            Object[] objs = Object.objects.toArray(new Object[Object.objects.size()]);
            for(Object o : objs) {

                if(o==self || o.getLayer() != myLayer) continue;

                RectCollider collider = (RectCollider) o.getComponent(RectCollider.class);
                if(collider != null) {

                    Rectangle rectangle = Rectangle.CreateRectFromCollider(collider);
                    //System.out.println(rectangle.toString());
                    if(rectangle.Contains(testPoint)){ return new RayHit(o, collider, testPoint, position); }
                }
            }

            testPoint = testPoint.sumWith(direction.times(raycast_detail));
        }

        return new RayHit();
    }
}
