/*
Project: Hand of Destiny
*/

import java.util.Scanner;

public class ProjectOneTester{
    public static void main(String[] args) {
        //initialting the user input system
        Scanner input = new Scanner(System.in);
        boolean playing = true; //controls main loop
        
        //main play loop (just so that the highscore tracker updates)
        while(playing){

            //make objects
            Character player = new Character(10, 5, 0, "Basic Sword");
            Enemy monster = new Enemy(1, 5, 0, "Stick");
            monster.setMoney(monster.moneyCalc());
            WaveTracker tracker = new WaveTracker();

            //GAME LOOP
            while (player.getHp() > 0){
                //header
                System.out.println("\n=========================================================================================");
                System.out.println("                                       WAVE " + tracker.getWave());
                System.out.println("=========================================================================================");

                //Character Stats (User and Enemy)
                System.out.println(player.toString());
                System.out.println(monster.toString());
                System.out.println("-----------------------------------------------------------------------------------------");

                //User input section
                System.out.println("Please type in your selection to the terminal.");
                System.out.println("\nAction [ Attack ]: ");
                String actionInput = input.next();
                System.out.print("\nChoose your move [ Rock | Paper | Scissors ]: ");
                String moveInput = input.next();

                //sends the user input through the system to check if they won against the enemy
                String result = player.action(actionInput, monster, moveInput);
                System.out.println("\n~~~~ " + Gameutil.msg(result) + " ~~~~");

                //if monster is killed, then move onto next wave
                if (result.contains("you won") || monster.getHp() <= 0) {
                    int earnings = monster.getMoney();
                    player.setMoney(earnings);
                    int nextWave = tracker.getWave() + 1; 
                    tracker.setWave(nextWave); 
                    
                    System.out.println("\n*** ENEMY DEFEATED! Leveling up... ***");

                    //generates new enemy
                    monster = new Enemy(nextWave, nextWave * 5, 0, "Steel Blade"); 
                }
            }

            System.out.println("\n                GAME OVER. You reached Wave " + tracker.getWave());

            //updates the highscore
            Gameutil.saveHighScore(tracker);
            playing = false;
        }

        System.out.println("\nThanks for playing!");
        input.close();
    }

}