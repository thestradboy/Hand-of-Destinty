/*
Developer: Mushfiqul Islam
Project: Hand of Destiny
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.geom.AffineTransform;

public class TitleScreen extends JPanel {
    //fields
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Image paperpic;
    private Image rockpic;
    private Image handlogo;
    private Image ofImage;
    private Image destinylogo;
    private Clapperboard clapper;
    private Timer animTimer;
    //buttons
    private Image playbtnimg;
    private Image quitbtnimg;
    //button hitboxes
    private Rectangle playbtn = new Rectangle(410,450, 380,130);
    private Rectangle quitbtn = new Rectangle(410,600, 380,130);
    
    public TitleScreen(CardLayout cardLayout, JPanel mainPanel){
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setBackground(Color.GRAY);
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

        addMouseListener(new MouseAdapter() {//handles mouse input for title screen buttons
            @Override
            public void mousePressed(MouseEvent e){
                if (playbtn.contains(e.getPoint())){
                    cardLayout.show(mainPanel, "battle");
                    System.out.println("play");
                }
                else if (quitbtn.contains(e.getPoint())){
                    System.exit(0);
                }
            }
        });

        try {//loads all the images for the title screen
            rockpic  = ImageIO.read(new File("assets/rock.png"));
            paperpic = ImageIO.read(new File("assets/paper.png"));
            handlogo = ImageIO.read(new File("assets/handlogo.png"));
            ofImage = ImageIO.read(new File("assets/OF.png"));
            destinylogo = ImageIO.read(new File("assets/destiny.png"));
            playbtnimg = ImageIO.read(new File("assets/playbtn.png"));
            quitbtnimg = ImageIO.read(new File("assets/quitbtn.png"));
            System.out.println("title Images loaded successfully");
        } 
        catch (Exception e) {
            System.out.println("TITLE IMAGE LOAD FAILED: " + e.getMessage());
            e.printStackTrace();
        }

        clapper  = new Clapperboard(480, 270, -60, false);
        //animation timer for the clapperboard on the title screen
        animTimer = new Timer(1, e -> {
            if (!clapper.closed) {
                clapper.flapangle++;
                if (clapper.flapangle >= 0) {
                    clapper.flapangle = 0;
                    clapper.closed = true;
                    animTimer.stop();
                }
            }
            repaint();
        });
        animTimer.start();

    }

    private class Clapperboard {//inner class for the clapperboard animation on the title screen
        int x, y;
        double flapangle;
        boolean closed;

        private Clapperboard(int x, int y, double flapangle, boolean closed) {
            this.x = x;
            this.y = y;
            this.flapangle = flapangle;
            this.closed = closed;
        }

        public void draw(Graphics2D g2d) {
            int w = 240, h = 120, flapH = 40;

            // body
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x, y, w, h);
            g2d.drawRect(x, y, w, h);
            g2d.drawImage(ofImage, x,y,w,h,null);

            // flap rotates around top-left corner
            AffineTransform orig = g2d.getTransform();
            g2d.rotate(Math.toRadians(flapangle), x, y);

            int stripeW = w / 10;
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0){
                    g2d.setColor(Color.BLACK);
                }
                else {
                    g2d.setColor(Color.WHITE);
                }
                g2d.fillRect(x + i * stripeW, y - flapH, stripeW, flapH);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x + i * stripeW, y - flapH, stripeW, flapH);
            }
            g2d.setTransform(orig);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {//handles all the drawing for the title screen
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        //playbtn

        g2d.drawImage(playbtnimg, 350,258, 500,500, null);

        //quitbtn

        g2d.drawImage(quitbtnimg, 350,410, 500,500, null);

        //left arm
        g2d.setColor(new Color(220, 170, 140));
        g2d.drawImage(paperpic, 260, -35, 250, 290, null);
        g2d.fillRect(0, 105, 260, 74);
        g2d.drawImage(handlogo, 50, 105,170, 70, null);

        //right arm
        g2d.setColor(new Color(220, 170, 140));
        g2d.fillRect(973, 320, 265, 77);
        g2d.drawImage(rockpic, 725, 215, 250, 220, null);
        g2d.drawImage(destinylogo, 980, 305,230,100,null);

        clapper.draw(g2d);
    }
}