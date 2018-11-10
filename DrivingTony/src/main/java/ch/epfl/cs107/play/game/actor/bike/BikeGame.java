package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.UI.GameScreen;
import ch.epfl.cs107.play.game.actor.UI.IntroScreen;
import ch.epfl.cs107.play.game.actor.UI.LevelSelectionScreen;
import ch.epfl.cs107.play.game.actor.UI.MenuScreen;
import ch.epfl.cs107.play.game.actor.UI.Screen;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Crate3;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

//Une enumeration qui represente les differents ecrans du jeu
enum Screens {IntroScreen, MenuScreen, LevelSelection, GameScreen}

public class BikeGame extends ActorGame {

	//Les differents ecrans du jeu
	private IntroScreen myIntroScreen;
	private MenuScreen myMenuScreen;
	private LevelSelectionScreen myLevelSelectionScreen;
	private GameScreen myGameScreen;
	//L'ecran actuel affiche par le jeu
	private Screens currentScreen;

	public boolean begin(Window window, FileSystem fileSystem) {

		super.begin(window, fileSystem);
		
		//Creation des ecrans du jeu
		myIntroScreen = new IntroScreen(this);
		myMenuScreen = new MenuScreen(this);
		myLevelSelectionScreen = new LevelSelectionScreen(this);
		myGameScreen = new GameScreen(this);
		
		//L'ecran initial du jeu est l'ecran d'introduction
		currentScreen = Screens.IntroScreen;

		return true;
	}

	public void update(float deltaTime) {
		
		//Actualisation des entrees de l'utilisateur (clavier, souris)
		super.updateInput();
		
		//Actualisation de l'ecran actuel du jeu
		switch(currentScreen) {
		case IntroScreen:
			if(myIntroScreen.getTitleAlpha() <= 0) {
				currentScreen = Screens.MenuScreen;
			}
			
			myIntroScreen.update(deltaTime);
			myIntroScreen.draw(getCanvas());
			break;
		case MenuScreen:
			
			if(myMenuScreen.playGameButton.getClicked()) {
				myGameScreen.setLevel(0);
				currentScreen = Screens.GameScreen;
			}
			else if(myMenuScreen.levelSelection.getClicked()) {
				currentScreen = Screens.LevelSelection;
			}
			
			myMenuScreen.update(deltaTime);
			myMenuScreen.draw(getCanvas());
			break;
		case LevelSelection:
			
			if(myLevelSelectionScreen.level1Button.getClicked()) {
				myGameScreen.setLevel(0);
				currentScreen = Screens.GameScreen;
			}
			else if(myLevelSelectionScreen.level2Button.getClicked()) {
				myGameScreen.setLevel(1);
				currentScreen = Screens.GameScreen;
			}
			else if(myLevelSelectionScreen.level3Button.getClicked()) {
				myGameScreen.setLevel(2);
				currentScreen = Screens.GameScreen;
			}
			
			myLevelSelectionScreen.update(deltaTime);
			myLevelSelectionScreen.draw(getCanvas());
			break;
		case GameScreen:
			super.update(deltaTime);
			
			myGameScreen.update(deltaTime);
			myGameScreen.draw(getCanvas());
			break;
		}

	}

	public void end() {
		super.end();
	}
}
