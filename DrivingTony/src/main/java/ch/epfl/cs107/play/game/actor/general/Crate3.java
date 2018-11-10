package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.santa.BeeMinion;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Crate3 extends GameEntity implements Actor {

	private ImageGraphics crateImage;
	private boolean hit = false;
	private BasicContactListener listener;
	private Part cratePart;
	private boolean spawnedByBee = false;

	public Crate3(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		PartBuilder partBuilder = getE().createPartBuilder();
		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(1.0f, 0.0f), new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f));

		partBuilder.setShape(polygon);
		cratePart = partBuilder.build();

		crateImage = new ImageGraphics("crate.3.png", 1, 1);
		crateImage.setParent(getE());
		

		listener = new BasicContactListener();
		getE().addContactListener(listener);

	}
	
	public Crate3(ActorGame game, boolean fixed, Vector position, BeeMinion owner) {
		super(game, fixed, position);
		
		spawnedByBee = true;

		PartBuilder partBuilder = getE().createPartBuilder();
		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(1.0f, 0.0f), new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f));

		partBuilder.setShape(polygon);
		partBuilder.build();

		crateImage = new ImageGraphics("crate.3.png", 1, 1);
		crateImage.setParent(getE());
		getE().setPosition(new Vector(owner.getPosition().x, owner.getPosition().y));

		listener = new BasicContactListener();
		getE().addContactListener(listener);

	}

	public void update(float deltaTime) {
		
		int numberOfCollisions = listener.getEntities().size();
		if (numberOfCollisions > 0) {
			hit = true;
			destroy();
			getA().removeActor(this);
		}
		
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
	
	public Part getCratePart() {
		return cratePart;
	}
	
	public boolean getHit() {
		return hit;
	}

	public boolean getSpawnedByBee() {
		return spawnedByBee;
	}
}
