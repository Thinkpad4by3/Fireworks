/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fireworks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Jason
 */
public class Fireworks extends JPanel {
    public static final int WINDOW_X = 250;
    public static final int WINDOW_Y = 150;
    public static final int SCREEN_SCALE = 6;
    public static final double GRAVITY = 0.2;//defaults: Gravity = 0.2, Resistance = 0.05
    public static final double RESISTANCE = 0.05;//amount of air resistance, slows down both x and y velocity.
    public static int[][] oldArray = new int[1][3];
    public static Graphics offScreenGraphics;
    public static Image offScreenImage;
    public static boolean setup = true;//setup for black window
    public static int[][] screen = new int[WINDOW_X][WINDOW_Y];
    static Firework fire = new Firework(100,-8,1,10,5,20);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Fireworks Demo");//all the JFrame opening code
        Fireworks fireworks = new Fireworks();
        frame.add(fireworks);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_X*SCREEN_SCALE,WINDOW_Y*SCREEN_SCALE);
        frame.setVisible(true);
        frame.setResizable(false);
        fireworks.setBackground(Color.BLACK);
        //double buffering stuff
        fireworks.init();
        while(true) {//while true loop
            Thread.sleep(50);
            fire.update();
            if(fire.isExpired()) {
                Random rand = new Random();
                int launchX = rand.nextInt(WINDOW_X/2)+(WINDOW_X/4);
                int launchVelocity = rand.nextInt(5)-8;
                int color = 1;//color doesnt do anything yet
                int particles = rand.nextInt(15) + 10;
                int explodePower = rand.nextInt(6) + 4;
                int expireTime = rand.nextInt(10) + 15;
                fire = new Firework(launchX, launchVelocity, color, particles, explodePower, expireTime);
            }
            fireworks.repaint();
        }
    }
    @Override
    public void update(Graphics g) {
        paint(g);
        System.out.println("Update Called");
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(setup){
            g.setColor(Color.BLACK);//blank the screen before drawing the pixels
            g.fillRect(0,0, WINDOW_X, WINDOW_Y);//draw pixel at WINDOW_SCALE multiplier(to increase size)
            setup = false;
        }
        int[][] drawArray = fire.getDrawnPixels();
        for(int x = 0;x<drawArray.length;x++) {
            System.out.println("Array contents--X: " + drawArray[x][0] + ", Y: " + drawArray[x][1] + ", Color: " + drawArray[x][2] + ", Opacity: " + drawArray[x][3]);
            float red = 0;
            float green = 0;
            float blue = 0;
            float opacity = ((256f-drawArray[x][3])/256f);
            
            switch(drawArray[x][2]) {
                case 0: red = 1; blue = 1; green=1; break;
                case 1: red = 1; blue = 0; green=0.5f; break;
                case 2: red = 1; blue = 0; green=1; break;
                case 3: red = 0; blue = 0; green=1; break;
                case 4: red = 0; blue = 1; green=1; break;
                case 5: red = 0; blue = 1; green=0; break;
                case 6: red = 1; blue = 1; green=0; break;
                case 7: red = 1; blue = 0; green=0; break;
            }
            System.out.println("COLOR!!! " + red + ", " + green+  ", " + blue + ", " + opacity + ", " + drawArray[x][3]);
            g.setColor(new Color(red*opacity,green*opacity,blue*opacity));
            //g.setColor(randomColor());
            g.fillRect(drawArray[x][0]*SCREEN_SCALE, drawArray[x][1]*SCREEN_SCALE, SCREEN_SCALE, SCREEN_SCALE);//draw pixel at WINDOW_SCALE multiplier(to increase size)
        }
    }
    
    /*@Override
    public void paint(Graphics g) {
        super.paint(g);//JPanel stuff...need it for some reason
        if(setup){
            g.setColor(Color.BLACK);//blank the screen before drawing the pixels
            g.fillRect(0,0, WINDOW_X, WINDOW_Y);//draw pixel at WINDOW_SCALE multiplier(to increase size)
            setup = false;
        }
        /*for(int x = 0;x<oldArray.length;x++) {
            g.setColor(Color.BLACK);
            //g.setColor(randomColor());
            g.fillRect(oldArray[x][0]*SCREEN_SCALE, oldArray[x][1]*SCREEN_SCALE, SCREEN_SCALE, SCREEN_SCALE);//draw pixel at WINDOW_SCALE multiplier(to increase size)
        }*/
      
         //insert here---switch statement for color from array by the firework class. 0 is white, past that colors can be made up. 
        /*int[][] drawArray = fire.getDrawnPixels();
        for(int x = 0;x<drawArray.length;x++) {
            System.out.println("Array contents--X: " + drawArray[x][0] + ", Y: " + drawArray[x][1] + ", Color: " + drawArray[x][2] + ", Opacity: " + drawArray[x][3]);
            float red = 0;
            float green = 0;
            float blue = 0;
            float opacity = ((256f-drawArray[x][3])/256f);
            
            switch(drawArray[x][2]) {
                case 0: red = 1; blue = 1; green=1; break;
                case 1: red = 1; blue = 0; green=0.5f; break;
                case 2: red = 1; blue = 0; green=1; break;
                case 3: red = 0; blue = 0; green=1; break;
                case 4: red = 0; blue = 1; green=1; break;
                case 5: red = 0; blue = 1; green=0; break;
                case 6: red = 1; blue = 1; green=0; break;
                case 7: red = 1; blue = 0; green=0; break;
            }
            System.out.println("COLOR!!! " + red + ", " + green+  ", " + blue + ", " + opacity + ", " + drawArray[x][3]);
            g.setColor(new Color(red*opacity,green*opacity,blue*opacity));
            //g.setColor(randomColor());
            g.fillRect(drawArray[x][0]*SCREEN_SCALE, drawArray[x][1]*SCREEN_SCALE, SCREEN_SCALE, SCREEN_SCALE);//draw pixel at WINDOW_SCALE multiplier(to increase size)
        }
        //oldArray = drawArray;
    }*/
    
    public static void offScreenRender(Graphics g) {
        int[][] drawArray = fire.getDrawnPixels();
        for(int x = 0;x<drawArray.length;x++) {
            System.out.println("Array contents--X: " + drawArray[x][0] + ", Y: " + drawArray[x][1] + ", Color: " + drawArray[x][2] + ", Opacity: " + drawArray[x][3]);
            float red = 0;
            float green = 0;
            float blue = 0;
            float opacity = ((256f-drawArray[x][3])/256f);
            
            switch(drawArray[x][2]) {
                case 0: red = 1; blue = 1; green=1; break;
                case 1: red = 1; blue = 0; green=0.5f; break;
                case 2: red = 1; blue = 0; green=1; break;
                case 3: red = 0; blue = 0; green=1; break;
                case 4: red = 0; blue = 1; green=1; break;
                case 5: red = 0; blue = 1; green=0; break;
                case 6: red = 1; blue = 1; green=0; break;
                case 7: red = 1; blue = 0; green=0; break;
            }
            System.out.println("COLOR!!! " + red + ", " + green+  ", " + blue + ", " + opacity + ", " + drawArray[x][3]);
            g.setColor(new Color(red*opacity,green*opacity,blue*opacity));
            //g.setColor(randomColor());
            g.fillRect(drawArray[x][0]*SCREEN_SCALE, drawArray[x][1]*SCREEN_SCALE, SCREEN_SCALE, SCREEN_SCALE);//draw pixel at WINDOW_SCALE multiplier(to increase size)
        }
        //oldArray = drawArray;
    }
    
    //this is just a random color generator for testing purposes only, taken from StackOverflow. Generates BRIGHT colors, well most of the time!
    public static Color randomColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);
        randomColor.brighter();
        return randomColor;
    }
    
    public void init() {
        offScreenImage = createImage(WINDOW_X, WINDOW_Y);
        offScreenGraphics = offScreenImage.getGraphics();
        // Automatic in some systems, not in others
        offScreenGraphics.setColor(Color.black);
    }
}
