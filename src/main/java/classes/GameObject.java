/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Objects;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * describes all objects present in the game
 * @author graff
 */
public class GameObject {
  
    protected int posX; 
    protected int posY;
  
    public GameObject( int posX, int posY) {
       
        this.posX = posX;
        this.posY = posY;

    }
     public GameObject(Image img, int posX, int posY,  int speed, int hitbox_width, int hitbox_height) {
      
        this.posX = posX;
        this.posY = posY;
     
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
   
 
    
    
   
    
}
