/*
Project: Hand of Destiny
*/
public class Gameutil {
    private static int score = 0;

    public static String msg(String code){

        if(code.contains(":")){
            String[] part = code.split(":"); //splits at the colon
            String status = part[0];               //into status of enemy
            String dmg = part[1];                  //and damage dealt to/taken from enemy
        
            if (status.equals("not dead")) return "HIT! You dealt " + dmg + " damage. The enemy holds on!";
            if (status.equals("you won")) return "CRITICAL HIT! You dealt " + dmg + " damage and finished them!";
            if (status.equals("you lose")) return "OUCH! The enemy countered and you took " + dmg + " damage!";
        }


        if (code.equals("tie")) return "Stalemate! Both moves canceled out."; //if both the user and the enemy did the same action
        return "Invalid action!"; //the action(s) provided is(are) invalid
    }

    public static void saveHighScore(WaveTracker current){

        WaveTracker highscore = new WaveTracker(score);

        if (current.compareTo(highscore) > 0){
            score = current.getWave();
            System.out.println("\n*********NEW PERSONAL BEST ACHIEVED! HIGHEST WAVE ACHIEVED: " + score + "*********");
        }
        else{
            System.out.println("*********Current session record: Wave " + score + "*********");
        }
    }

}
