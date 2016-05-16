package MovieCorner.model;

import java.io.Serializable;
import utility.collection.ArrayList;

public class UserList implements Serializable
{

   private ArrayList<User> users;

   /**
    * Constructor for creating a UserList
    * 
    * @param users
    *           ArrayList that contains all the users
    */
   public UserList(ArrayList<User> users)
   {
      this.users = users;
   }

   /**
    * Search for a specific user
    * 
    * @param id
    *           The unique id for each user
    * @return Returns a specific user if the id given as argument matches the id
    *         of a user inside the list, else it returns null
    */
   public User getUser(int id)
   {
      for (User user : users)
         if (user.getID() == id)
            return user;

      return null;
   }
   
   /**
    * Gets the underlying ArrayList of users
    * 
    * @return Returns an ArrayList of User-objects
    */
   public ArrayList<User> get()
   {
	   return users;
   }

   /**
    * Adds a new user to the list
    * 
    * @param user
    *           The user that will be added to the user list
    */
   public void addUser(User user)
   {
      users.add(user);
   }

   /**
    * @return Returns the size of the UserList
    */
   public int size()
   {
      return users.size();
   }

}
