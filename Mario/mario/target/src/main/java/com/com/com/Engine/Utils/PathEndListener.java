package com.com.com.Engine.Utils;

import com.com.com.Engine.Utils.Geom.Vec2;

public interface PathEndListener {

    public void pathStart();

    public void pathEnd();

    public void pathMoved(Vec2 position);
}
