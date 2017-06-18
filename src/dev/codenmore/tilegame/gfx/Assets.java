package dev.codenmore.tilegame.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.Settings;

public class Assets implements Settings{
	
	private static final int width = 32, height = 32;
	
	//fonts
	public static Font font28;
	public static Font fontArial24;
	public static Font fontArialBold24;
	public static Font fontArialBlack24;
	
	//images
	public static BufferedImage  player, dirt, grass, wall, stone, tree;
	public static BufferedImage[] player_down, player_up, player_left, player_right;
	public static BufferedImage[] enemy_down, enemy_up, enemy_left, enemy_right;
	public static BufferedImage[] start_btn;
	public static BufferedImage item_log, item_stone, item_herb, item_magic_tear, 
			item_att_berry, item_def_berry, item_hp_berry, item_mp_berry;
	public static BufferedImage inventoryScreen;
	public static BufferedImage warp_tile;
	public static BufferedImage menu_arrow, menu_arrow_2, mainMenu_background, monsterMenu_background, monsterTaskMenu_background;
	public static BufferedImage fightmenu_monsterHealthBar;
	
	//monster images
	public static BufferedImage slime, dragon, golem;
	public static BufferedImage[] slime_attack;
	public static BufferedImage[] slime_die;
	public static BufferedImage[] dragon_attack;
	public static BufferedImage[] dragon_die;
	public static BufferedImage[] golem_attack;
	public static BufferedImage[] golem_die;
	
	

	public static void init(){
		font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);
		fontArial24 = FontLoader.loadFont("res/fonts/ARIAL.ttf", 22);
		fontArialBold24 = FontLoader.loadFont("res/fonts/ARIALBD.ttf", 22);
		fontArialBlack24 = FontLoader.loadFont("res/fonts/ARIBLK.ttf", 22);
		
		//init sheets
		SpriteSheet menu_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/menu.png"));
		SpriteSheet tiles_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet_1.png"));
		SpriteSheet player_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/player.png"));
		SpriteSheet enemy_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/enemy.png"));
		SpriteSheet monster_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/monsters.png"));
		SpriteSheet items_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/items.png"));
		SpriteSheet inventory_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/inventory/inventoryScreen.png"));

		//init BufferdImages Arrays
		start_btn = new BufferedImage[2];
		player_down = new BufferedImage[2];
		player_up = new BufferedImage[2];
		player_left = new BufferedImage[2];
		player_right = new BufferedImage[2];
		enemy_down = new BufferedImage[2];
		enemy_up = new BufferedImage[2];
		enemy_left = new BufferedImage[2];
		enemy_right = new BufferedImage[2];
		slime_attack = new BufferedImage[2];
		slime_die = new BufferedImage[2];
		dragon_attack = new BufferedImage[2];
		dragon_die = new BufferedImage[2];
		golem_attack = new BufferedImage[2];
		golem_die = new BufferedImage[2];

		//init menu images
		start_btn[0] = menu_sheet.crop(0, 0, width * 2, height);
		start_btn[1] = menu_sheet.crop(0, height, width * 2, height);
		
		//init player images
		player = tiles_sheet.crop(0, 0, width, height);
		player_down[0] = player_sheet.crop(0, 0, width, height); 
		player_down[1] = player_sheet.crop(width, 0, width, height);
		player_up[0] = player_sheet.crop(width * 2, 0, width, height); 
		player_up[1] = player_sheet.crop(width * 3, 0, width, height);
		player_right[0] = player_sheet.crop(0, height, width, height);
		player_right[1] = player_sheet.crop(width, height, width, height);
		player_left[0] = player_sheet.crop(width * 2, height, width, height);
		player_left[1] = player_sheet.crop(width * 3, height, width, height);
		
		//init enemy images
		enemy_down[0] = enemy_sheet.crop(0, 0, width, height); 
		enemy_down[1] = enemy_sheet.crop(width, 0, width, height);
		enemy_up[0] = enemy_sheet.crop(width * 2, 0, width, height); 
		enemy_up[1] = enemy_sheet.crop(width * 3, 0, width, height);
		enemy_right[0] = enemy_sheet.crop(0, height, width, height);
		enemy_right[1] = enemy_sheet.crop(width, height, width, height);
		enemy_left[0] = enemy_sheet.crop(width * 2, height, width, height);
		enemy_left[1] = enemy_sheet.crop(width * 3, height, width, height);
		
		//init tiles
		dirt = tiles_sheet.crop(width, 0, width, height);
		grass = tiles_sheet.crop(width * 2, 0, width, height);
		wall = tiles_sheet.crop(width * 3, 0, width, height);
		tree = tiles_sheet.crop(width * 5, 0, width, height * 2);
		stone = tiles_sheet.crop(width * 6, 0, width, height);
		warp_tile = tiles_sheet.crop(width * 7, 0, width, height);
		
		//init items
		item_log = items_sheet.crop(0, 0, width, height);
		item_stone = items_sheet.crop(width, 0, width, height);
		item_herb = items_sheet.crop(width * 2, 0, width, height);
		item_magic_tear = items_sheet.crop(width * 3, 0, width, height);
		item_att_berry = items_sheet.crop(width * 4, 0, width, height);
		item_def_berry = items_sheet.crop(width * 5, 0, width, height);
		item_hp_berry = items_sheet.crop(width * 6, 0, width, height);
		item_mp_berry = items_sheet.crop(width * 7, 0, width, height);
		
		//init inventory
		inventoryScreen = inventory_sheet.crop(0, 0, 512, 384);
		
		//init menu
		menu_arrow = ImageLoader.loadImage("/textures/arrow.png");
		menu_arrow_2 = ImageLoader.loadImage("/textures/arrow_2.png");
		mainMenu_background = ImageLoader.loadImage("/textures/menus/mainmenu.png");
		monsterMenu_background = ImageLoader.loadImage("/textures/menus/monstermenu.png");
		monsterTaskMenu_background = ImageLoader.loadImage("/textures/menus/monstertaskmenu.png");
		fightmenu_monsterHealthBar = ImageLoader.loadImage("/textures/menus/fight/monsterhealthbar.png");
		
		//init monsters
		slime = monster_sheet.crop(TILESIZE * 7, 0, TILESIZE, TILESIZE);
		slime_attack[0] = monster_sheet.crop(TILESIZE * 7, 0, TILESIZE, TILESIZE);
		slime_attack[1] = monster_sheet.crop(TILESIZE * 7, 0, TILESIZE, TILESIZE);
		slime_die[0] = monster_sheet.crop(TILESIZE * 7, 0, TILESIZE, TILESIZE);
		slime_die[1] = monster_sheet.crop(TILESIZE * 7, 0, TILESIZE, TILESIZE);
		
		dragon = monster_sheet.crop(0, TILESIZE * 4, TILESIZE, TILESIZE);
		dragon_attack[0] = monster_sheet.crop(0, TILESIZE * 4, TILESIZE, TILESIZE);
		dragon_attack[1] = monster_sheet.crop(0, TILESIZE * 4, TILESIZE, TILESIZE);
		dragon_die[0] = monster_sheet.crop(0, TILESIZE * 4, TILESIZE, TILESIZE);
		dragon_die[1] = monster_sheet.crop(0, TILESIZE * 4, TILESIZE, TILESIZE);
		
		golem = monster_sheet.crop(TILESIZE * 6, TILESIZE * 4, TILESIZE, TILESIZE);
		golem_attack[0] = monster_sheet.crop(TILESIZE * 6, TILESIZE * 4, TILESIZE, TILESIZE);
		golem_attack[1] = monster_sheet.crop(TILESIZE * 6, TILESIZE * 4, TILESIZE, TILESIZE);
		golem_die[0] = monster_sheet.crop(TILESIZE * 6, TILESIZE * 4, TILESIZE, TILESIZE);
		golem_die[1] = monster_sheet.crop(TILESIZE * 6, TILESIZE * 4, TILESIZE, TILESIZE);
	}
}
