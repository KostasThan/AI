package games.spheregame.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import games.spheregame.model.SphereGame;
import games.spheregame.searchalgorithms.AStarAlgorithm;
import games.spheregame.searchalgorithms.SphereGameSearch;
import games.spheregame.searchalgorithms.UCSAlgorithm;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ImageIcon;

//a comment
/**
 * This class provides the GUI
 * @author Kostas
 *
 */
public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3237662686851785517L;

	private JPanel contentPane;
	private JPanel welcomePanel;
	private JButton toMainGameButton;
	private JLayeredPane layeredPane;
	private JPanel gamePanel;
	private JTextPane gameTextPane;
	private PrettyScrollbar chooseLetterSlider;
	private JPanel newGamePanel;
	private JLabel newGameLabel;
	private JButton newGameButton;
	private SphereGame game;
	private JButton moveButton;
	private JLabel totalCostLabel;
	private JLabel totalMovesLabel;
	private JLabel totalCost;
	private JLabel totalMoves;
	private JPanel scoresPanel;
	private JPanel totalScoresPanel;
	private JLabel totalWinsLabel;
	private JLabel totalGiveUpsLabel;
	private JLabel totalDoneItBetter;
	private JLabel totalWins;
	private JLabel totalCouldHaveDoneItBetter;
	private JLabel totalGiveUps;
	private ArrayDeque<Integer> movesCosts = new ArrayDeque<>();
	private JButton undoButton;
	private JLabel chooseLengthLabel;
	private JSpinner difficultySpinner;
	private JPanel wannaKnowPanel;
	private JCheckBox pathCheckBox;
	private JCheckBox finalStateCheckBox;
	private JButton learnButton;
	private SphereGameSearch ucsSearch;
	private SphereGameSearch aStarSearch;
	private UCSAlgorithm ucsAlgorithm = new UCSAlgorithm();
	private AStarAlgorithm aStarAlgorithm = new AStarAlgorithm();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel pathLabel;
	private JCheckBox aStarCheckBox;
	private JCheckBox ucsCheckBox;
	private JLabel algorithmLabel;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private AtomicInteger gameCost = new AtomicInteger(-1);

	private FindSolutionRunnable findSolutionRunnable;
	private JLabel scoreToMatchLabel;
	private JLabel scoreToMatch;

	private ExecutorService taskExecutor;

	private AtomicBoolean costIsSet = new AtomicBoolean(false);
	private JPanel controlsPanel;
	private JButton iSaidGoButton;
	private JTextArea txtrEveryTimeYou;
	private JLabel lblNewLabel;
	private JTextPane txtpnTheMoreSpots;
	private JTextPane txtpnSelectAnAppropriate;
	private JTextPane txtpnAfterALetter;
	private JTextPane txtpnUseTheMove;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_9;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
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
	public MainView() {
		setResizable(false);
		initComponents();
		createEvents();
	}

	private void initComponents() {
		ucsSearch = new SphereGameSearch(game, ucsAlgorithm, true);
		aStarSearch = new SphereGameSearch(game, aStarAlgorithm, true);
		findSolutionRunnable = new FindSolutionRunnable(game, gameCost);
		taskExecutor = Executors.newSingleThreadExecutor();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle("Welcome to the Sphere Game!");

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 517, 393);
		contentPane.add(layeredPane);

		welcomePanel = new JPanel();
		layeredPane.setLayer(welcomePanel, 3);
		welcomePanel.setBackground(new Color(0, 0, 0));
		welcomePanel.setBounds(0, 0, 518, 396);
		layeredPane.add(welcomePanel);
		welcomePanel.setLayout(null);

		JLabel welcomeLabel = new JLabel("Welcome");
		welcomeLabel.setForeground(new Color(178, 34, 34));
		welcomeLabel.setFont(new Font("Cooper Black", Font.BOLD, 26));
		welcomeLabel.setBounds(166, 31, 143, 52);
		welcomePanel.add(welcomeLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 94, 498, 179);
		welcomePanel.add(scrollPane);
		scrollPane.requestFocusInWindow();

		JTextArea aboutGameTextArea = new JTextArea();
		aboutGameTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		aboutGameTextArea.setBackground(SystemColor.controlHighlight);
		aboutGameTextArea.setLineWrap(true);
		aboutGameTextArea.setWrapStyleWord(true);
		aboutGameTextArea.setText("There are 2 * n + 1 boxes.\r\nN is the number designated "
				+ "by the player and must be 1 or greater.\r\nThere are two di"
				+ "fferent spheres with the epigraph of the letter \"M\" or \"A\""
				+ ". \r\r\nLet's say n = 3. There will be 3 M's,3 A's, and one empty "
				+ "spot \"-\". The goal is by moving any letter to the empty spot you en"
				+ "d up with all the M's before the A's.\r\r\nTwo catches:\r\r\n    +Empty s"
				+ "pot cannot be on the far right spot \t\tfor you to win.\r\r\n    +Any sphere can "
				+ "\"travel\" up to the total amount \t\tof the same letter spheres.\r\r\nIn our case "
				+ "any sphere can move up to 3 spots. \r\r\nEnd the game with the least moves possible.");
		aboutGameTextArea.setEditable(false);

		scrollPane.setViewportView(aboutGameTextArea);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scrollPane.getVerticalScrollBar().setValue(0);
			}
		});

		toMainGameButton = new JButton("GO!");
		toMainGameButton.setFont(new Font("Ravie", Font.PLAIN, 14));

		toMainGameButton.setBounds(206, 302, 118, 40);
		welcomePanel.add(toMainGameButton);

		gamePanel = new JPanel();
		gamePanel.setForeground(new Color(255, 255, 255));
		gamePanel.setBackground(new Color(0, 51, 0));
		layeredPane.setLayer(gamePanel, 1);
		gamePanel.setBounds(0, 0, 518, 396);
		gamePanel.setLayout(null);

		gameTextPane = new JTextPane();
		gameTextPane.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				gameTextPane.requestFocusInWindow();
			}
		});
		gameTextPane.setBackground(SystemColor.controlHighlight);
		gameTextPane.setEditable(false);
		gameTextPane.setText("The game will be displayed here!");
		gameTextPane.setFont(new Font("Lucida Fax", Font.PLAIN, 15));
		gameTextPane.setBounds(0, 47, 518, 59);
		gameTextPane.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				
				e.consume();
				int keyInt = e.getExtendedKeyCode();
				if (keyInt == 38 || keyInt == 39) {
					chooseLetterSlider.setValue(chooseLetterSlider.getValue() + 1);

				} else if (keyInt == 37 || keyInt == 40) {
					chooseLetterSlider.setValue(chooseLetterSlider.getValue() - 1);

				} else if (keyInt == 10 || keyInt == 32) {
					moveButton.doClick();
				} else if (keyInt == 76) {
					learnButton.doClick();
				} else if (keyInt == 78) {
					newGameButton.doClick();
				} else if (keyInt == 85) {
					undoButton.doClick();
				}

			}
		});

		StyledDocument doc = gameTextPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		gamePanel.add(gameTextPane);

		chooseLetterSlider = new PrettyScrollbar(gameTextPane, game);
		chooseLetterSlider.setBounds(10, 128, 385, 47);

		newGamePanel = new JPanel();
		newGamePanel.setBackground(new Color(60, 179, 113));
		newGamePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		newGamePanel.setBounds(10, 308, 201, 77);
		gamePanel.add(newGamePanel);
		newGamePanel.setLayout(null);

		newGameLabel = new JLabel("Want a new Game?");
		newGameLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 15));
		newGameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newGameLabel.setBounds(26, 0, 147, 29);
		newGamePanel.add(newGameLabel);

		newGameButton = new JButton("GO!");
		newGameButton.setFont(new Font("Ravie", Font.PLAIN, 14));
		newGameButton.setBounds(110, 33, 81, 33);
		newGamePanel.add(newGameButton);

		chooseLengthLabel = new JLabel("Difficulty:");
		chooseLengthLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 14));
		chooseLengthLabel.setBounds(10, 33, 59, 33);
		newGamePanel.add(chooseLengthLabel);

		difficultySpinner = new JSpinner();
		difficultySpinner.setBorder(null);

		difficultySpinner.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 14));
		difficultySpinner.setForeground(new Color(0, 0, 0));
		difficultySpinner.setModel(new SpinnerNumberModel(2, 2, 20, 1));
		difficultySpinner.setBounds(66, 35, 37, 29);
		difficultySpinner.getEditor().getComponent(0).setBackground(new Color(60, 179, 113));
		newGamePanel.add(difficultySpinner);

		moveButton = new JButton("<html>Move!</html>");
		moveButton.setBorder(null);
		moveButton.setBackground(new Color(255, 255, 0));

		moveButton.setFont(new Font("Ravie", Font.PLAIN, 14));
		moveButton.setBounds(430, 128, 78, 47);
		gamePanel.add(moveButton);

		scoresPanel = new JPanel();
		scoresPanel.setBackground(new Color(0, 0, 0));
		scoresPanel.setBounds(237, 186, 156, 85);
		gamePanel.add(scoresPanel);
		scoresPanel.setLayout(null);

		totalCostLabel = new JLabel("Total Cost:");
		totalCostLabel.setForeground(new Color(255, 255, 255));
		totalCostLabel.setBounds(10, 0, 78, 33);
		scoresPanel.add(totalCostLabel);
		totalCostLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 15));

		totalMovesLabel = new JLabel("Total Moves:");
		totalMovesLabel.setForeground(new Color(255, 255, 255));
		totalMovesLabel.setBounds(10, 30, 78, 23);
		scoresPanel.add(totalMovesLabel);
		totalMovesLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 15));

		totalCost = new JLabel("0");
		totalCost.setForeground(new Color(255, 255, 255));
		totalCost.setBounds(88, 5, 46, 23);
		scoresPanel.add(totalCost);
		totalCost.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 15));

		totalMoves = new JLabel("0");
		totalMoves.setForeground(new Color(255, 255, 255));
		totalMoves.setBounds(88, 32, 46, 19);
		scoresPanel.add(totalMoves);
		totalMoves.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 15));

		totalScoresPanel = new JPanel();
		totalScoresPanel.setBounds(10, 186, 201, 111);
		gamePanel.add(totalScoresPanel);
		totalScoresPanel.setLayout(null);

		totalWinsLabel = new JLabel("Total wins: ");
		totalWinsLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 15));
		totalWinsLabel.setBounds(10, 11, 66, 14);
		totalScoresPanel.add(totalWinsLabel);

		totalGiveUpsLabel = new JLabel("Give up's: ");
		totalGiveUpsLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 15));
		totalGiveUpsLabel.setBounds(10, 86, 83, 14);
		totalScoresPanel.add(totalGiveUpsLabel);

		totalDoneItBetter = new JLabel("<html>Could have <br>done it better :</html>");
		totalDoneItBetter.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 15));
		totalDoneItBetter.setBounds(10, 25, 94, 50);
		totalScoresPanel.add(totalDoneItBetter);

		totalWins = new JLabel("0");
		totalWins.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 14));
		totalWins.setBounds(103, 11, 46, 14);
		totalScoresPanel.add(totalWins);

		totalCouldHaveDoneItBetter = new JLabel("0");
		totalCouldHaveDoneItBetter.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 14));
		totalCouldHaveDoneItBetter.setBounds(103, 52, 46, 14);
		totalScoresPanel.add(totalCouldHaveDoneItBetter);

		totalGiveUps = new JLabel("0");
		totalGiveUps.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 14));
		totalGiveUps.setBounds(103, 86, 46, 14);
		totalScoresPanel.add(totalGiveUps);

		undoButton = new JButton("Undo");
		undoButton.setBorder(null);
		undoButton.setBackground(new Color(255, 255, 0));
		undoButton.setFont(new Font("Ravie", Font.PLAIN, 14));
		undoButton.setBounds(429, 186, 79, 47);
		gamePanel.add(undoButton);

		wannaKnowPanel = new JPanel();
		wannaKnowPanel.setBounds(237, 274, 271, 111);
		gamePanel.add(wannaKnowPanel);
		wannaKnowPanel.setLayout(null);

		JLabel wantToKnowTheAnswerLabel = new JLabel("Wanna know?");
		wantToKnowTheAnswerLabel.setFont(new Font("Ravie", Font.PLAIN, 14));
		wantToKnowTheAnswerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wantToKnowTheAnswerLabel.setBounds(10, 11, 218, 26);
		wannaKnowPanel.add(wantToKnowTheAnswerLabel);

		pathCheckBox = new JCheckBox("Path");
		pathCheckBox.setSelected(true);
		buttonGroup.add(pathCheckBox);
		pathCheckBox.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 11));
		pathCheckBox.setBounds(10, 59, 47, 23);
		wannaKnowPanel.add(pathCheckBox);

		finalStateCheckBox = new JCheckBox("Final State");
		buttonGroup.add(finalStateCheckBox);
		finalStateCheckBox.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 11));
		finalStateCheckBox.setBounds(10, 87, 71, 23);
		wannaKnowPanel.add(finalStateCheckBox);

		learnButton = new JButton("Learn!");

		learnButton.setFont(new Font("Ravie", Font.PLAIN, 12));
		learnButton.setBounds(179, 85, 85, 23);
		wannaKnowPanel.add(learnButton);

		pathLabel = new JLabel("Path?");
		pathLabel.setFont(new Font("Ravie", Font.PLAIN, 12));
		pathLabel.setBounds(11, 37, 64, 26);
		wannaKnowPanel.add(pathLabel);

		aStarCheckBox = new JCheckBox("A* Search");
		aStarCheckBox.setSelected(true);
		buttonGroup_1.add(aStarCheckBox);
		aStarCheckBox.setFont(new Font("MV Boli", Font.PLAIN, 12));
		aStarCheckBox.setBounds(89, 59, 97, 23);
		wannaKnowPanel.add(aStarCheckBox);

		ucsCheckBox = new JCheckBox("Ucs Search");
		buttonGroup_1.add(ucsCheckBox);
		ucsCheckBox.setFont(new Font("MV Boli", Font.PLAIN, 12));
		ucsCheckBox.setBounds(89, 87, 85, 23);
		wannaKnowPanel.add(ucsCheckBox);

		algorithmLabel = new JLabel("Algorithm?");
		algorithmLabel.setFont(new Font("Ravie", Font.PLAIN, 12));
		algorithmLabel.setBounds(85, 37, 111, 22);
		wannaKnowPanel.add(algorithmLabel);

	
		
		controlsPanel = new JPanel();
		controlsPanel.setBackground(new Color(0, 51, 0));
		layeredPane.setLayer(controlsPanel, 2);
		controlsPanel.setBounds(0, 0, 518, 393);
	
		controlsPanel.setLayout(null);
		
		iSaidGoButton = new JButton("Start Game!");
	
		iSaidGoButton.setFont(new Font("Ravie", Font.PLAIN, 14));
		iSaidGoButton.setBounds(339, 346, 150, 36);
		controlsPanel.add(iSaidGoButton);
		
		txtrEveryTimeYou = new JTextArea();
		txtrEveryTimeYou.setEditable(false);
		txtrEveryTimeYou.setFont(new Font("Cooper Black", Font.PLAIN, 11));
		txtrEveryTimeYou.setText("Every time you want to start a\nnew game press the \"Go\" Button");
		txtrEveryTimeYou.setBounds(20, 11, 184, 36);
		controlsPanel.add(txtrEveryTimeYou);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MainView.class.getResource("/rescources/moveundobuttons.png")));
		lblNewLabel.setBounds(324, 249, 160, 36);
		controlsPanel.add(lblNewLabel);
		
		txtpnTheMoreSpots = new JTextPane();
		txtpnTheMoreSpots.setEditable(false);
		txtpnTheMoreSpots.setFont(new Font("Cooper Black", Font.PLAIN, 11));
		txtpnTheMoreSpots.setText("The more spots a sphere moves the more it costs you. You can keep track of the total moves cost  in the Total Cost label.");
		txtpnTheMoreSpots.setBounds(20, 58, 469, 36);
		controlsPanel.add(txtpnTheMoreSpots);
		
		txtpnSelectAnAppropriate = new JTextPane();
		txtpnSelectAnAppropriate.setEditable(false);
		txtpnSelectAnAppropriate.setText("Select an appropriate move using the scrollbar. ");
		txtpnSelectAnAppropriate.setFont(new Font("Cooper Black", Font.PLAIN, 11));
		txtpnSelectAnAppropriate.setBounds(20, 131, 279, 25);
		controlsPanel.add(txtpnSelectAnAppropriate);
		
		txtpnAfterALetter = new JTextPane();
		txtpnAfterALetter.setEditable(false);
		txtpnAfterALetter.setFont(new Font("Cooper Black", Font.PLAIN, 11));
		txtpnAfterALetter.setText("After a letter is selected it will change color to blue(for a valid move),red (for an invalid move), and green,(if you try to move the empty spot).\r\n");
		txtpnAfterALetter.setBounds(20, 176, 279, 62);
		controlsPanel.add(txtpnAfterALetter);
		
		txtpnUseTheMove = new JTextPane();
		txtpnUseTheMove.setEditable(false);
		txtpnUseTheMove.setFont(new Font("Cooper Black", Font.PLAIN, 11));
		txtpnUseTheMove.setText("Use the move button to move the selected letter or the undo button to undo the last move you made");
		txtpnUseTheMove.setBounds(20, 249, 279, 48);
		controlsPanel.add(txtpnUseTheMove);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(MainView.class.getResource("/rescources/totalcostlabel.png")));
		lblNewLabel_2.setBounds(20, 98, 137, 25);
		controlsPanel.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(MainView.class.getResource("/rescources/slider.png")));
		lblNewLabel_3.setBounds(309, 131, 199, 25);
		controlsPanel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(MainView.class.getResource("/rescources/gamestate.png")));
		lblNewLabel_4.setBounds(324, 188, 143, 29);
		controlsPanel.add(lblNewLabel_4);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(MainView.class.getResource("/rescources/newgamebutton.png")));
		lblNewLabel_1.setBounds(250, 8, 199, 48);
		controlsPanel.add(lblNewLabel_1);
		
		lblNewLabel_5 = new JLabel("ShortCuts");
		lblNewLabel_5.setForeground(new Color(255, 255, 255));
		lblNewLabel_5.setFont(new Font("Cooper Black", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(20, 308, 64, 25);
		controlsPanel.add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel("New game: N");
		lblNewLabel_6.setForeground(new Color(255, 255, 255));
		lblNewLabel_6.setBounds(20, 328, 87, 20);
		controlsPanel.add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel("Make move: Enter or Spacebar");
		lblNewLabel_7.setForeground(new Color(255, 255, 255));
		lblNewLabel_7.setBounds(117, 328, 150, 20);
		controlsPanel.add(lblNewLabel_7);
		
		lblNewLabel_8 = new JLabel("Undo Move: U");
		lblNewLabel_8.setForeground(new Color(255, 255, 255));
		lblNewLabel_8.setBounds(20, 364, 87, 20);
		controlsPanel.add(lblNewLabel_8);
		
		lblNewLabel_9 = new JLabel("Learn: L");
		lblNewLabel_9.setForeground(new Color(255, 255, 255));
		lblNewLabel_9.setBounds(20, 346, 87, 20);
		controlsPanel.add(lblNewLabel_9);
		
		layeredPane.add(controlsPanel);
		layeredPane.add(gamePanel);
		
				scoreToMatchLabel = new JLabel("Score to match:");
				scoreToMatchLabel.setBounds(10, 20, 164, 17);
				gamePanel.add(scoreToMatchLabel);
				scoreToMatchLabel.setForeground(new Color(255, 255, 255));
				scoreToMatchLabel.setFont(new Font("Ravie", Font.PLAIN, 15));
				
						scoreToMatch = new JLabel("0");
						scoreToMatch.setBounds(184, 18, 313, 20);
						gamePanel.add(scoreToMatch);
						scoreToMatch.setForeground(new Color(255, 255, 255));
						scoreToMatch.setFont(new Font("Ravie", Font.PLAIN, 15));

	}

	private void createEvents() {

		toMainGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(controlsPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		
		iSaidGoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(gamePanel);
				layeredPane.repaint();
				layeredPane.revalidate();
				gameTextPane.requestFocusInWindow();
			}
		});

		chooseLetterSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				chooseLetterSlider.changeSelectedText(((JSlider) e.getSource()).getValue());

			}
		});

		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (game != null) {
					evaluateEndGame();
					terminateGame();
				}
				initializeNewGame();
			}

		});

		moveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (game != null) {

					int moveLetter = chooseLetterSlider.getValue() - 1;
					int moveValue = game.move(moveLetter);
					if (moveValue > 0) {
						addToLabel(totalMoves, 1);
						addToLabel(totalCost, moveValue);
						movesCosts.push(moveValue);
						gameTextPane.setText(game.toString());
						if (game.isOver()) {
							evaluateEndGame();
							terminateGame();

						} else {
							refreshSelectedText();
						}
					} else if (moveValue == 0) {
						showPointlessMoveMessage();

					} else {
						showRulesMessage();

					}
				} else {
					showStartNewGameMessage();
				}

			}

		});

		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game != null && !movesCosts.isEmpty()) {
					game.undoMove();
					addToLabel(totalCost, -movesCosts.pop());
					addToLabel(totalMoves, -1);
					gameTextPane.setText(game.toString());
					refreshSelectedText();
				}
			}

		});

		learnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game != null) {
					if (ucsCheckBox.isSelected()) {
						ucsSearch.editPreferences(pathCheckBox.isSelected());
						ucsSearch.findSolution(true);
					} else if (aStarCheckBox.isSelected()) {
						aStarSearch.editPreferences(pathCheckBox.isSelected());
						aStarSearch.findSolution(true);
					}
				}
			}
		});

	}

	private void addToLabel(JLabel label, int addAmount) {
		int previousValue = getValueFromLabel(label);
		label.setText("" + (previousValue + addAmount));

	}

	private void setLabelToZero(JLabel label) {
		label.setText("0");
	}

	private void addLetterSlider(boolean enable) {
		if (enable) {
			gamePanel.add(chooseLetterSlider);

		} else {
			gamePanel.remove(chooseLetterSlider);
		}
		gamePanel.repaint();
		gamePanel.revalidate();
	}

	private void initializeNewGame() {
		game = new SphereGame((Integer) difficultySpinner.getValue());
		ucsSearch.setStartingGame(game);
		aStarSearch.setStartingGame(game);
		gameTextPane.setText(game.toString());
		addLetterSlider(true);
		chooseLetterSlider.setGame(game);
		chooseLetterSlider.refreshLabels();
		chooseLetterSlider.setValue(game.getEmptyPos() + 1);
		getGuess();

	}

	private void terminateGame() {

		game = null;
		ucsSearch.setStartingGame(null);
		aStarSearch.setStartingGame(null);
		chooseLetterSlider.setGame(null);
		setLabelToZero(totalCost);
		setLabelToZero(totalMoves);
		setLabelToZero(scoreToMatch);
		movesCosts.clear();
		gameTextPane.setText("Start a new Game!");
		gameCost.set(-1);
		addLetterSlider(false);
		costIsSet.set(false);
	}

	private void getGuess() {

		costIsSet.set(false);
		taskExecutor.shutdownNow();
		taskExecutor = Executors.newSingleThreadExecutor();
		findSolutionRunnable.setGame(game);
		scoreToMatch.setText("Just a sec.. Or two..");
		taskExecutor.execute(findSolutionRunnable);
		taskExecutor.execute(() -> {
			if(gameCost.get() == -1 ) {
			
				scoreToMatch.setText("We dont even know.. ");
			}else {
				scoreToMatch.setText("" + gameCost.get());
				costIsSet.set(true);
			}
			
	
		});

	}

	private int getValueFromLabel(JLabel label) {
		return Integer.parseInt(label.getText());
	}

	private void evaluateEndGame() {
		if (game != null) {
			// if player won
			if (game.isOver()) {

				// and the algorith produced a game cost
				if (costIsSet.get()) {
					int playerMoveCost = getValueFromLabel(totalCost);
					int scoreToEqualize = getValueFromLabel(scoreToMatch);
					if (playerMoveCost <= scoreToEqualize) {
						addToLabel(totalWins, 1);
						showWinnerMessage();
					} else {
						addToLabel(totalCouldHaveDoneItBetter, 1);
						showCouldHaveDoneItBetterMessage();
					}
				} else {
					addToLabel(totalWins, 1);
					showYouBeatTheBotMessage();
				}
			} else {
				addToLabel(totalGiveUps, 1);
			}
		}

	}


	private void refreshSelectedText() {
		chooseLetterSlider.changeSelectedText(chooseLetterSlider.getValue());

	}

	private void showCouldHaveDoneItBetterMessage() {
		JOptionPane
				.showMessageDialog(null,
						"You should have a total cost of: " + getValueFromLabel(scoreToMatch) + ".\nBut you had: "
								+ getValueFromLabel(totalCost),
						"Almost perfect job there..", JOptionPane.PLAIN_MESSAGE);

	}

	private void showWinnerMessage() {
		JOptionPane.showMessageDialog(null,
				"You won!\nTotal cost: " + totalCost.getText() + ".\nTotal moves: " + totalMoves.getText(),
				"Congratulations!", JOptionPane.INFORMATION_MESSAGE);
	}

	private void showStartNewGameMessage() {
		JOptionPane.showMessageDialog(null, "Start a new game first!", "No game available.",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void showRulesMessage() {
		JOptionPane.showMessageDialog(null,
				"A sphere-letter can only move up as much spaces\nas the same sphere-letter count."
						+ "\nIn our case up to: " + Math.floorDiv(game.getLength(), 2) + " spots.",
				"Illegal Move!", JOptionPane.INFORMATION_MESSAGE);
	}

	private void showPointlessMoveMessage() {
		JOptionPane.showMessageDialog(null, "Pointless move don't you think?", "Are we doing this?",
				JOptionPane.INFORMATION_MESSAGE);
	}
	

	private void showYouBeatTheBotMessage() {
			JOptionPane.showMessageDialog(null, "You beat our very sophisticated bot..", "WOW!",
					JOptionPane.INFORMATION_MESSAGE);
	}

}
