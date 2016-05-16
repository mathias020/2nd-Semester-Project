package JUnitTestModelManager;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.sql.SQLException;

import utility.collection.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import MovieCorner.mediator.ModelManager;
import MovieCorner.mediator.MovieCornerModel;
import MovieCorner.mediator.PacketHandler;
import MovieCorner.mediator.PacketUtilities;
import MovieCorner.mediator.ServerConnectionThread;
import MovieCorner.mediator.ServerModelManager;
import MovieCorner.model.Client;
import MovieCorner.model.Comment;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;
import MovieCorner.model.ItemList;
import MovieCorner.model.Movie;
import MovieCorner.model.MyDate;
import MovieCorner.model.Review;
import MovieCorner.model.TVShow;
import MovieCorner.model.User;
import MovieCorner.model.UserList;

public class ModelManagerTest {

	private static ModelManager model;
	private static boolean hasSetup;
	
	
	@Before
	public void setUp() throws Exception 
	{
		if(hasSetup)
			return;
		
		System.out.println("Setting up ModelManager");
		
		model= new ModelManager("localhost", 1337);
		model.loadItems();
		model.loadUsers();
		
		hasSetup = true;
	}
	
   @Test
   public void getFavoriteMovies()
   {
      int[] favorites = null;
      try {
         favorites = model.getFavoriteMoviesByUserID(94); // User with 1 favorite Movie
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      if(favorites == null)
         fail();
      
      assertEquals(1, favorites.length);
      
      try {
         favorites = model.getFavoriteMoviesByUserID(85); // User with 4 favorite Movies
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      if(favorites == null)
         fail();
      
      assertEquals(4, favorites.length);
      
      try {
         favorites = model.getFavoriteMoviesByUserID(93); // User with no favorite movies
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      if(favorites != null)
         fail();
   }
	
	@Test
	public void getFavoriteTVShows()
	{
      int[] favorites = null;
      try {
         favorites = model.getFavoriteTvShowsByUserID(94);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      if(favorites == null)
         fail();
      
      assertEquals(1, favorites.length);
      
      try {
         favorites = model.getFavoriteTvShowsByUserID(85); // User with 3 favorite TV shows
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      if(favorites == null)
         fail();
      
      assertEquals(3, favorites.length);
      
      try {
         favorites = model.getFavoriteTvShowsByUserID(93); // User with no favorite TV shows
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      if(favorites != null)
         fail();
	}
	
   @Test
   public void addFavoriteTVShow()
   {
      try {
         model.addFavoriteTvShow(139, 94);
      } catch (MySQLIntegrityConstraintViolationException e)
      {
         System.out.println("TV Show already favorited by user - behaves as it should");
         /* ok */
      } catch (SQLException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      } catch (IOException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
      
      int[] favorites = null;
      try {
         favorites = model.getFavoriteTvShowsByUserID(94);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      if(favorites == null)
         fail();
      
      assertEquals(1, favorites.length);
   }
	
	@Test
	public void addFavoriteMovie()
	{
	   try {
         model.addFavoriteMovie(145, 94);
	   } catch (MySQLIntegrityConstraintViolationException e)
	   {
	      System.out.println("Movie already favorited by user - behaves as it should");
	      /* ok */
      } catch (SQLException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      } catch (IOException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
	   
	   int[] favorites = null;
	   try {
         favorites = model.getFavoriteMoviesByUserID(94);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
	   
	   if(favorites == null)
	      fail();
	   
	   assertEquals(1, favorites.length);
	}
	
	@Test
	public void testAddEpisode()
	{
	   Episode episode = new Episode(1, "Test episode", 1, "A Test",
	         new MyDate(), "05:00", 0);
	   
	   
	   try {
         model.addEpisode(episode, 141);
	   } catch (MySQLIntegrityConstraintViolationException e)
	   {
	      System.out.println("Episode already exists - behaves as it should");
	      /* ok */
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	   
      TVShow item = (TVShow) model.getItemById(141, 1); // TV show with no episodes
      
      assertEquals(1, item.getEpisodesBySeason(1).size());
      
	   
	}
	
	@Test
	public void testGetEpisodeReviews()
	{
	     Review[] reviews = null;
	     
	     try {
         reviews = model.getEpisodeReviews(8);
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	     
	     assertEquals(1, reviews.length);
	     
	     reviews = null;
	     
	     try {
         reviews = model.getEpisodeReviews(1);
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	     
	     if(reviews != null)
	        fail();
	     
	     
	     
	}
	
	public void testGetMovieReviews()
	{
	   Review[] reviews = null;
	   
	   try {
         reviews = model.getMovieReviews(167);
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	   
	   if(reviews != null)
	      fail();
	   
	   
	   try {
         reviews = model.getMovieReviews(135);        // Movie with 1 review
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	   
	   assertEquals(1, reviews.length);
	   
	}
	
	@Test
	public void testAddMovieReview()
	{
	   Review review = new Review("Test review", 94, 118, 2.0, "Spammer");
	   
	   try {
         model.addMovieReview(review);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
	   
	   Review[] reviews = null;
	   
	   try {
         reviews = model.getMovieReviews(118);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
	   
	   if(reviews == null)
	      fail();
	   
	   assertEquals(2, reviews.length);
	   
	   
      review = new Review("Test review", 94, 135, 2.0, "Spammer");
      
      try {
         model.addMovieReview(review);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      
      reviews = null;
      try {
         reviews = model.getMovieReviews(135);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      if(reviews == null)
         fail();
      
      assertEquals(1, reviews.length);
      
      
      reviews = null;
      try {
         reviews = model.getMovieReviews(165); // Movie with no reviews
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      System.out.println(reviews);
      
      if(reviews != null)
         fail();
	}
	
	@Test
	public void testAddEpisodeReview()
	{
	     Review review = new Review("Test review", 94, 12, 2.0, "Spammer");
	     
	     try {
         model.addEpisodeReview(review);
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	     
	     Review[] reviews = null;
	     try {
         reviews = model.getEpisodeReviews(12); // Episode with 2 reviews
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	     
	     if(reviews==null)
	        fail();
	     
	     assertEquals(2, reviews.length);
	     
	     review = new Review("Test review", 94, 8, 2.0, "Spammer");
	
	       try {
	          model.addEpisodeReview(review);
	       } catch (SQLException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	       } catch (IOException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	       }
	       
	         try {
	          reviews = model.getEpisodeReviews(8); // Episode with 2 reviews
	       } catch (SQLException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	       } catch (IOException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	       }
	         
	         if(reviews==null)
	            fail();
	         
	         assertEquals(1, reviews.length);
	         
            try {
               reviews = model.getEpisodeReviews(1); // Episode with 2 reviews
            } catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
              
              if(reviews != null)
                 fail();
	}
	
	@Test
	public void testCheckLoginSuccess() 
	{
		User bob=null;
		User jimmy=null;
		try {
			 bob= model.checkLogin("bob@gmail.com", "blabla");
			 jimmy= model.checkLogin("jimmy@yahoo.com", "bigpassword");
		} catch (SQLException | IOException e1) 
		{
			e1.printStackTrace();
		}
		
		if(bob==null || jimmy==null)
		{
			fail();
		}
		
		assertEquals("Bob", bob.getNickname());
		assertEquals("bob@gmail.com", bob.getEmail());
		assertEquals("jimmy", jimmy.getNickname());
		assertEquals("jimmy@yahoo.com", jimmy.getEmail());
	}
	
	@Test
	public void testCheckLoginNull()
	{
		
		try
		{
			assertEquals(null, model.checkLogin("bob@gmail.com", "blablabla")); //password does not match email - should return null
			assertEquals(null, model.checkLogin("bobDoesnotExist@gmail.com" , "passwordDoesNotMatterUserDoesNotExist")); 
			//user with the email does not exist - should return null
		}
		catch(SQLException e)
		{
			/* ok */
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoveComment()
	{
		
		Comment newComment= new Comment(3, "jimmy", "wendy", MyDate.today());
		
		try {
			model.addComment(newComment, 118, true);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Item temp= model.getItemById(118, 0);
		ArrayList<Comment> comments=null;
		
		try {
			comments=model.getCommentsByItem(temp);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(comments==null)
			fail();
		
		try {
			model.removeComment(118, comments.get(0).getID());//only comment on the movie is the newly added one
			
			comments=model.getCommentsByItem(temp);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(comments!=null)
			fail();			//Comment got removed - comments should return null
			
	}
	
	@Test
	public void testgetItemByIdMovie()
	{	
			assertEquals("TERMINATOR", model.getItemById(118, 0).getTitle());
			assertEquals("Terminator 2", model.getItemById(135, 0).getTitle());
			
			Item movie= model.getItemById(118, 0);
			
			if(!(movie instanceof Movie))
				fail();
			
			movie=model.getItemById(135, 0);
			
			if(!(movie instanceof Movie))
				fail();
	}
	
	@Test
	public void testgetItemByIdTVShow()
	{
		assertEquals("The Walking dead", model.getItemById(110,1).getTitle());
		assertEquals("Terminator show", model.getItemById(122,1).getTitle());
		assertEquals("Bobs show edited", model.getItemById(135,1).getTitle());
		
		Item tvShow= model.getItemById(110,1);
		
		if(!(tvShow instanceof TVShow))
			fail();
		
		tvShow=model.getItemById(122, 1);
		
		if(!(tvShow instanceof TVShow))
			fail();
		
		tvShow=model.getItemById(135, 1);
		
		if(!(tvShow instanceof TVShow))
			fail();
	}
	
	@Test
	public void testGetComments()
	{
		ArrayList<Comment> comments=null;
		
		Item empty= model.getItemById(165, 0); //a movie without comments
		
		try {
			comments=model.getCommentsByItem(empty);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(comments);
		
		if(comments!=null)
			fail();
		
		Item oneComment= model.getItemById(122,1); //a show with one comment
		
		try {
			comments=model.getCommentsByItem(oneComment);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(comments.size()!=1)
			fail();
		
		Item twoComments= model.getItemById(143,0);	//Movie with two comments
		
		try {
			comments= model.getCommentsByItem(twoComments);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(comments.size(),2);
		
		
	}
	
	@Test
	public void testgetItemByIdNotFound()
	{
		
		assertEquals(null,model.getItemById(-1111, 0)); //ID does not exist in movies - should return null
		
		assertEquals(null,model.getItemById(118,5)); // type does not exist - should return null
		
		assertEquals(null, model.getItemById(-1111111, 1)); //ID does not exist in tvshows - should return null
	}
	
	@Test
	public void testgetUserById()
	{
		assertEquals("Bob", model.getUserById(1).getNickname());
		assertEquals("bobthebuilder", model.getUserById(2).getNickname());
		assertEquals("jimmy@yahoo.com",model.getUserById(4).getEmail());
		assertEquals(null, model.getUserById(-100)); //no user with ID=100 exists - returns null
		
	}
	@Test
	public void testgetItemsgettingTVShowsAndMovies()
	{
		ItemList temp= model.getItems();
		
		assertEquals(115, temp.getItem("Originals").getId());
		assertEquals(110, temp.getItem("The Walking dead").getId());
		assertEquals(118, temp.getItem("TERMINATOR").getId());
		assertEquals(123, temp.getItem("Titanic").getId());	
	}
	
	@Test
	public void testGetItemsGetsEpisodes()
	{
		ItemList temp= model.getItems();
		ArrayList<Item> tempArray= temp.get();
		
		TVShow tempShow= (TVShow)tempArray.get(0);
		assertEquals("The begining", tempShow.getEpisode(1,1).getTitle());
		assertEquals("No survivors", tempShow.getEpisode(1,2).getTitle());
		
		tempShow=(TVShow)tempArray.get(1);
		assertEquals("Tirst", tempShow.getEpisode(1,1).getTitle());
		assertEquals("True Blood", tempShow.getEpisode(1,2).getTitle());
	}
	
	@Test
	public void testupdateItemShowAndMovie()
	{
		Movie movie= new Movie("kkk", "ID 145 edited", MyDate.today(), new ArrayList<String>(), new ArrayList<String>(), "ol", 145, "144");
		
		Item show= new TVShow("lll","Bobs show edited", MyDate.today(), new ArrayList<String>(), new ArrayList<String>(),"lo",135,new ArrayList<Episode>(), true);
		
		try {
			model.updateItem(movie);
			model.updateItem(show);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int day1= MyDate.today().getDay();
		int month1= MyDate.today().getMonth();
		int year1= MyDate.today().getYear();
		
		Item movieUpdated= model.getItemById(145, 0);
		
		int day2= movieUpdated.getReleaseDate().getDay();
		int month2= movieUpdated.getReleaseDate().getMonth();
		int year2= movieUpdated.getReleaseDate().getYear();
		
		assertEquals(day1,day2);
		assertEquals(month1,month2);
		assertEquals(year1,year2);
		assertEquals(movieUpdated.getTitle(), "ID 145 edited");
		
		Item showUpdated= model.getItemById(135, 1);
		
		int day3= showUpdated.getReleaseDate().getDay();
		int month3= showUpdated.getReleaseDate().getMonth();
		int year3= showUpdated.getReleaseDate().getYear();
		
		assertEquals(day1,day3);
		assertEquals(month1,month3);
		assertEquals(year1,year3);
		assertEquals(showUpdated.getTitle(), "Bobs show edited");
				
	}
	
	@Test
	public void testgetfriendIDsByUserID()
	{
		int[] ids1=null;
		int[] ids2=null;
		
		try {
			ids1= model.getFriendIDsByUserID(85);	//2 friends
			ids2= model.getFriendIDsByUserID(3);	//no friends
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(ids1==null)
			fail();
		
		if(ids2!=null)
			fail();
		
		assertEquals(87, ids1[0]);
      assertEquals(94, ids1[1]);
	}
	
	@Test
	public void testAddFriend()
	{
		try {
			model.addFriendForUserID(1, 2);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int[] id=null;
		try
		{
			id=model.getFriendIDsByUserID(1);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		assertEquals(2,id[0]);	//user id 1 only has one friend
	}
	
	@Test
	public void testAddFriendAlreadyAdded()
	{
		try {
			model.addFriendForUserID(85, 87);
		} catch (SQLException e) 
		{
			if(e instanceof MySQLIntegrityConstraintViolationException)
				System.out.println("friend already added");
			else
				fail();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testSearchShows()
	{
		
		TVShow[] shows=null;
		
		shows=model.searchShows("Terminator show");
		
		if(shows.length==0)
			fail();
		
		assertEquals(shows[0].getTitle(), "Terminator show");
		
		shows=model.searchShows("no title");
		
		if(shows.length!=0)
			fail();
		
	}
	
	@Test
	public void testSeachUsers()
	{
		User[] results= model.searchUsers("bob");
		
		if(results.length==0)
			fail();
		
		assertEquals(3,results.length);
		
		results= model.searchUsers("This should not give any results");
		
		if(results.length!=0)
			fail();
		
	}
	
	@Test
	public void testGetUsers()
	{
		UserList temp= model.getUsers(); //Gets all the users in the database and stores them in temp
		
		assertEquals("Bob",temp.getUser(1).getNickname());
		assertEquals("wendy", temp.getUser(3).getNickname());
		assertEquals("jimmy", temp.getUser(4).getNickname());
		assertEquals(null,temp.getUser(1000)); // No user with ID 1000 exists - returns null
	}
	
	@Test
	public void testSearch()
	{	
		Item[] results= model.search("Terminator"); 
		
		//There are 3 items in the database with the word terminator in the title
		
		assertEquals(3, results.length); //size should be 3 because there are 3 items matching the search
		
		results=model.search("no title"); //should return null - no items matching title "no title"
		
		if(results!=null)
			fail();			//the results should be null since search should return null
		
		
	}
	
	@Test
	public void testAddUserAlreadyExists()
	{
	User temp= new User("Not Bob", "not@bob.com", 5, true); //id used for creation does not matter real id created in database
	
		try 
		{
			model.addUser(temp, "notBob");
		} 
		catch(SQLException e)
		{
			if(e instanceof MySQLIntegrityConstraintViolationException)
				System.out.println("User with the email address already exists - Behaves as it should");
			
			else
			{
				e.printStackTrace();
				fail();
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	
	}
	
	@Test 
	public void testAddComment()
	{
		Comment comment= new Comment(1, "Testing comment", "Bob", MyDate.today());
		
		try {
			model.addComment(comment, 171, true);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		Item movie= model.getItemById(171, 0);
		
		ArrayList<Comment> comments=null;
		
		try {
			comments= model.getCommentsByItem(movie);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		//Since its a movie without any comments the first comment should be the array list
		
		assertEquals("Testing comment", comments.get(0).getText()); 		//Checking the text matches
		assertEquals("Bob", comments.get(0).getNickname());						//Checking nicknames are a match	
		
	}
	
	@Test
	public void testAddUserAddingUser()
	{
		User temp= new User("Not existing", "newUser4@newUser.com", 5, true); //id used for creation does not matter real id created in database
		
		try
		{
			model.addUser(temp, "newUser");
		}
		catch(SQLException e)
		{
			if(e instanceof MySQLIntegrityConstraintViolationException)
				System.out.println("User with the email address already exists - Behaves as it should");
			
			else
			{
				e.printStackTrace();
				fail();
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		try 
		{
			model.loadUsers();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		User saved=model.getUserById(121);
		
		assertEquals(saved.getNickname(), temp.getNickname());
		assertEquals(saved.getEmail(), temp.getEmail());
		
		
		
	}
	
	@Test
	public void testaddWatchedMovie()
	{
		try {
			model.addWatchedMovie(171,1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] watchedIDs=null;
		try {
			 watchedIDs= model.getWatchedMoviesByUserID(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean added=false;
		
		for(int i=0; i<watchedIDs.length; i++)
		{
			if(watchedIDs[i]==171)
				added=true;
		}
		
		if(!added)
			fail();
		
		
	}
	
	@Test
	public void testAddWathcedMovieAlreadyWatched()
	{
		try
		{
			model.addWatchedMovie(1,143);
		}
		catch(SQLException e)
		{
			if(e instanceof MySQLIntegrityConstraintViolationException)
				System.out.println("Has already been watched");//-- ok--- 
			else
				fail();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetWathcedMovies()
	{
		int[] watchedIDs1= null;
		int[] watchedIDs2=null;
		int[] watchedIDs3=null;
		
		try {
			 watchedIDs1= model.getWatchedMoviesByUserID(87);	//one watched
			 watchedIDs2= model.getWatchedMoviesByUserID(85);	//3 watched
			 watchedIDs3= model.getWatchedMoviesByUserID(2); //none watched
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(watchedIDs1==null || watchedIDs2==null)
			fail();
		
		if(watchedIDs3!=null)
			fail();
		
		assertEquals(watchedIDs1.length, 1);
		assertEquals(watchedIDs2.length, 3);
	
	}
	
	@Test
	public void testgetWatchedEpisodes()
	{
		Episode[] watchedIDs1=null;
		Episode[] watchedIDs2=null;
		Episode[] watchedIDs3=null;
		
		try {
			 watchedIDs1= model.getWatchedEpisodesByUserID(1);	//one watched
			 watchedIDs2= model.getWatchedEpisodesByUserID(85);	//11 watched
			 watchedIDs3= model.getWatchedEpisodesByUserID(2); //none watched
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(watchedIDs1==null || watchedIDs2==null)
			fail();
		
		if(watchedIDs3!=null)
			fail();
		
		assertEquals(watchedIDs1.length, 1);
		assertEquals(watchedIDs2.length, 11);
	}
	
	@Test
	public void testaddWatchedEpisode()
	{
		try {
			model.addWatchedEpisode(5, 3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Episode[] watchedEpisodes=null;
		try {
			 watchedEpisodes= model.getWatchedEpisodesByUserID(3);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(watchedEpisodes[0].getID(), 5);		//only watched by user with ID 3 is the newly added one
		
	}
	
	@Test
	public void testAlreadyAddedWatchedepisode()
	{
		try {
			model.addWatchedEpisode(5, 3);
		} catch (SQLException e) 
		{
			if(e instanceof MySQLIntegrityConstraintViolationException)
				System.out.println("Already added"); // --- ok ---
			else
			fail();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testAddItemTitleAlreadyExists()
	{
		ArrayList<String> taggy=new ArrayList<String>();
		ArrayList<String> genry= new ArrayList<String>();
		taggy.add("tag");
		genry.add("documentary");
		
		Movie temp= new Movie("kkk","Terminator 2",new MyDate(27,12,1999),genry,taggy,"ol",0, "144");
		
	//	taggy.add("derp");
	//	genry.add("derp2");
		TVShow temp2= new TVShow("lll","Terminator show", new MyDate(27,12,1999), genry, taggy,"lo",0,new ArrayList<Episode>(), true);
		
		try 
		{
			model.addItem(temp);
			model.addItem(temp2);	
		} 
		catch (SQLException e) 
		{
			if(e instanceof MySQLIntegrityConstraintViolationException)
				System.out.println("Movie or TVShow with title already exists - Behaves as it should");
			else
			{
				e.printStackTrace();
				fail();
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateUser()
	{
		try {
			model.updateUser(87, "Just edited", "danielb@hot.me", false);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		User updatedUser= model.getUserById(87);
		
		assertEquals("Just edited", updatedUser.getNickname());
		
	}
	
	@Test
	public void testremoveUser()
	{
		User userToDelete = new User("JUnitTest", "j@unit.test", 0, false);
		
		try {
         model.addUser(userToDelete, "junittest");
      } catch (SQLException e1) {
         e1.printStackTrace();
      } catch (IOException e1) {
         e1.printStackTrace();
      }
		
		User[] users = model.searchUsers("j@unit.test");
		
		userToDelete = users[0];
		
		try {
			model.removeUser(userToDelete.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		User temp= model.getUserById(userToDelete.getID());
		
		if(temp!=null)
			fail();
	}
	
	@Test
	public void testAddItemAddingItem()
	{
		ArrayList<String> taggy=new ArrayList<String>();
		ArrayList<String> genry= new ArrayList<String>();
		taggy.add("tag");
		genry.add("documentary");
		
		Movie temp= new Movie("kkk", "The movie5!!", new MyDate(27,12,1999), genry, taggy, "ol", 0, "144");
		
		TVShow temp2= new TVShow("lll","The TVShow5!!", new MyDate(27,12,1999), genry, taggy,"lo",0,new ArrayList<Episode>(), true);
		
		try 
		{
			model.addItem(temp);
			model.addItem(temp2);	
		} 
		catch (SQLException e) 
		{
			if(e instanceof MySQLIntegrityConstraintViolationException)
				System.out.println("Movie or TVShow with title already exists - Behaves as it should");
			else
			{
				e.printStackTrace();
				fail();
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		assertEquals(model.getItemById(172,0).getTitle(), "The movie5!!");
		assertEquals(model.getItemById(144,1).getTitle(), "The TVShow5!!");
	}
		
		
		
		
	}