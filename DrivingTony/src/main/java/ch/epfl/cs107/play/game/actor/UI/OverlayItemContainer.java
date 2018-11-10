package ch.epfl.cs107.play.game.actor.UI;

import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class OverlayItemContainer {
	
	private ShapeGraphics containerGraphics;
	
	public OverlayItemContainer() {
		Polygon containerShape = new Polygon(new Vector(0.0f, 0.0f), new Vector(1.0f, 0.0f), new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f));
		
		//containerGraphics = new ShapeGraphics()
	}
	
	public void update(float deltaTime) {
		
	}
	
	public void draw(Canvas canvas) {
		
	}
}
