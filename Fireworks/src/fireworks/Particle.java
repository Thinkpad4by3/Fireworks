/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fireworks;

import static java.lang.Math.log;

/**
 *
 * @author Jason
 */
public class Particle {
        //This class is the particle class. It is the particles that show up on screen and make the pretty colors. The fireworks class holds particles and sends them to the main paint class to be drawn.
        private int color;//sets color of firework particle, see Fireworks.paint() to see color definitions
        private double xVelocity;//velocity of the particle, updates position
        private double yVelocity;
        private int xPosition;//position of the firework on the screen
        private int yPosition;
        private int duration;//Time decay of a particle
        private int time;//Time since particle creation
        private boolean particleType; //is it a firework launch particle or an light particle -- false = firework launch, true = light particle
        
        public Particle(int c, int xP, int yP, double xV, double yV, boolean pT,int d) {//particle constructor
            //set values from constructor
            color = c;
            xVelocity = xV;
            yVelocity = yV;
            xPosition = xP;
            yPosition = yP;
            particleType = pT;
            duration = d;
            System.out.println("Paarticle created of type: " + pT);
        }
        
        public void updateParticle() {
            System.out.println("Particle being updated");
            if(xVelocity > 0) {
                xVelocity -= Fireworks.RESISTANCE*2;
            }
            else if(xVelocity < 0) {
                xVelocity += Fireworks.RESISTANCE*2;
            }
            xPosition += xVelocity;//update positions and velocities
            yVelocity += Fireworks.GRAVITY;
            if(yVelocity > 0) {
                yVelocity -= Fireworks.RESISTANCE;
            }
            else if(yVelocity < 0) {
                yVelocity += Fireworks.RESISTANCE;
            }
            yPosition += yVelocity;
            time++;
        }
        
        public int getXPosition() {//get xPosition
            return xPosition;
        }
        
        public int getYPosition() {//get xPosition
            return yPosition;
        }
        
        public int getColor() {//get xPosition
            if(particleType) {//if the particle is a firework particle it is always white, otherwise, return the color
                return color;
            }
            else {
                return 0;
            }
        }
        
        public boolean isExpired() {//check to see if particle is expired and needs to be deleted
            if(time >= duration){return true;} else{return false;} //check if time is greater than duration
        }
        
        public double getOpacity() {//returns opacity to make the fireworks fade as they explode & fall.
            if((time <= 11) || (particleType == false)) {
                return 0.0d;
            }
            else {
                if((log(time-10)/log(duration)) > 0 && (log(time-10)/log(duration)) < 1) {
                    return (log(time-10)/log(duration));
                }
                return 1;
            }
        }
        
        public int[] getDrawArray() {
            int[] returnArray = new int[4];//make an array to return parameters of the Particle, this will be used in the drawing routine
            returnArray[0] = xPosition;
            returnArray[1] = yPosition;
            returnArray[2] = color;
            System.out.println("GetOpacity: " + getOpacity());
            returnArray[3] = (int) (getOpacity() * 256);
            System.out.println("getDrawnArray is beign accesses");
            return returnArray;
        }
}
