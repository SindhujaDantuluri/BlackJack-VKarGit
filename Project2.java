import java.util.Scanner; //Imports scanner

public class Project2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Define/Initialize variables
        Deck deck = new Deck(); //Makes instance of deck class

        Card player = new Card();  //Makes instance of card class for player

        Card dealer = new Card(); //Makes instance of card class for player
        int rounds = 0;

        String playerChoice;
        String play = "Yes";

        System.out.println("You are playing black jack!");
        System.out.println("The goal of the game is get as close to 21 as possible without passing it.");
        System.out.println("You start off with 2 cards and if the sum isn't 21, then you can decide to hit(get another card) or stay(stick with current cards).\n");
        System.out.println("NOTE: If you pull an ace, then the value of it will be 11 unless it makes you bust.");
        System.out.println("      In that case, the value of the ace is 1.\n");
        System.out.println("Good luck!\n");


        do {
            deck.fillDeck(); //Starts game with a full deck


            int[] playerCards = new int[52]; //Creates array to store all cards pulled by player
            int playerCardCount = 2; //Keeps track of number of cards the player has

            int[] dealerCards = new int[52]; //Creates array to store all cards pulled by player
            int dealerCardCount = 2; //Keeps track of number of cards the player has

            if (rounds > 0) { //Asks user if they would like to play the game again only after the first round
                System.out.print("\nWould you like to play again?");
                play = input.nextLine();
                System.out.print("");

                if (play.equalsIgnoreCase("no")) {
                    System.out.println("Thanks for playing!");
                    break;
                }
            }

            System.out.println("Your first 2 cards are: ");

            for (int j = 0; j < playerCardCount; j++) { //Loops two times(Gives player 2 cards)
                player.setHandValue(playerCards); //Calculates the value of the cards the player has picked
                //Loops until a card is found that hasn't been used
                do {
                    deck.setRandCard();
                } while (deck.getRandCard().equals("R"));

                playerCards[j] = player.getCardValue(deck.getRandCard(), player.getHandValue()); //Finding the value of the random card
                System.out.println(deck.getRandCard()); //Prints the cards

                deck.removeCard(deck.getRandCard()); //Removes card from the deck(replaces the String value with "R")
            }


            player.setHandValue(playerCards); //Calculates the value of the cards the player has picked

            if (player.getHandValue() == 21) { //Checks if the sum of the dealer's cards are 21
                System.out.println("Congratulations! You won the game!");
                rounds++;
                continue;
            } else if (player.getHandValue() > 21) {
                System.out.println("You have busted!");
                rounds++;
                continue;
            }


            System.out.println();
            System.out.println("One of the dealers cards are: ");

            for (int j = 0; j < dealerCardCount; j++) {
                dealer.setHandValue(dealerCards); //Sets the value of the dealer's deck

                //Loops until a card is found that hasn't been used
                do {
                    deck.setRandCard();
                } while (deck.getRandCard().equals("R"));

                if (j == 0) { //Only displays one of the dealer's cards
                    System.out.println(deck.getRandCard());
                    System.out.println("");
                }

                dealerCards[j] = dealer.getCardValue(deck.getRandCard(), dealer.getHandValue()); //Finding the point value of the random card

                deck.removeCard(deck.getRandCard()); //Removes card from the deck(replaces the String value with "R")
            }
            dealer.setHandValue(dealerCards); //Calculates the value of the cards the dealer has picked

            if (dealer.getHandValue() == 21) { //Checks if the sum of the dealer's cards are 21
                System.out.println("The dealer has won.");
                rounds++;
                continue;
            } else if (dealer.getHandValue() > 21) {
                System.out.println("The dealer busted so you won!");
                rounds++;
                continue;
            }

            //hit/stay for player
            do {
                System.out.print("Would you like to hit or stay?");
                playerChoice = input.nextLine();    //Creates variable from input
                System.out.println();

                if (playerChoice.equalsIgnoreCase("Hit")) { //If the user's choice is hit then the dealer(computer) gives the player another card
                    playerCardCount++;
                    System.out.println("This is your new card: ");

                    //Loops until a card is found that hasn't been used
                    do {
                        deck.setRandCard();
                    } while (deck.getRandCard().equals("R"));

                    playerCards[playerCardCount - 1] = player.getCardValue(deck.getRandCard(), player.getHandValue()); //finding the value of the random card
                    System.out.println(deck.getRandCard() + "\n"); //Prints the new card

                    deck.removeCard(deck.getRandCard()); //Removes card from the deck(replaces the String value with "R")

                    player.setHandValue(playerCards); //Sets the total point value of the player's hand

                    if (player.getHandValue() == 21) { //Checks if the sum of the dealer's cards are 21
                        break;
                    } else if (player.getHandValue() > 21) {
                        break;
                    }
                }
            } while (playerChoice.equalsIgnoreCase("Hit"));

            player.setHandValue(playerCards); //Calculates the value of the cards the player has picked

            if (player.getHandValue() == 21) { //Checks if the sum of the dealer's cards are 21
                System.out.println("Congratulations! You won the game!");
                rounds++;
                continue;
            } else if (player.getHandValue() > 21) {
                System.out.println("You have busted!");
                rounds++;
                continue;
            }

            System.out.println("Now it's the dealer's turn!");

            //Computer/dealer's automated hit/stay logic
            while (dealer.getHandValue() < 17) {
                dealerCardCount++;

                //Loops until a card is found that hasn't been used
                do {
                    deck.setRandCard();
                } while (deck.getRandCard().equals("R"));

                dealerCards[dealerCardCount - 1] = dealer.getCardValue(deck.getRandCard(), dealer.getHandValue()); //finding the value of the random card

                deck.removeCard(deck.getRandCard()); //Removes card from the deck(replaces the String value with "R")

                dealer.setHandValue(dealerCards); //Calculates the value of the cards the dealer has picked


                if (dealer.getHandValue() == 21) { //Checks if the sum of the dealer's cards are 21
                    rounds++;
                    break;
                } else if (dealer.getHandValue() > 21) {
                    rounds++;
                    break;
                }
            }

            if (dealer.getHandValue() == 21) { //Checks if the sum of the dealer's cards are 21
                System.out.println("The dealer has won.");
                rounds++;
                continue;
            } else if (dealer.getHandValue() > 21) {
                System.out.println("The dealer busted so you won!");
                rounds++;
                continue;
            }

            //Tells if player or dealer won
            player.setHandValue(playerCards);
            dealer.setHandValue(dealerCards);

            if (dealer.getHandValue() > player.getHandValue()) { //Finds if the dealer or the player is the winner
                System.out.println("The dealer won the game with a total of " + dealer.getHandValue() + " while you had a total of " + player.getHandValue() + ".");
            } else if (dealer.getHandValue() < player.getHandValue()) {
                System.out.println("You won with a total of " + player.getHandValue() + " while the dealer had a total of " + dealer.getHandValue() + ".");
            } else {
                System.out.println("Both you and the dealer tied with " + dealer.getHandValue() + " points.");
            }

            rounds++;
        } while (play.equalsIgnoreCase("yes"));
    }
}

class Card {
    private int handValue = 0;
    private String cardRank;
    
    public int getCardValue(String cardVisual, int totalHandValue) { //Gets the value of the card
        cardRank = cardVisual.substring(0, cardVisual.indexOf("-"));
        if (cardRank.equals("J") || cardRank.equals("Q") || cardRank.equals("K")) { //Extracts rank
            cardRank = "10";
        } else if (cardRank.equals("A")) { //If the hand total plus 11 doesn't make the player/dealer bust, then the value of the ace is 11
            if (totalHandValue + 11 <= 21) {
                cardRank = "11";
            } else {
                cardRank = "1";
            }
        }

        return Integer.valueOf(cardRank);
    }


    public void setHandValue(int[] hand) { //Calculates the hand value
        handValue = 0;
        for (int j = 0; j < hand.length; j++) {
            handValue += hand[j];
        }
    }

    public int getHandValue() { //Returns the hand value
        return handValue;
    }
}

class Deck {
    private int elementNum;
    private String[] deck = new String[52];
    private String suit;
    private String card;
    private int counter = 0;
    private boolean isEmpty = true;

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

    public void setRandCard() { //Getting an element number for the array from 1 to 52
        elementNum = (int) (Math.random() * 52);
    }

    public String getRandCard() { //Finds element in the deck and returns it
        //Check if the deck is empty and fill
        if (isDeckEmpty()) {
            fillDeck();
        }
        return deck[elementNum];
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
}
