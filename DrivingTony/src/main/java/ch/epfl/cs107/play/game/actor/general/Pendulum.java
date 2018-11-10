package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Pendulum extends GameEntity implements Actor {

	private Ball ball;
	private Block block;
	private ShapeGraphics ropeShape;

	public Pendulum(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		ball = new Ball(getA(), false, new Vector(position.x - 3.0f, position.y - 4.0f));
		block = new Block(getA(), true, position);

		Polyline rope = new Polyline(ball.getPosition().x, ball.getPosition().y, block.getPosition().x,
				block.getPosition().y);

		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(rope);
		partBuilder.setFriction(5.0f);
		partBuilder.build();

		ropeShape = new ShapeGraphics(rope, Color.GRAY, Color.GRAY, 5.5f, 1.0f, 0);
		ropeShape.setParent(getE());

		RopeConstraintBuilder ropeConstraintBuilder = getA().createRopeCB();
		ropeConstraintBuilder.setFirstEntity(block.getBlockEntity());
		ropeConstraintBuilder.setFirstAnchor(new Vector(block.getWidth() / 2, block.getHeight() / 2));
		ropeConstraintBuilder.setSecondEntity(ball.getBallEntity());
		ropeConstraintBuilder.setSecondAnchor(Vector.ZERO);
		ropeConstraintBuilder.setMaxLength(7.0f);
		ropeConstraintBuilder.setInternalCollision(true);
		ropeConstraintBuilder.build();

	}

	public void update(float deltaTime) {

	}

	public void draw(Canvas canvas) {

		ball.draw(canvas);
		block.draw(canvas);
		ropeShape.draw(canvas);
	}

	public void destroy() {
		ball.destroy();
		block.destroy();
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