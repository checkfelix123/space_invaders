package classes;

import classes.PositioningObject;
import java.util.Iterator;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import states.Game;
import static states.Game.HEIGHT;
import static states.Game.HOP;
import static states.Game.SHOTSPEED;
import static states.Game.WITDH;

/**
 *methods that are used more often
 * @author graff
 */
public class GameFunctions {

    /**
     * Render a ship on a specific position
     * @param gc
     * @param ship
     * @return 
     */
    public static PositioningObject renderShip(GameContainer gc, Image ship) {
        int xShip = (gc.getWidth() / 2) - WITDH / 2;
        return new PositioningObject(ship, xShip, gc.getHeight() - HEIGHT, Game.HOP, WITDH);
    }

    
    /**
     * Render a ship on a specific position
     * @param gc
     * @param ship
     * @param hop
     * @return 
     */
    public static PositioningObject renderShip(GameContainer gc, Image ship, int hop) {
        int xShip = (gc.getWidth() / 2) - WITDH / 2;
        return new PositioningObject(ship, xShip, gc.getHeight() - HEIGHT, hop, WITDH);
    }
    /**
     * Render a shot on a specific position
     * @param gc
     * @param shot
     * @param shipObject
     * @return 
     */
    public static PositioningObject renderShot(GameContainer gc, Image shot, PositioningObject shipObject) {
        Input input = gc.getInput();
        //when the player press space a new shot will be fired
        // WITDH of shot is WITDHShip/2 --> middle WITDHShip/4 
        return new PositioningObject(shot, shipObject.getPosX() + WITDH / 4, gc.getHeight() - HEIGHT, SHOTSPEED, shipObject.getHitbox_size() / 2, shipObject.getHitbox_size());

    }
    public static void updateShot(List<PositioningObject> shotList) {
          Iterator<PositioningObject> itShotL = shotList.iterator();
    
            while(itShotL.hasNext()){
                PositioningObject o = itShotL.next();
                 if(o.getPosY()+WITDH>=0){
                      o.updateY(true);
                }else if(!shotList.isEmpty()){
                     itShotL.remove();
                 }

            }
           
    }
}
