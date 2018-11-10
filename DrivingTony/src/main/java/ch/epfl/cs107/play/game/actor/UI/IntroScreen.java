package ch.epfl.cs107.play.game.actor.UI;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class IntroScreen extends Screen {
	
	//Texte du titre du jeu
	private TextGraphics titleText;
	private ActorGame mainGame;
	//Transparence actuelle du titre
	private float currentTitleAlpha = 1.0f;
	//Vitesse de disparition
	private float fadeRate = 0.005f;
	
	public IntroScreen(ActorGame actorGame) {
		
		super(actorGame);
		
		mainGame = actorGame;
		
		titleText = new TextGraphics("Driving Tony", 0.3f, Color.WHITE, Color.RED, 0.02f, true, false,
				new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		titleText.setParent(mainGame.getCanvas());
		titleText.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
	}
	
	public void update(float deltaTime) {
		currentTitleAlpha -= fadeRate;
		titleText.setAlpha(currentTitleAlpha);
	}
	
	public void draw(Canvas canvas) {
		titleText.draw(canvas);
	}
	
	public float getTitleAlpha() {
		return currentTitleAlpha;
	}
}
