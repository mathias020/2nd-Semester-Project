package MovieCorner.view;

import MovieCorner.controller.Controller;
import MovieCorner.model.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.*;
import java.awt.event.*;
import utility.collection.ArrayList;

@SuppressWarnings("serial")
public class settingsFrame extends JFrame {

   private Controller controller;
   private JButton btnAddTVShow;
   private GroupLayout menuLayout;
   private JPanel menuPanel, addPanel;
   private JButton btnAddMovie;
   private JButton btnAddEpisode;
   private JButton btnManageItems;
   private Object[] data;

   // FOR ADD ITEM
   private JPanel mainPanel, titlePanel, descriptionPanel, directorPanel,
   tagsPanel, genresPanel, genresBoxPanel, bottomPanel, seasonPanel,
   episodePanel, durationPanel, releasePanel, showsPanel;
   private JLabel lblReleaseDate;
   private String type;
   private JTextField fieldTitle, fieldDirector, durationField,
         releaseDateField;
   private ArrayList<JCheckBox> boxList;
   private JComboBox<Object> comboBox;
   private JTextArea fieldDescription, fieldTags;
   private JSpinner fieldEpisode, fieldSeason;
   
   // FOR MANAGE ITEM
	private JTextField titleField;
	private JTextField directorField;
	private JTextField releaseField;
	private JTextField fieldDuration;
	private JTextArea descField;
	private JTextArea tagField;
   
	private JComboBox<String> comboBoxManage;
	public ArrayList<JCheckBox> boxListManage;

   public settingsFrame() {
      setupFrame();
      createComponents();
      initializeComponents();
      eventHandler();

      setContentPane(menuPanel);
   }

   private void createComponents() {

      menuPanel = new JPanel();

      btnAddTVShow = new JButton("Add TV Show");
      btnAddMovie = new JButton("Add Movie");
      btnAddEpisode = new JButton("Add Episode");
      btnManageItems = new JButton("Manage Items");
      
      btnManageItems.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      	}
      });

      menuLayout = new GroupLayout(menuPanel);
      menuLayout.setHorizontalGroup(
      	menuLayout.createParallelGroup(Alignment.TRAILING)
      		.addGroup(menuLayout.createSequentialGroup()
      			.addContainerGap(59, Short.MAX_VALUE)
      			.addGroup(menuLayout.createParallelGroup(Alignment.LEADING)
      				.addGroup(Alignment.TRAILING, menuLayout.createParallelGroup(Alignment.TRAILING, false)
      					.addComponent(btnAddEpisode, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
      					.addComponent(btnAddTVShow, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
      					.addComponent(btnAddMovie, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
      					.addComponent(btnManageItems, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)))
      			.addGap(46))
      );
      menuLayout.setVerticalGroup(
      	menuLayout.createParallelGroup(Alignment.LEADING)
      		.addGroup(menuLayout.createSequentialGroup()
      			.addGap(23)
      			.addComponent(btnAddMovie, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
      			.addPreferredGap(ComponentPlacement.RELATED)
      			.addComponent(btnAddTVShow, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
      			.addPreferredGap(ComponentPlacement.RELATED)
      			.addComponent(btnAddEpisode, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
      			.addPreferredGap(ComponentPlacement.RELATED)
      			.addComponent(btnManageItems, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
      			.addPreferredGap(ComponentPlacement.RELATED))
      );
   }

   private void initializeComponents() {

      menuPanel.setLayout(menuLayout);

   }

   private void setupFrame() {
      setTitle("Control panel");
      setSize(300, 249);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setResizable(false);
      setType(JFrame.Type.UTILITY);
      setLocationRelativeTo(null);
   }

   private void eventHandler() {
	   
	   
	  // Manage items
	   
	   btnManageItems.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			   controller.execute("getAllItems");
			   
			   setTitle("Manage Items");
			   type = "manageItems";
			   addPanel = new ManageItems();
			   setSize(500, 550);
			   setLocationRelativeTo(null);
			   setContentPane(addPanel);
			
		}
		   
	   });
	   
      // add Movie
      btnAddMovie.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {

            setTitle("Add Movie");
            type = "movie";
            addPanel = new addItem();
            setSize(450, 350);
            setLocationRelativeTo(null);
            setContentPane(addPanel);

         }
      });

      // add TV Show
      btnAddTVShow.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {

            setTitle("Add TV Show");
            type = "tvshow";
            addPanel = new addItem();
            setSize(450, 350);
            setLocationRelativeTo(null);
            setContentPane(addPanel);

         }
      });

      // add TV Show
      btnAddEpisode.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {

            controller.execute("getTVShowList");

            setTitle("Add Episode");
            type = "episode";
            addPanel = new addItem();
            setSize(270, 350);
            setLocationRelativeTo(null);
            setContentPane(addPanel);
         }
      });
   }

   private void menu() {
      setSize(300, 249);
      setLocationRelativeTo(null);
      setContentPane(menuPanel);
   }

   public void start(Controller controller) {
      this.controller = controller;

      setContentPane(menuPanel);
      setVisible(true);
   }

   public Item getCurrentItem() {

      if (type.equals("tvshow")) {
         String[] releaseDate = releaseDateField.getText().split("/");
         ArrayList<String> genres = new ArrayList<>();

         for (JCheckBox genre : boxList)
            if (genre.isSelected() == true) genres.add(genre.getText());

         ArrayList<String> tags = new ArrayList<>();
         String[] tempTags = fieldTags.getText().split(",");
         for (String tag : tempTags)
            tags.add(tag);

         return new TVShow(fieldDescription.getText(), fieldTitle.getText(),
               new MyDate(Integer.parseInt(releaseDate[0]), Integer
                     .parseInt(releaseDate[1]), Integer
                     .parseInt(releaseDate[2])), genres, tags,
               fieldDirector.getText(), -1, null, null, false);

      } else if (type.equals("movie")) {
         String[] releaseDate = releaseDateField.getText().split("/");
         ArrayList<String> genres = new ArrayList<>();

         for (JCheckBox genre : boxList)
            if (genre.isSelected() == true) genres.add(genre.getText());

         ArrayList<String> tags = new ArrayList<>();
         String[] tempTags = fieldTags.getText().split(",");
         for (String tag : tempTags)
            tags.add(tag);

         return new Movie(fieldDescription.getText(), fieldTitle.getText(),
               new MyDate(Integer.parseInt(releaseDate[0]), Integer
                     .parseInt(releaseDate[1]), Integer
                     .parseInt(releaseDate[2])), genres, tags,
               fieldDirector.getText(), -1, durationField.getText());

      } else if (type.equals("episode")) return (Item) comboBox
            .getSelectedItem();
   else if (type.equals("manageItems"))
   {

		   String[] releaseDate = releaseField.getText().split("/");
			
			ArrayList<String> tags = new ArrayList<>();
	         String[] tempTags = tagField.getText().split(",");
	         for (String tag : tempTags)
	            tags.add(tag);
			
			ArrayList<String> genres = new ArrayList<>();
	         for (JCheckBox genre : boxListManage)
	             if (genre.isSelected() == true) genres.add(genre.getText());
  	   if(data[comboBoxManage.getSelectedIndex()] instanceof Movie)
	   {      
		   return new Movie(descField.getText(), titleField.getText(),  new MyDate(Integer.parseInt(releaseDate[0]), Integer
                   .parseInt(releaseDate[1]), Integer
                   .parseInt(releaseDate[2])), genres, tags, directorField.getText(), ((Item) data[comboBoxManage.getSelectedIndex()]).getId(), fieldDuration.getText());
	   }
  	   else
  	   {
		   return new TVShow(descField.getText(), titleField.getText(),  new MyDate(Integer.parseInt(releaseDate[0]), Integer
                   .parseInt(releaseDate[1]), Integer
                   .parseInt(releaseDate[2])), genres, tags, directorField.getText(), ((Item) data[comboBoxManage.getSelectedIndex()]).getId(), null, null, false);

  	   }
   } 
   else return null;
   
   }

   public Episode getCurrentEpisode() {

      String[] releaseDate = releaseDateField.getText().split("/");

      return new Episode(Integer.parseInt(fieldSeason.getValue().toString()),
            fieldTitle.getText(), Integer.parseInt(fieldEpisode.getValue()
                  .toString()), fieldDescription.getText(), new MyDate(
                  Integer.parseInt(releaseDate[0]),
                  Integer.parseInt(releaseDate[1]),
                  Integer.parseInt(releaseDate[2])), durationField.getText(),
            -1);
   }

   public void show(Object[] message) {
      switch (message[0].toString()) {
         case "getTVShowList":
            data = message;
            break;
         case "getAll":
        	 data = message;
        	 break;
         default:
            break;
      }
   }

   private class addItem extends JPanel {
      /**
       * Create the panel.
       */
      public addItem() {
         setLayout(new BorderLayout(0, 0));

         seasonPanel = new JPanel();
         episodePanel = new JPanel();

         mainPanel = new JPanel();
         add(mainPanel, BorderLayout.CENTER);

         titlePanel = new JPanel();
         titlePanel.setLayout(new BorderLayout(0, 0));

         JLabel lblTitle = new JLabel("Title: ");
         titlePanel.add(lblTitle, BorderLayout.WEST);

         fieldTitle = new JTextField();
         fieldTitle.setHorizontalAlignment(SwingConstants.LEFT);
         titlePanel.add(fieldTitle, BorderLayout.CENTER);
         fieldTitle.setColumns(10);

         descriptionPanel = new JPanel();
         descriptionPanel.setLayout(new BorderLayout(0, 0));

         JLabel lblDescription = new JLabel("Description: ");
         descriptionPanel.add(lblDescription, BorderLayout.NORTH);

         directorPanel = new JPanel();
         directorPanel.setLayout(new BorderLayout(0, 0));

         JLabel lblDirector = new JLabel("Director: ");
         directorPanel.add(lblDirector, BorderLayout.WEST);

         fieldDirector = new JTextField();
         fieldDirector.setColumns(10);
         directorPanel.add(fieldDirector, BorderLayout.CENTER);

         tagsPanel = new JPanel();
         tagsPanel.setLayout(new BorderLayout(0, 0));

         JLabel lblTags = new JLabel("Tags: ");
         tagsPanel.add(lblTags, BorderLayout.NORTH);

         genresPanel = new JPanel();

         showsPanel = new JPanel();
         durationPanel = new JPanel();
         releasePanel = new JPanel();

         GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
         gl_mainPanel.setHorizontalGroup(gl_mainPanel.createParallelGroup(
               Alignment.LEADING).addGroup(
               gl_mainPanel
                     .createSequentialGroup()
                     .addContainerGap()
                     .addGroup(
                           gl_mainPanel
                                 .createParallelGroup(Alignment.LEADING)
                                 .addComponent(showsPanel,
                                       GroupLayout.DEFAULT_SIZE, 240,
                                       Short.MAX_VALUE)
                                 .addComponent(tagsPanel,
                                       GroupLayout.DEFAULT_SIZE, 240,
                                       Short.MAX_VALUE)
                                 .addGroup(
                                       Alignment.LEADING,
                                       gl_mainPanel
                                             .createParallelGroup(
                                                   Alignment.TRAILING, false)
                                             .addComponent(directorPanel,
                                                   Alignment.LEADING,
                                                   GroupLayout.DEFAULT_SIZE,
                                                   GroupLayout.DEFAULT_SIZE,
                                                   Short.MAX_VALUE)
                                             .addComponent(titlePanel,
                                                   Alignment.LEADING,
                                                   GroupLayout.DEFAULT_SIZE,
                                                   240, Short.MAX_VALUE))
                                 .addComponent(durationPanel,
                                       Alignment.LEADING,
                                       GroupLayout.DEFAULT_SIZE, 240,
                                       Short.MAX_VALUE)
                                 .addComponent(releasePanel, Alignment.LEADING,
                                       GroupLayout.DEFAULT_SIZE, 240,
                                       Short.MAX_VALUE)
                                 .addComponent(seasonPanel, Alignment.LEADING,
                                       GroupLayout.DEFAULT_SIZE, 240,
                                       Short.MAX_VALUE)
                                 .addComponent(episodePanel,
                                       GroupLayout.DEFAULT_SIZE, 240,
                                       Short.MAX_VALUE)
                                 .addComponent(descriptionPanel,
                                       GroupLayout.DEFAULT_SIZE, 240,
                                       Short.MAX_VALUE))
                     .addPreferredGap(ComponentPlacement.RELATED)
                     .addComponent(genresPanel, GroupLayout.DEFAULT_SIZE, 184,
                           Short.MAX_VALUE).addContainerGap()));
         gl_mainPanel
               .setVerticalGroup(gl_mainPanel
                     .createParallelGroup(Alignment.TRAILING)
                     .addGroup(
                           gl_mainPanel
                                 .createSequentialGroup()
                                 .addContainerGap()
                                 .addGroup(
                                       gl_mainPanel
                                             .createParallelGroup(
                                                   Alignment.LEADING)
                                             .addComponent(genresPanel,
                                                   GroupLayout.DEFAULT_SIZE,
                                                   253, Short.MAX_VALUE)
                                             .addGroup(
                                                   gl_mainPanel
                                                         .createSequentialGroup()
                                                         .addComponent(
                                                               titlePanel,
                                                               GroupLayout.PREFERRED_SIZE,
                                                               GroupLayout.DEFAULT_SIZE,
                                                               GroupLayout.PREFERRED_SIZE)
                                                         .addPreferredGap(
                                                               ComponentPlacement.RELATED)
                                                         .addComponent(
                                                               directorPanel,
                                                               GroupLayout.PREFERRED_SIZE,
                                                               GroupLayout.DEFAULT_SIZE,
                                                               GroupLayout.PREFERRED_SIZE)
                                                         .addPreferredGap(
                                                               ComponentPlacement.RELATED)
                                                         .addComponent(
                                                               durationPanel,
                                                               GroupLayout.PREFERRED_SIZE,
                                                               GroupLayout.DEFAULT_SIZE,
                                                               GroupLayout.PREFERRED_SIZE)
                                                         .addPreferredGap(
                                                               ComponentPlacement.RELATED)
                                                         .addComponent(
                                                               releasePanel,
                                                               GroupLayout.PREFERRED_SIZE,
                                                               GroupLayout.DEFAULT_SIZE,
                                                               GroupLayout.PREFERRED_SIZE)
                                                         .addPreferredGap(
                                                               ComponentPlacement.RELATED)
                                                         .addComponent(
                                                               seasonPanel,
                                                               GroupLayout.PREFERRED_SIZE,
                                                               GroupLayout.DEFAULT_SIZE,
                                                               GroupLayout.PREFERRED_SIZE)
                                                         .addPreferredGap(
                                                               ComponentPlacement.RELATED)
                                                         .addComponent(
                                                               episodePanel,
                                                               GroupLayout.PREFERRED_SIZE,
                                                               GroupLayout.DEFAULT_SIZE,
                                                               GroupLayout.PREFERRED_SIZE)
                                                         .addPreferredGap(
                                                               ComponentPlacement.RELATED)
                                                         .addComponent(
                                                               descriptionPanel,
                                                               GroupLayout.PREFERRED_SIZE,
                                                               100,
                                                               GroupLayout.PREFERRED_SIZE)
                                                         .addPreferredGap(
                                                               ComponentPlacement.RELATED)
                                                         .addComponent(
                                                               tagsPanel,
                                                               GroupLayout.PREFERRED_SIZE,
                                                               60,
                                                               GroupLayout.PREFERRED_SIZE)
                                                         .addPreferredGap(
                                                               ComponentPlacement.RELATED)
                                                         .addComponent(
                                                               showsPanel,
                                                               GroupLayout.PREFERRED_SIZE,
                                                               GroupLayout.DEFAULT_SIZE,
                                                               GroupLayout.PREFERRED_SIZE)))
                                 .addGap(49)));

         showsPanel.setLayout(new BorderLayout(0, 0));

         JLabel lblTvShow = new JLabel("TV Show:");
         showsPanel.add(lblTvShow, BorderLayout.WEST);

         comboBox = new JComboBox<>();
         showsPanel.add(comboBox, BorderLayout.CENTER);
         releasePanel.setLayout(new BorderLayout(0, 0));

         lblReleaseDate = new JLabel("Release date: ");
         releasePanel.add(lblReleaseDate, BorderLayout.WEST);

         releaseDateField = new JTextField();
         releasePanel.add(releaseDateField, BorderLayout.CENTER);
         releaseDateField.setColumns(10);
         durationPanel.setLayout(new BorderLayout(0, 0));

         JLabel lblDuration = new JLabel("Duration: ");
         durationPanel.add(lblDuration, BorderLayout.WEST);

         durationField = new JTextField();
         durationPanel.add(durationField, BorderLayout.CENTER);
         durationField.setColumns(10);

         episodePanel.setLayout(new BorderLayout(0, 0));

         JLabel lblEpisode = new JLabel("Episode: ");
         episodePanel.add(lblEpisode, BorderLayout.WEST);

         fieldEpisode = new JSpinner();
         episodePanel.add(fieldEpisode, BorderLayout.CENTER);
         seasonPanel.setLayout(new BorderLayout(0, 0));

         JLabel lblSeason = new JLabel("Season: ");
         seasonPanel.add(lblSeason, BorderLayout.WEST);

         fieldSeason = new JSpinner();
         seasonPanel.add(fieldSeason, BorderLayout.CENTER);

         fieldDescription = new JTextArea();
         fieldDescription.setLineWrap(true);
         descriptionPanel.add(fieldDescription, BorderLayout.CENTER);

         fieldTags = new JTextArea();
         fieldTags.setLineWrap(true);
         tagsPanel.add(fieldTags, BorderLayout.CENTER);
         genresPanel.setLayout(new BorderLayout(0, 0));

         if (type.equals("tvshow")) {
            durationPanel.setVisible(false);
         }

         if (type.equals("episode")) {
            directorPanel.setVisible(false);
            tagsPanel.setVisible(false);
            lblDescription.setText("Resume: ");

            if (data != null) {
               for (int i = 1; i < data.length; i++) {
                  comboBox.addItem(data[i]);
               }
            }
         }

         if (!type.equals("episode")) {
            showsPanel.setVisible(false);
            seasonPanel.setVisible(false);
            episodePanel.setVisible(false);

            JLabel lblGenres = new JLabel("Genres: ");
            genresPanel.add(lblGenres, BorderLayout.NORTH);

            genresBoxPanel = new JPanel();
            genresPanel.add(genresBoxPanel, BorderLayout.CENTER);
            genresBoxPanel.setLayout(new GridLayout(0, 2, 0, 0));

            boxList = new ArrayList<>();
            boxList.add(new JCheckBox("Action"));
            boxList.add(new JCheckBox("Adventure"));
            boxList.add(new JCheckBox("Animation"));
            boxList.add(new JCheckBox("Biography"));
            boxList.add(new JCheckBox("Comedy"));
            boxList.add(new JCheckBox("Crime"));
            boxList.add(new JCheckBox("Documentary"));
            boxList.add(new JCheckBox("Drama"));
            boxList.add(new JCheckBox("Family"));
            boxList.add(new JCheckBox("Fantasy"));
            boxList.add(new JCheckBox("History"));
            boxList.add(new JCheckBox("Horror"));
            boxList.add(new JCheckBox("Mystery"));
            boxList.add(new JCheckBox("Musical"));
            boxList.add(new JCheckBox("Romance"));
            boxList.add(new JCheckBox("Sport"));
            boxList.add(new JCheckBox("Thriller"));
            boxList.add(new JCheckBox("Sci-Fi"));
            boxList.add(new JCheckBox("War"));
            boxList.add(new JCheckBox("Western"));

            for (JCheckBox box : boxList)
               genresBoxPanel.add(box);
         }
         mainPanel.setLayout(gl_mainPanel);

         bottomPanel = new JPanel();
         add(bottomPanel, BorderLayout.SOUTH);
         bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

         JButton btnGoBack = new JButton("Go Back");
         btnGoBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               menu();
            }
         });

         bottomPanel.add(btnGoBack);

         JButton btnAdd = new JButton("Add");
         bottomPanel.add(btnAdd);

         btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

               if (type.equals("episode")) controller.execute("AddEpisode");
               else controller.execute("AddItem");
            }
         });

      }
   }
   
   @SuppressWarnings("unused")
   private class ManageItems extends JPanel {
		private JPanel panel_1;
		private JPanel panel_2;
		private GridBagConstraints gbc_panel_2;
		private JCheckBox actionGenre;
		
		private JCheckBox adventureGenre;
		private JCheckBox animationGenre;
		private JCheckBox biographyGenre;
		private JCheckBox comedyGenre;
		private JCheckBox crimeGenre;
		private JCheckBox documentaryGenre;
		private JCheckBox dramaGenre;
		private JCheckBox familyGenre;
		private JCheckBox fantasyGenre;
		private JCheckBox historyGenre;
		private JCheckBox horrorGenre;
		private JCheckBox mysteryGenre;
		private JCheckBox musicalGenre;
		private JCheckBox romanceGenre;
		private JCheckBox sportGenre;
		private JCheckBox thrillerGenre;
		private JCheckBox scifiGenre;
		private JCheckBox warGenre;
		private JCheckBox westernGenre;
		public ManageItems()
		{
			
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{101, 155, 0, 220, 0};
			gridBagLayout.rowHeights = new int[]{0, 0, 377, 0, 0, 0};
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
			setLayout(gridBagLayout);
			
			JLabel lblSelectAnItem = new JLabel("Select an item");
			GridBagConstraints gbc_lblSelectAnItem = new GridBagConstraints();
			gbc_lblSelectAnItem.anchor = GridBagConstraints.WEST;
			gbc_lblSelectAnItem.insets = new Insets(10, 5, 5, 5);
			gbc_lblSelectAnItem.gridx = 0;
			gbc_lblSelectAnItem.gridy = 0;
			add(lblSelectAnItem, gbc_lblSelectAnItem);
			
			comboBoxManage = new JComboBox<String>();
			comboBoxManage.addItem("Choose an item...");
			
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.gridwidth = 3;
			gbc_comboBox.insets = new Insets(10, 5, 5, 10);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 0;
			gbc_comboBox.weightx = 1.0;
			
			for(int i = 1; i < data.length; i++)
				comboBoxManage.addItem( ((Item) data[i]).getTitle());
			
			add(comboBoxManage, gbc_comboBox);
			
			comboBoxManage.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(comboBoxManage.getSelectedIndex() != 0)
						setData();
				}
				
			});
			
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 2;
			gbc_panel.insets = new Insets(5, 5, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 2;
			add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			
			JLabel lblTitle = new JLabel("Title");
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.anchor = GridBagConstraints.WEST;
			gbc_lblTitle.insets = new Insets(5, 5, 5, 10);
			gbc_lblTitle.gridx = 0;
			gbc_lblTitle.gridy = 1;
			panel.add(lblTitle, gbc_lblTitle);
			
			titleField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(5, 0, 5, 0);
			gbc_textField.gridwidth = 2;
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy = 1;
			panel.add(titleField, gbc_textField);
			titleField.setColumns(10);
			
			JLabel lblDirector = new JLabel("Director");
			GridBagConstraints gbc_lblDirector = new GridBagConstraints();
			gbc_lblDirector.anchor = GridBagConstraints.WEST;
			gbc_lblDirector.insets = new Insets(5, 5, 5, 10);
			gbc_lblDirector.gridx = 0;
			gbc_lblDirector.gridy = 2;
			panel.add(lblDirector, gbc_lblDirector);
			
			directorField = new JTextField();
			directorField.setColumns(10);
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(5, 0, 5, 0);
			gbc_textField_1.gridwidth = 2;
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 1;
			gbc_textField_1.gridy = 2;
			panel.add(directorField, gbc_textField_1);
			
			JLabel lblReleaseDate = new JLabel("Release date");
			GridBagConstraints gbc_lblReleaseDate = new GridBagConstraints();
			gbc_lblReleaseDate.anchor = GridBagConstraints.WEST;
			gbc_lblReleaseDate.insets = new Insets(5, 5, 5, 10);
			gbc_lblReleaseDate.gridx = 0;
			gbc_lblReleaseDate.gridy = 3;
			panel.add(lblReleaseDate, gbc_lblReleaseDate);
			
			releaseField = new JTextField();
			GridBagConstraints gbc_textField_2 = new GridBagConstraints();
			gbc_textField_2.anchor = GridBagConstraints.WEST;
			gbc_textField_2.insets = new Insets(5, 0, 5, 0);
			gbc_textField_2.gridwidth = 2;
			gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_2.gridx = 1;
			gbc_textField_2.gridy = 3;
			panel.add(releaseField, gbc_textField_2);
			releaseField.setColumns(10);
			
			JLabel lblDuration = new JLabel("Duration");
			GridBagConstraints gbc_lblDuration = new GridBagConstraints();
			gbc_lblDuration.anchor = GridBagConstraints.WEST;
			gbc_lblDuration.insets = new Insets(5, 5, 5, 10);
			gbc_lblDuration.gridx = 0;
			gbc_lblDuration.gridy = 4;
			panel.add(lblDuration, gbc_lblDuration);
			
			fieldDuration = new JTextField();
			GridBagConstraints gbc_textField_3 = new GridBagConstraints();
			gbc_textField_3.gridwidth = 2;
			gbc_textField_3.insets = new Insets(5, 0, 5, 0);
			gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_3.gridx = 1;
			gbc_textField_3.gridy = 4;
			panel.add(fieldDuration, gbc_textField_3);
			fieldDuration.setColumns(10);
			
			JLabel lblDescription = new JLabel("Description");
			GridBagConstraints gbc_lblDescription = new GridBagConstraints();
			gbc_lblDescription.anchor = GridBagConstraints.WEST;
			gbc_lblDescription.insets = new Insets(5, 5, 5, 10);
			gbc_lblDescription.gridx = 0;
			gbc_lblDescription.gridy = 5;
			panel.add(lblDescription, gbc_lblDescription);
			
			descField = new JTextArea();
			descField.setLineWrap(true);
			GridBagConstraints gbc_textArea = new GridBagConstraints();
			gbc_textArea.gridwidth = 3;
			gbc_textArea.insets = new Insets(5, 0, 5, 0);
			gbc_textArea.fill = GridBagConstraints.BOTH;
			gbc_textArea.gridx = 0;
			gbc_textArea.gridy = 6;
			panel.add(new JScrollPane(descField), gbc_textArea);
			
			JLabel lblTags = new JLabel("Tags");
			GridBagConstraints gbc_lblTags = new GridBagConstraints();
			gbc_lblTags.anchor = GridBagConstraints.WEST;
			gbc_lblTags.insets = new Insets(5, 5, 5, 10);
			gbc_lblTags.gridx = 0;
			gbc_lblTags.gridy = 7;
			panel.add(lblTags, gbc_lblTags);
			
			tagField = new JTextArea();
			tagField.setLineWrap(true);
			GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
			gbc_textArea_1.gridwidth = 3;
			gbc_textArea_1.insets = new Insets(5, 0, 5, 0);
			gbc_textArea_1.fill = GridBagConstraints.BOTH;
			gbc_textArea_1.gridx = 0;
			gbc_textArea_1.gridy = 8;
			panel.add(new JScrollPane(tagField), gbc_textArea_1);
			
			panel_1 = new JPanel();
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.gridwidth = 2;
			gbc_panel_1.insets = new Insets(5, 10, 5, 0);
			gbc_panel_1.fill = GridBagConstraints.BOTH;
			gbc_panel_1.gridx = 2;
			gbc_panel_1.gridy = 2;
			add(panel_1, gbc_panel_1);
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[]{0, 0};
			gbl_panel_1.rowHeights = new int[]{0, 0, 0};
			gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			panel_1.setLayout(gbl_panel_1);
			
			JLabel lblGenres = new JLabel("Genres");
			GridBagConstraints gbc_lblGenres = new GridBagConstraints();
			gbc_lblGenres.anchor = GridBagConstraints.WEST;
			gbc_lblGenres.insets = new Insets(5, 5, 5, 0);
			gbc_lblGenres.gridx = 0;
			gbc_lblGenres.gridy = 0;
			panel_1.add(lblGenres, gbc_lblGenres);
			
			panel_2 = new JPanel();
			gbc_panel_2 = new GridBagConstraints();
			gbc_panel_2.insets = new Insets(5, 5, 5, 5);
			gbc_panel_2.fill = GridBagConstraints.BOTH;
			gbc_panel_2.gridx = 0;
			gbc_panel_2.gridy = 1;
			panel_1.add(panel_2, gbc_panel_2);
	        panel_2.setLayout(new GridLayout(0, 2, 0, 0));
	        
	        JPanel panel_3 = new JPanel();
	        
	        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
	        gbc_panel_3.gridwidth = 4;
	        gbc_panel_3.insets = new Insets(5, 5, 5, 5);
	        gbc_panel_3.fill = GridBagConstraints.BOTH;
	        gbc_panel_3.anchor = GridBagConstraints.CENTER;
	        gbc_panel_3.gridx = 0;
	        gbc_panel_3.gridy = 3;
	        add(panel_3, gbc_panel_3);
	        
	        JButton btnGoBack = new JButton("Go back");
	        panel_3.add(btnGoBack);
	        
	        btnGoBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
					int choice = JOptionPane.showConfirmDialog(null, "Any unsaved changes will be discarded. Continue?");
					
					if(choice == JOptionPane.YES_OPTION)
					{
						menu();
					}
	            }
	         });
	        
	        JButton btnSaveChanges = new JButton("Save changes");
	        panel_3.add(btnSaveChanges);
	        
	        btnSaveChanges.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					controller.execute("SaveChanges");
				}
	        	
	        });
			
	        boxListManage = new ArrayList<>();
	        boxListManage.add((actionGenre = new JCheckBox("Action")));
	        boxListManage.add((adventureGenre = new JCheckBox("Adventure")));
	        boxListManage.add((animationGenre = new JCheckBox("Animation")));
	        boxListManage.add((biographyGenre = new JCheckBox("Biography")));
	        boxListManage.add((comedyGenre = new JCheckBox("Comedy")));
	        boxListManage.add((crimeGenre = new JCheckBox("Crime")));
	        boxListManage.add((documentaryGenre = new JCheckBox("Documentary")));
	        boxListManage.add((dramaGenre = new JCheckBox("Drama")));
	        boxListManage.add((familyGenre = new JCheckBox("Family")));
	        boxListManage.add((fantasyGenre = new JCheckBox("Fantasy")));
	        boxListManage.add((historyGenre = new JCheckBox("History")));
	        boxListManage.add((horrorGenre = new JCheckBox("Horror")));
	        boxListManage.add((mysteryGenre = new JCheckBox("Mystery")));
	        boxListManage.add((musicalGenre = new JCheckBox("Musical")));
	        boxListManage.add((romanceGenre = new JCheckBox("Romance")));
	        boxListManage.add((sportGenre = new JCheckBox("Sport")));
	        boxListManage.add((thrillerGenre = new JCheckBox("Thriller")));
	        boxListManage.add((scifiGenre = new JCheckBox("Sci-Fi")));
	        boxListManage.add((warGenre = new JCheckBox("War")));
	        boxListManage.add((westernGenre = new JCheckBox("Western")));

//	        for (JCheckBox box : boxList)
//	        	panel_2.add(box);
	        
	        for(int i = 0; i < boxListManage.size(); i++)
	        	panel_2.add(boxListManage.get(i));
	        
	        titleField.setEnabled(false);
	        directorField.setEnabled(false);
	        
	        releaseField.setEnabled(false);
	        fieldDuration.setEnabled(false);
	        descField.setEnabled(false);
	        tagField.setEnabled(false);
	        
			
		}
		
		public void setData()
		{
	        titleField.setEnabled(false);
	        directorField.setEnabled(false);
	        
	        releaseField.setEnabled(false);
	        fieldDuration.setEnabled(false);
	        descField.setEnabled(false);
	        tagField.setEnabled(false);
	        
			int selectedIndex = comboBoxManage.getSelectedIndex();
			Item item = (Item) data[selectedIndex];
			
			if(item instanceof Movie)
			{
				titleField.setEnabled(true);
				titleField.setText(item.getTitle());
				
				directorField.setEnabled(true);
				directorField.setText(item.getDirector());
				
				releaseField.setEnabled(true);
				releaseField.setText(item.getReleaseDate().getDay() + "/" + item.getReleaseDate().getMonth() + "/" + item.getReleaseDate().getYear());
				
				fieldDuration.setEnabled(true);
				fieldDuration.setText( ( (Movie) item).getDuration());
				
				descField.setEnabled(true);
				descField.setText(item.getDescription());
				
				tagField.setEnabled(true);
				String tags = "";
				
				for(int i = 0; i < item.getTags().size(); i++)
				{
					tags += item.getTags().get(i);
					
					if(i != item.getTags().size()-1)
						tags += ", ";
				}
				
				tagField.setText(tags);
		        
		        ArrayList<String> genres = item.getGenres();
		        
		        for(int i = 0; i < boxListManage.size(); i++)
		        {
		        	if(genres.contains(boxListManage.get(i).getText()))
		        		boxListManage.get(i).setSelected(true);
		        	else
		        		boxListManage.get(i).setSelected(false);
		        }
		        
			}
			else
			{
				fieldDuration.setText("");
				titleField.setEnabled(true);
				titleField.setText(item.getTitle());
				
				directorField.setEnabled(true);
				directorField.setText(item.getDirector());
				
				releaseField.setEnabled(true);
				releaseField.setText(item.getReleaseDate().getDay() + "/" + item.getReleaseDate().getMonth() + "/" + item.getReleaseDate().getYear());				
				descField.setEnabled(true);
				descField.setText(item.getDescription());
				
				tagField.setEnabled(true);
				String tags = "";
				
				for(int i = 0; i < item.getTags().size(); i++)
				{
					tags += item.getTags().get(i);
					
					if(i != item.getTags().size()-1)
						tags += ", ";
				}
				
				tagField.setText(tags);
		        
		        ArrayList<String> genres = item.getGenres();
		        
		        for(int i = 0; i < boxListManage.size(); i++)
		        {
		        	if(genres.contains(boxListManage.get(i).getText()))
		        		boxListManage.get(i).setSelected(true);
		        	else
		        		boxListManage.get(i).setSelected(false);
		        }
			}
			
		}
	}

}
