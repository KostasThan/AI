package games.spheregame.model;

import games.spheregame.searchalgorithms.SearchMethod;

/**
 * 
 * This Class extends the abstract {@link Node})<br>
 * Just like the {@link PathedNode} it has the same utilities without keeping
 * track of the games passed to reach the last game.<br>
 * It contains the logic for a Pathed Noded,a node that has a list of all the
 * "states" between the root and reaching this node.
 * 
 * 
 */
public class SingleNode extends Node {

	private SphereGame lastGame;

	public SingleNode(SphereGame game, SearchMethod searchMethod) {
		this(game, 0, 0, searchMethod);
	}

	/**
	 * Creates a {@link SingleNode} instance with the a game as root,the actual cost
	 * of the game,a "guess" cost to the end game and a {@link SearchMethod}
	 * 
	 * @param lastGame the game to be set as root
	 * @param actualCost the actual cost payed to reach this game
	 * @param algorithmCost the "guess" cost to the end-game
	 * @param searchMethod a {@link SearchMethod} to be used
	 */
	public SingleNode(SphereGame lastGame, int actualCost, int algorithmCost, SearchMethod searchMethod) {
		this.lastGame = lastGame;
		super.actualCost = actualCost;
		super.searchMethod = searchMethod;
		super.algorithmCost = algorithmCost;

	}

	/**
	 * Copy constructor
	 * 
	 * @param node the node to copy
	 */
	public SingleNode(SingleNode node) {
		lastGame = node.getGame();
		super.actualCost = node.getActualCost();
		super.algorithmCost = node.getAlgorithmCost();
		super.searchMethod = node.getSearchMethod();

	}

	/**
	 * 
	 * Adds the specified game along with its value to this node.
	 * 
	 * @param game  the game to be added
	 * @param value the cost of this game provided by a search method
	 */
	public void add(SphereGame game, int value) {
		super.add(game, value);
		lastGame = game;

	}

	/**
	 * Provides a copy of the last game added to the node @ return the last
	 * {@link SphereGame} added to the node
	 */
	public SphereGame getGame() {
		return lastGame;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("State: " + lastGame.toString() + "\n");
		sb.append("\nTotal cost: " + getActualCost());

		return sb.toString();
	}

	@Override
	public int compareTo(INode o) {
		if (equals(o)) {
			return 0;
		} else {
			return algorithmCost >= o.getAlgorithmCost() ? 1 : -1;
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + actualCost;
		result = prime * result + ((lastGame == null) ? 0 : lastGame.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SingleNode other = (SingleNode) obj;

		if (lastGame == null) {
			if (other.lastGame != null)
				return false;
		} else if (!(lastGame.equals(other.lastGame)))
			return false;
		return true;
	}

}
