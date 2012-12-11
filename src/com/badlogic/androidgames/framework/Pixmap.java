package com.badlogic.androidgames.framework;

import com.badlogic.androidgames.framework.Graphics.PixmapFormat;

public interface Pixmap {

	public int getWidth();
	public int getHeight();
	
	//Pixmapをメモリに格納したときのPixelFomatを返す
	public PixmapFormat getFormat();
	
	//リソースの開放　インスタンスの削除
	public void dispose();
	
}
