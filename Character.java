/*
Project: Hand of Destiny
*/
import java.util.ArrayList;

public class Character {

    private int atk = 1; //attack
    private int hp = 5;  //hit points or health
    private int money = 5; //money
    private String weapon = "basic sword";

    public Character(int atk, int hp, int money, String weapon){
        this.atk = atk; 
        this.hp = hp;
        this.weapon = weapon;
        this.money = money;
    }

    //setters
    public void setAtk(int atk) {
        this.atk = atk;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    //getters
    public int getAtk() {
        return atk;
    }
    public int getHp() {
        return hp;
    }
    public String getWeapon() {
        return weapon;
    }
    public int getMoney() {
        return money;
    }
    public int hpLoss(int damage){
        return this.hp -= damage;
    }


    public void addMoney(int amount){
        this.money += amount;
    }
    
    public String action(String choice, Enemy target, String playerMove){
        //if its not "attack" and one of rock, paper, or scissors then it will not work
        if (choice.equalsIgnoreCase("attack") && (playerMove.equalsIgnoreCase("rock") || playerMove.equalsIgnoreCase("paper") || playerMove.equalsIgnoreCase("scissors"))){

            ArrayList<Integer> combatData = rpsCombat(target, playerMove);

            return BattleProcessing(combatData, target);
        }

        return "That's not a valid action"; // and output this instead to avoid breaking the code
    }

    public String BattleProcessing(ArrayList<Integer> res, Enemy target){

        if (res.get(1) == 3){  //enemy has been hit but not yet dead to move onto the next wave
            return "not dead:" + this.getAtk();
        }
        else{
            if (res.get(1) == 0){
            return "tie";
            }
            else if (res.get(1) == 1){
                return "you won:" + this.getAtk(); //the ":" is added for the message formatting in Gameutil
            }
            else{
                return "you lose:" + target.getAtk(); //the ":" is added for the message formatting in Gameutil
            }
        }
        
    }

    public ArrayList<Integer> rpsCombat(Enemy target, String playerMove){
        int enemyRps = target.getAIMove(); //gets the Ai's move from enemy file
        ArrayList<Integer> res = new ArrayList<>(); // arraylist consisting player move, and battle outcome
        
        int pMove = -1;
        if (playerMove.equalsIgnoreCase("rock")){
            pMove = 0;
            res.add(0); // rock -> index 0 of ArrayList
        }
        else if (playerMove.equalsIgnoreCase("paper")){
            pMove = 1;
            res.add(1); // paper -> index 0 of ArrayList
        } 
        else if (playerMove.equalsIgnoreCase("scissors")){
            pMove = 2;
            res.add(2); // scissor -> index 0 of ArrayList
        }

        if (pMove == enemyRps) res.add(0); // tie -> index 1 of ArrayList
        else if(pMove == 0 && enemyRps == 2 || pMove == 1 && enemyRps == 0 || pMove == 2 && enemyRps == 1){
            //           r                s             p                r             s                p
            target.setHp(target.getHp() - this.getAtk());
            
            if (target.getHp() <= 0){
                res.add(1); // target dies -> index 1 of ArrayList
            }
            else{
                res.add(3); // target hit but alive -> index 1 of ArrayList
            }
        }
        else{
            this.setHp(this.getHp() - target.getAtk());
            res.add(2); // player took dmg -> index 1 of ArrayList
        }

        return res;
    }

    @Override
    public String toString() {
        //formats the characters stats into a cohesive format
        return String.format("Status [HP: %d | Atk: %d | Weapon: %s | Money: %d]", 
                            this.getHp(), this.getAtk(), this.getWeapon(), this.getMoney());
    }

    @Override
    public boolean equals(Object o) {
        // Check if the other object is actually a Character
        if (this == o) return true;
        if (o == null || !(o instanceof Character)) return false;

        //casts character object onto "o"
        Character other = (Character) o;

        // Compare stats to see if they are "equal"
        return this.getAtk() == other.getAtk() && this.getWeapon().equals(other.getWeapon());
    }
}

