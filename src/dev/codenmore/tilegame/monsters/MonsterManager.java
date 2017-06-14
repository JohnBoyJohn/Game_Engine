package dev.codenmore.tilegame.monsters;

import java.util.ArrayList;
import java.util.Random;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.gfx.Animation;
import dev.codenmore.tilegame.gfx.Assets;

public class MonsterManager {

	private Handler handler;
	
	public MonsterManager(Handler handler){
		this.handler = handler;
	}
	
	public ArrayList<Monster> initRandomMonsters(){
		ArrayList<Monster> res = new ArrayList<Monster>();
		
		Random rand = new Random();
		int num = rand.nextInt(2);
		
		for(int i = 0; i <= num; i++){
			res.add(getRandomMonster());
		}
		
		return res;
	}
	
	private Monster getRandomMonster(){
		Random rand = new Random();
		int num = rand.nextInt(2);
		
		if(num == 0){
			return new Monster(handler, 0, 0, Assets.slime, new Animation(250, Assets.slime_attack), new Animation(250, Assets.slime_die));
		}else if(num == 1){
			return new Monster(handler, 1, 0, Assets.dragon, new Animation(250, Assets.dragon_attack), new Animation(250, Assets.dragon_die));
		}else{
			return new Monster(handler, 2, 0, Assets.golem, new Animation(250, Assets.golem_attack), new Animation(250, Assets.golem_die));
		}
	}
}
