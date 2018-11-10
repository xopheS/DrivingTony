package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Bomb;
import ch.epfl.cs107.play.game.actor.general.Slower;
import ch.epfl.cs107.play.game.actor.general.Spikes;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

public class Wheel extends GameEntity implements Actor {

	private ShapeGraphics wheelShape;
	private ImageGraphics wheelImage;
	private WheelConstraint constraint;
	private Entity ownerVehicle;
	private Vehicle owner;
	private Boolean hit = false;
	private Boolean slower = false;

	public Wheel(ActorGame game, boolean fixed, Vector position, Vehicle owner) {

		super(game, fixed, position);
		
		this.owner = owner;

		PartBuilder partBuilder = getE().createPartBuilder();
		Circle circle = new Circle(0.5f);

		partBuilder.setShape(circle);
		partBuilder.build();

		wheelImage = new ImageGraphics("tanks_tankTracks4.png", 1.0f, 1.0f, new Vector(0.5f, 0.5f));
		wheelImage.setParent(getE());
		
		
		// CONTACT LISTENER
		ContactListener listener = new ContactListener() {
		@Override
		public void beginContact(Contact contact) {


			for(Actor a : getA().getInvolvedActors().getList()) {
				if(a instanceof Bomb) {
				    if(contact.getOther() == ((Bomb) a).getBombPart()) {
						owner.setHit(true);	
					}
				}
				
				if(a instanceof Slower) {
					if(contact.getOther() == ((Slower) a).getSlowerPart()) {
					  slower = true;
					}
					else slower = false;
				}
				if(a instanceof Spikes) {
					if(contact.getOther() == ((Spikes) a).getSpikesPart()) {
						hit = true;
					}			
				}
				
		}
							

	    }

			@Override
			public void endContact(Contact contact) {

			}
	    };
	    
	    getE().addContactListener(listener);
	}

	//Cette methode attache une roue a l'entite d'un vehicule
	public void attach(Entity vehicle, Vector anchor, Vector axis) {

		ownerVehicle = vehicle;
		WheelConstraintBuilder constraintBuilder = getA().createWCB();
		constraintBuilder.setFirstEntity(vehicle);
		constraintBuilder.setFirstAnchor(anchor);
		constraintBuilder.setSecondEntity(getE());
		constraintBuilder.setSecondAnchor(Vector.ZERO);
		constraintBuilder.setAxis(axis);
		constraintBuilder.setFrequency(3.0f);
		constraintBuilder.setDamping(0.5f);
		constraintBuilder.setMotorMaxTorque(10.0f);
		constraint = constraintBuilder.build();

	}

	public void power(float speed) {

		constraint.setMotorEnabled(true);
	}

	public void relax() {

		constraint.setMotorEnabled(false);
	}

	public void detach() {

		constraint.destroy();
	}

	/**
	 * @return relative rotation speed , in radians per second
	 */
	public float getSpeed() {

		return getE().getAngularVelocity() - ownerVehicle.getAngularVelocity();
	}

	public void update(float deltaTime) {
		// By default , actors have nothing to update
	}

	public void draw(Canvas canvas) {
		wheelImage.draw(canvas);
	}

	public void destroy() {

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
	
	public boolean getHit() {
		return hit;
	}
	
	public boolean getSlower() {
		return slower;
	}
	
	public float getAngularPosition() {
		return getE().getAngularPosition();
	}


}
