package MovieCorner.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import MovieCorner.controller.Controller;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;

public class ClientGUI implements MovieCornerView{

   private JFrame loginFrame, mainFrame;
   private LoadingFrame loadingFrame;
   private Controller controller;
   
   public ClientGUI() {
      createComponents();
      
   }
   
   private void createComponents() {
      loginFrame = null;
      mainFrame = null;
      loadingFrame = null;
   }

   @Override
   public void start(Controller controller) {
      this.controller = controller;
      
      if (loginFrame == null || !loginFrame.isVisible())
      {
         loginFrame = new LoginFrame(controller);
         loginFrame.setVisible(true);
      } else {
         show("ups");
      }
   }

   @Override
   public void show(String message) {
      if (message.equals("userIsAdmin"))
         ((MovieCornerView) mainFrame).show(message);
      else if(message.equals("reviewAdded"))
    	  ((MovieCornerView) mainFrame).show(message);
      else if(message.equals("isFriend"))
         ((MovieCornerView) mainFrame).show(message);
      else if (message.equals("Login successful!"))
      {
         loginFrame.setVisible(false);
         
         
         loadingFrame = new LoadingFrame("Loading MovieCorner, please wait...");
         
         SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
		         mainFrame = new MainFrame();
		         
		         
		         ((MovieCornerView) mainFrame).start(controller);
		         loadingFrame.dispose();
		         loadingFrame = null;
		         
		         mainFrame.setVisible(true);
			}
        	 
         });

      }
      else
          JOptionPane.showMessageDialog(null, message);
      
   }

   @Override
   public String get(String what) {
      if ( what.equals("Search for") || 
            what.equals("rating") || 
            what.equals("review") ||
            what.equals("commentID") ||
            what.equals("userIDforWatched") ||
            what.equals("nameOfUser") ||
            what.equals("emailOfUser") ||
            what.equals("privilegesOfUser"))
          return ((MovieCornerView) mainFrame).get(what);
       else
          return ((LoginFrame) loginFrame).get(what);
   }

   @Override
   public void show(Object[] message) {
      ((MovieCornerView) mainFrame).show(message);
   }
   
   public Item getCurrentItem()
   {
	   return ((MovieCornerView) mainFrame).getCurrentItem();
   }

	@Override
	public Episode getCurrentEpisode() {
		return ((MovieCornerView) mainFrame).getCurrentEpisode();
	}
	
	@Override
	public void setCurrentEpisode(Episode episode) {
		((MovieCornerView) mainFrame).setCurrentEpisode(episode);
		
	}

	@Override
	public void startLoading(String text) {
		((MovieCornerView) mainFrame).startLoading(text);
	}

	@Override
	public void finishLoading() {
		((MovieCornerView) mainFrame).finishLoading();
	}

}
