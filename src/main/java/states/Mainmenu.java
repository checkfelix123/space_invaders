/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import classes.GameFunctions;
import classes.PositioningObject;
import checkfelix123.space_invaders.app.Application;
import java.awt.Font;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import static states.Game.HEIGHT;
import static states.Game.HOP;
import static states.Game.WITDH;

/**
 *
 * @author graff
 */
public class Mainmenu extends BasicGameState {

    private int Id;
    private MouseOverArea moe;
    private int playersChoice = 0;
    private TrueTypeFont ttf;
    private List<String> options = new ArrayList<>();
    private List<PositioningObject> shotList = new ArrayList<>();
    private static boolean connected = false;
    
    

    
    private Image ship;
    private PositioningObject shipObject;
    private boolean right = true;
    private long time = System.currentTimeMillis();
    
    
    public Mainmenu(int Id) {
        this.Id = Id;
    }

    @Override
    public int getID() {
        return Id;
    }
    

    @Override
    public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
        ttf = new TrueTypeFont(new Font("Verdana", Font.CENTER_BASELINE, 20), true);
        options.add("Start");
        options.add("Save");
        options.add("Load");
        options.add("Multiplayer");
        options.add("Quit");
        
        ship = new Image("img/ship.png");
        
        shipObject = GameFunctions.renderShip(gc, ship, 2);
        
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
       ship.draw(shipObject.getPosX(), shipObject.getPosY(), Game.WITDH, Game.HEIGHT);
        
        for (int i = 0; i < options.size(); i++) {
            if(playersChoice == i){
                  
                 ttf.drawString(100, i * 50 + 200, options.get(i), new Color(Color.green));
            }else{
                 ttf.drawString(100, i * 50 + 200, options.get(i));
            }
           
        }
          for (PositioningObject x : shotList) {
            x.getImg().draw(x.getPosX(), x.getPosY(), WITDH / 2, HEIGHT / 2);
        }
     
      
         ttf.drawString(gc.getWidth()/2, gc.getHeight()/2, "SpaceInvaders " + Application.VERSION);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame arg1, int arg2) throws SlickException {
     
        Input input = gc.getInput();
  
            if(input.isKeyDown(Input.KEY_SPACE) ){

            }
            if (input.isKeyPressed(Input.KEY_DOWN)&& playersChoice+1 < options.size()) {          
                playersChoice++;

            }
            if (input.isKeyPressed(Input.KEY_UP) && playersChoice-1 >= 0) {

                playersChoice--;

            }
            if (input.isKeyPressed(Input.KEY_ENTER)) {

            }

        if(shipObject.getPosX() < gc.getWidth()-Game.WITDH  && right){

            shipObject.updateX(true);
        }else{
            right = false;
        }
       if( shipObject.getPosX() > gc.getWidth()/3  && !right){
            shipObject.updateX(false);
         }else{
           right = true;
       }
          if(System.currentTimeMillis() -time > 4200){
          shotList.add( GameFunctions.renderShot(gc, new Image("img/shot.png"), shipObject));
          time = System.currentTimeMillis();
       }
        GameFunctions.updateShot(shotList);
       
        switch (playersChoice) {
            
            case 0:
                if(input.isKeyDown(Input.KEY_ENTER) )
                {
                     arg1.enterState(2); 
                }
                break;
          case 3:
                if(input.isKeyDown(Input.KEY_ENTER) && !connected )
                {
                 connectTCP();
                    
                }
                break;
                
           case 4:
                   if(input.isKeyDown(Input.KEY_ENTER))
                {
                     gc.exit();
                }
                break;
         
        }
        
        
    }

    private void connectUDP() {
        System.out.println("here");
         String host = "localhost";
         int port = 4711;
        try(DatagramSocket socket = new DatagramSocket(port)){
            byte[] buf = new byte[1024];
          InetAddress address = InetAddress.getLocalHost();
         DatagramPacket dp = new DatagramPacket(buf, buf.length, address, port);
          socket.send(dp);
            System.out.println("Package send");
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     private void connectTCP() {
         String host = "localhost";
         int port = 4711;
        try(Socket socket = new Socket(host, port);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();){
           
           DataOutputStream dos = new DataOutputStream(out);
           dos.writeInt(1);
           connected = true;
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
