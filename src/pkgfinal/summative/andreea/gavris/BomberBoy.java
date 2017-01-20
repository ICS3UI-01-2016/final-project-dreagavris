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
    static final int WIDTH = 635;
    static final int HEIGHT = 550;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // game vairables 
    Color backgroundcolor = new Color(43, 23, 5);
    Color skincolor = new Color(244, 66, 66);
    Rectangle boy = new Rectangle(53, 469, 30, 35);
    Color blockscolor = new Color(196, 170, 137);
    Rectangle bomb = new Rectangle(53, 600, 5, 10);
    Rectangle[] blocks = new Rectangle[30];
    Color cratescolor = new Color(94, 46, 26);
    Rectangle[] crates = new Rectangle[55];
    boolean upkey = false;
    boolean downkey = false;
    boolean leftkey = false;
    boolean rightkey = false;
    // Start the crate count at 0
    int crateCount = 0;

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
        //Create the boy
        g.fillRect(boy.x, boy.y, boy.width, boy.height);
        g.fillRect(boy.x, boy.y, boy.width, boy.height);

        // Set the color of the blocks
        g.setColor(blockscolor);

        // Create a for loop that starts at 0 and postion is less than 30, than add one each time 
        for (int i = 0; i < 30; i++) {
            // once it geos through and skip each crate the actual blocks
            g.fillRect(blocks[i].x, blocks[i].y, blocks[i].width, blocks[i].height);
        }
        g.setColor(cratescolor);
        for (int i = 0; i < crateCount; i++) {
            g.fillRect(crates[i].x, crates[i].y, crates[i].width, crates[i].height);
        }

        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        int blockcount = 0;
        for (int row = 1; row < 10; row = row + 2) {
            for (int col = 1; col < 12; col = col + 2) {
                blocks[blockcount] = new Rectangle(col * 42 + 42, row * 42 + 42, 42, 42);
                blockcount++;
            }
        }



        // Determine if the row is even or odd
        // This one increases by one only nad the a maxiumum of 12
        for (int row = 0; row < 11; row = row + 2) {
            for (int col = 1; col < 13; col = col + 1) {
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
                            //-------------------------------------------------------------------------------------------------------------------                         

                            if (boy.y > crates[i].y) {
                                if (crateCollusion.width < crateCollusion.height) {
                                    boy.y = boy.y - crateCollusion.width;
                                }
                            }

                            if (boy.y < crates[i].y) {
                                if (crateCollusion.width < crateCollusion.height) {
                                    boy.x = boy.x + crateCollusion.width;
                                }

                            }
                        }
                    }

                    for (int e = 0; e < blockcount; e++) {

                        if (blocks[e].intersects(boy)) {
                            Rectangle blocksCollusion = boy.intersection(blocks[e]);
                            if (boy.x > blocks[e].x) {
                                boy.x = boy.x - blocksCollusion.width;
                            }
                            if (boy.x < blocks[e].x) {
                                boy.x = boy.x - blocksCollusion.width;
                            }
                            if (boy.y > blocks[e].y) {
                                boy.y = boy.y + blocksCollusion.width;
                            }
                            if (boy.y < blocks[e].y) {
                                boy.y = boy.y - blocksCollusion.width;

                            }
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
        }
    }
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

    }
}
//if(crates[1].intersects(boy)){}

