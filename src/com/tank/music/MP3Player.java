package com.tank.music;

import java.io.*;

import javazoom.jl.player.Player;

//mp3播放工具类
public class MP3Player implements Runnable {

	private String filename;
    private Player player;

    public MP3Player(String filename) {
        this.filename = filename;
    }

    public void play() {
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
            player = new Player(buffer);
            player.play();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{			
			this.play();
		}catch(Exception e){
			System.out.println("未找到音乐路径");
		}
	}

}
