/*

	RENDER EVERYTHING CLASS:
	-> Self obvious.

*/

import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class RenderEverything{

	private GamePhysics gamePhysics;
	private Graphics2D ballGraphics, groundGraphics, paddleGraphics, startScreenGraphics, winScreenGraphics, pauseScreenGraphics;
	private BufferedImage ballImage, tennisGroundImage, paddleImage, startScreenImage, winScreenImage, pauseScreenImage;
	private Ellipse2D.Double ballShape, paddleShape;

	public RenderEverything(GamePhysics gamePhysics){
		this.gamePhysics = gamePhysics;

		createBallGraphics();
		createTennisGroundGraphics();
		createPaddleGraphics();
		createStartScreenGraphics();
		createWinScreenGraphics();
		createPauseScreenGraphics();
	}

	public void createBallGraphics(){
		ballShape = new Ellipse2D.Double(0, 0, gamePhysics.ballDia, gamePhysics.ballDia);

		ballImage = new BufferedImage(gamePhysics.ballDia, gamePhysics.ballDia, BufferedImage.TYPE_INT_ARGB);
		ballGraphics = (Graphics2D)ballImage.getGraphics();

		ballGraphics.setColor(gamePhysics.ballColor);
		ballGraphics.fill(ballShape);
		ballGraphics.dispose();
	}

	public void createTennisGroundGraphics(){
		tennisGroundImage = new BufferedImage(gamePhysics.canvas.width, gamePhysics.canvas.height, BufferedImage.TYPE_INT_RGB);
		groundGraphics = (Graphics2D)tennisGroundImage.getGraphics();

		//CREATING TENNIS GROUND

		//FIRST GRASS
		groundGraphics.setColor(gamePhysics.groundColor);
		groundGraphics.fillRect(0, 0, gamePhysics.canvas.width, gamePhysics.canvas.height);

		//NOW LINES
		groundGraphics.setColor(gamePhysics.groundLineColor);
		groundGraphics.fillRect(gamePhysics.groundLineGap, 
								gamePhysics.groundLineGap, 
								gamePhysics.canvas.width - 2*gamePhysics.groundLineGap, 
								gamePhysics.canvas.height - 2*gamePhysics.groundLineGap);

		//GRASS AGAIN
		groundGraphics.setColor(gamePhysics.groundColor);
		groundGraphics.fillRect(gamePhysics.groundLineGap + gamePhysics.groundLineThickness, 
								gamePhysics.groundLineGap + gamePhysics.groundLineThickness, 
								gamePhysics.canvas.width - 2*(gamePhysics.groundLineGap + gamePhysics.groundLineThickness), 
								gamePhysics.canvas.height - 2*(gamePhysics.groundLineGap + gamePhysics.groundLineThickness));

		//MIDDLE LINE
		groundGraphics.setColor(gamePhysics.groundLineColor);
		groundGraphics.fillRect((gamePhysics.canvas.width - gamePhysics.groundLineThickness)/2,
								gamePhysics.groundLineGap,
								gamePhysics.groundLineThickness,
								gamePhysics.canvas.height - 2*gamePhysics.groundLineGap);

		groundGraphics.dispose();
	}

	public void createPaddleGraphics(){
		paddleImage = new BufferedImage(gamePhysics.paddleWidth, gamePhysics.paddleThickness, BufferedImage.TYPE_INT_ARGB);
		paddleGraphics = (Graphics2D)paddleImage.getGraphics();

		paddleGraphics.setColor(gamePhysics.paddleColor);
		paddleGraphics.fillRect(0, 0, gamePhysics.paddleWidth, gamePhysics.paddleThickness);
		paddleGraphics.dispose();
	}

	public void createStartScreenGraphics(){
		startScreenImage = new BufferedImage(gamePhysics.canvas.width, gamePhysics.canvas.height, BufferedImage.TYPE_INT_RGB);
		startScreenGraphics = (Graphics2D)startScreenImage.getGraphics();

		//CREATING TENNIS GROUND

		//FIRST GRASS
		startScreenGraphics.setColor(gamePhysics.groundColor);
		startScreenGraphics.fillRect(0, 0, gamePhysics.canvas.width, gamePhysics.canvas.height);

		//NOW LINES
		startScreenGraphics.setColor(gamePhysics.groundLineColor);
		startScreenGraphics.fillRect(gamePhysics.groundLineGap, 
								gamePhysics.groundLineGap, 
								gamePhysics.canvas.width - 2*gamePhysics.groundLineGap, 
								gamePhysics.canvas.height - 2*gamePhysics.groundLineGap);

		//GRASS AGAIN
		startScreenGraphics.setColor(gamePhysics.groundColor);
		startScreenGraphics.fillRect(gamePhysics.groundLineGap + gamePhysics.groundLineThickness, 
								gamePhysics.groundLineGap + gamePhysics.groundLineThickness, 
								gamePhysics.canvas.width - 2*(gamePhysics.groundLineGap + gamePhysics.groundLineThickness), 
								gamePhysics.canvas.height - 2*(gamePhysics.groundLineGap + gamePhysics.groundLineThickness));

		startScreenGraphics.dispose();
	}

	public void createWinScreenGraphics(){
		winScreenImage = new BufferedImage(gamePhysics.canvas.width, gamePhysics.canvas.height, BufferedImage.TYPE_INT_RGB);
		winScreenGraphics = (Graphics2D)winScreenImage.getGraphics();

		//CREATING TENNIS GROUND

		//FIRST GRASS
		winScreenGraphics.setColor(gamePhysics.groundColor);
		winScreenGraphics.fillRect(0, 0, gamePhysics.canvas.width, gamePhysics.canvas.height);

		//NOW LINES
		winScreenGraphics.setColor(gamePhysics.groundLineColor);
		winScreenGraphics.fillRect(gamePhysics.groundLineGap, 
								gamePhysics.groundLineGap, 
								gamePhysics.canvas.width - 2*gamePhysics.groundLineGap, 
								gamePhysics.canvas.height - 2*gamePhysics.groundLineGap);

		//GRASS AGAIN
		winScreenGraphics.setColor(gamePhysics.groundColor);
		winScreenGraphics.fillRect(gamePhysics.groundLineGap + gamePhysics.groundLineThickness, 
								gamePhysics.groundLineGap + gamePhysics.groundLineThickness, 
								gamePhysics.canvas.width - 2*(gamePhysics.groundLineGap + gamePhysics.groundLineThickness), 
								gamePhysics.canvas.height - 2*(gamePhysics.groundLineGap + gamePhysics.groundLineThickness));

		winScreenGraphics.dispose();
	}

	public void createPauseScreenGraphics(){
		pauseScreenImage = new BufferedImage(gamePhysics.canvas.width, gamePhysics.canvas.height, BufferedImage.TYPE_INT_ARGB);
		pauseScreenGraphics = (Graphics2D)pauseScreenImage.getGraphics();

		//WHITE OVERLAY
		pauseScreenGraphics.setColor(gamePhysics.pauseScreenColor);
		pauseScreenGraphics.fillRect(0, 0, gamePhysics.canvas.width, gamePhysics.canvas.height);

		pauseScreenGraphics.dispose();
	}

	public void renderEverythingOnBB(Graphics2D bbGraphics){

		if(gamePhysics.presentState == gamePhysics.presentState.START_SCREEN){
			renderStartScreenBackground(bbGraphics);

			//TEMPORARY SCREEN
			bbGraphics.setColor(Color.black);
			bbGraphics.drawString("Tennis Game", 
								  (gamePhysics.canvas.width - 80)/2, 
								  4*gamePhysics.groundLineGap);

			bbGraphics.setColor(gamePhysics.bColor);
			bbGraphics.fillRect(gamePhysics.startButton.x,
								gamePhysics.startButton.y, 
								gamePhysics.bThickness, 
								gamePhysics.bWidth);

			bbGraphics.setColor(Color.white);
			bbGraphics.drawString("START", gamePhysics.canvas.width/2 - 18, 
										   gamePhysics.canvas.height/2 + 5);

		}
		else if(gamePhysics.presentState == gamePhysics.presentState.GAME_SCREEN){
			renderGameBackground(bbGraphics);			
			renderBall(bbGraphics);
			renderPaddle(bbGraphics);

			//PRINTING SCORE

			bbGraphics.setColor(Color.black);
			bbGraphics.drawString("Player Score: "+gamePhysics.playerScore, 
								  2*gamePhysics.groundLineGap, 
								  2*gamePhysics.groundLineGap);
			bbGraphics.drawString("Computer Score: "+gamePhysics.computerScore, 
								  gamePhysics.canvas.width - 6*gamePhysics.groundLineGap, 
								  2*gamePhysics.groundLineGap);
		}
		else if(gamePhysics.presentState == gamePhysics.presentState.PAUSE_SCREEN){
			renderPauseScreenBackground(bbGraphics);

			//PRINTING MESSAGE
			bbGraphics.setColor(Color.black);
			bbGraphics.drawString("GAME PAUSED!", (gamePhysics.canvas.width - 40)/2, (gamePhysics.canvas.height)/2);
		}

		//TAG
		String tag = "(C) Shubham Chawla - 2017";
		bbGraphics.setColor(Color.black);
		bbGraphics.drawString(tag, 
							 (gamePhysics.canvas.width - 160)/2, 
							 gamePhysics.canvas.height - 10);
	}

	public void renderStartScreenBackground(Graphics2D bbGraphics){
		//RENDER GRAPHICS TO BACK-BUFFER.
		bbGraphics.drawImage(startScreenImage, 0, 0, null);
	}

	public void renderGameBackground(Graphics2D bbGraphics){
		//RENDER GRAPHICS TO BACK-BUFFER.
		bbGraphics.drawImage(tennisGroundImage, 0, 0, null);
	}

	public void renderBall(Graphics2D bbGraphics){
		//RENDER GRAPHICS TO BACK-BUFFER.
		bbGraphics.drawImage(ballImage, 
							(gamePhysics.ballX - gamePhysics.ballDia/2), 
							(gamePhysics.ballY - gamePhysics.ballDia/2), 
							null);
	}

	public void renderPaddle(Graphics2D bbGraphics){
		//RENDER GRAPHICS TO BACK-BUFFER.
		bbGraphics.drawImage(paddleImage, 
							 gamePhysics.paddle1X, 
							 gamePhysics.paddle1Y, 
							 null);

		bbGraphics.drawImage(paddleImage, 
							 gamePhysics.paddle2X, 
							 gamePhysics.paddle2Y, 
							 null);
	}

	public void renderPauseScreenBackground(Graphics2D bbGraphics){
		//RENDER GRAPHICS TO BACK-BUFFER.
		bbGraphics.drawImage(pauseScreenImage, 0, 0, null);
	}
}