/*
Developer: Mushfiqul Islam
Project: Hand of Destiny
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;

public class BattleScreen extends JPanel{

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Character player;
    private Enemy monster;
    private WaveTracker tracker;
    private Shop shop;
    private GameGUI gameGUI;

    private boolean attacking = false;
    private boolean showResult = false;
    private String resultMsg = "";

    //images
    private Image battlefield;
    private Image battlebtnimg;
    private Image shopbtnimg;
    private Image rockbtnimg;
    private Image paperbtnimg;
    private Image scissorsbtnimg;
    private Image moneyimg;
    private Image playerimg;
    private Image enemyimg;
    private Image battlestatusimg;

    //button hitboxes
    private Rectangle attackbtn = new Rectangle(85,580, 380,130);
    private Rectangle shopbtn = new Rectangle(525,580, 380,130);
    private Rectangle rockbtn = new Rectangle(45,585, 380,120);
    private Rectangle paperbtn = new Rectangle(430,585, 375,120);
    private Rectangle scissorsbtn = new Rectangle(810,585, 375,120);
    
    public BattleScreen(CardLayout cardLayout, JPanel mainPanel, Character player, Enemy monster, WaveTracker tracker, Shop shop, GameGUI gameGUI){
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.player = player;
        this.monster = monster;
        this.tracker = tracker;
        this.shop = shop;
        this.gameGUI = gameGUI;
        setFocusable(true);
        requestFocusInWindow();

        //force exit
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }
        });

        try {//loads all the images for the battle screen
            battlebtnimg = ImageIO.read(new File("assets/Battlebtn.png"));
            shopbtnimg = ImageIO.read(new File("assets/Shopbtn.png"));
            rockbtnimg = ImageIO.read(new File("assets/Rockbtn.png"));
            paperbtnimg = ImageIO.read(new File("assets/Paperbtn.png"));
            scissorsbtnimg = ImageIO.read(new File("assets/Scissorsbtn.png"));
            moneyimg = ImageIO.read(new File("assets/Shopcontainer.png"));
            playerimg = ImageIO.read(new File("assets/Player.png"));
            enemyimg = ImageIO.read(new File("assets/Enemy.png"));
            battlestatusimg = ImageIO.read(new File("assets/BattleStatus.png"));
            System.out.println("Button Images loaded successfully");
        } 
        catch (Exception e) {
            System.out.println("BUTTON IMAGE LOAD FAILED: " + e.getMessage());
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {//handles all mouse inputs for screen
            @Override
            public void mousePressed(MouseEvent e){
                requestFocusInWindow();
                if (showResult){//if the result of an attack is being shown
                    showResult = false;
                    attacking = false;
                    repaint();
                    return;
                }
                else if (!attacking){//if the player is not currently attacking, show the battle/shop options
                    if (attackbtn.contains(e.getPoint())){
                        System.out.println("attack");
                        System.out.println( "wave" + tracker.getWave());
                        shop.hpFlip();
                        shop.resetReroll();
                        attacking = true;
                        repaint();
                    }
                    else if(shopbtn.contains(e.getPoint())){
                        System.out.println("shop");
                        cardLayout.show(mainPanel, "shop");
                    }
                }
                else if (attacking){//if the player is attacking, show the rock paper scissors options and handle the attack logic
                    if (rockbtn.contains(e.getPoint())){
                        System.out.println("rock");
                        String res = player.action("attack", monster, "rock");
                        resultMsg = Gameutil.msg(res);

                        if(res.contains("you won")){
                            nextWave();
                        }

                        showResult = true;
                        if(player.getHp() <= 0){
                            String msg = Gameutil.saveHighScore(tracker);
                            gameGUI.showGameOver(msg);
                        }
                        repaint();
                    }
                    else if(paperbtn.contains(e.getPoint())){
                        System.out.println("paper");
                        String res = player.action("attack", monster, "paper");
                        resultMsg = Gameutil.msg(res);

                        if(res.contains("you won")){
                            nextWave();
                        }

                        showResult = true;
                        if(player.getHp() <= 0){
                            String msg = Gameutil.saveHighScore(tracker);
                            gameGUI.showGameOver(msg);
                        }
                        repaint();
                    }
                    else if (scissorsbtn.contains(e.getPoint())){
                        System.out.println("scissors");
                        String res = player.action("attack", monster, "scissors");
                        resultMsg = Gameutil.msg(res);

                        if(res.contains("you won")){
                            nextWave();
                        }

                        showResult = true;
                        if(player.getHp() <= 0){
                            String msg = Gameutil.saveHighScore(tracker);
                            gameGUI.showGameOver(msg);
                        }
                        repaint();
                    }
                }
            }   
        });

        try {
            battlefield = ImageIO.read(new File("assets/BattleFieldnew.png"));
            System.out.println("Battlefield Images loaded successfully");
        } 
        catch (Exception e) {
            System.out.println("BATTLEFIELD IMAGE LOAD FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void nextWave(){//handles changes needed for next wave
    player.addMoney(monster.getMoney());
    int nextWave = tracker.getWave() + 1;
    tracker.setWave(nextWave);
    monster = new Enemy(nextWave, nextWave * 5, monster.moneyCalc(nextWave), shop.randomEnemyWeapon(nextWave));
    repaint();
    }

    @Override
    protected void paintComponent(Graphics g){//handles all the drawing for the battle screen
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(battlefield, 0, 0, getWidth(), getHeight(), null);
        g2d.drawImage(playerimg, 100, 220, 300, 300, null);
        g2d.drawImage(enemyimg, 700, 50, 300, 300, null);
        g2d.drawImage(battlestatusimg, 0, 0, 400, 200, null);
        g2d.drawImage(battlestatusimg, 820, 370, 400, 150, null);

        
        g2d.setFont(new Font("Serif", Font.BOLD, 40));
        g2d.setColor(Color.BLACK);
        g2d.drawString("HP: " + player.getHp(), 20, 50);
        g2d.drawString("ATK: " + player.getAtk(), 20, 80);
        g2d.drawString("Wave: " + tracker.getWave(), 20, 110);
        g2d.setFont(new Font("Serif", Font.BOLD, 30));
        g2d.setColor(Color.BLACK);
        g2d.drawString("HP: " + monster.getHp(), 850, 420);
        g2d.drawString("ATK: " + monster.getAtk(), 850, 450);
        g2d.drawString("MONEY: " + monster.getMoney(), 850, 480);

        
        if (showResult){
            g2d.setFont(new Font("Serif", Font.BOLD, 36));
            g2d.setColor(Color.BLACK);
            g2d.drawString(resultMsg, 300, 600);
            g2d.setFont(new Font("Serif", Font.BOLD, 24));
            g2d.drawString("Click anywhere to continue...", 350, 660);
        }
        else if (!attacking){
            g2d.drawImage(battlebtnimg, 20,390, 500,500, null);
            g2d.drawImage(shopbtnimg, 460, 390, 500, 500, null);
            g2d.drawImage(moneyimg, 950, 585, 220, 120, null);
            g2d.setFont(new Font("Serif", Font.BOLD, 48));
            g2d.setColor(Color.BLACK);
            g2d.drawString("$" + player.getMoney(), 1030, 660);
            
        }
        else{
            g2d.drawImage(rockbtnimg, -20, 390, 500, 500, null);
            g2d.drawImage(paperbtnimg, 365, 390, 500, 500, null);
            g2d.drawImage(scissorsbtnimg, 745, 390, 500, 500, null);
        }
    }
}
