package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Spikes extends GameEntity implements Actor {

	private ImageGraphics spikesImage;
	private ShapeGraphics spikesShape;
	private Part spikesPart;

	public Spikes(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		Polyline spikes = new Polyline(0.0f, 0.0f, 0.2f, 0.3f, 0.4f, 0.0f, 0.6f, 0.3f, 0.8f, 0.0f, 1.0f, 0.3f, 1.2f,
				0.0f);
		spikesImage = new ImageGraphics("spikes.png", 1.2f, 0.4f, new Vector(0.0f, 0.0f));
		spikesImage.setParent(getE());
		spikesShape = new ShapeGraphics(spikes, Color.BLUE, Color.GRAY, 0.1f, 1.0f, 0);
		spikesShape.setParent(getE());

		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(spikes);
		spikesPart = partBuilder.build();

	}

	public void update(float deltaTime) {

	}

	public void draw(Canvas canvas) {
		spikesImage.draw(canvas);
		// spikesShape.draw(canvas);

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
	

	public Part getSpikesPart() {
		// TODO Auto-generated method stub
		return spikesPart;
	}

}