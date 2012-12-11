package com.badlogic.androidgames.framework;

/*
 * モジュールやウィンドウイベントの通知を受け取り　画面上に実装
 */
public interface Game {
	
	public Input getInput();
	public FileIO getFileIO();
	public Graphics getGraphics();
	
	public Audio getAudio();
	
	//使用するスクリーンのセット
	public void setScreen(Screen screen);
	
	//現在のスクリーンを返す
	public Screen getCurrentScreen();
	
	//実装時はオーバーライドしゲームの最初のスクリーンを返す
	public Screen getStartScreen();
	
	

}
