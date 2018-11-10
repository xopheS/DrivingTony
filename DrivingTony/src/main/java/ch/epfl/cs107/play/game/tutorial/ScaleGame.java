package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class ScaleGame implements Game {

	// Declaring a world, window, and the entities with their graphics
	private World world;
	private Window window;
	private Entity ball, plank, block;
	private ImageGraphics graphBlock, graphPlank, graphBallCover;
	private ShapeGraphics graphBall;

	public boolean begin(Window window, FileSystem fileSystem) {

		// Store context
		this.window = window;

		// Setting the World
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));

		// Spawning the objects
		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(true);
		entityBuilder.setPosition(new Vector(1.f, 1.5f));
		block = entityBuilder.build();
		entityBuilder.setFixed(false);
		ball = entityBuilder.build();
		plank = entityBuilder.build();

		// Setting Fixtures(shape properties)
		PartBuilder partBuilder = block.createPartBuilder();
		PartBuilder partBuilder2 = ball.createPartBuilder();
		PartBuilder partBuilder3 = plank.createPartBuilder();

		Polygon polygon1 = new Polygon(new Vector(0.0f, 0.0f), new Vector(5.0f, 0.0f), new Vector(5.0f, 0.2f),
				new Vector(0.0f, 0.2f));
		Polygon polygon2 = new Polygon(new Vector(0.0f, 0.0f), new Vector(10.0f, 0.0f), new Vector(10.0f, 1.0f),
				new Vector(0.0f, 1.0f));
		Circle circle = new Circle(0.6f);
		partBuilder.setShape(polygon2);
		partBuilder.setFriction(3.0f);
		partBuilder.build();
		partBuilder3.setShape(polygon1);
		partBuilder3.setFriction(1.0f);
		partBuilder3.build();
		partBuilder2.setShape(circle);
		partBuilder2.setFriction(3.0f);
		partBuilder2.build();

		// Setting the Ball
		graphBall = new ShapeGraphics(circle, Color.BLUE, Color.GRAY, 0.1f, 1.0f, 0);
		graphBall.setParent(ball);
		Circle Ball = (Circle) graphBall.getShape();
		graphBallCover = new ImageGraphics("explosive.11.png", 2.0f * Ball.getRadius(), 2.0f * Ball.getRadius(),
				new Vector(0.5f, 0.5f));
		graphBall.setParent(ball);
		graphBallCover.setParent(ball);
		ball.setPosition(new Vector(2.0f, 6.0f));

		// Setting the Block
		graphBlock = new ImageGraphics("stone.broken.4.png", 10, 1);
		graphBlock.setParent(block);
		block.setPosition(new Vector(-5.0f, -1.0f));

		// Setting the Plank
		graphPlank = new ImageGraphics("wood.3.png", 5, 0.2f);
		graphPlank.setParent(plank);
		plank.setPosition(new Vector(-2.5f, 0.8f));

		// RevoluteConstraint
		RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder();
		revoluteConstraintBuilder.setFirstEntity(block);
		revoluteConstraintBuilder
				.setFirstAnchor(new Vector(graphBlock.getWidth() / 2, (graphBlock.getHeight() * 7) / 4));
		revoluteConstraintBuilder.setSecondEntity(plank);
		revoluteConstraintBuilder.setSecondAnchor(new Vector(graphPlank.getWidth() / 2, graphPlank.getHeight() / 2));
		revoluteConstraintBuilder.setInternalCollision(true);
		revoluteConstraintBuilder.build();

		return true;
	}

	public void update(float deltaTime) {

		world.update(deltaTime);
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		graphBall.draw(window);
		graphBallCover.draw(window);
		graphBlock.draw(window);
		graphPlank.draw(window);

		// Control
		if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
			ball.applyAngularForce(10.0f);
		}

		else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
			ball.applyAngularForce(-10.0f);
		}

	}

	public void end() {

	}

}
