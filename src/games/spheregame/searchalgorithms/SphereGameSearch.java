package games.spheregame.searchalgorithms;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import games.spheregame.model.INode;
import games.spheregame.model.PathedNode;
import games.spheregame.model.SphereGame;
import games.spheregame.model.NodeFactory;

/**
 * This class implements the "bot" behhind finding the solution on a
 * {@link SphereGame}
 *
 */
public class SphereGameSearch {
	private SphereGame startingGame;
	private TreeSet<INode> searchFront;
	private Set<SphereGame> visitedGames = new HashSet<>();
	private NodeFactory sf;
	private static final int DEFAULT_TIME = 15;

	/**
	 * Create an instance of {@link SphereGameSearch}
	 * 
	 * @param startingGame the game to find a solution for
	 * @param searchMethod the search method to be used
	 * @param enablePath   whether or not to keep track of the path
	 */
	public SphereGameSearch(SphereGame startingGame, SearchMethod searchMethod, boolean enablePath) {
		this.startingGame = startingGame;
		sf = new NodeFactory(enablePath, searchMethod);
		searchFront = new TreeSet<>();
	}

	/**
	 * Sets the starting game to another one than the one specifies in the
	 * costructor
	 * 
	 * @param startingGame A {@link SphereGame} to be set as starting game
	 */
	public void setStartingGame(SphereGame startingGame) {
		this.startingGame = startingGame;
		searchFront.clear();
		visitedGames.clear();

	}

	
	
	/**
	 * Change the preferences in regards to the {@link SearchMethod} and whether or
	 * not to keep track of the path. This is the method the
	 * {@link #editPreferences(SearchMethod) } and {@link #editPreferences(Boolean)
	 * searchMethod(enablePath)} internally call
	 * 
	 * @param searchMethod a {@link SearchMethod} instance to be set as the
	 *                     preffered one
	 * @param enablePath   whether or not to keep track of the path
	 */
	public void editPreferences(SearchMethod searchMethod, boolean enablePath) {
		sf.editPrefereces(searchMethod, enablePath);
	}

	
	
	
	/**
	 * Calls the {@link #editPreferences(SearchMethod,boolean)} with the search
	 * method given and the already used path enabling strategy
	 * 
	 * @param searchMethod a {@link SearchMethod searchMethod} to be used
	 */
	public void editPreferences(SearchMethod searchMethod) {
		sf.editPrefereces(searchMethod, sf.hasEnabledPath());
	}

	
	
	
	/**
	 * Calls the {@link #editPreferences(SearchMethod,boolean)} with the search
	 * method already used the given parameter.
	 * 
	 * @param enablePath whehther or not to keep track of the path
	 */
	public void editPreferences(boolean enablePath) {
		sf.editPrefereces(sf.getSearchMethod(), enablePath);
	}
	
	
	
	/**
	 * This method has the bussiness logic for finding a complete solution for the {@link SphereGame startingGame} given.
	 * All the answers are unique and do not contain loops.
	 * @param enableMultipleAnswers whether or not to have the option to search for another answer after the first
	 * @param seconds the seconds this methos is allowed to run
	 * @return an int representing the cost for the last solution provided <br>
	 * Or -1 :
	 * <li> if no answer could be found for the given amount of time </li>
	 * <li> if prompted to give more answers that it could be generated </li>
	 * 
	 */
	public int findSolution(boolean enableMultipleAnswers, int seconds) {
		
		
		//find the amount we have to find 
		long startTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);  
		long endTime = startTime + seconds;

		//if the staring game is not null
		if (startingGame != null) {

			//clear any previous searches and visited games
			searchFront.clear();
			visitedGames.clear();
			
			//add the starting game to the searchFront
			searchFront.add(sf.getNode(startingGame));		
			
			//keeping track of the nodes we expaned
			int nodesExpanded = 0;
			
			
			//while we are in time and the search front is not empty
			while (TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS) < endTime
					&& !searchFront.isEmpty()) {

				//add one to nodes expaned
				nodesExpanded++;
				
				//get the node to be expaned
				INode expandState = searchFront.pollFirst();
				
				//get the last game of the node to be modified
				SphereGame expandGame = expandState.getGame();

				
				//if the expand game is over
				if (expandGame.isOver()) {

					
					//set the answer to no
					int answer = JOptionPane.NO_OPTION;

					
					//ask the user if he wants more answers
					//this is enabled by the method's parametres
					if (enableMultipleAnswers) {
						answer = JOptionPane.showConfirmDialog(null,
								expandState.toString() + "\nNodesExpaded: " + nodesExpanded
										+ "\nWant to search for another solution?",
								"A very smart Title!", JOptionPane.YES_NO_OPTION);
					}
					
					//if the user says he does not needs another one
					if (answer != JOptionPane.YES_OPTION) {


						//return the cost of the final last game
						return expandState.getActualCost();
					}

				}

				//add the game to visited game so we dont visit it again
				visitedGames.add(expandGame);

				
				//for all the possibled moves the game can make
				for (int i = 0; i < expandGame.getLength(); i++) {

					//get the move value
					int moveValue = expandGame.move(i);
					
					//if it is greater than 0 (the .move(i) returns -1 if a move is not valid)
					if (moveValue > 0) {
						
						//make a copu
						SphereGame game = new SphereGame(expandGame);
						
						//reset the game to its correct state
						expandGame.undoMove();
						
						//if the visited games already has the game we just "found" skip the rest
						if (visitedGames.contains(game)) {
							
							continue;
						}
						
						
						//if we get here means we got a new game to search
						
	
						//create a replica of the node
						INode node = sf.getNode(expandState);
						
						//add to the node the game found
						node.add(game, moveValue);
						
						//add the node to the search front
						searchFront.add(node);
					}
				}
			}

			
			// we get here if user kept asking for different answers
			if (searchFront.isEmpty()) {			
				JOptionPane.showMessageDialog(null, "No other solution found");
				searchFront.clear();
				visitedGames.clear();
				System.gc();
				return 0;
			}

		}

		//we get here if the time elapsed!
		searchFront.clear();
		visitedGames.clear();
		System.gc();
		return -1;

	}

	/**
	 * Calls the {@link #findSolution(boolean,int)} with a default time
	 * @param enableMultipleAnswers whether or not the user can ask for multiple answers
	 * @return an int representing the cost of the final solution
	 */
	public int findSolution(boolean enableMultipleAnswers) {
		return findSolution(enableMultipleAnswers, DEFAULT_TIME);

	}

	
	//use this method to print the search front after each move..
	//cpus cant handle big games using this method
	@SuppressWarnings("unused")
	private void printSearchFront(TreeSet<PathedNode> searchFront) {
		System.out.println("----------------");
		for (PathedNode state : searchFront) {

			System.out.print("Dephth: " + state.getAlgorithmCost());
			System.out.println(" ||Game: " + state.getGame().toString());

		}
		System.out.println("----------------");
	}

}
