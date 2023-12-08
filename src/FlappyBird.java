import java.awt.*;
import java.util.*;

public class FlappyBird extends Game{
    public static int NEW_SCORE = 0;
    public static int OLD_SCORE = 0;
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 800;

    private java.util.List<Pipe> randomPipes = new ArrayList<Pipe>();
    private java.util.ArrayList<Pipe> outPipes = new ArrayList<Pipe>();
    java.util.List<Pipe> pipesArray = new ArrayList<>(5);

    private Bird bird;
    private boolean birdCrashed;

    private Base base;

    private java.util.ArrayList<Clouds> clouds = new ArrayList<Clouds>();

    public FlappyBird() {
        super("Flappy Bird", SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setFocusable(true);
        this.requestFocus();

        this.NEW_SCORE = 0;
        this.OLD_SCORE = 0;

        randomPipes = createRandomPipes(5);
        bird = createBird();
        this.addKeyListener(bird);
        clouds = createCloud();
        createBase();
    }

    private void scoreKeeper() {
        if(NEW_SCORE > 0){
            OLD_SCORE++;
            NEW_SCORE = 0;
        }
    }

    private java.util.List<Pipe> createRandomPipes(int num) {
        for (int i = 0; i < num; i++) {
            Random random = new Random();
            int pipeHeight = random.nextInt(150) + 250;
            Point[] pipeCoordinates = {
                    new Point(SCREEN_WIDTH,0),
                    new Point(SCREEN_WIDTH+100,0),
                    new Point(SCREEN_WIDTH+100, pipeHeight),
                    new Point(SCREEN_WIDTH, pipeHeight)
            };
            Point[] bottomPipeCoordinates = {
                    new Point(SCREEN_WIDTH,pipeHeight),
                    new Point(SCREEN_WIDTH+100,pipeHeight),
                    new Point(SCREEN_WIDTH+100, SCREEN_HEIGHT),
                    new Point(SCREEN_WIDTH, SCREEN_HEIGHT)
            };
            int multiplier;
            if(num == 1){
                multiplier = 2;
            }else{
                multiplier = pipesArray.size()/2;
            }
            Point pipePosition = new Point(SCREEN_WIDTH + (multiplier*230),0);
            Point bottomPipePosition = new Point(SCREEN_WIDTH + (multiplier*230),pipeHeight+300);
            pipesArray.add(new Pipe(pipeCoordinates,pipePosition));
            pipesArray.add(new Pipe(bottomPipeCoordinates,bottomPipePosition));
        }
        return pipesArray;
    }

    private Bird createBird() {
        Point [] birdCoordinates = {
                new Point(240,465),
                new Point(270,450),
                new Point(300,465),
                new Point(300,480),
                new Point(270,495),
        };
        Point startingPosition = new Point((width -Bird.BIRD_WIDTH)/2, (height - Bird.BIRD_HEIGHT)/2);
        return new Bird(birdCoordinates, startingPosition);
    }

    private java.util.ArrayList<Clouds> createCloud() {
        java.util.ArrayList<Clouds> createdClouds = new ArrayList<Clouds>();
        Point [] cloudCoordinates = {
                new Point(80, 200),
                new Point(110, 140),
                new Point(140, 160),
                new Point(170, 140),
                new Point(200, 200),
                new Point(170, 220),
                new Point(140, 200),
                new Point(110, 220)
        };
        createdClouds.add(new Clouds(cloudCoordinates, new Point(450,110)));
        createdClouds.add(new Clouds(cloudCoordinates, new Point(300,150)));
        createdClouds.add(new Clouds(cloudCoordinates, new Point(250,50)));
        createdClouds.add(new Clouds(cloudCoordinates, new Point(100,120)));
        return createdClouds;
    }

    private void createBase(){
        Point [] baseCoordinates = {
                new Point(0, 0),
                new Point(800, 0),
                new Point(800, 100),
                new Point(0, 100)
        };
        this.base = new Base(baseCoordinates, new Point(0,700));
    }

    public void paint(Graphics brush) {
        brush.setFont(brush.getFont().deriveFont(20.0f));
        Color backgroundColor = new Color(58, 181, 211);
        brush.setColor(backgroundColor);
        brush.fillRect(0,0,width,height);

        for (Clouds thisCloud: clouds) {
            thisCloud.paint(brush,Color.white);
        }


        bird.paint(brush,Color.yellow);
        boolean birdMoving = bird.move();

        if(birdMoving) {
            for (Pipe pipe : randomPipes) {
                pipe.paint(brush, Color.green);
                boolean isOut = pipe.move();
                birdCrashed = pipe.collision(bird);
                if(birdCrashed){
                    bird.paint(brush,Color.red);
                    brush.setColor(Color.black);
                    brush.drawString("Bird Crashed" ,SCREEN_WIDTH/2 - 50,SCREEN_HEIGHT/2);
                    on = false;
                    this.addKeyListener(new Restart());
                }
                if (isOut) {
                    outPipes.add(pipe);
                }
            }
        }



        for (Pipe thisPipe: outPipes) {
            randomPipes.remove(thisPipe);
            createRandomPipes(1);
        }
        outPipes.clear();


        base.paint(brush, new Color(150, 113, 23));

        scoreKeeper();
        brush.setColor(Color.white);
        brush.drawString("Flappy Bird | Score: " + OLD_SCORE ,5,20);
    }

    public static void main (String[] args) {
        FlappyBird game = new FlappyBird();
        game.repaint();
    }
}
