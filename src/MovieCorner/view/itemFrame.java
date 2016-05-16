package MovieCorner.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import utility.collection.ArrayList;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import MovieCorner.controller.Controller;
import MovieCorner.model.Comment;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;
import MovieCorner.model.Movie;
import MovieCorner.model.Review;
import MovieCorner.model.TVShow;

@SuppressWarnings("serial")
public class itemFrame extends JFrame {

   private Item obj;
   private Controller controller;
   private JTabbedPane tabPane;
   private JEditorPane commentsTab;
   private Comment[] comments;
   
   private JButton markWatched;
   
   private JButton markFavorite;
   private JButton btnReviews;
   private JButton btnComments;
   
   private MainFrame main;
   
   private Episode[] watchedEpisodes;
   private int[] watchedMovies;
   private int[] favorites;
   
   private Review[] reviews;
   private double rating;
   private String commentIDtoRemove;
   
   private boolean commentsLoaded;
   
   private JLabel loadingLabel;
   
   private ReviewFrame reviewFrame;
   
   private static final double MAX_RATING = 10;
   
   public itemFrame(Object object, Controller controller, MainFrame main) {
   
      this.obj = (Item) object;
      this.controller = controller;
      this.main = main;
      watchedEpisodes = null;
      watchedMovies = null;
      reviews = null;
      rating = 0;
      
      commentsLoaded = false;
      
      reviewFrame = null;
   }
   
   public void setReviews(Review[] reviews)
   {
	   this.reviews = reviews;
   }
   
   public void setRating(double rating)
   {
	   this.rating = rating;
   }
   
   public void setWatchedEpisodes(Episode[] episodes)
   {
	   watchedEpisodes = episodes;
   }
   
   public void setWatchedMovies(int[] movies)
   {
	   watchedMovies = movies;
   }
   
   private boolean inEpisodes(int episodeID)
   {
	   if(watchedEpisodes == null)
		   return false;
	   
	   for(int i = 0; i < watchedEpisodes.length; i++)
		   if(watchedEpisodes[i] != null)
			   if(watchedEpisodes[i].getID() == episodeID) return true;
	   
	   return false;
   }
   
   private boolean inMovies(int movieID)
   {
	   if(watchedMovies == null)
		   return false;
	   
	   for(int i = 0; i < watchedMovies.length; i++)
		   if(watchedMovies[i] == movieID) return true;
	   
	   return false;
   }
   
   private boolean inFavorites(int itemID)
   {
	   if(favorites == null)
		   return false;
	   
	   for(int i = 0; i < favorites.length; i++)
		   if(favorites[i] == itemID) return true;
	   
	   return false;
   }
   
   public void setFavorites(int[] favorites)
   {
	   this.favorites = favorites;
   }
   
   public void start() {
	  
	  
      String type = "Movie";
      
      if (obj instanceof TVShow)
         type = "TVShow";
      
      setTitle("Details");
      
      TableModel detailTableModel = new DefaultTableModel();
      
      if (type.equals("Movie")) {
      detailTableModel = new DefaultTableModel(
            new Object[][] {
                  {"Type", type},
                  {"Release date", obj.getReleaseDate()},
                  {"Description", obj.getDescription()},
                  {"Director", obj.getDirector()},
                  {"Genres", obj.getGenres()},
                  {"Tags", obj.getTags()},
                  {"Duration", ((Movie) obj).getDuration()},
               },
               new String[] {
                  "", ""
               }
            );
      
      		
      } else if (type.equals("TVShow")) {
      detailTableModel = new DefaultTableModel(
            new Object[][] {
                  {"Type", type},
                  {"Release date", obj.getReleaseDate()},
                  {"Description", obj.getDescription()},
                  {"Director", obj.getDirector()},
                  {"Genres", obj.getGenres()},
                  {"Tags", obj.getTags()},
                  {"Amount of seasons", ((TVShow) obj).getAmountOfSeasons()},
               },
               new String[] {
                  "", ""
               }
            );
      
      	
      }
      
      
      
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setBounds(100, 100, 859, 460);
      JPanel contentPanel = new JPanel();
      contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPanel.setLayout(new BorderLayout(0, 0));
      
      JPanel topPanel = new JPanel();
      topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      topPanel.setLayout(new BorderLayout(0, 0));
      
      JPanel panela = new JPanel();
      contentPanel.add(panela, BorderLayout.SOUTH);
      
      btnComments = new JButton("Add a comment");
      btnComments.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			addComment();
		}});
      panela.add(btnComments);
      
      
      
      btnReviews = new JButton("Add a review");
      panela.add(btnReviews);
      
      
      JLabel lblTitle = new JLabel(obj.getTitle());
      lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
      lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
      topPanel.add(lblTitle, BorderLayout.WEST);
      
      JPanel buttonsEast = new JPanel();
      buttonsEast.setLayout(new FlowLayout());
      
      
      if(obj instanceof Movie)
      {
	      markWatched = new JButton("Mark as seen");
	      markWatched.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you wish to mark this movie as seen? This can not be undone.");
				
				if(choice == JOptionPane.YES_OPTION) 
				{
					markWatched.setEnabled(false);
					markWatched.setText("Already seen");
					controller.execute("MarkWatched");
					controller.execute("GetWatchedMovies");
				}
				
			}
	    	  
	      });
	      
	      markWatched.setHorizontalAlignment(SwingConstants.CENTER);
	      buttonsEast.add(markWatched);
      }
      
      
      markFavorite = new JButton("Favourite");
      markFavorite.addActionListener( new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int choice = JOptionPane.showConfirmDialog(null, "Are you sure you wish to favorite this ? This action can not be undone");
			
			if(choice == JOptionPane.YES_OPTION)
			{
				markFavorite.setEnabled(false);
				controller.execute("AddFavourite");
			}
		}
      });
      
      markFavorite.setHorizontalAlignment(SwingConstants.CENTER);
      buttonsEast.add(markFavorite);
      
      markFavorite.setEnabled(false);
      if(obj instanceof Movie)
    	  markWatched.setEnabled(false);
      btnReviews.setEnabled(false);
      btnComments.setEnabled(false);
      
      
      topPanel.add(buttonsEast, BorderLayout.EAST);
      contentPanel.add(topPanel, BorderLayout.NORTH);
      
      
      JPanel centerPanel = new JPanel();
      centerPanel.setLayout(new BorderLayout());
      
      JTable tablea = new JTable();
      tablea.setRowHeight(20);
      
      tablea.setEnabled(false);
      tablea.setModel(detailTableModel);
      
      tablea.getColumnModel().getColumn(1).setResizable(false);
      

      centerPanel.add(tablea, BorderLayout.NORTH);
      
      
      tabPane = new JTabbedPane();
      tabPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      loadingLabel = new JLabel("Loading details, please wait...");
      tabPane.add(loadingLabel);

      centerPanel.add(tabPane, BorderLayout.CENTER);

      contentPanel.add(centerPanel, BorderLayout.CENTER);
      
      setContentPane(contentPanel);
      setVisible(true);
      
      SwingUtilities.invokeLater(new Runnable() {

		@Override
		public void run() {
			if(obj instanceof Movie)
				controller.execute("GetWatchedMovies");
			else
			{
				controller.execute("GetWatchedEpisodes");
				loadSeasonsAndEpisodes();
				tabPane.remove(loadingLabel);
			}
			
			
			
			controller.execute("GetFavourites");
			
			
		      if(inMovies(obj.getId()))
		      {
		    	  markWatched.setEnabled(false);
		    	  markWatched.setText("Already seen");
		      }
		      else if(obj instanceof Movie)
		    	  markWatched.setEnabled(true);
		      
		      if(inFavorites(obj.getId()))
		      {
		    	  markFavorite.setEnabled(false);
		      }
		      else
		    	  markFavorite.setEnabled(true);
		      
		      

		      commentsTab = new JEditorPane();
		      HTMLEditorKit editorKit = new HTMLEditorKit();
		      commentsTab.setEditorKit(editorKit);
		      
		      commentsTab.addHyperlinkListener(new HyperlinkListener() {
		          @Override
		          public void hyperlinkUpdate(HyperlinkEvent hle) {
		              if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
		                 commentIDtoRemove = hle.getDescription();
		                 controller.execute("removeComment");
		                 SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								loadComments();
								
							}
		                	 
		                 });
		                 
		              }
		          }
		      });
		      
		      editorKit.getStyleSheet().addRule("div {border: solid 1px #bcbcbc; background-color: #F2F2F2; font: Tahoma}");
		      editorKit.getStyleSheet().addRule("h1 {padding: 2; margin:0; font-size: 9px; font: Tahoma; border-bottom: solid 1px #bcbcbc; background-color: #DBDBDB; float:left;}");
		      editorKit.getStyleSheet().addRule("p {padding: 4px; font: Tahoma; font-size: 10px; float:left; margin:0;}");
		      editorKit.getStyleSheet().addRule("h2 {font: Tahoma; font-size: 12px; font-style: italic;}");
		      
		      Document doc = editorKit.createDefaultDocument();
		      
		      
		      commentsTab.setDocument(doc);
		      commentsTab.setEditable(false);
		      
		      loadComments();
		      
		      if(obj instanceof Movie)
		      {
		    	  tabPane.remove(loadingLabel);
		    	  loadReviews();
		      }
		      
		      
		      btnReviews.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(obj instanceof Movie)
					{
						if(inMovies(obj.getId()))
						{
							reviewFrame = new ReviewFrame(controller);
						}
						else
							JOptionPane.showMessageDialog(null, "You can not review a movie you have not seen.");
					}
					else
					{
						if(main.getCurrentEpisode() != null)
							if(inEpisodes(main.getCurrentEpisode().getID()))
							{
								reviewFrame = new ReviewFrame(controller);
							}
							else
								JOptionPane.showMessageDialog(null, "You can not review an episode you have not seen.");
					}
					
					if(reviewFrame != null)
					{
						reviewFrame.addWindowListener(new WindowListener() {

							@Override
							public void windowActivated(WindowEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowClosed(WindowEvent arg0) {
								reviewFrame = null;
								
							}

							@Override
							public void windowClosing(WindowEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowDeactivated(WindowEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowDeiconified(WindowEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowIconified(WindowEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowOpened(WindowEvent arg0) {
								// TODO Auto-generated method stub
								
							}
							
						});
					}
					
				}
		    	  
		      });
		      
		      sendToFront();
		      
		      btnReviews.setEnabled(true);
		      btnComments.setEnabled(true);
		}
    	  
      });
   }
   
   private void sendToFront()
   {
	   SwingUtilities.invokeLater(new Runnable() {

		@Override
		public void run() {
		   toFront();
		   repaint();
		}
		   
	   });

   }
   
   public void reviewAdded()
   {
	   reviewFrame.dispose();
	   
	   if(obj instanceof Movie)
	   {
		   loadReviews();
	   	   tabPane.setSelectedIndex(1);
	   }
	   else
	   {
		   if(main.getCurrentEpisode() != null)
		   {
			   Episode episode = main.getCurrentEpisode();
			   
			   viewReviews(episode.getEpisodeNo(), episode.getSeasonNo());
		   }
	   }
   }
   
   public void setComments(Comment[] comments)
   {
	   this.comments = comments;
   }
   
   public void addComment()
   {
	   controller.execute("Add comment");
	   
	   loadComments(); // Reload comments
   }
   
   public void removeComment()
   {
       controller.execute("removeComment");
       
       
       loadComments(); // Reload comments
   }
   
   public void loadComments()
   {
	   controller.execute("Get comments"); // Reload comments
	   
	   String html = "<html><body>";
	   
	   if(comments == null)
	   {
		   html += "<center><h2>No comments on this item</h2></center>";
	   }
	   else 
		   for(int i = 0; i < comments.length; i++)
		   {
		      String removeComment = (main.isAdmin()) ? " <a href="+comments[i].getID()+">REMOVE</a>" : "";
            String commentHeaderLeft = "<b>" + comments[i].getNickname() + "</b> @ " + comments[i].getDate().toString(); 
            html += "<div class='comment'> <h1>" + commentHeaderLeft + removeComment + "</h1><p>" + comments[i].getText() + "</p> </div><br />";
            }
	   
	   html += "</body></html>";
	   
	   commentsTab.setText(html);
	   
      if(commentsLoaded)
      {
	      if(obj instanceof TVShow) {
	    	  if(tabPane.getTabCount() == 1)
	    	  {
		    	  tabPane.setComponentAt(0, new JScrollPane(commentsTab));
		    	  tabPane.setTitleAt(0, "Comments ( " + ((comments == null) ? 0 : comments.length) + " )");
	    	  }
	    	  else
	    	  {
		    	  tabPane.setComponentAt(1, new JScrollPane(commentsTab));
		    	  tabPane.setTitleAt(1, "Comments ( " + ((comments == null) ? 0 : comments.length) + " )");
	    	  }

	      }
	      else if(obj instanceof Movie){
	         tabPane.setComponentAt(0, new JScrollPane(commentsTab));
	         tabPane.setTitleAt(0, "Comments ( " + ((comments == null) ? 0 : comments.length) + " )");
	      }
      }
      else { 
         tabPane.addTab("Comments ( " + ((comments == null) ? 0 : comments.length) + " )", new JScrollPane(commentsTab));
         commentsLoaded = true;
      }
      
      

   }
   
   public String displayRating(double rating, boolean colors)
   {
	   if(rating > MAX_RATING)
		   rating = MAX_RATING;
	   else if(rating < 0)
		   rating = 0;
	   
	   DecimalFormat formatter = new DecimalFormat("##.0", new DecimalFormatSymbols(Locale.US));
	   
	   String formatted = formatter.format(rating);
	   
	   rating = Double.valueOf(formatted);
	   
	   String asString = "" + rating + "";
	   
	   if(colors)
	   {
		   if(rating >= 0 && rating <= 3)
			   asString = "<font color='#DE0000' size='10' face='Tahoma'>" + rating + "</font>";
		   else if(rating > 3 && rating <= 7)
			   asString = "<font color='#917700' size='10' face='Tahoma'>" + rating + "</font>";
		   else if(rating > 7)
			   asString = "<font color='#1D9100' size='10' face='Tahoma'>" + rating + "</font>";
	   }
	   
	   return asString + "  <font size='6' face='Tahoma'>/ " + (int)MAX_RATING + "</font>";
   }
   
   public void loadReviews()
   {
	   		controller.execute("getMovieReviews");
	   
	      JEditorPane reviewsTab = new JEditorPane();
	      HTMLEditorKit editorKit = new HTMLEditorKit();
	      reviewsTab.setEditorKit(editorKit);
	      
	      editorKit.getStyleSheet().addRule("div {border: solid 1px #bcbcbc; background-color: #F2F2F2; font: Tahoma}");
	      editorKit.getStyleSheet().addRule("h1 {padding: 2; margin:0; font-size: 9px; font: Tahoma; border-bottom: solid 1px #bcbcbc; background-color: #DBDBDB; float:left;}");
	      editorKit.getStyleSheet().addRule("p {padding: 4px; font: Tahoma; font-size: 10px; float:left; margin:0;}");
	      editorKit.getStyleSheet().addRule("h2 {font: Tahoma; font-size: 12px; font-style: italic;}");
	      editorKit.getStyleSheet().addRule("span {font: Tahoma; font-size: 13px; }");
	      Document doc = editorKit.createDefaultDocument();
	      
	      
	      reviewsTab.setDocument(doc);
	      reviewsTab.setEditable(false);
	      
		   String html = "<html><body> <table border='0' width='100%' cellpadding='5' cellspacing='5'><tr><td width='20%' valign='middle'><span><b>Average Rating: </b></span></td> <td valign='middle' width='80%'> " + ((Double.compare(rating, Double.NaN) == 0) ? displayRating(0, true) : displayRating(rating, true)) + "</td></tr></table> <hr noshade size='1'>";
		   
		   if(reviews == null)
		   {
			   html += "<center><h3>No reviews on this item</h3></center>";
		   }
		   else 
			   for(int i = 0; i < reviews.length; i++)
			   {
				   String commentHeaderLeft = "<b>" + reviews[i].getNickname() + "</b> @ " + reviews[i].getDateAdded().toString() + " - <b>Rating:</b> " + reviews[i].getRating(); 
				   html += "<div class='comment'> <h1>" + commentHeaderLeft + "</h1>  <p>" + reviews[i].getReview() + "</p> </div><br />";
			   }
		   
		   html += "</body></html>";
		   
		   reviewsTab.setText(html);
		   
		   if(tabPane.getTabCount() > 1) {
			   tabPane.setComponentAt(1, new JScrollPane(reviewsTab));
			   tabPane.setTitleAt(1, "Reviews ( " + (reviews==null ? 0 : reviews.length) + " )");
		   }
		   else
		   		tabPane.add("Reviews ( " + (reviews==null ? 0 : reviews.length) + " )" , new JScrollPane(reviewsTab));
   }
   
   @SuppressWarnings("unused")
   public void loadSeasonsAndEpisodes()
   {
  		TVShow show = (TVShow) obj;
   		
  		if(show.getAmountOfSeasons() == 0)
  			return;
	
   		JTabbedPane seasonTabs = new JTabbedPane();
   		seasonTabs.setBorder(new EmptyBorder(5, 5, 5, 5));
   		seasonTabs.setTabPlacement(JTabbedPane.LEFT);
   		JScrollPane seasonTabsScroll = new JScrollPane(seasonTabs);
	   	
      Action markAsSeen = new AbstractAction()
	  	  {
	  	      public void actionPerformed(ActionEvent e)
	  	      {
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you wish to mark this episode as seen? This can not be undone.");
				
				if(choice == JOptionPane.OK_OPTION)
				{
	  	          JTable table = (JTable)e.getSource();
	  	          
	  	          int episodeNo = (int) table.getModel().getValueAt(table.getSelectedRow(), 0);
	  	          int seasonNo = (int) table.getModel().getValueAt(table.getSelectedRow(), 6);
	  	          
	  	          System.out.println(episodeNo + " | " + seasonNo);
	  	          
	  	          TVShow show = (TVShow) obj;
	  	          Episode episode = show.getEpisode(seasonNo, episodeNo);
	  	          
	  	          main.setCurrentEpisode(episode);
	  	          table.setValueAt("Marked", table.getSelectedRow(), 5);
	  	        
	  	          controller.execute("MarkWatched");
	  	          controller.execute("GetWatchedEpisodes");
//	  	          loadSeasonsAndEpisodes();
	  	          
				}
	  	      }
	  	  };
	  	  
   		for(int i = 1; i <= show.getAmountOfSeasons(); i++)
   		{
   			ArrayList<Episode> episodesInSeason = show.getEpisodesBySeason(i);
   			
   			if(episodesInSeason.size() == 0)
   				continue;
   			
	  	      Object[] columnNames = new Object[7];
	  	      Object[][] rowData = new Object[episodesInSeason.size()][7];
	  	      
	  	      columnNames[0] = "Episode #";
	  	      columnNames[1] = "Title";
	  	      columnNames[2] = "Resume";
	  	      columnNames[3] = "Release Date";
	  	      columnNames[4] = "Duration";
	  	      columnNames[5] = "Watched";
	  	      columnNames[6] = "Season ID"; // This column is hidden only used for program functionality
	  	      
	  	      
	  	      for(int k = 0; k < episodesInSeason.size(); k++ )
	  	      {
	  	    	  Episode episode = episodesInSeason.get(k);
	  	    	  rowData[k][0] = episode.getEpisodeNo();
	  	    	  rowData[k][1] = episode.getTitle();
	  	    	  rowData[k][2] = episode.getResume();
	  	    	  rowData[k][3] = episode.getReleaseDate();
	  	    	  rowData[k][4] = episode.getDuration();
	  	    	  if(inEpisodes(episode.getID()))
	  	    		  rowData[k][5] = "Marked";
	  	    	  else
	  	    		  rowData[k][5] = "Mark as seen";
	  	    	  
	  	    	  rowData[k][6] = episode.getSeasonNo();
	  	      }
	  	      
	  	    DefaultTableModel model = new MyModel(rowData, columnNames);
	  	    
	  	    JTable table = new JTable( model );
	  	    
	  	    table.removeColumn(table.getColumnModel().getColumn(6));
	  	    
	  	    
	        table.addMouseListener(new MouseAdapter()
	        { 
	        public void mouseClicked(MouseEvent e)
	        { int mouseClick = e.getClickCount(); 
	        if (mouseClick == 2)
	        {
	        
	          int episodeNo = (int) table.getModel().getValueAt(table.getSelectedRow(), 0);
	          int seasonNo = (int) table.getModel().getValueAt(table.getSelectedRow(), 6);
	           viewReviews(episodeNo, seasonNo);
	        }
	        }
	        });
	  	      
	  	      JScrollPane tableScroll = new JScrollPane(table);
			  
	  	      table.setRowHeight(20);
	  	      ButtonColumn buttonColumn = new ButtonColumn(table, markAsSeen, 5);
	  	      seasonTabs.addTab("Season " + i, tableScroll);
	  	      

   		}
   		
		if(tabPane.getTabCount() > 0)
		{
			tabPane.setComponentAt(0, seasonTabsScroll);
			tabPane.setTitleAt(0, "Seasons ( " + show.getAmountOfSeasons() + " )");
		}
		else
			tabPane.addTab("Seasons ( " + show.getAmountOfSeasons() + " )", seasonTabsScroll);
   		
   }
   
   public void show(Object[] message)
   {
	   switch(message[0].toString())
	   {
	   case "episodeReviews":
		   reviews = new Review[message.length-1];
		   
		   for(int i = 1; i < message.length; i++)
		   {
			   reviews[i-1] = (Review) message[i];
		   }
		   break;
	   case "episodeRating":
		   rating = (double)message[1];
		   break;
		   
	   case "movieReviews":
		   reviews = new Review[message.length-1];
		   
		   for(int i = 1; i < message.length; i++)
		   {
			   reviews[i-1] = (Review) message[i];
		   }
		   break;
	   case "movieRating":
		   rating = (double)message[1];
		   break;
	   }
   }
   
   public Object get(String what)
   {
	   switch(what)
	   {
	   case "review":
		   return reviewFrame.getReview();
	   case "commentID":
         return commentIDtoRemove;
	   case "rating":
		   return reviewFrame.getRating();
	   }
	   
	   return null;
   }
   
   private void viewReviews(int episodeNo, int seasonNo)
   {
	   Episode episode = ((TVShow) obj).getEpisode(seasonNo, episodeNo);
	   main.setCurrentEpisode(episode);
	   
	   controller.execute("getEpisodeReviews");
	   
      JEditorPane reviewsTab = new JEditorPane();
      HTMLEditorKit editorKit = new HTMLEditorKit();
      reviewsTab.setEditorKit(editorKit);
      
      editorKit.getStyleSheet().addRule("div {border: solid 1px #bcbcbc; background-color: #F2F2F2; font: Tahoma}");
      editorKit.getStyleSheet().addRule("h1 {padding: 2; margin:0; font-size: 9px; font: Tahoma; border-bottom: solid 1px #bcbcbc; background-color: #DBDBDB; float:left;}");
      editorKit.getStyleSheet().addRule("p {padding: 4px; font: Tahoma; font-size: 10px; float:left; margin:0;}");
      editorKit.getStyleSheet().addRule("h2 {font: Tahoma; font-size: 12px; font-style: italic;}");
      editorKit.getStyleSheet().addRule("span {font: Tahoma; font-size: 13px; }");
      
      Document doc = editorKit.createDefaultDocument();
      
      
      reviewsTab.setDocument(doc);
      reviewsTab.setEditable(false);
      
	   String html = "<html><body> <table border='0' width='100%' cellpadding='5' cellspacing='5'><tr><td width='20%' valign='middle'><span><b>Average Rating: </b></span></td> <td valign='middle' width='80%'> " + ((Double.compare(rating, Double.NaN) == 0) ? displayRating(0, true) : displayRating(rating, true)) + "</td></tr></table> <hr noshade size='1'>";
	   
	   if(reviews == null)
	   {
		   html += "<center><span>No reviews on this item</span></center>";
	   }
	   else 
		   for(int i = 0; i < reviews.length; i++)
		   {
			   String commentHeaderLeft = "<b>" + reviews[i].getNickname() + "</b> @ " + reviews[i].getDateAdded().toString() + " - <b>Rating:</b> " + reviews[i].getRating(); 
			   html += "<div class='comment'> <h1>" + commentHeaderLeft + "</h1>  <p>" + reviews[i].getReview() + "</p> </div><br />";
		   }
	   
	   html += "</body></html>";
	   
	   reviewsTab.setText(html);
      
	   if(tabPane.getTabCount() > 2)
		   tabPane.remove(2);
	   
      tabPane.add(new JScrollPane(reviewsTab), 2);
      tabPane.setSelectedIndex(2);
      tabPane.setTitleAt(2, "Reviews [S" + episode.getSeasonNo() + "E" + episode.getEpisodeNo() + "] ( " + (reviews==null ? 0 : reviews.length) + ")");
   }
   
   class MyModel extends DefaultTableModel 
   {
	   public MyModel(Object[][] data, Object[] columns) 
	   {
	       super(data, columns);
	   }
	
	   public MyModel()
	   {
	      super();
	   }
	   
	   public boolean isCellEditable(int row, int col) 
	   {
	       if(col == 5)
	    	   return true;
	       else
	    	   return false;
	   }
   }   
   
   }