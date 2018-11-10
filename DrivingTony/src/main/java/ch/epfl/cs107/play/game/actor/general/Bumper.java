package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.bike.Vehicle;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bumper extends GameEntity implements Actor {


	private ShapeGraphics ballShape;
	private Vehicle vehicle;
	private float force;
	private BasicContactListener listener;

	public Bumper(ActorGame game, boolean fixed, Vector position, Vehicle Player, float force) {
		super(game, fixed, position);

		vehicle = Player;
		this.force = force;
		
		Circle ball = new Circle(1.5f);
		ballShape = new ShapeGraphics(ball, Color.RED, Color.GRAY, 0.1f, 1.0f, 0);
		ballShape.setParent(getE());

		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(ball);
		partBuilder.setGhost(true);
		partBuilder.build();
		
		listener = new BasicContactListener();
		getE().addContactListener(listener);

	}

	public void update(float deltaTime) {
		if (listener.getEntities().contains(getPayloadEntity())) {
		   vehicle.ApplyForce(new Vector(0.0f,force), vehicle.getPosition());
		}
			
	}

	public void draw(Canvas canvas) {

		ballShape.draw(canvas);

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