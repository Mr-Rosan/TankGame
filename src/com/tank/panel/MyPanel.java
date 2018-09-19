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

//游戏界面图形类
public class MyPanel extends Panel implements KeyListener, MouseMotionListener,
		Runnable {
	// 定义玩家坦克
	Player player = null;
	// 定义敌人坦克集合
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	// 初始化敌人坦克的数量
	int enSize = 4;
	// 定义炸弹集合
	Vector<Bomb> bombs = new Vector<Bomb>();

	// 定义三张图片，三张图片才能组成一颗炸弹
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;

	public MyPanel() // 构造函数
	{
		// 玩家坦克初始化位置
		player = new Player(300, 450); // 坦克的初始位置（x,y）
		this.addKeyListener(this);// 注册监听事件
		this.addMouseMotionListener(this);
		// 初始化敌人坦克
		initEnemyTank();

		// 初始化图片,三张图片才能组成一颗炸弹
		image1 = getToolkit().getDefaultToolkit().getImage(
				Panel.class.getResource("/tank/bomb_1.gif"));
		image2 = getToolkit().getDefaultToolkit().getImage(
				Panel.class.getResource("/tank/bomb_2.gif"));
		image3 = getToolkit().getDefaultToolkit().getImage(
				Panel.class.getResource("/tank/bomb_3.gif"));
		// 初始化炸弹
		initBomb();
	}

	// 初始化敌人坦克
	public void initEnemyTank() {
		// 循环创建敌人坦克对象实例 并加入到集合中
		// 初始化敌人的坦克
		for (int i = 0; i < enSize; i++) {
			EnemyTank et;
			// 让敌人出生地定位在地图左右上角
			if (i % 2 == 0) {
				// 左上角
				et = new EnemyTank(0, 0);
			} else {
				// 右上角
				et = new EnemyTank(Screen.gamemap_width - 30, 0);
			}
			// EnemyTank et=new EnemyTank((i+1)*50, 0);
			et.setColor(0);
			et.setDirect(2);
			ets.add(et);
			// ...
			// 启动敌人的坦克
			Thread t = new Thread(et);
			t.start();

			// 给敌人坦克添加一颗子弹
			Shot s = new Shot(et.x + 10, et.y + 30, 2);
			// 加入给敌人坦克
			et.ss.add(s);
			Thread t2 = new Thread(s);
			t2.start();

			Recorder.reduceEnNum();
		}
	}

	// 初始化一颗炸弹
	public void initBomb() {
		Graphics g = getGraphics();
		for (int i = 0; i < bombs.size(); i++) {
			// 取出炸弹
			Bomb b = bombs.get(i);
			// 给炸弹不同
			if (b.life > 5) {// 下列画图片的数据还是经测试比较好看才写出来的，可以自己琢磨一下
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			} else if (b.life > 2) {
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			} else {
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}
			// 让b的生命值减小
			b.lifeDown();
			// 如果炸弹生命值为0，就把该炸弹从bombs向量去掉
			if (b.life == 0) {
				bombs.remove(b);
			}
		}
	}

	Image ImageBuffer = null;
	Graphics GraImage = null;

	@Override
	public void update(Graphics g) { // 覆盖update方法，截取默认的调用过程
		ImageBuffer = createImage(this.getWidth(), this.getHeight()); // 创建图形缓冲区
		GraImage = ImageBuffer.getGraphics(); // 获取图形缓冲区的图形上下文
		paint(GraImage); // 用paint方法中编写的绘图过程对图形缓冲区绘图
		GraImage.dispose(); // 释放图形上下文资源
		g.drawImage(ImageBuffer, 0, 0, this); // 将图形缓冲区绘制到屏幕上
	}

	public void addNewEnemyTank() {
		// 最多同时显示四个敌人,并且敌人的可用总数大于1 往队列中添加坦克
		if (ets.size() < enSize) {
			if (Recorder.getEnNum() > 0) {
				EnemyTank et;
				// 让敌人出生地定位在地图左右上角
				if (Recorder.getEnNum() % 2 == 0) {
					// 左上角
					et = new EnemyTank(0, 0);
				} else {
					// 右上角
					et = new EnemyTank(Screen.gamemap_width - 30, 0);
				}
				// EnemyTank et=new EnemyTank(0, 0);
				et.setColor(0);
				et.setDirect(2);
				ets.add(et);
				// ...
				// 启动敌人的坦克
				Thread t = new Thread(et);
				t.start();

				// 给敌人坦克添加一颗子弹
				Shot s = new Shot(et.x + 10, et.y + 30, 2);
				// 加入给敌人坦克
				et.ss.add(s);
				Thread t2 = new Thread(s);
				t2.start();
				Recorder.reduceEnNum();
			}
		}
	}

	// 绘图方法
	@Override
	public void paint(Graphics g) {
		// 游戏是未结束状态
		if (Recorder.isIsgameover() == false && Recorder.isVictory() == false) {
			paintGame(g);
		}
		// 游戏结束状态分胜利和失败
		if (Recorder.isIsgameover()) {
			paintGameOver(g);
			// 游戏失败播放音乐 音乐只播放一次
			if (!Recorder.isIsgameovermusic()) {
				gameovermusic();
				Recorder.setIsgameovermusic(true);
			}
		}
		if (Recorder.isVictory()) {
			paintVictory(g);
		}
	}

	// 绘制游戏界面
	public void paintGame(Graphics g) {
		// 初始化游戏记录
		this.showInfo(g);
		// 根据敌人坦克队列数量,增加新坦克
		addNewEnemyTank();
		// 覆盖JPanel的paint方法
		// Graphicd是绘图的重要类，可以理解为一只画笔
		// super.paint(g);//调用父类函数完成初始化
		g.fillRect(0, 0, Screen.gamemap_width, Screen.gamemap_higth); // 填充使背景变为黑色
		// 画玩家坦克
		// 如果玩家生命为0,则设置玩家坦克存活为false,后面不在重绘新的坦克
		if (Recorder.getMyLife() <= 0) {
			// 玩家生命等于0,游戏结束
			Recorder.setIsgameover(true);
			player = null;
		}
		if (player.isLive) {
			this.drawTank(player.getX(), player.getY(), g, player.getDirect(),
					1);
		} else {
			// 玩家死亡复活 生命大于0复活
			if (Recorder.getMyLife() > 0) {
				player = new Player(300, 450);
				this.drawTank(player.getX(), player.getY(), g,
						player.getDirect(), 1);
			} else {
				player.setLive(false);
				player = null;
			}
		}

		// 画敌人坦克，从集合中循环画出敌人坦克
		for (EnemyTank et : ets) {
			if (et.isLive()) {
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
				// 画子弹
				// 再画出敌人的子弹
				for (int j = 0; j < et.ss.size(); j++) {
					// 取出子弹
					Shot enemyShot = et.ss.get(j);
					if (enemyShot.isLive) {
						g.draw3DRect(enemyShot.x, enemyShot.y, 1, 1, false);
					} else { // 如果敌人坦克死亡就从Vector去掉
						et.ss.remove(enemyShot);
					}
				}
			}
		}
		// 画多发子弹
		for (int i = 0; i < this.player.ss.size(); i++) {
			// 循环从集合中读出当前子弹，画出子弹并运行子弹路径
			Shot mys = this.player.ss.get(i);

			// 判断子弹是否存活，也就是是否触碰到敌人或者超出屏幕边缘
			if (mys.isLive == false) {
				this.player.ss.remove(mys);
			}
			if (mys != null && mys.isLive == true) {
				g.setColor(Color.yellow);
				g.fill3DRect(mys.x, mys.y, 3, 3, false);
			}
		}
		// 画出炸弹
		for (int i = 0; i < bombs.size(); i++) {
			// 取出炸弹
			Bomb b = bombs.get(i);
			// 给炸弹不同
			if (b.life > 5) {// 下列画图片的数据还是经测试比较好看才写出来的，可以自己琢磨一下
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			} else if (b.life > 2) {
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			} else {
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}
			// 让b的生命值减小
			b.lifeDown();
			// 如果炸弹生命值为0，就把该炸弹从bombs向量去掉
			if (b.life == 0) {
				bombs.remove(b);
			}
		}

		// 判断敌人坦克集合数量为0
		if (ets.size() == 0) {
			Recorder.setVictory(true);
		}

		// g.drawImage(paint, 0, 0, this);
	}

	// 画出提示信息
	public void showInfo(Graphics g) {
		// 画出提示坦克信息坦克（该坦克不参与战斗）
		this.drawTank(80, Screen.gamemap_higth + 10, g, 0, 0);
		g.setColor(Color.BLACK);
		g.drawString(Recorder.getEnNum() + "", 110, Screen.gamemap_higth + 30);
		this.drawTank(130, Screen.gamemap_higth + 10, g, 0, 1);
		g.setColor(Color.BLACK);
		g.drawString(Recorder.getMyLife() + "", 165, Screen.gamemap_higth + 30);

		// 画出玩家的总成绩
		g.setColor(Color.black);
		Font f = new Font("宋体", Font.BOLD, 20);
		g.setFont(f);
		g.drawString("总成绩", Screen.gamemap_width, 30);

		this.drawTank(Screen.gamemap_width + 10, 60, g, 0, 0);

		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnNum() + "", Screen.gamemap_width + 40, 80);

	}

	// 画出坦克的函数
	// 1-2参数为坦克坐标，3为画笔，4为坦克的方向，5为坦克的敌我类型
	public void drawTank(int x, int y, Graphics g, int direct, int type) {

		switch (type) // 判断类型（敌方坦克还是自己的坦克），从而设置颜色
		{
		case 0: // 敌方坦克
			g.setColor(Color.cyan);
			break;
		case 1: // 我的坦克
			g.setColor(Color.yellow);
			break;
		}

		// 判断方向（暂时先设置一种方向）
		switch (direct) {
		// 向上
		case 0:
			// 画出我的坦克
			// 1.画出左边的矩形
			g.fill3DRect(x, y, 5, 30, false);
			// //2.画出右边矩形
			g.fill3DRect(x + 15, y, 5, 30, false);
			// //3.画出中间矩形
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			// //4.画出圆形
			g.fillOval(x + 5, y + 10, 10, 10);
			// //5.画直线
			g.drawLine(x + 10, y + 15, x + 10, y);
			break;
		case 1:// 炮筒向右
				// 画出上面矩形
			g.fill3DRect(x, y, 30, 5, false);
			// //2.画出右边矩形
			g.fill3DRect(x, y + 15, 30, 5, false);
			// //3.画出中间矩形
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			// //4.画出圆形
			g.fillOval(x + 10, y + 5, 10, 10);
			// //5.画直线
			g.drawLine(x + 15, y + 10, x + 30, y + 10);
			break;
		case 2:// 炮筒向下
				// 画出上面矩形
			g.fill3DRect(x, y, 5, 30, false);
			// //2.画出右边矩形
			g.fill3DRect(x + 15, y, 5, 30, false);
			// //3.画出中间矩形
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			// //4.画出圆形
			g.fillOval(x + 5, y + 10, 10, 10);
			// //5.画直线
			g.drawLine(x + 10, y + 15, x + 10, y + 30);
			break;

		case 3:// 炮筒向左
				// 画出上面矩形
			g.fill3DRect(x, y, 30, 5, false);
			// //2.画出右边矩形
			g.fill3DRect(x, y + 15, 30, 5, false);
			// //3.画出中间矩形
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			// //4.画出圆形
			g.fillOval(x + 10, y + 5, 10, 10);
			// //5.画直线
			g.drawLine(x + 15, y + 10, x, y + 10);
			break;
		}
	}
	
	// 键盘敲击事件
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)// 向上
		{
			this.player.setDirect(0);
			this.player.moveUp();			
			player_movingmusic();			
		} else if (e.getKeyCode() == KeyEvent.VK_D
				|| e.getKeyCode() == KeyEvent.VK_RIGHT)// 向右
		{
			this.player.setDirect(1);
			this.player.moveRight();
			player_movingmusic();
		} else if (e.getKeyCode() == KeyEvent.VK_S
				|| e.getKeyCode() == KeyEvent.VK_DOWN)// 向下
		{
			this.player.setDirect(2);
			this.player.moveDown();
			player_movingmusic();
		} else if (e.getKeyCode() == KeyEvent.VK_A
				|| e.getKeyCode() == KeyEvent.VK_LEFT)// 向左
		{
			this.player.setDirect(3);
			this.player.moveLeft();
			player_movingmusic();
		}
		
		

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			// 最多五发子弹
			if (this.player.ss.size() <= 4) {
				// 开炮声音
				firemusic();
				this.player.shotEnemy();
			}
		}

		this.repaint();// 调用repaint()函数，来重绘页面
	}

	// 敌人的子弹是否击中我
	public void hitMe() {
		// 取出每一个敌人的坦克
		for (int i = 0; i < this.ets.size(); i++) {
			// 取出坦克
			EnemyTank et = ets.get(i);

			// 取出每一颗子弹
			for (int j = 0; j < et.ss.size(); j++) {
				// 取出子弹
				Shot enemyShot = et.ss.get(j);

				// 击中玩家 玩家生命减少1
				if (this.hitTank(enemyShot, player)) {
					Recorder.reducePlayerLife();
				}
			}

		}
	}

	// 判断我的子弹是否击中敌人的坦克
	public void hitEnemyTank() {
		for (int i = 0; i < player.ss.size(); i++) {
			// 取出子弹
			Shot myShot = player.ss.get(i);
			// 判断子弹是否有效
			if (myShot.isLive) {
				// 取出每个坦克，与它判断
				for (int j = 0; j < ets.size(); j++) {
					// 取出坦克
					EnemyTank et = ets.get(j);

					if (et.isLive()) {
						// 如果击中敌人,敌人总数减少
						if (this.hitTank(myShot, et)) {
							Recorder.addEnNumRec();
							// 每击毁一辆敌人坦克,则从敌人坦克队列中移除,便于更新添加新敌人坦克
							ets.remove(et);
						}
					}
				}
			}
		}
	}

	// 写一个函数专门判断子弹是否会击中敌人坦克
	// 修改返回值,击中坦克返回true
	public boolean hitTank(Shot s, Tank et) {
		// 判断该坦克的方向
		switch (et.direct) {
		// 如果敌人坦克方向是上或是下
		case 0:
		case 2:
			// 根据子弹坐标和敌人坦克坐标确定是否子弹到敌人坦克范围（可以测试看看）
			// 坦克长30宽20
			if (s.x > et.x && s.x < et.x + 20 && s.y > et.y && s.y < et.y + 30) {
				// 击中
				// 子弹死亡
				s.isLive = false;
				// 坦克死亡
				et.setLive(false);
				// 创建一颗炸弹，放入Vector
				Bomb b = new Bomb(et.x, et.y);
				// 放入Vector
				bombs.add(b);
				bombmusic();
				return true;
			} else {
				return false;
			}
			// 向左或向右
		case 1:
		case 3:
			if (s.x > et.x && s.x < (et.x + 30) && s.y > et.y
					&& s.y < (et.y + 20)) {
				// 击中
				// 子弹死亡
				s.isLive = false;
				// 坦克死亡
				et.setLive(false);
				// 创建一颗炸弹，放入Vector
				Bomb b = new Bomb(et.x, et.y);
				// 放入Vector
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

	// 将玩家坦克定义在一个线程中执行
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// 每隔50毫秒来重绘
		while (true) {
			try {
				Thread.sleep(50); // 经测试50毫秒比较合理
			} catch (Exception e) {
				// TODO: handle exception
			}

			// 判断是否击中 玩家攻击电脑
			this.hitEnemyTank();

			// 电脑攻击玩家
			this.hitMe();

			// 重绘
			this.repaint();
		}
	}

	// 画游戏胜利界面
	public void paintVictory(Graphics g) {
		// 画笔要绘制的范围 屏幕画黑色
		g.fillRect(0, 0, Screen.gamemap_width, Screen.gamemap_higth);
		// 取模,间隔重绘
		g.setColor(Color.white);
		// 开关信息的字体
		Font myFont = new Font("宋体", Font.BOLD, 50);
		g.setFont(myFont);
		g.drawString("游戏胜利", Screen.gamemap_width / 2 - 100,
				Screen.gamemap_higth / 2 - 25);
	}

	// 画游戏失败界面
	public void paintGameOver(Graphics g) {
		// 画笔要绘制的范围 屏幕画黑色
		g.fillRect(0, 0, Screen.gamemap_width, Screen.gamemap_higth);
		// 取模,间隔重绘
		g.setColor(Color.white);
		// 开关信息的字体
		Font myFont = new Font("宋体", Font.BOLD, 50);
		g.setFont(myFont);
		g.drawString("GAME OVER", Screen.gamemap_width / 2 - 100,
				Screen.gamemap_higth / 2 - 25);
	}

	// 游戏失败时候的音乐
	public void gameovermusic() {
		Thread musict = null;
		try {
			MP3Player startmusic = new MP3Player("./src/tank/gameover.mp3");
			musict = new Thread(startmusic);
			musict.start();
		} catch (Exception e) {
			System.out.println("未找到音乐路径");
			musict.interrupt();
		}
	}

	// 玩家开炮声音
	public void firemusic() {
		Thread musict = null;
		try {
			MP3Player startmusic = new MP3Player("./src/tank/player_fire.mp3");
			musict = new Thread(startmusic);
			musict.start();
		} catch (Exception e) {
			System.out.println("未找到音乐路径");
			musict.interrupt();
		}
	}
	
	
	// 玩家移动声音
	public void player_movingmusic() {	
		//放在外面连续运行的时候中止再播放
	    Thread musicmovingt = null;
		try {
			MP3Player startmusic = new MP3Player("./src/tank/player_moving.mp3");			
			musicmovingt = new Thread(startmusic);
			musicmovingt.start();
		} catch (Exception e) {
			System.out.println("未找到音乐路径");
			musicmovingt.interrupt();
		}
	}

	// 爆炸音效
	public void bombmusic() {
		Thread musict = null;
		try {
			MP3Player startmusic = new MP3Player("./src/tank/bomb.mp3");
			musict = new Thread(startmusic);
			musict.start();
		} catch (Exception e) {
			System.out.println("未找到音乐路径");
			musict.interrupt();
		}
	}

}
