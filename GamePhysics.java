/*

	GAME PHYSICS CLASS:
	-> Handles all the physics and inputs of the game.

*/
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class GamePhysics extends JComponent implements MouseListener, MouseMotionListener{

	//THESE VARIABLES WILL SHARED BY CLASS RENDER EVERYTHING THEREFORE DECLARED PUBLIC
	public Rectangle canvas, ballBounds;
	public int ballX, ballY, ballDia, groundLineGap, groundLineThickness;
	public Point ballSpeed = new Point();
	public Color ballColor, groundColor, groundLineColor;
	private int minBallSpeed;
	public int playerScore, computerScore, maxScore;
	public boolean playerWin;

	//PADDLE VARIABLES
	public int paddle1X, paddle2X;
	public int paddle1Y, paddle2Y;
	public int paddle2Speed, maxPaddle2Speed;
	public int paddleThickness, paddleWidth;
	public Color paddleColor;

	//BUTTON VARIABLES
	public Point startButton = new Point();
	public int bThickness, bWidth;
	public Color bColor;
	public Color hoverColor;
	public boolean mouseOver;

	//PAUSE TOGGLE
	private boolean paused;
	public boolean escapeKeyPressed;

	public Color pauseScreenColor;

	public EventProcessor eventProcessor;

	public enum gameStates {START_SCREEN, GAME_SCREEN, PAUSE_SCREEN, WIN_SCREEN};
	public gameStates presentState;

	public GamePhysics(Rectangle canvas, EventProcessor eventProcessor){
		
		this.canvas = canvas;
		setBounds(canvas);
		setLayout(null);

		this.eventProcessor = eventProcessor;

		addMouseListener(this);
		addMouseMotionListener(this);

		createMainGameConstraints();
		createBallConstraints();
		createGroundConstraints();
		createPaddleConstraints();
		createScoreConstraints();
	}

	public void createMainGameConstraints(){
		bThickness = 100;
		bWidth = 50;
		startButton.setLocation((canvas.width - bThickness)/2, 
								(canvas.height-bWidth)/2);
		bColor = Color.blue;
		pauseScreenColor = new Color(1.0f, 1.0f, 1.0f, 0.5f);
		hoverColor = new Color(44, 62, 80);

		presentState = gameStates.START_SCREEN;
		paused = false;
		escapeKeyPressed = false;
		mouseOver = false;
	}

	public void createBallConstraints(){
		Random rand = new Random();

		ballDia = 20;	//pixels
		ballBounds = new Rectangle(0, 0, ballDia, ballDia);
		ballColor = new Color(44, 62, 80);
		minBallSpeed = 4;

		resetBallPosition();
	}

	public void createPaddleConstraints(){
		paddleThickness = 100;
		paddleWidth = 16;
		paddleColor = new Color(192, 57, 43);
		
		paddle1Y = (canvas.height - paddleThickness)/2;
		paddle2Y = (canvas.height - paddleThickness)/2;

		paddle1X = groundLineGap + (groundLineThickness - paddleWidth)/2;
		paddle2X = canvas.width - (groundLineGap + (groundLineThickness + paddleWidth)/2);

		paddle2Speed = 0;
		maxPaddle2Speed = 10;
	}

	public void createGroundConstraints(){
		groundColor = new Color(39, 174, 96);
		groundLineColor = Color.white;
		groundLineGap = canvas.width/32;	//pixels
		groundLineThickness = 6;	//pixels
	}

	public void createScoreConstraints(){
		maxScore = 5;
		resetScore();
	}

	public void resetScore(){
		playerScore = 0;
		computerScore = 0;
	}

	//THIS FUNCTION RUNS BOTH BALL MOTION AND PADDLE MOTION
	public void allGamePhysics(){
		if(playerScore == maxScore || computerScore == maxScore){
			presentState = presentState.WIN_SCREEN;
			if(playerScore == maxScore)
				playerWin = true;
			else
				playerWin = false;
		}

		ballMotionPhysics();
		computerPaddleAI();
	}

	public void ballMotionPhysics(){

		ballX += ballSpeed.x;
		ballY += ballSpeed.y;

		int paddleCenter;

		//PADDLE CONDITIONS FROM HERE

		//PADDLE 1
		if(ballX - ballDia/2 > paddle1X && 
			ballX - ballDia/2 < paddle1X + paddleWidth){

			if(ballY + ballDia/2 > paddle1Y && 
				ballY - ballDia/2 < paddle1Y + paddleThickness){

				ballSpeed.x *= -1;

				//REBOUND MECHANICS
				paddleCenter = paddle1Y + paddleThickness/2;
				ballSpeed.y = (ballY - paddleCenter)/4;

				ballX = paddle1X + paddleWidth + ballDia/2;
			}
		}

		//PADDLE 2
		if(ballX + ballDia/2 > paddle2X && 
			ballX + ballDia/2 < paddle2X + paddleWidth){

			if(ballY + ballDia/2 > paddle2Y && 
				ballY - ballDia/2 < paddle2Y + paddleThickness){

				ballSpeed.x *= -1;

				//REBOUND MECHANICS
				paddleCenter = paddle2Y + paddleThickness/2;
				ballSpeed.y = (ballY - paddleCenter)/4;

				ballX = paddle2X - ballDia/2;
			}
		}
		
		//PADDLE CONDITION TILL HERE

		if(ballX <= 0){
			//ballX = 0;
			resetBallPosition();
			computerScore++;

			//FOR DEBUGGING
			//System.out.println("Computer Score: "+ computerScore);

			ballSpeed.x *= -1;
		}

		if(ballX >= canvas.width){
			//ballX = canvas.width;
			resetBallPosition();
			playerScore++;

			//FOR DEBUGGING
			//System.out.println("Player Score: "+ playerScore);

			ballSpeed.x *= -1;
		}

		if(ballY - ballDia/2 <= 0){
			//ballY = 0;
			ballSpeed.y *= -1;
		}

		if(ballY + ballDia/2 >= canvas.height){
			//ballY = canvas.height;
			ballSpeed.y *= -1;
		}
	}

	//COMPUTER'S PADDLE PHYSICS
	public void computerPaddleAI(){
		int factor = (paddle2X - ballX);

		if(ballY > paddle2Y + paddleThickness){
			paddle2Y += maxPaddle2Speed - factor/50;
		}
		else if(ballY > paddle2Y + paddleThickness/4){
				paddle2Y += maxPaddle2Speed - factor/25;
		}
		else if(ballY < paddle2Y + 3*paddleThickness/4){
			paddle2Y -= maxPaddle2Speed - factor/25;
		}
		else if(ballY < paddle2Y){
			paddle2Y -= maxPaddle2Speed - factor/50;
		}

		if(paddle2Y + paddleThickness >= canvas.height){
			paddle2Y = canvas.height - paddleThickness;
		}

		if(paddle2Y <= 0){
			paddle2Y = 0;
		}
	}

	public void resetBallPosition(){
		Random rand = new Random();
		ballSpeed.setLocation((minBallSpeed + rand.nextInt(minBallSpeed)), (minBallSpeed + rand.nextInt(minBallSpeed)));

		ballX = (canvas.width)/2;
		ballY = (canvas.height)/2;
	}

	/*

		INTERFACES FOR MOUSE INPUTS

	*/

	public void somethingEvent(AWTEvent e){
		if(e.getID() == 500 || e.getID() == 503 || e.getID() == 505 || e.getID() == 504){
			switch(e.getID()){
				case MouseEvent.MOUSE_CLICKED:
					MouseEvent m_event = (MouseEvent)e;
					if(presentState == presentState.START_SCREEN || presentState == presentState.WIN_SCREEN || presentState == presentState.PAUSE_SCREEN){
						if(m_event.getX() >= startButton.x && 
						   m_event.getX() <= startButton.x + bThickness && 
						   m_event.getY() >= startButton.y && 
						   m_event.getY() <= startButton.y + bWidth){
							
							resetScore();
							resetBallPosition();
							if(presentState != presentState.PAUSE_SCREEN)
								presentState = presentState.GAME_SCREEN;
							else
								presentState = presentState.START_SCREEN;
						}
					}
					break;

				case MouseEvent.MOUSE_MOVED:
					MouseEvent m_event1 = (MouseEvent)e;
					paddle1Y = m_event1.getY() - paddleThickness/2;

					if(m_event1.getX() >= startButton.x && 
					   m_event1.getX() <= startButton.x + bThickness && 
					   m_event1.getY() >= startButton.y && 
					   m_event1.getY() <= startButton.y + bWidth){
						mouseOver = true;
					}
					else{
						mouseOver = false;
					}

					break;

				case MouseEvent.MOUSE_EXITED:
					if(presentState == presentState.GAME_SCREEN){
						presentState = presentState.PAUSE_SCREEN;
					}
					break;

				case MouseEvent.MOUSE_ENTERED:
					if(presentState == presentState.PAUSE_SCREEN){
						presentState = presentState.GAME_SCREEN;
					}
					break;
				default:
			}
		}
		else{
			KeyEvent k = (KeyEvent)e;
			if(k.getKeyCode() == KeyEvent.VK_ESCAPE && !paused){
				presentState = presentState.PAUSE_SCREEN;
				paused = true;
			}else if(k.getKeyCode() == KeyEvent.VK_ESCAPE && paused){
				presentState = presentState.GAME_SCREEN;
				paused = false;
			}
		}
	}

	public void mousePressed(MouseEvent e){
		//empty
	}

	public void mouseReleased(MouseEvent e){
		//empty
	}

	public void mouseExited(MouseEvent e){
		eventProcessor.addEvent(e);
	}

	public void mouseEntered(MouseEvent e){
		eventProcessor.addEvent(e);
	}

	public void mouseClicked(MouseEvent e){
		eventProcessor.addEvent(e);
	}

	public void mouseMoved(MouseEvent e){
		eventProcessor.addEvent(e);
	}

	public void mouseDragged(MouseEvent e){
		//empty
	}
}