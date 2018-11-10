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
import ch.epfl.cs107.play.math.PartBuilder;

public class SimpleCrateGame implements Game {

	// Declaring a world, window, and the two entities with their graphics
	private World world;
	private Window window;
	private Entity crate, block;
	private ImageGraphics graphCrate, graphBlock;

	public boolean begin(Window window, FileSystem fileSystem) {

		// Store context
		this.window = window;

		// Setting the World
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));

		//
		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(false);
		entityBuilder.setPosition(new Vector(1.f, 1.5f));
		block = entityBuilder.build();
		entityBuilder.setFixed(true);
		crate = entityBuilder.build();

		// Setting Fixtures(shape properties)
		PartBuilder partBuilder = block.createPartBuilder();
		PartBuilder partBuilder2 = crate.createPartBuilder();

		Polygon polygon = new Polygon(new Vector(0.0f, 0.0f), new Vector(1.0f, 0.0f), new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f));
		partBuilder.setShape(polygon);
		partBuilder.build();
		partBuilder2.setShape(polygon);
		partBuilder2.setFriction(0.5f);
		partBuilder2.build();

		// Setting the Crate
		graphCrate = new ImageGraphics("crate.1.png", 1, 1);
		graphCrate.setParent(crate);
		crate.setPosition(new Vector(1.0f, 0.5f));

		// Setting the Block
		graphBlock = new ImageGraphics("stone.broken.4.png", 1, 1);
		graphBlock.setParent(block);
		block.setPosition(new Vector(0.2f, 4.0f));

		return true;
	}

	public void update(float deltaTime) {

		world.update(deltaTime);
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		graphCrate.draw(window);
		graphBlock.draw(window);

	}

	public void end() {

	}

}
