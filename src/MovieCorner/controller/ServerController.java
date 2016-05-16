package MovieCorner.controller;

import java.io.IOException;
import java.sql.SQLException;
import utility.collection.ArrayList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import MovieCorner.mediator.*;
import MovieCorner.view.*;
import MovieCorner.model.*;

public class ServerController implements Controller {
   private MovieCornerModel model;
   private MovieCornerView view;
   private User user;

   private static final int MAX_SUGGESTIONS = 5;
   private static final int TAG_SUGGESTION_LIMIT = 1;

   public ServerController(MovieCornerModel model, MovieCornerView view) {
      this.model = model;
      this.view = view;
      user = null;
   }

   public void execute(String what) {
      switch (what) {
         case "port":
            port();
            break;
         case "Login":
            login();
            break;
         case "All Movies":
            allMovies();
            break;
         case "All TV shows":
            allTvShows();
            break;
         case "Get users":
            getUser();
            break;
         case "Search for tvshow":
            search("tvshow");
            break;
         case "Search for movie":
            search("movie");
            break;
         case "Search for user":
            search("user");
            break;
         case "Register":
            register();
            break;
         case "Find Episode":
            findEpisode();
            break;
         case "getEmail":
            getEmail();
            break;
         case "allMoviesAndTVShows":
            allMoviesAndTVShows();
            break;
         case "allNotifications":
            notifications();
            break;
         case "Add comment":
            addComment();
            break;
         case "Get comments":
            getComments();
            break;
         case "isUserAdmin?":
            checkAdmin();
            break;
         case "MarkWatched":
            addWatched();
            break;
         case "GetWatchedEpisodes":
            getWatchedEpisodes();
            break;
         case "GetWatchedMovies":
            getWatchedMovies();
            break;
         case "AddFavourite":
            addFavorite();
            break;
         case "GetFavourites":
            getFavorites();
            break;
         case "AddEpisode":
            addEpisode();
            break;
         case "AddItem":
            addItem();
            break;
         case "getTVShowList":
            getTVShowList();
            break;
         case "Suggestions":
            loadSuggestions();
            break;
         case "getFavoritesList":
            getFavoritesList();
            break;
         case "getWatchedList":
            getWatchedList();
            break;
         case "addReview":
            addReview();
            break;
         case "programSetup":
            loadUsersAndItems();
            break;
         case "Search":
            search("all");
            break;
         case "getEpisodeReviews":
            getEpisodeReviews();
            break;
         case "getMovieReviews":
            getMovieReviews();
            break;
         case "removeUser":
            removeUser();
            break;
         case "saveUser":
            saveUser();
            break;
         case "manageItem":
            // manageItem();
            break;
         case "getWatchedByUser":
            getWatchedByUser();
            break;
         case "removeComment":
            removeComment();
            break;
         case "getAllItems":
            getAllItems();
            break;
         case "SaveChanges":
            saveChanges();
            break;
         case "getFriends":
            getFriends();
            break;
         case "addFriend":
            addFriend();
            break;
         case "checkFriend":
            checkFriend();
            break;
         default:
            break;
      }
   }
   
   private void port() {
      int port = model.getPort();
      view.show("");
      view.show("[MovieCorner] Server listening on port " + port);
   }

   /**
    * Checks if a user is friends with that is retrieved from the view
    */
   private void checkFriend() {
      int friendID = Integer.parseInt(view.get("userIDforWatched"));
      int userID = user.getID();
      
      int[] friendIDs = null;
      
      try {
         friendIDs = model.getFriendIDsByUserID(userID);
      }
      catch (SQLException | IOException e) {
         e.printStackTrace();
      }
      
      for (int id:friendIDs)
         if (id == friendID) {
            view.show("isFriend");
            break;
         }
      
   }
   
   /**
    * Adds a friend by the user ID of the friend
    */
   private void addFriend() {
      int friendID = Integer.parseInt(view.get("userIDforWatched"));
      int userID = user.getID();
      
      try {
         model.addFriendForUserID(userID, friendID);
         view.show("User added as a friend!");
      }
      catch (SQLException | IOException e) {
        view.show("User coudn't be added as a friend!");
      }
   }
   
   /**
    * Gets all friends for the user that is logged in
    */
   private void getFriends() {
      int userID = user.getID();
      int[] friendIDs = null;
      
      try {
         friendIDs = model.getFriendIDsByUserID(userID);
      }
      catch (SQLException | IOException e) {
         e.printStackTrace();
      }
      
      User[] friends = new User[friendIDs.length];
      
      for (int i = 0; i < friendIDs.length; i++)
         friends[i] = model.getUserById(friendIDs[i]);
      
      view.show(prepareList(friends, "getFriends"));
   }
   
   /**
    * Saves changes to an item that is retrieved from the view
    */
   private void saveChanges() {
      Item item = view.getCurrentItem();
      
      try {
      model.updateItem(item);
   } catch (SQLException e) {
      view.show("Could not save changes. Please try again.");
   } catch (IOException e) {
      view.show("Could not save changes. Please try again.");
   }
      
      view.show("Your changes has been saved");
   }
   
   /**
    * Gets all items from the model and sends it to the view
    */
   private void getAllItems() {
      
      ArrayList<Item> allItems = model.getItems().get();
      
      Object[] result = new Object[allItems.size()+1];
      
      result[0] = "getAll";
      
      for(int i = 1; i <= allItems.size(); i++)
         result[i] = allItems.get(i-1);
      
      view.show(result);
   }
   
   /**
    * Saves the changes made for a user with data retrieved from the view
    */
   private void saveUser() {
      int userID = Integer.parseInt(view.get("userIDforWatched"));
      String name = view.get("nameOfUser");
      String email = view.get("emailOfUser");
      boolean isAdmin = Boolean.parseBoolean(view.get("privilegesOfUser"));
      
      try { 
         model.updateUser(userID, name, email, isAdmin);
         view.show("Changes saved!");
      } catch (Exception e){
         view.show("Changes couldn't be saved");
      }
   }
   
   /**
    * Removes a user by an ID retrieved from the view
    */
   private void removeUser() {
      int userID = Integer.parseInt(view.get("userIDforWatched"));
      
      try { 
         model.removeUser(userID);
         view.show("User removed!");
      } catch (Exception e){
         view.show("User couldn't be removed");
      }
   }
   
   /**
    * Gets watched items by a user ID retrieved from the view
    */
   private void getWatchedByUser() {
      ArrayList<Object> watched = new ArrayList<>();

      int[] watchedMovies = null;
      Episode[] watchedEpisodes = null;
      int userID = Integer.parseInt(view.get("userIDforWatched"));
      
      try {
         watchedMovies = model.getWatchedMoviesByUserID(userID);
         watchedEpisodes = model.getWatchedEpisodesByUserID(userID);
      }
      catch (SQLException | IOException e) {
         view.show("Error when loading watched history!");
      }

      Movie[] movieData = model.getAllMovies();
      TVShow[] showData = model.getAllTVShows();

      if (watchedMovies != null) for (int movieID : watchedMovies)
         for (Movie movie : movieData)
            if (movie.getId() == movieID) watched.add(movie);

      if (watchedEpisodes != null) for (Episode ep : watchedEpisodes) {
         ArrayList<Object> epData = new ArrayList<>();
         for (TVShow show : showData) {
            Episode tempEp = show.getEpisode(ep.getSeasonNo(),
                  ep.getEpisodeNo());

            if (tempEp != null && tempEp.equals(ep)) {
               epData.add(show.getTitle() + " [S" + ep.getSeasonNo() + "E"
                     + ep.getEpisodeNo() + "]");
               break;
            }
         }

         if (epData.isEmpty()) epData.add("[S" + ep.getSeasonNo() + "E"
               + ep.getEpisodeNo() + "]");

         epData.add(ep.getReleaseDate());
         watched.add(epData);
      }

      view.show(prepareList(watched.toArray(), "watchedByUserID"));
   }
   
   /**
    * Removes a comment by data retrieved from the view
    */
   private void removeComment() {
      int itemID = view.getCurrentItem().getId();
      int commentID = Integer.parseInt(view.get("commentID"));

      try {
         model.removeComment(itemID, commentID);
      }
      catch (Exception e) {
         view.show("Error when removing comment");
      }

      // view.show("Comment removed");
   }
   
   /**
    * Gets reviews for an episode that is retrieved through the view
    */
   private void getEpisodeReviews() {
      Episode currentEpisode = view.getCurrentEpisode();

      Review[] reviews = null;

      try {
         reviews = model.getEpisodeReviews(currentEpisode.getID());
      }
      catch (SQLException e) {
         e.printStackTrace();
      }
      catch (IOException e) {
         e.printStackTrace();
      }

      if (reviews == null) {
         Object[] result2 = new Object[2];
         result2[0] = "episodeRating";
         result2[1] = 0;

         view.show(result2);
         return;
      }

      double avgRating = 0;
      Object[] result = new Object[reviews.length + 1];
      result[0] = "episodeReviews";

      for (int i = 1; i <= reviews.length; i++) {
         result[i] = reviews[i - 1];
         avgRating += reviews[i - 1].getRating();
      }

      Object[] result2 = new Object[2];
      result2[0] = "episodeRating";

      result2[1] = avgRating / reviews.length;

      view.show(result);
      view.show(result2);

   }
   
   /**
    * Gets reviews for a movie that is retrieved through the view
    */
   private void getMovieReviews() {
      Item currentItem = view.getCurrentItem();

      Review[] reviews = null;

      try {
         reviews = model.getMovieReviews(currentItem.getId());
      }
      catch (SQLException e) {
         e.printStackTrace();
      }
      catch (IOException e) {
         e.printStackTrace();
      }

      if (reviews == null) {
         Object[] result2 = new Object[2];
         result2[0] = "movieRating";
         result2[1] = 0;

         view.show(result2);
         return;
      }

      double avgRating = 0;
      Object[] result = new Object[reviews.length + 1];
      result[0] = "movieReviews";

      for (int i = 1; i <= reviews.length; i++) {
         result[i] = reviews[i - 1];
         avgRating += reviews[i - 1].getRating();
      }

      Object[] result2 = new Object[2];
      result2[0] = "movieRating";

      result2[1] = avgRating / reviews.length;

      view.show(result);
      view.show(result2);
   }
   
   /**
    * Loads all users and items to the model
    */
   private void loadUsersAndItems() {
      try {
         model.loadItems();
         model.loadUsers();
      }
      catch (IOException e) {
         view.show("Could not establish connection to server.");
      }
   }
   
   /**
    * Gets a list of favorites for the user that is logged in
    */
   private void getFavoritesList() {

      ArrayList<Object> favorites = new ArrayList<>();

      int[] favoriteMovies = null;
      int[] favoriteShows = null;

      try {
         favoriteMovies = model.getFavoriteMoviesByUserID(user.getID());
         favoriteShows = model.getFavoriteTvShowsByUserID(user.getID());
      }
      catch (SQLException | IOException e) {
         view.show("Error when loading favorites!");
      }

      Movie[] movieData = model.getAllMovies();
      TVShow[] showData = model.getAllTVShows();

      if (favoriteMovies != null) for (int movieID : favoriteMovies)
         for (Movie movie : movieData)
            if (movie.getId() == movieID) favorites.add(movie);

      if (favoriteShows != null) for (int showID : favoriteShows)
         for (TVShow show : showData)
            if (show.getId() == showID) favorites.add(show);

      view.show(prepareList(favorites.toArray(), "getFavoritesList"));
   }
   
   /**
    * Gets a list of watched items for the user that is logged in
    */
   private void getWatchedList() {

      ArrayList<Object> watched = new ArrayList<>();

      int[] watchedMovies = null;
      Episode[] watchedEpisodes = null;

      try {
         watchedMovies = model.getWatchedMoviesByUserID(user.getID());
         watchedEpisodes = model.getWatchedEpisodesByUserID(user.getID());
      }
      catch (SQLException | IOException e) {
         view.show("Error when loading watched history!");
      }

      Movie[] movieData = model.getAllMovies();
      TVShow[] showData = model.getAllTVShows();

      if (watchedMovies != null) for (int movieID : watchedMovies)
         for (Movie movie : movieData)
            if (movie.getId() == movieID) watched.add(movie);

      if (watchedEpisodes != null) for (Episode ep : watchedEpisodes) {
         ArrayList<Object> epData = new ArrayList<>();
         for (TVShow show : showData) {
            Episode tempEp = show.getEpisode(ep.getSeasonNo(),
                  ep.getEpisodeNo());

            if (tempEp != null && tempEp.equals(ep)) {
               epData.add(show.getTitle() + " [S" + ep.getSeasonNo() + "E"
                     + ep.getEpisodeNo() + "]");
               break;
            }
         }

         if (epData.isEmpty()) epData.add("[S" + ep.getSeasonNo() + "E"
               + ep.getEpisodeNo() + "]");

         epData.add(ep.getReleaseDate());
         watched.add(epData);
      }

      view.show(prepareList(watched.toArray(), "getWatchedList"));
   }
   
   /**
    * Gets favorites  for the user that is logged in
    */
   private void getFavorites() {
      int[] tempIdsMovie = null;
      int[] tempIdsTVShow = null;
      try {
         tempIdsMovie = model.getFavoriteMoviesByUserID(user.getID());
         tempIdsTVShow = model.getFavoriteTvShowsByUserID(user.getID());
      }
      catch (SQLException e) {
         e.printStackTrace();
      }
      catch (IOException e) {
         e.printStackTrace();
      }

      Object[] result;

      if (tempIdsMovie != null && tempIdsTVShow != null) result = new Object[(tempIdsMovie.length + tempIdsTVShow.length) + 1];
      else if (tempIdsMovie == null && tempIdsTVShow != null) result = new Object[tempIdsTVShow.length + 1];
      else if (tempIdsMovie != null && tempIdsTVShow == null) result = new Object[tempIdsMovie.length + 1];
      else result = new Object[1];

      result[0] = "favorites";

      int resultCounter = 1;

      if (tempIdsMovie != null) {
         for (int i = 0; i < tempIdsMovie.length; i++) {
            result[resultCounter++] = tempIdsMovie[i];
         }
      }

      if (tempIdsTVShow != null) {
         for (int i = 0; i < tempIdsTVShow.length; i++) {
            result[resultCounter++] = tempIdsTVShow[i];
         }
      }

      view.show(result);
   }
   
   /**
    * Loads suggestions based on the movies and TV shows that the user has favorited
    */
   private void loadSuggestions() 
   {
      int[] favoriteMovies = null;
      int[] favoriteShows = null;

      try {
         favoriteMovies = model.getFavoriteMoviesByUserID(user.getID());
         favoriteShows = model.getFavoriteTvShowsByUserID(user.getID());
      }
      catch (SQLException e) {
         e.printStackTrace();
      }
      catch (IOException e) {
         e.printStackTrace();
      }

      ArrayList<String> favorites = new ArrayList<String>();
      ArrayList<Item> favoritesItems = new ArrayList<Item>();
      Object[] result;

      if (favoriteMovies != null) {
         for (int i = 0; i < favoriteMovies.length; i++) {
            Item item = model.getItemById(favoriteMovies[i], 0);
            favoritesItems.add(item);
            favorites.addAll(item.getTags());
         }
      }

      if (favoriteShows != null) {
         for (int i = 0; i < favoriteShows.length; i++) {
            Item item = model.getItemById(favoriteShows[i], 1);
            favoritesItems.add(item);
            favorites.addAll(item.getTags());
         }
      }

      result = new Object[MAX_SUGGESTIONS + 1];
      result[0] = "suggestions";

      ArrayList<Item> items = new ArrayList<Item>(model.getItems().get());

      for (int i = 1; i <= MAX_SUGGESTIONS; i++) {
         for (Item item : items) {
            if (favoritesItems.contains(item)) continue;

            int matches = item.matchTags(favorites);
            if (matches > TAG_SUGGESTION_LIMIT) {
               result[i] = item;
               items.remove(item);
               break;
            }
         }
      }

      view.show(result);
   }
   
   /**
    * Adds a review to either an episode or a movie depending on what is selected in the view
    */
   private void addReview() {
      Item currentItem = view.getCurrentItem();

      if (currentItem instanceof TVShow) {

         Episode episode = view.getCurrentEpisode();

         String review = view.get("review");
         double rating = Double.parseDouble(view.get("rating"));

         Review reviewObject = new Review(review, user.getID(),
               episode.getID(), rating, user.getNickname());

         try {
            model.addEpisodeReview(reviewObject);
         }
         catch (SQLException e) {
            view.show("Could not add your review. Please try again.");
         }
         catch (IOException e) {
            e.printStackTrace();
         }
      } else {
         System.out.println(currentItem.getId());
         String review = view.get("review");
         double rating = Double.parseDouble(view.get("rating"));

         Review reviewObject = new Review(review, user.getID(),
               currentItem.getId(), rating, user.getNickname());

         try {
            model.addMovieReview(reviewObject);
         }
         catch (SQLException e) {
            view.show("Could not add your review. Please try again.");
         }
         catch (IOException e) {
            e.printStackTrace();
         }

      }
      view.show("Your review has been added.");
      view.show("reviewAdded");
   }
   
   /**
    * Adds a movie or TV show as a favorite for the user that is logged in
    */
   private void addFavorite() {
      Item item = view.getCurrentItem();

      if (item instanceof TVShow) {
         try {
            model.addFavoriteTvShow(item.getId(), user.getID());
         }
         catch (SQLException e) {
            e.printStackTrace();
         }
         catch (IOException e) {
            e.printStackTrace();
         }

         view.show("You have favorited this TV show.");
      }

      if (item instanceof Movie) {
         try {
            model.addFavoriteMovie(item.getId(), user.getID());
         }
         catch (SQLException e) {
            e.printStackTrace();
         }
         catch (IOException e) {
            e.printStackTrace();
         }

         view.show("You have favorited this movie.");
      }

   }
   
   /**
    * Adds an episode with the data retrieved from the view
    */
   private void addEpisode() {

      int showId = view.getCurrentItem().getId();

      Episode tempEp = view.getCurrentEpisode();

      try {
         model.addEpisode(tempEp, showId);
         view.show("New episode has been added!");
      }
      catch (SQLException e) {
         if (e instanceof MySQLIntegrityConstraintViolationException) view
               .show("Episode has already been defined");
         else e.printStackTrace();
      }
      catch (IOException e) {
         
         e.printStackTrace();
      }

   }
   
   /**
    * Adds a new item with data retrieved from the view
    */
   private void addItem() {

      try {
         model.addItem(view.getCurrentItem());
         view.show("Item has been added");
      }
      catch (Exception e) {
         view.show("Error");
      }

   }
   
   /**
    * Gets a list of TV shows
    */
   private void getTVShowList() {
      if (user == null) {
         view.show("Login first!");

      } else {
         TVShow[] shows = model.getAllTVShows();
         view.show(prepareList(shows, "getTVShowList"));
      }
   }
   
   /**
    * Gets a list of watched episodes for the user that is logged in
    */
   private void getWatchedEpisodes() {
      Episode[] watchedEpisodes = null;

      try {
         watchedEpisodes = model.getWatchedEpisodesByUserID(user.getID());
      }
      catch (SQLException e) {
         
         e.printStackTrace();
      }
      catch (IOException e) {
         
         e.printStackTrace();
      }

      if (watchedEpisodes == null) return;

      Object[] result = new Object[watchedEpisodes.length + 1];
      result[0] = "watchedEpisodes";

      for (int i = 1; i <= watchedEpisodes.length; i++)
         result[i] = watchedEpisodes[i - 1];

      view.show(result);
   }
   
   /**
    * Gets a list of watched movies for the user that is logged in
    */
   private void getWatchedMovies() {
      int[] watchedMovies = null;

      try {
         watchedMovies = model.getWatchedMoviesByUserID(user.getID());
      }
      catch (SQLException e) {
         
         e.printStackTrace();
      }
      catch (IOException e) {
         
         e.printStackTrace();
      }

      if (watchedMovies == null) return;

      Object[] result = new Object[watchedMovies.length + 1];
      result[0] = "watchedMovies";

      for (int i = 1; i <= watchedMovies.length; i++)
         result[i] = watchedMovies[i - 1];

      view.show(result);
   }
   
   /**
    * Adds an item as watched for the user that is logged in.<br />
    * Adds an episode as watched if the currently selected item in the view is an instance of TVShow
    */
   private void addWatched() {
      Item tempItems3 = view.getCurrentItem();

      if (tempItems3 instanceof TVShow) {
         Episode[] getWatchedEpisodes = null;
         try {
            getWatchedEpisodes = model.getWatchedEpisodesByUserID(user.getID());
         }
         catch (SQLException e1) {
            
            e1.printStackTrace();
         }
         catch (IOException e1) {
            
            e1.printStackTrace();
         }

         Episode episode = view.getCurrentEpisode();

         if (getWatchedEpisodes != null) for (int i = 0; i < getWatchedEpisodes.length; i++)
            if (getWatchedEpisodes[i].getID() == episode.getID()) return;

         try {
            model.addWatchedEpisode(episode.getID(), user.getID());
         }
         catch (SQLException e) {

            e.printStackTrace();
         }
         catch (IOException e) {

            e.printStackTrace();
         }
         return;
      } else {
         int[] watchedMovies = null;
         try {
            watchedMovies = model.getWatchedMoviesByUserID(user.getID());
         }
         catch (SQLException e1) {
            
            e1.printStackTrace();
         }
         catch (IOException e1) {
            
            e1.printStackTrace();
         }

         if (watchedMovies != null) for (int i = 0; i < watchedMovies.length; i++)
            if (watchedMovies[i] == tempItems3.getId()) return;

         try {
            model.addWatchedMovie(tempItems3.getId(), user.getID());
         }
         catch (SQLException e) {

            e.printStackTrace();
         }
         catch (IOException e) {

            e.printStackTrace();
         }
         return;
      }
   }
   
   /**
    * Checks if the user is an administrator
    */
   private void checkAdmin() {
      if (user.isAdmin()) {
         view.show("userIsAdmin");
      } else {
         // Do nothing
      }
   }
   
   /**
    * Adds a comment to an item
    */
   private void addComment() {
      Item currentItem = view.getCurrentItem();

      boolean isMovie = false;

      if (currentItem instanceof Movie) isMovie = true;

      String text = view.get("Enter your comment");

      if (text == null) return;

      Comment tempCom = new Comment(user.getID(), text, user.getNickname(),
            new MyDate());

      try {
         model.addComment(tempCom, currentItem.getId(), isMovie);
      }
      catch (SQLException | IOException e) {
         e.printStackTrace();
      }
   }
   
   /**
    * Gets the comments for the currently selected item in the view
    */
   private void getComments() {

      ArrayList<Comment> tempComments = null;
      try {
         tempComments = model.getCommentsByItem(view.getCurrentItem());
      }
      catch (SQLException e) {
         
         e.printStackTrace();
      }
      catch (IOException e) {
         
         e.printStackTrace();
      }

      if (tempComments == null) {
         Object[] result = new Object[2];
         result[0] = "allCommentsForItem";
         result[1] = null;

         view.show(result);
         return;
      }

      Object[] result = new Object[tempComments.size() + 1];
      result[0] = "allCommentsForItem";

      for (int i = 1; i <= tempComments.size(); i++) {
         result[i] = tempComments.get(i - 1);
      }

      view.show(result);

   }
   
   /**
    * Gets the notifications for the user that is currently logged in
    */
   private void notifications() {
      if (user == null) {
         view.show("Login first!");
      } else {
         ArrayList<String[]> messages = new ArrayList<String[]>();
         int[] tempMovie = null;
         int[] favouriteMovieIds = null;
         try {
            tempMovie = model.getWatchedMoviesByUserID(user.getID());
            favouriteMovieIds = model.getFavoriteMoviesByUserID(user.getID());

         }
         catch (SQLException e) {

            e.printStackTrace();
         }
         catch (IOException e) {
            e.printStackTrace();
         }
         if (favouriteMovieIds != null) {
            ArrayList<Integer> notWatchedids = new ArrayList<Integer>();
            if (tempMovie != null) {
               for (int i = 0; i < favouriteMovieIds.length; i++) {
                  boolean found = false;
                  for (int k = 0; k < tempMovie.length; k++) {
                     if (tempMovie[k] == favouriteMovieIds[i]) found = true;
                  }
                  if (!found) notWatchedids.add(favouriteMovieIds[i]);
               }
            }
            if (notWatchedids.size() != 0) {

               for (int i = 0; i < notWatchedids.size(); i++) {
                  String[] tempInfo = new String[4];
                  Item tempItem = model.getItemById(notWatchedids.get(i), 0);

                  if (tempItem != null) {
                     tempInfo[0] = tempItem.getTitle();
                     tempInfo[1] = "has been released";
                     tempInfo[2] = tempItem.getReleaseDate().toString();
                     tempInfo[3] = "Movie";
                  }
               }
            }

            int[] favouriteShows = null;
            Episode[] watchedEpisodes = null;

            try {
               favouriteShows = model.getFavoriteTvShowsByUserID(user.getID());
               watchedEpisodes = model.getWatchedEpisodesByUserID(user.getID());
            }
            catch (SQLException e) {
               
               e.printStackTrace();
            }
            catch (IOException e) {
               
               e.printStackTrace();
            }

            if (favouriteShows != null) {
               ArrayList<TVShow> shows = new ArrayList<TVShow>();

               for (int i = 0; i < favouriteShows.length; i++) {
                  shows.add((TVShow) model.getItemById(favouriteShows[i], 1));
               }

               if (shows.size() != 0) {

                  for (int i = 0; i < shows.size(); i++) {
                     for (int k = 1; k <= shows.get(i).getAmountOfSeasons(); k++) {
                        ArrayList<Episode> temp = shows.get(i)
                              .getEpisodesBySeason(k);

                        for (int s = 0; s < temp.size(); s++) {
                           if (temp.get(s).getReleaseDate().isPast()) {
                              boolean found = false;

                              if (watchedEpisodes != null) {
                                 for (int b = 0; b < watchedEpisodes.length; b++) {
                                    if (watchedEpisodes[b].equals(temp.get(s))) {
                                       found = true;
                                    }
                                 }
                              }
                              if (!found) {
                                 String[] tempInfo = new String[4];
                                 tempInfo[0] = temp.get(s).getTitle() + " [S"
                                       + temp.get(s).getSeasonNo() + " E"
                                       + temp.get(s).getEpisodeNo() + "]";
                                 tempInfo[1] = "has been released";
                                 tempInfo[2] = temp.get(s).getReleaseDate()
                                       .toString();
                                 tempInfo[3] = shows.get(i).getTitle();
                                 messages.add(tempInfo);
                              }

                           }

                        }
                     }
                  }
               }
            }
         }
         Object[] result = new Object[messages.size() + 1];

         result[0] = "allNotifications";

         for (int i = 1; i <= messages.size(); i++) {
            Object[] obj = messages.get(i - 1);

            result[i] = obj;
         }

         view.show(result);
      }
   }
   
   /**
    * Gets the email address of the user that is currently logged in
    */
   private void getEmail() {
      Object[] msg = new Object[2];
      msg[0] = "email";
      msg[1] = user.getEmail();

      view.show(msg);
   }
   
   /**
    * Searches for users, movies or tv shows
    * @param type The type of the search (eg. to search for users the type is 'user')
    */
   private void search(String type) {
      if (user == null) {
         view.show("Login first!");
      } else {
         String title = view.get("Search for");
         Item[] itemArray = model.search(title);

         ArrayList<Object> result = new ArrayList<>();

         if (!type.equals("user")) {
            if (itemArray != null) for (Item item : itemArray)
               if (type.equals("movie") && item instanceof Movie) result
                     .add(item);
               else if (type.equals("tvshow") && item instanceof TVShow) result
                     .add(item);
               else if (type.equals("all")) result.add(item);
         } else {
            User[] userArray = model.searchUsers(title);

            for (User user : userArray) {
               if (!this.user.equals(user))
                  result.add(user);
            }
         }

         view.show(prepareList(result.toArray(), "search"));
      }
   }
   
   /**
    * Registers a user with data retrieved from the view
    */
   private void register() {
      String nickname = view.get("Your nickname");
      String emailAddress = view.get("Your email");
      String passwordRegister = view.get("Your password");

      User temp = new User(nickname, emailAddress, 0, false);
      boolean error = false;
      try {

         model.addUser(temp, passwordRegister);
      }
      catch (MySQLIntegrityConstraintViolationException e) {
         view.show("A user with that e-mail already exists");
         error = true;
      }
      catch (IOException e) {
         view.show("Error handling registration. Please try again.");
         error = true;
      }
      catch (SQLException e) {
         view.show("Error handling registration. Please try again.");
         error = true;
      }
      finally {
         if (!error) view
               .show("Thank you for registering an account. You can now login.");
      }
   }
   
   /**
    * Finds an episodes for a TV Show that is retrieved from the view
    */
   private void findEpisode() {
      if (user == null) {
         view.show("Login first!");
      } else {
         String tvShow = view.get("Enter name of TVShow");

         TVShow[] tempList = model.searchShows(tvShow);
         if (tempList == null) {
            view.show("Show was not found");

         } else {
            /*
             * ArrayList<TVShow> tempShows= new ArrayList<TVShow>(); // can be
             * used/ finished when search is less specific? for(int i=0;
             * i<tempList.length; i++) { if(tempList[i] instanceof TVShow)
             * tempShows.add((TVShow)tempList[i]); } for(int i=0;
             * i<tempShows.length; i++) { view.show((i+1) + ". " +
             * tempShows.get(i).getTitle(); }
             */

            view.show(tempList[0].getTitle());

            TVShow tempShow = tempList[0];

            int seasons = tempShow.getAmountOfSeasons();

            for (int i = 1; i <= seasons; i++) {
               view.show("Season " + i);
               ArrayList<Episode> episodes = tempShow.getEpisodesBySeason(i);
               view.show("" + episodes);
            }
         }
      }
   }
   
   /**
    * Gets all users
    */
   private void getUser() {
      if (user == null) {
         System.out.println("Login first!");
      } else {
         UserList userList = model.getUsers();

         for (int i = 0; i < userList.size(); i++) {
            if (userList.getUser(i) != null) {
               view.show(userList.getUser(i).getNickname());
            }
         }
      }
   }
   
   /**
    * Gets all TV shows
    */
   private void allTvShows() {
      if (user == null) {
         view.show("Login first!");

      } else {
         TVShow[] shows = model.getAllTVShows();
         if (shows == null) {
            view.show("No shows found");

         } else {
            Object[] result = new Object[shows.length + 1];
            result[0] = "allTVShows";

            for (int i = 1; i <= shows.length; i++)
               result[i] = shows[i - 1];

            view.show(result);
         }
      }
   }
   
   /**
    * Gets all movies
    */
   private void allMovies() {
      if (user == null) {
         view.show("Login first!");
      } else {
         Movie[] movies = model.getAllMovies();

         if (movies == null) {
            view.show("No movies found");
         } else {
            Object[] result = new Object[movies.length + 1];
            result[0] = "allMovies";

            for (int i = 1; i <= movies.length; i++)
               result[i] = movies[i - 1];

            view.show(result);
         }
      }
   }
   
   /**
    * Gets all movies and TV shows
    */
   private void allMoviesAndTVShows() {
      if (user == null) {
         view.show("Login first!");
      } else {
         Movie[] movies = model.getAllMovies();
         TVShow[] shows = model.getAllTVShows();

         if (movies == null) {
            view.show("No movies found");
         } else {
            Object[] result = new Object[movies.length + shows.length + 1];
            result[0] = "allMoviesAndTVShows";

            for (int i = 0; i < movies.length; i++) {
               result[i + 1] = movies[i];
            }

            for (int i = 0; i < shows.length; i++) {
               result[i + 1 + movies.length] = shows[i];
            }
            view.show(result);
         }
      }
   }
   
   /**
    * Logs in with data retrieved from the view
    */
   private void login() {

      String email = view.get("Enter email");
      String password = view.get("Enter password");
      try {
         User temp = model.checkLogin(email, password);

         user = temp;
         view.show("Login successful!");
      }
      catch (SQLException e) {
         view.show("Wrong username and/or password.");
      }
      catch (IOException e) {
         view.show("Could not connect to server. Please try again later");
      }
   }
   
   /**
    * Prepares a list to be shown in the view
    * @param list The list of data
    * @param word The identifier for the action (used in the view)
    * @return Returns an Object array with a list that is ready to be shown in the view
    */
   private Object[] prepareList(Object[] list, String word) {
      Object[] result = new Object[list.length + 1];
      result[0] = word;

      for (int i = 1; i <= list.length; i++)
         result[i] = list[i - 1];

      return result;
   }
}
