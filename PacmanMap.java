import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PacmanMap {
    public static final int rows = 31, cols = 28;
    public static JFrame frame = new JFrame();
    public static JPanel pmap = new JPanel();
    public static Color[][] colorMapGrid = new Color[rows][cols];
    public static NewPanels[][] panelGrid = new NewPanels[rows][cols];
    public static int[][] modes = new int[rows][cols];
    public static PackmanFigure packguy;
    public static BufferedImage pacImg, ballImg, bigBallImg, ghostImg;
    public static JLabel pic;
    public static Component[] componentList;
    public static final int startRow = 22, startCol = 14;
    public static int ballsCount = 0, steps = 0;
    public static Ghost redG, orangeG, cyanG, pinkG;
    public static JButton upb, leftb, rightb, downb;
    public static JPanel panelbuttons;
    public static boolean isGot = false;
    public static int keyPressed, lastkey;

    PacmanMap() {
        if (steps == 0) {
            frame.setLayout(new GridLayout(2, 1));
            pmap.setBorder(BorderFactory.createTitledBorder(""));
            pmap.setLayout(new GridLayout(rows, cols));
            //panelGrid=new JPanel[rows][cols];
            for (int x = 0; x < rows; x++) {
                //int y=0; y<cols; y++
                for (int y = 0; y < cols; y++) {
                    panelGrid[x][y] = new NewPanels(x, y, modes[x][y]);
                    //panelGrid[x][y].getPanel().setBorder(BorderFactory.createTitledBorder("("+x+","+y+")"));
                    //panelGrid[x][y].setBackground(colorMapGrid[x][y]);
                    //panelGrid[x][y].setPreferredSize(new Dimension(90, 25));
                    pmap.add(panelGrid[x][y].getPanel());
                }
            }
            NewPanels up, next, before, down;
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    if (x == 0)
                        up = null;
                    else
                        up = panelGrid[x - 1][y];
                    if (x == rows - 1)
                        down = null;
                    else
                        down = panelGrid[x + 1][y];
                    if (y == 0)
                        before = null;
                    else
                        before = panelGrid[x][y - 1];
                    if (y == cols - 1)
                        next = null;
                    else
                        next = panelGrid[x][y + 1];
                    panelGrid[x][y].setPanels(up, down, next, before);
                /*if(x==0) {
                    if (y == cols - 1) {
                        panelGrid[x][y].setPanels(null, panelGrid[x + 1][y], null, panelGrid[x][y - 1]);
                    } else {
                        if (y == 0) {
                            panelGrid[x][y].setPanels(null, panelGrid[x + 1][y], panelGrid[x][y + 1], null);
                        } else {
                            panelGrid[x][y].setPanels(null, panelGrid[x + 1][y], panelGrid[x][y + 1], panelGrid[x][y - 1]);
                        }
                    }
                }
                else {
                    if(x==rows-1) {
                        if (y == cols - 1) {
                            panelGrid[x][y].setPanels(panelGrid[x-1][y], null, null, );
                        }
                    }
                }*/
                }
            }
            packguy = new PackmanFigure(panelGrid[startRow][startCol]);
            packguy.getCurrentPanel().setContain(3);
            //pic.setMaximumSize(packguy.getCurrentPanel().getPanel().getSize());
            //packguy.getCurrentPanel().getPanel().setBackground(new ImageIcon("C:\\Users\\user\\Desktop\\packman game\\packmanRight3.jpg"));
        /*if(p==null)
            System.out.println("is null");
        else
            System.out.println("is not null");*/
            //processKeys();
            //JOptionPane.showMessageDialog(frame, panelGrid[10][14].getContain());
            redG = new Ghost(panelGrid[10][14], 0, 7);
            //JOptionPane.showMessageDialog(frame, panelGrid[10][14].getContain()+" red "+redG.getLastContain());
            //framePaint();
            panelbuttons = new JPanel();
            panelbuttons.setBorder(BorderFactory.createTitledBorder(""));
            panelbuttons.setLayout(new FlowLayout());
            upb = new JButton("up button");
            downb = new JButton("down button");
            leftb = new JButton("left button");
            rightb = new JButton("right button");
            upb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keyPressed = 38;
                    handle();
                }
            });
            downb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keyPressed = 40;
                    handle();
                }
            });
            leftb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keyPressed = 37;
                    handle();
                }
            });
            rightb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keyPressed = 39;
                    handle();
                }
            });
            panelbuttons.add(upb);
            panelbuttons.add(rightb);
            panelbuttons.add(leftb);
            panelbuttons.add(downb);
            framePaint();
            //frame.add(panelbuttons);
        /*frame.repaint();
        frame.validate();*/
            frame.setVisible(true);
            frame.add(pmap);
            frame.add(panelbuttons);
            frame.setResizable(true);
            pmap.setMinimumSize(new Dimension(1400, 780));
            frame.setPreferredSize(new Dimension(1500, 800));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            processKeys();
            frame.pack();
        }
    }

    public static void framePaint() {
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                componentList = panelGrid[i][j].getPanel().getComponents();
                for (int z = 0; z < componentList.length; z++)
                    panelGrid[i][j].getPanel().remove(componentList[z]);
                switch (panelGrid[i][j].getContain()) {
                    case 1:
                        try {
                            ballImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\ball3.png"));
                            pic = new JLabel(new ImageIcon(ballImg));
                            panelGrid[i][j].getPanel().add(pic);
                        } catch (IOException e) {
                        }
                        break;
                    case 2:
                        try {
                            bigBallImg = ImageIO.read(new File(("C:\\Users\\user\\Desktop\\packman game\\bigball3.png")));
                            pic = new JLabel(new ImageIcon(bigBallImg));
                            panelGrid[i][j].getPanel().add(pic);
                        } catch (IOException e) {
                        }
                        break;
                    case 3:
                        switch (packguy.getDir()) {
                            case 0:
                                try {
                                    pacImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\leftPac.png"));
                                    pic = new JLabel(new ImageIcon(pacImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 1:
                                try {
                                    pacImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\upPac.png"));
                                    pic = new JLabel(new ImageIcon(pacImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 2:
                                try {
                                    pacImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\rightPac.png"));
                                    pic = new JLabel(new ImageIcon(pacImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 3:
                                try {
                                    pacImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\downPac.png"));
                                    pic = new JLabel(new ImageIcon(pacImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                        }
                        break;
                    case 4:
                        switch (orangeG.getDir()) {
                            case 0:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\leftOrange.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 1:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\upOrange.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 2:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\rightOrange.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 3:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\downOrange.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                        }
                        break;
                    case 5:
                        switch (pinkG.getDir()) {
                            case 0:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\leftPink.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 1:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\upPink.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 2:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\rightPink.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 3:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\downPink.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                        }
                        break;
                    case 6:
                        switch (cyanG.getDir()) {
                            case 0:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\leftCyan.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 1:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\upCyan.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 2:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\rightCyan.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 3:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\downCyan.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                        }
                        break;
                    case 7:
                        switch (redG.getDir()) {
                            case 0:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\leftRed.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 1:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\upRed.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 2:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\rightRed.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                            case 3:
                                try {
                                    ghostImg = ImageIO.read(new File("C:\\Users\\user\\Desktop\\packman game\\downRed.png"));
                                    pic = new JLabel(new ImageIcon(ghostImg));
                                    panelGrid[i][j].getPanel().add(pic);
                                } catch (IOException e) {
                                }
                                break;
                        }
                        break;
                }
            }
        }
        frame.repaint();
        frame.validate();
    }

    public static void MapPainting() //הפונקציה הזאת צובעת את המפה בהתאם למה שמוחלט מראש - ניתן לשנות את העיצוב של המפה דרך הפונקציה הזאת
    {
        for (int i = 1; i < (cols - 1); i++)
            for (int j = 1; j < (rows - 1); j++)
                modes[j][i] = 1;
        //colorMapGrid[j][i]=Color.YELLOW;
        for (int i = 0; i < cols; i++) {
            modes[0][i] = -1;
            modes[rows - 1][i] = -1;
            //colorMapGrid[0][i] = Color.GRAY;
            //colorMapGrid[rows-1][i] = Color.GRAY;
        }
        for (int i = 0; i < rows; i++) {
            modes[i][0] = -1;
            modes[i][cols - 1] = -1;
            //colorMapGrid[i][0]=Color.GRAY;
            //colorMapGrid[i][cols-1]=Color.GRAY;
        }
        for (int i = 11; i < 16; i++) {
            for (int j = 10; j < 18; j++) {
                modes[i][j] = 8;
                //colorMapGrid[i][j]=Color.CYAN;
            }
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 2; j < 4; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 5; j < 7; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 11; j < 14; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 15; j < 17; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 21; j < 23; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 27; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
        }
        for (int i = 7; i < 9; i++) {
            for (int j = 2; j < 7; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 8; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 11; j < 19; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 20; j < 26; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 27; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
        }
        for (int i = 10; i < 12; i++) {
            for (int j = 1; j < 4; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 5; j < 7; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 8; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 17; j < 19; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 20; j < 22; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 23; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
        }
        for (int i = 13; i < 14; i++) {
            for (int j = 2; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 17; j < 22; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 23; j < 26; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 27; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
        }
        for (int i = 2; i < 4; i++) {
            for (int j = 7; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 24; j < 27; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
        }
        for (int i = 1; i < 3; i++) {
            for (int j = 18; j < 20; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
        }
        for (int i = 4; i < 6; i++) {
            for (int j = 17; j < 21; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
        }
        for (int i = 5; i < 7; i++) {
            for (int j = 8; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 24; j < 26; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 27; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
        }
        for (int i = 9; i < 10; i++) {
            for (int j = 5; j < 7; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 20; j < 22; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
        }
        for (int i = 12; i < 13; i++) {
            for (int j = 8; j < 10; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 17; j < 19; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
            for (int j = 27; j < 29; j++) {
                modes[j][i] = -1;
                modes[j][cols - i - 1] = -1;
                //colorMapGrid[j][i]=Color.GRAY;
                //colorMapGrid[j][cols-i-1]=Color.GRAY;
            }
        }

        for (int i = 8; i < 19; i++) {
            for (int j = 7; j < 21; j++) {
                if (modes[i][j] == 1)
                    modes[i][j] = 0;
            }
        }
        for (int i = 15; i < 23; i++) {
            for (int j = 1; j < 4; j++) {
                if (modes[i][j] == 1)
                    modes[i][j] = 0;
                if (modes[i][cols - j - 1] == 1)
                    modes[i][cols - j - 1] = 0;
            }
        }

        modes[startRow][startCol] = 3;

        for (int i = 4; i < 5; i++) {
            for (int j = 5; j < 6; j++) {
                modes[i][j] = 2;
                modes[i][cols - j - 1] = 2;
                modes[rows - i - 1][j] = 2;
                modes[rows - i - 1][cols - j - 1] = 2;
            }
        }
        for (int i = 11; i < 16; i++) {
            modes[i][10] = -1;
            modes[i][cols - 11] = -1;
        }
        for (int i = 10; i < 17; i++)
            modes[15][i] = -1;
        for (int i = 10; i < 17; i++)
            modes[11][i] = -1;
        modes[11][14] = 8;
    }

    public static void moveRight() {
        packguy.setDir(2);
        if ((packguy.getCurrentPanel().canNext() == 1) || (packguy.getCurrentPanel().canNext() == 3)) {
            packguy.getCurrentPanel().setContain(0);
            packguy.setCurrentPanel(packguy.getCurrentPanel().getNextPanel());
            System.out.println("You moved right! " + packguy.toString());
        } else
            System.out.println("You didn't move! " + packguy.toString());
    }

    public static void moveDown() {
        packguy.setDir(3);
        if ((packguy.getCurrentPanel().canDown() == 1) || (packguy.getCurrentPanel().canDown() == 3)) {
            packguy.getCurrentPanel().setContain(0);
            packguy.setCurrentPanel(packguy.getCurrentPanel().getDownPanel());
            System.out.println("You moved down! " + packguy.toString());
        } else
            System.out.println("You didn't move! " + packguy.toString());
    }

    public static void moveUp() {
        packguy.setDir(1);
        if ((packguy.getCurrentPanel().canUp() == 1) || (packguy.getCurrentPanel().canUp() == 3)) {
            packguy.getCurrentPanel().setContain(0);
            packguy.setCurrentPanel(packguy.getCurrentPanel().getUpPanel());
            System.out.println("You moved up! " + packguy.toString());
        } else
            System.out.println("You didn't move! " + packguy.toString());
    }

    public static void moveLeft() {
        packguy.setDir(0);
        if ((packguy.getCurrentPanel().canBefore() == 1) || (packguy.getCurrentPanel().canBefore() == 3)) {
            packguy.getCurrentPanel().setContain(0);
            packguy.setCurrentPanel(packguy.getCurrentPanel().getBeforePanel());
            System.out.println("You moved left! " + packguy.toString());
        } else
            System.out.println("You didn't move! " + packguy.toString());
    }

    /*public void run(){ //פעולת הTHREAD
        while (true) {

            isGot = redG.moves(packguy.getCurrentPanel().getLoc());
            if (isGot) {
                while (true)
                    JOptionPane.showMessageDialog(frame, "you lost!");
            }

            if (pinkG == null) {
                if((panelGrid[10][14].isEmpty()) && (redG != null)) {
                    pinkG = new Ghost(panelGrid[10][14], 0, 5);
                }
            }
            else {
                isGot = pinkG.moves(packguy.getCurrentPanel().getLoc());
                if (isGot) {
                    while (true)
                        JOptionPane.showMessageDialog(frame, "you lost!");
                }
            }

            if (orangeG == null) {
                if((panelGrid[10][14].isEmpty()) && (pinkG != null)) {
                    orangeG = new Ghost(panelGrid[10][14], 0, 4);
                }
            }
            else {
                isGot = orangeG.moves(packguy.getCurrentPanel().getLoc());
                if (isGot) {
                    while (true)
                        JOptionPane.showMessageDialog(frame, "you lost!");
                }
            }

            if (cyanG == null) {
                if((panelGrid[10][14].isEmpty()) && (orangeG != null)) {
                    cyanG = new Ghost(panelGrid[10][14], 0, 6);
                }
            }
            else {
                isGot=cyanG.moves(packguy.getCurrentPanel().getLoc());
                if(isGot) {
                    while (true)
                        JOptionPane.showMessageDialog(frame, "you lost!");
                }
            }

            framePaint();
            try {
                Thread.sleep(333);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            //System.out.println(panelGrid[10][14].toString());
        }
    }*/
    public static void handle() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                if (panelGrid[i][j].getContain() == 1)
                    count++;
        }
        ballsCount = count;

        //boolean isGot=false;
        //if(isMoved){
        steps++;
        if (steps == 1) {
            lastkey = keyPressed;
            Thread t1 = new Thread(new Runnable() { //הthread של הרוחות
                @Override
                public void run() {
                    while (true) {
                        isGot = redG.moves(packguy.getCurrentPanel().getLoc());
                        if (isGot) {
                            while (true)
                                JOptionPane.showMessageDialog(frame, "you lost!");
                        }

                        if (pinkG == null) {
                            if ((panelGrid[10][14].isEmpty()) && (redG != null)) {
                                pinkG = new Ghost(panelGrid[10][14], 0, 5);
                            }
                        } else {
                            isGot = pinkG.moves(packguy.getCurrentPanel().getLoc());
                            if (isGot) {
                                while (true)
                                    JOptionPane.showMessageDialog(frame, "you lost!");
                            }
                        }

                        if (orangeG == null) {
                            if ((panelGrid[10][14].isEmpty()) && (pinkG != null)) {
                                orangeG = new Ghost(panelGrid[10][14], 0, 4);
                            }
                        } else {
                            isGot = orangeG.moves(packguy.getCurrentPanel().getLoc());
                            if (isGot) {
                                while (true)
                                    JOptionPane.showMessageDialog(frame, "you lost!");
                            }
                        }

                        if (cyanG == null) {
                            if ((panelGrid[10][14].isEmpty()) && (orangeG != null)) {
                                cyanG = new Ghost(panelGrid[10][14], 0, 6);
                            }
                        } else {
                            isGot = cyanG.moves(packguy.getCurrentPanel().getLoc());
                            if (isGot) {
                                while (true)
                                    JOptionPane.showMessageDialog(frame, "you lost!");
                            }
                        }

                        framePaint();
                        try {
                            Thread.sleep(333);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        //System.out.println(panelGrid[10][14].toString());
                    }
                }
            });
            t1.start();
        }
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (lastkey == keyPressed) {
                    switch (keyPressed) {
                        case 37:
                            moveLeft();
                            break;
                        case 38:
                            moveUp();
                            break;
                        case 39:
                            moveRight();
                            break;
                        case 40:
                            moveDown();
                            break;
                    }
                    if (packguy.getCurrentPanel().getContain() == 1)
                        ballsCount--;
                    packguy.getCurrentPanel().setContain(3);
                    if (ballsCount == 0) {
                        while (true)
                            JOptionPane.showMessageDialog(frame, "you won!");
                    }
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    framePaint();
                }
            }
        });
        if (keyPressed == lastkey)
            t2.start();
        else {
            lastkey = keyPressed;
            t2.stop();
        }
        framePaint();
    }

    private void processKeys() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
                new KeyEventDispatcher() {
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            keyPressed = e.getKeyCode();
                            /*NewPanels p=packguy.getCurrentPanel();
                            /*if(p==null)
                                System.out.println("is null");
                            else
                                System.out.println("is not null");
                            System.out.println("hello you are here with "+packguy.toString());*/
                            /*packguy.getCurrentPanel().getPanel().removeAll();
                            packguy.getCurrentPanel().getPanel().setBackground(Color.GRAY);*/
                            packguy.getCurrentPanel().setContain(0);
                            boolean isMoved = false;

                            handle();
                            /*switch(keyPressed) {
                                case 37: //left key
                                    moveLeft();
                                    break;
                                case 38: //up key
                                    moveUp();
                                    break;
                                case 39: //right key
                                    moveRight();
                                    break;
                                case 40: //down key
                                    moveDown();
                                    break;
                            }*/
                            //handle(isMoved);
                        }
                        return false;
                    }
                });
    }

    public static void main(String[] args) {
        MapPainting();
        new PacmanMap();
        //PackmanFigure packguy =new PackmanFigure(panelGrid[14][22]);
        System.out.println("start position: " + packguy.toString());
        //System.out.println(packguy.getCurrentPanel().getBeforePanel().toString());
        /*packguy.setCurrentPanel(panelGrid[1][1]);
        System.out.println(packguy.toString());*/
        /*NewPanels p=packguy.getCurrentPanel();
        if(p==null)
            System.out.println("is null");
        else
            System.out.println("is not null");*/
    }
    /*public static void main(String[] args){
        JFrame map=new JFrame();
        map.setTitle("pacman");
        map.setSize(1150,550);
        JPanel pmap=new JPanel();
        pmap.setSize(1150,550);
        pmap.setLocation(0, 0);

        JTable table = new JTable(31, 28);
        table.setLocation(0,0);
        TableColumn column=null;
        for(int i=0;i<table.getColumnCount();i++)
        {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(40);
        }
        pmap.add(table);
        map.setContentPane(pmap);
        map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map.setVisible(true);
    }*/
}
