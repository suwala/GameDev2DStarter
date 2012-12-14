package com.badlogic.androidgames.gamedev2d;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;
import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpatialHashGrid;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.gl.Vertices;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Vector2;

public class SpriteBatcherTest extends GLGame{
	
	@Override
	public Screen getStartScreen(){
		return new SpriteBatcherScreen(this);
	}
	
	class SpriteBatcherScreen extends Screen{

		final int NUM_TRAGETS = 20;
		final float WORLD_WIDTH = 9.6f;
		final float WORLD_HEIGHT = 4.8f;
		GLGraphics glGraphics;
		Cannon cannon;
		DynamicGameObject ball;
		List<GameObject> targets;
		SpatialHashGrid grid;
		
		TextureRegion cannonRegion;
		TextureRegion ballRegion;
		TextureRegion bobRegion;
		SpriteBatcher batcher;
		
		Vector2 touchPos = new Vector2();
		Vector2 gravity = new Vector2(0,-10);
		
		Camera2D camera;
		Texture texture;
		
		public SpriteBatcherScreen(Game game) {
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			
			cannon = new Cannon(0,0, 1,0.5f);
			ball = new DynamicGameObject(0, 0, 0.2f, 0.2f);
			targets = new ArrayList<GameObject>(NUM_TRAGETS);
			grid = new SpatialHashGrid(WORLD_WIDTH, WORLD_HEIGHT, 2.5f);
			for(int i=0;i<NUM_TRAGETS;i++){
				GameObject target = new GameObject((float)Math.random()*WORLD_WIDTH, (float)Math.random() * WORLD_HEIGHT, 0.5f, 0.5f);
				grid.insertStaticObject(target);
				targets.add(target);
			}
			
			batcher = new SpriteBatcher(glGraphics, 100);
			camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
		}

		@Override
		public void update(float deltaTime) {
			List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			
			int len = touchEvents.size();
			for(int i=0;i < len;i++){
				TouchEvent event = touchEvents.get(i);
				camera.touchToWorld(touchPos.set(event.x,event.y));
				
				cannon.angle = touchPos.sub(cannon.position).angle();
				
				if(event.type == TouchEvent.TOUCH_UP){
					float radians = cannon.angle * Vector2.TO_RADIANS;
					float ballSpeed = touchPos.len() * 2;
					ball.position.set(cannon.position);
					ball.velocity.x = FloatMath.cos(radians) * ballSpeed;
					ball.velocity.y = FloatMath.sin(radians) * ballSpeed;
					ball.bounds.lowerLeft.set(ball.position.x - 0.1f,ball.position.y - 0.1f);
					
				}
			}
			
			//追記：一定値を越えたらxを反転(壁で反射)
			if(ball.position.x > WORLD_WIDTH)
				ball.velocity.x = -ball.velocity.x;
			if(ball.position.x < 0)
				ball.velocity.x = -ball.velocity.x;
			
			ball.velocity.add(gravity.x * deltaTime,gravity.y * deltaTime);
			ball.position.add(ball.velocity.x * deltaTime,ball.velocity.y * deltaTime);
			ball.bounds.lowerLeft.add(ball.velocity.x * deltaTime,ball.velocity.y * deltaTime);
			
			List<GameObject> colliders = grid.getPotentialColliders(ball);
			len = colliders.size();
			for(int i =0;i<len;i++){
				GameObject collider = colliders.get(i);
				if(OverlapTester.overlapRectangles(ball.bounds, collider.bounds)){
					grid.removeObject(collider);
					targets.remove(collider);
					ball.velocity.x = -ball.velocity.x;
				}
			}
			
			if(ball.position.y > 0){
				camera.position.set(ball.position);
				camera.zoom = 1 + ball.position.y/WORLD_HEIGHT;
			}else{
				camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2);
				camera.zoom = 1.2f;
			}
			
			
		}

		@Override
		public void present(float deltaTime) {
			
			GL10 gl = glGraphics.getGL();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			camera.setViewportAndMatrices();
			
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			
			batcher.beginBatch(texture);
			
			int len = targets.size();
			for(int i=0;i<len;i++){
				GameObject target = targets.get(i);
				batcher.drawSprite(target.position.x, target.position.y, 0.5f, 0.5f, bobRegion);
			}
			
			batcher.drawSprite(ball.position.x, ball.position.y, 0.2f, 0.2f, ballRegion);
			batcher.drawSprite(cannon.position.x, cannon.position.y, 1, 0.5f, cannon.angle,cannonRegion);
			
			batcher.endBatch();
		}

		@Override
		public void pause() {
			// TODO 自動生成されたメソッド・スタブ
			
		}

		@Override
		public void resume() {
			texture = new Texture(((GLGame)game),"dir/texture/atlas.png");
			cannonRegion = new TextureRegion(texture, 0, 0, 64, 32);
			ballRegion = new TextureRegion(texture, 0, 32, 16, 16);
			bobRegion = new TextureRegion(texture, 32, 32, 32, 32);
		}

		@Override
		public void dispose() {
			// TODO 自動生成されたメソッド・スタブ
			
		}
		
	}

}