package MovieCorner.model;

import java.io.Serializable;

public class User implements Serializable
{

   private String nickname;
   private String email;
   private int userID;
   private boolean isAdmin;

   /**
    * Constructor for creating a new User
    * 
    * @param nickname
    *           User's nickname
    * @param email
    *           User's email
    * @param userID
    *           User's unique identifier
    * @param isAdmin
    *           Boolean status that shows if the user has admin status or not
    */
   public User(String nickname, String email, int userID, boolean isAdmin)
   {
      this.nickname = nickname;
      this.email = email;
      this.userID = userID;
      this.isAdmin = isAdmin;
   }

   /**
    * @return Returns the user's nickname
    */
   public String getNickname()
   {
      return nickname;
   }

   /**
    * @return Returns the user's email
    */
   public String getEmail()
   {
      return email;
   }

   /**
    * @return Returns the user's id
    */
   public int getID()
   {
      return userID;
   }

   /**
    * @return Returns true if user is admin and return false if user is not admin
    *         
    */
   public boolean isAdmin()
   {
      return isAdmin;
   }

   /**
    * Boolean that checks if the user is a part of the User class ( instanceof)
    */
   public boolean equals(Object obj)
   {
      if (!(obj instanceof User))
         return false;
      else
      {
         User temp = (User) obj;

         return temp.userID == userID;
      }
   }

}
