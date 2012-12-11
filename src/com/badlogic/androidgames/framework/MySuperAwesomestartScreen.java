package com.badlogic.androidgames.framework;


import com.badlogic.androidgames.framework.Graphics.PixmapFormat;

public class MySuperAwesomestartScreen extends Screen {

	Pixmap awesomePic;
	int x;
	
	
	public MySuperAwesomestartScreen(Game game) {
		super(game);
		// TODO 自動生成されたコンストラクター・スタブ
		this.awesomePic = game.getGraphics().newPixmap("data/pic.png", PixmapFormat.RGB565);
	}

	@Override
	public void update(float deltaTime) {
		// TODO 自動生成されたメソッド・スタブ
		this.x ++;
		if(x > 100)
			this.x = 0;
		
	}

	@Override
	public void present(float deltaTime) {
		// TODO 自動生成されたメソッド・スタブ
		this.game.getGraphics().clear(0);
		game.getGraphics().drawPixmap(awesomePic, x, 0, 0, 0, awesomePic.getWidth(), awesomePic.getHeight());

	}

	@Override
	public void pause() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void resume() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void dispose() {
		// TODO 自動生成されたメソッド・スタブ
		awesomePic.dispose();
	}

}
