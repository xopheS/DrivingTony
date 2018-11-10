package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Glissant extends GameEntity implements Actor {

	private ShapeGraphics glissantShape;

	public Glissant(ActorGame game, boolean fixed, Vector position,float... points) {
		super(game, fixed, position);

		Polygon p = new Polygon(points);
		glissantShape = new ShapeGraphics(p, Color.CYAN, Color.WHITE, 0.2f, 1.0f, 0);
		glissantShape.setParent(getE());

		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(p);
		partBuilder.setFriction(0.1f);
		partBuilder.build();

	}

	public void update(float deltaTime) {

	}

	public void draw(Canvas canvas) {
		glissantShape.draw(canvas);

	}

	public void destroy() {
		// By default , actors have nothing to destroy
		getE().destroy();

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