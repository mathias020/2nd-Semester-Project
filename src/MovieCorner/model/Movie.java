package MovieCorner.model;

import java.io.Serializable;
import utility.collection.ArrayList;

public class Movie extends Item implements Serializable
{
   private String duration;

   /**
    * Constructor for creating a movie
    * 
    * @param description
    *           Description of the movie
    * @param title
    *           Title of the movie
    * @param releaseDate
    *           Date of the movie's release
    * @param genres
    *           Category of genres ( can be horror, action etc)
    * @param tags
    *           ArrayList of tags that represent keywords for the movie
    * @param director
    *           Director of the movie
    * @param id
    *           Id of the movie which is used to uniquely identify the movie
    * @param duration
    *           Duration of the movie
    */
   public Movie(String description, String title, MyDate releaseDate,
         ArrayList<String> genres, ArrayList<String> tags, String director,
         int id, String duration)
   {
      super(description, title, releaseDate, genres, tags, director, id);
      this.duration = duration;
   }
   /**
    * Constructor for creating a movie
    * 
    * @param description
    *           Description of the movie
    * @param title
    *           Title of the movie
    * @param releaseDate
    *           Date of the movie's release
    * @param genres
    *           Category of genres ( can be horror, action etc)
    * @param tags
    *           ArrayList of tags that represent keywords for the movie
    * @param director
    *           Director of the movie
    * @param id
    *           Id of the movie which is used to uniquely identify the movie
    * @param duration
    *           Duration of the movie
    * @param comments
    * 			The comments belonging to the item
    */
   public Movie(String description, String title, MyDate releaseDate,
	         ArrayList<String> genres, ArrayList<String> tags, String director,
	         int id, ArrayList<Comment> comments,String duration)
	   {
	      super(description, title, releaseDate, genres, tags, director, id, comments);
	      this.duration = duration;
	   }

   /**
    * @return Returns the duration of the movie
    */
   public String getDuration()
   {
      return duration;
   }

   /**
    * Method to check if a new created movie is a part of the Item Class (instanceof)
    * 
    */
   @Override
   public boolean equals(Object obj)
   {
      if (obj instanceof Movie && super.getId() == ((Movie) obj).getId())
      {
         return true;
      }
      return false;
   }

}
