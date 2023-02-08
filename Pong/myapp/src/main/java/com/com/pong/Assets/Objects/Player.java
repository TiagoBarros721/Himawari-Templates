package com.com.pong.Assets.Objects;

import java.awt.Color;
import java.awt.Graphics2D;

import com.com.pong.Engine.Entity.Object;
import com.com.pong.Engine.Gfx.Widget;
import com.com.pong.Engine.Gfx.Debugging.type;
import com.com.pong.Engine.Utils.StdBehaviour;
import com.com.pong.Engine.Utils.Window;
import com.com.pong.Engine.Utils.Geom.Rectangle;

public class Player extends Object implements StdBehaviour {

    public static int score = 0;

    public Player() {
        super("Player");
        
    }

    @Override
    public void Start() {
        

    }

    @Override
    public void Update(float deltaTime) {
        

    }

    @Override
    public void DrawGUI(Graphics2D g) {
        
        Widget.setColor(Color.BLACK);
        Widget.setDrawType(type.FILLED);
        Widget.drawRectangle(new Rectangle(0,0, Window.width, Window.height), g);

        Widget.setColor(Color.WHITE);

        if(Object.FindObject("Ball") == null){

            Widget.drawText("Game Over D:\n Score: " + score, (int)(Window.width/2), 50, g);
        }else{
            
            Widget.drawText(String.valueOf(score), (int)(Window.width/2), 50, g);
        }
    }

    @Override
    public void ReceiveMessage(String message) {
        
    }

    @Override
    public StdBehaviour getBehaviour() {return this;}
}
