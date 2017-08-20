import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class Tennis_Game extends JFrame implements Runnable,  KeyListener, EventProcessable{

	private static final int DISPLAY_WIDTH = 1200;
	private static final int DISPLAY_HEIGHT = 600;
	public int X_BORDER;
	public int Y_BORDER;
	private int FRAMES_PER_SEC = 60;
	
	private BufferedImage bbImage;
	private Graphics2D bbGraphics;
	private static Thread loop;

	private GamePhysics gamePhysics;
	private RenderEverything render;

	private EventProcessor eventProcessor;

	public Tennis_Game(){
		super("Tennis Game");
		getContentPane().setLayout(null);
		setResizable(false);
		setVisible(true);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				exitProgram();

				//FOR DEBUGGING
				System.out.println("Current thread: NULL");
				//System.exit(0);
			}
		});

		addKeyListener(this);

		eventProcessor = new EventProcessor(this);

		bbImage = new BufferedImage(DISPLAY_WIDTH, DISPLAY_HEIGHT, BufferedImage.TYPE_INT_RGB);
		bbGraphics = (Graphics2D)bbImage.getGraphics();

		gamePhysics = new GamePhysics(new Rectangle(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT), eventProcessor);
		getContentPane().add(gamePhysics);

		render = new RenderEverything(gamePhysics);

		resizeInternalWindow(DISPLAY_WIDTH,DISPLAY_HEIGHT);
	}

	public void resizeInternalWindow(int internalWidth, int internalHeight){

		Insets borderInfo = getInsets();
		X_BORDER = borderInfo.left;
		Y_BORDER = borderInfo.top;
		int newWidth = internalWidth + borderInfo.left + borderInfo.right;
		int newHeight = internalHeight + borderInfo.top + borderInfo.bottom;

		Runnable resize = new Runnable(){
			public void run(){
				setSize(newWidth, newHeight);
			}
		};

		if(!SwingUtilities.isEventDispatchThread()){
			try{
				SwingUtilities.invokeAndWait(resize);
			}catch(Exception e){}
		}
		else{
			resize.run();
		}

		validate();
	}

	public void exitProgram(){
		loop = null;
	}

	public static void main(String args[]){
		Tennis_Game app = new Tennis_Game();

		//START OUR THREAD
		loop = new Thread(app);
		app.loop.start();
	}

	public void run(){

		long startTime, elapsedTime, waitTime;
		long frameTime = 1000/FRAMES_PER_SEC;

		//MAIN GAME LOOP HERE!

		while(loop!=null){
			startTime = System.currentTimeMillis();

			//FOR DEBUGGING
			// System.out.println(gamePhysics.presentState);

			//HANDLE IMPUTS
			gamePhysics.eventProcessor.processEventList();

			//APPLY PHYSICS
			if(gamePhysics.presentState == gamePhysics.presentState.GAME_SCREEN){
				gamePhysics.allGamePhysics();
			}

			//RENDER EVERYTHING ON BACK-BUFFER
			render.renderEverythingOnBB(bbGraphics);

			//RENDER GRAPHICS ON SCREEN
			Graphics g = getGraphics();
			g.drawImage(bbImage, X_BORDER, Y_BORDER, null);
			g.dispose();

			//HANDLE FRAME-RATES
			elapsedTime = System.currentTimeMillis() - startTime;
			waitTime = Math.max((frameTime - elapsedTime), 5);
			try{
				loop.sleep(waitTime);
			}catch(Exception e){}
		}

		System.out.println("Program Exited!");
		System.exit(0);
	}


	/*
		KEY BOARD INTERFACES
	*/

	public void handleEvent(AWTEvent e){
		gamePhysics.somethingEvent(e);
	}

	public void keyPressed(KeyEvent e){
		if(!gamePhysics.escapeKeyPressed)
			eventProcessor.addEvent(e);
		gamePhysics.escapeKeyPressed = true;
	}

	public void keyReleased(KeyEvent e){
		gamePhysics.escapeKeyPressed = false;
	}

	public void keyTyped(KeyEvent e){
		//empty
	}
}