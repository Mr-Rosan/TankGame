package com.tank.panel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import com.tank.behaviour.Bomb;
import com.tank.behaviour.Shot;
import com.tank.enemy.EnemyTank;
import com.tank.game.Recorder;
import com.tank.model.Tank;
import com.tank.music.MP3Player;
import com.tank.player.Player;
import com.tank.screen.Screen;

//��Ϸ����ͼ����
public class MyPanel extends Panel implements KeyListener, MouseMotionListener,
		Runnable {
	// �������̹��
	Player player = null;
	// �������̹�˼���
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	// ��ʼ������̹�˵�����
	int enSize = 4;
	// ����ը������
	Vector<Bomb> bombs = new Vector<Bomb>();

	// ��������ͼƬ������ͼƬ�������һ��ը��
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;

	public MyPanel() // ���캯��
	{
		// ���̹�˳�ʼ��λ��
		player = new Player(300, 450); // ̹�˵ĳ�ʼλ�ã�x,y��
		this.addKeyListener(this);// ע������¼�
		this.addMouseMotionListener(this);
		// ��ʼ������̹��
		initEnemyTank();

		// ��ʼ��ͼƬ,����ͼƬ�������һ��ը��
		image1 = getToolkit().getDefaultToolkit().getImage(
				Panel.class.getResource("/tank/bomb_1.gif"));
		image2 = getToolkit().getDefaultToolkit().getImage(
				Panel.class.getResource("/tank/bomb_2.gif"));
		image3 = getToolkit().getDefaultToolkit().getImage(
				Panel.class.getResource("/tank/bomb_3.gif"));
		// ��ʼ��ը��
		initBomb();
	}

	// ��ʼ������̹��
	public void initEnemyTank() {
		// ѭ����������̹�˶���ʵ�� �����뵽������
		// ��ʼ�����˵�̹��
		for (int i = 0; i < enSize; i++) {
			EnemyTank et;
			// �õ��˳����ض�λ�ڵ�ͼ�����Ͻ�
			if (i % 2 == 0) {
				// ���Ͻ�
				et = new EnemyTank(0, 0);
			} else {
				// ���Ͻ�
				et = new EnemyTank(Screen.gamemap_width - 30, 0);
			}
			// EnemyTank et=new EnemyTank((i+1)*50, 0);
			et.setColor(0);
			et.setDirect(2);
			ets.add(et);
			// ...
			// �������˵�̹��
			Thread t = new Thread(et);
			t.start();

			// ������̹�����һ���ӵ�
			Shot s = new Shot(et.x + 10, et.y + 30, 2);
			// ���������̹��
			et.ss.add(s);
			Thread t2 = new Thread(s);
			t2.start();

			Recorder.reduceEnNum();
		}
	}

	// ��ʼ��һ��ը��
	public void initBomb() {
		Graphics g = getGraphics();
		for (int i = 0; i < bombs.size(); i++) {
			// ȡ��ը��
			Bomb b = bombs.get(i);
			// ��ը����ͬ
			if (b.life > 5) {// ���л�ͼƬ�����ݻ��Ǿ����ԱȽϺÿ���д�����ģ������Լ���ĥһ��
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			} else if (b.life > 2) {
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			} else {
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}
			// ��b������ֵ��С
			b.lifeDown();
			// ���ը������ֵΪ0���ͰѸ�ը����bombs����ȥ��
			if (b.life == 0) {
				bombs.remove(b);
			}
		}
	}

	Image ImageBuffer = null;
	Graphics GraImage = null;

	@Override
	public void update(Graphics g) { // ����update��������ȡĬ�ϵĵ��ù���
		ImageBuffer = createImage(this.getWidth(), this.getHeight()); // ����ͼ�λ�����
		GraImage = ImageBuffer.getGraphics(); // ��ȡͼ�λ�������ͼ��������
		paint(GraImage); // ��paint�����б�д�Ļ�ͼ���̶�ͼ�λ�������ͼ
		GraImage.dispose(); // �ͷ�ͼ����������Դ
		g.drawImage(ImageBuffer, 0, 0, this); // ��ͼ�λ��������Ƶ���Ļ��
	}

	public void addNewEnemyTank() {
		// ���ͬʱ��ʾ�ĸ�����,���ҵ��˵Ŀ�����������1 �����������̹��
		if (ets.size() < enSize) {
			if (Recorder.getEnNum() > 0) {
				EnemyTank et;
				// �õ��˳����ض�λ�ڵ�ͼ�����Ͻ�
				if (Recorder.getEnNum() % 2 == 0) {
					// ���Ͻ�
					et = new EnemyTank(0, 0);
				} else {
					// ���Ͻ�
					et = new EnemyTank(Screen.gamemap_width - 30, 0);
				}
				// EnemyTank et=new EnemyTank(0, 0);
				et.setColor(0);
				et.setDirect(2);
				ets.add(et);
				// ...
				// �������˵�̹��
				Thread t = new Thread(et);
				t.start();

				// ������̹�����һ���ӵ�
				Shot s = new Shot(et.x + 10, et.y + 30, 2);
				// ���������̹��
				et.ss.add(s);
				Thread t2 = new Thread(s);
				t2.start();
				Recorder.reduceEnNum();
			}
		}
	}

	// ��ͼ����
	@Override
	public void paint(Graphics g) {
		// ��Ϸ��δ����״̬
		if (Recorder.isIsgameover() == false && Recorder.isVictory() == false) {
			paintGame(g);
		}
		// ��Ϸ����״̬��ʤ����ʧ��
		if (Recorder.isIsgameover()) {
			paintGameOver(g);
			// ��Ϸʧ�ܲ������� ����ֻ����һ��
			if (!Recorder.isIsgameovermusic()) {
				gameovermusic();
				Recorder.setIsgameovermusic(true);
			}
		}
		if (Recorder.isVictory()) {
			paintVictory(g);
		}
	}

	// ������Ϸ����
	public void paintGame(Graphics g) {
		// ��ʼ����Ϸ��¼
		this.showInfo(g);
		// ���ݵ���̹�˶�������,������̹��
		addNewEnemyTank();
		// ����JPanel��paint����
		// Graphicd�ǻ�ͼ����Ҫ�࣬�������Ϊһֻ����
		// super.paint(g);//���ø��ຯ����ɳ�ʼ��
		g.fillRect(0, 0, Screen.gamemap_width, Screen.gamemap_higth); // ���ʹ������Ϊ��ɫ
		// �����̹��
		// ����������Ϊ0,���������̹�˴��Ϊfalse,���治���ػ��µ�̹��
		if (Recorder.getMyLife() <= 0) {
			// �����������0,��Ϸ����
			Recorder.setIsgameover(true);
			player = null;
		}
		if (player.isLive) {
			this.drawTank(player.getX(), player.getY(), g, player.getDirect(),
					1);
		} else {
			// ����������� ��������0����
			if (Recorder.getMyLife() > 0) {
				player = new Player(300, 450);
				this.drawTank(player.getX(), player.getY(), g,
						player.getDirect(), 1);
			} else {
				player.setLive(false);
				player = null;
			}
		}

		// ������̹�ˣ��Ӽ�����ѭ����������̹��
		for (EnemyTank et : ets) {
			if (et.isLive()) {
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
				// ���ӵ�
				// �ٻ������˵��ӵ�
				for (int j = 0; j < et.ss.size(); j++) {
					// ȡ���ӵ�
					Shot enemyShot = et.ss.get(j);
					if (enemyShot.isLive) {
						g.draw3DRect(enemyShot.x, enemyShot.y, 1, 1, false);
					} else { // �������̹�������ʹ�Vectorȥ��
						et.ss.remove(enemyShot);
					}
				}
			}
		}
		// ���෢�ӵ�
		for (int i = 0; i < this.player.ss.size(); i++) {
			// ѭ���Ӽ����ж�����ǰ�ӵ��������ӵ��������ӵ�·��
			Shot mys = this.player.ss.get(i);

			// �ж��ӵ��Ƿ��Ҳ�����Ƿ��������˻��߳�����Ļ��Ե
			if (mys.isLive == false) {
				this.player.ss.remove(mys);
			}
			if (mys != null && mys.isLive == true) {
				g.setColor(Color.yellow);
				g.fill3DRect(mys.x, mys.y, 3, 3, false);
			}
		}
		// ����ը��
		for (int i = 0; i < bombs.size(); i++) {
			// ȡ��ը��
			Bomb b = bombs.get(i);
			// ��ը����ͬ
			if (b.life > 5) {// ���л�ͼƬ�����ݻ��Ǿ����ԱȽϺÿ���д�����ģ������Լ���ĥһ��
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			} else if (b.life > 2) {
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			} else {
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}
			// ��b������ֵ��С
			b.lifeDown();
			// ���ը������ֵΪ0���ͰѸ�ը����bombs����ȥ��
			if (b.life == 0) {
				bombs.remove(b);
			}
		}

		// �жϵ���̹�˼�������Ϊ0
		if (ets.size() == 0) {
			Recorder.setVictory(true);
		}

		// g.drawImage(paint, 0, 0, this);
	}

	// ������ʾ��Ϣ
	public void showInfo(Graphics g) {
		// ������ʾ̹����Ϣ̹�ˣ���̹�˲�����ս����
		this.drawTank(80, Screen.gamemap_higth + 10, g, 0, 0);
		g.setColor(Color.BLACK);
		g.drawString(Recorder.getEnNum() + "", 110, Screen.gamemap_higth + 30);
		this.drawTank(130, Screen.gamemap_higth + 10, g, 0, 1);
		g.setColor(Color.BLACK);
		g.drawString(Recorder.getMyLife() + "", 165, Screen.gamemap_higth + 30);

		// ������ҵ��ܳɼ�
		g.setColor(Color.black);
		Font f = new Font("����", Font.BOLD, 20);
		g.setFont(f);
		g.drawString("�ܳɼ�", Screen.gamemap_width, 30);

		this.drawTank(Screen.gamemap_width + 10, 60, g, 0, 0);

		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnNum() + "", Screen.gamemap_width + 40, 80);

	}

	// ����̹�˵ĺ���
	// 1-2����Ϊ̹�����꣬3Ϊ���ʣ�4Ϊ̹�˵ķ���5Ϊ̹�˵ĵ�������
	public void drawTank(int x, int y, Graphics g, int direct, int type) {

		switch (type) // �ж����ͣ��з�̹�˻����Լ���̹�ˣ����Ӷ�������ɫ
		{
		case 0: // �з�̹��
			g.setColor(Color.cyan);
			break;
		case 1: // �ҵ�̹��
			g.setColor(Color.yellow);
			break;
		}

		// �жϷ�����ʱ������һ�ַ���
		switch (direct) {
		// ����
		case 0:
			// �����ҵ�̹��
			// 1.������ߵľ���
			g.fill3DRect(x, y, 5, 30, false);
			// //2.�����ұ߾���
			g.fill3DRect(x + 15, y, 5, 30, false);
			// //3.�����м����
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			// //4.����Բ��
			g.fillOval(x + 5, y + 10, 10, 10);
			// //5.��ֱ��
			g.drawLine(x + 10, y + 15, x + 10, y);
			break;
		case 1:// ��Ͳ����
				// �����������
			g.fill3DRect(x, y, 30, 5, false);
			// //2.�����ұ߾���
			g.fill3DRect(x, y + 15, 30, 5, false);
			// //3.�����м����
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			// //4.����Բ��
			g.fillOval(x + 10, y + 5, 10, 10);
			// //5.��ֱ��
			g.drawLine(x + 15, y + 10, x + 30, y + 10);
			break;
		case 2:// ��Ͳ����
				// �����������
			g.fill3DRect(x, y, 5, 30, false);
			// //2.�����ұ߾���
			g.fill3DRect(x + 15, y, 5, 30, false);
			// //3.�����м����
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			// //4.����Բ��
			g.fillOval(x + 5, y + 10, 10, 10);
			// //5.��ֱ��
			g.drawLine(x + 10, y + 15, x + 10, y + 30);
			break;

		case 3:// ��Ͳ����
				// �����������
			g.fill3DRect(x, y, 30, 5, false);
			// //2.�����ұ߾���
			g.fill3DRect(x, y + 15, 30, 5, false);
			// //3.�����м����
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			// //4.����Բ��
			g.fillOval(x + 10, y + 5, 10, 10);
			// //5.��ֱ��
			g.drawLine(x + 15, y + 10, x, y + 10);
			break;
		}
	}
	
	// �����û��¼�
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)// ����
		{
			this.player.setDirect(0);
			this.player.moveUp();			
			player_movingmusic();			
		} else if (e.getKeyCode() == KeyEvent.VK_D
				|| e.getKeyCode() == KeyEvent.VK_RIGHT)// ����
		{
			this.player.setDirect(1);
			this.player.moveRight();
			player_movingmusic();
		} else if (e.getKeyCode() == KeyEvent.VK_S
				|| e.getKeyCode() == KeyEvent.VK_DOWN)// ����
		{
			this.player.setDirect(2);
			this.player.moveDown();
			player_movingmusic();
		} else if (e.getKeyCode() == KeyEvent.VK_A
				|| e.getKeyCode() == KeyEvent.VK_LEFT)// ����
		{
			this.player.setDirect(3);
			this.player.moveLeft();
			player_movingmusic();
		}
		
		

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			// ����巢�ӵ�
			if (this.player.ss.size() <= 4) {
				// ��������
				firemusic();
				this.player.shotEnemy();
			}
		}

		this.repaint();// ����repaint()���������ػ�ҳ��
	}

	// ���˵��ӵ��Ƿ������
	public void hitMe() {
		// ȡ��ÿһ�����˵�̹��
		for (int i = 0; i < this.ets.size(); i++) {
			// ȡ��̹��
			EnemyTank et = ets.get(i);

			// ȡ��ÿһ���ӵ�
			for (int j = 0; j < et.ss.size(); j++) {
				// ȡ���ӵ�
				Shot enemyShot = et.ss.get(j);

				// ������� �����������1
				if (this.hitTank(enemyShot, player)) {
					Recorder.reducePlayerLife();
				}
			}

		}
	}

	// �ж��ҵ��ӵ��Ƿ���е��˵�̹��
	public void hitEnemyTank() {
		for (int i = 0; i < player.ss.size(); i++) {
			// ȡ���ӵ�
			Shot myShot = player.ss.get(i);
			// �ж��ӵ��Ƿ���Ч
			if (myShot.isLive) {
				// ȡ��ÿ��̹�ˣ������ж�
				for (int j = 0; j < ets.size(); j++) {
					// ȡ��̹��
					EnemyTank et = ets.get(j);

					if (et.isLive()) {
						// ������е���,������������
						if (this.hitTank(myShot, et)) {
							Recorder.addEnNumRec();
							// ÿ����һ������̹��,��ӵ���̹�˶������Ƴ�,���ڸ�������µ���̹��
							ets.remove(et);
						}
					}
				}
			}
		}
	}

	// дһ������ר���ж��ӵ��Ƿ����е���̹��
	// �޸ķ���ֵ,����̹�˷���true
	public boolean hitTank(Shot s, Tank et) {
		// �жϸ�̹�˵ķ���
		switch (et.direct) {
		// �������̹�˷������ϻ�����
		case 0:
		case 2:
			// �����ӵ�����͵���̹������ȷ���Ƿ��ӵ�������̹�˷�Χ�����Բ��Կ�����
			// ̹�˳�30��20
			if (s.x > et.x && s.x < et.x + 20 && s.y > et.y && s.y < et.y + 30) {
				// ����
				// �ӵ�����
				s.isLive = false;
				// ̹������
				et.setLive(false);
				// ����һ��ը��������Vector
				Bomb b = new Bomb(et.x, et.y);
				// ����Vector
				bombs.add(b);
				bombmusic();
				return true;
			} else {
				return false;
			}
			// ���������
		case 1:
		case 3:
			if (s.x > et.x && s.x < (et.x + 30) && s.y > et.y
					&& s.y < (et.y + 20)) {
				// ����
				// �ӵ�����
				s.isLive = false;
				// ̹������
				et.setLive(false);
				// ����һ��ը��������Vector
				Bomb b = new Bomb(et.x, et.y);
				// ����Vector
				bombs.add(b);
				bombmusic();
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	// �����̹�˶�����һ���߳���ִ��
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// ÿ��50�������ػ�
		while (true) {
			try {
				Thread.sleep(50); // ������50����ȽϺ���
			} catch (Exception e) {
				// TODO: handle exception
			}

			// �ж��Ƿ���� ��ҹ�������
			this.hitEnemyTank();

			// ���Թ������
			this.hitMe();

			// �ػ�
			this.repaint();
		}
	}

	// ����Ϸʤ������
	public void paintVictory(Graphics g) {
		// ����Ҫ���Ƶķ�Χ ��Ļ����ɫ
		g.fillRect(0, 0, Screen.gamemap_width, Screen.gamemap_higth);
		// ȡģ,����ػ�
		g.setColor(Color.white);
		// ������Ϣ������
		Font myFont = new Font("����", Font.BOLD, 50);
		g.setFont(myFont);
		g.drawString("��Ϸʤ��", Screen.gamemap_width / 2 - 100,
				Screen.gamemap_higth / 2 - 25);
	}

	// ����Ϸʧ�ܽ���
	public void paintGameOver(Graphics g) {
		// ����Ҫ���Ƶķ�Χ ��Ļ����ɫ
		g.fillRect(0, 0, Screen.gamemap_width, Screen.gamemap_higth);
		// ȡģ,����ػ�
		g.setColor(Color.white);
		// ������Ϣ������
		Font myFont = new Font("����", Font.BOLD, 50);
		g.setFont(myFont);
		g.drawString("GAME OVER", Screen.gamemap_width / 2 - 100,
				Screen.gamemap_higth / 2 - 25);
	}

	// ��Ϸʧ��ʱ�������
	public void gameovermusic() {
		Thread musict = null;
		try {
			MP3Player startmusic = new MP3Player("./src/tank/gameover.mp3");
			musict = new Thread(startmusic);
			musict.start();
		} catch (Exception e) {
			System.out.println("δ�ҵ�����·��");
			musict.interrupt();
		}
	}

	// ��ҿ�������
	public void firemusic() {
		Thread musict = null;
		try {
			MP3Player startmusic = new MP3Player("./src/tank/player_fire.mp3");
			musict = new Thread(startmusic);
			musict.start();
		} catch (Exception e) {
			System.out.println("δ�ҵ�����·��");
			musict.interrupt();
		}
	}
	
	
	// ����ƶ�����
	public void player_movingmusic() {	
		//���������������е�ʱ����ֹ�ٲ���
	    Thread musicmovingt = null;
		try {
			MP3Player startmusic = new MP3Player("./src/tank/player_moving.mp3");			
			musicmovingt = new Thread(startmusic);
			musicmovingt.start();
		} catch (Exception e) {
			System.out.println("δ�ҵ�����·��");
			musicmovingt.interrupt();
		}
	}

	// ��ը��Ч
	public void bombmusic() {
		Thread musict = null;
		try {
			MP3Player startmusic = new MP3Player("./src/tank/bomb.mp3");
			musict = new Thread(startmusic);
			musict.start();
		} catch (Exception e) {
			System.out.println("δ�ҵ�����·��");
			musict.interrupt();
		}
	}

}
