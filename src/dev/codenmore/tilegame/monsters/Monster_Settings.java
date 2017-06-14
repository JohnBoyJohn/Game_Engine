package dev.codenmore.tilegame.monsters;

public interface Monster_Settings {
	public static final int DEFAULT_ATT = 3;
	public static final int DEFAULT_DEF = 2;
	public static final int DEFAULT_LUK = 1;
	public static final int DEFAULT_AGI = 1;
	public static final int DEFAULT_HP = 10;
	public static final int DEFAULT_MP = 0;

	//Monster Classes and Names
	public static final String[] SLIMES = {
			"Slime", "DragonSlime", "BeastSlime" 
	};
	public static final String[] DRAGONS = {
			"Dragon", "FireDragon", "IceDragon" 
	};
	public static final String[] MATERIALS = {
			"Golem", "FireGolem", "IronGolem" 
	};
	
	public static final String[][] MONSTERS = {
		SLIMES, DRAGONS, MATERIALS
	};
	
	public static final String[] MONSTERCLASSNAMES = {
		"Slimes", "Dragons", "Materials"
	};
	
	public static final String[] MONSTERTASKS = {
		"arrange", "give item", "use skill", "let free"
	};
}
