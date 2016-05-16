

import java.sql.SQLException;
import java.text.ParseException;

import MovieCorner.controller.ServerController;
import MovieCorner.controller.Controller;
import MovieCorner.mediator.ServerModelManager;
import MovieCorner.mediator.MovieCornerModel;
import MovieCorner.view.ServerConsole;
import MovieCorner.view.MovieCornerView;


public class MainServer {
	public static void main(String[] args) throws NumberFormatException, SQLException, ParseException {
		MainServer serv = new MainServer();
		serv.start(args);
	}
	   public void start(String[] args) throws NumberFormatException, SQLException, ParseException {
		      
	         MovieCornerModel model;
	         
	         if(args.length > 0)
	            model = new ServerModelManager(Integer.parseInt(args[0]));
	         else
	            model = new ServerModelManager(1337);
	         
		      MovieCornerView view = new ServerConsole();
		      Controller controller = new ServerController(model, view);
		      
		      view.start(controller);
		   }
}