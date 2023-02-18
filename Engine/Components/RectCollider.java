package Engine.Components;

import Engine.Utils.Geom.Rectangle;
import Engine.Utils.Geom.Vec2;

import java.util.Iterator;
import java.util.List;

import Engine.Entity.Object;

public class RectCollider extends Component {

    public Transform transform;
    public Vec2 bounds;
    private Vec2 originalBounds;
    private Object object;

    public boolean solid = true;

    public void resizeCollider(Vec2 scale) {

        bounds = originalBounds.times(scale);
    }

    public void resizeColliderSpecifics(Vec2 scale) {

        bounds = scale;
    }

    public RectCollider(Transform transform, Vec2 bounds) {

        this.transform = transform;
        this.bounds = bounds;

        this.originalBounds = bounds;

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
                    Rectangle myRect = new Rectangle(transform.position.x, transform.position.y, bounds.x, bounds.y);

                    if (myRect.Intersects(rect))
                        return true;

                } else
                    return false;

            }
        }

        return false;
    }

    public Object isColliding() {

        List<Object> objs = Object.objects;
        for (Iterator<Object> iterator = objs.iterator(); iterator.hasNext();) {
            Object o = iterator.next();

            if (o == object)
                continue;

            RectCollider r = (RectCollider) o.getComponent(RectCollider.class);
            if (r != null) {

                Transform t = o.transform;

                Rectangle rect = new Rectangle(t.position.x, t.position.y, r.bounds.x, r.bounds.y);
                Rectangle myRect = new Rectangle(transform.position.x, transform.position.y, bounds.x, bounds.y);

                if (myRect.Intersects(rect)) {
                    return o;
                }

            } else
                continue;
        }

        return null;
    }

    public boolean willCollideWith(Object obj, Vec2 position) {

        List<Object> objs = Object.objects;
        for (int i = 0; i < objs.size(); i++) {
            Object o = objs.get(i);

            System.out.println(o);

            if (o == obj) {

                RectCollider r = (RectCollider) obj.getComponent(RectCollider.class);
                if (r != null) {

                    Transform t = o.transform;

                    Rectangle rect = new Rectangle(t.position.x, t.position.y, r.bounds.x, r.bounds.y);
                    Rectangle myRect = new Rectangle(position.x, position.y, bounds.x, bounds.y);

                    if (myRect.Intersects(rect))
                        return true;

                } else
                    return false;

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

                Transform t = o.transform;

                Rectangle rect = new Rectangle(t.position.x, t.position.y, r.bounds.x, r.bounds.y);
                Rectangle myRect = new Rectangle(position.x, position.y, bounds.x, bounds.y);

                if (myRect.Intersects(rect))
                    return true;

            } else
                return false;

        }

        return false;
    }

    public boolean isCollidingWith(Vec2 point) {

        Rectangle myRect = new Rectangle(transform.position.x, transform.position.y, bounds.x, bounds.y);
        return myRect.Contains(point);
    }
}
