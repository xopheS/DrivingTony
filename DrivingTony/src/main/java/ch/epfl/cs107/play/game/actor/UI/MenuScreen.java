package ch.epfl.cs107.play.game.actor.UI;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

//Ceci est l'ecran de menu principal du jeu
public class MenuScreen extends Screen {
	
	public Button playGameButton;
	public Button levelSelection;
	
	public MenuScreen(ActorGame actorGame) {
		super(actorGame);
		
		playGameButton = new Button(this, "Play Game", new Vector(0.f, 0.f));
		levelSelection = new Button(this, "Select Level", new Vector(0.5f, 0.5f));
	}

	public void update(float deltaTime) {
		playGameButton.update(deltaTime);
		levelSelection.update(deltaTime);
	}
	
	public void draw(Canvas canvas) {
		playGameButton.draw(canvas);
		levelSelection.draw(canvas);
	}
}
