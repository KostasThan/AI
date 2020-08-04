package games.spheregame.model;

import java.util.LinkedList;

import games.spheregame.searchalgorithms.SearchMethod;
/**
 * 
 * This Class extends the abstract {@link Node})<br>
 * It contains the logic for a Pathed Noded,a node that has a list of all the "states" between the root and reaching this node.
 * 
 *
 */
public class PathedNode extends Node {

	//the liste containing all the games from the start up to that node.
	private LinkedList<SphereGame> games = new LinkedList<>();


	/**
	 * Instantiates a {@link PathedNode} PathedNode with the given game as root
	 * @param game The game to be set as root
	 * @param searchMethod The searchMethod that is used to determine the "value" of each game-state
	 */
	public PathedNode(SphereGame game, SearchMethod searchMethod) {
		this(game, 0, 0, searchMethod);
	}

	
	/**\
	 * Instantiates a {@link PathedNode} with the gives game as root but with a specified actual cost, and algorithm cost.
	 * @param game The game to be set as root.
	 * @param actualCost The cost to reach this game
	 * @param algorithmCost The cost the algorithm used provides
	 * @param searchMethod The algorith to use
	 */
	public PathedNode(SphereGame game, int actualCost, int algorithmCost, SearchMethod searchMethod) {
		games.add(game);
		super.actualCost = actualCost;
		super.algorithmCost = algorithmCost;
		super.searchMethod = searchMethod;

	}

	/**
	 * Instantiates a {@link PathedNode} using a {@link State} to extract the node.
	 * @param state The state from which to extract a Node.
	 */
	public PathedNode(PathedNode state) {
		games = state.getGames();
		super.actualCost = state.getActualCost();
		super.searchMethod = state.getSearchMethod();
		super.algorithmCost = state.getAlgorithmCost();

	}

	/**
	 * Adds a game to the node.
	 * @param game to be added
	 * @param value the cost of the game
	 */
	public void add(SphereGame game, int value) {
		super.add(game, value);
		games.add(game);

	}

	/**
	 * Provides a copy of the last game added to the node
	 * @ return the last {@link SphereGame} added to the node
	 */
	public SphereGame getGame() {
		return games.peekLast();
	}

	
	/**
	 * Provides a deep copy of the list of the games passes to reach this node
	 * @return
	 */
	private LinkedList<SphereGame> getGames() {
		LinkedList<SphereGame> clone = new LinkedList<>();
		games.forEach((g) -> clone.add(new SphereGame(g)));
		return clone;

	}

	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		games.forEach((g) -> sb.append("State: " + g.toString() + "\n"));
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
		result = prime * result + ((games == null) ? 0 : games.hashCode());
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
		PathedNode other = (PathedNode) obj;

		if (games == null) {
			if (other.games != null)
				return false;
		} else if (!(games.getLast().equals(other.games.getLast())))
			return false;
		return true;
	}

}
