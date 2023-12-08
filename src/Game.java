import java.awt.*;
import java.awt.event.*;

public abstract class Game extends Canvas {
    protected boolean on = true;
    protected int width, height;
    protected Image buffer;

    public Game(String name, int width, int height) {
        this.width = width;
        this.height = height;

        Frame frame = new Frame(name);
        frame.add(this);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        buffer = createImage(width, height);
    }

    abstract public void paint(Graphics brush);


    public void update(Graphics brush) {
        paint(buffer.getGraphics());
        brush.drawImage(buffer,0,0,this);
        if (on) {sleep(10); repaint();}
    }

    private void sleep(int time) {
        try {Thread.sleep(time);} catch(Exception exc){};
    }
}