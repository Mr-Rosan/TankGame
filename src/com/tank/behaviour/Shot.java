package com.tank.behaviour;

import com.tank.screen.Screen;


//�ӵ���
public class Shot implements Runnable {

	public int x;
	public int y;
    int direct;//�ӵ��ķ���
    int speed=4; //�ӵ��ٶ�
    public boolean isLive=true;//�ӵ��Ƿ����
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
                Thread.sleep(50);  //��������Ϣ50����ȽϺã�Ҫ��Ȼ�ڴ����׳Ų���
            } catch (Exception e) {
                // TODO: handle exception
            }
            switch(direct)
            {
            case 0://���ϴ��
                y-=speed;
                break;
            case 1://���Ҵ��
                x+=speed;
                break;
            case 2://���´��
                y+=speed;
                break;
            case 3://������
                x-=speed;
                break;
            }
            //�ж��ӵ��Ƿ�������Ե ���Ǹտ�ʼ��setsize(400,300)
            if( x<0 || x>Screen.gamemap_width || y<0 || y>Screen.gamemap_higth)
            {
                this.isLive=false;
                break;
            }
        }
	}

}
