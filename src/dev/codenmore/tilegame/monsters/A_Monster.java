package dev.codenmore.tilegame.monsters;

import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.gfx.Animation;
import dev.codenmore.tilegame.gfx.SpriteSheet;

public abstract class A_Monster implements Monster_Settings{
	protected Handler handler;
	
	//typical monster variables
	private int att;
	private int def;
	private int luk;
	private int agi;
	private int hp;
	private int hp_max;
	private int mp;
	private int mp_max;
	
	private String name;
	private int race;
	private int racenumber;
	
	//images
	private SpriteSheet sheet;
	private BufferedImage image;
	@SuppressWarnings("unused")
	private Animation attack, die;
	
	public A_Monster(Handler handler, int race, int racenumber, SpriteSheet sheet){
		this.handler = handler;
		this.sheet = sheet;
		this.race = race;
		this.racenumber = racenumber;
		name = MONSTERS[race][racenumber];
		
		att = DEFAULT_ATT;
		def = DEFAULT_DEF;
		luk = DEFAULT_LUK;
		agi = DEFAULT_AGI;
		hp = hp_max = DEFAULT_HP;
		mp = mp_max = DEFAULT_MP;
		
		getImagesFromSpriteSheet();
	}
	
	public abstract void getImagesFromSpriteSheet();

	//GETTERS & SETTERS
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public int getAtt() {
		return att;
	}

	public void setAtt(int att) {
		this.att = att;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getLuk() {
		return luk;
	}

	public void setLuk(int luk) {
		this.luk = luk;
	}

	public int getAgi() {
		return agi;
	}

	public void setAgi(int agi) {
		this.agi = agi;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHp_max() {
		return hp_max;
	}

	public void setHp_max(int hp_max) {
		this.hp_max = hp_max;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public int getMp_max() {
		return mp_max;
	}

	public void setMp_max(int mp_max) {
		this.mp_max = mp_max;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public SpriteSheet getSprite() {
		return sheet;
	}

	public void setSheet(SpriteSheet sheet) {
		this.sheet = sheet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	}

	public int getRacenumber() {
		return racenumber;
	}

	public void setRacenumber(int racenumber) {
		this.racenumber = racenumber;
	}
}
