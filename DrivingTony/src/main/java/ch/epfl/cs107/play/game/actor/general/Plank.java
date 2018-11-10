package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Plank extends GameEntity implements Actor {

	private ImageGraphics plankImage;

	public Plank(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		PartBuilder partBuilder = getE().createPartBuilder();
		Polygon plank = new Polygon(new Vector(0.0f, 0.0f), new Vector(4.95f, 0.0f), new Vector(4.95f, 0.2f),
				new Vector(0.0f, 0.2f));

		partBuilder.setShape(plank);
		partBuilder.setFriction(3.0f);
		partBuilder.build();

		plankImage = new ImageGraphics("wood.3.png", 5, 0.2f);
		plankImage.setParent(getE());

	}

	public void update(float deltaTime) {
		// By default , actors have nothing to update
	}

	public void draw(Canvas canvas) {
		plankImage.draw(canvas);
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

	public float getWidth() {
		// TODO Auto-generated method stub
		return plankImage.getWidth();
	}

	public float getHeight() {
		// TODO Auto-generated method stub
		return plankImage.getHeight();
	}

	public Entity getPlankEntity() {
		// TODO Auto-generated method stub
		return getE();
	}

}
