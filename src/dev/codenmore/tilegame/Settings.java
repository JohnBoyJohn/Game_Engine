package dev.codenmore.tilegame;

public interface Settings {
	//game width/height and tilesize
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int TILESIZE = 64;
	
	//player and enemies
	public static final int DEFAULT_HEALTH = 10;
	public static final int DEFAULT_ATTACK = 5;
	
	public static final int MAX_LEVELS = 4;
	
	//ingame menu
	public static final int MENU_FONT_X_OFFSET = 30;
	public static final int MENU_FONT_Y_OFFSET = -10;
	public static final int MENU_FONT_Y_ITERATOR = 60;
	public static final int MENU_WIDTH = (int) (WIDTH/3.5);
	public static final int MENU_HEIGHT = (int) (HEIGHT/1.5);
	public static final int MENU_X = (int) (WIDTH - WIDTH/3.5);
	public static final int MENU_Y = MENU_HEIGHT/4;
	public static final int MENU_ARROW_X_OFFSET = 10;
	public static final int MENU_ARROW_Y_OFFSET = -17;	
}
