/*
Project: Hand of Destiny
*/
import java.util.Random;

public class Enemy extends Character {
    public Enemy(int atk, int hp, int money, String weapon){
        super(atk, hp, money, weapon);
    }

    //setter
    public void setHp(int hp) {
        super.setHp(hp);
    }
    
    //getter
    public int getHp() {
        return super.getHp();
    }

    public int moneyCalc(){
        int drops;

        Random rand = new Random();
        WaveTracker tracker = new WaveTracker();

        if (tracker.getWave() < 3){
            drops = rand.nextInt(11);
        }
        else if(tracker.getWave() < 7){
            drops = rand.nextInt(40);
        }
        else{
            drops = rand.nextInt(60);
        }

        return drops;
    }

    //does the rng for the AI
    public int getAIMove() {
        Random rand = new Random();
        return rand.nextInt(3); // Returns 0, 1, or 2 where they are rock, paper, or scissors respectively
    }

    @Override
    public String toString() {
        //formats the Enemy stats into a cohesive format
        return String.format("Enemy  [HP: %d | Atk: %d | Weapon: %s | Money: %d]", 
                            this.getHp(), this.getAtk(), this.getWeapon(), this.getMoney());
    }
}
