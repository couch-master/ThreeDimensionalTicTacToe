// Importing Libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ThreeDimensionalTicTacToe extends JFrame implements ActionListener {

	// UI Global Variable References
	private CardLayout cardLayout;
	private JPanel titlePanel, mainPanel, optionsPanel, gameBoardPanel, scoreCardPanel;
	private JRadioButton EasyButton, DifficultButton, InsaneButton, xRadButton, humanFirstButton;
	private JLabel gameBoardTitle, scoreCardLabel;
	private JPanel boardPanel;

	// Global Variables
	private boolean win = false;
	private int difficulty = 2, totalLooksAhead = 2, lookAheadCounter = 0, humanScore = 0, computerScore = 0;
	private int[] finalWin = new int[4];
	private char playerSymbol = 'X', aiSymbol = 'O', boardState[][][];
	private GameTileButton[][][] threeDGameBoard;
	private GameTileButton[] endMoveButtons = new GameTileButton[4];

	// Constructors
	public ThreeDimensionalTicTacToe() {
		super("AI Mavericks");
		loadMainWindow();
		loadCardLayoutScreens();
		setVisible(true);
	}

	// Main method
	public static void main(String args[]) {
		new ThreeDimensionalTicTacToe();
	}

	// UI Helper Functions
	// Method for loading and boardStateuring the Main window
	private void loadMainWindow() {
		setSize(600, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	// Initializes and boardStateures CardLayout screens, adding title, options, and
	// game board panels to the main panel.
	private void loadCardLayoutScreens() {
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		mainPanel.add(createTitlePanel(), "title");
		mainPanel.add(createOptionsPanel(), "options");
		mainPanel.add(createGameBoardPanel(), "gameboard");
		add(mainPanel);
	}

	// Title Panel Helper Functions
	// Creates and boardStateures the title panel with the game title, an image, and a "Play Game" button.
	private JPanel createTitlePanel() {
		titlePanel = new JPanel(null);
		titlePanel.setBackground(Color.LIGHT_GRAY);

		JLabel title = new JLabel(
				"<html><font color='violet'>Welcome to</font>  <font color='brown'>3D Tic-Tac-Toe Game</font></html>");
		title.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		title.setBounds(138, 50, 420, 30);
		titlePanel.add(title);

		String imagePath = "/resource/download.png"; 
		System.out.println(imagePath);
		ImageIcon imageIcon = new ImageIcon(ThreeDimensionalTicTacToe.class.getResource(imagePath));
		JLabel imageLabel = new JLabel(imageIcon);
		int imageWidth = imageIcon.getIconWidth();
		int imageHeight = imageIcon.getIconHeight();
		int imageX = 140 + (320 - imageWidth) / 2;
		int imageY = 130;
		imageLabel.setBounds(imageX, imageY, imageWidth, imageHeight);
		titlePanel.add(imageLabel);

		JButton playButton = new JButton("Start the Game");
		playButton.setBounds(180, 620, 220, 50);
		playButton.addActionListener(new playButtonListener());
		playButton.setName("playButton");
		Font buttonFont = playButton.getFont();
		playButton.setFont(new Font(buttonFont.getName(), Font.PLAIN, 18));
		playButton.setFocusPainted(false);
		titlePanel.add(playButton);
		return titlePanel;
	}

	// Title Panel Action Listeners
	// Implements ActionListener for the "Play Game" button, switching the CardLayout to display the options panel when the button is clicked.
	class playButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(mainPanel, "options");
		}
	}

	// Options Panel Helper Functions
	// Creates and boardStateures the Options panel with the Game Settings options.
	private JPanel createOptionsPanel() {
		optionsPanel = new JPanel(null);
		addLabels();
		addDifficultyRadioButton();
		addSelectPieceRadioButtons();
		addSelectPlayerFirstRadioButton();
		addStartGameButton();
		return optionsPanel;
	}

	// Adds and boardStateures JLabels for game settings, including titles, rules, difficulty level, coin selection, and player selection.
	private void addLabels() {
		JLabel optionSelectionTitle = new JLabel("<html><font color='red'>Game Settings</font></html>");
		optionSelectionTitle.setFont(new Font("Georgia", Font.BOLD, 27));
		optionSelectionTitle.setBounds(200, 50, 420, 30);
		optionsPanel.add(optionSelectionTitle);

		JLabel rulesTitle = new JLabel(
				"<html><font color='Blue'>Rules: The OBJECTIVE is to place four of your markers in a row horizontally, diagonally, or vertically while simultaneously trying to block your opponent from doing the same. <br> <br>GAMEPLAY: Click on any empty square and one of your markers will appear in that space.</font></html>");
		rulesTitle.setFont(new Font("Georgia", Font.PLAIN, 13));
		rulesTitle.setBounds(40, 100, 520, 65);
		optionsPanel.add(rulesTitle);

		JLabel difficultyLevelLabel = new JLabel("Select the Level of Difficulty");
		difficultyLevelLabel.setFont(new Font("Georgia", Font.BOLD, 18));
		difficultyLevelLabel.setBounds(40, 200, 320, 30);
		optionsPanel.add(difficultyLevelLabel);

		JLabel pieceSelectionLabel = new JLabel("Select the coin");
		pieceSelectionLabel.setFont(new Font("Georgia", Font.BOLD, 18));
		pieceSelectionLabel.setBounds(40, 310, 320, 30);
		optionsPanel.add(pieceSelectionLabel);

		JLabel playerSelectionLabel = new JLabel("Select the Player");
		playerSelectionLabel.setFont(new Font("Georgia", Font.BOLD, 18));
		playerSelectionLabel.setBounds(40, 420, 320, 30);
		optionsPanel.add(playerSelectionLabel);
	}

	// Adds a group of radio buttons for selecting difficulty levels (Easy, Difficult, Insane) to the options panel with a listener for changes.
	private void addDifficultyRadioButton() {
		ButtonGroup difficultyGroup = new ButtonGroup();
		GameLevelActionListener gameLevelListener = new GameLevelActionListener();
		EasyButton = new JRadioButton("Noob");
		DifficultButton = new JRadioButton("Amateur", true);
		InsaneButton = new JRadioButton("Pro");

		String[] difficultyLevels = { "Noob", "Amateur", "Pro" };
		JRadioButton[] difficultyLevelButtons = new JRadioButton[difficultyLevels.length];
		difficultyLevelButtons[0] = EasyButton;
		difficultyLevelButtons[1] = DifficultButton;
		difficultyLevelButtons[2] = InsaneButton;

		for (int i = 0; i < difficultyLevelButtons.length; i++) {
			difficultyLevelButtons[i].setBounds(100 + i * 110, 250, 100, 40);
			difficultyGroup.add(difficultyLevelButtons[i]);
			difficultyLevelButtons[i].addActionListener(gameLevelListener);
			optionsPanel.add(difficultyLevelButtons[i]);
		}
		difficultyLevelButtons[1].setSelected(true);
	}

	// Adds radio buttons for selecting the player's game piece (X or O).
	private void addSelectPieceRadioButtons() {
		ButtonGroup coinSelectionGroup = new ButtonGroup();
		CoinSelectionActionListener coinSelectionListener = new CoinSelectionActionListener();

		xRadButton = new JRadioButton("X", true);
		JRadioButton oRadButton = new JRadioButton("O");

		createAndAddRadioButton(xRadButton, 100, 345, coinSelectionGroup, coinSelectionListener, 50, 50);
		createAndAddRadioButton(oRadButton, 160, 345, coinSelectionGroup, coinSelectionListener, 50, 50);
	}

	// Adds radio buttons for selecting the player who moves first (Human or CPU)
	private void addSelectPlayerFirstRadioButton() {
		ButtonGroup firstSelect = new ButtonGroup();
		PlayerTurnActionListener playerTurnListener = new PlayerTurnActionListener();

		humanFirstButton = new JRadioButton("Human First", true);
		JRadioButton cpuFirstButton = new JRadioButton("CPU First");

		createAndAddRadioButton(humanFirstButton, 210, 460, firstSelect, playerTurnListener, 150, 40);
		createAndAddRadioButton(cpuFirstButton, 100, 460, firstSelect, playerTurnListener, 100, 40);
	}

	// Adds a "Start Game" button to the options panel with an ActionListener to handle button clicks
	private void addStartGameButton() {
		JButton startGameButton = new JButton("Start Game");
		startGameButton.setBounds(180, 620, 220, 50);
		startGameButton.addActionListener(new startGameButtonActionListner());
		startGameButton.setName("startGameButton");
		Font buttonFont = startGameButton.getFont();
		startGameButton.setFont(new Font(buttonFont.getName(), Font.PLAIN, 18));
		startGameButton.setFocusPainted(false);
		optionsPanel.add(startGameButton);
	}

	// Option Panel Action Listeners
	// Handles the action event for difficulty level selection, updating gameBoardTitle, setting difficulty parameters.
	class GameLevelActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameBoardTitle.setForeground(Color.DARK_GRAY);
			gameBoardTitle.setText("                  Game On!!!");
			

			if (EasyButton.isSelected()) {
				configureDifficulty(1, 2);
			} else if (DifficultButton.isSelected()) {
				configureDifficulty(2, 5);
			} else {
				configureDifficulty(3, 8);
			}

			if (!humanFirstButton.isSelected() && difficulty == 3) {
				aiStrategicMove();
			} else if (!humanFirstButton.isSelected()) {
				randomAiMove();
			}
		}
	}

	// ActionListener for player's game piece selection.
	class CoinSelectionActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameBoardTitle.setForeground(Color.DARK_GRAY);
			gameBoardTitle.setText("                  Game On!!!");

			if (xRadButton.isSelected()) {
				assignSymbols('X', 'O');
			} else {
				assignSymbols('O', 'X');
			}

			if (!humanFirstButton.isSelected()) {
				if (difficulty == 3) {
					aiStrategicMove();
				} else {
					randomAiMove();
				}
			}
		}
	}

	// ActionListener for player turn setup, clears the board, sets gameBoardTitle message, and triggers computer player move based on user preferences.
	class PlayerTurnActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			initializeGame();
			gameBoardTitle.setForeground(Color.DARK_GRAY);
			gameBoardTitle.setText("                  Game On!!!");
			if (!humanFirstButton.isSelected()) {
				if (!InsaneButton.isSelected())
					randomAiMove();
				else
					aiStrategicMove();
			}
		}
	}

	// ActionListener for the "Start Game" button, switches the card layout to the gameboard panel when the button is clicked.
	class startGameButtonActionListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(mainPanel, "gameboard");
		}
	}

	// Game Board Panel Helper Functions, Creates and boardStateures the Game board panel for playing the 3D Tic-Tac-Toe game.
	private JPanel createGameBoardPanel() {
		gameBoardPanel = new JPanel(null);
		boardStateureGameBoard();
		addButtonsToGamePanel();
		return gameBoardPanel;
	}

	// Configures the game board by adding the text panel, button panel, and board panel
	private void boardStateureGameBoard() {
		addTextPanel();
		addButtonPanel();
		addBoardPanel();
		gameBoardPanel.setVisible(true);
	}

	// Adds a text panel with game board title and score card labels to the game board panel.
	private void addTextPanel() {
		scoreCardPanel = new JPanel();
		scoreCardPanel.setLayout(new GridLayout(2, 1));
		scoreCardPanel.setBounds(00, 50, 280, 100);
		scoreCardPanel.setVisible(true);

		gameBoardTitle = new JLabel("         Welcome to 3D Tic-Tac-Toe 4X4");
		gameBoardTitle.setFont(new Font("georgia", Font.BOLD, 14));
		gameBoardTitle.setVisible(true);
		scoreCardPanel.add(gameBoardTitle);

		scoreCardLabel = new JLabel("        You: " + humanScore + "   Computer: " + computerScore);
		scoreCardLabel.setFont(new Font("georgia", Font.BOLD, 15));
		scoreCardPanel.add(scoreCardLabel);

		gameBoardPanel.add(scoreCardPanel);
	}

	// Adds a custom BoardPanel to the game board, setting its position and visibility.
	private void addBoardPanel() {
		boardPanel = new BoardPanel();
		boardPanel.setBounds(200, 0, 550, 750);
		boardPanel.setVisible(true);
		gameBoardPanel.add(boardPanel);
	}

	// Configures and adds a 3D array of custom GameTileButtons to the gameBoardPanel.
	private void addButtonPanel() {
		boardState = new char[4][4][4];
		threeDGameBoard = new GameTileButton[4][4][4];

		int rowShift = 18;
		int rowStart = 325;
		int xPos = 325;
		int yPos = 35;
		int width = 60;
		int height = 50;
		int boxCounter = 0;

		for (int brdNum = 0; brdNum <= 3; brdNum++) {
			int rowNum = 0, colNum = 0;
			for (int i = 0; i <= 3; i++) {
				for (int j = 0; j <= 3; j++) {
					boardState[brdNum][rowNum][colNum] = '-';
					threeDGameBoard[brdNum][rowNum][colNum] = generateGameTile(brdNum, rowNum, colNum, xPos, yPos,
							width, height, boxCounter);
					gameBoardPanel.add(threeDGameBoard[brdNum][rowNum][colNum]);
					colNum++;
					boxCounter++;
					xPos += 49;
				}
				colNum = 0;
				rowNum++;
				xPos = rowStart -= rowShift;
				yPos += 40;
			}
			rowNum = 0;
			rowShift = 18;
			rowStart = 333;
			xPos = rowStart;
			yPos += 4;
		}
	}

	// Adds buttons (New Game, Restart Game, Back to Settings) to the game board panel
	private void addButtonsToGamePanel() {
		addButtonToPanel("New Game", 40, 450, new newGameButtonActionListner());
		addButtonToPanel("Restart Game", 40, 510, new restartGameButtonActionListner());
		addButtonToPanel("Back to Settings", 40, 570, new backToSettingsButtonActionListner());
	}

	// Game board Panel Action Listeners
	// Custom JPanel class representing the 3D game board, drawing lines for each board section and a red line through winning positions when a win occurs.
	public class BoardPanel extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));

			// Draw boards
			for (int board = 0; board <= 3; board++) {
				int yOffset = 167 * board;
				g2.drawLine(130, 73 + yOffset, 330, 73 + yOffset);
				g2.drawLine(105, 115 + yOffset, 305, 115 + yOffset);
				g2.drawLine(90, 157 + yOffset, 290, 157 + yOffset);
				g2.drawLine(190, 40 + yOffset, 115, 190 + yOffset);
				g2.drawLine(245, 40 + yOffset, 170, 190 + yOffset);
				g2.drawLine(300, 40 + yOffset, 225, 190 + yOffset);
			}

			// Draw red line through winning positions
			if (win) {
				g2.setColor(Color.MAGENTA);
				g2.drawLine(endMoveButtons[0].getBounds().x - 165, endMoveButtons[0].getBounds().y + 20,
						endMoveButtons[3].getBounds().x - 172, endMoveButtons[3].getBounds().y + 23);
			}
		}
	}

	// Handles the player's move in the 3D Tic Tac Toe game, updates the board, checks for a winning move, and triggers the computer's move or declares player victory.
	public void actionPerformed(ActionEvent e) {
		GameTileButton button = (GameTileButton) e.getSource();
		int board = button.boxBoard;
		int row = button.boxRow;
		int column = button.boxColumn;

		boardState[board][row][column] = playerSymbol;
		threeDGameBoard[board][row][column].setText(Character.toString(playerSymbol));
		threeDGameBoard[board][row][column].setEnabled(false);

		OneMove playerMove = new OneMove();
		playerMove.board = board;
		playerMove.row = row;
		playerMove.column = column;

		if (checkVictoryCondition(playerSymbol, playerMove)) {
			gameBoardTitle.setText("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You Won!<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Press New Game to play again.</html>");
			gameBoardTitle.setForeground(new Color(0, 150, 0));
			humanScore++;
			win = true;
			disableGameTileButtons();
			resetScoreCard();
		} else {
			aiStrategicMove();
		}
	}

	// ActionListener for the "New Game" button, triggers the clearing of the game board when clicked.
	class newGameButtonActionListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gameBoardTitle.setForeground(Color.DARK_GRAY);
			gameBoardTitle.setText("                  Game On!!!");
			initializeGame();
		}
	}

	// ActionListener for the "Restart Game" button, displays a confirmation dialog, resets scores and clears the game board if the user chooses to restart
	class restartGameButtonActionListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int option = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to restart the game? Your progress will be lost!!", "Restart Game",
					JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {
				System.out.println("Restarting the game...");
				gameBoardTitle.setForeground(Color.DARK_GRAY);
				gameBoardTitle.setText("                  Game On!!!");

				humanScore = 0;
				computerScore = 0;
				scoreCardLabel.setText("          You: " + humanScore + "   Computer: " + computerScore);
				initializeGame();
			} else {
				// User clicked No or closed the dialog, do nothing
				System.out.println("Cancelled restart.");
			}
		}
	}

	// ActionListener for the "Back to Settings" button, switches the card layout to the options panel if the user chooses to go back.
	class backToSettingsButtonActionListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to go back to settings?",
					"Back to Settings", JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {
				System.out.println("Back to Settings....");
				cardLayout.show(mainPanel, "options");
				initializeGame();
			} else {
				// User clicked No or closed the dialog, do nothing
				System.out.println("Cancelled Back to settings.");
			}
		}
	}

	// General Helper functions Sets the difficulty parameters for the computer player based on the specified level and looks ahead.
	private void configureDifficulty(int level, int looksAhead) {
		difficulty = level;
		totalLooksAhead = looksAhead;
	}

	// Sets the game pieces for the human and computer players.
	private void assignSymbols(char human, char computer) {
		playerSymbol = human;
		aiSymbol = computer;
	}

	// Creates and adds a radio button with specified parameters to the options panel using the given ButtonGroup and ActionListener.
	private void createAndAddRadioButton(JRadioButton XorOradioButton, int x, int y, ButtonGroup group,
			ActionListener listener, int width, int height) {
		XorOradioButton.setBounds(x, y, width, height);
		group.add(XorOradioButton);
		XorOradioButton.addActionListener(listener);
		optionsPanel.add(XorOradioButton);
	}

	// Helper method to add a button to the game board panel with specified parameters.
	private void addButtonToPanel(String label, int x, int y, ActionListener listener) {
		JButton button = new JButton(label);
		button.setBounds(x, y, 140, 40);
		button.addActionListener(listener);
		button.setName(label.replaceAll("\\s", "") + "Button");
		Font buttonFont = button.getFont();
		button.setFont(new Font(buttonFont.getName(), Font.PLAIN, 14));
		button.setFocusPainted(false);
		gameBoardPanel.add(button);
	}

	// Custom JButton class representing a button on the 3D Tic-Tac-Toe game board.
	public class GameTileButton extends JButton {
		public int boxRow;
		public int boxColumn;
		public int boxBoard;
	}

	// Represents a single move in 3D Tic Tac Toe, storing the board, row, and column indices.
	public class OneMove {
		int board;
		int row;
		int column;
	}

	// Creates and boardStateures a custom GameTileButton with specified parameters.
	private GameTileButton generateGameTile(int brdNum, int rowNum, int colNum, int xPos, int yPos, int width,
			int height, int boxCounter) {
		GameTileButton button = new GameTileButton();
		button.setFont(new Font("Arial Bold", Font.ITALIC, 20));
		button.setText("");
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setBounds(xPos, yPos, width, height);
		button.setName(Integer.toString(boxCounter));
		button.boxBoard = brdNum;
		button.boxRow = rowNum;
		button.boxColumn = colNum;
		button.addActionListener(this);
		return button;
	}

	// Clears the 3D game board by resetting boardStateuration, text, and enabling buttons.
	private void initializeGame() {
		repaint();
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				for (int k = 0; k <= 3; k++) {
					boardState[i][j][k] = '-';
					threeDGameBoard[i][j][k].setText("");
					threeDGameBoard[i][j][k].setEnabled(true);
				}
			}
		}
		finalWin = new int[4];
		win = false;
		lookAheadCounter = 0;
		gameBoardTitle.setForeground(Color.DARK_GRAY);
		gameBoardTitle.setText("                  Game On!!!");
	}

	// Updates the scorecard label with the current scores for the human and computer players in the 3D Tic Tac Toe game.
	private void resetScoreCard() {
		scoreCardLabel.setText("          You: " + humanScore + "   Computer: " + computerScore);
	}

	// Disables all board buttons except those involved in the final winning combination and highlights them in red.
	private void disableGameTileButtons() {
		int index = 0;
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				for (int k = 0; k <= 3; k++) {
					GameTileButton currentButton = threeDGameBoard[i][j][k];
					if (contains(finalWin, Integer.parseInt(currentButton.getName()))) {
						currentButton.setEnabled(true);
						currentButton.setForeground(Color.BLUE);
						endMoveButtons[index++] = currentButton;
					} else {
						currentButton.setEnabled(false);
					}
				}
			}
		}
		repaint();
	}

	// Checks if the integer array 'a' contains the value 'k' and returns true if found, otherwise returns false.
	private boolean contains(int[] a, int k) {
		for (int i : a) {
			if (k == i)
				return true;
		}
		return false;
	}

	// Generates a random move for the computer in 3D Tic Tac Toe, updating the board with the computer's piece at a random position.
	private void randomAiMove() {
		Random random = new Random();
		int row = random.nextInt(4);
		int column = random.nextInt(4);
		int board = random.nextInt(4);
		boardState[board][row][column] = aiSymbol;
		threeDGameBoard[board][row][column].setText(Character.toString(aiSymbol));
		threeDGameBoard[board][row][column].setEnabled(false);
	}

	// Implements the computer's move in 3D Tic Tac Toe using a minimax algorithm with alpha-beta pruning
	private void aiStrategicMove() {
		int optimalScore = -1000;
		int hValue;
		OneMove nextMove;
		int optimalScoreBoard = -1;
		int optimalScoreRow = -1;
		int optimalScoreColumn = -1;

		check: for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				for (int k = 0; k <= 3; k++) {
					if (boardState[i][j][k] == '-') {
						nextMove = new OneMove();
						nextMove.board = i;
						nextMove.row = j;
						nextMove.column = k;
						if (checkVictoryCondition(aiSymbol, nextMove)) {
							boardState[i][j][k] = aiSymbol;
							threeDGameBoard[i][j][k].setText(Character.toString(aiSymbol));
							gameBoardTitle.setText("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I win!<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Press New Game to play again.</html>");
							gameBoardTitle.setForeground(Color.MAGENTA);
							win = true;
							computerScore++;
							disableGameTileButtons();
							resetScoreCard();
							break check;
						} else {
							if (difficulty != 1) {
								hValue = calculateOptimalMove(playerSymbol, -1000, 1000);
							} else {
								hValue = evaluateHeuristic();
							}

							lookAheadCounter = 0;
							if (hValue >= optimalScore) {
								optimalScore = hValue;
								optimalScoreBoard = i;
								optimalScoreRow = j;
								optimalScoreColumn = k;
								boardState[i][j][k] = '-';
							} else {
								boardState[i][j][k] = '-';
							}
						}
					}
				}
			}
		}
		if (!win) {
			boardState[optimalScoreBoard][optimalScoreRow][optimalScoreColumn] = aiSymbol;
			threeDGameBoard[optimalScoreBoard][optimalScoreRow][optimalScoreColumn]
					.setText(Character.toString(aiSymbol));
			threeDGameBoard[optimalScoreBoard][optimalScoreRow][optimalScoreColumn].setEnabled(false);
		}
	}

	// Recursively determines the best move for the minimax algorithm, alternating between computer and human turns
	private int calculateOptimalMove(char currentPlayer, int alpha, int beta) {
		if (lookAheadCounter > totalLooksAhead) {
			return evaluateHeuristic();
		}
		lookAheadCounter++;
		if (currentPlayer == aiSymbol) {
			return calculateOptimalMoveComputerTurn(alpha, beta);
		} else {
			return calculateOptimalMoveHumanTurn(alpha, beta);
		}
	}
	
	// Determines the best move for the computer during the minimax algorithm's search
	private int calculateOptimalMoveComputerTurn(int alpha, int beta) {
		int hValue;
		OneMove nextMove;
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				for (int k = 0; k <= 3; k++) {
					if (boardState[i][j][k] == '-') {
						nextMove = new OneMove();
						nextMove.board = i;
						nextMove.row = j;
						nextMove.column = k;
						if (checkVictoryCondition(aiSymbol, nextMove)) {
							boardState[i][j][k] = '-';
							return 1000;
						} else {
							hValue = calculateOptimalMove(playerSymbol, alpha, beta);
							if (hValue > alpha) {
								alpha = hValue;
								boardState[i][j][k] = '-';
							} else {
								boardState[i][j][k] = '-';
							}
						}

						if (alpha >= beta)
							break;
					}
				}
			}
		}
		return alpha;
	}

	// Determines the best move for the human player during the minimax algorithm's search
	private int calculateOptimalMoveHumanTurn(int alpha, int beta) {
		int hValue;
		OneMove nextMove;
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				for (int k = 0; k <= 3; k++) {
					if (boardState[i][j][k] == '-') {
						nextMove = new OneMove();
						nextMove.board = i;
						nextMove.row = j;
						nextMove.column = k;
						if (checkVictoryCondition(playerSymbol, nextMove)) {
							boardState[i][j][k] = '-';
							return -1000;
						} else {
							hValue = calculateOptimalMove(aiSymbol, alpha, beta);
							if (hValue < beta) {
								beta = hValue;
								boardState[i][j][k] = '-';
							} else {
								boardState[i][j][k] = '-';
							}
						}
						if (alpha >= beta)
							break;
					}
				}
			}
		}
		return beta;
	}

	// Computes a simple evaluateHeuristic value based on the difference between available moves for the computer and human players
	private int evaluateHeuristic() {
		return (checkAvailable(aiSymbol) - checkAvailable(playerSymbol));
	}

	// Checks if the specified player has achieved a winning combination on the 3D Tic Tac Toe board
	private boolean checkVictoryCondition(char playerPiece, OneMove move) {
		boardState[move.board][move.row][move.column] = playerPiece;
		int[][] wins = {
				{ 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 10, 11 }, { 12, 13, 14, 15 },
				{ 16, 17, 18, 19 }, { 20, 21, 22, 23 }, { 24, 25, 26, 27 }, { 28, 29, 30, 31 },
				{ 32, 33, 34, 35 }, { 36, 37, 38, 39 }, { 40, 41, 42, 43 }, { 44, 45, 46, 47 },
				{ 48, 49, 50, 51 }, { 52, 53, 54, 55 }, { 56, 57, 58, 59 }, { 60, 61, 62, 63 },
				{ 0, 4, 8, 12 }, { 1, 5, 9, 13 }, { 2, 6, 10, 14 }, { 3, 7, 11, 15 },
				{ 16, 20, 24, 28 }, { 17, 21, 25, 29 }, { 18, 22, 26, 30 }, { 19, 23, 27, 31 },
				{ 32, 36, 40, 44 }, { 33, 37, 41, 45 }, { 34, 38, 42, 46 }, { 35, 39, 43, 47 },
				{ 48, 52, 56, 60 }, { 49, 53, 57, 61 }, { 50, 54, 58, 62 }, { 51, 55, 59, 63 },
				{ 0, 5, 10, 15 }, { 3, 6, 9, 12 }, { 16, 21, 26, 31 }, { 19, 22, 25, 28 },
				{ 32, 37, 42, 47 }, { 35, 38, 41, 44 }, { 48, 53, 58, 63 }, { 51, 54, 57, 60 },
				{ 0, 16, 32, 48 }, { 1, 17, 33, 49 }, { 2, 18, 34, 50 }, { 3, 19, 35, 51 },
				{ 4, 20, 36, 52 }, { 5, 21, 37, 53 }, { 6, 22, 38, 54 }, { 7, 23, 39, 55 },
				{ 8, 24, 40, 56 }, { 9, 25, 41, 57 }, { 10, 26, 42, 58 }, { 11, 27, 43, 59 },
				{ 12, 28, 44, 60 }, { 13, 29, 45, 61 }, { 14, 30, 46, 62 }, { 15, 31, 47, 63 },
				{ 0, 20, 40, 60 }, { 1, 21, 41, 61 }, { 2, 22, 42, 62 }, { 3, 23, 43, 63 },
				{ 12, 24, 36, 48 }, { 13, 25, 37, 49 }, { 14, 26, 38, 50 }, { 15, 27, 39, 51 },
				{ 4, 21, 38, 55 }, { 8, 25, 42, 59 }, { 7, 22, 37, 52 }, { 11, 26, 41, 56 },
				{ 0, 17, 34, 51 }, { 3, 18, 33, 48 }, { 12, 29, 46, 63 }, { 15, 30, 45, 60 },
				{ 0, 21, 42, 63 }, { 3, 22, 41, 60 }, { 12, 25, 38, 51 }, { 15, 26, 37, 48 }, };
		int[] gameBoard = new int[64];
		int counter = 0;
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				for (int k = 0; k <= 3; k++) {
					if (boardState[i][j][k] == playerPiece) {
						gameBoard[counter] = 1;
					} else {
						gameBoard[counter] = 0;
					}
					counter++;
				}
			}
		}
		for (int i = 0; i <= 75; i++) {
			counter = 0;
			for (int j = 0; j <= 3; j++) {
				if (gameBoard[wins[i][j]] == 1) {
					counter++;
					finalWin[j] = wins[i][j];
					if (counter == 4) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Counts and returns the number of available winning combinations for the specified player on the 3D Tic Tac Toe board.
	private int checkAvailable(char playerPiece) {
		int winCounter = 0;
		int[][] wins = {
				{ 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 10, 11 }, { 12, 13, 14, 15 },
				{ 16, 17, 18, 19 }, { 20, 21, 22, 23 }, { 24, 25, 26, 27 }, { 28, 29, 30, 31 },
				{ 32, 33, 34, 35 }, { 36, 37, 38, 39 }, { 40, 41, 42, 43 }, { 44, 45, 46, 47 },
				{ 48, 49, 50, 51 }, { 52, 53, 54, 55 }, { 56, 57, 58, 59 }, { 60, 61, 62, 63 },
				{ 0, 4, 8, 12 }, { 1, 5, 9, 13 }, { 2, 6, 10, 14 }, { 3, 7, 11, 15 },
				{ 16, 20, 24, 28 }, { 17, 21, 25, 29 }, { 18, 22, 26, 30 }, { 19, 23, 27, 31 },
				{ 32, 36, 40, 44 }, { 33, 37, 41, 45 }, { 34, 38, 42, 46 }, { 35, 39, 43, 47 },
				{ 48, 52, 56, 60 }, { 49, 53, 57, 61 }, { 50, 54, 58, 62 }, { 51, 55, 59, 63 },
				{ 0, 5, 10, 15 }, { 3, 6, 9, 12 }, { 16, 21, 26, 31 }, { 19, 22, 25, 28 },
				{ 32, 37, 42, 47 }, { 35, 38, 41, 44 }, { 48, 53, 58, 63 }, { 51, 54, 57, 60 },
				{ 0, 16, 32, 48 }, { 1, 17, 33, 49 }, { 2, 18, 34, 50 }, { 3, 19, 35, 51 },
				{ 4, 20, 36, 52 }, { 5, 21, 37, 53 }, { 6, 22, 38, 54 }, { 7, 23, 39, 55 },
				{ 8, 24, 40, 56 }, { 9, 25, 41, 57 }, { 10, 26, 42, 58 }, { 11, 27, 43, 59 },
				{ 12, 28, 44, 60 }, { 13, 29, 45, 61 }, { 14, 30, 46, 62 }, { 15, 31, 47, 63 },
				{ 0, 20, 40, 60 }, { 1, 21, 41, 61 }, { 2, 22, 42, 62 }, { 3, 23, 43, 63 },
				{ 12, 24, 36, 48 }, { 13, 25, 37, 49 }, { 14, 26, 38, 50 }, { 15, 27, 39, 51 },
				{ 4, 21, 38, 55 }, { 8, 25, 42, 59 }, { 7, 22, 37, 52 }, { 11, 26, 41, 56 },
				{ 0, 17, 34, 51 }, { 3, 18, 33, 48 }, { 12, 29, 46, 63 }, { 15, 30, 45, 60 },
				{ 0, 21, 42, 63 }, { 3, 22, 41, 60 }, { 12, 25, 38, 51 }, { 15, 26, 37, 48 }, };
		int[] gameBoard = new int[64];
		int counter = 0;
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				for (int k = 0; k <= 3; k++) {
					if (boardState[i][j][k] == playerPiece || boardState[i][j][k] == '-')
						gameBoard[counter] = 1;
					else
						gameBoard[counter] = 0;
					counter++;
				}
			}
		}
		for (int i = 0; i <= 75; i++) {
			counter = 0;
			for (int j = 0; j <= 3; j++) {
				if (gameBoard[wins[i][j]] == 1) {
					counter++;
					finalWin[j] = wins[i][j];
					if (counter == 4)
						winCounter++;
				}
			}
		}
		return winCounter;
	}
}