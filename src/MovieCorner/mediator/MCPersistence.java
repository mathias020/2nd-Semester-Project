package MovieCorner.mediator;


import java.sql.SQLException;
import java.text.ParseException;
import utility.collection.ArrayList;

import MovieCorner.model.Comment;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;
import MovieCorner.model.ItemList;
import MovieCorner.model.Review;
import MovieCorner.model.User;
import MovieCorner.model.UserList;

public interface MCPersistence {
   /**
    * Gets the items from the database, and returns them in an ItemList.
    * 
    * @return returns an ItemList containing all the items stored in the
    *         database.
    */
   public ItemList loadItems() throws SQLException, NumberFormatException, ParseException;
   
   /**
    * <b>Not implemented</b> Saves a list of items to the database
    * 
    * @return returns an 1 if the saving was successfull and returns -1 if it
    *         was unsuccessfull
    * @exception throws an SQLException
    * @argument item is the item that has to be saved to the database
    */
   public int saveItems(ItemList list);
   
   /**
    * Saves the item to the database
    * 
    * @return returns an 1 if the saving was successfull and returns -1 if it
    *         was unsuccessfull
    * @exception throws an SQLException
    * @argument item is the item that has to be saved to the database
    */
   public int saveItem(Item item) throws SQLException;
   
   /**
    * Checks if the email and password are a match to the data stored in the
    * user table in the database
    * 
    * @param email
    *           - String containing the email of the user
    * @param password
    *           - String containing the password of the user
    * @return A user object with the information of the user from the database
    *         if email and password match what is stored in the database if not
    *         it returns null
    */
   public User login(String username, String password) throws SQLException;
   
   /**
    * Gets the users from the database and returns them in an UserList
    * 
    * @return UserList containing all the users stored in the database
    */
   public UserList loadUsers() throws SQLException;
   
   /**
    * Saves the user with the password chosen to the database
    * 
    * @param user
    *           contains the information about the user
    * @param password
    *           a string containing the chosen password of the user
    * @return 1 if the saving was successfull
    * @exception throws SQLException
    */
   public int saveUser(User user, String password) throws SQLException;
   
   /**
    * Gets all the comments for a specific item
    * 
    * @param item The item to get comments for
    * @return An ArrayList containing Comment-objects
    * @throws SQLException
    *          
    */
   public ArrayList<Comment> getAllComments(Item item) throws SQLException;
   
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
   public void addComment(Comment comment, int itemID, boolean isMovie) throws SQLException;
   
   /**
    * Adds the episodeID and userID to watched_shows table in the database
    * 
    * @param episodeID
    *           - the ID of the episode that the user marked of as watched
    * @param userID
    *           - the ID of the user who marked the episode of as watched
    */
   public void addWatchedEpisode(int episodeID, int userID) throws SQLException;
   
   /**
    * Adds the itemID and userID to the table watched_movies in the database
    * 
    * @param itemID
    *           - the ID of the movie that has been watched by the user
    * @param userID
    *           - the ID of the user who marked the movie as watched
    */
   public void addWatchedMovie(int itemID, int userID) throws SQLException;
   
   /**
    * ¨Gets an Episode array of watched episodes by userID
    * 
    * @param userID The id of the user
    * 
    * @throws SQLException
    *          If 
    */
   public Episode[] getWatchedEpisodesByUserID(int userID) throws SQLException;
   
   /**
    * Gets all ids of watched movies by the user with the userID as ID
    * 
    * @param userID
    *           is the ID of the user whose watched movies needs to be found
    * @return an array of integers containing the IDs of the movies watched by
    *         the user with id userID - returns null if none found
    * @throws SQLException
    */
   public int[] getWatchedMoviesByUserID(int userID) throws SQLException;
   
   /**
    * Adds a movie as a favorite for a user with the specified ID
    * @param movieID The ID of the movie to favorite
    * @param userID The ID of the user
    * @throws SQLException
    */
   public void addFavoriteMovie(int movieID, int userID) throws SQLException;
   
   /**
    * Adds a TV Show as a favorite for a user with the specified ID
    * @param tvshowsID The ID of the TV Show to favorite
    * @param userID The ID of the user
    * @throws SQLException
    */
   public void addFavoriteTvShow(int tvshowsID, int userID) throws SQLException;
   
   /**
    * Gets a list of favorite movies for a user with the specified ID
    * @param userID The ID of the user
    * @return An integer array containing the IDs of the movies
    * @throws SQLException
    */
   public int[] getFavoriteMoviesByUserID(int userID) throws SQLException;
   
   /**
    * Gets a list of favorite TV shows for a user with the specified ID
    * @param userID The ID of the user
    * @return An integer array containing the ids of the favorited TV Shows
    * @throws SQLException
    */
   public int[] getFavoriteTvShowsByUserID(int userID) throws SQLException;
   
   /**
    * Adds an episode to a TV Show with the specified ID
    * @param episode The episode the add
    * @param tvShowID The ID of the TV Show that the episode should be added to
    * @throws SQLException
    */
   public void addEpisode(Episode episode, int tvShowID) throws SQLException;
   
   /**
    * Gets the reviews for a movie with the specified ID
    * @param movieID The ID of the movie
    * @return An array of reviews
    * @throws SQLException
    */
   public Review[] getMovieReviews(int movieID) throws SQLException;
   
   /**
    * Adds a review to an episode
    * @param review The review to add
    * @throws SQLException
    */
   public void addEpisodeReview(Review review) throws SQLException;
   
   /**
    * Adds a review to a movie
    * @param review The review to add
    * @throws SQLException
    */
   public void addMovieReview(Review review) throws SQLException;
   
   /**
    * Gets reviews for an episode with the specified ID
    * @param episodeID The id of the episode
    * @return A list of reviews
    * @throws SQLException
    */
   public Review[] getEpisodeReviews(int episodeID) throws SQLException;
   
   /**
    * Removes a comment
    * @param itemID The ID of them item on which the comment is
    * @param commentID The ID of the comment
    * @throws SQLException
    */
   public void removeComment(int itemID, int commentID) throws SQLException;
   
   /**
    * Updates the information of a user by ID.
    * @param userID The ID of the user to update
    * @param name A string containing the name
    * @param email A string containing the email
    * @param isAdmin A boolean indicating whether the user is admin or not
    * @throws SQLException
    */
   public void updateUser(int userID, String name, String email, boolean isAdmin) throws SQLException;
   
   /**
    * Removes a user by ID
    * @param userID The ID of the user
    * @throws SQLException
    */
   public void removeUser(int userID) throws SQLException;
   
   /**
    * Updates an item.<br /> <i><b>The Item-object must have an ID that exists in the database</b></i>
    * @param item The updated item object
    * @throws SQLException
    */
   public void updateItem(Item item) throws SQLException;
   
   /**
    * Gets friends for a user with the specified ID
    * @param userID The ID of the user
    * @return A list of user IDs of the friends
    * @throws SQLException
    */
   public int[] getFriendsByUserID(int userID) throws SQLException;
   
   /**
    * Adds a friend to a user with specified ID
    * @param userID The user that adds the friend
    * @param friendID The userID of the friend to add
    * @throws SQLException
    */
   public void addFriendForUserID(int userID, int friendID) throws SQLException;

}