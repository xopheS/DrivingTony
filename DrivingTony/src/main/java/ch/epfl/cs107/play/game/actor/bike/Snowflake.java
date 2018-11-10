package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Snowflake extends GameEntity implements Actor {

	ShapeGraphics snowflakeShape;
	
	public Snowflake(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		
		Circle snowflake = new Circle(0.1f, position);
		
		snowflakeShape = new ShapeGraphics(snowflake, Color.WHITE, Color.GRAY, 0.05f, 1.0f, 0);
		snowflakeShape.setParent(getE());
	}
	
	public void update(float deltaTime) {
		
	}
	
	public void draw(Canvas canvas) {
		snowflakeShape.draw(canvas);
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
