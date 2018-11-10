package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Support extends GameEntity implements Actor {

	private ShapeGraphics supportShape;
	private float size = 0.5f;

	public Support(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		PartBuilder partBuilder = getE().createPartBuilder();
		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(size, 0.0f), new Vector(size, size),
				new Vector(0.0f, size));

		supportShape = new ShapeGraphics(polygon, Color.BLUE, Color.GRAY, 0.1f, 1.0f, 0);
		supportShape.setParent(getE());
		partBuilder.setShape(polygon);
		partBuilder.build();

	}

	public Entity getSupportEntity() {
		// TODO Auto-generated method stub
		return getE();
	}

	public void update(float deltaTime) {
		// By default , actors have nothing to update
	}

	public void draw(Canvas canvas) {
		supportShape.draw(canvas);
	}

	public void destroy() {

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

	public float getSize() {
		return size;
	}
}