/*
Developer: Mushfiqul Islam
Project: Hand of Destiny
*/
public class ShopItem {
    private String name;
    private int dmg;
    private int cost;

    public ShopItem(String name, int dmg, int cost){
        this.name = name;
        this.dmg = dmg;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
    public int getDmg() {
        return dmg;
    }
    public String getName() {
        return name;
    }

    public String toString(){
        return String.format("%s | DMG: %d | Cost: %d", this.name, this.dmg, this.cost);
    }
}
