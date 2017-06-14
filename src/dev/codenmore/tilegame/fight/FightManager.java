package dev.codenmore.tilegame.fight;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import dev.codenmore.tilegame.Handler;

public class FightManager {

	private Handler handler;
	private ArrayList<Fight> fights;
	private Fight activeFight;
	
	private boolean got2Fight = false;
	
	public FightManager(Handler handler){
		this.handler = handler;
		fights = new ArrayList<Fight>();
		activeFight = null;
	}
	
	public void tick(){
		Iterator<Fight> itf = fights.iterator();
		if(itf.hasNext()){
			Fight f = itf.next();
			f.tick();
			activeFight = f;
			if(f.getState().equals("FINISHED")){
				itf.remove();
			}
		}else{
			got2Fight = false;
		}
	}
	
	public void render(Graphics g){
		if(activeFight != null){
			activeFight.render(g);
		}
	}
	
	public void addFight(Fight f){
		f.setHandler(handler);
		if(fights.isEmpty())
			activeFight = f;
		fights.add(f);
		
	}

	public Fight getActiveFight() {
		return activeFight;
	}

	public void setActiveFight(Fight activeFight) {
		this.activeFight = activeFight;
	}

	public boolean isGot2Fight() {
		return got2Fight;
	}

	public void setGot2Fight(boolean got2fight) {
		this.got2Fight = got2fight;
	}
}
