package MovieCorner.model;

import java.io.Serializable;

public class Episode implements Serializable
{

   private int seasonNo;
   private String title;
   private int episodeNo;
   private String resume;
   private MyDate releaseDate;
   private String duration;
   private int episodeID;

   /**
    * @param seasonNo
    *           The season number from which the episode is part of
    * @param title
    *           The episode's title
    * @param episodeNo
    *           The episode number
    * @param resume
    *           Short description of the episode
    * @param releaseDate
    *           Episode's release date
    * @param duration
    *           Episode's duration
    */
   public Episode(int seasonNo, String title, int episodeNo, String resume,
         MyDate releaseDate, String duration, int episodeID)
   {
      this.seasonNo = seasonNo;
      this.title = title;
      this.episodeNo = episodeNo;
      this.resume = resume;
      this.releaseDate = releaseDate;
      this.duration = duration;
      this.episodeID= episodeID;
   }

   /**
    * @return Returns the season number
    */
   public int getSeasonNo()
   {
      return seasonNo;
   }

   /**
    * @return Returns the title of the episode
    */
   public String getTitle()
   {
      return title;
   }

   /**
    * @return Returns the episode's number
    */
   public int getEpisodeNo()
   {
      return episodeNo;
   }

   /**
    * @return Returns the resume of the episode
    */
   public String getResume()
   {
      return resume;
   }

   /**
    * @return Returns the release date of the episode
    */
   public MyDate getReleaseDate()
   {
      return releaseDate;
   }

   /**
    * @return Returns the episode's duration
    */
   public String getDuration()
   {
      return duration;
   }
   /**
    * @return a string containing information about the episode
    */
   public String toString()
   {
	   return title;
   }
   /**
    * 
    * @return a integer containing the ID of the episode
    */
   public int getID()
   {
	   return episodeID;
   }
   
   /**
    * Checks if the object passed as an argument is the same as this.<br />
    * 
    * @param obj The Object to compare with
    * 
    * @return True if all instance fields has the same value, otherwise false.
    */
   public boolean equals(Object obj) {
      if (!(obj instanceof Episode))
         return false;

      Episode ep = (Episode) obj;
      return 
            ep.duration.equals(duration) &&
            ep.episodeID == episodeID &&
            ep.episodeNo == episodeNo &&
            ep.seasonNo == seasonNo &&
            ep.releaseDate.equals(releaseDate) &&
            ep.resume.equals(resume) &&
            ep.title.equals(title);
   }
   
}
