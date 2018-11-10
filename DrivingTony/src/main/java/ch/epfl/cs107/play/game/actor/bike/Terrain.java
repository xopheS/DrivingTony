package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Terrain extends GameEntity implements Actor {

	private ShapeGraphics terrainShape;

	public Terrain(ActorGame game, boolean fixed, Vector position, Color terrainColor, Color terrainColorBorders,
			float... points) {
		super(game, fixed, position);

		PartBuilder partBuilder = getE().createPartBuilder();
		Polyline terrain = new Polyline(points);

		partBuilder.setShape(terrain);
		partBuilder.setFriction(5.0f);
		partBuilder.build();

		terrainShape = new ShapeGraphics(terrain, terrainColor, terrainColorBorders, 0.25f, 1.0f, 0);
		terrainShape.setParent(getE());

	}

	public void update(float deltaTime) {
		// By default , actors have nothing to update
	}

	public void draw(Canvas canvas) {
		terrainShape.draw(canvas);
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

}
