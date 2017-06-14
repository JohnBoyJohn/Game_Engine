package dev.codenmore.tilegame.monsters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.gfx.Animation;

public class Monster implements Monster_Settings{

	private Handler handler;
	
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
	private BufferedImage image;
	private Animation attack, die;
	
	public Monster(Handler handler, int race, int racenumber, BufferedImage image, Animation attack, Animation die){
		this.handler = handler;
		this.image = image;
		this.attack = attack;
		this.die = die;
		this.race = race;
		this.racenumber = racenumber;
		name = MONSTERS[race][racenumber];
		
		att = DEFAULT_ATT;
		def = DEFAULT_DEF;
		luk = DEFAULT_LUK;
		agi = DEFAULT_AGI;
		hp = hp_max = DEFAULT_HP;
		mp = mp_max = DEFAULT_MP;
	}
	
	public Monster(Handler handler, int race, int racenumber, int att, int def, int luk, int agi, int hp, int mp, BufferedImage image, Animation attack, Animation die){
		this.handler = handler;
		this.image = image;
		this.attack = attack;
		this.die = die;
		this.race = race;
		this.racenumber = racenumber;
		name = MONSTERS[race][racenumber];
		
		this.att = att;
		this.def = def;
		this.luk = luk;
		this.agi = agi;
		this.hp = hp_max = hp;
		this.mp = mp_max = mp;
	}
	
	public void tick(){
		attack.tick();
	}
	
	public void render(Graphics g){
		
	}

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

	public Animation getAttack() {
		return attack;
	}

	public void setAttack(Animation attack) {
		this.attack = attack;
	}

	public Animation getDie() {
		return die;
	}

	public void setDie(Animation die) {
		this.die = die;
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
