package com.badlogic.androidgames.framework;


/*
 * フレームバッファ(画面/VRAM)上に描画するインターフェース
 */
public interface Graphics {

	public static enum PixmapFormat{
		ARGB8888,ARGB4444,RGB565;
	}

	//JPEG/PNGを読み込む Pixmapに適したフォーマットが帰ってくる
	public Pixmap newPixmap(String fileName,PixmapFormat format);
	
	//指定された色でフレームバッファを塗りつぶす
	public void clear(int color);
	
	//指定された点を塗りつぶす　通称クリッピング
	public void drawPixel(int x,int y,int color);
	
	//直線の描画
	public void drawLine(int x,int y,int x2,int y2,int color);
	
	//四角形の描画
	public void drawRect(int x,int y,int width,int height,int color);
	
	//Pixmapの指定した矩形領域を描画する/部分描画
	public void drawPixmap(Pixmap pixmap,int x,int y,int srcX,int srcY,int srcWidth,int srcHeight);
	
	//Pixmapを指定した座標に描画する
	public void drawPixmap(Pixmap pixmap,int x,int y);
	public int getWidth();
	public int getHeight();
}
