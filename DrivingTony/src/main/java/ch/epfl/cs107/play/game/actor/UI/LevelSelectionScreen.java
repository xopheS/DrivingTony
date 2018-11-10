package ch.epfl.cs107.play.game.actor.UI;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

//Cet ecran permet de selectionner le niveau desire afin d'y jouer directement
public class LevelSelectionScreen extends Screen {
	
	public Button level1Button, level2Button, level3Button;

	public LevelSelectionScreen(ActorGame actorGame) {
		super(actorGame);
		
		level1Button = new Button(this, "Level 1", new Vector(0.0f, 0.0f));
		level2Button = new Button(this, "Level 2", new Vector(0.0f, -0.4f));
		level3Button = new Button(this, "Level 3", new Vector(0.0f, -0.8f));
	}
	
	public void update(float deltaTime) {
		level1Button.update(deltaTime);
		level2Button.update(deltaTime);
		level3Button.update(deltaTime);
	}
	
	public void draw(Canvas canvas) {
		level1Button.draw(canvas);
		level2Button.draw(canvas);
		level3Button.draw(canvas);
	}
}
