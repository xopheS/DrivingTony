package ch.epfl.cs107.play.game.actor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Mouse;
import ch.epfl.cs107.play.window.Window;

public abstract class ActorGame implements Game {
	//La liste des acteurs
	private ConcurrentlyModifiableList<Actor> actorList = new ConcurrentlyModifiableList<Actor>();
	private World currentWorld = new World();
	private FileSystem fs;
	private Window window;
	//Le temps total qui s'est passe depuis le debut du jeu
	private float totalElapsedTime;	
	
	private int currentScore = 0;
	
	//Un generateur de valeur de divers types aleatoires
	Random randomGenerator;

	//L'etat actuel du clavier
	private Keyboard currentKeyboard;
	//L'etat actuel de la souris
	private Mouse currentMouse;

	// Viewport properties
	private Vector viewCenter;
	private Vector viewTarget;
	private Positionable viewCandidate;
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f;
	private static final float VIEW_SCALE = 12.0f;

	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		fs = fileSystem;
		currentWorld.setGravity(new Vector(0, -9.81f));
		viewCenter = Vector.ZERO;
		viewTarget = Vector.ZERO;
		
		randomGenerator = new Random();

		return true;
	}
	
	//Cette fonction permet d'actualiser les entrees de l'utilisateur (clavier et souris)
	public void updateInput() {
		currentKeyboard = window.getKeyboard();
		currentMouse = window.getMouse();
	}

	public void update(float deltaTime) {
		
		actorList.update(deltaTime);
		
		//Actualisation du temps ecoule
		totalElapsedTime = System.nanoTime();

		currentWorld.update(deltaTime);

		// Update expected viewport center
		if (viewCandidate != null) {
			viewTarget = viewCandidate.getPosition()
					.add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION));
		}
		// Interpolate with previous location
		float ratio = (float) Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
		viewCenter = viewCenter.mixed(viewTarget, 1.0f - ratio);
		// Compute new viewport
		Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
		window.setRelativeTransform(viewTransform);

		for(int i = 0; i < actorList.length(); i++) {
			actorList.get(i).draw(window);
		}
	}

	public void end() {
		for(int i = 0; i < actorList.length(); i++) {
			actorList.get(i).destroy();
		}
		actorList.clear();
	}

	public EntityBuilder createEB() {

		return currentWorld.createEntityBuilder();
	}

	public WheelConstraintBuilder createWCB() {

		return currentWorld.createWheelConstraintBuilder();
	}

	public RevoluteConstraintBuilder createRevoluteCB() {

		return currentWorld.createRevoluteConstraintBuilder();
	}

	public RopeConstraintBuilder createRopeCB() {

		return currentWorld.createRopeConstraintBuilder();
	}

	public void setViewCandidate(Positionable candidate) {
		viewCandidate = candidate;
	}

	public void addActor(Actor toAdd) {
		actorList.add(toAdd);
	}

	public void removeActor(Actor toRemove) {
		actorList.remove(toRemove);
	}

	public Canvas getCanvas() {
		return window;
	}

	public Window getW() {

		return window;
	}

	public FileSystem getFS() {
		return fs;
	}

	public Keyboard getCurrentKeyboard() {
		return currentKeyboard;
	}
	
	public Mouse getCurrentMouse() {
		return currentMouse;
	}

	public GameEntity getPayload() {
		return (GameEntity) actorList.get(0);
	}

	public ConcurrentlyModifiableList<Actor> getInvolvedActors() {
		return actorList;
	}
	
	public float getTotalElapsedTime() {
		return totalElapsedTime;
	}
	
	public int getRandomInt(int max) {
		return randomGenerator.nextInt(max + 1);
	}
	
	public int getRandomSignedInt(int bound) {
		if(getRandomBoolean()) {
			return randomGenerator.nextInt(bound + 1);
		}
		else {
			return -randomGenerator.nextInt(bound + 1);
		}
	}
	
	public boolean getRandomBoolean() {
		return randomGenerator.nextBoolean();
	}
	
	public int getCurrentScore() {
		return currentScore;
	}
	
	public void setCurrentScore(int score) {
		currentScore = score;
	}
}
