/*
Developer: Mushfiqul Islam
Project: Hand of Destiny
*/
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class ShopScreen extends JPanel {
    //fields
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Character player;
    private WaveTracker tracker;
    private Shop shop;
    //shop lineup fields
    private ArrayList<ShopItem> lineup = new ArrayList<>();
    private boolean lineupGenerated = false;
    //images
    private Image shopfield;
    private Image backbtnimg;
    private Image rerollbtnimg;
    private Image hpbtnimg;
    private Image shopcontainerimg;
    //button hitboxes
    private Rectangle weaponSlots1 = new Rectangle(40, 520,220,230);
    private Rectangle weaponSlots2 = new Rectangle(270, 520,220,230);
    private Rectangle weaponSlots3 = new Rectangle(500, 520,220,230);
    private Rectangle weaponSlots4 = new Rectangle(730, 520,220,230);
    private Rectangle weaponSlots5 = new Rectangle(960, 520,220,230);
    private Rectangle hpbtn = new Rectangle(1000, 290, 200, 220);
    private Rectangle rerollbtn = new Rectangle(10, 400, 300, 100);
    private Rectangle backbtn = new Rectangle(0, 0, 240, 85);

    public ShopScreen(CardLayout cardLayout, JPanel mainPanel, Character player, Shop shop, WaveTracker tracker){
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.player = player;
        this.shop = shop;
        this.tracker = tracker;
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {//force exit
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }
        });

        addMouseListener(new MouseAdapter() {//handles all the mouse inputs
            @Override
            public void mousePressed(MouseEvent e){
                if (backbtn.contains(e.getPoint())){
                    System.out.println("back");
                    cardLayout.show(mainPanel, "battle");
                }
                else if (rerollbtn.contains(e.getPoint())){//rerolls the shop lineup and updates the player's money if they can afford it
                    System.out.println("reroll");
                    int costBefore = shop.getRerollCost();
                    lineup = shop.reroll(tracker.getWave(), player.getMoney(), lineup);
                    if (lineup != null && player.getMoney() >= costBefore){
                        player.addMoney(-costBefore);
                    }
                    repaint();
                }
                else if(hpbtn.contains(e.getPoint())){//buys hp and updates the player's money if they can afford it
                    System.out.println("hp");
                    shop.buyHp(player, tracker.getWave());
                    repaint();
                }
                else if(weaponSlots1.contains(e.getPoint())){//buys the first weapon in the lineup and updates the player's money if they can afford it same for the following
                    System.out.println("weapon 1");
                    if (lineup.size() > 0){
                        shop.buyWeapon(player, lineup.get(0), lineup);
                        repaint();
                    }
                }
                else if(weaponSlots2.contains(e.getPoint())){
                    System.out.println("weapon 2");
                    if (lineup.size() > 0){
                        shop.buyWeapon(player, lineup.get(1), lineup);
                        repaint();
                    }
                }
                else if(weaponSlots3.contains(e.getPoint())){
                    System.out.println("weapon 3");
                    if (lineup.size() > 0){
                        shop.buyWeapon(player, lineup.get(2), lineup);
                        repaint();
                    }
                }
                else if(weaponSlots4.contains(e.getPoint())){
                    System.out.println("weapon 4");
                    if (lineup.size() > 0){
                        shop.buyWeapon(player, lineup.get(3), lineup);
                        repaint();
                    }
                }
                else if(weaponSlots5.contains(e.getPoint())){
                    System.out.println("weapon 5");
                    if (lineup.size() > 0){
                        shop.buyWeapon(player, lineup.get(4), lineup);
                        repaint();
                    }
                }
            }
        });
        
        try {//loads all the images for the shop screen
            shopfield = ImageIO.read(new File("assets/ShopField.png"));
            hpbtnimg = ImageIO.read(new File("assets/Hpbtn.png"));
            rerollbtnimg = ImageIO.read(new File("assets/Rerollbtn.png"));
            backbtnimg = ImageIO.read(new File("assets/Backbtn.png"));
            shopcontainerimg = ImageIO.read(new File("assets/Shopcontainer.png"));
            System.out.println("ShopScreen loaded successfully");
        } 
        catch (Exception e) {
            System.out.println("SHOP SCREEN LOAD FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetLineup(){//resets the shop lineup for the next wave
        lineupGenerated = false;
        lineup.clear();
    }   

    @Override
    protected void paintComponent(Graphics g) {//handles all the drawing for the shop screen
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(shopfield, 0, 0, getWidth(), getHeight(), null);

        if(!lineupGenerated){
            lineup = shop.weaponPicker(tracker.getWave());
            lineupGenerated = true;
        }

        g2d.drawImage(shopcontainerimg, 35, 510, 230, 250, null);//weapon 1
        g2d.drawImage(shopcontainerimg, 265, 510, 230, 250, null);//weapon 2
        g2d.drawImage(shopcontainerimg, 495, 510, 230, 250, null);//weapon 3
        g2d.drawImage(shopcontainerimg, 725, 510, 230, 250, null);//weapon 4
        g2d.drawImage(shopcontainerimg, 955, 510, 230, 250, null); //weapon 5
        g2d.drawImage(hpbtnimg, 995, 290, 210, 220, null);//hp button
        g2d.drawImage(rerollbtnimg, -40, 260, 360, 370, null);//reroll button
        g2d.drawImage(backbtnimg, -35, -110, 300, 300, null);//back button

        if (lineup.size() > 0){
            g2d.setFont(new Font("Serif", Font.BOLD, 20));
            g2d.setColor(Color.BLACK);
            g2d.drawString(lineup.get(0).getName(), 50, 600);
            g2d.drawString("DMG: " + lineup.get(0).getDmg(), 50, 655);
            g2d.drawString("Cost: " + lineup.get(0).getCost(), 50, 700);
        }
        if (lineup.size() > 1){
            g2d.setFont(new Font("Serif", Font.BOLD, 20));
            g2d.setColor(Color.BLACK);
            g2d.drawString(lineup.get(1).getName(), 280, 600);
            g2d.drawString("DMG: " + lineup.get(1).getDmg(), 280, 655);
            g2d.drawString("Cost: " + lineup.get(1).getCost(), 280, 700);
        }
        if (lineup.size() > 2){
            g2d.setFont(new Font("Serif", Font.BOLD, 20));
            g2d.setColor(Color.BLACK);
            g2d.drawString(lineup.get(2).getName(), 510, 600);
            g2d.drawString("DMG: " + lineup.get(2).getDmg(), 510, 655);
            g2d.drawString("Cost: " + lineup.get(2).getCost(), 510, 700);
        }
        if (lineup.size() > 3){

            g2d.setFont(new Font("Serif", Font.BOLD, 20));
            g2d.setColor(Color.BLACK);
            g2d.drawString(lineup.get(3).getName(), 740, 600);
            g2d.drawString("DMG: " + lineup.get(3).getDmg(), 740, 655);
            g2d.drawString("Cost: " + lineup.get(3).getCost(), 740, 700);
        }
        if (lineup.size() > 4){
            g2d.setFont(new Font("Serif", Font.BOLD, 20));
            g2d.setColor(Color.BLACK);
            g2d.drawString(lineup.get(4).getName(), 970, 600);
            g2d.drawString("DMG: " + lineup.get(4).getDmg(), 970, 655);
            g2d.drawString("Cost: " + lineup.get(4).getCost(), 970, 700);
        }

        g2d.setFont(new Font("Serif", Font.BOLD, 30));
        g2d.setColor(Color.BLACK);
        g2d.drawString("BAL: $" + player.getMoney(), 350, 50);
        g2d.drawString("DMG: " + player.getAtk(), 550, 50);
        g2d.drawString("HP: " + player.getHp(), 750, 50);
        g2d.setFont(new Font("Serif", Font.BOLD, 35));
        g2d.setColor(Color.WHITE);
        g2d.drawString("$ " + shop.getRerollCost(), 220, 460);
        g2d.drawString(" " + (tracker.getWave() + 4), 1080, 420);
        g2d.drawString("??", 1140, 360);
        g2d.setFont(new Font("Serif", Font.BOLD, 24));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Current Weapon: " + player.getWeapon(), 50, 200);
        g2d.drawString("ATK: " + player.getAtk(), 50, 230);
        g2d.drawString("Upgrades: " + player.getUpgradeCount(), 50, 260);
    }
    
}
