package ch.epfl.cs107.play.game.actor.UI;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.BikeGame;
import ch.epfl.cs107.play.game.actor.bike.Level1BG;
import ch.epfl.cs107.play.game.actor.bike.Level2BG;
import ch.epfl.cs107.play.game.actor.bike.Level3BG;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class GameScreen extends Screen {

	private TextGraphics finishMessage;
	private TextGraphics finalMessage;
	private TextGraphics deathMessage;
	private TextGraphics deathMessage2;
	private TextGraphics scoreText;
	private Level1BG level1;
	private Level2BG level2;
	private Level3BG level3;
	private int currentLevelNumber = 2;
	private int currentScreenNumber = 0;
	private Level currentLevel;
	
	public GameScreen(ActorGame actorGame) {
		super(actorGame);
		
		//Creation des trois niveaux du jeu
		level1 = new Level1BG(actorGame);
		level2 = new Level2BG(actorGame);
		level3 = new Level3BG(actorGame);
		
		scoreText = new TextGraphics("Score: " + getActorGame().getCurrentScore(), 0.1f, Color.BLACK, Color.WHITE, 0.02f, true, false,
				new Vector(2.3f, 5.0f), 1.0f, 100.0f);
		scoreText.setParent(getActorGame().getCanvas());
		scoreText.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		
		//Message de fin de niveau
		finishMessage = new TextGraphics("Finished!", 0.3f, Color.GREEN, Color.WHITE, 0.02f, true, false,
				new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		finishMessage.setParent(getActorGame().getCanvas());
		finishMessage.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));

		//Message final 
		finalMessage = new TextGraphics("THANKS FOR PLAYING!!!", 0.15f, Color.GREEN, Color.WHITE, 0.02f, true, false,
				new Vector(0.5f, -1.2f), 1.0f, 100.0f);
		finalMessage.setParent(getActorGame().getCanvas());
		finalMessage.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));

		//Message de mort
		deathMessage = new TextGraphics("You are dead!", 0.3f, Color.RED, Color.WHITE, 0.02f, true, false,
				new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		deathMessage.setParent(getActorGame().getCanvas());
		deathMessage.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));

		//Deuxieme message de mort
		deathMessage2 = new TextGraphics("Press R to restart", 0.07f, Color.RED, Color.WHITE, 0.0f, true, true,
				new Vector(0.5f, -2.0f), 1.0f, 100.0f);
		deathMessage2.setParent(getActorGame().getCanvas());
		deathMessage2.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
	}
	
	public void update(float deltaTime) {
		currentLevel.update(deltaTime);
		
		scoreText.setText("Score: " + getActorGame().getCurrentScore());

		if (currentLevel.getFinishLine().getFinished() && currentLevelNumber != 2) {

			if (currentLevelNumber == 0) {
				//Si le niveau actuel est fini depuis moins de 100.0f, lever les bras et afficher le message de victoire
				if(((Level1BG)currentLevel).levelTimer < 100.0f) {
					((Level1BG)currentLevel).levelTimer += 0.1f;
					((Bike) currentLevel.getVehicle()).setElbowLocation(new Vector(0.7f, 1.7f));
					((Bike) currentLevel.getVehicle()).setHandLocation(new Vector(0.7f, 2.8f));
					((Bike) currentLevel.getVehicle()).getHandShape()
							.setShape(new Polyline(((Bike) currentLevel.getVehicle()).getElbowLocation(),
									((Bike) currentLevel.getVehicle()).getHandLocation()));
					((Bike) currentLevel.getVehicle()).getArmShape()
							.setShape(new Polyline(((Bike) currentLevel.getVehicle()).getShoulderLocation(),
									((Bike) currentLevel.getVehicle()).getElbowLocation()));
					finishMessage.draw(getActorGame().getCanvas());
				}
				else {
			
					currentLevel.destroy();
					currentLevelNumber++;
					currentLevel = getLevelList().get(currentLevelNumber);
					currentLevel.createAllActors();
				}
			}
			else {
				if(((Level2BG)currentLevel).levelTimer < 100.0f) {
					
				   ((Level2BG)currentLevel).levelTimer += 0.1f;
				   finishMessage.draw(getActorGame().getCanvas());
				}
				
				else{
					currentLevel.destroy();
					currentLevelNumber++;
					currentLevel = getLevelList().get(currentLevelNumber);
					currentLevel.createAllActors();
				}
				
					
			}
			
		}

		//Si le dernier niveau est fini, afficher le message final de victoire
		if (currentLevel.getFinishLine().getFinished() && currentLevelNumber == 2) {

			finishMessage.draw(getActorGame().getCanvas());
			finalMessage.draw(getActorGame().getCanvas());
			currentLevel.destroy();
		}

		//Si le vehicule du niveau actuel est touche, afficher le message de mort
		if (currentLevel.getVehicle().getHit() && !currentLevel.getFinishLine().getFinished()) {
			deathMessage.draw(getActorGame().getCanvas());
			deathMessage2.draw(getActorGame().getCanvas());
		}

		//Si la touche R est appuyee, reinitialiser le niveau actuel
		if (getActorGame().getCurrentKeyboard().get(KeyEvent.VK_R).isDown() && getActorGame().getCurrentKeyboard().get(KeyEvent.VK_R).wasUp()) {
			resetLevel();
		}
	}
	
	public void draw(Canvas canvas) {
		scoreText.draw(canvas);
	}
	
	//Cette fonction permet d'obtenir la liste niveaux du jeu
	protected List<Level> getLevelList() {
		return Arrays.asList(level1, level2, level3);
	}
	
	//Cette fonction permet de choisir un niveau
	public void setLevel(int level) {
		currentLevelNumber = level;
		currentLevel = getLevelList().get(currentLevelNumber);
		currentLevel.createAllActors();
	}
	
	//Cette fonction permet de reinitialiser le niveau actuel
	public void resetLevel() {
		currentLevel.destroy();
		currentLevel.createAllActors();
	}
}
