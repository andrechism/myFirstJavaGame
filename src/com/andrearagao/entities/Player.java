
package com.andrearagao.entities;

import com.andrearagao.main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Player extends Entity{
    
    public boolean right, up, left, down;
    public int RIGHT_DIR = 0, LEFT_DIR = 1;
    public int dir = RIGHT_DIR;
    public double speed = 1.4;
    
    private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
    private boolean moved = false;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;
    
    public Player(int x, int y, int width, int height, BufferedImage sprite){
        super(x, y, width, height, sprite);
        
        rightPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];
        
        for (int i = 0; i < 4; i++){
            rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
        }
        for (int i = 0; i < 4; i++){
            leftPlayer[i] = Game.spritesheet.getSprite(96 + (i * 16), 0, 16, 16);
        }
    }
    
    @Override
    public void tick(){
        moved = false;
        if(right){
            moved = true;
            dir = RIGHT_DIR;
            x += speed;
        }else if(left){
            moved = true;
            dir = LEFT_DIR;
            x -= speed;
        }
        
        if(up){
            moved = true;
            y -= speed;
        }else if(down){
            moved = true;
            y += speed;
        }
        
        if(moved) {
            frames++;
            if(frames == maxFrames){
                frames = 0;
                index++;
                if(index > maxIndex){
                    index = 0;
                }
            }
        }
    }
    
    @Override
    public void render(Graphics g){
        if(dir == RIGHT_DIR){
            g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
        }else if(dir == LEFT_DIR){
            g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
        }
    }
    
}
