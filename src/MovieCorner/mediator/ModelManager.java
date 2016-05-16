package MovieCorner.mediator;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import utility.collection.ArrayList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import MovieCorner.model.Comment;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;
import MovieCorner.model.ItemList;
import MovieCorner.model.Movie;
import MovieCorner.model.Review;
import MovieCorner.model.TVShow;
import MovieCorner.model.User;
import MovieCorner.model.UserList;

public class ModelManager implements MovieCornerModel
{
   private ItemList items;
   private UserList users;
   private ClientCommunicationThread ccT;
   
   /**
    * Constructor for the Client ModelManager
    * @param host The host address to connect to (default = localhost)
    * @param port The port to connect to on the host (default = 1337)
    * @throws SQLException
    * @throws NumberFormatException
    * @throws ParseException
    * @throws IOException
    */
   public ModelManager(String host, int port) throws SQLException, NumberFormatException,
         ParseException, IOException
   {
      ccT = new ClientCommunicationThread(host, port);
   }
   
   public void loadItems() throws IOException
   {
	   items = (ItemList) ccT.exec("getAllItems");
   }
   
   public void loadUsers() throws IOException
   {
	   users = (UserList) ccT.exec("getAllUsers");
   }
   
   public ItemList getItems()
   {
      return items;
   }

   public UserList getUsers()
   {
      return users;
   }

   public User getUserById(int id)
   {
      return users.getUser(id);
   }

   public Item getItemById(int id, int type)
   {
      return items.getItemById(id, type);
   }

   public void addUser(User user, String password) throws SQLException,
         IOException
   {
      Integer rCode = (Integer) ccT.exec("saveUser", user, password);
            
      if (rCode.intValue() == 1)
         throw new MySQLIntegrityConstraintViolationException();
      else if (rCode.intValue() == 2)
         throw new SQLException();
      else
         users = (UserList) ccT.exec("getAllUsers");

   }

   public void addItem(Item item) throws SQLException, IOException
   {
      Integer rCode = (Integer) ccT.exec("saveItem", item);
      
      if(rCode == 0)
         items = (ItemList) ccT.exec("getAllItems");
   }

   public User checkLogin(String email, String password) throws SQLException,
         IOException
   {
	   User user = (User) ccT.exec("login", email, password);
	   
	   if(user != null) {
		   if(user.getID() == -1)
			   throw new SQLException();
	   } else
		   throw new IOException();
			   
	   
      return user;
   }

   public Item[] search(String title)
   {
      ArrayList<Item> temp = new ArrayList<Item>();

      for (int i = 0; i < items.size(); i++)
      {
         if (items.get().get(i).getTitle().toUpperCase().indexOf(title.toUpperCase()) != -1)
            temp.add(items.get().get(i));
      }

      if (temp.size() > 0)
         return temp.toArray(new Item[temp.size()]);

      else
         return null;
   }

   // Added 3 methods - not used yet, but probably should to make it real MVC

   /**
    * returns an array of tvshows with matching title
    * 
    * @param title
    *           the title of the tvshow
    * @return an array containing the tvshows matching with the title
    */
   public TVShow[] searchShows(String title)
   {
      if (items.get() == null)
      {
         return null;
      }
      ArrayList<TVShow> temp = new ArrayList<TVShow>();

      for (int i = 0; i < items.size(); i++)
      {
         if (items.get().get(i) instanceof TVShow)
         {
            if (items.get().get(i).getTitle().equals(title))
               temp.add((TVShow) items.get().get(i));
         }
      }
      

      return temp.toArray(new TVShow[temp.size()]);
   }

   /**
    * gets all the tvshows in the system
    * 
    * @return an array of TVShow containing all the tv shows in the system
    */
   public TVShow[] getAllTVShows()
   {
      if (items.get() == null)
      {
         return null;
      }

      ArrayList<TVShow> temp = new ArrayList<TVShow>();

      for (int i = 0; i < items.size(); i++)
      {
         if (items.get().get(i) instanceof TVShow)
         {
            temp.add((TVShow) items.get().get(i));
         }
      }

      return temp.toArray(new TVShow[temp.size()]);
   }

   /**
    * returns all movies as an array of the type Movie
    * 
    * @return an array of movies containing all movies in the system
    */
   public Movie[] getAllMovies()
   {
      if (items.get() == null)
      {
         return null;
      }

      ArrayList<Movie> temp = new ArrayList<Movie>();

      for (int i = 0; i < items.size(); i++)
      {
         if (items.get().get(i) instanceof Movie)
         {
            temp.add((Movie) items.get().get(i));
         }
      }

      return temp.toArray(new Movie[temp.size()]);
   }

   @Override
   public ArrayList<Comment> getCommentsByItem(Item item) throws SQLException,
         IOException
   {

      ArrayList<Comment> comments = (ArrayList<Comment>) ccT.exec(
            "getComments", item);

      return comments;

   }

   @Override
   public void addComment(Comment comment, int itemId, boolean isMovie)
         throws SQLException, IOException
   {

      ccT.exec("addComment", comment, itemId, isMovie);
   }

   @Override
   public void addWatchedEpisode(int episodeID, int userID)
         throws SQLException, IOException
   {

      ccT.exec("addWatchedEpisode", episodeID, userID);

   }

   @Override
   public void addWatchedMovie(int itemID, int userID) throws SQLException,
         IOException
   {
      ccT.exec("addWatchedMovie", itemID, userID);

   }

   @Override
   public Episode[] getWatchedEpisodesByUserID(int userID) throws SQLException,
         IOException
   {
      Episode[] watched = (Episode[]) ccT.exec("getWatchedEpisodes", userID);

      return watched;
   }

   @Override
   public int[] getWatchedMoviesByUserID(int userID) throws SQLException,
         IOException
   {

      int[] watched = (int[]) ccT.exec("getWatchedMovies", userID);

      return watched;
   }

   public Item getWatchedMovies(int itemID)
   {

      for (int i = 0; i < items.size(); i++)
      {
         if (items.get().get(i) instanceof Movie)
         {
            if (items.get().get(i).getId() == itemID)
               return items.get().get(i);
         }
      }

      return null;
   }

   @Override
   public int[] getFavoriteTvShowsByUserID(int userID) throws SQLException,
         IOException
   {
      int[] favtvshows = (int[]) ccT.exec("getFavoriteShows", userID);

      return favtvshows;
   }
   
   public void addEpisode(Episode episode, int tvShowID) throws SQLException, IOException
   {
	   Integer temp=(Integer)ccT.exec("addEpisode", episode,tvShowID);
	   
	   if(temp.intValue()==2)
		   throw new MySQLIntegrityConstraintViolationException();
	   else
	      loadItems();
   }

   @Override
   public int[] getFavoriteMoviesByUserID(int userID) throws SQLException,
         IOException
   {
      int[] favmovies = (int[]) ccT.exec("getFavoriteMovies", userID);

      return favmovies;
   }

   @Override
   public void addFavoriteMovie(int movieID, int userID) throws SQLException,
         IOException
   {

      Integer rCode = (Integer) ccT.exec("addFavoriteMovie", movieID, userID);
      
      if(rCode == 2)
         throw new MySQLIntegrityConstraintViolationException();

   }

   @Override
   public void addFavoriteTvShow(int tvshowsID, int userID)
         throws SQLException, IOException
   {
      Integer rCode = (Integer) ccT.exec("addFavoriteTvShow", tvshowsID, userID);
      
      if(rCode == 2)
         throw new MySQLIntegrityConstraintViolationException();

   }
   
   public void addEpisodeReview(Review review) throws SQLException, IOException
   {
	   ccT.exec("addEpisodeReview", review);  
   }
   
   public void addMovieReview(Review review) throws SQLException, IOException
   {
	   ccT.exec("addMovieReview", review);
   }

@Override
	public Review[] getEpisodeReviews(int episodeID) throws SQLException, IOException {
	return (Review[]) ccT.exec("getEpisodeReviews", episodeID);
}

@Override
	public Review[] getMovieReviews(int movieID) throws SQLException, IOException {
	return (Review[]) ccT.exec("getMovieReviews", movieID);
}

   @Override
   public void removeComment(int itemID, int commentID) throws SQLException, IOException {
      ccT.exec("removeComment", itemID, commentID);
   }
   
   @Override
   public void removeUser(int userID) throws SQLException, IOException {
      Integer rCode = (Integer) ccT.exec("removeUser", userID);
      
      if(rCode == 0)
         loadUsers();
   }
   
   @Override
   public void updateUser(int userID, String name, String email, boolean isAdmin) throws SQLException, IOException {
     Integer rCode = (Integer) ccT.exec("updateUser", userID, name, email, isAdmin);

     if(rCode == 0)
        loadUsers();
   }
   

	@Override
	public User[] searchUsers(String search) {
		ArrayList<User> actualUsers = users.get();
		ArrayList<User> result = new ArrayList<User>();
		
		for(int i = 0; i < actualUsers.size(); i++)
		{
			if(actualUsers.get(i).getNickname().indexOf(search) != -1 || actualUsers.get(i).getEmail().indexOf(search) != -1)
				result.add(actualUsers.get(i));
		}
		
		return result.toArray(new User[result.size()]);
	}
	
	  @Override
	   public void updateItem(Item item) throws SQLException, IOException {
	      int code = (Integer) ccT.exec("manageItem", item);
	      
	      if(code == 1)
	         throw new SQLException();
	      else 
	         loadItems();
	   }

   @Override
   public int[] getFriendIDsByUserID(int userID) throws SQLException,
         IOException {
      return (int[]) ccT.exec("getFriendIDsByUserID", userID);
   }

   @Override
   public void addFriendForUserID(int userID, int friendID) throws SQLException, IOException {
      ccT.exec("addFriendForUserID", userID, friendID);
   }

   @Override
   public int getPort() {
      return 0; // Not used on the client
   }
}
