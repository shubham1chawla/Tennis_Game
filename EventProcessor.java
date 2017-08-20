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

//CLASS
public class EventProcessor{

    public EventProcessor(EventProcessable handler){
        eventList = new LinkedList();
        this.handler = handler;
    }
    
    public void addEvent(AWTEvent event){   
        synchronized(eventList)
        {
            eventList.add(event);
        }
    }
  
    public void processEventList(){
        AWTEvent event;
   
        while(eventList.size() > 0)
        {
            synchronized(eventList)
            {
                event = (AWTEvent) eventList.removeFirst();
            }
   
            handler.handleEvent(event);
        }
    }
    
    private LinkedList eventList;
    private EventProcessable handler;
}