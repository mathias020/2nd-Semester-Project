package MovieCorner.mediator;

import java.sql.SQLException;
import java.text.ParseException;

import utility.Database;
import utility.collection.ArrayList;
import MovieCorner.model.*;

/**
 * @author Mathias The adapter class handles all the communication with the
 *         database class, translating the clients actions into sql statements
 */
public class MCAdapter implements MCPersistence
{
   private static final String DB_NAME = "MovieCorner";
   private Database database;

   public MCAdapter()
   {
      try
      {
         database = new Database(DB_NAME, "sep2015", "viasep2015");
      }
      catch (ClassNotFoundException | SQLException e)
      {
         // TODO Auto-generasted catch block
         e.printStackTrace();
      }
   }


   public ItemList loadItems() throws SQLException, NumberFormatException,
         ParseException
   {
      ArrayList<Object[]> temp1 = database.query("SELECT * FROM tvshows");
      ArrayList<Object[]> temp2 = database.query("SELECT * FROM movies");

      ArrayList<Item> items = new ArrayList<Item>();

      for (int i = 0; i < temp1.size(); i++)
      {
         Object[] rows = temp1.get(i);
         ArrayList<Object[]> tempEpisodes = database.query(
               "SELECT * FROM episodes where showID= ?", rows[3]);
         ArrayList<Episode> episodes = new ArrayList<Episode>();
         for (int k = 0; k < tempEpisodes.size(); k++)
         {
            Object[] epRows = tempEpisodes.get(k);
            episodes.add(new Episode(Integer.parseInt(epRows[0].toString()),
                  epRows[1].toString(), Integer.parseInt(epRows[2].toString()),
                  epRows[3].toString(), splitDate(epRows[4]), epRows[5]
                        .toString(), Integer.parseInt(epRows[6].toString())));
         }

         boolean status = false;
         if (rows[0].toString().toLowerCase().equals("true"))
            status = true;

         items.add(new TVShow(rows[1].toString(), rows[2].toString(),
               splitDate(rows[4]), split(rows[5]), split(rows[6]), rows[7]
                     .toString(), Integer.parseInt(rows[3].toString()),
               episodes, status));
      }

      for (int i = 0; i < temp2.size(); i++)
      {
         Object[] rows = temp2.get(i);

         items.add(new Movie(rows[2].toString(), rows[3].toString(),
               splitDate(rows[5]), split(rows[6]), split(rows[7]), rows[8]
                     .toString(), Integer.parseInt(rows[4].toString()), rows[0]
                     .toString()));
      }

      return new ItemList(items);
   }

   public int saveItem(Item item) throws SQLException
   {
      // NOT FINISHED!!!!

      if (item instanceof Movie)
      {
         Movie temp = (Movie) item;

         String sql = "INSERT INTO movies(description, title, releaseDate, genres, tags, director, duration, is_finished) "
               + "SELECT * FROM (SELECT ?,?,?,?,?,?,?,?) AS tmp";

         database.update(sql, temp.getDescription(), temp.getTitle(),
               stringDate(temp.getReleaseDate()),
               createString(temp.getGenres()), createString(temp.getTags()),
               temp.getDirector(), temp.getDuration(), "true");

         return 1;
      }

      if (item instanceof TVShow)
      {
         String sql = "INSERT INTO tvshows(description, title, releaseDate, genres, tags, director, is_finished) "
               + "SELECT * FROM (SELECT ?,?,?,?,?,?,?) AS tmp";
         TVShow temp = (TVShow) item;

         boolean status = temp.isFinished();

         database.update(sql, temp.getDescription(), temp.getTitle(),
               stringDate(temp.getReleaseDate()),
               createString(temp.getGenres()), createString(temp.getTags()),
               temp.getDirector(), status);
         return 1;
      }
      else
         return -1; // -1 indicates failure;
   }

   /**
    * @param temp
    *           the date object that has to be translated to string in the
    *           format year-month-day
    * @return a string containing the date in format year-month-day which the
    *         database takes
    */
   private String stringDate(MyDate temp)
   {
      String date = "" + temp.getYear() + "-" + temp.getMonth() + "-"
            + temp.getDay();

      return date;
   }

   /**
    * turns an ArrayList of strings into a string divided by commas
    * 
    * @param string
    *           the ArrayList wished to be turned into a string
    * @return a string containing the Strings from the ArrayList
    */
   private String createString(ArrayList<String> string)
   {
      String temp = "";

      for (int i = 0; i < string.size(); i++)
      {
         temp += string.get(i);

         if (i != string.size() - 1)
         {
            temp += ",";
         }
      }

      return temp;
   }


   public User login(String email, String password) throws SQLException
   {
      ArrayList<Object[]> temp = database.query(
            "SELECT * FROM users where email = ?", email);

      if (temp.size() == 0)
         throw new SQLException();

      Object[] tempOb = temp.get(0);
      String passwordSaved = tempOb[4].toString();

      if (passwordSaved != null)
      {
         if (passwordSaved.equals(password))
         {
            boolean status = false;
            if (tempOb[3].toString().equals("true"))
               status = true;

            return new User(tempOb[0].toString(), tempOb[1].toString(),
                  Integer.parseInt(tempOb[2].toString()), status);
         }
         else
            throw new SQLException();
      }
      else
         throw new SQLException();

   }


   public int saveUser(User user, String password) throws SQLException
   {
      // not finished - needs to check?

//      String sql = "INSERT INTO users(nickname, password, email, isAdmin) "
//            + "SELECT * FROM (SELECT ?,?,?,?) AS tmp";
      
      String sql = "INSERT INTO users(nickname, password, email, isAdmin) VALUES (?, ?, ?, ?)";

      database.update(sql, user.getNickname(), password, user.getEmail(),
            user.isAdmin());

      return 1;
   }


   public UserList loadUsers() throws SQLException
   {
      ArrayList<Object[]> temp = database.query("SELECT * FROM users");

      ArrayList<User> users = new ArrayList<User>();

      for (int i = 0; i < temp.size(); i++)
      {
         Object[] rows = temp.get(i);

         boolean status = false;
         if (rows[3].toString().toLowerCase().equals("true"))
            status = true;

         users.add(new User(rows[0].toString(), rows[1].toString(), Integer
               .parseInt(rows[2].toString()), status));
      }

      return new UserList(users);
   }

   /**
    * Splits obj into a an ArrayList
    * 
    * @param obj
    *           an obj containing an amount of words/sentences seperated by
    *           commas
    * @return an ArrayList containing a string for each word/sentence that was
    *         seperated by commas
    */
   private ArrayList<String> split(Object obj)
   {
      ArrayList<String> list = new ArrayList<>();

      String[] array = obj.toString().split(",");

      for (String a : array)
         list.add(a);

      return list;
   }

   /**
    * Turns the information from the object into a Date object
    * 
    * @param obj
    *           an object containing information about the date stored in the
    *           database
    * @return a Date object containing a date which was stored in the obj object
    */
   private MyDate splitDate(Object obj)
   {

      String[] array = obj.toString().split("-");

      MyDate date = new MyDate(Integer.parseInt(array[2]),
            Integer.parseInt(array[1]), Integer.parseInt(array[0]));

      return date;
   }

   public int saveItems(ItemList list)
   {
      // not used?
      return 0;
   }

   public ArrayList<Comment> getAllComments(Item item) throws SQLException
   {
      ArrayList<Object[]> temp = null;

      if (item instanceof Movie)
      {
         temp = database
               .query(
                     "SELECT userID, `text`, nickname, `date`, id FROM movie_comments WHERE itemId = ?",
                     item.getId());

      }
      if (item instanceof TVShow)
      {
         temp = database
               .query(
                     "SELECT userID, `text`, nickname, `date`, id FROM show_comments WHERE itemId = ?",
                     item.getId());
      }

      if (temp.size()==0)
      {
         return null;
      }

      ArrayList<Comment> comments = new ArrayList<Comment>();

      for (int i = 0; i < temp.size(); i++)
      {
         Object[] temp2 = temp.get(i);

         comments.add(new Comment(Integer.parseInt(temp2[0].toString()),
               temp2[1].toString(), temp2[2].toString(), Integer
                     .parseInt(temp2[4].toString()), splitDate(temp2[3])));

      }

      return comments;

   }

   /**
    * Adds the comment to the database along with the itemID
    *
    * @param comment
    *           contains the comment object wished to be stored
    * @param itemID
    *           the ID of the Item the comment belongs to
    * @param isMove
    *           boolean - true if the item is a movie, false if the item is a
    *           show
    */
   public void addComment(Comment comment, int itemID, boolean isMovie)
         throws SQLException
   {
      String sql = null;

      if (isMovie)
      {
         sql = "INSERT INTO movie_comments(`text`, userID, nickname, `date`, itemId) "
               + "SELECT * FROM (SELECT ?,?,?,?,?) AS tmp";
      }
      else
      {
         sql = "INSERT INTO show_comments(`text`, userID, nickname, `date`, itemId) "
               + "SELECT * FROM (SELECT ?,?,?,?,?) AS tmp";
      }

      if (sql != null)
      {
         database.update(sql, comment.getText(), comment.getUserID(),
               comment.getNickname(), stringDate(comment.getDate()), itemID);
      }
   }


   public int[] getWatchedMoviesByUserID(int userID) throws SQLException
   {
      ArrayList<Object[]> temp = database.query(
            "SELECT itemID FROM watched_movies where userID= ?", userID);

      if (temp.size() == 0)
         return null;

      int[] movieIDs = new int[temp.size()];

      for (int i = 0; i < temp.size(); i++)
      {
         Object[] tempOb = temp.get(i);

         movieIDs[i] = (int) tempOb[0];
      }

      return movieIDs;
   }

   /**
    * Gets all ids of the episodes watched by the user with id userID
    * 
    * @param userID
    *           is the ID of the user whose watched episodes need to be found
    * @return an array of integers containing the IDs of the episodes watched
    * @throws SQLException
    */
   private int[] getEpisodListWatchedByUserID(int userID) throws SQLException
   {
      ArrayList<Object[]> temp = database.query(
            "SELECT itemID FROM watched_shows where userID= ?", userID);

      if (temp.size() == 0)
         return null;

      int[] episodeIDs = new int[temp.size()];

      for (int i = 0; i < temp.size(); i++)
      {
         Object[] tempOb = temp.get(i);

         episodeIDs[i] = (int) tempOb[0];
      }

      return episodeIDs;

   }

   public void addWatchedEpisode(int episodeID, int userID) throws SQLException
   {
      String sql = "INSERT INTO watched_shows(userID,itemID) "
            + "SELECT * FROM (SELECT ?,?) AS tmp";

      database.update(sql, userID, episodeID);

   }

   public void addWatchedMovie(int itemID, int userID) throws SQLException
   {
      String sql = "INSERT INTO watched_movies(userID,itemID) "
            + "SELECT * FROM (SELECT ?,?) AS tmp";

      database.update(sql, userID, itemID);
   }
   
   public Episode[] getWatchedEpisodesByUserID(int userID) throws SQLException
   {
      int[] ids = getEpisodListWatchedByUserID(userID);

      ArrayList<Episode> temp = new ArrayList<Episode>();

      if (ids == null)
         return null;

      for (int i = 0; i < ids.length; i++)
      {
         Episode tempEp = getEpisodeByID(ids[i]);
         if (tempEp != null)
            temp.add(tempEp);
      }

      return temp.toArray(new Episode[temp.size()]);
   }

   private Episode getEpisodeByID(int episodeID) throws SQLException
   {
      String sql = "SELECT seasonNo, title, episodeNo, resume, releaseDate, duration, id FROM episodes WHERE id=?";

      ArrayList<Object[]> temp = database.query(sql, episodeID);

      if (temp.size() == 0)
         return null;

      Object[] obj = temp.get(0);

      return new Episode(Integer.parseInt(obj[0].toString()),
            obj[1].toString(), Integer.parseInt(obj[2].toString()),
            obj[3].toString(), splitDate(obj[4].toString()), obj[5].toString(),
            Integer.parseInt(obj[6].toString()));
   }

   public void addFavoriteMovie(int movieID, int userID) throws SQLException
   {
      String sql = "INSERT INTO favorites_movies(userID,itemID) "
            + "SELECT * FROM (SELECT ?,?) AS tmp";

      database.update(sql, userID, movieID);

   }

   public void addFavoriteTvShow(int tvshowsID, int userID) throws SQLException
   {
      String sql = "INSERT INTO favorites_tvshows(userID,itemID) "
            + "SELECT * FROM (SELECT ?,?) AS tmp";

      database.update(sql, userID, tvshowsID);

   }
   
   public void addEpisode(Episode episode, int tvShowID) throws SQLException
   {
	   String sql= "INSERT INTO episodes(seasonNo, title, episodeNo, `resume`, releaseDate, duration, showId) "
			   		+ "VALUES (?,?,?,?,?,?,?)";
	   database.update(sql, episode.getSeasonNo(), episode.getTitle(), episode.getEpisodeNo(), episode.getResume(), stringDate(episode.getReleaseDate()), episode.getDuration(), tvShowID);
   }

   public int[] getFavoriteMoviesByUserID(int userID) throws SQLException
   {
      ArrayList<Object[]> temp = database.query(
            "SELECT itemID FROM favorites_movies where userID= ?", userID);

      if (temp.size() == 0)
         return null;

      int[] movies = new int[temp.size()];

      for (int i = 0; i < temp.size(); i++)
      {
         Object[] tempOb = temp.get(i);

         movies[i] = (int) tempOb[0];
      }

      return movies;
   }

   public int[] getFavoriteTvShowsByUserID(int userID) throws SQLException
   {
      ArrayList<Object[]> temp = database.query(
            "SELECT itemID FROM favorites_tvshows where userID= ?", userID);

      if (temp.size() == 0)
         return null;

      int[] tvshows = new int[temp.size()];

      for (int i = 0; i < temp.size(); i++)
      {
         Object[] tempOb = temp.get(i);

         tvshows[i] = (int) tempOb[0];
      }

      return tvshows;

   }

   public void addEpisodeReview(Review review) throws SQLException
   {
	   String sql= "INSERT INTO episode_reviews(review, userID, episodeID, rating, dateAdded, nickname) "
			   	+ "SELECT * FROM (SELECT ?,?,?,?,?,?) AS tmp";
	   
	   database.update(sql, review.getReview(), review.getUserID(), review.getItemID(), review.getRating(), stringDate(review.getDateAdded()), review.getNickname());
   }
   
   public void addMovieReview(Review review) throws SQLException
   {
	   String sql= "INSERT INTO movie_reviews(review, userID, movieID, rating, dateAdded, nickname) "
			   	+ "SELECT * FROM (SELECT ?,?,?,?,?,?) AS tmp";
	   
	   database.update(sql, review.getReview(), review.getUserID(), review.getItemID(), review.getRating(), stringDate(review.getDateAdded()), review.getNickname());
   }
   
   public Review[] getEpisodeReviews(int episodeID) throws SQLException
   {
	  String sql= "SELECT * FROM episode_reviews WHERE episodeID=?";
	  
	  ArrayList<Object[]> temp= database.query(sql, episodeID);
	  ArrayList<Review> reviews= new ArrayList<Review>();
	  
	  for(int i=0; i<temp.size(); i++)
	  {
		  Object[] tempArray= temp.get(i);
		  reviews.add(new Review(tempArray[0].toString(), Integer.parseInt(tempArray[1].toString()), Integer.parseInt(tempArray[2].toString()), Double.parseDouble(tempArray[3].toString()), splitDate(tempArray[4]), tempArray[5].toString()));  
	  }
	  
	  if(temp.size() == 0)
	     return null;
	  
	  return reviews.toArray(new Review[temp.size()]);	  
   }
   
   public Review[] getMovieReviews(int movieID) throws SQLException
   {
		  String sql= "SELECT * FROM movie_reviews WHERE movieID=?";
		  
		  ArrayList<Object[]> temp= database.query(sql, movieID);
		  ArrayList<Review> reviews= new ArrayList<Review>();
		  
		  for(int i=0; i<temp.size(); i++)
		  {
			  Object[] tempArray= temp.get(i);
			  reviews.add(new Review(tempArray[0].toString(), Integer.parseInt(tempArray[1].toString()), Integer.parseInt(tempArray[2].toString()), Double.parseDouble(tempArray[3].toString()), splitDate(tempArray[4]), tempArray[5].toString()));  
		  }
		  
		  if(temp.size() == 0)
		     return null;
		  
		  return reviews.toArray(new Review[temp.size()]);	
   }

   public void removeComment(int itemID, int commentID) throws SQLException
   {
      String sql = "DELETE FROM movie_comments WHERE itemId = ? AND id = ?";
      database.update(sql, itemID, commentID);
      
      sql = "DELETE FROM show_comments WHERE itemId = ? AND id = ?";
      database.update(sql, itemID, commentID);
      
   }

   @Override
   public void updateUser(int userID, String name, String email, boolean isAdmin)
         throws SQLException {
      String sql = "UPDATE users SET nickname=?, email=?, isAdmin=? WHERE userId=?";
      
      database.update(sql, name, email, isAdmin, userID);
   }

   @Override
   public void removeUser(int userID) throws SQLException {
//      String sql = "DELETE FROM episode_reviews WHERE userID=?";
//      database.update(sql, userID);
//      sql = "DELETE FROM favorites_movies WHERE userID=?";
//      database.update(sql, userID);
//      sql = "DELETE FROM favorites_tvshows WHERE userID=?";
//      database.update(sql, userID);
//      sql = "DELETE FROM movie_comments WHERE userID=?";
//      database.update(sql, userID);
//      sql = "DELETE FROM movie_reviews WHERE userID=?";
//      database.update(sql, userID);
//      sql = "DELETE FROM show_comments WHERE userID=?";
//      database.update(sql, userID);
//      sql = "DELETE FROM watched_movies WHERE userID=?";
//      database.update(sql, userID);
//      sql = "DELETE FROM watched_shows WHERE userID=?";
//      database.update(sql, userID);
//      sql = "DELETE FROM users WHERE userID=?";
//      database.update(sql, userID);
      
//      String sql = "DELETE FROM episode_reviews WHERE userID=?; DELETE FROM favorites_movies WHERE userID=?; DELETE FROM favorites_tvshows WHERE userID=?; DELETE FROM movie_comments WHERE userID=?; DELETE FROM movie_reviews WHERE userID=?; DELETE FROM show_comments WHERE userID=?; DELETE FROM watched_movies WHERE userID=?; DELETE FROM watched_shows WHERE userID=?; DELETE FROM users WHERE userID=?;";
      
      String sql = "DELETE FROM `users` WHERE userID = ?";
      
      database.update(sql, userID);
   }
   
   @Override
   public void updateItem(Item item) throws SQLException {
      if(item instanceof Movie)
      {
         Movie movie = (Movie) item;
         database.update("UPDATE `movies` SET duration = ?, description = ?, title = ?, releaseDate = ?, genres = ?, tags = ?, director = ? WHERE id = ?", 
               movie.getDuration(),
               movie.getDescription(),
               movie.getTitle(),
               stringDate(movie.getReleaseDate()),
               createString(movie.getGenres()),
               createString(movie.getTags()),
               movie.getDirector(),
               movie.getId());
      }
      else
      {
         TVShow tvshow = (TVShow) item;
         database.update("UPDATE `tvshows` SET description = ?, title = ?, releaseDate = ?, genres = ?, tags = ?, director = ? WHERE id = ?", 
               tvshow.getDescription(),
               tvshow.getTitle(),
               stringDate(tvshow.getReleaseDate()),
               createString(tvshow.getGenres()),
               createString(tvshow.getTags()),
               tvshow.getDirector(),
               tvshow.getId());
      }
   }

   @Override
   public int[] getFriendsByUserID(int userID) throws SQLException {
      
      String sql= "SELECT friendID FROM friends WHERE userID=?";
      
      ArrayList<Object[]> temp = database.query(sql, userID);
      
      if(temp.size()==0)
    	  return null;
      
      int[] allFriendIDs = new int[temp.size()];
      
      
      for(int i=0; i<temp.size(); i++)
      {
         Object[] tempArray= temp.get(i);
         allFriendIDs[i] = Integer.parseInt(tempArray[0].toString());  
      }
      
      return allFriendIDs;   
   }

   @Override
   public void addFriendForUserID(int userID, int friendID) throws SQLException {
      String sql = "INSERT INTO friends(userID, friendID) "
            + "VALUES (?,?)";
      
      database.update(sql, userID, friendID);
   }
}
