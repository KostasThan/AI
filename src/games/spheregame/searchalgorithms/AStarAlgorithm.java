package games.spheregame.searchalgorithms;

import java.util.ArrayDeque;

import games.spheregame.model.INode;
import games.spheregame.model.SphereGame;

/**
 * This class has the logic that is used to implemenent the A* algorithm
 * @author Kostas
 */
public class AStarAlgorithm implements SearchMethod {

	
	
	@Override
	public Integer apply(INode state, SphereGame game) {
		
		//the cost for this algorithm is the actuall cost up until that game provided by state.getActualCost() + the amount of the "guess"
		return state.getActualCost()+guessEndGame(game);
	}
	
	
	/**
	 * Return an int representing the guess of this algorith to the "end-game"
	 * @param game the game to guess upon
	 * @return an int representing the "distance" to the end
	 */
	public int guessEndGame(SphereGame game) {
		//if the game is over
		if(game.isOver()) {
			//the distance if 0
			return 0;
		}
		//else the distance is the distance to swap the lefter "A" with the righter "M"
		//after that if the emmpty bopx if on the far right add one more move
		
		String board[] = game.getState();
		ArrayDeque<Integer> aSpots = new ArrayDeque<>();
		ArrayDeque<Integer> mSpots = new ArrayDeque<>();
		for(int i =0; i<board.length;i++) {
			if(board[i].equals("M")) {
				mSpots.add(i);
			}
			if(board[i].equals("A")) {
				aSpots.add(i);
			}
		}
		int guess = 0;
		while(!aSpots.isEmpty()) {
			int aSpot = aSpots.pop();
			int mSpot = mSpots.pollLast();
			if(mSpot>aSpot) {
				guess += mSpot - aSpot;
				guess++;
			}else {
				break;
			}
		
		}
		
		if(board[board.length-1].contentEquals("-")) {
			guess++;
		}
		return guess;
	}




}


