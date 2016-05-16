import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import MovieCorner.controller.ClientController;
import MovieCorner.controller.Controller;
import MovieCorner.mediator.ModelManager;
import MovieCorner.mediator.MovieCornerModel;
import MovieCorner.view.*;


public class MainClient {

   public static void main(String[] args) throws NumberFormatException, SQLException, ParseException, IOException {
      MovieCornerModel model = null;
      
      if(args.length == 1)
         model = new ModelManager(args[0], 1337);
      else if(args.length == 2)
         model = new ModelManager(args[0], Integer.parseInt(args[1]));
      else
         model = new ModelManager("localhost", 1337);
      
      MovieCornerView view = new ClientGUI();
      Controller controller = new ClientController(model, view);
      
      view.start(controller);
   }
 
}
