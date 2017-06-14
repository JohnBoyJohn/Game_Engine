package dev.codenmore.tilegame.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Text {

	public static void drawString(Graphics g, String text, int posX, int posY, boolean center, Color c, Font font){
		g.setColor(c);
		g.setFont(font);
		int x = posX;
		int y = posY;
		if(center){
			FontMetrics fm = g.getFontMetrics(font);
			x = posX - fm.stringWidth(text) / 2;
			y = (posY - fm.getHeight() / 2) + fm.getAscent();
		}
		g.drawString(text, x, y);
	}
	
}
