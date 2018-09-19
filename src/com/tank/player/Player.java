package com.tank.player;

import java.util.Vector;

import com.tank.behaviour.Shot;
import com.tank.model.Tank;
import com.tank.screen.Screen;

public class Player extends Tank {
	
	//新建集合存放子弹
	public Vector<Shot> ss=new Vector<Shot>();
	//子弹
	public Shot s=null;	

	//开炮方法
	public void shotEnemy(){
		switch (this.direct) //子弹的方向
        {
        case 0:
            s=new Shot(x+10, y,0);
            ss.add(s);
            break;
        case 1:
            s=new Shot(x+30,y+10,1);
            ss.add(s);
            break;
        case 2:
            s=new Shot(x+10,y+30,2);
            ss.add(s);
            break;
        case 3:
            s=new Shot(x,y+10,3);
            ss.add(s);
            break;
        }
        //启动子弹线程
        Thread t = new Thread(s);
        t.start();
	}

	public Player(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	//坦克向上移动
    public void moveUp()
    {
    	if(y>0)
        {
            y-=speed;
        }
    }
    //坦克向右移动
    public void moveRight()
    {
    	if(x+30<Screen.gamemap_width&&y+20<Screen.gamemap_higth)
        {
            x+=speed;
        }
    }
    //坦克向下移动
    public void moveDown()
    {
    	 if(y+30<Screen.gamemap_higth&&x+20<Screen.gamemap_width)
         {
             y+=speed;
         }
    }
    //坦克向左移动
    public void moveLeft()
    {
    	if(x>0)
        {
            x-=speed;
        }
    }

}
