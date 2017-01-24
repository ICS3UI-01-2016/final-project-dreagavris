package pkgfinal.summative.andreea.gavris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author gavra1870
 */
public class BomberBoy extends JComponent implements KeyListener {

    // Height and Width of our game
    static final int WIDTH = 630;
    static final int HEIGHT = 550;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // game vairables 
    Color backgroundcolor = new Color(43, 23, 5);
    Color skincolor = new Color(244, 66, 66);
    Rectangle boy = new Rectangle(53, 440, 30, 35);
    Color bombcolor = new Color(132, 8, 0);
    Rectangle bomb = new Rectangle(600, 600, 15, 20);
    Color blockscolor = new Color(196, 170, 137);
    Rectangle[] blocks = new Rectangle[30];
    Color topBordercolor = new Color(196, 170, 137);
    Rectangle[] topBorder = new Rectangle[15];

    Color cratescolor = new Color(94, 46, 26);
    Rectangle[] crates = new Rectangle[55];
    Color middlecolor = new Color(244, 66, 66);
    Rectangle middleExplosion = new Rectangle(600, 600, 42, 42);
    Color topcolor = new Color(244, 66, 66);
    Rectangle topExplosion = new Rectangle(600, 600, 30, 50);
    Color bottomcolor = new Color(244, 66, 66);
    Rectangle bottomExplosion = new Rectangle(600, 600, 30, 42);
    Color leftcolor = new Color(244, 66, 66);
    Rectangle leftExplosion = new Rectangle(600, 600, 50, 30);
    Color rightcolor = new Color(244, 66, 66);
    Rectangle rightExplosion = new Rectangle(600, 600, 42, 30);
    boolean upkey = false;
    boolean downkey = false;
    boolean leftkey = false;
    boolean rightkey = false;
    boolean spacekey = false;
    int lifecount = 3;
    int crateCount = 0;
    int blockcount = 0;
    int topBordercount = 0;
    long bombtimer = 0;
    long explosiontimer = 0;

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        // GAME DRAWING GOES HERE 
        // Set the color of the player(red)
        g.setColor(backgroundcolor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(skincolor);

        g.fillRect(boy.x, boy.y, boy.width, boy.height);
        g.setColor(bombcolor);
        g.fillRect(bomb.x, bomb.y, bomb.width, bomb.height);
        g.setColor(cratescolor);
        for (int i = 0; i < crateCount; i++) {
            g.fillRect(crates[i].x, crates[i].y, crates[i].width, crates[i].height);
        }
        g.setColor(middlecolor);
        g.fillRect(middleExplosion.x, middleExplosion.y, middleExplosion.width, middleExplosion.height);
        g.setColor(topcolor);
        g.fillRect(topExplosion.x, topExplosion.y, topExplosion.width, topExplosion.height);
        g.setColor(bottomcolor);
        g.fillRect(bottomExplosion.x, bottomExplosion.y, bottomExplosion.width, bottomExplosion.height);
        g.setColor(leftcolor);
        g.fillRect(leftExplosion.x, leftExplosion.y, leftExplosion.width, leftExplosion.height);
        g.setColor(rightcolor);
        g.fillRect(rightExplosion.x, rightExplosion.y, rightExplosion.width, rightExplosion.height);

        // Set the color of the blocks
        g.setColor(blockscolor);

        // Create a for loop that starts at 0 and postion is less than 30, than add one each time 
        for (int i = 0; i < 30; i++) {
            // once it geos through and skip each crate the actual blocks
            g.fillRect(blocks[i].x, blocks[i].y, blocks[i].width, blocks[i].height);
        }
        for (int b = 0; b < 15; b++) {
            g.fillRect(topBorder[b].x, topBorder[b].y, topBorder[b].width, topBorder[b].height);
        }

        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime = 0;
        long deltaTime;
        for (int toprow = 0; toprow < 15; toprow = toprow + 1) {
            topBorder[topBordercount] = new Rectangle(toprow * 42 + 42, 42);
            topBordercount++;
        }

        for (int row = 1; row < 10; row = row + 2) {
            for (int col = 1; col < 12; col = col + 2) {
                blocks[blockcount] = new Rectangle(col * 42 + 42, row * 42 + 42, 42, 42);
                blockcount++;
            }
        }

        // Determine if the row is even or odd
        // This one increases by one only nad the a maxiumum of 12
        for (int row = 0; row < 12; row = row + 2) {
            for (int col = 0; col < 13; col = col + 1) {
                int rand = (int) (Math.random() * 10 + 1);
                if (rand < 8) {
                    if (crateCount < 50) {
                        crates[crateCount] = new Rectangle(col * 42 + 42, row * 42 + 42, 42, 42);
                        crateCount++;
                        if (crateCount == 27) {
                            break;

                        }
                    }
                }
                if (crateCount == 15) {
                    break;

                }

            }
        }
        // Determine if the row is even or odd
        // This one increases by two because it skipping and has the a maxiumum of 12

        for (int row = 0; row < 11; row = row + 2) {
            for (int col = 1; col < 13; col = col + 1) {
                // 
                int rand = (int) (Math.random() * 10 + 1);
                if (rand < 8) {
                    crates[crateCount] = new Rectangle(col * 42 + 42, row * 42 + 42, 42, 42);
                    crateCount++;
                    if (crateCount == 50) {
                        break;
                    }

                }
            }
            if (crateCount == 35) {
                break;
            }

            // the main game loop section
            // game will end if you set done = false;
            boolean done = false;
            while (!done) {
                // determines when we started so we can keep a framerate
                startTime = System.currentTimeMillis();

                // all your game rules and move is done in here
                // GAME LOGIC STARTS HERE 
                if (upkey) {
                    boy.y = boy.y - 3;
                }
                if (downkey) {
                    boy.y = boy.y + 3;
                }
                if (leftkey) {
                    boy.x = boy.x - 3;
                }
                if (rightkey) {
                    boy.x = boy.x + 3;
                }
                if (spacekey) {
                    bomb.x = boy.x + 8;
                    bomb.y = boy.y + 8;
                    bombtimer = 60 * 1;
                }

                for (int i = 0; i < crateCount; i++) {
                    if (crates[i].intersects(boy)) {
                        Rectangle crateCollusion = boy.intersection(crates[i]);

                        //Left Side of the crate 
                        if (boy.x < crates[i].x) {
                            if (crateCollusion.width > crateCollusion.height) {
                                boy.x = boy.x - crateCollusion.height;

                            }
                            if (crateCollusion.width < crateCollusion.height) {
                                boy.x = boy.x - crateCollusion.width;

                            }

                        }
                        //Right Side of the crate
                        if (boy.x > crates[i].x) {
                            if (crateCollusion.width > crateCollusion.height) {
                                boy.x = boy.x + crateCollusion.height;
                            }
                            if (crateCollusion.width < crateCollusion.height) {
                                boy.x = boy.x + crateCollusion.width;
                            }
                        }

                        //Top Side of the crate
                        if (boy.y > crates[i].y) {
                            if (crateCollusion.width > crateCollusion.height) {
                                boy.y = boy.y + crateCollusion.height;
                            }
                            if (crateCollusion.width < crateCollusion.height) {
                                boy.y = boy.y + crateCollusion.width;
                            }
                        }
                        //Bottom Side of the Crate
                        if (boy.y < crates[i].y) {
                            if (crateCollusion.width > crateCollusion.height) {
                                boy.y = boy.y - crateCollusion.height;
                            }
                            if (crateCollusion.width < crateCollusion.height) {
                                boy.y = boy.y - crateCollusion.width;
                            }
                        }
                    }
                }
                for (int e = 0; e < blockcount; e++) {
                    if (blocks[e].intersects(boy)) {
                        Rectangle blockCollusion = boy.intersection(blocks[e]);

                        //Left Side of the block 
                        if (boy.x < blocks[e].x) {
                            if (blockCollusion.width > blockCollusion.height) {
                                boy.x = boy.x - blockCollusion.height;

                            }
                            if (blockCollusion.width < blockCollusion.height) {
                                boy.x = boy.x - blockCollusion.width;

                            }

                        }
                        //Right Side of the block
                        if (boy.x > blocks[e].x) {
                            if (blockCollusion.width > blockCollusion.height) {
                                boy.x = boy.x + blockCollusion.height;
                            }
                            if (blockCollusion.width < blockCollusion.height) {
                                boy.x = boy.x + blockCollusion.width;
                            }
                        }

                        //Top Side of the block
                        if (boy.y > blocks[e].y) {
                            if (blockCollusion.width > blockCollusion.height) {
                                boy.y = boy.y + blockCollusion.height;
                            }
                            if (blockCollusion.width < blockCollusion.height) {
                                boy.y = boy.y + blockCollusion.width;
                            }
                        }
                        //Bottom Side of the block
                        if (boy.y < blocks[e].y) {
                            if (blockCollusion.width > blockCollusion.height) {
                                boy.y = boy.y - blockCollusion.height;
                            }
                            if (blockCollusion.width < blockCollusion.height) {
                                boy.y = boy.y - blockCollusion.width;
                            }
                        }
                    }
                }

                bombtimer--;
                if (bombtimer == 0) {
                    middleExplosion.x = bomb.x - 14;
                    middleExplosion.y = bomb.y - 12;
                    topExplosion.x = middleExplosion.x + 6;
                    topExplosion.y = middleExplosion.y - 40;
                    bottomExplosion.x = middleExplosion.x + 6;
                    bottomExplosion.y = middleExplosion.y + 40;
                    leftExplosion.x = middleExplosion.x - 40;
                    leftExplosion.y = middleExplosion.y + 6;
                    rightExplosion.x = middleExplosion.x + 40;
                    rightExplosion.y = middleExplosion.y + 6;
                }

                for (int i = 0; i < crateCount; i++) {
                    if (crates[i].intersects(topExplosion)) {
                        crates[i].x = 600;
                        crates[i].y = 600;
                        explosiontimer = 60 * 2;
                    }
                }
                for (int i = 0; i < crateCount; i++) {
                    if (crates[i].intersects(bottomExplosion)) {
                        crates[i].x = 600;
                        crates[i].y = 600;
                        explosiontimer = 60 * 2;
                    }
                }
                for (int i = 0; i < crateCount; i++) {
                    if (crates[i].intersects(leftExplosion)) {
                        crates[i].x = 600;
                        crates[i].y = 600;
                        explosiontimer = 60 * 2;
                    }
                }
                for (int i = 0; i < crateCount; i++) {
                    if (crates[i].intersects(rightExplosion)) {
                        crates[i].x = 600;
                        crates[i].y = 600;
                        explosiontimer = 60 * 2;
                    }
                }

                explosiontimer--;

                if (explosiontimer == 0) {
                    middleExplosion.x = 600;
                    middleExplosion.y = 600;
                    topExplosion.x = 600;
                    topExplosion.y = 600;
                    bottomExplosion.x = 600;
                    bottomExplosion.y = 600;
                    leftExplosion.x = 600;
                    leftExplosion.y = 600;
                    rightExplosion.x = 600;
                    rightExplosion.y = 600;
                    bomb.x = 600;
                    bomb.y = 600;

                }
                if (middleExplosion.intersects(boy)) {
                    boy.x = 53;
                    boy.y = 440;
                    lifecount = lifecount - 1;
                }
                if (topExplosion.intersects(boy)) {
                    boy.x = 53;
                    boy.y = 440;
                    lifecount = lifecount - 1;
                }
                if (bottomExplosion.intersects(boy)) {
                    boy.x = 53;
                    boy.y = 440;
                    lifecount = lifecount - 1;
                }
                if (leftExplosion.intersects(boy)) {
                    boy.x = 53;
                    boy.y = 440;
                    lifecount = lifecount - 1;
                }
                if (rightExplosion.intersects(boy)) {
                    boy.x = 53;
                    boy.y = 440;
                    lifecount = lifecount - 1;
                }

                // GAME LOGIC ENDS HERE 
                // update the drawing (calls paintComponent)
                repaint();
                // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
                // USING SOME SIMPLE MATH
                deltaTime = System.currentTimeMillis() - startTime;
                try {
                    if (deltaTime > desiredTime) {
                        //took too much time, don't wait
                        Thread.sleep(1);
                    } else {
                        // sleep to make up the extra time
                        Thread.sleep(desiredTime - deltaTime);
                    }
                } catch (Exception e) {
                }
            }

            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try {
                if (deltaTime > desiredTime) {
                    //took too much time, don't wait
                    Thread.sleep(1);
                } else {
                    // sleep to make up the extra time
                    Thread.sleep(desiredTime - deltaTime);
                }
            } catch (Exception e) {
            };
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        BomberBoy game = new BomberBoy();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            upkey = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            downkey = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            leftkey = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightkey = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            spacekey = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            upkey = false;
        }
        if (key == KeyEvent.VK_DOWN) {
            downkey = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            leftkey = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightkey = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            spacekey = false;
        }
    }
}
