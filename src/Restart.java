import java.awt.event.KeyEvent;

public class Restart implements java.awt.event.KeyListener{
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_N ){
            System.gc();
            FlappyBird game = new FlappyBird();
            game.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
