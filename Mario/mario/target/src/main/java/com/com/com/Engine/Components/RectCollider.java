package com.com.com.Engine.Components;

import com.com.com.Engine.Utils.Geom.Rectangle;
import com.com.com.Engine.Utils.Geom.Vec2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.com.com.Engine.Entity.Object;

public class RectCollider extends Component {

    public Transform transform;
    public Vec2 bounds;
    private Vec2 originalBounds;
    public Vec2 offset = new Vec2();

    private Object object;
    private List<String> ignoreMask = new ArrayList<String>();

    public String[] getIgnoredTags() {
        return ignoreMask.toArray(new String[ignoreMask.size()]);
    }

    public void ignoreTag(String tag) {
        ignoreMask.add(tag);
    }

    public void regonizeTag(String tag) {
        ignoreMask.remove(tag);
    }

    public int getIgnoreTagsCount() {
        return ignoreMask.size();
    }

    public boolean isTagIgnored(String tag) {
        return ignoreMask.contains(tag);
    }

    public boolean solid = true;

    public void resizeCollider(Vec2 scale) {

        bounds = originalBounds.times(scale);
    }

    public void resizeColliderSpecifics(Vec2 scale) {

        bounds = scale;
    }

    public RectCollider(Transform transform, Vec2 bounds) {

        this.transform = transform;
        this.originalBounds = bounds;
        resizeColliderSpecifics(bounds);

        object = transform.obj;
    }

    public boolean isCollidingWith(Object obj) {

        for (Iterator<Object> iterator = Object.objects.iterator(); iterator.hasNext();) {
            Object o = iterator.next();

            if (o == obj) {

                RectCollider r = (RectCollider) obj.getComponent(RectCollider.class);
                if (r != null) {

                    Transform t = o.transform;

                    Rectangle rect = new Rectangle(t.position.x, t.position.y, r.bounds.x, r.bounds.y);
                    Rectangle myRect = new Rectangle(transform.position.x + offset.x, transform.position.y + offset.y,
                            bounds.x, bounds.y);

                    if (myRect.Intersects(rect))
                        return true;

                } else
                    continue;

            }
        }

        return false;
    }

    public boolean isColliding() {

        List<Object> objs = Object.objects;
        for (Iterator<Object> iterator = objs.iterator(); iterator.hasNext();) {
            Object o = iterator.next();

            if (o == object)
                continue;

            RectCollider r = (RectCollider) o.getComponent(RectCollider.class);
            if (r != null) {

                Transform t = o.transform;

                Rectangle rect = new Rectangle(t.position.x, t.position.y, r.bounds.x, r.bounds.y);
                Rectangle myRect = new Rectangle(transform.position.x + offset.x, transform.position.y + offset.y,
                        bounds.x, bounds.y);

                if (myRect.Intersects(rect)) {
                    return true;
                }

            } else
                continue;
        }

        return false;
    }

    public boolean willCollideWith(Object obj, Vec2 position) {

        List<Object> objs = Object.objects;
        for (int i = 0; i < objs.size(); i++) {
            Object o = objs.get(i);

            if (o == obj) {

                RectCollider r = (RectCollider) obj.getComponent(RectCollider.class);

                if (r != null) {

                    if (!r.solid || isTagIgnored(o.getTag()))
                        continue;

                    Transform t = o.transform;

                    Rectangle rect = new Rectangle(t.position.x, t.position.y, r.bounds.x, r.bounds.y);
                    Rectangle myRect = new Rectangle(position.x + offset.x, position.y + offset.y, bounds.x, bounds.y);

                    if (myRect.Intersects(rect))
                        return true;

                }
            }
        }

        return false;
    }

    public boolean willCollide(Vec2 position) {

        Object[] copyArray = Object.objects.toArray(new Object[Object.objects.size()]);
        for (int i = 0; i < copyArray.length; i++) {
            Object o = copyArray[i];

            if (o == object)
                continue;

            RectCollider r = (RectCollider) o.getComponent(RectCollider.class);

            if (r != null) {

                if (!r.solid || isTagIgnored(o.getTag()))
                    continue;
                Transform t = o.transform;

                Rectangle rect = new Rectangle(t.position.x, t.position.y, r.bounds.x, r.bounds.y);
                Rectangle myRect = new Rectangle(position.x + offset.x, position.y + offset.y, bounds.x, bounds.y);

                if (myRect.Intersects(rect))
                    return true;

            } else
                continue;

        }

        return false;
    }

    public boolean isCollidingWith(Vec2 point) {

        Rectangle myRect = new Rectangle(transform.position.x + offset.x, transform.position.y + offset.y, bounds.x,
                bounds.y);
        return myRect.Contains(point);
    }

    public boolean isCollidingWith(RectCollider collider) {

        Rectangle myRect = new Rectangle(transform.position.x + offset.x, transform.position.y + offset.y, bounds.x,
                bounds.y);
        Rectangle otherRect = new Rectangle(collider.transform.position.x + collider.offset.x,
                collider.transform.position.y + collider.offset.y,
                collider.bounds.x, collider.bounds.y);
        return myRect.Intersects(otherRect);
    }

    public double getCollisionArea(RectCollider collider) {

        Rectangle myRect = new Rectangle(transform.position.x + offset.x, transform.position.y + offset.y, bounds.x,
                bounds.y);
        Rectangle otherRect = new Rectangle(collider.transform.position.x + collider.offset.x,
                collider.transform.position.y + collider.offset.y,
                collider.bounds.x, collider.bounds.y);

        if (myRect.Intersects(otherRect)) {

            Rectangle difference = new Rectangle(myRect.x - otherRect.x, myRect.y - otherRect.y,
                    myRect.width - otherRect.width, myRect.height - otherRect.height);
            return difference.area();

        } else
            return 0;
    }
}
