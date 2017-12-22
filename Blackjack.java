/*
       __                     ________    _      _                 
      / /___  ____ _____     / ____/ /_  (_)____(_)___  ____  _____
 __  / / __ \/ __ `/ __ \   / /   / __ \/ / ___/ / __ \/ __ \/ ___/
/ /_/ / /_/ / /_/ / / / /  / /___/ / / / / /  / / / / / /_/ (__  ) 
\____/\____/\__,_/_/ /_/   \____/_/ /_/_/_/  /_/_/ /_/\____/____/  

~Joan Chirinos, December 22, 2017
*/

import jutils.Keyboard;
import java.util.ArrayList;

public class Blackjack {

    public ArrayList<Integer> deck;
    public ArrayList<Integer> playerHand = new ArrayList<Integer>();
    public ArrayList<Integer> computerHand = new ArrayList<Integer>();
    public int playerCash;
    public int computerCash;
    public int totalBet = 0;

    public Blackjack() {
	playerCash = 100;
	computerCash = 100;
    }

    public Blackjack(int pc, int cc) {
	playerCash = pc;
	computerCash = cc;
    }

    public boolean playerTurn() {

	for (int i = 0; i < 2; i++) {
	    int indexToAdd = (int)(Math.random() * deck.size());
	    playerHand.add(deck.get(indexToAdd));
	    deck.remove(indexToAdd);
	}

	outside:
	while (true) {

	    System.out.println("You have " + playerCash + " dollars");

	    if (handSum(playerHand) > 21) {
		System.out.println("Bust!");
		return false;
	    }

	    if (handSum(playerHand) == 21) {
		System.out.println("Blackjack!");
		return true;
	    }

	    System.out.println("Hand: " + playerHand);
	    System.out.println("Sum: " + handSum(playerHand));

	    int pbet = 1;
	    top:
	    while (true) {
		boolean toBreak = true;
		System.out.print("How much do you want to bet?\n" +
				   "(You will bet at least 1 dollar)\nBet: ");
		try {
		    pbet = Integer.parseInt(Keyboard.readString());
		}
		catch (Exception e) { toBreak = false; }
		if (toBreak) break;
	    }
	    if (pbet <= 0) pbet = 1;
	    if (pbet <= playerCash) {
		playerCash -= pbet;
		totalBet += playerCash;
	    }
	    else {
		totalBet += playerCash;
		playerCash = 0;
	    }

	    //hit or hold
	    while (true) {
		System.out.print("hit or hold(hit, hold): ");
		String choice = Keyboard.readString();
		if (choice.equals("hit")) break;
		else if (choice.equals("hold")) break outside;
	    }

	    //draw card from deck
	    System.out.println();
	    int indexToAdd = (int)(Math.random() * deck.size());
	    playerHand.add(deck.get(indexToAdd));
	    deck.remove(indexToAdd);
	}

	    System.out.println("\nHand: " + playerHand);
	    System.out.println("Sum: " + handSum(playerHand));
	    

	    return true;
	    
    }

    public boolean computerTurn() {

	for (int i = 0; i < 2; i++) {
	    int indexToAdd = (int)(Math.random() * deck.size());
	    computerHand.add(deck.get(indexToAdd));
	    deck.remove(indexToAdd);
	}

	while (true) {

	    if (handSum(computerHand) == 21) break;
	    else if (handSum(computerHand) > 21) {
		System.out.println("\nComputer hand:  " + computerHand);
		System.out.println("Sum: " + handSum(computerHand));
		System.out.println("Computer: Dang it...");
		return false;
	    }
	    else if (handSum(computerHand) > handSum(playerHand)) break;

	    if (computerCash > playerCash && computerCash >= 10) {
		int compbet = (int)(Math.random() * 11);
		computerBet(compbet);
		System.out.println("The computer bet " + compbet + " dollars");
	    }

	    int indexToAdd = (int)(Math.random() * deck.size());
	    computerHand.add(deck.get(indexToAdd));
	    deck.remove(indexToAdd);
	    
	}

	System.out.println("\nComputer hand:  " + computerHand);
	System.out.println("Sum: " + handSum(computerHand));

	return true;
	
    }

    public void playerBet(int bet) {
	playerCash -= bet;
	totalBet += bet;
    }

    public void computerBet(int bet) {
	computerCash -= bet;
	totalBet += bet;
    }

    public void popDeck() {
	deck = new ArrayList<Integer>();
	for (int i = 1; i < 14; i++) {
	    deck.add(i);
	    deck.add(i);
	    deck.add(i);
	    deck.add(i);
	}
    }

    public int handSum(ArrayList<Integer> hand) {
	int sum = 0;
	for (int i = 0; i < hand.size(); i++)
	    sum += hand.get(i);
	return sum;
    }

    public void shuffle() {
	for (int i = 0; i < 100; i++) {
	    int indexOne = (int)(Math.random() * deck.size());
	    int indexTwo = (int)(Math.random() * deck.size());
	    deck.set(indexOne, deck.set(indexTwo, deck.get(indexOne)));
	}
    }

    public boolean playGame() {

	popDeck();
	shuffle();
	if (!playerTurn()) {
	    System.out.println("\nComputer: Woot woot!");
	    System.out.println("Computer wins!");
	}
	else if (!computerTurn()) {
	    System.out.println("\nYou: Woot woot!");
	    System.out.println("You win!");
	}
	else if (handSum(playerHand) > handSum(computerHand)) {
	    System.out.println("\nYou: Woot woot!");
	    System.out.println("You win!");
	}
	else if (handSum(playerHand) == handSum(computerHand)) {
	    System.out.println("\nYou and Computer: It's a tie!");
	}
	else {
	    System.out.println("\nComputer: Woot woot!");
	    System.out.println("Computer wins!");
	}

	if (computerCash <= 0) {
	    System.out.println("You won! The computer has no money left!");
	    return true;
	}
	else if (playerCash <= 0) {
	    System.out.println("You lost! You have no money left!");
	    return true;
	}

	return false;

    }

    public static void main(String[] args) {
	Blackjack game;
	game = new Blackjack();
	game.playGame();
	while (true) {
	    System.out.print("\n\nPlay again? (y/n): ");
	    String choice = Keyboard.readString();
	    if ( !(choice.equals("y")) && !(choice.equals("Y")) ) break;
	    else System.out.println("\n\n");
	    game = new Blackjack(game.playerCash, game.computerCash);
	    if (game.playGame()) break;
	}
    }

}//end class
