/*
 * Calculator.java
 *
 * Version: 2.4: Calculator.java,v 2.4 9/7/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *            2.1 adding power operator
 *            2.2 processing parenthesis
 *            2.3 removed try-catch
 *            2.4 removed static methods
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class FileReading {

	public static void main(String[] args) {
		
		int totalPlayers=0;
			// Taking input from user about total number of players and names.
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter total number of players playing the game.");
			if(sc.hasNextInt()) {
				totalPlayers = sc.nextInt();
			}
			else {
				System.exit(0);
			}
			System.out.println("Enter names of all the players.");
			int count =0;
			String [] allNames = new String[totalPlayers];
			while(count<totalPlayers) {
				allNames[count] = sc.next();
				count++;
			}
			hangman(allNames,totalPlayers);
			sc.close();
	}
	
	public static String randomWord() {
		String word ="";
		try {
			System.out.println("Guess the below word, you have 8 chances, best luck!!");
			// getting random word from the given text file.
			Random rnd = new Random();
			int randomNumber = rnd.nextInt(3);
			File file = new File("Kapil.txt");
			BufferedReader fr = new BufferedReader(new FileReader(file));
			for(int counter=0;counter<randomNumber;counter++) {
				fr.readLine();
			}
			// Randomly generated word.
			word = fr.readLine();
			fr.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
		return word;
	}
	
	public static void displayWord(StringBuffer guessWord) {
		
		// Displaying word to guess with white spaces in between them.
		for(int letter=0;letter<guessWord.length();letter++) {
			System.out.print(Character.toUpperCase(guessWord.charAt(letter))+" ");
		}
		System.out.println();
	}
	
	public static void hangman(String[] allNames, int totalPlayers) {
		// Used for scoring of players.
		int scoreCounter=0;
		int [] scores = new int[totalPlayers];
		try {
			Scanner sc = new Scanner(System.in);
			// Starting game giving each player their turn to play.
			for(String name: allNames)
			{
				// We are giving each player 8 chances to guess word.
				int missCount=8;
				System.out.println("\n\n"+name+ "'s turn: ");
				
				// Each player can play until he finishes all his chances.
				while(missCount>=0)
				{	
					// It returns randomly generated word.
					String word = randomWord();
					// Displaying dashes instead of word that player has to guess.
					StringBuffer guessWord = new StringBuffer();
					for(int dash=0;dash<word.length();dash++) {
						guessWord.append("-");
					}
					
					displayWord(guessWord);
					
					// Total number of unguessed characters in word.
					int countDash=word.length();
	
					// Player's turn will end when he uses all of his chances.
					while(missCount>=0 && countDash>0) {
						String guessChar = sc.next();
						
						// Player should enter one letter at a time.
						if(guessChar.length()>1) {
							System.out.println("Please enter one letter at a "
									+ "time.\n \n");
							displayWord(guessWord);
							continue;
						}
						/* Flag to check if the letter entered is already 
						 * guessed or not.*/
						int alreadyExist=0;
						
						/* Checking whether letter entered is already guessed
						 * or not*/
						for(int check=0;check<guessWord.length();check++) {
							if(Character.toUpperCase(guessChar.charAt(0))==
									Character.toUpperCase(guessWord.charAt(check))) {
								alreadyExist=1;
							}
						}
						
						// If letter is already guessed, asks user to try again.
						if(alreadyExist==1) {
							System.out.println("Letter already guessed. "
									+ "Try another one.\n \n");
							displayWord(guessWord);
							continue;
						}
				
						/* Checking whether players guess is correct or not,
						 * If correct then displaying it on screen. */ 
						
						int flag=0;
						for(int check=0;check<word.length();check++) {
							if(word.charAt(check)==Character.toUpperCase
									(guessChar.charAt(0))) {	
							guessWord.replace(check,check+1, 
									String.valueOf(guessChar.toString()));
							flag=1;
							countDash--;
							}
						}
						
						/* If players guess is incorrect and he used up all of
						 * his chances then its game over for player otherwise
						 * it displays chances remaining for player.If guess is 
						 * correct display letter and message. */ 
						if(flag==0) {
							missCount--;
							if(missCount<0) {
								System.out.println("\n \nGame Over.");
								/* If word is not guessed by player then deduct
								 * 5 points from final score.*/ 
								if(scores[scoreCounter]>=5) {
									scores[scoreCounter]=scores[scoreCounter]-5;
								}
								scoreCounter++;
								
								/* If it is game over for player then draws hang
								 * man figure. */
								BufferedReader hfr = new BufferedReader(new FileReader
										(new File("hangman.txt")));
								String hangman="";
								while((hangman=hfr.readLine())!=null) {
									System.out.println(hangman);
								}
								hfr.close();
							}
							else {
								System.out.println("Oops.. Incorrect guess. "
										+ "Chances remaining: " 
										+ missCount+"\n \n");
							}
	
						}
						else {
							System.out.println("Good Job!!\n \n");
						}
						
						displayWord(guessWord);
					}
					
					/* If user guessed complete word, display success message
					 * and add 10 points in his score. */ 
					if(countDash==0) {
						scores[scoreCounter]=scores[scoreCounter]+10;
						System.out.println("Excellent. that's correct!!!"
								+ " Try next one \n \n ");
						missCount=8;
					}

				}
			
			}
			
			// At last displays final score board for all players.
			System.out.println("\n \n \nFinal score board is");
			for(int finalScore=0;finalScore<totalPlayers;finalScore++) {
				System.out.println(allNames[finalScore]+" : "
				+scores[finalScore]);
			}
			sc.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
