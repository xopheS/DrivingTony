package ch.epfl.cs107.play.game.actor.UI;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Mouse;

public class Button extends GameEntity {

	private ShapeGraphics buttonGraphics;
	private TextGraphics buttonText;
	private float width = 0.6f;
	private float height = 0.3f;
	private String buttonString;
	private Screen ownerScreen;
	private boolean isClicked = false;
	
	public Button(Screen owner, String buttonString, Vector position) {
		super(owner.getActorGame(), true, position);
		
		ownerScreen = owner;
		
		this.buttonString = buttonString;
		
		//La forme du bouton
		Polygon containerShape = new Polygon(new Vector(0.0f, 0.0f), new Vector(width, 0.0f), new Vector(width, height),
				new Vector(0.0f, height));
		
		buttonGraphics = new ShapeGraphics(containerShape, Color.GRAY, Color.BLACK, 0.1f, 1.0f, 0);
		buttonGraphics.setParent(getE());
		
		//Le texte du bouton
		buttonText = new TextGraphics(buttonString, 0.1f, Color.BLACK, Color.WHITE, 0.02f, true, false,
				new Vector(0.0f, 0.0f), 1.0f, 100.0f);
		buttonText.setParent(getE());
		buttonText.setRelativeTransform(Transform.I.translated(0.05f, -0.8f));
	}
	
	public void update(float deltaTime) {
		//Verifie si le bouton gauche de la souris est appuye et si la souris se situe a l'interieur du bouton
		if(ownerScreen.getActorGame().getCurrentMouse().getLeftButton().isDown() && ownerScreen.getActorGame().getCurrentMouse().getLeftButton().wasUp() && rectangleContains(ownerScreen.getActorGame().getCurrentMouse().getPosition())) {
			isClicked = true;
		}
		else {
			isClicked = false;
		}
	}
	
	public void draw(Canvas canvas) {
		buttonGraphics.draw(canvas);
		buttonText.draw(canvas);
	}
	
	public boolean getClicked() {
		return isClicked;
	}
	
	public boolean rectangleContains(Vector position) {
		if(position.x > getE().getPosition().x && position.x < getE().getPosition().x + width && position.y > getE().getPosition().y && position.y < getE().getPosition().y + height) {
			return true;
		}
		else
		{
			return false;
		}
	}
}
