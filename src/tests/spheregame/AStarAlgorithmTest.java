package tests.spheregame;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import games.spheregame.model.SphereGame;
import games.spheregame.searchalgorithms.AStarAlgorithm;
import games.spheregame.searchalgorithms.SphereGameSearch;
import games.spheregame.searchalgorithms.UCSAlgorithm;

class AStarAlgorithmTest {

	
	
	
	
	@RepeatedTest(value = 100)
	void test() {
		
		SphereGame game = new SphereGame(3);
		

			SphereGameSearch bot = new SphereGameSearch(game,new UCSAlgorithm(),false);
			AStarAlgorithm astar = new AStarAlgorithm();
			int actuallCost;
			int guess;
			for (int tries = 0; tries < 1000; tries++) {

				actuallCost = bot.findSolution(false);
				guess = astar.guessEndGame(game);


				assertTrue(guess <= actuallCost);

				game.reset();
			}
		
	}

}
