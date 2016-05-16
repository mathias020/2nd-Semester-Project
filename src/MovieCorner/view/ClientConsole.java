package MovieCorner.view;

import java.util.Scanner;

import MovieCorner.controller.Controller;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;

public class ClientConsole implements MovieCornerView {
   private Scanner input;

   public ClientConsole() {
      input = new Scanner(System.in);
   }

   private int consoleMenu() {
      show("Menu:");
      show("1. Login");
      show("2. Get Movies");
      show("3. Get TV shows");
      show("4. Get list of users");
      show("5. Search by title");
      show("6. Register");
      show("7. Add review");
      show("0 quit");
      int choice= input.nextInt();

      return choice;

   }

   public void start(Controller controller){
      int choice;
      do
      {
         choice=consoleMenu();
         
         switch(choice)
         {
         case 1: 
            controller.execute("Login");
            break;
         case 2:
            controller.execute("All Movies");
            break;
         case 3:
            controller.execute("All TV shows");
            break;
         case 4:
            controller.execute("Get users");
            break;
         case 5:
            controller.execute("Search for tvshow or movie");
            break;
         case 6:
        	 controller.execute("Register");
        	 break;
         case 7:
        	 controller.execute("addReview");
        	 break;
         default:
             show("Shutting down");
             choice=-1;
         }
         
      } while(choice!=-1);

   }


   public String get(String what) {
      input.nextLine();
      System.out.println(what);
      String temp = input.nextLine();
      return temp;
   }

   @Override
   public void show(String message) {
      System.out.println(message);
      
   }
   
   @Override
   public void show(Object[] message) {
      for (Object msg:message)
         show(msg.toString());
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
