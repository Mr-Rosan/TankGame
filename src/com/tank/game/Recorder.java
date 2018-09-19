package com.tank.game;


//游戏记录类
public class Recorder {
	//记录每关有多少敌人
    private static int enNum=20;
    //设置我有多少可以用的人
    private static int myLife=3;
    //记录总共消灭了多少敌人
    private static int allEnNum=0;
    //游戏是否结束的参数
    private static boolean isgameover=false;
    private static boolean isVictory=false;
    private static boolean isgameovermusic=false; //失败音乐只播放一次
    
    
    public static boolean isIsgameovermusic() {
		return isgameovermusic;
	}
	public static void setIsgameovermusic(boolean isgameovermusic) {
		Recorder.isgameovermusic = isgameovermusic;
	}
	public static boolean isVictory() {    	
		return isVictory;
	}
	public static void setVictory(boolean isVictory) {
		Recorder.isVictory = isVictory;
	}
	public static boolean isIsgameover() {
    	if(myLife==0){
    		isgameover=true;
    	}    	
		return isgameover;
	}
	public static void setIsgameover(boolean isgameover) {
		Recorder.isgameover = isgameover;
	}
	public static int getAllEnNum() {
        return allEnNum;
    }
    public static void setAllEnNum(int allEnNum) {
        Recorder.allEnNum = allEnNum;
    }
    public static int getEnNum() {
        return enNum;
    }
    public static void setEnNum(int enNum) {
        Recorder.enNum = enNum;
    }
    public static int getMyLife() {
        return myLife;
    }
    public static void setMyLife(int myLife) {
        Recorder.myLife = myLife;
    }
    
    public static void reducePlayerLife(){
    	myLife--;
    }

    //减掉敌人数
    public static void reduceEnNum()
    {
        enNum--;
    }
    //消灭敌人
    public static void addEnNumRec()
    {
        allEnNum++;
    }
}
