package com.badlogic.androidgames.framework;

/*
 * Game型のインスタンスが必要
 * 音声・描画・ファイルIO・ユーザーの入出力を行う
 * Gameはインターフェースなので実体化したクラスが必要
 */

public abstract class Screen {

	protected final Game game;

	public Screen(Game game){
		this.game = game;
	}
	
	public abstract void update(float deltaTime);
	public abstract void present(float deltaTime);
	
	//Gameインスタンスで呼び出される
	public abstract void pause();
	public abstract void resume();
	
	//リソースの解放　※まっさらになる
	public abstract void dispose();
}

