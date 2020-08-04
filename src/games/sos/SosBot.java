package games.sos;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class SosBot {

	private Board board;

	private int movesChecked = 0;

	/**
	 * Creates a SosBot to "play" to the given {@link Board}.
	 * @param board
	 */
	public SosBot(Board board) {
		this.board = board;
	}

	
	/**
	 * Acts upon the already set-up board. Using the minimax algorithm decides which is the best next move.
	 * Returns whether or not a move was made.
	 * <p> An unpredictable move is made if the board has a winner and minimax is called upon that.
	 * @return True if a move was made. False otherwise.
	 */
	public boolean startMinimax() {
		
		TreeMap<Integer, ArrayList<Move>> movesMap = new TreeMap<>();  //a treemap that hold all the possible moves
		if (board.hasNextMove()) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					
					if (board.setMove(i, j, "S")) {
						movesChecked = 0;
						
						
						//find the score of the move
						int moveScore = minimaxBoard(board, false, 0); 
						
						//if the move score value is not presenent add it
						if (!movesMap.containsKey(moveScore)) {
							movesMap.put(moveScore, new ArrayList<Move>());
						}
						
						//for the specific score value add a new move 
						movesMap.get(moveScore).add(new Move(i, j, "S"));
						
						
						//reset board to its previous condition
						board.undoMove();
						
						//check for the same pos with "O" for letter
						board.setMove(i, j, "O");
						
						//do the same
						moveScore = minimaxBoard(board, false, 0);
						if (!movesMap.containsKey(moveScore)) {
							movesMap.put(moveScore, new ArrayList<Move>());
						}
						movesMap.get(moveScore).add(new Move(i, j, "O"));
						board.undoMove();
					}
					
				}
			}
			
			//uncoment below line to print how many moves where checked before making a decision
			//System.out.println(movesChecked); 
			
			
			//from all the collection of equally good moves 
			//play a random one.
			setRandomMove(movesMap);
			
			//uncoment below line to provide a "Value" for each possible move
			showAvailableMoves(movesMap);
			

			
			return true;
		}else {
			return false;
		}
	}


	public int minimaxBoard(Board currentBoard, boolean botPlayerTurn, int moveValue) {
		
		//if there is a winner or there is no next move
		if (!currentBoard.hasNextMove() || board.hasWinner()) {
			
			//evaluate the state
			return evaluateState(currentBoard, botPlayerTurn);
		} else {
			
			
			//else for all the moves
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					
	
					//if a move is available
					if (currentBoard.setMove(i, j, "S")) {
						
						
						if (botPlayerTurn) {
							
							//find the max between this move and the move given
							moveValue = Math.max(minimaxBoard(currentBoard, !botPlayerTurn, moveValue), moveValue);
						} else {
							
							//else find themin between this move and the move given
							moveValue = Math.min(minimaxBoard(currentBoard, !botPlayerTurn, moveValue), moveValue);
						}
						
						//reset board to it's previous state
						board.undoMove();
						
						//do the same with "O" move
						currentBoard.setMove(i, j, "O") ;
						movesChecked++;
						if (botPlayerTurn) {
							moveValue = Math.max(minimaxBoard(currentBoard, !botPlayerTurn, moveValue), moveValue);
						} else {
							moveValue = Math.min(minimaxBoard(currentBoard, !botPlayerTurn, moveValue), moveValue);
						}

						board.undoMove();
					}

					
					
				}
			}
		}
		
		return moveValue;
	}
	
	

	/**
	 * Given a TreeMap with the moves it prints all the available moves with their value
	 * @param movesMap The map that containts the moves with their respective value
	 */
	private void showAvailableMoves(TreeMap<Integer, ArrayList<Move>> movesMap) {
		for (Map.Entry<Integer, ArrayList<Move>> entry : movesMap.entrySet()) {
			System.out.println("------------------Value: " + entry.getKey() + "-------------------");
			for (Move move : entry.getValue()) {

				System.out.println(
						"Move: x-> " + move.getX() + " y:" + move.getY() + " letter -> " + move.getLetter());
			}
		}
		
	}

	/**
	 * Given a TreeMap with the moves it makes a random move from all the best possible ones.
	 * Used for playing one random move from all the best ones instead of the first it finds
	 * @param movesMap The map that containts the moves with their respective value
	 */
	private void setRandomMove(TreeMap<Integer, ArrayList<Move>> movesMap) {
		List<Move>  availableMoves = movesMap.lastEntry().getValue();
		Random randomChooser = new Random();
		Move move = availableMoves.get(randomChooser.nextInt(availableMoves.size()));
		board.setMove(move);
	}

	/**
	 * Evaluates the board's current state.
	 * @param currentBoard the board to evalueate
	 * @param botPlayerTurn determines if it's the bot's turn
	 * @return the value of the current state
	 */

	private int evaluateState(Board currentBoard, boolean botPlayerTurn) {
		if (currentBoard.hasWinner()) {
			return botPlayerTurn ? -10 : 10;
		} else
			return 0;
	}

}
