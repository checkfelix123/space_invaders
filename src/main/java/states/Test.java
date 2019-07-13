/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import classes.PositioningObject;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author graff
 */
public class Test extends BasicGameState{
     List<PositioningObject> shotList = new ArrayList<>();
    List<PositioningObject> alienList = new ArrayList<>();

    //ship
    private Image ship;

    //shot
    private Image shot;

    private Image alien;

    public static final int HOP = 6;
    // ID we return to class 'Application'
    private static final int ID = 2;

    private Long time_last_spawn = System.currentTimeMillis();

   PositioningObject ShotObject;
   PositioningObject AlienObject;
    //Static variables
    private static final int WITDH = 60;
    private static final int HEIGHT = 60;

    //----------------------------------
    //Config Variables:
    private static final int SHOTSPEED = 2;
    private static final int ALIENSPEED = 5;
    
    long time = System.currentTimeMillis();

    @Override
    public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {

        ship = new Image("img/ship.jpg");

        shot = new Image("img/shot.png");
        alien = new Image("img/alien.png");

 
        ShotObject = new PositioningObject(shot, 20, gc.getHeight()-HEIGHT, HOP, WITDH);
        AlienObject = new PositioningObject(alien, 20, 60, HOP, WITDH);
        

    }

    @Override
    public void update(GameContainer gc, StateBasedGame arg1, int arg2) throws SlickException {
        Input input = gc.getInput();
        int screen = gc.getWidth() - WITDH;
     
        
        ShotObject.updateY(true);

            if (ShotObject.getHitbox().intersects(AlienObject.getHitbox())) {
                System.out.println("getroffen");
            }


        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame arg1, Graphics arg2) throws SlickException {
        
        shot.draw(ShotObject.getPosX(),ShotObject.getPosY(), WITDH / 2, HEIGHT / 2);

        alien.draw(AlienObject.getPosX(),AlienObject.getPosY(), WITDH / 2, HEIGHT / 2);
        

       

    }

    @Override
    public int getID() {
        return ID;
    }

}
