package games.spheregame.model;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class has the logic for implementing a Sphere Game.
 * 
 * @author Kostas
 *
 */
public class SphereGame {

	private String[] board;
	private ArrayDeque<int[]> undoMoves = new ArrayDeque<>();

	/**
	 * Creates an instance of a sphere game with 2*n + 1 boxes and 2*n spheres
	 * 
	 * @param n the number of each colour of the spheres
	 */
	public SphereGame(int n) {
		board = new String[2 * n + 1];
		initializeBoard();

	}

	/**
	 * Copy constructor
	 * 
	 * @param game the game to copy
	 */
	public SphereGame(SphereGame game) {
		this.board = game.getState();
		undoMoves = game.getUndoMoves();
	}

	/**
	 * Returns whether or not the game is over
	 * 
	 * @return True if the game is over,false otherwise.
	 */
	public boolean isOver() {

		// if the last spot is not a "-"
		if (!board[board.length - 1].contentEquals("-")) {

			// check all the spots
			for (int i = 0; i < board.length; i++)

				// when you find the first "A"
				if (board[i].contentEquals("A")) {
					while (i < board.length - 1) {
						// adavance the spot by one
						i++;

						// else if we find a single "M" after the first "Q" return false
						if (board[i].contentEquals("M")) {
							return false;
						}

					}
				}

		} else {
			return false;
		}

		return true;
	}

	/**
	 * Resets the instance of the {@link SphereGame} to a random starting game with
	 * the same amount of spheres.
	 */
	public void reset() {
		initializeBoard();
	}

	/**
	 * Given a pos representing the place of the sphere it will place the said
	 * sphere to the empty box.
	 * 
	 * @param pos the int representing which sphere to move to the empty box.
	 * @return -1 if the move was not a valid move, the cost of the move otherwise
	 */
	public int move(int pos) {

		int moveValue = isAvailableMove(pos);
		if (moveValue > 0) {
			undoMoves.push(new int[] { getEmptyPos(), pos });
			swap(getEmptyPos(), pos);

		}
		return moveValue;

	}

	/**
	 * 
	 * @return the length of the board
	 */
	public int getLength() {
		return board.length;
	}

	/**
	 * Determines whether a sphere can be moved to the empty box or not
	 * 
	 * @param pos the position of the sphere to be moved
	 * @return the cost of the move if it is indeed a valid mobe,-1 otherwise
	 */
	public int isAvailableMove(int pos) {
		if (pos < board.length) {
			if (board[pos].contentEquals("-")) {
				return 0;
			} else {
				int furtherPos = Math.max(getEmptyPos(), pos);
				int closerPos = Math.min(getEmptyPos(), pos);
				int moveValue = furtherPos - closerPos;
				if (moveValue <= (board.length - 1) / 2) {
					return moveValue;
				} else
					return -1;
			}
		} else
			return -1;

	}

	/**
	 * Returns a deep copy of the state of the board
	 * 
	 * @return A string array representing the current state of the board
	 */
	public String[] getState() {

		return Arrays.copyOf(board, board.length);
	}

	/**
	 * Reverts the last move. If there is no move to be reverted this method has no
	 * effect.
	 * 
	 */
	public void undoMove() {
		if (!undoMoves.isEmpty()) {
			int[] elements = undoMoves.pop();
			swap(elements[0], elements[1]);
		}

	}

	/**
	 * Returns the stack of the moves that can be undone. Only used by the
	 * {@link #SphereGame(SphereGame) copy constructor}.
	 * 
	 * @return
	 */
	private ArrayDeque<int[]> getUndoMoves() {
		return undoMoves.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
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
		SphereGame other = (SphereGame) obj;
		if (!Arrays.equals(board, other.board))
			return false;

		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Arrays.stream(board).forEach(sb::append);
		return sb.toString();
	}

	/**
	 * Initializes a random new board.
	 */
	private void initializeBoard() {
		do {
			// create a Random instance
			Random rand = new Random();

			// a set to hold the white sphere positions
			Set<Integer> whitePos = new HashSet<>();

			// the amount of white sphere is the total (length-1) /2
			int whiteSheresCount = (board.length - 1) / 2;

			// up to whiteSpheres count
			for (int i = 0; i < whiteSheresCount; i++) {

				// roll a random pos withing the board bounds
				int pos = rand.nextInt(board.length);

				// if this pos is already rolled
				while (whitePos.contains(pos)) {
					// roll again
					pos = rand.nextInt(board.length);
				}

				// add the pos to the white position set
				whitePos.add(pos);
			}

			// find the pos to set the "empth box"
			int emptyPos = rand.nextInt(board.length);

			// if this pos is taken by one of the white spheres
			while (whitePos.contains(emptyPos)) {

				// roll again
				emptyPos = rand.nextInt(board.length);
			}

			// for every pos of the board
			for (int i = 0; i < board.length; i++) {

				// if its a white pos
				if (whitePos.contains(i)) {

					// add A
					board[i] = "A";
				} else {

					// else add M
					// this will also add an M to the empty spot
					board[i] = "M";
				}
			}

			// place "-" for the empty box
			board[emptyPos] = "-";
			
			// if the game is in an end-game state re-Initiliaze it.
		} while (isOver()); 

	}

	/**
	 * 
	 * @return an int representing the pos of the empty box
	 */
	public int getEmptyPos() {
		for (int i = 0; i < board.length; i++) {
			if (board[i].contentEquals("-"))
				return i;
		}
		return -1;
	}

	/**
	 * Swaps the two "contentes of the boxes".
	 * Used to move the selected sphere to the empty box
	 * @param i the first box
	 * @param j the second box
	 */
	private void swap(int i, int j) {
		String temp = board[i];
		board[i] = board[j];
		board[j] = temp;
	}

	// TEST PURPOSES. DO NOT USE
	public void setExactState(String[] s) {
		for (int i = 0; i < s.length; i++) {
			board[i] = s[i];
		}
	}

}
