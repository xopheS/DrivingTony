package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.Vector;

public interface Vehicle extends Actor {

	//Interface pour les vehicules du jeu
	
	public boolean getHit();

	public void setHit(boolean isHit);
	
	public void ApplyForce(Vector force, Vector Location);
	
	public boolean getRight();
	
	public Part getTankPart();
}
