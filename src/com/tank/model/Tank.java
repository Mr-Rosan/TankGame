package com.tank.model;

public class Tank {
	//坦克坐标x,y
	public int x=0;
	public int y=0;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	//坦克类构造函数
	public Tank(int x,int y)
    {
        this.x=x;
        this.y=y;
    }
	
	//坦克移动速度
	protected int speed=2;
	
	 //0表示上，1表示右，2表示下，3表示左
	public int direct=0;

	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	
	int color;

	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	public boolean isLive=true;

	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	
	
}
