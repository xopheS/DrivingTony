package ch.epfl.cs107.play.game.actor.santa;

import java.awt.Color;
import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.bike.Missile;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Bomb;
import ch.epfl.cs107.play.game.actor.general.Crate3;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SantaBoss extends GameEntity implements Actor {

	private ImageGraphics santaShape;
	private ShapeGraphics santaShapeTest;
	private ShapeGraphics santaHealthBar;
	private TextGraphics tauntText;
	
	private ContactListener listener;
	
	//Liste des repliques du boss de fin de niveau
	private String[] taunts = {"I will destroy you!", "Happy Christmas, noob!", "Enjoy your holidays, in hell!", "Mwahahahaha!"};
	//Replique actuelle du boss
	private String currentTaunt;
	
	private boolean hit;
	
	//Vie maximale du boss
	private float maxHP = 20f;
	//Vie actuelle du boss
	private float currentHP;
	
	//Vitesse horizontale du boss
	private float horizontalSpeed = 0.02f;
	//Deviation maximale du boss
	private float maxHorizontalMovement = 3.0f;
	//Deviation actuelle du boss de sa position originelle
	private float horizontalPosition = 0.0f;
	private boolean isMovingRight = true;
	
	private float healthBarHeight = 1.0f;
	private float healthBarWidth;
	private Polygon containerShape;
	private Vector healthBarPosition = new Vector(-3.0f, 10.0f);
	
	public SantaBoss(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		
		tauntText = new TextGraphics(taunts[0], 0.8f, Color.CYAN, Color.WHITE, 0.02f, true, false,
				new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		tauntText.setParent(getE());
	    tauntText.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		
		currentHP = maxHP;
		healthBarWidth = currentHP/2f;
		
		santaShape = new ImageGraphics("santa.png", 3.0f, 5.0f, new Vector(0.5f, -0.25f));
		santaShape.setParent(getE());
		
		containerShape = new Polygon(new Vector(healthBarPosition.x, healthBarPosition.y), new Vector(healthBarPosition.x + healthBarWidth, healthBarPosition.y), new Vector(healthBarPosition.x + healthBarWidth, healthBarPosition.y + healthBarHeight),
				new Vector(healthBarPosition.x, healthBarPosition.y + healthBarHeight));
		santaHealthBar = new ShapeGraphics(containerShape, Color.PINK, Color.GRAY, 0.1f, 1.0f, 0);
		santaHealthBar.setParent(getE());
		
		Polygon P = new Polygon(
				 1.0f,0.0f,
				 1.0f,7.0f,
				 -0.8f,7.0f,
				-1.2f,4.6f,
				-1.2f,0.0f
				);
		santaShapeTest = new ShapeGraphics(P, Color.YELLOW, Color.GRAY, 0.1f, 1.0f, 0);
		santaShapeTest.setParent(getE());
		
		PartBuilder partBuilder = getE().createPartBuilder();
		partBuilder.setShape(P);
		partBuilder.build();
		
		listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {

				if (contact.getOwner().isGhost())
					return;
				
				for(Actor a : getA().getInvolvedActors().getList()) {
					if(a instanceof Missile) {
						if(contact.getOther() == ((Missile) a).getMissilePart()) {
							currentHP -= 1.0f;
							containerShape = new Polygon(new Vector(healthBarPosition.x, healthBarPosition.y), new Vector(healthBarPosition.x + healthBarWidth, healthBarPosition.y), new Vector(healthBarPosition.x + healthBarWidth, healthBarPosition.y + healthBarHeight),
									new Vector(healthBarPosition.x, healthBarPosition.y + healthBarHeight));
							santaHealthBar.setShape(containerShape);
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
	
	public void update(float deltaTime) {
		
		if(currentHP > 0) {
			move();
			
			healthBarWidth = currentHP/2f;
			
			//Avec une frequence de 9000.f faire apparaitre une abeille
			if(getA().getTotalElapsedTime() % 9000.f == 0) {
				BeeMinion b = new BeeMinion(getA(), false, santaShape.getAnchor(), this);
				getA().addActor(b);
				//Obtenir une replique aleatoire de la liste des repliques
				currentTaunt = taunts[getA().getRandomInt(taunts.length - 1)];
				tauntText.setText(currentTaunt);
			}
		}
		else {
			getE().destroy();
			getA().removeActor(this);
		}
		
	}
	
	//Cette fonction permet au boss de bouger vers la gauche et la droite alternativement
	void move() {
		if(isMovingRight) {
			if(horizontalPosition < maxHorizontalMovement) {
				horizontalPosition += horizontalSpeed;
				moveRight();
			}
			else {
				isMovingRight = false;
			}
		}
		else {
			if(horizontalPosition > -maxHorizontalMovement) {
				horizontalPosition -= horizontalSpeed;
				moveLeft();
			}
			else {
				isMovingRight = true;
			}
		}
	}
	
	//Cette fonction permet de faire bouger le boss vers la droite
	void moveRight() {
		getE().setPosition(new Vector(getE().getPosition().x + horizontalSpeed, getE().getPosition().y));
	}
	
	//Cette fonction permet de faire bouger le boss vers la gauche
	void moveLeft() {
		getE().setPosition(new Vector(getE().getPosition().x - horizontalSpeed, getE().getPosition().y));
	}
	
	void jump() {
		
	}
	 
	public void draw(Canvas canvas) {
		if(currentHP > 0) {
			santaShape.draw(canvas);
			//santaShapeTest.draw(canvas);
			tauntText.draw(canvas);
			santaHealthBar.draw(canvas);
		}
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

}
