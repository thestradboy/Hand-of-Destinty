/*
Developer: Mushfiqul Islam
Project: Hand of Destiny
*/

import java.util.*;

public class Shop {
    private ArrayList<ShopItem> basicTier = new ArrayList<>();
    private ArrayList<ShopItem> midTier = new ArrayList<>();
    private ArrayList<ShopItem> godTier = new ArrayList<>();
    private int rerollCost = 5;
    private boolean hpBought = false;

    public Shop(){
        //basic tier shop
        basicTier.add(new ShopItem("Plastic Bow", 2, 1));
        basicTier.add(new ShopItem("Stone Spear", 3, 2));
        basicTier.add(new ShopItem("Stone Axe", 4, 3));
        basicTier.add(new ShopItem("Stone Sword", 5, 4));
        basicTier.add(new ShopItem("Dual Sword", 6, 7));
        basicTier.add(new ShopItem("Land Mine", 7, 9));
        basicTier.add(new ShopItem("Glock", 10, 13));
        basicTier.add(new ShopItem("Stick of Majik?", 13, 15));

        //mid tier shop
        midTier.add(new ShopItem("Longbow", 19, 19));
        midTier.add(new ShopItem("Double-Sided \nSpear", 22, 20));
        midTier.add(new ShopItem("Hunting Axe",24 , 23));
        midTier.add(new ShopItem("Katana", 27, 25));
        midTier.add(new ShopItem("C4", 30, 29));
        midTier.add(new ShopItem("AR-47", 35, 32));
        midTier.add(new ShopItem("Staff of Power", 40, 37));

        //god tier shop
        godTier.add(new ShopItem("Compound Bow", 41, 40));
        godTier.add(new ShopItem("Massive Javelin", 44, 45));
        godTier.add(new ShopItem("Dwarven \nHunting \nAxe", 48, 53));
        godTier.add(new ShopItem("Greatsword \nof \nLegends", 60, 70));
        godTier.add(new ShopItem("NUKE", 100, 130));
        godTier.add(new ShopItem("Bazooka", 70, 100));
        godTier.add(new ShopItem("Yggdrasil's \nBranch", 140, 160));
    }

    public int getRerollCost(){
        return rerollCost;
    }

    public ArrayList<ShopItem> weaponPicker(int wave){//picks 5 random weapons for the shop lineup based on the current wave
        Random rand = new Random();
        ArrayList<ShopItem> lineup = new ArrayList<>();

        if (wave < 3){
            for (int i = 0; i < 5; i++){
                int index = rand.nextInt(basicTier.size());
                ShopItem item = basicTier.get(index);
                lineup.add(item);
            }
        }
        else if (wave < 7){
            for (int i = 0; i < 5; i++){
                int index = rand.nextInt(midTier.size());
                ShopItem item = midTier.get(index);
                lineup.add(item);
            }
        }
        else{
            for (int i = 0; i < 5; i++){
                int index = rand.nextInt(godTier.size());
                ShopItem item = godTier.get(index);
                lineup.add(item);
            }
        }

        return lineup;
    }

    public String randomEnemyWeapon (int wave){//picks a random weapon for the enemy based on the current wave
        Random rand = new Random();
        String weapon = "";

        if (wave < 3){
            int index = rand.nextInt(basicTier.size());
            weapon = basicTier.get(index).getName();
        }
        else if (wave < 7){
            int index = rand.nextInt(midTier.size());
            weapon = midTier.get(index).getName();
        }
        else{
            int index = rand.nextInt(godTier.size());
            weapon = godTier.get(index).getName();
        }

        return weapon;
    }

    public ArrayList<ShopItem> reroll(int wave, int money, ArrayList<ShopItem> currentLineup){//reroll logic for shop
        ArrayList<ShopItem> lineup = new ArrayList<>();

        if (money >= rerollCost){
            lineup = weaponPicker(wave);
            rerollCost += 2;
            return lineup;
        }

        return currentLineup;
    }

    public void resetReroll(){//resets the reroll cost for the next wave
        this.rerollCost = 5;
    }

    public String buyWeapon(Character player, ShopItem item, ArrayList<ShopItem> lineup){//buy weapon logic for shop
        if (player.getMoney() >= item.getCost()){
            player.addMoney(-item.getCost());
            
            if (item.getName().equals(player.getWeapon())){
                player.weaponUpgrade(true, item.getDmg(), item.getName());
            }
            else{
                player.weaponUpgrade(false, item.getDmg(), item.getName());
            }

            lineup.remove(lineup.indexOf(item));
            return "Purchase Successful";
        }
        else{
            return "Not Enough Money";
        }
    }

    public String buyHp(Character player, int wave){//buy hp logic for shop
        Random rand = new Random();
        if (player.getMoney() >= wave + 4 && hpBought == false){
            player.addMoney(-(wave + 4));
            if (wave < 3){
                int hpGain = rand.nextInt(14);
                player.setHp(player.getHp() + hpGain);
            }
            else if (wave < 7){
                int hpGain = rand.nextInt(15, 41);
                player.setHp(player.getHp() + hpGain);
            }
            else{
                int hpGain = rand.nextInt(30, 141);
                player.setHp(player.getHp() + hpGain);
            }
            hpBought = true;
            return "Purchase Successful";
        }
        else if (hpBought == true){
            return "HP can only be bought once per Shop";
        }
        else{
            return "Not Enough Money";
        }
    }
    
    public void hpFlip(){
        hpBought = false;
    }
}
