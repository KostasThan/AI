package games.sos;

import javax.swing.JButton;

public class MoveFactory {

	/**
	 * Given a {@link JButton} and a {@link String} will return the move represented
	 * from those instances. <br>
	 * <li>The button name must be in the format 00 for first row first column</li>
	 * <li>01 for first row second column etc..</li>
	 * 
	 * @param button to extract the x,y "coordinates" from
	 * @param letter the letter to be placed at the x,y coordinates
	 * @return a {@link Move} instance
	 */
	public static Move getMove(JButton button, String letter) {
		int x = Integer.parseInt(button.getName().substring(0, 1));
		int y = Integer.parseInt(button.getName().substring(1));

		return new Move(x, y, letter);

	}
}
