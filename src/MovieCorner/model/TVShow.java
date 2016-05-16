package MovieCorner.model;

import java.io.Serializable;
import utility.collection.ArrayList;

public class TVShow extends Item implements Serializable
{

   private ArrayList<Episode> episodes;
   private boolean isFinished;

   /**
    * Constructor for creating a TV Show
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
    * @param episodes
    *           ArrayList that contains all of the TV shows episodes
    * @param isFinsihed
    *           Boolean that checks if the TV show is completed or not
    */
   public TVShow(String description, String title, MyDate releaseDate,
         ArrayList<String> genres, ArrayList<String> tags, String director,
         int id, ArrayList<Episode> episodes, boolean isFinsihed)
   {
      super(description, title, releaseDate, genres, tags, director, id);

      this.episodes = episodes;
      this.isFinished = isFinsihed;
   }

   /**
    * Constructor for creating a TV Show
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
    * @param comments
    * 			comments belonging to the TVShow
    * @param episodes
    *           ArrayList that contains all of the TV shows episodes
    * @param isFinsihed
    *           Boolean that checks if the TV show is completed or not
    */
   public TVShow(String description, String title, MyDate releaseDate,
         ArrayList<String> genres, ArrayList<String> tags, String director,
         int id, ArrayList<Comment> comments, ArrayList<Episode> episodes, boolean isFinsihed)
   {
      super(description, title, releaseDate, genres, tags, director, id,comments);

      this.episodes = episodes;
      this.isFinished = isFinsihed;
   }
   /**
    * @return Returns true if the TV show is marked as completed or returns
    *         false if the TV show is still running
    */
   public boolean isFinished()
   {
      return isFinished;
   }

   /**
    * @param seasonNo
    *           Takes a season as an argument to search for all episodes in that
    *           season
    * @return Returns a list with all of the episodes if the episodes season
    *         number matches the Season number given as argument; else if no
    *         episodes found returns null
    */
   public ArrayList<Episode> getEpisodesBySeason(int seasonNo)
   {
      ArrayList<Episode> list = new ArrayList<>();

      for (Episode episode : episodes)
         if (episode.getSeasonNo() == seasonNo)
            list.add(episode);

      return list;
   }

   /**
    * @param seasonNo
    *           The season in which we search
    * @param episodeNo
    *           The episode number we search for
    * @return If the season number and episode number given as arguments match ,
    *         it will return that specific episode; else it will return null
    */
   public Episode getEpisode(int seasonNo, int episodeNo)
   {
      for (Episode episode : episodes)
         if (episode.getEpisodeNo() == episodeNo
               && episode.getSeasonNo() == seasonNo)
            return episode;

      return null;
   }

   /**
    * @return Returns the number of seasons that the TV show has until now
    */
   public int getAmountOfSeasons()
   {
      int lastSeason = 0;

      for (Episode episode : episodes)
         if (episode.getSeasonNo() > lastSeason)
            lastSeason = episode.getSeasonNo();

      return lastSeason;
   }

   /**
    * @param seasonNo
    *           The season for which we perform the search
    * @return Returns the 1 episode's release date if the first episode's number
    *         matches the season number given as argument
    */
   public MyDate getSeasonReleaseDate(int seasonNo)
   {

      for (Episode episode : episodes)
         if (episode.getEpisodeNo() <= 1 && episode.getSeasonNo() == seasonNo)
            return episode.getReleaseDate();

      return null;
   }

   /**
    * Boolean that checks if the TV show is a part of the Item class
    * (instanceof)
    */
   @Override
   public boolean equals(Object obj)
   {

      if (obj instanceof TVShow && super.getId() == ((TVShow) obj).getId())
      {
         return true;
      }
      return false;
   }

}
