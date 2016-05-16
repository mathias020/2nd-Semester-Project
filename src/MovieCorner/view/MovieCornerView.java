package MovieCorner.view;

import MovieCorner.controller.Controller;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;

public interface MovieCornerView 
{
   public void start(Controller controller);
   public String get(String what);
   public void show(String message);
   public void show(Object[] message);
   
   public Item getCurrentItem();
   
   public Episode getCurrentEpisode();
   
   public void setCurrentEpisode(Episode episode);
   
   public void startLoading(String text);
   public void finishLoading();
}
