/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import checkfelix123.space_invaders.app.Application;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * describes all objects which are moved. inherts from GameObject.
 * @author graff
 */
public class PositioningObject extends GameObject {

    private Image img;

    protected Shape hitbox;
    protected int hitbox_size;
    protected int speed;

    public PositioningObject(Image img, int posX, int posY, int speed, int hitbox_width, int hitbox_height) {
        super(posX, posY);
        this.img = img;

        this.speed = speed;
        this.hitbox = new Rectangle(posX, posY, hitbox_width, hitbox_height);
        this.hitbox_size = hitbox_size;
    }

    public PositioningObject(Image img, int posX, int posY, int speed, int hitbox_size) {
        super(posX, posY);
        this.img = img;

        this.speed = speed;
        this.hitbox = new Rectangle(posX, posY, hitbox_size, hitbox_size);
        this.hitbox_size = hitbox_size;
    }

    public void updateY(boolean up) {

        if (up) {
            this.posY -= this.speed;
            this.hitbox.setY(this.hitbox.getY() - this.speed);
        } else {
            this.posY += this.speed;
            this.hitbox.setY(this.hitbox.getY() + this.speed);
        }

    }

    public void updateX(boolean right) {

        if (right) {
            this.posX += this.speed;
        } else {
            posX -= this.speed;
        }

    }

    public int getSpeed() {
        return speed;
    }

    public Image getImg() {
        return img;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Shape getHitbox() {
        return hitbox;
    }

    public int getHitbox_size() {
        return hitbox_size;
    }

}
