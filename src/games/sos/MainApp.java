package games.sos;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
   
public class MainApp extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4114195362174413809L;
	private JButton button00;
	private JButton button01;
	private JButton button02;
	private JButton button10;
	private JButton button11;
	private JButton button12;
	private JButton button20;
	private JButton button21;
	private JButton button22;
	List<JButton> buttons = new LinkedList<>();

	private JPanel gamePanel;
	private JPanel buttonsPanel;
	private JButton endTurnButton;

	private JButton lastButton;

	private boolean hasSelected = false;
	private JButton resetButton;
	private Board board;
	private Move move = null;
	private boolean botTurn;
	private SosBot bot;
	private ShowBoard show;
	private JButton minmax;
	private int botWins = 0;
	private int playerWins = 0;
	private int draws = 0;
	private JPanel mainAppPanel;
	private JPanel homePanel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton toGamePanelButton;
	private boolean botFirst;
	private JCheckBox playerCheckBox;
	private JCheckBox botCheckBox;
	private JLayeredPane layeredPane;
	private JButton startGameButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp frame = new MainApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainApp() {
		setTitle("SOS GAME!");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 506, 442);
		getContentPane().setLayout(null);

		gamePanel = new JPanel();
		gamePanel.setBounds(0, 0, 500, 363);

		gamePanel.setLayout(new GridLayout(3, 3));

		button00 = new JButton("");
		button00.setFont(new Font("Tahoma", Font.PLAIN, 50));
		button00.setName("00");
		gamePanel.add(button00);
		buttons.add(button00);

		button01 = new JButton("");
		button01.setFont(new Font("Tahoma", Font.PLAIN, 50));
		button01.setName("01");
		gamePanel.add(button01);
		buttons.add(button01);

		button02 = new JButton("");
		button02.setFont(new Font("Tahoma", Font.PLAIN, 50));
		button02.setName("02");
		gamePanel.add(button02);
		buttons.add(button02);

		button10 = new JButton("");
		button10.setFont(new Font("Tahoma", Font.PLAIN, 50));
		button10.setName("10");
		gamePanel.add(button10);
		buttons.add(button10);

		button11 = new JButton("");
		button11.setFont(new Font("Tahoma", Font.PLAIN, 50));
		button11.setName("11");
		gamePanel.add(button11);
		buttons.add(button11);

		button12 = new JButton("");
		button12.setFont(new Font("Tahoma", Font.PLAIN, 50));
		button12.setName("12");
		gamePanel.add(button12);
		buttons.add(button12);

		button20 = new JButton("");
		button20.setFont(new Font("Tahoma", Font.PLAIN, 50));
		button20.setName("20");
		gamePanel.add(button20);
		buttons.add(button20);

		button21 = new JButton("");
		button21.setFont(new Font("Tahoma", Font.PLAIN, 50));
		button21.setName("21");
		gamePanel.add(button21);
		buttons.add(button21);

		button22 = new JButton();
		button22.setFont(new Font("Tahoma", Font.PLAIN, 50));
		button22.setName("22");
		gamePanel.add(button22);
		buttons.add(button22);

		buttonsPanel = new JPanel();

		buttonsPanel.setLayout(null);

		endTurnButton = new JButton("End Turn");

		endTurnButton.setBounds(390, 11, 89, 23);
		buttonsPanel.add(endTurnButton);

		resetButton = new JButton("Reset");

		resetButton.setBounds(10, 11, 89, 23);
		buttonsPanel.add(resetButton);

		minmax = new JButton("Play my Turn");

		minmax.setBounds(253, 11, 115, 23);
		buttonsPanel.add(minmax);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 500, 413);
		getContentPane().add(layeredPane);

		mainAppPanel = new JPanel();
		layeredPane.setLayer(mainAppPanel, 1);
		mainAppPanel.setBounds(0, 0, 501, 412);

		mainAppPanel.setLayout(null);

		board = new Board();
		bot = new SosBot(board);

		mainAppPanel.add(gamePanel);
		mainAppPanel.add(buttonsPanel);
		layeredPane.setVisible(true);
		buttonsPanel.setBounds(0, 361, 500, 51);

		startGameButton = new JButton("Start Game");

		startGameButton.setBounds(122, 11, 99, 23);
		buttonsPanel.add(startGameButton);

		homePanel = new JPanel();
		layeredPane.setLayer(homePanel, 2);
		homePanel.setBounds(0, 0, 501, 412);
		layeredPane.add(homePanel);
		homePanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("WELCOME!");
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 28));
		lblNewLabel.setBounds(188, 35, 112, 68);
		homePanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Who will play first?");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 133, 125, 30);
		homePanel.add(lblNewLabel_1);

		playerCheckBox = new JCheckBox("Me of course!");
		buttonGroup.add(playerCheckBox);
		playerCheckBox.setBounds(135, 139, 125, 23);
		homePanel.add(playerCheckBox);

		botCheckBox = new JCheckBox("Let the bot play first...");
		botCheckBox.setSelected(true);
		buttonGroup.add(botCheckBox);
		botCheckBox.setBounds(135, 171, 179, 23);
		homePanel.add(botCheckBox);

		toGamePanelButton = new JButton("Start!");

		toGamePanelButton.setBounds(389, 329, 89, 23);
		homePanel.add(toGamePanelButton);

		createEvents();
	}

	private void createEvents() {
		show = new ShowBoard(this::getBoard, this::getButtons);
		showBoard();
		
		//add a new move logic
		buttons.forEach((b) -> b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//if there is another empty spot left
				if (board.hasNextMove()) {
					JButton selectedButton = (JButton) e.getSource();

					//if the user has already selected a move
					if (hasSelected) {
						
						//and the last button is not the same
						if (lastButton != selectedButton) {
							
							//if the new button selected is empty
							if (selectedButton.getText().contentEquals(Board.EMPTY)) {
								//reset the previous button to empty
								lastButton.setText(Board.EMPTY);
								lastButton = selectedButton;
								lastButton.setText("S");
								
								//set the new button to s
								move = MoveFactory.getMove(lastButton, "S");

							}
							
							//if he clicked the same button again
						} else if (lastButton == selectedButton) {

							//advance "S" to "O", "O" to "S" etc..
							if (selectedButton.getText().equals("S")) {
								selectedButton.setText("O");
								move = MoveFactory.getMove(lastButton, "O");
							} else if (selectedButton.getText().equals("O")) {
								selectedButton.setText(Board.EMPTY);
								move = null;
							} else if (selectedButton.getText().equals(Board.EMPTY)) {
								selectedButton.setText("S");
								move = MoveFactory.getMove(lastButton, "S");
							}
						}

						//if he hasn't selectected anything and the selected button is empty
					} else if (selectedButton.getText().equals(Board.EMPTY)) {
						//set it to "S"
						hasSelected = true;
						lastButton = selectedButton;
						lastButton.setText("S");
						move = MoveFactory.getMove(lastButton, "S");
					}

				}
			}
		}));

		endTurnButton.addActionListener(new ActionListener() {

			//end turn logic
			@Override
			public void actionPerformed(ActionEvent e) {
				// change he window title if need be
				setWindowTitle();

				// player's turn
				if (!botTurn) {

					//if the playered didn't chose any move
					if (!playerHasPlayed()) {
						JOptionPane.showMessageDialog(null, "Please select an appropiate move");
					} else {
						
						//else if the game is over
						if (hasEnded()) {
							
							//reset the game
							resetButton.doClick();
						}
						botTurn = true;
					}
				}
				
				// bot turn
				if (botTurn) {

					setTitle("Bot is figuring out his move..");
					botPlay();

					//if the game ended
					if (hasEnded()) {
						resetButton.doClick();
					}
					
					//reset variable like hasSelected etc..
					resetPlayerVariables();
					botTurn = false;
				}
				
				//update window title
				setWindowTitle();
				
				//update board
				showBoard();
			}
		});

		
		//reset logic
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//reset the board
				board.reset();
				
				//reset players variable
				resetPlayerVariables();
				
				//select the first player in regards to users choice at the first screen
				botTurn = botFirst;
				
				//update the window title
				setWindowTitle();
				
				//update board
				showBoard();
				
			
				//update buttons' visibility in respect to the first player
				startGameButton.setVisible(true);
				startGameButton.setEnabled(true);
				if (botTurn) {
					startGameButton.setVisible(false);
					startGameButton.setEnabled(false);
					endTurnButton.doClick();
				}
				
				

			}
		});

		
		//logic behind updating the gui/variables when bot is playing
		minmax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//reset the player's variables
				resetPlayerVariables();

				//update the gui
				setTitle("Bot is figuring out his move..");
				
				//let the bot play
				botPlay();
				
				//update the board
				showBoard();

				//change the player(it might be bot's turn again if the user pressed play my turn button)
				botTurn = !botTurn;
				
				//update title
				setWindowTitle();
				
				//end this turn
				endTurnButton.doClick();
			}
		});

		
		//logic behind initializing a game
		toGamePanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//set first player
				botFirst = botCheckBox.isSelected() ? true : false;
				botTurn = botFirst;
				
				//update window title
				setWindowTitle();
				
				//set the game panel
				layeredPane.removeAll();
				layeredPane.add(mainAppPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				//disable the respective components
				enableComponents(false);
			
				

			}
		});

		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//enable-disable the appropriate buttons
				endTurnButton.setEnabled(true);
		
				startGameButton.setVisible(false);
				startGameButton.setEnabled(false);
			
				if (botTurn) {
					endTurnButton.doClick();
				}
				
				//re-enable the components if its the players turn or the bot has already played
				enableComponents(true);
			

			}
		});
	}

	public LinkedList<JButton> getButtons() {
		//a list with all the in-game 
		return (LinkedList<JButton>) buttons;
	}

	public Board getBoard() {
		
		//the board which displays the game
		return board;
	}

	private void botPlay() {

		//logic behind bot's move 
		bot.startMinimax();
		
		//update the board
		showBoard();

	}

	private boolean playerHasPlayed() {
		// if a move is selected and its correctly placed
		if (move != null && board.setMove(move)) {
			
			//update the gui
			showBoard();
			
			//reset the variables
			resetPlayerVariables();
			return true;
		} else {
	
			//player hasn't placed an appropriate move
			return false;
		}

	}

	private void resetPlayerVariables() {
		//variables used to keep track of the players actions each turn
		move = null;
		lastButton = null;
		hasSelected = false;
	}

	
	//updates the window title in respect to the current player
	private void setWindowTitle() {
	
		String player = botTurn ? "Bot " : "Player ";
		setTitle(
				player + "is playing..   || Score ||  Player: " + playerWins + " Bot: " + botWins + " Draws: " + draws);
	}

	
	//checks whether there is another move to be made
	private boolean hasEnded() {
		
		//if there is a winned
		if (board.hasWinner()) {
			//show the win-lose message
			
			if (!botTurn) {
				showWinMessage();
				playerWins++;
				
			} else {
				showLoseMessage();
				botWins++;
			}
			
			//and return that there is no other move to be made
			return true;
			
			//else if the board is full
		} else if (!board.hasNextMove()) {
			
			//draw message
			showDrawMessage();
			
			//update draws
			draws++;
			
			//return that there is no other move to be made
			return true;
		}
		
		//if none of this is true the board has another move
		return false;

	}
	
	
	
	//enable-disable the in-game buttons
	private void enableComponents(boolean enable) {
		buttons.stream().forEach((b) -> b.setEnabled(enable));
		endTurnButton.setEnabled(enable);
		resetButton.setEnabled(enable);
		minmax.setEnabled(enable);
	}

	
	//messages
	private void showWinMessage() {
		JOptionPane.showMessageDialog(null, "Congratulations!! You won!");
	}

	private void showLoseMessage() {
		JOptionPane.showMessageDialog(null, "Sorry.. You lost!");
	}

	private void showDrawMessage() {
		JOptionPane.showMessageDialog(null, "Draw Game..");
	}

	private void showBoard() {
		show.actionPerformed(null);
	}


}
