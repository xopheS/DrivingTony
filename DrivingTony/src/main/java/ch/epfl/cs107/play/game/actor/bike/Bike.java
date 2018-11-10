package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Bomb;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Bike extends GameEntity implements Actor, Vehicle {

	private final static float MAX_WHEEL_SPEED = 20.0f;
	private boolean right = true;
	private boolean hit = false;
	private float maxAngularPosition = 30.0f;

	//Les positions relatives (vecteurs) des articulations du pantin
	private Vector handLocation = new Vector(0.9f, 1.5f);
	private Vector elbowLocation = new Vector(0.2f, 1.4f);
	private Vector shoulderLocation = new Vector(-0.1f, 2.0f);
	private Vector assLocation = new Vector(-0.7f, 1.0f);
	private Vector knee1Location = new Vector(0.2f, 0.9f);
	private Vector knee2Location = new Vector(0.0f, 0.5f);
	private Vector foot1Location = new Vector(0.25f, 0.2f);
	private Vector foot2Location = new Vector(0.0f, 0.1f);
	private Vector headLocation = new Vector(-0.1f, 2.2f);

	//Localisation initiale des jambes
	private Vector initialKnee1Location = new Vector(0.2f, 0.9f);
	private Vector initialKnee2Location = new Vector(0.0f, 0.5f);
	private Vector initialFoot1Location = new Vector(0.25f, 0.2f);
	private Vector initialFoot2Location = new Vector(0.0f, 0.1f);
	
	//Les graphismes des membres du corps du pantin
	private ShapeGraphics headShape;
	private ShapeGraphics handShape;
	private ShapeGraphics armShape;
	private ShapeGraphics bodyShape;
	private ShapeGraphics upperLeg1Shape;
	private ShapeGraphics upperLeg2Shape;
	private ShapeGraphics lowerLeg1Shape;
	private ShapeGraphics lowerLeg2Shape;

	//Les formes des membres du corps du pantin
	private Circle head;
	private Polyline body;
	private Polyline hand;
	private Polyline arm;
	private Polyline upperLeg1;
	private Polyline upperLeg2;
	private Polyline lowerLeg1;
	private Polyline lowerLeg2;
	
	private Vector upperLeg1Vector = getKnee1Location().sub(getAssLocation());
	private float upperLegLength = upperLeg1Vector.getLength();
	private float upperLeg1InitialAngle = (float) (Math.PI + upperLeg1Vector.getAngle());
	private float upperLeg1Angle = upperLeg1InitialAngle;
	
	private float angularPosition;

	//Les deux roues du velo 
	private Wheel leftWheel = new Wheel(getA(), false, new Vector(getE().getPosition().x - 1.0f, getE().getPosition().y), this);
	private Wheel rightWheel = new Wheel(getA(), false, new Vector(getE().getPosition().x + 1.0f, getE().getPosition().y), this);

	//La forme de la zone de detection des collisions
	private ShapeGraphics hitboxShape;

	public Bike(ActorGame game, boolean fixed, Vector position) {

		super(game, fixed, position);

		//Specification de la tete
		head = new Circle(0.3f, getHeadLocation());
		headShape = new ShapeGraphics(head, new Color(255,204,153), new Color(255,204,153), 0.1f, 1.0f, 0);
		headShape.setParent(getE());

		//Specification du corps
		body = new Polyline(getShoulderLocation(), getAssLocation());
		bodyShape = new ShapeGraphics(body, Color.BLACK, Color.BLACK, 0.1f, 1.0f, 0);
		bodyShape.setParent(getE());

		//Specification de la main
		hand = new Polyline(getElbowLocation(), getHandLocation());
		handShape = new ShapeGraphics(hand, Color.BLUE, Color.BLACK, 0.1f, 1.0f, 0);
		handShape.setParent(getE());

		//Specification du bras
		arm = new Polyline(getShoulderLocation(), getElbowLocation());
		armShape = new ShapeGraphics(arm, Color.BLUE, Color.BLACK, 0.1f, 1.0f, 0);
		armShape.setParent(getE());

		//Specification de la jambe superieure droite
		upperLeg1 = new Polyline(getAssLocation(), getKnee1Location());
		upperLeg1Shape = new ShapeGraphics(upperLeg1, Color.BLACK, Color.BLACK, 0.1f, 1.0f, 0);
		upperLeg1Shape.setParent(getE());

		//Specification de la jambe inferieure droite
		lowerLeg1 = new Polyline(getKnee1Location(), getFoot1Location());
		lowerLeg1Shape = new ShapeGraphics(lowerLeg1, Color.BLACK, Color.BLACK, 0.1f, 1.0f, 0);
		lowerLeg1Shape.setParent(getE());

		//Specification de la jambe superieure gauche
		upperLeg2 = new Polyline(getAssLocation(), getKnee2Location());
		upperLeg2Shape = new ShapeGraphics(upperLeg2, Color.BLACK, Color.BLACK, 0.1f, 1.0f, 0);
		upperLeg2Shape.setParent(getE());

		//Specification de la jambe inferieure gauche
		lowerLeg2 = new Polyline(getKnee2Location(), getFoot2Location());
		lowerLeg2Shape = new ShapeGraphics(lowerLeg2, Color.BLACK, Color.BLACK, 0.1f, 1.0f, 0);
		lowerLeg2Shape.setParent(getE());

		//Rattachement des roues au velo
		leftWheel.attach(getE(), new Vector(-1.0f, 0.0f), new Vector(-0.5f, -1.0f));
		rightWheel.attach(getE(), new Vector(1.0f, 0.0f), new Vector(0.5f, -1.0f));

		//Specification de la zone de detection des collisions
		Polygon hitbox = new Polygon(0.0f, 0.5f, 0.5f, 1.0f, 0.0f, 2.5f, -0.5f, 1.0f);
		hitboxShape = new ShapeGraphics(hitbox, Color.BLUE, Color.GRAY, 0.1f, 1.0f, 0);
		hitboxShape.setParent(getE());

		//Creation du PartBuilder
		PartBuilder partBuilder = getE().createPartBuilder();

		//Creation du Part non solide pour la zone de detection des collisions
		partBuilder.setShape(hitbox);
		partBuilder.setGhost(true);
		partBuilder.build();

		//Creation du ContactListener specifique du velo
		ContactListener listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {

				if (contact.getOther().isGhost())
					return;

				else if (contact.getOther() == leftWheel.getPart(0) || contact.getOther() == rightWheel.getPart(0)) {
					
					//Si l'autre partie appartient a une bombe, le joueur est touche
					for(Actor a : getA().getInvolvedActors().getList()) {
						if(a instanceof Bomb) {
							if(contact.getOwner() == ((Bomb) a).getBombPart()) {
								hit = true;
							}
						}
					}
					return;
				}

				else hit = true;

			}

			@Override
			public void endContact(Contact contact) {

			}
		};

		//Rajout du ContactListener a l'entite du velo
		getE().addContactListener(listener);

	}

	public void update(float deltaTime) {

		//Applicable uniquement si le velo est en etat de fonctionnement
		if (!hit) {

			//Si la touche espace est appuyee, le velo change d'orientation
			if (getA().getCurrentKeyboard().get(KeyEvent.VK_SPACE).isDown()
					&& getA().getCurrentKeyboard().get(KeyEvent.VK_SPACE).wasUp()) {
				right = !right;

				handLocation = new Vector(-handLocation.x, handLocation.y);
				elbowLocation = new Vector(-elbowLocation.x, elbowLocation.y);
				shoulderLocation = new Vector(-shoulderLocation.x, shoulderLocation.y);
				assLocation = new Vector(-assLocation.x, assLocation.y);
				knee1Location = new Vector(-knee1Location.x, knee1Location.y);
				knee2Location = new Vector(-knee2Location.x, knee2Location.y);
				foot1Location = new Vector(-foot1Location.x, foot1Location.y);
				foot2Location = new Vector(-foot2Location.x, foot2Location.y);
				headLocation = new Vector(-headLocation.x, headLocation.y);

				headShape.setShape(new Circle(0.3f, getHeadLocation()));
				handShape.setShape(new Polyline(getElbowLocation(), getHandLocation()));
				armShape.setShape(new Polyline(getShoulderLocation(), getElbowLocation()));
				bodyShape.setShape(new Polyline(getShoulderLocation(), getAssLocation()));
			}

			//Desactivation des moteurs des roues par defaut
			leftWheel.relax();
			rightWheel.relax();

			//Si la touche directionnelle droite est appuyee, le velo se deplace vers la droite
			if (getA().getW().getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {

				if (right && leftWheel.getSpeed() > -MAX_WHEEL_SPEED) {

					leftWheel.ApplyAngularForce(-10.0f);
				}

				if (!right && rightWheel.getSpeed() < MAX_WHEEL_SPEED) {

					rightWheel.ApplyAngularForce(10.0f);
				}

			}

			//Si la touche Shift est appuyee, le velo freine
			if (getA().getW().getKeyboard().get(KeyEvent.VK_SHIFT).isDown()) {

				if (right)
					leftWheel.power(0.0f);
				if (!right)
					rightWheel.power(0.0f);
			}

			//Si la touche directionnelle gauche est appuyee, le velo se deplace vers la gauche
			if (getA().getW().getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {

				if (right && leftWheel.getSpeed() > -MAX_WHEEL_SPEED) {
					leftWheel.ApplyAngularForce(10.0f);
					
				}

				if (!right && rightWheel.getSpeed() < MAX_WHEEL_SPEED) {

					rightWheel.ApplyAngularForce(-10.0f);
					;
				}
			}

			//Si la touche E est appuyee, le velo saute
			if (getA().getW().getKeyboard().get(KeyEvent.VK_E).isDown()) {
				getE().setPosition(new Vector(getPosition().x, getPosition().y + 0.05f));
			}

			//Si la touche directionnelle haut est appuyee, le velo tourne vers le haut
			if (getA().getW().getKeyboard().get(KeyEvent.VK_UP).isDown()) {
				if (right)
					getE().applyAngularForce(50.0f);
				else
					getE().applyAngularForce(-50.0f);
			}

			//Si la touche directionnelle bas est appuyee, le velo tourne vers le bas
			if (getA().getW().getKeyboard().get(KeyEvent.VK_DOWN).isDown()) {
				if (right)
					getE().applyAngularForce(-50.0f);
				else
					getE().applyAngularForce(50.0f);
			}
			
			//Si la roue gauche est touchee, elle se detache du velo
			if (leftWheel.getHit() == true) {
				leftWheel.detach();
			}
			
			//Si la roue droite est touchee, elle se detache du velo
			if (rightWheel.getHit() == true) {
				rightWheel.detach();
			}
			
			//SLOWER IMPACT CHECK DIRECTION OF VELOCITY TO APPY RESISTANCE
			if (leftWheel.getSlower() == true || rightWheel.getSlower() == true && getE().getVelocity().x != 0) {
				if(getE().getVelocity().x > 0) getE().applyForce(new Vector(-150.0f,0.0f), getE().getPosition());
				if(getE().getVelocity().x < 0) getE().applyForce(new Vector(150.0f,0.0f), getE().getPosition());
			}

			// PEDALAGE
			if(right) angularPosition = leftWheel.getAngularPosition();
			if(!right) angularPosition = rightWheel.getAngularPosition();
			setFoot1Location(new Vector((float)Math.cos(angularPosition)*(0.2f) - initialFoot1Location.x, 
					(float)Math.sin(angularPosition)*0.2f - initialFoot1Location.y));
			setKnee1Location(new Vector((float)Math.cos(angularPosition)*(0.2f) + initialKnee1Location.x, 
					(float)Math.sin(angularPosition)*0.2f + initialKnee1Location.y));
			setFoot2Location(new Vector((float)Math.cos(angularPosition + Math.PI)*(0.2f) - initialFoot2Location.x, 
					(float)Math.sin(angularPosition+ Math.PI)*(0.2f) - initialFoot2Location.y));
			setKnee2Location(new Vector((float)Math.cos(angularPosition+ Math.PI)*(0.4f) + initialKnee2Location.x, 
					(float)Math.sin(angularPosition + Math.PI)*0.4f + initialKnee2Location.y));
			
			upperLeg1 = new Polyline(getAssLocation(), getKnee1Location());
			upperLeg2 = new Polyline(getAssLocation(), getKnee2Location());
			lowerLeg1 = new Polyline(getKnee1Location(), getFoot1Location());
			lowerLeg2 = new Polyline(getKnee2Location(), getFoot2Location());
			
			upperLeg1Shape.setShape(upperLeg1);
			upperLeg2Shape.setShape(upperLeg2);
			lowerLeg1Shape.setShape(lowerLeg1);
			lowerLeg2Shape.setShape(lowerLeg2);
		}
	  //Si le velo est touche, il est entierement detruit
	  else {
			destroy();
			getA().removeActor(this);
			getA().removeActor(leftWheel);
			getA().removeActor(rightWheel);
		}
      
     }

	//Cette methode dessine toutes les parties du velo
	public void draw(Canvas canvas) {
		leftWheel.draw(canvas);
		rightWheel.draw(canvas);
		// hitboxShape.draw(canvas);
		handShape.draw(canvas);
		armShape.draw(canvas);
		bodyShape.draw(canvas);
		upperLeg1Shape.draw(canvas);
		upperLeg2Shape.draw(canvas);
		lowerLeg1Shape.draw(canvas);
		lowerLeg2Shape.draw(canvas);
		headShape.draw(canvas);

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

	//Cette methode detruit le velo entierement
	public void destroy() {
		leftWheel.destroy();
		rightWheel.destroy();
		getE().destroy();
	}

	public Vector getHeadLocation() {
		return headLocation;
	}

	public Vector getAssLocation() {
		return assLocation;
	}

	public Vector getShoulderLocation() {
		return shoulderLocation;
	}

	public Vector getKnee1Location() {
		return knee1Location;
	}

	public Vector getKnee2Location() {

		return knee2Location;
	}

	public Vector getFoot1Location() {

		return foot1Location;
	}

	public Vector getFoot2Location() {

		return foot2Location;
	}

	public Vector getHandLocation() {

		return handLocation;
	}

	public Vector getElbowLocation() {

		return elbowLocation;
	}

	public void setElbowLocation(Vector v) {
		elbowLocation = v;
	}

	public void setHandLocation(Vector v) {
		handLocation = v;
	}

	public ShapeGraphics getHandShape() {
		return handShape;
	}

	public ShapeGraphics getArmShape() {
		return armShape;
	}
	
	public void setHit(boolean isHit) {
		hit = isHit;
	}
	
	public void ApplyForce(Vector force, Vector Location) {
		getE().applyForce(force, Location);
	}

	@Override
	public boolean getRight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Part getTankPart() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setFoot1Location(Vector v) {
		foot1Location = v;
	}
	
	public void setFoot2Location(Vector v) {
		foot2Location = v;
	}
	
	public void setKnee1Location(Vector v) {
		knee1Location = v;
	}
	
	public void setKnee2Location(Vector v) {
		knee2Location = v;
	}
}
