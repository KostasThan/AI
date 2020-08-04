package games.spheregame.model;

import games.spheregame.searchalgorithms.SearchMethod;

/**
 * This class had the logic for creating all the types of INode upon demand.
 * 
 * @author Kostas
 *
 */

public class NodeFactory {
	private boolean enablePath;
	private SearchMethod searchMethod;

	/**
	 * Creates a instance of a {@link NodeFactory}. The preferences passed to this
	 * contstructor is saved and every time a node is demanded, a node is created
	 * using this preferences.
	 * 
	 * @param enablePath
	 * @param searchMethod
	 */
	public NodeFactory(boolean enablePath, SearchMethod searchMethod) {
		this.enablePath = enablePath;
		this.searchMethod = searchMethod;
	}

	/**
	 * Returns an instance of an {@link INode} using the saved preferences and the {@link SphereGame} given.
	 * @param game the game to be set as root to the node
	 * @return an instance of an {@link INode}
	 */
	public INode getNode(SphereGame game) {
		if (enablePath) {
			return new PathedNode(game, searchMethod);
		} else
			return new SingleNode(game, searchMethod);

	}

	/**
	 * Sets the preferences accoriding to the passed parametres.
	 * @param searchMethod an instance of {@link SearchMethd} to be used from the nodes.
	 * @param enablePath whether or not the {@link INode} will save the entire path or just the last game
	 */
	public void editPrefereces(SearchMethod searchMethod, boolean enablePath) {
		this.enablePath = enablePath;
		this.searchMethod = searchMethod;
	}
	

	/**
	 * 
	 * Returns an instance of an {@link INode} using another {@link INode}
	 * @param node the node to copy from
	 * @return an instance of an {@link INode}
	 */
	public INode getNode(INode node) {
		if (node.getClass() == PathedNode.class) {
			return new PathedNode((PathedNode) node);
		} else {
			return new SingleNode((SingleNode) node);
		}

	}

	/**
	 * Return a boolean representing whehter the path is enabled or not
	 * @return true if the path is enabled,false if not
	 */
	public boolean hasEnabledPath() {
		return enablePath;
	}

	/**
	 * Return the {@link SearchMethod} that is used by the {@link INode}
	 * @return an instance of {@link SearchMethod} 
	 */
	public SearchMethod getSearchMethod() {
		return searchMethod;
	}

}
