package ch.epfl.cs107.play.game.actor.santa;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.general.Crate3;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class BeeMinion extends GameEntity implements Actor {
	
	private ImageGraphics beeRight;
	private ImageGraphics beeLeft;
	private ImageGraphics bee;
	private boolean isRight = true;
	
	ArrayList<Crate3> spawnedCrates = new ArrayList<Crate3>();
	ArrayList<Crate3> cratesToRemove = new ArrayList<Crate3>();

	public BeeMinion(ActorGame game, boolean fixed, Vector position, SantaBoss owner) {
		super(game, fixed, position);
		
		beeRight = new ImageGraphics("bee.right.1.png", 1.0f, 1.0f, position);
		beeRight.setParent(getE());
		beeLeft = new ImageGraphics("bee.left.1.png", 1.0f, 1.0f, position);
		
		getE().setPosition(new Vector(owner.getPosition().x, owner.getPosition().y  + 10.0f));
		getE().setVelocity(new Vector(getA().getRandomSignedInt(10), getA().getRandomSignedInt(10) + 5.0f));
	}
	
	public void update(float deltaTime) {
		
		//Une fois toutes les 5000.f faire apparaitre une boite a la position de l'abeille
		if(getA().getTotalElapsedTime() % 5000.f == 0) {
			Crate3 x = new Crate3(getA(), false, beeRight.getAnchor(), this);
			getA().addActor(x);
		}
		
		//Une fois toutes les 500.f changer de mouvement
		if(getA().getTotalElapsedTime() % 500.f == 0) {
			getE().setVelocity(new Vector(getA().getRandomSignedInt(10), getA().getRandomSignedInt(10) + 5.0f));
		}
	}
	
	public void draw(Canvas canvas) {
		beeRight.draw(canvas);
	}
	
	public void move() {
		
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
