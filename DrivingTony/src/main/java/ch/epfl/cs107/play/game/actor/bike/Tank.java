package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Bomb;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Tank extends GameEntity implements Actor, Vehicle {

	private final static float MAX_WHEEL_SPEED = 20.0f;
	private boolean right = true;
	private boolean hit = false;

	private ShapeGraphics headShape;
	private ImageGraphics tankShape;
	private ImageGraphics canonShape;
	private Wheel leftWheel = new Wheel(getA(), false,
			new Vector(getE().getPosition().x - 1.0f, getE().getPosition().y), this);
	private Wheel rightWheel = new Wheel(getA(), false,
			new Vector(getE().getPosition().x + 1.0f, getE().getPosition().y), this);
	private Part tankPart;
	private Vector headLocation = new Vector(-0.7f, 1.75f);

	private ShapeGraphics hitboxShape;

	public Tank(ActorGame game, boolean fixed, Vector position) {

		super(game, fixed, position);

		// HEAD
		Circle head = new Circle(0.3f, getHeadLocation());
		headShape = new ShapeGraphics(head, new Color(255,204,153), new Color(255,204,153), 0.1f, 1.0f, 0);
		headShape.setParent(getE());

		// TANK
		Polygon tank = new Polygon(1.8f, 0.35f, 1.2f, 1.5f, -1.1f, 1.5f, -1.8f, 0.35f);
		tankShape = new ImageGraphics("tanks_tankGrey_body1.png", 3.6f, 1.3f, new Vector(0.5f, -0.25f));
		tankShape.setParent(getE());

		// CANON
		canonShape = new ImageGraphics("tanks_turret1.png", 1.6f, 0.2f, new Vector(-0.5f, -5.5f));
		canonShape.setParent(getE());

		// WHEELS
		leftWheel.attach(getE(), new Vector(-1.0f, 0.0f), new Vector(-0.5f, -1.0f));
		rightWheel.attach(getE(), new Vector(1.0f, 0.0f), new Vector(0.5f, -1.0f));

		// HITBOX
		Polygon hitbox = new Polygon(1.3f, 1.3f, 1.0f, 2.0f, -1.0f, 2.0f, -1.2f, 1.3f);

		hitboxShape = new ShapeGraphics(hitbox, Color.BLUE, Color.GRAY, 0.1f, 1.0f, 0);
		hitboxShape.setParent(getE());

		// PARTBUILDER
		PartBuilder partBuilder = getE().createPartBuilder();

		// Build hitbox
		partBuilder.setShape(hitbox);
		partBuilder.setGhost(true);
		partBuilder.build();

		// Build tank
		partBuilder.setShape(tank);
		partBuilder.setGhost(false);
		tankPart = partBuilder.build();

		//Creation d'un ContactListener specifique
		ContactListener listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {

				if (contact.getOther().isGhost())
					return;

				// Eviter "GAME OVER" du contact avec le tank et les missiles
				else if (contact.getOther() == leftWheel.getPart(0) || contact.getOther() == rightWheel.getPart(0)
						|| contact.getOwner() == tankPart)
					return;
				
				for(Actor a : getA().getInvolvedActors().getList()) {
					if(a instanceof Missile) {
						if(contact.getOther() == ((Missile) a).getMissilePart()) {
							return;
						}
					}
				}

				hit = true;

			}

			@Override
			public void endContact(Contact contact) {
			}

		};

		getE().addContactListener(listener);

	}

	public void update(float deltaTime) {

		//Commandes similaires a celles du bike
		if (!hit) {

			if (getA().getCurrentKeyboard().get(KeyEvent.VK_SPACE).isDown()
					&& getA().getCurrentKeyboard().get(KeyEvent.VK_SPACE).wasUp()) {
				right = !right;
				tankShape.setWidth(-tankShape.getWidth());
				canonShape.setWidth(-canonShape.getWidth());
				headLocation = new Vector(-headLocation.x, headLocation.y);
				headShape.setShape(new Circle(0.3f, getHeadLocation()));
			}

			if (getA().getCurrentKeyboard().get(KeyEvent.VK_Z).isDown()
					&& getA().getCurrentKeyboard().get(KeyEvent.VK_Z).wasUp()) {
				Missile missile = new Missile(getA(), false, canonShape.getAnchor(), this);
				getA().addActor(missile);
			}

			leftWheel.relax();
			rightWheel.relax();

			if (getA().getW().getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {

				if (right && leftWheel.getSpeed() > -MAX_WHEEL_SPEED) {

					leftWheel.ApplyAngularForce(-15.0f);
					;
				}

				if (!right && rightWheel.getSpeed() < MAX_WHEEL_SPEED) {

					rightWheel.ApplyAngularForce(15.0f);
				}

			}

			if (getA().getW().getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {

				if (right && leftWheel.getSpeed() > -MAX_WHEEL_SPEED) {

					leftWheel.ApplyAngularForce(15.0f);
				}

				if (!right && rightWheel.getSpeed() < MAX_WHEEL_SPEED) {

					rightWheel.ApplyAngularForce(-15.0f);
				}
			}

			if (getA().getW().getKeyboard().get(KeyEvent.VK_E).isDown()) {
				getE().setPosition(new Vector(getPosition().x, getPosition().y + 0.02f));
			}
			
			if (getA().getW().getKeyboard().get(KeyEvent.VK_UP).isDown()) {
				if (right)
					getE().applyAngularForce(50.0f);
				else
					getE().applyAngularForce(-50.0f);
			}

			if (getA().getW().getKeyboard().get(KeyEvent.VK_DOWN).isDown()) {
				if (right)
					getE().applyAngularForce(-50.0f);
				else
					getE().applyAngularForce(50.0f);
			}
            
			//Tir de missiles
			for (Actor a : getA().getInvolvedActors().getList()) {
				if(a instanceof Missile) {
					if(((Missile) a).getExplosionTime() > 50.0f) {
						getA().removeActor(a);
					}
				}	
			}
			
		}

		else {
			destroy();
			getA().removeActor(this);
			getA().removeActor(leftWheel);
			getA().removeActor(rightWheel);
		}

	}

	public boolean getHit() {
		return hit;
	}

	public void draw(Canvas canvas) {
		leftWheel.draw(canvas);
		rightWheel.draw(canvas);
		headShape.draw(canvas);
		tankShape.draw(canvas);
		canonShape.draw(canvas);
		// hitboxShape.draw(canvas);
	}

	public void destroy() {
		leftWheel.destroy();
		rightWheel.destroy();
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

	public Vector getHeadLocation() {
		return headLocation;
	}

	public boolean getRight() {
		return right;
	}

	public Vector getCanonLocation() {
		return new Vector(-0.5f, -5.5f);
	}

	public Part getTankPart() {
		return tankPart;
	}
	
	public void setHit(boolean isHit) {
		hit = isHit;
	}

	@Override
	public void ApplyForce(Vector force, Vector Location) {
		getE().applyForce(force, Location);
	}
}
