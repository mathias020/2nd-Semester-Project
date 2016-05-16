package MovieCorner.mediator;

import java.io.IOException;
import java.sql.SQLException;

import utility.collection.ArrayList;
import MovieCorner.model.Comment;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;
import MovieCorner.model.ItemList;
import MovieCorner.model.Movie;
import MovieCorner.model.Review;
import MovieCorner.model.TVShow;
import MovieCorner.model.User;
import MovieCorner.model.UserList;

public interface MovieCornerModel
{
   /**
    * Gets all items
    * @return An ItemList containing all items
    */
   public ItemList getItems();
   
   /**
    * Gets all users
    * @return A UserList containing all users
    */
   public UserList getUsers();
   
   /**
    * Gets a user by its ID
    * @param id The ID of the user
    * @return A User-object with the specified ID
    */
   public User getUserById(int id);

   /**
    * Gets an item by its ID
    * @param id The ID of the item
    * @param type The type of the item (0 = movie, 1 = tvshow)
    * @return The Item that was found by the specified parameters
    */
   public Item getItemById(int id, int type);

   /**
    * Adds a user
    * @param user The User-object
    * @param password The password of the user
    * @throws SQLException
    * @throws IOException
    */
   public void addUser(User user, String password) throws SQLException,
         IOException;
   
   /**
    * Adds an item
    * @param item The item to add
    * @throws SQLException
    * @throws IOException
    */
   public void addItem(Item item) throws SQLException, IOException;
   
   /**
    * Checks if the email and password exists
    * @param email The email to check
    * @param password The password to check
    * @return Returns a User-object if the combination exists, otherwise null.
    * @throws SQLException
    * @throws IOException
    */
   public User checkLogin(String email, String password) throws SQLException,
         IOException;
   
   
   /**
    * Search for an item
    * @param title The title of the item
    * @return Returns a list of items
    */
   public Item[] search(String title);
   
   /**
    * Search for a user
    * @param username The username to search for
    * @return Returns a list of users
    */
   public User[] searchUsers(String username);
   
   /**
    * Search for a TV Show
    * @param title The title to search for
    * @return Returns a list of TV Shows
    */
   public TVShow[] searchShows(String title);
   
   /**
    * Gets all TV shows
    * @return Returns a list of all TV shows
    */
   public TVShow[] getAllTVShows();
   
   /**
    * Gets all movies
    * @return Returns a list of all movies
    */
   public Movie[] getAllMovies();
   
   /**
    * Gets all comments for a specified item
    * @param item The item to get comments for
    * @return Returns an ArrayList of comments
    * @throws SQLException
    * @throws IOException
    */
   public ArrayList<Comment> getCommentsByItem(Item item) throws SQLException,
         IOException;
   
   /**
    * Adds a comment to an item
    * @param comment The comment to add
    * @param itemId The ID of the item to add comment to
    * @param isMovie Indicates if the item is a movie or TV show (true = movie, false = TV show)
    * @throws SQLException
    * @throws IOException
    */
   public void addComment(Comment comment, int itemId, boolean isMovie)
         throws SQLException, IOException;
   
   /**
    * Adds an episode as watched for a specified user
    * @param episodeID The ID of the episode
    * @param userID The ID of the user
    * @throws SQLException
    * @throws IOException
    */
   public void addWatchedEpisode(int episodeID, int userID)
         throws SQLException, IOException;
   
   /**
    * Adds a movie as watched for a specified user
    * @param itemID The ID of the movie
    * @param userID The ID of the user
    * @throws SQLException
    * @throws IOException
    */
   public void addWatchedMovie(int itemID, int userID) throws SQLException,
         IOException;
   
   /**
    * Gets all watched episodes for a user with the specified ID
    * @param userID The user ID
    * @return Returns a list of episodes that the user watched
    * @throws SQLException
    * @throws IOException
    */
   public Episode[] getWatchedEpisodesByUserID(int userID) throws SQLException,
         IOException;
   
   /**
    * Gets all watched movies for a user with the specified ID
    * @param userID The ID of the user
    * @return Returns a list of IDs of the watched movies
    * @throws SQLException
    * @throws IOException
    */
   public int[] getWatchedMoviesByUserID(int userID) throws SQLException,
         IOException;
   
   /**
    * Gets all favorite tv shows by user ID
    * @param userID The ID of the user
    * @return Returns a list of ids for the TV shows that are favorited
    * @throws SQLException
    * @throws IOException
    */
   public int[] getFavoriteTvShowsByUserID(int userID) throws SQLException,
         IOException;
   
   /**
    * Gets all favorite movies by user ID
    * @param userID The id of the user
    * @return Returns a list of ids for the favorited movies
    * @throws SQLException
    * @throws IOException
    */
   public int[] getFavoriteMoviesByUserID(int userID) throws SQLException,
         IOException;
   
   /**
    * Adds a movie as a favorite for a user with the specified ID
    * @param movieID The ID of the movie
    * @param userID The ID of the user
    * @throws SQLException
    * @throws IOException
    */
   public void addFavoriteMovie(int movieID, int userID) throws SQLException,
         IOException;
   
   /**
    * Adds a tv show as a favorite for a user with the specified ID
    * @param tvshowsID The ID of the TV Show
    * @param userID The ID of the user
    * @throws SQLException
    * @throws IOException
    */
   public void addFavoriteTvShow(int tvshowsID, int userID)
         throws SQLException, IOException;
   
   /**
    * Adds an episode to the TV Show with the specified ID
    * @param episode The episode to add
    * @param tvShowID The ID of the TV show to add the episode to
    * @throws SQLException
    * @throws IOException
    */
   public void addEpisode(Episode episode, int tvShowID)
   		throws SQLException, IOException;
   
   /**
    * Adds a review to an episode
    * @param review The review to add
    * @throws SQLException
    */
   public void addEpisodeReview(Review review) throws SQLException, IOException;
   
   
   /**
    * Gets the reviews for a movie with the specified ID
    * @param movieID The ID of the movie
    * @return An array of reviews
    * @throws SQLException
    */
   public Review[] getMovieReviews(int movieID) throws SQLException, IOException;
   
   /**
    * Adds a review to a movie
    * @param review The review to add
    * @throws SQLException
    */
   public void addMovieReview(Review review) throws SQLException, IOException;
   
   /**
    * Gets reviews for an episode with the specified ID
    * @param episodeID The id of the episode
    * @return A list of reviews
    * @throws SQLException
    */
   public Review[] getEpisodeReviews(int episodeID) throws SQLException, IOException;
   
   /**
    * Removes a comment
    * @param itemID The ID of them item on which the comment is
    * @param commentID The ID of the comment
    * @throws SQLException
    */
   public void removeComment(int itemID, int commentID) throws SQLException, IOException;
   
   /**
    * Updates the information of a user by ID.
    * @param userID The ID of the user to update
    * @param name A string containing the name
    * @param email A string containing the email
    * @param isAdmin A boolean indicating whether the user is admin or not
    * @throws SQLException
    */
   public void updateUser(int userID, String name, String email, boolean isAdmin) throws SQLException, IOException;
   
   /**
    * Removes a user by ID
    * @param userID The ID of the user
    * @throws SQLException
    */
   public void removeUser(int userID) throws SQLException, IOException;
   
   /**
    * Updates an item.<br /> <i><b>The Item-object must have an ID that exists in the database</b></i>
    * @param item The updated item object
    * @throws SQLException
    */
   public void updateItem(Item item) throws SQLException, IOException;
   
   /**
    * Gets friends for a user with the specified ID
    * @param userID The ID of the user
    * @return A list of user IDs of the friends
    * @throws SQLException
    */
   public int[] getFriendIDsByUserID(int userID) throws SQLException, IOException;
   
   /**
    * Adds a friend to a user with specified ID
    * @param userID The user that adds the friend
    * @param friendID The userID of the friend to add
    * @throws SQLException
    */
   public void addFriendForUserID(int userID, int friendID) throws SQLException, IOException;
   
   /**
    * Loads all users to the model
    * @throws IOException
    */
   public void loadUsers() throws IOException;
   
   /**
    * Loads all items to the model
    * @throws IOException
    */
   public void loadItems() throws IOException;
   
   public int getPort();
}
