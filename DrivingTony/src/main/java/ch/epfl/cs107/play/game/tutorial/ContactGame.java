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
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import java.awt.Color;

public class ContactGame implements Game {

	// Declaring a world, window, and the two entities with their graphics
	private World world;
	private Window window;
	private Entity ball, block;
	private ImageGraphics graphBlock;
	private ShapeGraphics graphBall;
	private BasicContactListener contactListener;

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

		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(10.0f, 0.0f), new Vector(10.0f, 1.0f),
				new Vector(0.0f, 1.0f));
		Circle circle = new Circle(0.5f);
		partBuilder.setShape(polygon);
		partBuilder.build();
		partBuilder2.setShape(circle);
		partBuilder2.setFriction(0.5f);
		partBuilder2.build();

		// Setting the Ball
		graphBall = new ShapeGraphics(circle, Color.BLUE, Color.RED, 0.1f, 1.0f, 0);
		graphBall.setParent(ball);
		ball.setPosition(new Vector(0.0f, 2.0f));

		// Setting the Block
		graphBlock = new ImageGraphics("blocker.happy.png", 10, 1);
		graphBlock.setParent(block);
		block.setPosition(new Vector(-5.0f, -1.0f));

		// Contact Listener
		contactListener = new BasicContactListener();
		ball.addContactListener(contactListener);

		return true;
	}

	public void update(float deltaTime) {

		world.update(deltaTime);
		window.setRelativeTransform(Transform.I.scaled(10.0f));

		int numberOfCollisions = contactListener.getEntities().size();
		if (numberOfCollisions > 0) {
			graphBall.setFillColor(Color.RED);
		}

		graphBall.draw(window);
		graphBlock.draw(window);

	}

	public void end() {

	}

}