package games.sos;

/**
 * Move class is a class that stores the three neccesary variables for a
 * {@link Board} game move.<br>
 * The row,column and letter.
 * 
 */

public class Move {
	private int x;
	private int y;
	private String letter;

	/**
	 * Instantiates a move.
	 * 
	 * @param x      the column
	 * @param y      the row
	 * @param letter to be place at the x,y "coordinates"
	 */
	public Move(int x, int y, String letter) {
		this.x = x;
		this.y = y;
		this.letter = letter;
	}

	/**
	 * 
	 * @return the column of the move
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return the row of the move
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return the letter of the move
	 */
	public String getLetter() {
		return letter;
	}

}
