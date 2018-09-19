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
	//���忪ʼ������
	JMenuBar jmb=null;
	//���忪ʼ�˵���ť
	JMenu jm=null;
	JMenu Jmc=null;
	//���尴ť������
	JMenuItem jm_start=null;
	JMenuItem jm_restart=null;
	JMenuItem jm_exit=null;
	//��������
	JMenuItem jm_about=null;
	
	
	//������Ϸ��ʼ��ͼ��
	MyStartPanel msp=new MyStartPanel();
	//��Ϸ��ͼ��
	MyPanel mp=null;	
	
	public TankGame()
    {  
		//ʵ��������˵�
		jmb=new JMenuBar();
		jm=new JMenu("��Ϸ(G)");
		Jmc=new JMenu("����");
		//���ÿ�ݷ�ʽ
		jm.setMnemonic('G');
		jm_start=new JMenuItem("��ʼ����Ϸ(N)");
		jm_restart=new JMenuItem("���¿�ʼ��Ϸ");
		jm_exit=new JMenuItem("�˳�()");
		jm.add(jm_start);
		jm.add(jm_restart);
		jm.add(jm_exit);
		jm_about=new JMenuItem("��������");
		Jmc.add(jm_about);
		jmb.add(jm);
		jmb.add(Jmc);
		
		this.setJMenuBar(jmb);
		
		msp=new MyStartPanel();
        this.setSize(Screen.screen_width, Screen.screen_higth);  //���һ�㣬��������;
        this.add(msp);//��Ϊ������ʱ����
        
		Thread st=new Thread(msp);
		st.start();	
		
		//������ʼ��ť
		jm_start.addActionListener(this);
		jm_start.setActionCommand("newgame");
		//�����رհ�ť
		jm_exit.addActionListener(this);
		jm_exit.setActionCommand("exit");
		//�������¿�ʼ����
		jm_restart.addActionListener(this);
		jm_restart.setActionCommand("restart");
		//��������
		jm_about.addActionListener(this);
		jm_about.setActionCommand("aboutme");
		
		this.setVisible(true);
    }
	
	
	//�½����ַ���
	public void startMusic(){		
		Thread musict = null;
		try{
			MP3Player startmusic=new MP3Player("./src/tank/tank.mp3");
			musict=new Thread(startmusic);
			musict.start();
		}catch(Exception e){
			System.out.println("δ�ҵ�����·��");			
			musict.interrupt();
		}		
	}	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//����ս�����
		if(e.getActionCommand().equals("newgame"))
        {   //����ս�����
            mp=new MyPanel();
            Thread t = new Thread(mp);
            t.start();
            //��ɾ���ɵĿ�ʼ���
            this.remove(msp);
            this.addKeyListener(mp);//ע�����
            this.add(mp);
            startMusic();
            //��ʾ��ˢ��
            this.setVisible(true);
        }
		if(e.getActionCommand().equals("exit")){
			System.exit(0);
		}
		//���¿�ʼ��Ϸ
		if(e.getActionCommand().equals("restart")){
			//��Ϸ���ó�ʼ��
			Recorder.setEnNum(20);
			Recorder.setAllEnNum(0);
			Recorder.setMyLife(3);
			Recorder.setIsgameover(false);
			Recorder.setVictory(false);
			Recorder.setIsgameovermusic(false);
			this.remove(mp);
			//����ս�����
            mp=new MyPanel();
            Thread t = new Thread(mp);
            t.start();            
            this.addKeyListener(mp);//ע�����
            this.add(mp);   
            startMusic();
          //��ʾ��ˢ��
            this.setVisible(true);
		}
		//��������
		if(e.getActionCommand().equals("aboutme")){
			JOptionPane.showMessageDialog(null, "����:eaglet,2018��5��3��");
		}
	}	
}
