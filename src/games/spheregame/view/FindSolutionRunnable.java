package games.spheregame.view;

import java.util.concurrent.atomic.AtomicInteger;

import games.spheregame.model.SphereGame;
import games.spheregame.searchalgorithms.AStarAlgorithm;
import games.spheregame.searchalgorithms.SphereGameSearch;

/**
 * A find solution runnable used bythe [@link MainView} to find the solution cost at the background
 * @author Kostas
 *
 */
public class FindSolutionRunnable implements  Runnable{

	SphereGame game;
	SphereGameSearch bot = new SphereGameSearch(game, new AStarAlgorithm(), false);
	AtomicInteger gameCost;
	

	public FindSolutionRunnable(SphereGame game, AtomicInteger gameCost) {
		this.game = game;
		this.gameCost = gameCost;
		
	}

	public void setGame(SphereGame game) {
		this.game = game;

	}

	@Override
	public void run() {
		synchronized (bot) {
			bot.setStartingGame(game);
			gameCost.set((bot.findSolution(false,20)));
	
		}
	}

}
