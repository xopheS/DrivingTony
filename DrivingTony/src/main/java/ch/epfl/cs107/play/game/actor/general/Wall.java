package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Wall extends GameEntity implements Actor {

	private ImageGraphics crateImage;
	private ImageGraphics crateImageCracked;
	private boolean crack = false;
	private BasicContactListener listener;

	public Wall(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);

		PartBuilder partBuilder = getE().createPartBuilder();
		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(1.0f, 0.0f), new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f));

		partBuilder.setShape(polygon);
		partBuilder.build();

		crateImage = new ImageGraphics("stone.4.png", 1, 1);
		crateImage.setParent(getE());
		crateImageCracked = new ImageGraphics("stone.cracked.4.png", 1, 1);
		crateImageCracked.setParent(getE());
		crateImageCracked.setAlpha(0);
		

		listener = new BasicContactListener();
		getE().addContactListener(listener);

	}

	public void update(float deltaTime) {
		
		int numberOfCollisions = listener.getEntities().size();
		if (numberOfCollisions > 0) {
			if (crack = false) {
				crack = true;
				crateImage.setAlpha(0);
				crateImageCracked.setAlpha(1);
				numberOfCollisions = 0;
			}
			else {
				getA().setCurrentScore(getA().getCurrentScore() + 10);
				destroy();
			}
		}
			
	}
	
	public void draw(Canvas canvas) {
		crateImage.draw(canvas);
		crateImageCracked.draw(canvas);
	}

	public void destroy() {
		//Impact de l'explosion d'un bloc si la distance entre le vehicule et le mur < 4.0
		getE().destroy();
		float X = getE().getPosition().x-getPayloadEntity().getPosition().x;
		float Y = getE().getPosition().y-getPayloadEntity().getPosition().y;
		float d = (float)Math.sqrt(X*X + Y*Y);
		if(d < 4.0f) {
		getPayloadEntity().applyImpulse(new Vector(-12.0f, 0.0f) , getPayloadEntity().getPosition());
		}
		getA().removeActor(this);

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