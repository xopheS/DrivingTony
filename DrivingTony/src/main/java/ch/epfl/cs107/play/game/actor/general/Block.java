package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Block extends GameEntity implements Actor {

	private ImageGraphics blockImage;
	private ShapeGraphics blockShape;

	public Block(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(2.0f, 0.0f), new Vector(2.0f, 2.0f),
				new Vector(0.0f, 2.0f));
		blockImage = new ImageGraphics("stone.4.png", 2.0f, 2.0f);
		blockImage.setParent(getE());
		blockShape = new ShapeGraphics(polygon, Color.BLUE, Color.GRAY, 0.1f, 1.0f, 0);
		blockShape.setParent(getE());

		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(polygon);
		partBuilder.build();

	}

	public void update(float deltaTime) {
		// By default , actors have nothing to update
	}

	public void draw(Canvas canvas) {
		blockImage.draw(canvas);
		// blockShape.draw(canvas);

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

	public Entity getBlockEntity() {
		// TODO Auto-generated method stub
		return getE();
	}

	public float getWidth() {
		// TODO Auto-generated method stub
		return blockImage.getWidth();
	}

	public float getHeight() {
		// TODO Auto-generated method stub
		return blockImage.getHeight();
	}

}