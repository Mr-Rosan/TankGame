package com.tank.behaviour;

public class Bomb {
	//����ը��������
    public int x;
	public int y;
    //ը��������
    public int life=9;  //ըһ����һ��
    boolean isLive=true;
    public Bomb(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    //��������ֵ
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
