# Tennis_Game

## General Overview
Tennis Game uses no external APIs or any other framework. The game uses core JAVA library to create a JFrame and JComponents in order to create a machine independent application.

## Concepts Used
Objected oriented gaming, game development techniques, Threads, Java Runtime Enviournment.

## Code files and their brief

### Tennis_Game.java
Main Game file, contains the main functions. It extends Runnable, EventProcessable and KeyListener.

### GamePhysics.java
This file holds all the important variables and extends JComponent functionality in order to track mouse activities. These variables are used by other files to define functionality. This file also implements all the main mechanics behind the game.

### RenderEverything.java
This files creates off-screen images all the graphics that are used by the game, and renders all of them to Back-Buffer image to improve rendering process.

### EventProcessor.java
This file implements a AWT event queue to make processing of these events seemless and in order.

### EventProcessable.java
An abstract class used to handle the events from the queue of EventProcessor class.