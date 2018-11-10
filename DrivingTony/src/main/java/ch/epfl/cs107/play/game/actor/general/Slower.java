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

public class Slower extends GameEntity implements Actor {

	private ImageGraphics slowerImage;
	private ShapeGraphics slowerShape;
	private Part slowerPart;

	public Slower(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		Polygon slow = new Polygon(0.0f, 0.0f, 3.0f, 0.0f, 3.0f, 0.01f, 0.0f, 0.01f);
		slowerImage = new ImageGraphics("slime.blue.right.3.png", 3.0f, 1.3f, new Vector(0.0f, 0.0f));
		slowerImage.setParent(getE());
		slowerShape = new ShapeGraphics(slow, Color.BLUE, Color.GRAY, 0.1f, 1.0f, 0);
		slowerShape.setParent(getE());

		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(slow);
		slowerPart = partBuilder.build();

	}

	public void update(float deltaTime) {

	}

	public void draw(Canvas canvas) {
		slowerImage.draw(canvas);
		slowerShape.draw(canvas);

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
	

	public Part getSlowerPart() {
		// TODO Auto-generated method stub
		return slowerPart;
	}

}