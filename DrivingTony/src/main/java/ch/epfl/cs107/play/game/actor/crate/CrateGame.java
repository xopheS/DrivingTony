package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Terrain;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class CrateGame extends ActorGame {

	public boolean begin(Window window, FileSystem fileSystem) {

		super.begin(window, fileSystem);

		Crate crate1 = new Crate(this, false, new Vector(0.0f, 5.0f));
		Crate crate2 = new Crate(this, false, new Vector(0.2f, 7.0f));
		Crate crate3 = new Crate(this, false, new Vector(2.0f, 6.0f));
		addActor(crate1);
		addActor(crate2);
		addActor(crate3);

		return true;
	}

	public void update(float deltaTime) {

		super.update(deltaTime);

	}

	public void end() {

		super.end();
	}

}
