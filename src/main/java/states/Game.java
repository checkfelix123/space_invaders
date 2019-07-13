/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import classes.GameObject;
import classes.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.omg.CORBA.Current;

/**
 *
 * @author graff
 */
public class Game extends BasicGameState {
    
        // ID we return to class 'Application'
    private int ID;
        //Static variables
    //width and height of objects
    public static final int WITDH = 60;
    public static final int HEIGHT = 60;

    //----------------------------------
    //Config Variables:
    public static final int SHOTSPEED = 8; 
    public static final int HOP = 6;
    public static final int ALIENSPEED = 4;  
    public static final int LIFE = 4;
    public static final int shotDelay = 300;

    
    
    

    List<PositioningObject> shotList = new ArrayList<>();
    List<PositioningObject> alienList = new ArrayList<>();
    List<GameObject> heartList = new ArrayList<>();

    
    //ship
    private Image ship;
    //shot
    private Image shot;
    private Image alien;
    private Image heart;
   
    private Long time_last_spawn_alien = System.currentTimeMillis();
    private long time_between_shots=System.currentTimeMillis();
     private PositioningObject shipObject;

    
    private int countShotAliens=0;
   private int countUpdateAliens=0;
   
   private int alienspeed;
   private int life= 4;
   
    long time = System.currentTimeMillis();
         
    public Game(int Id) {
        this.ID = Id;
    }

    /**
     * used for initialisation of objects
     * @param gc
     * @param arg1
     * @throws SlickException 
     */
    @Override
    public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {

        ship = new Image("img/ship.png");

        shot = new Image("img/shot.png");
        alien = new Image("img/alien.png");
        heart = new Image("img/heart.png");
        
         
        shipObject = GameFunctions.renderShip(gc, ship);
        
        this.alienspeed = ALIENSPEED;
        this.life       = LIFE;
    }
    
/**
 * used for updates --> for ship and alien movements 
 * @param gc
 * @param sbg
 * @param arg2
 * @throws SlickException 
 */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
        Input input = gc.getInput();
        int screen = gc.getWidth() - WITDH;
          pause(input, gc);

          
        if ((shipObject.getPosX() <= screen) && shipObject.getPosX() >= 0 && !gc.isPaused()) {
          
            //ship left/right
            updateShip(gc);
            //fire GameObject
            
              if (input.isKeyDown(Input.KEY_SPACE) && System.currentTimeMillis()-time_between_shots>shotDelay) {
                time_between_shots = System.currentTimeMillis();
                shotList.add(GameFunctions.renderShot(gc, shot, shipObject));
            }

            updateAliens(gc);
            
           GameFunctions.updateShot(shotList);
            
            removeAliens(gc, sbg);
            hitDetection();
            
        }
        
    }
    /**
     * In this method, all objects are rendered.
     * @param gc
     * @param arg1
     * @param g
     * @throws SlickException 
     */
     @Override
    public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {

        ship.draw(shipObject.getPosX(), shipObject.getPosY(), WITDH, HEIGHT);

        //DrawGameObjects
        shotList.forEach((PositioningObject x) -> {
            x.getImg().draw(x.getPosX(), x.getPosY(), WITDH / 2, HEIGHT / 2);
        });

        alienList.forEach((PositioningObject x) -> {
            x.getImg().draw(x.getPosX(), x.getPosY(), WITDH , HEIGHT);
        });
        
        g.drawString("Punkte: " + countShotAliens, 20, 20);
        heartList.forEach((heartObject)->{
     
            heart.draw(heartObject.getPosX(), heartObject.getPosY(), WITDH, HEIGHT/2);
        });      
        
        
    }
    
      private void renderShip(GameContainer gc) {
        int xShip = (gc.getWidth() / 2) - WITDH / 2;
        this.shipObject = new PositioningObject(ship, xShip, gc.getHeight() - HEIGHT, HOP, WITDH);
    }

    private void updateShot(GameContainer gc) {
  
        //when the player press space a new shot will be fired
      
            // WITDH of shot is WITDHShip/2 --> middle WITDHShip/4 
            shotList.add(new PositioningObject(shot, shipObject.getPosX()+WITDH/4, gc.getHeight() - HEIGHT, SHOTSPEED, shipObject.getHitbox_size()/2,shipObject.getHitbox_size() ));

        
    }

    private void updateAliens(GameContainer gc) {
       
        int posX = Math.abs((int) (((Math.random()) * gc.getWidth()) - WITDH/2));

        if (System.currentTimeMillis() - time_last_spawn_alien > 490 ){
            alienList.add(new PositioningObject(alien, posX, 0, this.alienspeed , WITDH));
            time_last_spawn_alien = System.currentTimeMillis();
            countUpdateAliens++;
        }if(countUpdateAliens >=100){
            this.alienspeed++;
            countUpdateAliens = 0;
        }

    }
     private void updateShip(GameContainer gc) {
        Input input = gc.getInput();
        int screen = gc.getWidth() - WITDH;
        if (input.isKeyDown(Input.KEY_D) && ((shipObject.getPosX() + HOP) <= screen)) {
            shipObject.updateX(true);
        }
        if (input.isKeyDown(Input.KEY_A) && ((shipObject.getPosX() - HOP) >= 0)) {
            shipObject.updateX(false);
        }

    }

    private void updateLife(int life) {
         heartList.remove(life);

       
    }

   
    @Override
    public int getID() {
        return ID;
    }

  

    private void pause(Input input,GameContainer gc) {
       
        if(input.isKeyDown(Input.KEY_P)){
             System.out.println("here");
                if(gc.isPaused()){
                     gc.setPaused(false);
                }else{
                     gc.setPaused(true);
                }
                  
           }      
    }

    private void removeAliens(GameContainer gc, StateBasedGame sbg) throws SlickException {
          Iterator<PositioningObject> itAlienL = alienList.iterator();
            while(itAlienL.hasNext()){
                PositioningObject o = itAlienL.next();
                 if(o.getPosY()-WITDH<=gc.getHeight()){
                     
                      o.updateY(false);
                }else if(!alienList.isEmpty()){
                    
                    if(life>1){
                       
                         updateLife(--this.life);

                    }else{
                        
                      
                     
                        sbg.enterState(1);
                        sbg.getState(2).leave(gc, sbg);
                    }
//                    
                     itAlienL.remove();
                 }
              

            }
    }
    private void hitDetection() {
        Iterator<PositioningObject> it = shotList.iterator();
       
        while(it.hasNext()) {
             PositioningObject x;
             
             x = it.next();
                for(PositioningObject y : alienList){
                   
                    if (x.getHitbox().intersects(y.getHitbox())) {
                        
                        countShotAliens++;
                       
                        alienList.remove(y);
                        it.remove();
                        break;
                    }
                     
                }         
                
            }
    }

   

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game); //To change body of generated methods, choose Tools | Templates.
        alienList.clear();
        shotList.clear();
        heartList.clear(); 
        this.life = LIFE;
        countShotAliens = 0;
        this.alienspeed = ALIENSPEED;
        
        for(int i=0; i<LIFE; i++){
         
            heartList.add(new GameObject(container.getWidth()-(i*70)-70, 20));//-70 damit alle herzen dargestellt werden
        }
        time = System.currentTimeMillis();
        

    }

   

    

}
