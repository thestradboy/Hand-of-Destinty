/*
Developer: Mushfiqul Islam
Project: Hand of Destiny
*/
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameOverScreen extends JPanel {
    private CardLayout cardlayout;
    private JPanel mainPanel;
    private WaveTracker tracker;
    private GameGUI gameGUI;

    private String highscoreMsg = "";

    private Image mainmenuimg;
    private Image quitimg;
    private Image gameovernoknightimg;

    private Rectangle mainmenubtn = new Rectangle(50, 650, 300, 100);
    private Rectangle quitbtn = new Rectangle(900, 650, 300, 100);

    public GameOverScreen(CardLayout cardLayout, JPanel mainPanel, WaveTracker tracker, GameGUI gameGUI){
        this.cardlayout = cardLayout;
        this.mainPanel = mainPanel;
        this.tracker = tracker;
        this.gameGUI = gameGUI;
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if (mainmenubtn.contains(e.getPoint())){
                    gameGUI.resetGame();
                    System.out.println("main menu");
                }
                else if (quitbtn.contains(e.getPoint())){
                    System.exit(0);
                }
            }
        });

        try {
            mainmenuimg = ImageIO.read(new File("assets/Menubtn.png"));
            quitimg = ImageIO.read(new File("assets/Quitbtn.png"));
            gameovernoknightimg = ImageIO.read(new File("assets/gameover-noKnight.png"));
            System.out.println("Gameover buttons loaded successfully");
        } 
        catch (Exception e) {
            System.out.println("GAMEOVER BUTTONS LOAD FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setHighscoreMsg(String msg){
        this.highscoreMsg = msg;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(gameovernoknightimg, 0, 0, getWidth(), getHeight(), null);
        g2d.drawImage(mainmenuimg, 0, 495, 400, 400, null);
        g2d.drawImage(quitimg, 847, 495, 400, 400, null);
        g2d.setFont(new Font("Serif", Font.BOLD, 48));
        g2d.setColor(Color.WHITE);
        g2d.drawString("GAME OVER", 450, 120);
        g2d.setFont(new Font("Serif", Font.PLAIN, 30));
        g2d.drawString(highscoreMsg, 320, 230);
    }
}
