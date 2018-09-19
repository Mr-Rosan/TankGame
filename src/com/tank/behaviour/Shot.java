package com.tank.behaviour;

import com.tank.screen.Screen;


//子弹类
public class Shot implements Runnable {

	public int x;
	public int y;
    int direct;//子弹的方向
    int speed=4; //子弹速度
    public boolean isLive=true;//子弹是否活着
    public Shot(int x,int y,int direct)
    {
        this.x=x;
        this.y=y;
        this.direct=direct;
    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
        {
            try {
                Thread.sleep(50);  //经测试休息50毫秒比较好，要不然内存容易撑不了
            } catch (Exception e) {
                // TODO: handle exception
            }
            switch(direct)
            {
            case 0://向上打出
                y-=speed;
                break;
            case 1://向右打出
                x+=speed;
                break;
            case 2://向下打出
                y+=speed;
                break;
            case 3://向左打出
                x-=speed;
                break;
            }
            //判断子弹是否碰到边缘 我们刚开始是setsize(400,300)
            if( x<0 || x>Screen.gamemap_width || y<0 || y>Screen.gamemap_higth)
            {
                this.isLive=false;
                break;
            }
        }
	}

}
