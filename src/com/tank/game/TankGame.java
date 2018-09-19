package com.tank.game;




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


import com.tank.music.MP3Player;
import com.tank.panel.MyPanel;
import com.tank.panel.MyStartPanel;
import com.tank.screen.Screen;

public class TankGame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//定义开始工具栏
	JMenuBar jmb=null;
	//定义开始菜单按钮
	JMenu jm=null;
	JMenu Jmc=null;
	//定义按钮弹出项
	JMenuItem jm_start=null;
	JMenuItem jm_restart=null;
	JMenuItem jm_exit=null;
	//关于我们
	JMenuItem jm_about=null;
	
	
	//定义游戏开始绘图区
	MyStartPanel msp=new MyStartPanel();
	//游戏绘图区
	MyPanel mp=null;	
	
	public TankGame()
    {  
		//实例化构造菜单
		jmb=new JMenuBar();
		jm=new JMenu("游戏(G)");
		Jmc=new JMenu("关于");
		//设置快捷方式
		jm.setMnemonic('G');
		jm_start=new JMenuItem("开始新游戏(N)");
		jm_restart=new JMenuItem("重新开始游戏");
		jm_exit=new JMenuItem("退出()");
		jm.add(jm_start);
		jm.add(jm_restart);
		jm.add(jm_exit);
		jm_about=new JMenuItem("关于我们");
		Jmc.add(jm_about);
		jmb.add(jm);
		jmb.add(Jmc);
		
		this.setJMenuBar(jmb);
		
		msp=new MyStartPanel();
        this.setSize(Screen.screen_width, Screen.screen_higth);  //设大一点，下面有用途
        this.add(msp);//作为刚运行时界面
        
		Thread st=new Thread(msp);
		st.start();	
		
		//监听开始按钮
		jm_start.addActionListener(this);
		jm_start.setActionCommand("newgame");
		//监听关闭按钮
		jm_exit.addActionListener(this);
		jm_exit.setActionCommand("exit");
		//监听重新开始方法
		jm_restart.addActionListener(this);
		jm_restart.setActionCommand("restart");
		//监听方法
		jm_about.addActionListener(this);
		jm_about.setActionCommand("aboutme");
		
		this.setVisible(true);
    }
	
	
	//新建音乐方法
	public void startMusic(){		
		Thread musict = null;
		try{
			MP3Player startmusic=new MP3Player("./src/tank/tank.mp3");
			musict=new Thread(startmusic);
			musict.start();
		}catch(Exception e){
			System.out.println("未找到音乐路径");			
			musict.interrupt();
		}		
	}	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//创建战场面板
		if(e.getActionCommand().equals("newgame"))
        {   //创建战场面板
            mp=new MyPanel();
            Thread t = new Thread(mp);
            t.start();
            //先删除旧的开始面板
            this.remove(msp);
            this.addKeyListener(mp);//注册监听
            this.add(mp);
            startMusic();
            //显示，刷新
            this.setVisible(true);
        }
		if(e.getActionCommand().equals("exit")){
			System.exit(0);
		}
		//重新开始游戏
		if(e.getActionCommand().equals("restart")){
			//游戏设置初始化
			Recorder.setEnNum(20);
			Recorder.setAllEnNum(0);
			Recorder.setMyLife(3);
			Recorder.setIsgameover(false);
			Recorder.setVictory(false);
			Recorder.setIsgameovermusic(false);
			this.remove(mp);
			//创建战场面板
            mp=new MyPanel();
            Thread t = new Thread(mp);
            t.start();            
            this.addKeyListener(mp);//注册监听
            this.add(mp);   
            startMusic();
          //显示，刷新
            this.setVisible(true);
		}
		//关于我们
		if(e.getActionCommand().equals("aboutme")){
			JOptionPane.showMessageDialog(null, "作者:eaglet,2018年5月3日");
		}
	}	
}
