package games.spheregame.model;

import games.spheregame.searchalgorithms.SearchMethod;

/**
 * Abstract class implementing INode and it's basic utilities
 * @author Kostas
 *
 */
public abstract class Node implements INode {
	protected int actualCost = 0;
	protected int algorithmCost = 0;
	protected SearchMethod searchMethod;

	/**
	 * Provides the search method used to evaluate each state
	 * @return an instance of {@link SearchMethod}
	 */
	public SearchMethod getSearchMethod() {
		return searchMethod;
	}

	/**
	 * 
	 * @return an int representing the actual cost "payed" to reach this state
	 */
	public int getActualCost() {
		return actualCost;
	}

	/**
	 * 
	 * @return an int representing the distance to the "end-game" the {@link SearchMethod} provides
	 */
	public int getAlgorithmCost() {
		return algorithmCost;
	}

	/**
	 * Adds the sphere game to the node along with the cost of that game
	 */
	@Override
	public void add(SphereGame game, int value) {
		this.actualCost += value;
		this.algorithmCost = searchMethod.apply(this, getGame());
	}
	



}
