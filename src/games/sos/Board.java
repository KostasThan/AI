package games.sos;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * This class has all the methods to realize the SOS 3 by 3 game
 * 
 * @author Kostas
 *
 */
public class Board {

	// starting game values
	public static final String EMPTY = "-";
	private static final String[][] STARTING_BOARD = { { EMPTY, EMPTY, EMPTY }, { "O", EMPTY, "-" },
			{ EMPTY, EMPTY, EMPTY } };

	private String[][] board;
	private Deque<Move> movesMade = new ArrayDeque<>();

	/**
	 * Initializes a new board with starting state the STARTING_BOARD state
	 */
	public Board() {
		this.board = STARTING_BOARD;
	}


	/**
	 *  Initializes a new board with starting state the state at which the gives board is. Pretty muic
	 * @param board the board to copy the state from
	 */
	public Board(Board board) {
		this.board = board.getState();
	}

	/**
	 * Makes a move according the parametres given. The letter given must be one of
	 * {"S","O"}.
	 * 
	 * @param x      the column in which the letter will be placed
	 * @param y      the row in which the letter will be placed
	 * @param letter the letter to place
	 * @return whether or not the move was appropriate and  correctly placed
	 */
	public boolean setMove(int x, int y, String letter) {
		Move move = new Move(x, y, letter);
		return move != null ? makeMove(move) : false;

	}

	/**
	 * Given a Move instance will place the move if its not null and not in an
	 * invalid location.
	 * 
	 * @param move the move to be set
	 * @return whether or not the move was appropriate and  correctly placed
	 */
	public boolean setMove(Move move) {
		return move != null ? makeMove(move) : false;
	}

	
	
	/**
	 * Reverses the last move placed.<br>
	 * Returns false if there was not a move to be reversed,in other words the board
	 * is in the initial state
	 * 
	 * @return whether or not there was a move was reversed.
	 */
	public boolean undoMove() {
		if (movesMade.size() > 0) {
			Move move = movesMade.pop();
			board[move.getX()][move.getY()] = Board.EMPTY;
			return true;
		} else
			return false;
	}

	
	
	/**
	 * Checks whether or not there is a winner.
	 * 
	 * @return True if there is a winner. False otherwise
	 */
	public boolean hasWinner() {
		if (board[0][0].contentEquals("S") & board[0][1].contentEquals("O") & board[0][2].contentEquals("S")) {
			return true;
		} else if (board[1][0].contentEquals("S") & board[1][1].contentEquals("O") & board[1][2].contentEquals("S")) {
			return true;
		} else if (board[2][0].contentEquals("S") & board[2][1].contentEquals("O") & board[2][2].contentEquals("S")) {
			return true;
		} else if (board[0][0].contentEquals("S") & board[1][0].contentEquals("O") & board[2][0].contentEquals("S")) {
			return true;
		} else if (board[0][1].contentEquals("S") & board[1][1].contentEquals("O") & board[2][1].contentEquals("S")) {
			return true;
		} else if (board[0][2].contentEquals("S") & board[1][2].contentEquals("O") & board[2][2].contentEquals("S")) {
			return true;
		} else if (board[0][0].contentEquals("S") & board[1][1].contentEquals("O") & board[2][2].contentEquals("S")) {
			return true;
		} else if (board[0][2].contentEquals("S") & board[1][1].contentEquals("O") & board[2][0].contentEquals("S")) {
			return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * Checks all the buttons for an empty spot. Note that there might be an empty spot but there is already a winner<br>
	 * This method only checks if there is an empty spot,not of the game should continue or not.
	 * @return	whether or not there is an empty spot for a move to be placed
	 */
	public boolean hasNextMove() {
		return Arrays.stream(board).anyMatch((b) -> Arrays.stream(b).anyMatch((s) -> s.contentEquals(EMPTY)));
	}


	
	/**
	 * Returns a deep copy String[][] that displays the current state game. The array is not backed by the original state array.
	 * @return A replica of the current board state
	 */
	public String[][] getState() {
		return deepCopy(board);
	}

	
	
	
	/**
	 * Reset's the game to it's initial state
	 */
	public void reset() {
		board = new String[][] { { EMPTY, EMPTY, EMPTY }, { "O", EMPTY, "-" }, { EMPTY, EMPTY, EMPTY } };
	}

	
	
	
	/**
	 * Returns the colums of the board. This should alwasys return the same value as the 
	 * {@link  games.sos.Board#getXDimension() getYDimension} as the game internal String[][] represens a rectangle
	 * @return 
	 */
	public int getXDimension() {
		return board.length;
	}

	
	
	
	/**
	 * Returns the rows of the board. This should always return the same value as the 
	 * {@link  games.sos.Board#getXDimension() getXDimension} as the game internal String[][] represens a rectangle
	 * @return 
	 */
	public int getYDimension() {
		return board[0].length;
	}

	
	
	
	/**
	 * Returns a String represantation of the current state
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String[] rows : board) {
			Arrays.stream(rows).forEach(sb::append);
			sb.append("\n");
		}
		return sb.toString();
	}

	
	/**
	 * Retuns true iff the specified move is valid move. A valid move is a move that is withing it's bounds
	 * and does not override an already placed letter
	 * @param move The move to be checked
	 * @return whether or not the move is valid
	 */
	public boolean isValidMove(Move move) {

		int x = move.getX();
		int y = move.getY();
		String letter = move.getLetter();
		if (isInsideRange(x, y)) {
			return isNotTaken(x, y) && isValidLetter(letter);
		} else
			return false;
	}

	
	
	/**
	 * Return's whether or not the X,Y "coordinates" are within the game's bound
	 * @param x the column
	 * @param y the row
	 * @return whether the x,y "point" to an in-bounds spot
	 */
	private boolean isInsideRange(int x, int y) {
		return x < board.length && y < board[x].length && x >= 0 && y >= 0 ? true : false;
	}

	
	/**
	 * Return's whether or not the the X,Y "coordinates" point an empty spot. Will throw IllegalOutOfBoundsException
	 * if the paramatres are outside the game's bounds.
	 * @param x the column
	 * @param y the row
	 * @return whether or not x,y "point" to an empty spot.
	 */
	private boolean isNotTaken(int x, int y) {

		return board[x][y].contentEquals(EMPTY);
	}

	
	/**
	 * Return whether or not the specified letter is a valid one. Valid letters are {"S,"O"}
	 * @param letter the letter to be checked
	 * @return whether or not the letter is valid
	 */
	private boolean isValidLetter(String letter) {
		return "S".equals(letter) || "O".equals(letter);
	}

	
	/**
	 * Return a deep copy of a 2D matrix. Used to return a deep copy of the current game 2D game-representing array instead of the original one.
	 * @param <T> the type of the array
	 * @param matrix the 2D array to be copied
	 * @return a deep copy of the given 2d array
	 */
	private <T> T[][] deepCopy(T[][] matrix) {
		return Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
	}

	
	
	/**
	 * Place the move if it's valid. A valid move means that the methods
	 * <li> {@link  games.sos.Board#isValidLetter(letter) isValidLetter(String letter)}</li>
	 * <li> {@link  games.sos.Board#isNotTaken(x, y) isNotTaken(int x,int y)}</li>
	 * <li> {@link  games.sos.Board#isInsideRange(x, y) isInsideRange(int x,int y)}</li>
	 * return true when the parametres passed onto them are the speciefed move's ones.
	 * @param move the move to be placed
	 * @return whether or not the move was placed
	 */
	private boolean makeMove(Move move) {
		if (isValidMove(move)) {
			board[move.getX()][move.getY()] = move.getLetter();
			movesMade.push(move);
			return true;
		} else
			return false;

	}

}
