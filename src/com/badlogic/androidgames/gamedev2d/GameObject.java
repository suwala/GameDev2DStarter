package com.badlogic.androidgames.gamedev2d;

import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class GameObject {

	public final Vector2 position;
	public final Rectangle bounds;
	
	public GameObject(float x,float y,float width,float height){
		this.position = new Vector2(x,y);
		//外接矩形の設定　1.0fだとちょっと大きい？
		this.bounds= new Rectangle(x-width/2, y-height/2, width, height);
	}
}
