package com.com.pong.Engine.Utils;
import com.com.pong.Engine.Entity.Object;
import com.com.pong.Engine.Utils.Geom.*;
import com.com.pong.Assets.Objects.*;
import com.com.pong.Engine.Components.Camera;
public class ObjectLoader {
public static Object LoadObjectOfName(String name, Vec2 position, float angle, Vec2 scale){
			Object obj = null;
			switch (name){
case "Ball" : obj = new Ball(); break;
case "Enemy" : obj = new Enemy(); break;
case "Paddle" : obj = new Paddle(); break;
case "Player" : obj = new Player(); break;

}
			if(obj.getComponent(Camera.class) != null){
						System.out.println("[ERROR] Can't instantiate the Camera");
return null;
}
//Apply the objects properties
obj.transform.setPosition(position);
obj.transform.setAngle(angle);
obj.transform.setScale(scale);
obj.getBehaviour().Start();
			return obj;
}
}