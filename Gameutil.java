/*
Developer: Mushfiqul Islam
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

    public static String saveHighScore(WaveTracker current){//saves the highscore and returns a message to be displayed on the game over screen
        WaveTracker highscore = new WaveTracker(score);

        if (current.compareTo(highscore) > 0){
            score = current.getWave();
            return "NEW PERSONAL BEST! HIGHEST WAVE: " + score;
        }
        else{
            return "           Current session record: Wave " + score;
        }
    }

}
