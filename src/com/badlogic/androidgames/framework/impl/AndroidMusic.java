package com.badlogic.androidgames.framework.impl;

import java.io.IOException;

import com.badlogic.androidgames.framework.Music;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class AndroidMusic implements Music,OnCompletionListener{
	
	MediaPlayer mediaPlayer;
	//再生中かのフラグ
	boolean isPrepared = false;
	
	public AndroidMusic(AssetFileDescriptor assetDedcriptor){
		mediaPlayer = new MediaPlayer();
		try{
			mediaPlayer.setDataSource(assetDedcriptor.getFileDescriptor(),
					assetDedcriptor.getStartOffset(),assetDedcriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
		}catch (Exception e){
			throw new RuntimeException("Couldn't load music");
		}
	}
	
	@Override
	public void dispose(){
		if(mediaPlayer.isPlaying())
			mediaPlayer.stop();
		//再生中だとエラーが出るので一旦STOP
		mediaPlayer.release();
	}
	
	@Override
	public boolean isLooping(){
		return mediaPlayer.isLooping();
	}
	
	@Override
	public boolean isPlaying(){
		return mediaPlayer.isPlaying();
	}
	
	@Override
	public boolean isStopped(){
		return !isPrepared;
	}
	
	@Override
	public void pause(){
		if(mediaPlayer.isPlaying())
			mediaPlayer.pause();
	}
	
	@Override
	public void play(){
		if(mediaPlayer.isPlaying())
			return;
		
		//他のスレッドでも呼ばれる可能性があるのでシンクロ
		try{
			synchronized(this){
				//準備中かのチェック
				if(!isPrepared)
					mediaPlayer.prepare();
				mediaPlayer.start();
				
			}
		}catch (IllegalStateException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void setLooping(boolean isLooping){
		mediaPlayer.setLooping(isLooping);
	}
	
	@Override
	public void setVolume(float volume){
		mediaPlayer.setVolume(volume, volume);
	}
	
	@Override
	public void stop(){
		mediaPlayer.stop();
		synchronized(this){
			isPrepared = false;
		}
	}
	
	@Override
	public void onCompletion(MediaPlayer player){
		synchronized(this){
			isPrepared = false;
		}
	}

}
