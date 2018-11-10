package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Window;

public class Crate extends GameEntity implements Actor {

	private ImageGraphics crateImage;

	public Crate(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		crateImage = new ImageGraphics("crate.1.png", 1, 1);
		crateImage.setParent(this);
		getE().setPosition(position);

	}

	public void update(float deltaTime) {
		// By default , actors have nothing to update
	}

	public void draw(Canvas canvas) {
		crateImage.draw(canvas);
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
