package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class FinishLine extends GameEntity implements Actor {

	private ImageGraphics finishFlag;
	private ShapeGraphics finishFlagShape;
	private boolean isFinished = false;
	private Polygon rectangle = new Polygon(-0.1f, 0.0f, 0.1f, 0.0f, 0.1f, 50.0f, -0.1f, 50.0f);
	private Vehicle vehicle;
	private BasicContactListener listener;

	public FinishLine(ActorGame game, boolean fixed, Vector position, Vehicle Player) {
		super(game, fixed, position);

		vehicle = Player;

		//Creation de l'image a afficher du drapeau de fin
		finishFlag = new ImageGraphics("flag.red.png", 1.6f, 1.3f, Vector.ZERO);
		finishFlag.setParent(getE());
		finishFlagShape = new ShapeGraphics(rectangle, Color.YELLOW, Color.GRAY, 0.1f, 1.0f, 0);
		finishFlagShape.setParent(getE());

		
		PartBuilder partBuilder = getE().createPartBuilder();
		
		//Creation de la partie de detection des collisions du drapeau de fin
		partBuilder.setShape(rectangle);
		partBuilder.setGhost(true);
		partBuilder.build();

		//Creation d'un BasicContactListener
		listener = new BasicContactListener();
		getE().addContactListener(listener);

	}

	public void update(float deltaTime) {

		//Si le velo atteint la ligne de fin, l'on modifie l'etat de celle-ci
		if (listener.getEntities().contains(getPayloadEntity())) 
		{
			getA().setCurrentScore(getA().getCurrentScore() + 100);
			isFinished = true;
		}

	}

	public void draw(Canvas canvas) {
		finishFlagShape.draw(canvas);
		finishFlag.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getFinished() {
		return isFinished;
	}

}
