package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.general.Bumper;
import ch.epfl.cs107.play.game.actor.general.Crate3;
import ch.epfl.cs107.play.game.actor.general.Spikes;
import ch.epfl.cs107.play.game.actor.general.Wall;
import ch.epfl.cs107.play.math.Vector;

public class Level2BG extends Level {

	private Tank vehicle;
	private FinishLine finishLine;
	public float levelTimer = 0.0f;

	public Level2BG(ActorGame actorGame) {
		super(actorGame);
	}

	@Override
	public void createAllActors() {

		vehicle = new Tank(getA(), false, new Vector(-4.0f, 5.0f));
		getA().addActor(vehicle);
		
		finishLine = new FinishLine(getA(), true, new Vector(100.0f, 30.0f), vehicle);

		
		Terrain gameTerrain = new Terrain(getA(), true, new Vector(0.0f, 0.0f), Color.LIGHT_GRAY, Color.GRAY, 
				-1100.0f, 50.0f,
				-11.0f, 50.0f,
				-11.0f, 0.0f,
				0.0f, 0.0f,
				13.0f, 0.0f, 
				24.0f, -3.0f, 
				60.0f, -3.0f,
				71.0f, 0.0f,
				90.0f, 0.0f,
				90.0f, 30.0f,
				110.0f, 30.0f,
				110.0f,60.0f,
				6500.0f, -1000.0f);
		
		Bumper bumper = new Bumper(getA(), true, new Vector(75.0f, 0.0f), vehicle, 10000.0f);
		
		//BUILDING WALL
		Wall wall[][] = new Wall[15][7];
		
		float height = 0.0f;
		float width = 0.0f;
		for(int i=0; i<15; i++) {
			for (int j=0; j<7; j++) {
			   wall[i][j] = new Wall(getA(), true, new Vector(40.0f + width, -3.0f + height));
			   getA().addActor(wall[i][j]);
			   width = width + 1.0f;
			}
			width = 0;
		    height = height + 1.0f;
		}

		
		getA().addActor(bumper);
		getA().addActor(gameTerrain);
		getA().addActor(finishLine);
		getA().setViewCandidate(vehicle);
	}

	@Override
	public Vehicle getVehicle() {
		return vehicle;
	}

	@Override
	public FinishLine getFinishLine() {
		return finishLine;
	}

}
