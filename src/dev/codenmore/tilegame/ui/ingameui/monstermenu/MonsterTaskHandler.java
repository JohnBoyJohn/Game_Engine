package dev.codenmore.tilegame.ui.ingameui.monstermenu;

import java.util.ArrayList;
import java.util.Collections;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.monsters.Monster;

public class MonsterTaskHandler {
	
	private static Handler handler;
	
	public MonsterTaskHandler(Handler handler){
		MonsterTaskHandler.handler = handler;
	}
	
	public void arrangeMonsters(int m1, int m2){
		ArrayList<Monster> monsters = handler.getWorld().getEntityManager().getPlayer().getMonsters();
		System.out.println("Change monsters " + monsters.get(m1).getName() + " with " + monsters.get(m2).getName());
		Collections.swap(monsters, m1, m2);
	}
	
	public static void deleteMonster(int monster){
		handler.getWorld().getEntityManager().getPlayer().getMonsters().remove(monster);
	}
}
