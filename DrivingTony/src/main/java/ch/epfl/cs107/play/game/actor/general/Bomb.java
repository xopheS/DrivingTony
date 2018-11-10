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
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bomb extends GameEntity implements Actor {

	private ImageGraphics bombImage;
	private ShapeGraphics bombShape;
	private ImageGraphics explosionImage;
	private boolean hit = false;
	private Part bombPart;

	public Bomb(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		Circle bomb = new Circle(0.4f);
		bombImage = new ImageGraphics("bomb.png", 1.1f, 1.1f, new Vector(0.5f, 0.4f));
		bombImage.setParent(getE());
		bombShape = new ShapeGraphics(bomb, Color.BLUE, Color.GRAY, 0.1f, 1.0f, 0);
		bombShape.setParent(getE());

		explosionImage = new ImageGraphics("explosion08.png", 2.0f, 2.0f,
				new Vector(position.x + 3.5f, position.y - 0.5f));
		explosionImage.setParent(getE());

		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(bomb);
		bombPart = partBuilder.build();

		ContactListener listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {

				hit = true;

			}

			@Override
			public void endContact(Contact contact) {
			}
		};

		getE().addContactListener(listener);
	}

	public void update(float deltaTime) {
		if (hit) {
			explosionImage.setAlpha(1);
			destroy();
			getA().removeActor(this);
		}
	}

	public void draw(Canvas canvas) {
		bombImage.draw(canvas);
		// bombShape.draw(canvas);
		explosionImage.draw(canvas);
		explosionImage.setAlpha(0.0f);

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

	public Part getBombPart() {
		return bombPart;
	}

}