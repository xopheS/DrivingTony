package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;

public abstract class GameEntity {
	//Entite associee a la GameEntity
	private Entity e;
	//ActorGame auquel appartient la GameEntity
	private ActorGame aGame;

	public GameEntity(ActorGame game, boolean fixed, Vector position) {
		
		if(game == null) {
			throw new NullPointerException("ActorGame can't be null");
		}
		
		aGame = game;

		EntityBuilder eBuilder = game.createEB();
		eBuilder.setFixed(fixed);
		eBuilder.setPosition(position);
		e = eBuilder.build();

	}

	public GameEntity(ActorGame game, boolean fixed) {
		aGame = game;

		EntityBuilder eBuilder = game.createEB();
		eBuilder.setFixed(fixed);
		e = eBuilder.build();
	}

	public void destroy() {
		e.destroy();
	}

	protected Entity getE() {
		return e;
	}

	public Part getPart(int index) {
		return e.getParts().get(index);
	}

	public void ApplyAngularForce(float index) {
		e.applyAngularForce(index);
	}
	
	

	protected ActorGame getA() {

		return aGame;
	}

	public Entity getPayloadEntity() {
		return aGame.getPayload().e;
	}

	public void ApplyForce(Vector force, Vector Location) {
		e.applyForce(force, Location);
	}
}
