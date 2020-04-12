
package com.andrearagao.main;

import com.andrearagao.entities.Entity;
import com.andrearagao.entities.Player;
import com.andrearagao.graficos.Spritesheet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


public class Game extends Canvas implements Runnable, KeyListener{
    
    public static JFrame frame;
    private Thread thread;
    private boolean isRunning;
    private final int WIDTH = 240;
    private final int HEIGHT = 160;
    private final int SCALE = 3;
    
    private BufferedImage image;

    public List<Entity> entities;
    public static Spritesheet spritesheet;
    
    private Player player;
    
    public Game(){

        addKeyListener(this);
        
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        
        // Inicializando Objetos
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        entities = new ArrayList<Entity>();
        spritesheet = new Spritesheet("/spritesheet.png");
        
        //Player player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16)); //    essas duas linhas
        //entities.add(player);                                                           //    se juntam na mesma linha abaixo:
        
        //entities.add(new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16)));  //mas como preciso controlar com keyboard a entidade eu usei a forma anterior
        
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
        entities.add(player);
    }
    
    public void initFrame(){
        frame = new JFrame("Game #1");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }
    
    public synchronized void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
    
    public void tick(){
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.tick();
        }
    }
    
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        //Graphics g = bs.getDrawGraphics();
        Graphics g = image.getGraphics();
        g.setColor(new Color(0,200,0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        /*Renderização do game*/
        
        //Graphics2D g2 = (Graphics2D) g; //isso não é um instanciamento, isso é um cast (seria ativar uma função no 'g')
        
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.render(g);
        }
        
        
        /***/
        
        
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null); 
        bs.show();
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while(isRunning){
            long now =System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            if(delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: "+ frames);
                frames = 0;
                timer += 1000;
            }
            
        }
        
        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
            //Direita!
            player.right = true;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
            //Esquerda!
            player.left = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
            //Para cima!
            player.up = true;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
            //Para baixo!
            player.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
            //Direita!
            player.right = false;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
            //Esquerda!
            player.left = false;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
            //Para cima!
            player.up = false;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
            //Para baixo!
            player.down = false;
        }
    }
    
}
