package com.badlogic.androidgames.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable{
	
	AndroidGame game;
	Bitmap freambuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;
	
	public AndroidFastRenderView(AndroidGame game,Bitmap freambuffer) {
		super(game);
		this.game = game;
		this.freambuffer = freambuffer;
		this.holder = getHolder();		
		
	}
	
	public void resume(){
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}
	
	public void run(){
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while(running){
			if(!holder.getSurface().isValid())
				continue;
			
			float deltaTime = (System.nanoTime() - startTime)/1000000000.0f;
			
			startTime = System.nanoTime();
			
			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().present(deltaTime);
			
			//フレームバッファにレンダリング
			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(freambuffer, null, dstRect,null);
			//ここで描画
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	public void pause(){
		running = false;
		while(true){
			try{
				renderThread.join();
				break;
			}catch (InterruptedException e){
				//リトライ
			}
		}
	}

}
