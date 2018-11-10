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
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import java.awt.Color;

public class RopeGame implements Game {

	// Declaring a world, window, and the two entities with their graphics
	private World world;
	private Window window;
	private Entity ball, block;
	private ImageGraphics graphBlock;
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

		// Setting Fixtures(shape properties)
		PartBuilder partBuilder = block.createPartBuilder();
		PartBuilder partBuilder2 = ball.createPartBuilder();

		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(1.0f, 0.0f), new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f));
		Circle circle = new Circle(0.6f);
		partBuilder.setShape(polygon);
		partBuilder.build();
		partBuilder2.setShape(circle);
		partBuilder2.setFriction(0.5f);
		partBuilder2.build();

		// Setting the Ball
		graphBall = new ShapeGraphics(circle, Color.BLUE, Color.RED, 0.1f, 1.0f, 0);
		graphBall.setParent(ball);
		ball.setPosition(new Vector(0.6f, 4.0f));

		// Setting the Block
		graphBlock = new ImageGraphics("stone.broken.4.png", 1, 1);
		graphBlock.setParent(block);
		block.setPosition(new Vector(1.0f, 0.0f));

		RopeConstraintBuilder ropeConstraintBuilder = world.createRopeConstraintBuilder();
		ropeConstraintBuilder.setFirstEntity(block);
		ropeConstraintBuilder.setFirstAnchor(new Vector(graphBlock.getWidth() / 2, graphBlock.getHeight() / 2));
		ropeConstraintBuilder.setSecondEntity(ball);
		ropeConstraintBuilder.setSecondAnchor(Vector.ZERO);
		ropeConstraintBuilder.setMaxLength(6.0f);
		ropeConstraintBuilder.setInternalCollision(true);
		ropeConstraintBuilder.build();

		return true;
	}

	public void update(float deltaTime) {

		world.update(deltaTime);
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		graphBall.draw(window);
		graphBlock.draw(window);

	}

	public void end() {

	}

}
