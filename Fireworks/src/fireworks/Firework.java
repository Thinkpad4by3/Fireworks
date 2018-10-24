/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fireworks;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jason
 */
public class Firework {
        private int launchX;//the initial launch position
        private int launchVelocity;//the initial velocity of the particle launch;
        private int explodeTime;//height at which the firework particle will explode;
        private int numberOfParticles;//number of particles in the explosion
        private int color;//color of particles when blow up
        private int explodePower;//power of explosion(set velocity of light particles)
        private int time;//time since creation of firework
        private boolean hasExploded;//set to true when firework explodes
        private ArrayList<Particle> particles = new ArrayList<Particle>(0);
        
        public Firework(int lX, int lV, int c, int nOP, int eP, int eT) {
            //set values from constructor
            launchX = lX;
            launchVelocity = lV;
            numberOfParticles = nOP;
            color = c;
            explodePower = eP;
            explodeTime = eT;
            hasExploded = false;//-----------------Fireworks.WINDOW_Y
            particles.add(new Particle(0, launchX, Fireworks.WINDOW_Y, 0, launchVelocity, false,1000));
        }
        
        public Firework() {//autofill constructor(no inputs)
            launchX = Fireworks.WINDOW_X/2;
            launchVelocity = -8;
            numberOfParticles = 10;
            color = 1;
            explodePower = 5;
            hasExploded = false;
            particles.add(new Particle(0, launchX, Fireworks.WINDOW_Y, 0, launchVelocity, false,1000));
        }
        
        public void update() {
            time++;
            //System.out.println("Explode Height" + (explodeHeight>=particles.get(0).getXPosition()));
            if(!hasExploded && explodeTime <= time) {
                int currentHeight = particles.get(0).getYPosition();
                particles.remove(0);
                hasExploded = true;
                Random rand = new Random();
                int randomInt = rand.nextInt(explodePower/4);
                randomInt -= (explodePower/4);
                for(int x = 0;x<numberOfParticles;x++) {
                    particles.add(new Particle(rand.nextInt(8), launchX, currentHeight, (int) (Math.cos((6.28/(numberOfParticles))*x)*(explodePower+randomInt)), (int) ((Math.sin((6.28/(numberOfParticles))*x)*(explodePower+randomInt))), hasExploded,20));
                }
            }
            for(int x = 0;x<particles.size();x++) {
                if(particles.get(x).isExpired()) {
                    particles.remove(x);
                    System.out.println("Particle Being Removed");
                }
                else {
                    particles.get(x).updateParticle();
                    System.out.println("Updated");
                }
            }
        }
        
        public boolean isExpired() {
            return particles.isEmpty();
        }
        
        public int[][] getDrawnPixels() {
            //System.out.println("getDrawnPixels is beign accesses");
            int[][] returnArray = new int[particles.size()][4];//make a two dimensional array of all the pixels to be drawn
            for(int x = 0;x<particles.size();x++) {//copy the from the particles into a bigger array
                int[] tempArray = particles.get(x).getDrawArray();
                for(int y = 0;y<4;y++) {
                    returnArray[x][y] = tempArray[y];
                }
            }
            return returnArray;
        }
}
