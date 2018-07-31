package Project2;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Scanner; //Imports scanner

public class Project2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Define/Initialize variables
        Deck deck = new Deck(); //Makes instance of deck class

        Card card = new Card(); //Makes instance of card class
        int rounds = 0;

        boolean nextLoop;

        String playerChoice;
        String play = "yes";

        System.out.println("You are playing black jack!");
        System.out.println("The goal of the game is get as close to 21 as possible without passing it.");
        System.out.println("You start off with 2 cards and if the sum isn't 21, then you can decide to hit(get another card) or stay(stick with current cards).\n");
        System.out.println("NOTE: If you pull an ace, then the value of it will be 11 unless it makes you bust.");
        System.out.println("      In that case, the value of the ace is 1.\n");
        System.out.println("Good luck!\n");

        do {

            deck.fillDeck(); //Starts game with a full deck  //////////////////////////////d

            Hand playerHand = new Hand(); //Makes instance of hand class for player
            Hand dealerHand = new Hand(); //Makes instance of hand class for dealer

            if (rounds > 0) { //Asks user if they would like to play the game again only after the first round
                System.out.print("\nWould you like to play again? ");
                play = input.nextLine();

                if (play.equalsIgnoreCase("no")) {
                    System.out.println("\nThanks for playing!");
                    break;
                }
            }

            System.out.println("Your first 2 cards are: ");

            for (int j = 0; j < 2; j++) { //Picks first two cards for player
                pickCard(playerHand, deck, card, true);
                playerHand.setCardCount();
            }

            System.out.println(playerHand.getHandValue()); //Prints the cards

            if (winOrBustPlayer(playerHand)) { //Checks if the player has won or busted
                rounds++;
                continue;
            }

            System.out.println("\nOne of the dealers cards are: ");

            for (int j = 0; j < 2; j++) {
                if (j == 0) { //Only displays one of the dealer's cards
                    pickCard(dealerHand, deck, card, true);
                } else {
                    pickCard(dealerHand, deck, card, true);///////////////////////////////////////////////////////
                }

                dealerHand.setCardCount();
            }

            System.out.println(dealerHand.getHandValue()); //Prints the cards

            if (winOrBustDealer(dealerHand)) {
                rounds++;
                continue;
            }

            do {
                nextLoop = false;
                System.out.print("Would you like to hit or stay?");
                playerChoice = input.nextLine();    //Creates variable from input
                System.out.println();

                if (playerChoice.equalsIgnoreCase("Hit")) { //If the user's choice is hit then the dealer(computer) gives the player another card
                    System.out.println("This is your new card: ");

                    pickCard(playerHand, deck, card, true);
                    playerHand.setCardCount();

                    if (winOrBustPlayer(playerHand)) {
                        rounds++;
                        nextLoop = true; //variable nextLoop lets the next round start since the player won or bust
                        break;
                    }
                }
            } while (playerChoice.equalsIgnoreCase("Hit"));
             if (nextLoop) {
                continue;
            }

            System.out.println("Now it's the dealer's turn!");

            //Computer/dealer's automated hit/stay logic
            while (dealerHand.getHandValue() < 17) {
                pickCard(dealerHand, deck, card, true); /////////////////////////////////////
                dealerHand.setCardCount();

                if (winOrBustDealer(dealerHand)) {
                    rounds++;
                    nextLoop = true; //variable nextLoop lets the next round start since the player won or bust
                    break;
                }
            }

            if (nextLoop) {
                continue;
            }

            if (dealerHand.getHandValue() > playerHand.getHandValue()) { //Finds if the dealer or the player is the winner
                System.out.println("The dealer won the game with a total of " + dealerHand.getHandValue() + " while you had a total of " + playerHand.getHandValue() + ".");
            } else if (dealerHand.getHandValue() < playerHand.getHandValue()) {
                System.out.println("You won with a total of " + playerHand.getHandValue() + " while the dealer had a total of " + dealerHand.getHandValue() + ".");
            } else {
                System.out.println("Both you and the dealer tied with " + dealerHand.getHandValue() + " points.");
            }

            rounds++;
        } while (play.equalsIgnoreCase("yes"));
    }

    public static void pickCard(Hand hand, Deck deck, Card card, boolean printTrue) {
        deck.setRandCardVisual();
        card.setCardRank(deck.getRandCardVisual());
        hand.addCard(hand.getCardValue(card.getCardRank()));
        hand.setHandValue();
        if (printTrue) {
            System.out.println(deck.getRandCardVisual()); //Prints the cards
        }
        deck.removeCard(deck.getRandCardVisual());
    }

    public static boolean winOrBustPlayer(Hand hand) {
        hand.setHandValue();
        if (hand.getHandValue() == 21) { //Checks if the sum of the dealer's cards are 21
            System.out.println("Congratulations! You won the game!");
            return true;
        } else if (hand.getHandValue() > 21) {
            System.out.println("You have busted!");
            return true;
        } else {
            return false;
        }
    }


    public static boolean winOrBustDealer(Hand hand) {
        hand.setHandValue();
        if (hand.getHandValue() == 21) { //Checks if the sum of the dealer's cards are 21
            System.out.println("The dealer has won.");
            return true;
        } else if (hand.getHandValue() > 21) {
            System.out.println("The dealer busted so you won!");
            return true;
        } else {
            return false;
        }
    }
}

class Hand {

    int[] handArray = new int[52];
    private int handValue = 0;
    int cardCount = 0;
    int cardValue = 0;

    public void addCard(int card) {
        handArray[cardCount + 1] = card;
    }

    public void setCardCount() {
        cardCount++;
    }

    public int getCardCount() {  /////////////////////////////////////////////////
        return cardCount;
    }

    public void setHandValue() { //Calculates the hand value
        handValue = 0;
        for (int j = 0; j < handArray.length; j++) {
            handValue += handArray[j];
        }


        if (handValue > 21) { //Changes the value of all aces valuing to 11 and change it's value to 1 so the player/dealer doesn't bust
            for (int j = 0; j < handArray.length; j++) {
                if (handArray[j] == 11){
                    handArray[j] = 1;
                }
                handValue += handArray[j];
            }
        }
      //  System.out.println("handValue " + handValue);
    }

    public int getHandValue() { //Returns the hand value
        return handValue;
    }

    public int getCardValue(String cardRank) { //Gets the value of the card
        if (cardRank.equals("J") || cardRank.equals("Q") || cardRank.equals("K")) { //Extracts rank
            cardValue = 10;
        } else if (cardRank.equals("A")) { //If the hand total plus 11 doesn't make the player/dealer bust, then the value of the ace is 11
            if (handValue + 11 <= 21) {
                cardValue = 11;
            } else {
                cardValue = 1;
            }
        } else {
            cardValue = Integer.valueOf(cardRank);
        }
        return cardValue;
    }

}

class Card {
    private String cardRank;

    public void setCardRank(String cardVisual) { //Gets the rank of the card
        cardRank = cardVisual.substring(0, cardVisual.indexOf("-"));
    }

    public String getCardRank() {
        return cardRank;
    }

}

class Deck {
    private int elementNum;
    private String[] deck = new String[52];
    private String suit;
    private String card;
    private int counter = 0;
    private boolean isEmpty = true;

    private String randCardVisual;

    public void fillDeck() { //Fills deck with all cards
        counter = 0;
        for (int j = 1; j <= 4; j++) {
            if (j == 1) {
                suit = "H"; //Hearts
            } else if (j == 2) {
                suit = "S"; //Spades
            } else if (j == 3) {
                suit = "C"; //Club
            } else {
                suit = "D"; //Diamond
            }
            for (int k = 1; k <= 13; k++) {

                if (k == 1) {
                    card = "A";
                } else if (k == 11) {
                    card = "J";
                } else if (k == 12) {
                    card = "Q";
                } else if (k == 13) {
                    card = "K";
                } else {
                    card = Integer.toString(k);
                }

                deck[counter] = card + "-" + suit;
                counter++;

            }
        }
    }

    public void removeCard(String card) { //Removes the card from the deck(replaces it with R in the array)
        for (int j = 0; j < deck.length; j++) {
            if (deck[j].equals(card)) {
                deck[j] = "R";  //R means the card is removed
            }
        }
    }

    public void setRandCardVisual() { //Getting an element number for the array from 1 to 52

        //Check if the deck is empty and fill
        if (isDeckEmpty()) {
            System.out.println("Deck is empty");
            fillDeck();
        }

        //Loops until a card is found that hasn't been used
        do {
            elementNum = (int) (Math.random() * 52);
        } while (deck[elementNum].equals("R"));
        randCardVisual = deck[elementNum];
    }

    public String getRandCardVisual() { //Finds element in the deck and returns it
        //Check if the deck is empty and fill
        if (isDeckEmpty()) {
            fillDeck();
        }
        return randCardVisual;
    }

    public boolean isDeckEmpty() { //Checks if the deck is empty
        for (int j = 0; j < deck.length; j++) {
            if (!deck[j].equals("R")) {  //Check if there is any occurence of not R character
                isEmpty = false;
                break;
            } else {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    public void displayDeck() {
        for (int j = 0; j < deck.length; j++) {
            System.out.print(deck[j] + "\t");
        }
    }
}

