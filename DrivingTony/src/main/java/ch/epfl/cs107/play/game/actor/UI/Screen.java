package ch.epfl.cs107.play.game.actor.UI;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.window.Canvas;

//Cette classe represente un ecran de type non precise
public abstract class Screen {
	
	private ActorGame ownerGame;
	
	public Screen(ActorGame owner) {
		ownerGame = owner;
	}

	public void update(float deltaTime) {
		
	}
	
	public void draw(Canvas canvas) {
		
	}
	
	public ActorGame getActorGame() {
		return ownerGame;
	}
}
