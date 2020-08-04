package games.spheregame.searchalgorithms;

import games.spheregame.model.INode;
import games.spheregame.model.SphereGame;

/**
 * 
 * Functionall interface.
 * All search types implement this <p>
 * Given a game and it's move values from last move
 * calculates the current cost to be added to the previous ones
 * 
 * @param SphereGame the game after the move is made
 * @param Integer the cost of the last move made
 * 
 * 
 */


@FunctionalInterface
public interface SearchMethod{
	
	/**
	 * Using a node and a game it returns an integer representing the value of the current state
	 * @param node the node containing all the games before the given one
	 * @param game the game to evaluate to evaluate
	 * @return an integer representing the  "distance" to the end game that the {@link SearchMethod} of the node provides 
	 */
	public Integer apply(INode node,SphereGame game);
	
	
}
