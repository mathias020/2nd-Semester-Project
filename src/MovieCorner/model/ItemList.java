package MovieCorner.model;

import java.io.Serializable;
import utility.collection.ArrayList;

public class ItemList implements Serializable
{

   private ArrayList<Item> items;

   public ItemList(ArrayList<Item> items)
   {
      this.items = items;
   }

   /**
    * @param id
    *           ID of the item which will be used to identify the item
    * @param type
    *           type of the item, 0== movie 1== tvshow
    * @return returns the item with ID of id and instance of type else it
    *         returns null
    */
   public Item getItemById(int id, int type)
   {
      if (type == 0)
      {
         for (Item item : items)
            if (item.getId() == id)
               if (item instanceof Movie)
                  return item;
      }
      if (type == 1)
      {
         for (Item item : items)
            if (item.getId() == id)
               if (item instanceof TVShow)
                  return item;
      }

      return null;
   }

   /**
    * Method to add a new item to the list
    * 
    * @param item
    *           The item that is being added to the list
    */
   public void addItem(Item item)
   {
      items.add(item);
   }

   /**
    * @return Returns the size of the Item list
    */
   public int size()
   {
      return items.size();
   }

   /**
    * @param title
    *           The item's title that we use in the search
    * @return Returns the item if the title was found in the list, else it
    *         returns null
    */
   public Item getItem(String title)
   {
      Item temp = null;

      for (int i = 0; i < items.size(); i++)
      {
         if (items.get(i).getTitle().equals(title))
            temp = items.get(i);
      }

      return temp;
   }

   /**
    * @return Returns the Item List with all elements inside
    */
   public ArrayList<Item> get()
   {
      return items;
   }

}
