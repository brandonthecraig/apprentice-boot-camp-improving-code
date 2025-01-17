package com.adaptionsoft.games.uglytrivia;

public class Game {
    Players players;
    int[] playerBoardPosition = new int[6];

    Board boardClass;
    
    int currentPlayer = 0;
    boolean isPlayerGettingOutOfPenaltyBox;
    
    public  Game(){
    	boardClass = new Board();
    	players = new Players();

    }

	public boolean addPlayer(String playerName) {
		
		int initialPlayerBoardPosition = 0;
	    playerBoardPosition[howManyPlayers()] = initialPlayerBoardPosition;
	    players.add(playerName);
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.currentPlayer().getName() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (players.currentPlayer().isInPenaltyBox()) {
			if (isOdd(roll)) {
				isPlayerGettingOutOfPenaltyBox = true;
				
				System.out.println(players.currentPlayer().getName() + " is getting out of the penalty box");
				updatePosition(roll);
			} else {
				System.out.println(players.currentPlayer().getName() + " is not getting out of the penalty box");
				isPlayerGettingOutOfPenaltyBox = false;
				}
			
		} else {

			updatePosition(roll);
		}
		
	}

	private boolean isOdd(int roll) {
		return roll % 2 != 0;
	}

	private void updatePosition(int roll) {
    	int numberOfBoardPositions = 12;
		playerBoardPosition[currentPlayer] = playerBoardPosition[currentPlayer] + roll;
		if (playerBoardPosition[currentPlayer] >= numberOfBoardPositions)
			playerBoardPosition[currentPlayer] = playerBoardPosition[currentPlayer] - numberOfBoardPositions;
		System.out.println(players.currentPlayer().getName()
				+ "'s new location is "
				+ playerBoardPosition[players.getCurrentPlayer()]);
		System.out.println("The category is " + currentCategory());
		boardClass.askQuestion(currentCategory());
	}
	
	
	private String currentCategory() {
    	int currentPosition = playerBoardPosition[players.getCurrentPlayer()];
		return boardClass.getTopicFromPosition(currentPosition);
	}

	public boolean wasCorrectlyAnswered() {
		if (players.currentPlayer().isInPenaltyBox()){
			if (isPlayerGettingOutOfPenaltyBox) {
				String message = "Answer was correct!!!!";
				updatePlayerCoinCount(message);

				boolean didPlayerWin = didPlayerWin();
				changeCurrentPlayer();
				players.changeCurrentPlayer();

				return didPlayerWin;
			} else {
				changeCurrentPlayer();
				players.changeCurrentPlayer();
				return true;
			}
			
			
			
		} else {

			String message = "Answer was corrent!!!!";
			updatePlayerCoinCount(message);

			boolean didPlayerWin = didPlayerWin();
			changeCurrentPlayer();
			players.changeCurrentPlayer();

			return didPlayerWin;
		}
	}

	private void changeCurrentPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
	}

	private void updatePlayerCoinCount(String message) {
		System.out.println(message);
		players.currentPlayer().incrementCoinCount();
		System.out.println(players.currentPlayer().getName()
				+ " now has "
				+ players.currentPlayer().getCoinCount()
				+ " Gold Coins.");
	}

	public boolean wasIncorrectlyAnswered(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.currentPlayer().getName()+ " was sent to the penalty box");
		players.currentPlayer().setInPenaltyBox(true);

		changeCurrentPlayer();
		players.changeCurrentPlayer();
		return true;
	}


	private boolean didPlayerWin() {
    	return (players.currentPlayer().getCoinCount() !=6);
	}
}
