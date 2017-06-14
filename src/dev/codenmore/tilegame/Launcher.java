package dev.codenmore.tilegame;


public class Launcher implements Settings{

	public static void main(String[] args){
		Game game = new Game("Tile Game!", WIDTH, HEIGHT);
		game.start();
	}
	
}
