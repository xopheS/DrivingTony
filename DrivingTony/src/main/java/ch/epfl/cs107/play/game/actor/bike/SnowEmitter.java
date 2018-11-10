package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public class SnowEmitter extends GameEntity implements Actor {

	public SnowEmitter(ActorGame game, boolean fixed) {
		super(game, fixed);
		// TODO Auto-generated constructor stub
	}

	public void update(float deltaTime) {
		if(getA().getTotalElapsedTime() % 10.0f == 0) {
			getA().addActor(new Snowflake(getA(), false, new Vector(getA().getRandomInt(100), 10.0f)));
		}
	}
	
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

}
