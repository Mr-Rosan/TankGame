package com.tank.game;


//��Ϸ��¼��
public class Recorder {
	//��¼ÿ���ж��ٵ���
    private static int enNum=20;
    //�������ж��ٿ����õ���
    private static int myLife=3;
    //��¼�ܹ������˶��ٵ���
    private static int allEnNum=0;
    //��Ϸ�Ƿ�����Ĳ���
    private static boolean isgameover=false;
    private static boolean isVictory=false;
    private static boolean isgameovermusic=false; //ʧ������ֻ����һ��
    
    
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

    //����������
    public static void reduceEnNum()
    {
        enNum--;
    }
    //�������
    public static void addEnNumRec()
    {
        allEnNum++;
    }
}
