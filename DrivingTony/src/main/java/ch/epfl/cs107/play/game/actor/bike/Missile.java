package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Missile extends GameEntity implements Actor {

	//Image du missile
	private ImageGraphics missileImage;
	//Image de l'explosion
	private ImageGraphics explosionImage;
	private ShapeGraphics missileShape;
	private Vehicle owner;
	private Part missilePart;
	private boolean hit = false;
	private boolean wasHit = false;
	private float explosionTime = 0.0f;

	public Missile(ActorGame game, boolean fixed, Vector position, Vehicle owner) {
		super(game, fixed, position);

		this.owner = owner;

		missileImage = new ImageGraphics("missile.2.png", 1.6f, 0.4f, position);
		missileImage.setParent(getE());

		explosionImage = new ImageGraphics("explosion08.png", 2.0f, 2.0f, new Vector(position.x, position.y + 4.8f));
		explosionImage.setParent(getE());

		Polygon missile = new Polygon(2.1f, 2.2f, 2.1f, 2.6f, 0.6f, 2.6f, 0.6f, 2.2f);
		missileShape = new ShapeGraphics(missile, Color.YELLOW, Color.GRAY, 0.1f, 1.0f, 0);
		missileShape.setParent(getE());

		getE().setPosition(new Vector(owner.getPosition().x, owner.getPosition().y - 1.0f));

		if (owner.getRight()) {
			getE().setVelocity(new Vector(40.0f, 0.0f));
		} else {
			missileImage.setWidth(-missileImage.getWidth());
			getE().setVelocity(new Vector(-40.0f, 0.0f));
		}

		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(missile);
		missilePart = partBuilder.build();

		//Creation d'un ContactListener specifique
		ContactListener listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {

				if (contact.getOther().isGhost()) {
					return;
				}

				//Le missile ignore les contacts avec le char
				if (contact.getOther() == owner.getTankPart())
					return;

				hit = true;

			}

			@Override
			public void endContact(Contact contact) {
			}
		};

		//Ajout du ContactListener a l'entite du missile
		getE().addContactListener(listener);
	}

	public Missile(ActorGame game, boolean fixed, Vector position, Tank1 tank1) {
		super(game, fixed, position);

		this.owner = tank1;

		missileImage = new ImageGraphics("missile.2.png", 1.6f, 0.4f, position);
		missileImage.setParent(getE());

		explosionImage = new ImageGraphics("explosion08.png", 2.0f, 2.0f, new Vector(position.x, position.y + 4.8f));
		explosionImage.setParent(getE());

		Polygon missile = new Polygon(2.1f, 2.2f, 2.1f, 2.6f, 0.6f, 2.6f, 0.6f, 2.2f);
		missileShape = new ShapeGraphics(missile, Color.YELLOW, Color.GRAY, 0.1f, 1.0f, 0);
		missileShape.setParent(getE());

		getE().setPosition(new Vector(owner.getPosition().x, owner.getPosition().y - 1.0f));

		if (owner.getRight()) {
			getE().setVelocity(new Vector(40.0f, 0.0f));
		} else {
			missileImage.setWidth(-missileImage.getWidth());
			getE().setVelocity(new Vector(-40.0f, 0.0f));
		}

		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(missile);
		missilePart = partBuilder.build();

		ContactListener listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {

				if (contact.getOther().isGhost()) {
					return;
				}

				if (contact.getOther() == owner.getTankPart())
					return;

				hit = true;

			}

			@Override
			public void endContact(Contact contact) {
			}
		};

		getE().addContactListener(listener);
	}

	public void update(float deltaTime) {
		if(hit) {
			destroy();
			explosionTime++;
		}	
	}

	public void draw(Canvas canvas) {
		missileImage.draw(canvas);
		// missileShape.draw(canvas);
		explosionImage.draw(canvas);
		explosionImage.setAlpha(0);

	}

	public void destroy() {
		//Le missile est rendu transparent, donc invisible
		missileImage.setAlpha(0);
		//L'explosion est rendue completement visible
		explosionImage.setAlpha(1);
		getE().destroy();
		
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return getE().getTransform();
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return getE().getVelocity();
	}

	public Part getMissilePart() {
		return missilePart;
	}
	
	public boolean getHit() {
		return hit;
	}
	
	public float getExplosionTime() {
		return explosionTime;
	}
}
