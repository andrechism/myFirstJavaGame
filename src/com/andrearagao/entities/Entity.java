
package com.andrearagao.entities;

import com.andrearagao.main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Entity {
    
    public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(176, 0, 16, 16);
    public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(192, 0, 16, 16);
    public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(208, 0, 16, 16);
    public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(224, 0, 16, 16);
    
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    
    private BufferedImage sprite;
    
    public Entity(double x, double y, int width, int height, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }
    
    public void setX(int newX){
        this.x = newX;
    }
    
    public void setY(int newY){
        this.y = newY;
    }
    
    public int getX(){
        return (int)this.x;
    }
    
    public int getY(){
        return (int)this.y;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public void tick(){
        
    }
    
    public void render(Graphics g){
        g.drawImage(sprite, this.getX(), this.getY(), null);
    }
    
    
}
