package dev.codenmore.tilegame.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;

public class EnterFightAnimation implements Settings{
	
	private Handler handler;
	
	private boolean animation = false;
	private boolean animationEnd = false;
	private int animSpeed = 150, anim_counter;
	private long lastTime, timer;
	private int renderMethod;
	private boolean drawTiles[][] = new boolean[(int) (HEIGHT/TILESIZE) + 2][(int) (WIDTH/TILESIZE) + 1];

	public EnterFightAnimation(Handler handler){
		this.handler = handler;
	}
	
	public void tick(){		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			System.out.println("Exit Fight Animation");
			animationEnd = true;
		}
		
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > animSpeed){
			timer = 0;
			if(renderMethod == 0){
				tickRenderMethod_1();
			}else{
				tickRenderMethod_2();
			}
			anim_counter++;
		}
		
		if(hasAnimationEnded()){
			animationEnd = true;
		}
	}

	public void render(Graphics g){
		g.setColor(Color.BLACK);
		for(int i = 0; i < drawTiles.length; i++){
			for(int j = 0; j < drawTiles[i].length; j++){
				if(drawTiles[i][j]){
					g.fillRect(j * TILESIZE, i * TILESIZE, TILESIZE, TILESIZE);
				}
			}
		}
	}
	
	private void tickRenderMethod_1(){
		for(int i = 0; i < drawTiles.length; i++){
			drawTiles[i][anim_counter] = true;
		}
	}
	
	private void tickRenderMethod_2(){
		for(int i = 0; i < drawTiles[0].length; i++){
			drawTiles[anim_counter][i] = true;
		}
	}
	
	private boolean hasAnimationEnded() {
		boolean res = true;
		for(int i = 0; i < drawTiles.length; i++){
			for(int j = 0; j < drawTiles[i].length; j++){
				if(!drawTiles[i][j]){
					res = false;
				}
			}
		}
		return res;
	}
	
	public void initAnimation(){
		animation = true;
		animationEnd = false;
		randomRenderMethod();
		anim_counter = 0;

		timer = 0;
		lastTime = System.currentTimeMillis();
		for(int i = 0; i < drawTiles.length; i++){
			for(int j = 0; j < drawTiles[i].length; j++){
				drawTiles[i][j] = false;
			}
		}
	}
	
	private void randomRenderMethod(){
		Random rand = new Random();
		renderMethod = rand.nextInt(2);
	}
	
	
	//GETTERS & SETTERS
	public boolean getAnimation(){
		return animation;
	}
	public boolean getAnimationEnd(){
		return animationEnd;
	}
	
	public void setAnimation(boolean var){
		animation = var;
	}
	public void setAnimationEnd(boolean var){
		animationEnd = var;
	}
}
