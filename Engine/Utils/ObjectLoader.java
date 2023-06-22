package com.com.com.Engine.Utils;
import com.com.com.Engine.Entity.Object;
import com.com.com.Engine.Utils.Geom.*;
import com.com.com.Assets.Objects.*;
import com.com.com.Engine.Components.Camera;
public class ObjectLoader {
public static Object LoadObjectOfName(String name, Vec2 position, float angle, Vec2 scale){
			Object obj = null;
			switch (name){
case "Coin" : obj = new Coin(); break;
case "CoinBlock" : obj = new CoinBlock(); break;
case "Enemy" : obj = new Enemy(); break;
case "GameCamera" : obj = new GameCamera(); break;
case "Player" : obj = new Player(); break;
case "Wall" : obj = new Wall(); break;

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