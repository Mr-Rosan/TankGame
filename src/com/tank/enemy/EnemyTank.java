package com.tank.enemy;

import java.util.Vector;

import com.tank.behaviour.Shot;
import com.tank.model.Tank;
import com.tank.screen.Screen;

public class EnemyTank extends Tank implements Runnable{
	
	//声明生存变量	

	public EnemyTank(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public Vector<Shot> ss=new Vector<Shot>();

	int times=0;//作为连发子弹设下的标志  （下面将用到）
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
        {
            switch(this.direct)
            {  //向上
            case 0:
                //说明坦克正在向上移动
                for(int i=0;i<30;i++)//30可以改成其他数，这里是控制坦克移动距离
                {   //保证坦克不出边界   （下面思路相同）
                    if(y>0)
                    {
                        y-=speed;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO: handle exception
                    }
                }
                break;
            case 1:
                //向右
                for(int i=0;i<30;i++)
                {
                    if(x+30<Screen.gamemap_width&&y+20<Screen.gamemap_higth)
                    {
                        x+=speed;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO: handle exception
                    }
                }
                break;
            case 2:
                //向下
                for(int i=0;i<30;i++)
                {
                    if(y+30<Screen.gamemap_higth&&x+20<Screen.gamemap_width)
                    {
                        y+=speed;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO: handle exception
                    }
                }
                break;
            case 3:
                //向左
                for(int i=0;i<30;i++)
                {
                    if(x>0)
                    {
                        x-=speed;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO: handle exception
                    }
                }   
                break;
            }

            this.times++;  

            //判断是否给坦克加入新的子弹
            if(times%2==0)
            {
                if(isLive)
                {
                    if(ss.size()<5)// 够不够五颗子弹
                    {
                        Shot s=null;
                        //没有子弹
                        //添加
                        switch(direct)
                        {
                        case 0:
                            //创建一颗子弹
                            s=new Shot(x+10, y,0);
                            //把子弹加入向量
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
                        Thread t=new Thread(s);
                        t.start();

                    }
                }
            }

            //让坦克随机产生一个新的方向
            this.direct=(int)(Math.random()*4);

            //判断敌人坦克是否死亡
            if(this.isLive==false)
            {
                //让坦克死亡后退出线程
                break;
            }

        }
	}	
}
