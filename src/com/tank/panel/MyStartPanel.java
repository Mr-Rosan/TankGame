package com.tank.panel;

import java.awt.*;

import javax.swing.*;

import com.tank.screen.Screen;

public class MyStartPanel extends JPanel implements Runnable {
	//times��ΪƵ�������ʱ��
	int times=0;
	//��ʼ����ͼƬ
	Image startImg=getToolkit().getDefaultToolkit().getImage(Panel.class.getResource("/tank/tankgamestart.png"));;

    //��ʼ�����ͼ
	@Override
    public void paint(Graphics g)
    {
        super.paint(g);
        //����Ҫ���Ƶķ�Χ ��Ļ����ɫ
        g.fillRect(0, 0, Screen.gamemap_width, Screen.gamemap_higth);
        //ȡģ,����ػ�
        if(times%2==0)
        {
            //g.setColor(Color.yellow);
            //������Ϣ������
            //Font myFont=new Font("������κ",Font.BOLD,30);
            //g.setFont(myFont);
            g.drawImage(startImg,(Screen.gamemap_width-400)/2,(Screen.gamemap_higth-230)/2,400,130,this);
            //g.drawString("̹�˴�ս", Screen.gamemap_width/2-50, Screen.gamemap_higth/2-25);
        }
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
        {
            //����
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                times++;
                //�ػ�
                this.repaint();
        }
		//this.repaint();
	}
}
