package MovieCorner.mediator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.text.ParseException;

import utility.collection.ArrayList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import MovieCorner.model.Client;
import MovieCorner.model.Comment;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;
import MovieCorner.model.ItemList;
import MovieCorner.model.Movie;
import MovieCorner.model.Review;
import MovieCorner.model.TVShow;
import MovieCorner.model.User;
import MovieCorner.model.UserList;

public class ServerModelManager implements MovieCornerModel {
	private MCPersistence adapter;
	private ServerConnectionThread server;
	private int port;

   public ServerModelManager(int port) throws SQLException, NumberFormatException, ParseException {
      adapter = new MCAdapter();
      this.port = port;
      server = new ServerConnectionThread(this, port);
      server.start();
      
      server.addHandler(100, new GetItemsHandler());
      server.addHandler(200, new LoginHandler());
      server.addHandler(300, new GetUsersHandler());
      server.addHandler(400, new SaveUserHandler());
      server.addHandler(500, new SaveItemHandler());
      server.addHandler(600, new GetCommentsHandler());
      server.addHandler(700, new AddCommentHandler());
      server.addHandler(800, new AddWatchedEpisodeHandler());
      server.addHandler(900, new AddWatchedMovieHandler());
      server.addHandler(1000, new GetWatchedEpisodesHandler());
      server.addHandler(1100, new GetWatchedMoviesHandler());
      server.addHandler(1200, new AddFavoriteTvShowHandler());
      server.addHandler(1300, new AddFavoriteMovieHandler());
      server.addHandler(1500, new GetFavoriteTvShowsHandler());
      server.addHandler(1400, new GetFavoriteMoviesHandler());
      server.addHandler(1600, new AddEpisodeHandler());
      server.addHandler(1700, new AddEpisodeReviewHandler());
      server.addHandler(1800, new AddMovieReviewHandler());
      server.addHandler(1900, new GetEpisodeReviewsHandler());
      server.addHandler(2000, new GetMovieReviewsHandler());
      server.addHandler(2100, new RemoveComment());
      server.addHandler(2200, new RemoveUser());
      server.addHandler(2300, new UpdateUser());
      server.addHandler(2400, new ManageItemHandler());
      server.addHandler(2500, new GetUserFriendsHandler());
      server.addHandler(2600, new AddFriendHandler());
   }

   public ItemList getItems() {
      try {
		return adapter.loadItems();
	} catch (NumberFormatException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
      return null;
   }

   public UserList getUsers() {
      try {
		return adapter.loadUsers();
	} catch (SQLException e) {
		e.printStackTrace();
	}
      return null;
   }

   public User getUserById(int id) {
      try {
		UserList users = adapter.loadUsers();
		
		return users.getUser(id);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      return null;
      
   }

   public Item getItemById(int id, int type) {
	   ItemList items = null;
	try {
		items = adapter.loadItems();
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   if(items != null)
		   return items.getItemById(id, type);
	   else
		   return null;
   }
   
   public void addEpisode(Episode episode, int tvShowID) throws SQLException, IOException
   {
	   adapter.addEpisode(episode, tvShowID);
   }

   public void addUser(User user, String password) throws SQLException {
     adapter.saveUser(user, password);
    
   }

   public void addItem(Item item) throws SQLException {
      adapter.saveItem(item);
   }

   public User checkLogin(String email, String password) throws SQLException {
      return adapter.login(email, password);
   }

   public Item[] search(String title) 
   {
	   ItemList items;
	try {
		items = adapter.loadItems();
		
	      ArrayList<Item> temp = new ArrayList<Item>();
	      
	      
	      for (int i = 0; i < items.size(); i++) {
	         if (items.get().get(i).getTitle().equalsIgnoreCase(title)) temp.add(items.get().get(i));
	      }
	
	      if(temp.size()>0)
	    	  return temp.toArray(new Item[temp.size()]);
	      
	      
	} catch (NumberFormatException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
	
	return null;

   }
   
   public TVShow[] getAllTVShows()
   {
	   ItemList temp= null;
	   try {
		temp= adapter.loadItems();
	} catch (NumberFormatException e) {
		e.printStackTrace();
	} catch (SQLException e) 
	   {
		e.printStackTrace();
	} catch (ParseException e) 
	   {
		e.printStackTrace();
	}
	   if(temp==null)
		   return null;
	   
	   ArrayList<TVShow> tempShows= new ArrayList<TVShow>();
	   
	   for(int i=0; i<temp.size(); i++)
	   {
		   if(temp.get().get(i) instanceof TVShow)
			   tempShows.add((TVShow)temp.get().get(i));
	   }
	   
	   return tempShows.toArray(new TVShow[tempShows.size()]);
   }
   
   public Movie[] getAllMovies()
   {
		   ItemList temp= null;
		   try {
			temp= adapter.loadItems();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) 
		   {
			e.printStackTrace();
		} catch (ParseException e) 
		   {
			e.printStackTrace();
		}
		   if(temp==null)
			   return null;
		   
		   ArrayList<Movie> tempMovies= new ArrayList<Movie>();
		   
		   for(int i=0; i<temp.size(); i++)
		   {
			   if(temp.get().get(i) instanceof Movie)
				   tempMovies.add((Movie)temp.get().get(i));
		   }
		   
		   return tempMovies.toArray(new Movie[tempMovies.size()]);
   }
   
   public TVShow[] searchShows(String title)
   {
	   ItemList temp= null;
	   try {
		temp= adapter.loadItems();
	} catch (NumberFormatException e) {
		e.printStackTrace();
	} catch (SQLException e) 
	   {
		e.printStackTrace();
	} catch (ParseException e) 
	   {
		e.printStackTrace();
	}
	   if(temp==null)
		   return null;
	   
   ArrayList<TVShow> tempShows= new ArrayList<TVShow>();
	   
	   for(int i=0; i<temp.size(); i++)
	   {
		   if(temp.get().get(i) instanceof TVShow)
		   {
			   if(temp.get().get(i).getTitle().equals(title))
			   tempShows.add((TVShow)temp.get().get(i));
		   }
	   }
	   
	   return tempShows.toArray(new TVShow[tempShows.size()]);
   }

	@Override
	public ArrayList<Comment> getCommentsByItem(Item item) throws SQLException,
			IOException {
		
		ArrayList<Comment> temp = null;
		
		temp = adapter.getAllComments(item);
		
		return temp;
	}
	
	@Override
	public void addComment(Comment comment, int itemId, boolean isMovie)
			throws SQLException, IOException {
		adapter.addComment(comment, itemId, isMovie);
		
		
	}

	@Override
	public void addWatchedEpisode(int episodeID, int userID)
			throws SQLException, IOException {
		adapter.addWatchedEpisode(episodeID, userID);
		
	}

	@Override
	public void addWatchedMovie(int itemID, int userID) throws SQLException,
			IOException {
		adapter.addWatchedMovie(itemID, userID);
		
	}

	@Override
	public Episode[] getWatchedEpisodesByUserID(int userID) throws SQLException,
			IOException {
		return adapter.getWatchedEpisodesByUserID(userID);
	}

	@Override
	public int[] getWatchedMoviesByUserID(int userID) throws SQLException,
			IOException {
		return adapter.getWatchedMoviesByUserID(userID);
	}

	@Override
	public int[] getFavoriteTvShowsByUserID(int userID) throws SQLException,
			IOException {
		
		return adapter.getFavoriteTvShowsByUserID(userID);
	}

	@Override
	public int[] getFavoriteMoviesByUserID(int userID) throws SQLException,
			IOException {
		return adapter.getFavoriteMoviesByUserID(userID);
	}

	@Override
	public void addFavoriteMovie(int movieID, int userID) throws SQLException,
			IOException {
		adapter.addFavoriteMovie(movieID, userID);
		
	}
	
   @Override
   public void removeComment(int itemID, int commentID) throws SQLException,
         IOException {
      adapter.removeComment(itemID, commentID);
      
   }

	@Override
	public void addFavoriteTvShow(int tvshowsID, int userID)
			throws SQLException, IOException {
		adapter.addFavoriteTvShow(tvshowsID, userID);
		
	}
	
	public void addEpisodeReview(Review review) throws SQLException
	{
		adapter.addEpisodeReview(review);
	}
	
	public void addMovieReview(Review review) throws SQLException
	{
		adapter.addMovieReview(review);
	}

	@Override
	public void loadItems() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadUsers() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Review[] getEpisodeReviews(int episodeID) throws SQLException,
			IOException {
		return adapter.getEpisodeReviews(episodeID);
	}

	@Override
	public Review[] getMovieReviews(int movieID) throws SQLException,
			IOException {
		return adapter.getMovieReviews(movieID);
	}

	@Override
	public User[] searchUsers(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	 
   @Override
   public void removeUser(int userID) throws SQLException, IOException {
      adapter.removeUser(userID);
   }
   
   @Override
   public void updateUser(int userID, String name, String email, boolean isAdmin) throws SQLException, IOException {
      adapter.updateUser(userID, name, email, isAdmin);
   }
   
   @Override
   public void updateItem(Item item) throws SQLException, IOException {
      adapter.updateItem(item);
   }
   
   @Override
   public int[] getFriendIDsByUserID(int userID) throws SQLException,
         IOException {
      return adapter.getFriendsByUserID(userID);
   }

   @Override
   public void addFriendForUserID(int userID, int friendID) throws SQLException, IOException {
      adapter.addFriendForUserID(userID, friendID);
   }

   @Override
   public int getPort() {
      return port;
   }
}


/* Packet handlers */

class GetItemsHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model)
	{	
		ItemList items = model.getItems();
		
		byte[] packetData = PacketUtilities.objectToBytes(items);
		
		ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 100);
		
		client.enqueuePacket(packetToSend);
	}
}

class GetUsersHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model)
	{	
		UserList users = model.getUsers();
		
		byte[] packetData = PacketUtilities.objectToBytes(users);
		
		ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 300);
		
		client.enqueuePacket(packetToSend);
	}
}

class LoginHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{	
		
		String[] login = (String[]) PacketUtilities.packetToObject(packet);
		
		User checkLogin = new User("", "", -1, false);;
		
		try {
			checkLogin = model.checkLogin(login[0], login[1]);
		} catch (SQLException e) {
			checkLogin = new User("", "", -1, false);
		} catch (IOException e) {
			checkLogin = new User("", "", -1, false);
		}
		
		byte[] packetData = PacketUtilities.objectToBytes(checkLogin);
		
		ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 200);
		
		client.enqueuePacket(packetToSend);
	}
}

class SaveUserHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{	
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		packetToSend.putInt(400);
		
		Object[] userInfo = (Object[]) PacketUtilities.packetToObject(packet);
		
		try {
			model.addUser((User) userInfo[0], (String) userInfo[1]);
		} catch (MySQLIntegrityConstraintViolationException e)
		{
			packetToSend.putInt(1); // Code for MySQL IntegrityConstraintViolation
		} catch (SQLException e) {
			packetToSend.putInt(2); // Code for MySQL Exception
			e.printStackTrace();
		}
		
		
		client.enqueuePacket(packetToSend);
	}
}

class SaveItemHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{	
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		packetToSend.putInt(500);
		
		Item item = (Item) PacketUtilities.packetToObject(packet);
		
		try {
			model.addItem(item);
		} catch (MySQLIntegrityConstraintViolationException e)
		{
			packetToSend.putInt(1); // Code for MySQL IntegrityConstraintViolation
		} catch (SQLException e) {
			packetToSend.putInt(2); // Code for MySQL Exception
		}
		
		client.enqueuePacket(packetToSend);
	}
}

class GetCommentsHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		Item item = (Item) PacketUtilities.packetToObject(packet);
		ArrayList<Comment> comments = null;
		
		try {
			comments = model.getCommentsByItem(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		byte[] packetData = PacketUtilities.objectToBytes(comments);
		ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 600);
		
		client.enqueuePacket(packetToSend);
			
	}
}

class AddCommentHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		packetToSend.putInt(700);
		
		Object[] commentInfo = (Object[]) PacketUtilities.packetToObject(packet);
		
		int itemId = (Integer) commentInfo[1];
		boolean isMovie = (Boolean) commentInfo[2];
		
		try {
			model.addComment((Comment) commentInfo[0], itemId, isMovie);
		} catch (SQLException e) {
			packetToSend.putInt(2); // Code for SQLException
			e.printStackTrace();
		}
		
		client.enqueuePacket(packetToSend);
	}
}

class AddWatchedEpisodeHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		packetToSend.putInt(800);
		
		Object[] watchedInfo = (Object[]) PacketUtilities.packetToObject(packet);
		
		int episodeId = (Integer) watchedInfo[0];
		int userId = (Integer) watchedInfo[1];
		
		try {
			model.addWatchedEpisode(episodeId, userId);
		} catch (SQLException e) {
			packetToSend.putInt(2);
		}
		
		client.enqueuePacket(packetToSend);
	}
}

class AddWatchedMovieHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		packetToSend.putInt(900);
		
		Object[] movieInfo = (Object[]) PacketUtilities.packetToObject(packet);
		
		int itemID = (Integer) movieInfo[0];
		int userID = (Integer) movieInfo[1];
		
		try {
			model.addWatchedMovie(itemID, userID);
		} catch (SQLException e )
		{
			packetToSend.putInt(2);
		}
		
		client.enqueuePacket(packetToSend);
	}
}

class GetWatchedEpisodesHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		int userID = (int) PacketUtilities.packetToObject(packet);
		
		
		Episode[] watched = null;
		try {
			watched = model.getWatchedEpisodesByUserID(userID);
		} catch (SQLException e )
		{
			e.printStackTrace();
		}
		
		byte[] packetData = PacketUtilities.objectToBytes(watched);
		
		ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 1000);
		
		client.enqueuePacket(packetToSend);
	}
}

class GetWatchedMoviesHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		Integer userID = (Integer) PacketUtilities.packetToObject(packet);
		
		
		int[] watched = null;
		try {
			watched = model.getWatchedMoviesByUserID(userID);
		} catch (SQLException e )
		{
			e.printStackTrace();
		}
		
		byte[] packetData = PacketUtilities.objectToBytes(watched);
		
		ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 1100);
		
		client.enqueuePacket(packetToSend);
	}
	
	public Item getWatchedMovies(int itemID)
	{
		return null;
	}
}

class AddFavoriteTvShowHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		packetToSend.putInt(1200);
		
		Object[] watchedInfo = (Object[]) PacketUtilities.packetToObject(packet);
		
		int tvShowId = (Integer) watchedInfo[0];
		int userId = (Integer) watchedInfo[1];
		
		try {
			model.addFavoriteTvShow(tvShowId, userId);
		} catch (MySQLIntegrityConstraintViolationException e)
		{
		   packetToSend.putInt(2);
		} catch (SQLException e) {
			packetToSend.putInt(1);
		}
		
		client.enqueuePacket(packetToSend);
	}
}

class AddFavoriteMovieHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		packetToSend.putInt(1300);
		
		Object[] movieInfo = (Object[]) PacketUtilities.packetToObject(packet);
		
		int itemID = (Integer) movieInfo[0];
		int userID = (Integer) movieInfo[1];
		
		try {
			model.addFavoriteMovie(itemID, userID);
		} catch (MySQLIntegrityConstraintViolationException e)
		{
		   packetToSend.putInt(2);
		} catch (SQLException e )
		{
			packetToSend.putInt(1);
		}
		
		client.enqueuePacket(packetToSend);
	}
}

class GetFavoriteTvShowsHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		Integer userID = (Integer) PacketUtilities.packetToObject(packet);
		
		
		int[] watched = null;
		try {
			watched = model.getFavoriteTvShowsByUserID(userID);
		} catch (SQLException e )
		{
			e.printStackTrace();
		}
		
		byte[] packetData = PacketUtilities.objectToBytes(watched);
		
		ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 1400);
		
		client.enqueuePacket(packetToSend);
	}
}

class AddEpisodeHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		packetToSend.putInt(1600);
		
		Object[] episodeData = (Object[]) PacketUtilities.packetToObject(packet);
		
		Episode episode = (Episode) episodeData[0];
		int showId = (Integer) episodeData[1];
		
		try {
			model.addEpisode(episode, showId);
		} catch (MySQLIntegrityConstraintViolationException e)
		{
			packetToSend.putInt(2); // Code for MySQL IntegrityConstraintViolation
		} catch (SQLException e) {
			packetToSend.putInt(1); // Code for MySQL Exception
		}
		
		client.enqueuePacket(packetToSend);
	}
}
class AddEpisodeReviewHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel  model) throws IOException
	{
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		packetToSend.putInt(1700);
		
		Object[] reviewData = (Object[]) PacketUtilities.packetToObject(packet);
		
		Review review= (Review) reviewData[0];
		
		try {
			model.addEpisodeReview(review);
		} catch (MySQLIntegrityConstraintViolationException e)
		{
			packetToSend.putInt(1); // Code for MySQL IntegrityConstraintViolation
		} catch (SQLException e) {
			packetToSend.putInt(2); // Code for MySQL Exception
		}
		
		client.enqueuePacket(packetToSend);
	}
}

class AddMovieReviewHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel  model) throws IOException
	{
		ByteBuffer packetToSend = ByteBuffer.allocate(8);
		packetToSend.putInt(1800);
		
		Review review = (Review) PacketUtilities.packetToObject(packet);
		
		try {
			model.addMovieReview(review);
		} catch (MySQLIntegrityConstraintViolationException e)
		{
			packetToSend.putInt(1); // Code for MySQL IntegrityConstraintViolation
		} catch (SQLException e) {
			packetToSend.putInt(2); // Code for MySQL Exception
		}
		
		client.enqueuePacket(packetToSend);
	}
}

class GetFavoriteMoviesHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		Integer userID = (Integer) PacketUtilities.packetToObject(packet);
		
		
		int[] watched = null;
		try {
			watched = model.getFavoriteMoviesByUserID(userID);
		} catch (SQLException e )
		{
			e.printStackTrace();
		}
		
		byte[] packetData = PacketUtilities.objectToBytes(watched);
		
		ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 1500);
		
		client.enqueuePacket(packetToSend);
	}
	
	public Item getWatchedMovies(int itemID)
	{
		return null;
	}
}

class GetEpisodeReviewsHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		Integer episodeID = (Integer) PacketUtilities.packetToObject(packet);
		
		Review[] reviews = null;
		
		try {
			reviews = model.getEpisodeReviews(episodeID);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		byte[] packetData = PacketUtilities.objectToBytes(reviews);
		
		ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 1900);
		
		client.enqueuePacket(packetToSend);
	}
}

class GetMovieReviewsHandler implements PacketHandler
{
	public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
	{
		Integer movieID = (Integer) PacketUtilities.packetToObject(packet);
		
		Review[] reviews = null;
		
		try {
			reviews = model.getMovieReviews(movieID);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		byte[] packetData = PacketUtilities.objectToBytes(reviews);
		
		ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 2000);
		
		client.enqueuePacket(packetToSend);
	}
}

class RemoveComment implements PacketHandler
{
   public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
   {
      ByteBuffer packetToSend = ByteBuffer.allocate(8);
      packetToSend.putInt(2100);
      
      Object[] commentInfo = (Object[]) PacketUtilities.packetToObject(packet);
      
      int itemID = (Integer) commentInfo[0];
      int commentID = (Integer) commentInfo[1];
      
      try {
         model.removeComment(itemID, commentID);
      } catch (SQLException e )
      {
         packetToSend.putInt(2);
      }
      
      client.enqueuePacket(packetToSend);
   }
}

class RemoveUser implements PacketHandler
{
   public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
   {
      ByteBuffer packetToSend = ByteBuffer.allocate(8);
      packetToSend.putInt(2200);
      
      Object[] userInfo = (Object[]) PacketUtilities.packetToObject(packet);
      
      int userID = (Integer) userInfo[0];
      
      try {
         model.removeUser(userID);
      } catch (SQLException e )
      {
         packetToSend.putInt(2);
    	  
    	  e.printStackTrace();
      }
      
      client.enqueuePacket(packetToSend);
   }
}

class UpdateUser implements PacketHandler
{
   public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
   {
      ByteBuffer packetToSend = ByteBuffer.allocate(8);
      packetToSend.putInt(2300);
      
      Object[] userInfo = (Object[]) PacketUtilities.packetToObject(packet);
      
      int userID = (Integer) userInfo[0];
      String name = userInfo[1].toString();
      String email = userInfo[2].toString();
      boolean isAdmin = Boolean.parseBoolean(userInfo[3].toString());
      
      try {
         model.updateUser(userID, name, email, isAdmin);
      } catch (SQLException e) {
         packetToSend.putInt(2);
      }
      
      client.enqueuePacket(packetToSend);
   }
}

class ManageItemHandler implements PacketHandler
{

   @Override
   public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException {
      ByteBuffer packetToSend = ByteBuffer.allocate(8);
      packetToSend.putInt(2200);
      
      Item itemInfo = (Item) PacketUtilities.packetToObject(packet);
      
      try {
         model.updateItem(itemInfo);
      } catch (SQLException e) {
//       packetToSend.putInt(1);
         e.printStackTrace();
      }
      
      client.enqueuePacket(packetToSend);
      
   }
   
}

class GetUserFriendsHandler implements PacketHandler
{
   public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
   {
      Integer userID = (Integer) PacketUtilities.packetToObject(packet);
      
      int[] friends = null;
      
      try {
         friends = model.getFriendIDsByUserID(userID);
      } catch (SQLException e)
      {
         e.printStackTrace();
      }
      
      byte[] packetData = PacketUtilities.objectToBytes(friends);
      
      ByteBuffer packetToSend = PacketUtilities.constructPacket(packetData, 2500);
      
      client.enqueuePacket(packetToSend);
   }
}

class AddFriendHandler implements PacketHandler
{
   public void handlePacket(Client client, ByteBuffer packet, MovieCornerModel model) throws IOException
   {
      ByteBuffer packetToSend = ByteBuffer.allocate(8);
      packetToSend.putInt(2600);
      
      Object[] userAndFriendIDs = (Object[]) PacketUtilities.packetToObject(packet);
      
      int userID = (Integer) userAndFriendIDs[0];
      int friendID = (Integer) userAndFriendIDs[1];
      
      try {
         model.addFriendForUserID(userID, friendID);
      } catch (SQLException e )
      {
         packetToSend.putInt(2);
      }
      
      client.enqueuePacket(packetToSend);
   }
}
