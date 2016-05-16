package MovieCorner.view;

import guiconsole.GUIConsole;

import javax.swing.JOptionPane;

import MovieCorner.controller.Controller;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;


public class ServerConsole implements MovieCornerView
{
   private GUIConsole console;
   
   public ServerConsole()
   {
      console = new GUIConsole("MovieCorner Server");
   }

   public void start(Controller controller) 
   {
	   show("[MovieCorner] Server startup complete.");
	   controller.execute("port");
   }

   @Override
   public void show(String message) {
      console.println(message);
   }
   
   @Override
   public void show(Object[] message) {
      for (Object msg:message)
         console.println(msg.toString());
   }

   public String get(String what) 
   {
	   String temp = JOptionPane.showInputDialog(null, what);
	   return temp;
   }

@Override
public Item getCurrentItem() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Episode getCurrentEpisode() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setCurrentEpisode(Episode episode) {
	// TODO Auto-generated method stub
	
}

@Override
public void startLoading(String text) {
	// TODO Auto-generated method stub
	
}

@Override
public void finishLoading() {
	// TODO Auto-generated method stub
	
}

}
