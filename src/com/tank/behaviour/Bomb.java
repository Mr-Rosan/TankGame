package com.tank.behaviour;

public class Bomb {
	//定义炸弹的坐标
    public int x;
	public int y;
    //炸弹的生命
    public int life=9;  //炸一次少一次
    boolean isLive=true;
    public Bomb(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    //减少生命值
    public void lifeDown()
    {
        if(life>0)
        {
            life--;
        }
        else
        {
            this.isLive=false;
        }
    }
}
