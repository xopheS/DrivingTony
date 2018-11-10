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
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Ball extends GameEntity implements Actor {

	private ImageGraphics ballImage;
	private ShapeGraphics ballShape;

	public Ball(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		Circle ball = new Circle(0.8f);
		ballImage = new ImageGraphics("spinner.dead.png", 2.1f, 2.1f, new Vector(0.5f, 0.4f));
		ballImage.setParent(getE());
		ballShape = new ShapeGraphics(ball, Color.BLUE, Color.GRAY, 0.1f, 1.0f, 0);
		ballShape.setParent(getE());

		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(ball);
		partBuilder.build();

	}

	public void update(float deltaTime) {

	}

	public void draw(Canvas canvas) {
		ballImage.draw(canvas);
		// ballShape.draw(canvas);

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

	public Entity getBallEntity() {
		// TODO Auto-generated method stub
		return getE();
	}

}