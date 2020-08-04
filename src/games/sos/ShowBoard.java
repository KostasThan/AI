package games.sos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.function.Supplier;

import javax.swing.JButton;

/**
 * A class extending {@link ActionListener} that given a Supplier<{@link Board}>
 * and {@link Supplier}<{@link LinkedList}<{@link JButton}>> will represent the
 * state of the {@link Board} on the {@link JButton} list.
 *
 */
public class ShowBoard implements ActionListener {
	Supplier<Board> boardSupplier;
	Supplier<LinkedList<JButton>> buttonListSupplier;

	
	
	/**
	 * Creates a ShowBoard instance.
	 * @param boardSupplier the board supplier which will provide the state
	 * @param buttonListSupplier the buttons which will show the stae
	 */
	public ShowBoard(Supplier<Board> boardSupplier, Supplier<LinkedList<JButton>> buttonListSupplier) {
		this.boardSupplier = boardSupplier;
		this.buttonListSupplier = buttonListSupplier;
	}

	
	
	/**
	 * Upon execution will update all the {@link JButton} text to represent the {@link Board} state.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		Board board = boardSupplier.get();
		String[][] boardState = board.getState();
		LinkedList<JButton> buttonList = buttonListSupplier.get();
		int count = 0;

		for (int i = 0; i < boardState.length; i++) {
			for (int j = 0; j < boardState[i].length; j++) {

				buttonList.get(count++).setText(boardState[i][j]);
			}
		}

	}

}
