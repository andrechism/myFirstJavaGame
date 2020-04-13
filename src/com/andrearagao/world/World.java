
package com.andrearagao.world;

import com.andrearagao.entities.Enemy;
import com.andrearagao.entities.Weapon;
import com.andrearagao.entities.Bullet;
import com.andrearagao.entities.LifePack;
import com.andrearagao.entities.Entity;
import com.andrearagao.main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class World {
    
    private Tile[] tiles;
    public static int WIDTH, HEIGHT;
    
    
    public World(String path){
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            for(int xx = 0; xx < map.getWidth(); xx++){
                for(int yy = 0; yy < map.getHeight(); yy++){
                    
                    int pixelAtual = pixels[xx + (yy * map.getWidth())];
                    
                    // FLOOR por padrão
                    tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    /***/
                    
                    if(pixelAtual == 0xFF000000){
                        //Floor ~ chão
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    }else if(pixelAtual == 0xFFFFFFFF){
                        //Parede
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_WALL);
                    }else if(pixelAtual == 0xFFDDDBBB){
                        //parede de lado
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_VERTICAL_WALL);
                    }
                    
                    
                    /*  corners     corners*/
                    else if(pixelAtual == 0xFFAAE090){
                        //Parede Corner
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_CORNER_WALL_1);
                    }else if(pixelAtual == 0xFFAAE080){
                        //Parede Corner
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_CORNER_WALL_2);
                    }else if(pixelAtual == 0xFFAAE070){
                        //Parede Corner
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_CORNER_WALL_3);
                    }else if(pixelAtual == 0xFFAAE060){
                        //Parede Corner
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_CORNER_WALL_4);
                    }
                    
                    
                    /***/
                    
                    
                    
                    
                    else if(pixelAtual == 0xFF0035FF){
                        //Player
                        //tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                        Game.player.setX(xx * 16);
                        Game.player.setY(yy * 16);
                    }
                    /*      ENTITIES        */
                    
                    else if(pixelAtual == 0xFFEA9696){
                        //LIFEPACK
                        Game.entities.add(new LifePack(xx * 16, yy * 16, 16, 16, Entity.LIFEPACK_EN));
                    }else if(pixelAtual == 0xFFFFBF00){
                        //WEAPON
                        Game.entities.add(new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPON_EN));
                    }else if(pixelAtual == 0xFFFBFF00){
                        //BULLET
                        Game.entities.add(new Bullet(xx * 16, yy * 16, 16, 16, Entity.BULLET_EN));
                    }else if(pixelAtual == 0xFFFF0000){
                        //ENEMY
                        Game.entities.add(new Enemy(xx * 16, yy * 16, 16, 16, Entity.ENEMY_EN));
                    }
                    
                    /***/
                    
                    }
                }
            }catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void render(Graphics g){
        for(int xx = 0; xx < WIDTH; xx++){
            for(int yy = 0; yy < HEIGHT; yy++){
                Tile tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }
        }
    }
    
}
