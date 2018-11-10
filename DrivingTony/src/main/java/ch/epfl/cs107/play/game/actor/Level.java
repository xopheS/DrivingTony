package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.FinishLine;
import ch.epfl.cs107.play.game.actor.bike.Vehicle;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

//Cette classe definit un niveau de sous-type non specifie
public abstract class Level implements Actor {

	private ActorGame aGame;

	public Level(ActorGame actorGame) {
		aGame = actorGame;
	}

	public void update(float deltaTime) {
		// aGame.update(deltaTime);
	}

	public abstract void createAllActors();

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	public ActorGame getA() {
		return aGame;
	}

	public abstract Vehicle getVehicle();

	public abstract FinishLine getFinishLine();

	public void destroy() {
		for (Actor a : aGame.getInvolvedActors().getList()) {
			a.destroy();
			aGame.removeActor(a);
		}
	}
}
