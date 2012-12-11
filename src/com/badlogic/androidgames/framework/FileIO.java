package com.badlogic.androidgames.framework;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/*
 * ファイルの入出力
 * readAssetはAPKファイルからの読み取り
 */
public interface FileIO {
	
	public InputStream readAsset(String fileName) throws IOException;
	public InputStream readFile(String fileName) throws IOException;
	public OutputStream writeFile(String filaName) throws IOException;
	

}
