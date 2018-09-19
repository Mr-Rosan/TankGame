package com.tank.enemy;

import java.util.Vector;

import com.tank.behaviour.Shot;
import com.tank.model.Tank;
import com.tank.screen.Screen;

public class EnemyTank extends Tank implements Runnable{
	
	//�����������	

	public EnemyTank(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public Vector<Shot> ss=new Vector<Shot>();

	int times=0;//��Ϊ�����ӵ����µı�־  �����潫�õ���
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
        {
            switch(this.direct)
            {  //����
            case 0:
                //˵��̹�����������ƶ�
                for(int i=0;i<30;i++)//30���Ըĳ��������������ǿ���̹���ƶ�����
                {   //��֤̹�˲����߽�   ������˼·��ͬ��
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
                //����
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
                //����
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
                //����
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

            //�ж��Ƿ��̹�˼����µ��ӵ�
            if(times%2==0)
            {
                if(isLive)
                {
                    if(ss.size()<5)// ����������ӵ�
                    {
                        Shot s=null;
                        //û���ӵ�
                        //���
                        switch(direct)
                        {
                        case 0:
                            //����һ���ӵ�
                            s=new Shot(x+10, y,0);
                            //���ӵ���������
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

                        //�����ӵ��߳�
                        Thread t=new Thread(s);
                        t.start();

                    }
                }
            }

            //��̹���������һ���µķ���
            this.direct=(int)(Math.random()*4);

            //�жϵ���̹���Ƿ�����
            if(this.isLive==false)
            {
                //��̹���������˳��߳�
                break;
            }

        }
	}	
}
