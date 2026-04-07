/*
Developer: Mushfiqul Islam
Project: Hand of Destiny
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameGUI extends JFrame {
    // game objects
    private Character player;
    private Enemy monster;
    private WaveTracker tracker;
    private Shop shop;
    private GameOverScreen gameOverScreen;
    private ShopScreen shopScreen;
    // GUI fields
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public GameGUI(){
        //init game obj
        player = new Character(2, 5, 50, "Basic Sword");
        monster = new Enemy(1, 5, 0, "Stick");
        monster.setMoney(monster.moneyCalc(1));
        tracker = new WaveTracker();
        shop = new Shop();

        //jframe setup
        setTitle("Hand of Destiny");
        setSize(1240,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //cardlayout and panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //adds all the classes into panels to change screens
        gameOverScreen = new GameOverScreen(cardLayout, mainPanel, tracker, this);
        shopScreen = new ShopScreen(cardLayout, mainPanel, player, shop, tracker);
        mainPanel.add(new TitleScreen(cardLayout, mainPanel), "title");
        mainPanel.add(new BattleScreen(cardLayout, mainPanel, player, monster, tracker, shop, this), "battle");
        mainPanel.add(gameOverScreen, "gameover");
        mainPanel.add(shopScreen, "shop");
        cardLayout.show(mainPanel, "title");

        add(mainPanel);
        setVisible(true);
    }

    public void resetGame(){ //resets all the values for a new game and goes to the title screen
        player = new Character(2, 5, 0, "Basic Sword");
        monster = new Enemy(1, 5, 0, "Stick");
        monster.setMoney(monster.moneyCalc(1));
        tracker = new WaveTracker();
        shop = new Shop();

        gameOverScreen = new GameOverScreen(cardLayout, mainPanel, tracker, this);
        shopScreen = new ShopScreen(cardLayout, mainPanel, player, shop, tracker);
        
        mainPanel.removeAll();
        mainPanel.add(new TitleScreen(cardLayout, mainPanel), "title");
        mainPanel.add(new BattleScreen(cardLayout, mainPanel, player, monster, tracker, shop, this), "battle");
        mainPanel.add(gameOverScreen, "gameover");
        mainPanel.add(shopScreen, "shop");
        mainPanel.revalidate(); // refresh the panel
        mainPanel.repaint();
        cardLayout.show(mainPanel, "title");
    }

    public void resetShop(){
        shopScreen.resetLineup();
    }

    public void showGameOver(String msg){//shows game over screen and sets the highscore message
        gameOverScreen.setHighscoreMsg(msg);
        cardLayout.show(mainPanel, "gameover");
    }


}