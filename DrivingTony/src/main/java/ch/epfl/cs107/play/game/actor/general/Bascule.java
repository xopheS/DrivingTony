package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bascule extends GameEntity implements Actor {

	private Support support;
	private Plank plank;

	public Bascule(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		plank = new Plank(getA(), false, new Vector(position.x - 2.25f, position.y + 2.0f));
		support = new Support(getA(), true, position);

		// RevoluteConstraint
		RevoluteConstraintBuilder revoluteConstraintBuilder = getA().createRevoluteCB();
		revoluteConstraintBuilder.setFirstEntity(plank.getPlankEntity());
		revoluteConstraintBuilder.setFirstAnchor(new Vector(plank.getWidth() / 2, plank.getHeight() / 2));
		revoluteConstraintBuilder.setSecondEntity(support.getSupportEntity());
		revoluteConstraintBuilder.setSecondAnchor(new Vector(support.getSize() / 2, (support.getSize()*1.4f)));
		revoluteConstraintBuilder.setInternalCollision(true);
		revoluteConstraintBuilder.build();

	}

	public void update(float deltaTime) {
		// By default , actors have nothing to update
	}

	public void draw(Canvas canvas) {
		plank.draw(canvas);
		//support.draw(canvas);
	}

	public void destroy() {
		// By default , actors have nothing to destroy
		getE().destroy();
		plank.destroy();
		support.destroy();

	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return getE().getVelocity();
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return getE().getTransform();
	}

}
