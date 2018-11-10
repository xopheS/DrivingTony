package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.general.Ball;
import ch.epfl.cs107.play.game.actor.general.Bascule;
import ch.epfl.cs107.play.game.actor.general.Block;
import ch.epfl.cs107.play.game.actor.general.Bomb;
import ch.epfl.cs107.play.game.actor.general.Bumper;
import ch.epfl.cs107.play.game.actor.general.Glissant;
import ch.epfl.cs107.play.game.actor.general.Pendulum;
import ch.epfl.cs107.play.game.actor.general.Plank;
import ch.epfl.cs107.play.game.actor.general.Slower;
import ch.epfl.cs107.play.game.actor.general.Spikes;
import ch.epfl.cs107.play.math.Vector;

public class Level1BG extends Level {

	private Bike vehicle;
	private FinishLine finishLine;
	public float levelTimer = 0.0f;

	public Level1BG(ActorGame actorGame) {
		super(actorGame);
	}

	@Override
	public void createAllActors() {

		vehicle = new Bike(getA(), false, new Vector(0.0f, 5.0f));
		getA().addActor(getVehicle());
		
		finishLine = new FinishLine(getA(), true, new Vector(542.0f, -10.0f), vehicle);
		
		
		//PENDULES
		Pendulum pendule1 = new Pendulum(getA(), true, new Vector(48.0f, 5.5f));
		Pendulum pendule2 = new Pendulum(getA(), true, new Vector(60.0f, 5.5f));
		Pendulum pendule3 = new Pendulum(getA(), true, new Vector(130.0f, 40.0f));
		Pendulum pendule4 = new Pendulum(getA(), true, new Vector(230.0f, 11.0f));
		Pendulum pendule5 = new Pendulum(getA(), true, new Vector(240.0f, 11.0f));
		Pendulum pendule6 = new Pendulum(getA(), true, new Vector(250.0f, 11.0f));
		
		//BASCULES
		Bascule bascule1= new Bascule(getA(), true, new Vector(20.5f, -2.0f));
		Bascule bascule2 = new Bascule(getA(), true, new Vector(28.5f, -2.0f));
		Bascule bascule3 = new Bascule(getA(), true, new Vector(303.5f, -3.0f));
		Bascule bascule4 = new Bascule(getA(), true, new Vector(309.5f, -6.0f));
		Bascule bascule5 = new Bascule(getA(), true, new Vector(315.5f, -9.0f));
		
		//SPIKES
		Spikes spikes1[] = new Spikes[15];
		Spikes spikes2[] = new Spikes[15];
		
		float s = 0.0f;
		for(int i=0; i<15; i++) {
			spikes1[i] = new Spikes(getA(), true, new Vector(15.0f + s, -6.0f));
			s= s + 1.2f;
			getA().addActor(spikes1[i]);
		}
		
		s = 0.0f;
		for(int i=0; i<15; i++) {
			spikes2[i] = new Spikes(getA(), true, new Vector(260.0f + s, -17.0f));
			s= s + 1.2f;
			getA().addActor(spikes2[i]);
		}
		
		
		//SLOWERs	
		Slower slower1 = new Slower(getA(), true, new Vector(230.0f, 0.0f));
		Slower slower2 = new Slower(getA(), true, new Vector(250.0f, 0.0f));
		Slower slower3 = new Slower(getA(), true, new Vector(280.0f, 0.0f));
		
		
		//BUMPERS
		Bumper bumper1 = new Bumper(getA(), true, new Vector(75.0f, -4.0f), vehicle, 2800.0f);
		Bumper bumper2 = new Bumper(getA(), true, new Vector(495.0f, -40.0f), vehicle, 2800.0f);
		
		Glissant terrainGlissant = new Glissant(getA(), true, new Vector(102.0f, 30.0f), 0.0f,0.0f, 62.0f,0.0f, 62.2f,0.2f, 0.0f,0.2f);
		//Bomb bomb = new Bomb(getA(), true, new Vector(-10.0f, 1.0f));
		
		Terrain gameTerrain = new Terrain(getA(), true, new Vector(0.0f, 0.0f), new Color(101,76,17), new Color(102, 204, 0),
				-6500.0f, -1000.0f,
				-11.0f, 50.0f,
				-11.0f, 0.0f,
				0.0f, 0.0f,//start 
				15.0f, 0.0f, 
				15.0f, -6.0f, 
				33.0f, -6.0f, 
				33.0f, -4.0f, 
				102.0f, -4.0f,
				102.0f, 30.0f,//terrain glissant 
				164.0f, 30.0f, 
				220.0f, 0.0f, 
				300.0f, 0.0f,
				300.0f,-17.0f,
				316.4f,-17.0f,
				316.4f,-12f,
				366f,-12,//Fall and jump
				400.0f,-50f,
				402.0f,-52f,
				405.0f,-54f,
				408.0f,-54f,
				411.0f,-54f,
				414.0f,-53f,
				417.0f,-50f,
				420.0f,-45f,//end
				420.0f,-60f,
				422.0f,-60f,
				482.0f,-40f,
				522.0f,-40f,
				522.0f,-10.0f,
				547.0f,-10.0f,
				547.0f,30.0f,
				6500.0f, -1000.0f);
				

		
		getA().addActor(gameTerrain);
		getA().setViewCandidate(vehicle);
		getA().addActor(finishLine);
		
		getA().addActor(bascule1);
		getA().addActor(bascule2);
		getA().addActor(bascule3);
		getA().addActor(bascule4);
		getA().addActor(bascule5);
		
		getA().addActor(pendule1);
		getA().addActor(pendule2);
		getA().addActor(pendule3);
		getA().addActor(pendule4);
		getA().addActor(pendule5);
		getA().addActor(pendule6);
		
		getA().addActor(bumper1);
		getA().addActor(bumper2);
		
		getA().addActor(terrainGlissant);
		//getA().addActor(bomb);
		
		getA().addActor(slower1);
		getA().addActor(slower2);
		getA().addActor(slower3);
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public FinishLine getFinishLine() {
		return finishLine;
	}

}
