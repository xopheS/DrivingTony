package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.santa.SantaBoss;
import ch.epfl.cs107.play.math.Vector;

public class Level3BG extends Level {

	private Tank1 vehicle;
	private FinishLine finishLine;
	private SantaBoss boss1;

	public Level3BG(ActorGame actorGame) {
		super(actorGame);
	}

	@Override
	public void createAllActors() {

		//Creation des acteurs du niveau 3
		vehicle = new Tank1(getA(), false, new Vector(-4.0f, 10.0f));
		finishLine = new FinishLine(getA(), true, new Vector(65.0f, 0.0f), vehicle);
		SnowEmitter snowEmitter = new SnowEmitter(getA(), true);

		Terrain gameTerrain = new Terrain(getA(), true, new Vector(0.0f, 0.0f), new Color(239,252,253), Color.lightGray, 
				-1100.0f, 50.0f,
				-11.0f, 50.0f,
				-11.0f, 0.0f, 
				0.0f, 0.0f,
				3.0f, 1.0f, 
				8.0f, 1.0f,
				15.0f, 3.0f,
				16.0f, 3.0f,
				25.0f, 0.0f,
				25.0f, -5.0f,
				35.0f, -5.0f,
				50.0f, -5.0f,
				55.0f, -4.0f,
				65.0f, 0.0f, 
				6500.0f, -1000.0f);
		
		// Crate3 crate = new Crate3(getA(), false, new Vector(0.0f, 5.0f));
		boss1 = new SantaBoss(getA(), true, new Vector(50.0f, -7.0f));

		//Ajout des acteurs du niveau 3 a la liste des acteurs
		getA().addActor(vehicle);
		getA().addActor(finishLine);
		getA().addActor(boss1);
		getA().setViewCandidate(vehicle);
		getA().addActor(gameTerrain);
		getA().addActor(snowEmitter);
		// getA().addActor(crate);

	}

	@Override
	public Vehicle getVehicle() {

		return vehicle;
	}

	@Override
	public FinishLine getFinishLine() {
		// TODO Auto-generated method stub
		return finishLine;
	}

}
