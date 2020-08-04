package games.spheregame.view;

import java.awt.Color;

import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

import games.spheregame.model.SphereGame;


/**
 * This class highlights the text pane with 2 colors representing whether a move is valid,invadlid or... pointless.
 * @author Kostas
 *
 */
public class PrettyScrollbar extends JSlider {

	private JTextPane textPane;
	private SphereGame game;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5622013033976347020L;

	public PrettyScrollbar(JTextPane textPane, SphereGame game) {
		this.textPane = textPane;
		super.setMinorTickSpacing(1);
		super.setMajorTickSpacing(1);
		super.setMinimum(1);
		super.setPaintTicks(true);
		super.setPaintTrack(false);
		super.setBorder(new LineBorder(new Color(0, 0, 0)));
		super.setBackground(Color.WHITE);
		super.setSnapToTicks(true);
		this.game = game;
		refreshLabels();

	

	}

	public void setGame(SphereGame game) {
		this.game = game;
	}

	public void changeSelectedText(int pos) {
		
		if (game != null) {
			textPane.requestFocusInWindow();
			textPane.select(pos - 1, pos);
	
			int moveValue = game.isAvailableMove(pos - 1);

			if (moveValue > 0) {
				textPane.setSelectionColor(Color.BLUE);
	
			} else if (moveValue == 0) {
				textPane.setSelectionColor(Color.GREEN);
			} else {
				textPane.setSelectionColor(Color.RED);
			}

		}
		
	

	}

	public void refreshLabels() {
		int max = textPane.getText().length();
		if (max > 25) {
			super.setPaintLabels(false);
		} else {
			super.setPaintLabels(true);
		}
	
		super.setMaximum(max);
		super.setValue(Math.floorDiv(max, 2));

	}


	

}



