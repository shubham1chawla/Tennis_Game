/*

	EVENT HANDLER CLASS:
	-> Used to store all the inputs in a queue and then later process them in our custom game loop.

*/

import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

//INTERFACE
public interface EventProcessable{
    public void handleEvent(AWTEvent e);
}