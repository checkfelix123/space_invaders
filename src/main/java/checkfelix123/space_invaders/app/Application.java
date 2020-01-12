/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkfelix123.space_invaders.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import states.Game;
import states.Mainmenu;
import states.Test;

/**
 *Inizialise SpaceInvaders
 * @author graff
 */
public class Application extends StateBasedGame {
// Game state identifiers
    private static final int SPLASHSCREEN = 0;
    private static final int MAINMENU     = 1;
    private static final int GAME         = 2;

    // Application Properties
    public static  int WIDTH   = 600;
    public static  int HEIGHT  = 900;
    public static final int FPS     = 70;
    public static final double VERSION = 1.0;
   
    /**
     *  Class Constructor
     * @param appName 
     */

    public Application(String appName) {
        super(appName);
     

    }

    /**
     * Initialize  game states (calls init method of each gamestate, and set's the state ID)
     */


    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new Mainmenu(MAINMENU));
        this.addState(new Game(GAME));
        // The first state added will be the one that is loaded first, when the application is launched
        //this.enterState(1);
        
        
          
//          
        
        
      
       // sizeWindow(gc.getScreenHeight(),gc.getScreenWidth());
    }

    // Main Method
    public static void main(String[] args) {
       System.setProperty("org.lwjgl.librarypath", new File(System.getProperty("user.dir"), "natives").getAbsolutePath());
       System.setProperty("net.java.games.input.librarypath", new File(System.getProperty("user.dir"), "natives").getAbsolutePath());
       
        try {
              
           
            AppGameContainer app = new AppGameContainer(new Application("SpaceInvaders" + VERSION));
            app.setDisplayMode(WIDTH, HEIGHT, false);
        
           app.setTargetFrameRate(FPS);
           app.setShowFPS(true);
            app.start();
           
        
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }

    private void sizeWindow(int screenHeight, int screenWidth) {
         int height;
        int width;
    
        HEIGHT = screenHeight;
        WIDTH = screenWidth;
                 
       
    }

   
}
