package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.window.Canvas;

public interface Actor extends Positionable, Graphics {
	/**
	 * Simulates a single time step.
	 * 
	 * @param deltaTime
	 *            elapsed time since last update , in seconds , non-negative
	 */

	public default void update(float deltaTime) {
		// By default , actors have nothing to update
	}

	public default void draw(Canvas canvas) {

	}

	public default void destroy() {
		// By default , actors have nothing to destroy

	}
}
