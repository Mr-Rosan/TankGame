package com.tank.panel;

import java.awt.*;

import javax.swing.*;

import com.tank.screen.Screen;

public class MyStartPanel extends JPanel implements Runnable {
	//times作为频闪间隔计时器
	int times=0;
	//开始界面图片
	Image startImg=getToolkit().getDefaultToolkit().getImage(Panel.class.getResource("/tank/tankgamestart.png"));;

    //开始界面绘图
	@Override
    public void paint(Graphics g)
    {
        super.paint(g);
        //画笔要绘制的范围 屏幕画黑色
        g.fillRect(0, 0, Screen.gamemap_width, Screen.gamemap_higth);
        //取模,间隔重绘
        if(times%2==0)
        {
            //g.setColor(Color.yellow);
            //开关信息的字体
            //Font myFont=new Font("华文新魏",Font.BOLD,30);
            //g.setFont(myFont);
            g.drawImage(startImg,(Screen.gamemap_width-400)/2,(Screen.gamemap_higth-230)/2,400,130,this);
            //g.drawString("坦克大战", Screen.gamemap_width/2-50, Screen.gamemap_higth/2-25);
        }
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
        {
            //休眠
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                times++;
                //重画
                this.repaint();
        }
		//this.repaint();
	}
}
