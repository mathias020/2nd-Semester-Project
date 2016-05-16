package MovieCorner.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import utility.collection.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import MovieCorner.controller.Controller;
import MovieCorner.model.Comment;
import MovieCorner.model.Episode;
import MovieCorner.model.Item;
import MovieCorner.model.Movie;
import MovieCorner.model.TVShow;
import MovieCorner.model.User;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements MovieCornerView {

   private boolean adminRight;
   private JTextField txtSearch;
   private JTable table;
   private ButtonGroup searchButtons;
   private JRadioButton rdbtnMovie, rdbtnTVShow, rdbtnUser;
   private JPanel contentPane, headerPanel, leftPanel, centerPanel,
         userInfoPanel, searchPanel, menuPanel, radioButtonPanel;
   private JButton btnSettings, btnSearch, btnNotifications, btnSuggestions,
         btnMoviesTv, btnHistory, btnFavorites, btnFriends;
   private JLabel logoLabel, lblLoggedInAs, lblEmail;
   private GridBagLayout gbl_radioButtonPanel, gbl_menuPanel;
   private GridBagConstraints gbc_rdbtnMovie, gbc_rdbtnTvShow, gbc_rdbtnUser,
         gbc_btnNotifications, gbc_btnSuggestions, gbc_btnMoviesTv,
         gbc_btnHistory, gbc_Favorites;
   private JScrollPane scrollPane;
   private DefaultTableModel tableModel;
   private Object[][] rowData;
   private Object[] columnNames;
   private Object[] currentData;

   private Item currentItem;
   private Episode currentEpisode;

   private Controller controller;

   private UserFrame userFrame;
   private boolean userFrameOpened;
   private itemFrame itemFrame;
   private boolean itemFrameOpened;
   private JFrame settingsFrame;

   private LoadingFrame loadingFrame;

   /**
    * Create the frame.
    */
   public MainFrame() {

      adminRight = false;

      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch (Throwable e) {
         e.printStackTrace();
      }

      setupFrame();
      createComponents();
      initializeComponents();
      registerEventHandlers();
      addComponentsToFrame();
   }

   private void setupGridBags() {
      // Radio button panel constraints
      gbl_radioButtonPanel.columnWidths = new int[] { 53, 0 };
      gbl_radioButtonPanel.rowHeights = new int[] { 23, 23, 23, 0 };
      gbl_radioButtonPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
      gbl_radioButtonPanel.rowWeights = new double[] { 0.0, 0.0, 0.0,
            Double.MIN_VALUE };

      // Radio button movie constrains
      gbc_rdbtnMovie.anchor = GridBagConstraints.NORTHWEST;
      gbc_rdbtnMovie.insets = new Insets(0, 0, 5, 0);
      gbc_rdbtnMovie.gridx = 0;
      gbc_rdbtnMovie.gridy = 0;

      // Radio button tv show constrains
      gbc_rdbtnTvShow.anchor = GridBagConstraints.NORTHWEST;
      gbc_rdbtnTvShow.insets = new Insets(0, 0, 5, 0);
      gbc_rdbtnTvShow.gridx = 0;
      gbc_rdbtnTvShow.gridy = 1;

      // Radio button user constrains
      gbc_rdbtnUser.anchor = GridBagConstraints.NORTHWEST;
      gbc_rdbtnUser.gridx = 0;
      gbc_rdbtnUser.gridy = 2;

      // Menu panel layout constrains
      gbl_menuPanel.columnWidths = new int[] { 91, 0 };
      gbl_menuPanel.rowHeights = new int[] { 23, 0, 0, 0, 0, 0 };
      gbl_menuPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
      gbl_menuPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
            Double.MIN_VALUE };

      // Menu notification button constrains
      gbc_btnNotifications.anchor = GridBagConstraints.NORTH;
      gbc_btnNotifications.fill = GridBagConstraints.HORIZONTAL;
      gbc_btnNotifications.insets = new Insets(0, 0, 5, 5);
      gbc_btnNotifications.gridx = 0;
      gbc_btnNotifications.gridy = 0;

      // Menu suggestions button constrains
      gbc_btnSuggestions.anchor = GridBagConstraints.NORTH;
      gbc_btnSuggestions.fill = GridBagConstraints.HORIZONTAL;
      gbc_btnSuggestions.insets = new Insets(0, 0, 5, 5);
      gbc_btnSuggestions.gridx = 0;
      gbc_btnSuggestions.gridy = 1;

      // Menu movies and tv shows button constrains
      gbc_btnMoviesTv.anchor = GridBagConstraints.NORTH;
      gbc_btnMoviesTv.fill = GridBagConstraints.HORIZONTAL;
      gbc_btnMoviesTv.insets = new Insets(0, 0, 5, 5);
      gbc_btnMoviesTv.gridx = 0;
      gbc_btnMoviesTv.gridy = 2;

      // Menu history button constrains
      gbc_btnHistory.anchor = GridBagConstraints.NORTH;
      gbc_btnHistory.fill = GridBagConstraints.HORIZONTAL;
      gbc_btnHistory.insets = new Insets(0, 0, 5, 5);
      gbc_btnHistory.gridx = 0;
      gbc_btnHistory.gridy = 3;

      // Menu favorites button constrains
      gbc_Favorites.anchor = GridBagConstraints.NORTH;
      gbc_Favorites.fill = GridBagConstraints.HORIZONTAL;
      gbc_Favorites.insets = new Insets(0, 0, 5, 5);
      gbc_Favorites.gridx = 0;
      gbc_Favorites.gridy = 4;

      // Set layouts
      radioButtonPanel.setLayout(gbl_radioButtonPanel);
      menuPanel.setLayout(gbl_menuPanel);

      // Add constrains to layouts
      radioButtonPanel.add(rdbtnMovie, gbc_rdbtnMovie);
      radioButtonPanel.add(rdbtnTVShow, gbc_rdbtnTvShow);
      radioButtonPanel.add(rdbtnUser, gbc_rdbtnUser);
      menuPanel.add(btnNotifications, gbc_btnNotifications);
      menuPanel.add(btnSuggestions, gbc_btnSuggestions);
      menuPanel.add(btnMoviesTv, gbc_btnMoviesTv);
      menuPanel.add(btnHistory, gbc_btnHistory);
      menuPanel.add(btnFavorites, gbc_Favorites);

   }

   private void addComponentsToFrame() {

      // Header panel
      headerPanel.add(logoLabel, BorderLayout.WEST);
      headerPanel.add(userInfoPanel, BorderLayout.CENTER);
      headerPanel.add(btnFriends, BorderLayout.EAST);
      
      // User Info panel
      userInfoPanel.add(lblLoggedInAs, BorderLayout.NORTH);
      userInfoPanel.add(lblEmail, BorderLayout.CENTER);

      // Search panel
      searchPanel.add(txtSearch, BorderLayout.NORTH);
      searchPanel.add(btnSearch, BorderLayout.SOUTH);
      searchPanel.add(radioButtonPanel, BorderLayout.CENTER);

      // Left panel
      leftPanel.add(searchPanel, BorderLayout.NORTH);
      leftPanel.add(menuPanel, BorderLayout.CENTER);

      // Center panel
      centerPanel.add(scrollPane, BorderLayout.CENTER);

      // Content panel
      contentPane.add(leftPanel, BorderLayout.WEST);
      contentPane.add(headerPanel, BorderLayout.NORTH);
      contentPane.add(centerPanel, BorderLayout.CENTER);

      setContentPane(contentPane);
   }

   private void setupFrame() {
      setTitle("MovieCorner");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setMinimumSize(new Dimension(640, 470));
      setLocationRelativeTo(null);
   }

   private void initializeComponents() {

      // GridBag layout setup
      setupGridBags();

      // Table data setup
      // setupTableData(rowData, "notifications");

      // Setting texts and labels
      txtSearch.setText("");
      lblLoggedInAs.setText("Logged in as:");
      lblEmail.setText("bob@gmail.com");

      // Setting images
      logoLabel
            .setIcon(new ImageIcon(getClass().getResource("/images/top.png")));

      // Setting fonts
      lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
      btnNotifications.setFont(new Font("Tahoma", Font.PLAIN, 15));
      btnSuggestions.setFont(new Font("Tahoma", Font.PLAIN, 15));
      btnMoviesTv.setFont(new Font("Tahoma", Font.PLAIN, 15));
      btnHistory.setFont(new Font("Tahoma", Font.PLAIN, 15));
      btnFavorites.setFont(new Font("Tahoma", Font.PLAIN, 15));
      btnFriends.setFont(new Font("Tahoma", Font.PLAIN, 14));

      // Setting borders
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      searchPanel.setBorder(new TitledBorder(null, "Search",
            TitledBorder.LEADING, TitledBorder.TOP, null, null));
      radioButtonPanel.setBorder(new TitledBorder(null, "For",
            TitledBorder.LEADING, TitledBorder.TOP, null, null));
      centerPanel.setBorder(new TitledBorder(null, "Notifications",
            TitledBorder.LEADING, TitledBorder.TOP, null, null));
      menuPanel.setBorder(new TitledBorder(null, "Menu", TitledBorder.LEADING,
            TitledBorder.TOP, null, null));

      // Setting layouts
      contentPane.setLayout(new BorderLayout(0, 0));
      headerPanel.setLayout(new BorderLayout(0, 0));
      userInfoPanel.setLayout(new BorderLayout(0, 0));
      leftPanel.setLayout(new BorderLayout(0, 0));
      searchPanel.setLayout(new BorderLayout(0, 0));
      centerPanel.setLayout(new BorderLayout(0, 0));

      // Search Radio buttons to ButtonGroup
      searchButtons.add(rdbtnMovie);
      searchButtons.add(rdbtnTVShow);
      searchButtons.add(rdbtnUser);

      // Table setup
      table.setModel(tableModel);

   }

   private void createComponents() {

      // Table
      columnNames = new Object[3];
      rowData = new Object[0][3];

      // Table Data
      columnNames[0] = "Title";
      columnNames[1] = "Type";
      columnNames[2] = "Release Date";

      // Panels
      contentPane = new JPanel();
      headerPanel = new JPanel();
      userInfoPanel = new JPanel();
      leftPanel = new JPanel();
      searchPanel = new JPanel();
      radioButtonPanel = new JPanel();
      menuPanel = new JPanel();
      centerPanel = new JPanel();

      // Button Group
      searchButtons = new ButtonGroup();

      // Radio Buttons
      rdbtnMovie = new JRadioButton("Movie");
      rdbtnTVShow = new JRadioButton("TV Show");
      rdbtnUser = new JRadioButton("User");

      // Buttons
      btnSettings = new JButton("Control Panel");
      btnSearch = new JButton("Search");
      btnNotifications = new JButton("Notifications");
      btnSuggestions = new JButton("Suggestions");
      btnMoviesTv = new JButton("Movies & TV Shows");
      btnHistory = new JButton("History");
      btnFavorites = new JButton("Favorites");
      btnFriends = new JButton("Friends");

      // Text Fields
      txtSearch = new JTextField();

      // Labels
      logoLabel = new JLabel();
      lblLoggedInAs = new JLabel();
      lblEmail = new JLabel();

      // Tables
      table = new JTable(rowData, columnNames);

      // Table models
      tableModel = new MyModel(rowData, columnNames);

      // Layouts
      gbl_radioButtonPanel = new GridBagLayout();
      gbl_menuPanel = new GridBagLayout();

      // Constraints
      gbc_rdbtnMovie = new GridBagConstraints();
      gbc_rdbtnTvShow = new GridBagConstraints();
      gbc_rdbtnUser = new GridBagConstraints();
      gbc_btnNotifications = new GridBagConstraints();
      gbc_btnSuggestions = new GridBagConstraints();
      gbc_btnMoviesTv = new GridBagConstraints();
      gbc_btnHistory = new GridBagConstraints();
      gbc_Favorites = new GridBagConstraints();

      // Scroll panes
      scrollPane = new JScrollPane(table);
   }

   private void setupTableData(Object[][] newData, Object[] columns) {

      columnNames = new Object[3];
      if (columns == null) {
         columnNames[0] = "Title";
         columnNames[1] = "Type";
         columnNames[2] = "Release Date";
      } else {
         if (columns.length > 3) columnNames = new Object[columns.length];

         columnNames = columns;
      }

      tableModel.setColumnIdentifiers(columnNames);
      if (tableModel != null) {
         for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
            tableModel.removeRow(i);

         for (int i = 0; i < newData.length; i++) {
            tableModel.addRow(newData[i]);
         }
      }

      if (columns == null) {
         table.getColumnModel().getColumn(0)
               .setCellRenderer(new MultiColorRenderer());
         table.getColumnModel().getColumn(1)
               .setCellRenderer(new MultiColorRenderer(true));
         table.getColumnModel().getColumn(2)
               .setCellRenderer(new MultiColorRenderer());
      } else {
         for (int i = 0; i < columns.length; i++)
            table.getColumnModel().getColumn(i)
                  .setCellRenderer(new MultiColorRenderer());
      }

   }

   private void registerEventHandlers() {
      // Details
      table.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            int mouseClick = e.getClickCount();
            if (mouseClick > 1) {
               Point origin = e.getPoint();
               int row = table.rowAtPoint(origin);
               viewDetails(row);
            }
         }
      });

      // Button settings
      btnSettings.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (settingsFrame == null) {
               settingsFrame = new settingsFrame();
               ((settingsFrame) settingsFrame).start(controller);
               settingsFrame.addWindowListener(new WindowListener() {

                  @Override
                  public void windowActivated(WindowEvent arg0) {
                     // TODO Auto-generated method stub

                  }

                  @Override
                  public void windowClosed(WindowEvent e) {
                     settingsFrame = null;

                  }

                  @Override
                  public void windowClosing(WindowEvent e) {
                     // TODO Auto-generated method stub

                  }

                  @Override
                  public void windowDeactivated(WindowEvent e) {
                     // TODO Auto-generated method stub

                  }

                  @Override
                  public void windowDeiconified(WindowEvent e) {
                     // TODO Auto-generated method stub

                  }

                  @Override
                  public void windowIconified(WindowEvent e) {
                     // TODO Auto-generated method stub

                  }

                  @Override
                  public void windowOpened(WindowEvent e) {
                     // TODO Auto-generated method stub

                  }

               });
            }
         }
      });

      // Button Friends
      btnFriends.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            centerPanel.setBorder(new TitledBorder(null, btnFriends
                  .getText(), TitledBorder.LEADING, TitledBorder.TOP, null,
                  null));
            setButtonStatus(btnFriends);
            startLoading("Loading friends, please wait...");
            SwingUtilities.invokeLater(new Runnable() {

               @Override
               public void run() {
                  controller.execute("getFriends");
                  finishLoading();
               }

            });

         }
      });
      
      // Button Search
      btnSearch.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            centerPanel.setBorder(new TitledBorder(null, "Search results",
                  TitledBorder.LEADING, TitledBorder.TOP, null, null));
            setButtonStatus(null);
            startLoading("Searching MovieCorner database...");
            SwingUtilities.invokeLater(new Runnable() {

               @Override
               public void run() {
                  if (rdbtnMovie.isSelected()) {
                     controller.execute("Search for movie");
                  } else if (rdbtnTVShow.isSelected()) {
                     controller.execute("Search for tvshow");
                  } else if (rdbtnUser.isSelected()) {
                     controller.execute("Search for user");
                  } else {
                     controller.execute("Search");
                  }
                  finishLoading();
               }

            });

         }
      });

      // Button Notifications
      btnNotifications.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            centerPanel.setBorder(new TitledBorder(null, btnNotifications
                  .getText(), TitledBorder.LEADING, TitledBorder.TOP, null,
                  null));
            setButtonStatus(btnNotifications);
            startLoading("Loading notifications, please wait...");
            SwingUtilities.invokeLater(new Runnable() {

               @Override
               public void run() {
                  controller.execute("allNotifications");
                  finishLoading();
               }

            });

         }
      });

      // Button Suggestions
      btnSuggestions.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            centerPanel.setBorder(new TitledBorder(null, btnSuggestions
                  .getText(), TitledBorder.LEADING, TitledBorder.TOP, null,
                  null));
            setButtonStatus(btnSuggestions);
            startLoading("Loading suggestions, please wait...");
            SwingUtilities.invokeLater(new Runnable() {

               @Override
               public void run() {
                  controller.execute("Suggestions");
                  finishLoading();
               }

            });

         }
      });

      // Button Movies & TV Shows
      btnMoviesTv.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            centerPanel.setBorder(new TitledBorder(null, btnMoviesTv.getText(),
                  TitledBorder.LEADING, TitledBorder.TOP, null, null));
            setButtonStatus(btnMoviesTv);
            startLoading("Loading movies and TV shows, please wait...");
            SwingUtilities.invokeLater(new Runnable() {

               @Override
               public void run() {
                  controller.execute("allMoviesAndTVShows");
                  finishLoading();
               }

            });

         }
      });

      // Button History
      btnHistory.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            centerPanel.setBorder(new TitledBorder(null, btnHistory.getText(),
                  TitledBorder.LEADING, TitledBorder.TOP, null, null));
            setButtonStatus(btnHistory);
            startLoading("Loading history, please wait...");
            SwingUtilities.invokeLater(new Runnable() {

               @Override
               public void run() {
                  controller.execute("getWatchedList");
                  finishLoading();
               }

            });

         }
      });

      // Button Favorites
      btnFavorites.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            centerPanel.setBorder(new TitledBorder(null,
                  btnFavorites.getText(), TitledBorder.LEADING,
                  TitledBorder.TOP, null, null));
            setButtonStatus(btnFavorites);
            startLoading("Loading favorites, please wait...");
            SwingUtilities.invokeLater(new Runnable() {

               @Override
               public void run() {
                  controller.execute("getFavoritesList");
                  finishLoading();
               }

            });

         }
      });
   }

   @Override
   public String get(String what) {

      if (what.equals("Search for")) return txtSearch.getText();
      else if (what.equals("review")) return (String) itemFrame.get("review");
      else if (what.equals("commentID")) return (String) itemFrame
            .get("commentID");
      else if (what.equals("rating")) return ((Integer) itemFrame.get("rating"))
            .toString();
      else if (what.equals("userIDforWatched") || what.equals("emailOfUser")
            || what.equals("nameOfUser") || what.equals("privilegesOfUser")) {
         return userFrame.get(what).toString();
      } else return JOptionPane.showInputDialog(what);

   }

   @Override
   public void start(Controller controller) {
      this.controller = controller;

      controller.execute("getEmail");
      controller.execute("isUserAdmin?");
      controller.execute("programSetup");
      controller.execute("allNotifications");
      setButtonStatus(btnNotifications);
   }

   public boolean isAdmin() {
      return adminRight;
   }

   @Override
   public void show(String message) {
      if (message.equals("userIsAdmin")) {
         adminRight = true;
         
         JPanel settingsAndFriendPanel = new JPanel(new BorderLayout());
         settingsAndFriendPanel.add(btnSettings, BorderLayout.CENTER);
         settingsAndFriendPanel.add(btnFriends, BorderLayout.SOUTH);
         
         headerPanel.remove(btnFriends);
         headerPanel.add(settingsAndFriendPanel, BorderLayout.EAST);
      } else if (message.equals("reviewAdded")) {
         itemFrame.reviewAdded();
      } else if (message.equals("isFriend")) {
         userFrame.show("isFriend");
      } else JOptionPane.showMessageDialog(null, message);
   }

   private void setButtonStatus(JButton buttonToDisable) {
	  
      btnNotifications.setEnabled(true);
      btnSuggestions.setEnabled(true);
      btnMoviesTv.setEnabled(true);
      btnHistory.setEnabled(true);
      btnFavorites.setEnabled(true);
      btnFriends.setEnabled(true);
      
      if(buttonToDisable != null)
    	  buttonToDisable.setEnabled(false);
   }

   public void show(Object[] message) {

      if (!message[0].toString().equals("allCommentsForItem")
            && !message[0].toString().equals("watchedEpisodes")
            && !message[0].toString().equals("watchedMovies")
            && !message[0].toString().equals("favorites")
            && !message[0].toString().equals("episodeReviews")
            && !message[0].toString().equals("episodeRating")
            && !message[0].toString().equals("movieReviews")
            && !message[0].toString().equals("movieRating")
            && !message[0].toString().equals("watchedByUserID")
            && !message[0].toString().equals("getAll")) currentData = message;
      
      
      switch (message[0].toString()) {
         case "watchedByUserID":
            if (userFrame != null) {
               userFrame.show(message);
            }
            break;
         case "movieReviews":
            if (itemFrame != null) {
               itemFrame.show(message);
            }
            break;
         case "movieRating":
            if (itemFrame != null) 
               if(message.length == 2) 
                  itemFrame.show(message);
            break;

         case "episodeReviews":
            if (itemFrame != null) {
               itemFrame.show(message);
            }
            break;
         case "episodeRating":
            if (itemFrame != null) 
               if(message.length == 2)
                  itemFrame.show(message);
            break;
         case "suggestions":
            Object[][] suggestionData = new Object[message.length - 1][3];

            for (int i = 1; i < message.length; i++) {
               if (message[i] == null) continue;

               suggestionData[i - 1][0] = ((Item) message[i]).getTitle();

               if (message[i] instanceof Movie) suggestionData[i - 1][1] = "Movie";
               else if (message[i] instanceof TVShow) suggestionData[i - 1][1] = "TVShow";
               else suggestionData[i - 1][1] = "?";

               suggestionData[i - 1][2] = ((Item) message[i]).getReleaseDate();
            }

            setupTableData(suggestionData, null);
            break;
         case "getTVShowList":
            ((settingsFrame) settingsFrame).show(message);
            break;
         case "getAll":
            ((settingsFrame) settingsFrame).show(message);
            break;
         case "email":
            lblEmail.setText(message[1].toString());
            break;
         case "allNotifications":
            Object[][] newData4 = new Object[message.length - 1][4];

            for (int i = 1; i < message.length; i++) {
               newData4[i - 1][0] = ((Object[]) message[i])[0].toString();
               newData4[i - 1][1] = ((Object[]) message[i])[1].toString();
               newData4[i - 1][2] = ((Object[]) message[i])[2].toString();
               newData4[i - 1][3] = ((Object[]) message[i])[3].toString();
            }

            setupTableData(newData4, new Object[] { "Title", "Message",
                  "Release date", "Type / Show title" });
            break;
         case "getFriends":
            Object[][] newData5 = new Object[message.length - 1][3];

            for (int i = 1; i < message.length; i++) {
               newData5[i - 1][0] = ((User) message[i]).getNickname();
               newData5[i - 1][1] = ((User) message[i]).getEmail();
               newData5[i - 1][2] = ((User) message[i]).isAdmin() ? "Administrator"
                     : "User";
            }

            setupTableData(newData5, new Object[] { "Nickname", "Email",
                  "Status" });
            break;
         case "search":
            Object[][] searchData = new Object[message.length - 1][3];

            if (message.length > 1 && message[1] instanceof User) {
               for (int i = 1; i < message.length; i++) {
                  searchData[i - 1][0] = ((User) message[i]).getNickname();
                  searchData[i - 1][1] = ((User) message[i]).getEmail();
                  searchData[i - 1][2] = ((User) message[i]).isAdmin() ? "Administrator"
                        : "User";
               }

               setupTableData(searchData, new Object[] { "Nickname", "E-mail",
                     "Account status" });
            } else {
               for (int i = 1; i < message.length; i++) {

                  searchData[i - 1][0] = ((Item) message[i]).getTitle();

                  if (message[i] instanceof Movie) searchData[i - 1][1] = "Movie";
                  else if (message[i] instanceof TVShow) searchData[i - 1][1] = "TVShow";

                  searchData[i - 1][2] = ((Item) message[i]).getReleaseDate();
               }

               setupTableData(searchData, null);
            }

            break;
         case "allMovies":
            Object[][] newData = new Object[message.length - 1][3];

            for (int i = 1; i < message.length; i++) {
               newData[i - 1][0] = ((Movie) message[i]).getTitle();
               newData[i - 1][1] = "Movie";
               newData[i - 1][2] = ((Movie) message[i]).getReleaseDate();
            }

            setupTableData(newData, null);
            break;
         case "allTVShows":
            Object[][] newData3 = new Object[message.length - 1][3];

            for (int i = 1; i < message.length; i++) {
               newData3[i - 1][0] = ((TVShow) message[i]).getTitle();
               newData3[i - 1][1] = "TVShow";
               newData3[i - 1][2] = ((TVShow) message[i]).getReleaseDate();
            }

            setupTableData(newData3, null);
            break;
         case "allMoviesAndTVShows":
            Object[][] newData2 = new Object[message.length - 1][3];
            for (int i = 1; i < message.length; i++) {
               newData2[i - 1][0] = ((Item) message[i]).getTitle();

               if (message[i] instanceof Movie) newData2[i - 1][1] = "Movie";
               else if (message[i] instanceof TVShow) newData2[i - 1][1] = "TVShow";
               else newData2[i - 1][1] = "?";

               newData2[i - 1][2] = ((Item) message[i]).getReleaseDate();
            }

            setupTableData(newData2, null);
            break;
         case "getFavoritesList":
            Object[][] favoriteData = new Object[message.length - 1][3];
            for (int i = 1; i < message.length; i++) {
               favoriteData[i - 1][0] = ((Item) message[i]).getTitle();

               if (message[i] instanceof Movie) favoriteData[i - 1][1] = "Movie";
               else if (message[i] instanceof TVShow) favoriteData[i - 1][1] = "TVShow";
               else favoriteData[i - 1][1] = "?";

               favoriteData[i - 1][2] = ((Item) message[i]).getReleaseDate();
            }

            setupTableData(favoriteData, null);
            break;
         case "getWatchedList":
            Object[][] watchedData = new Object[message.length - 1][3];
            for (int i = 1; i < message.length; i++) {

               if (message[i] instanceof Movie) {
                  watchedData[i - 1][0] = ((Item) message[i]).getTitle();
                  watchedData[i - 1][1] = "Movie";
                  watchedData[i - 1][2] = ((Item) message[i]).getReleaseDate();
               } else if (message[i] instanceof ArrayList<?>) {
                  watchedData[i - 1][0] = ((ArrayList<?>) message[i]).get(0)
                        .toString();
                  watchedData[i - 1][1] = "Episode";
                  watchedData[i - 1][2] = ((ArrayList<?>) message[i]).get(1)
                        .toString();
               } else {
                  watchedData[i - 1][0] = "?";
                  watchedData[i - 1][1] = "?";
                  watchedData[i - 1][2] = "?";
               }
            }

            setupTableData(watchedData, null);
            break;
         case "allCommentsForItem":
            Comment[] result = new Comment[message.length - 1];

            if (message[1] == null) {
               itemFrame.setComments(null);
               break;
            }

            for (int i = 1; i < message.length; i++)
               result[i - 1] = (Comment) message[i];

            itemFrame.setComments(result);
            break;

         case "watchedEpisodes":
            Episode[] episodeResult = new Episode[message.length - 1];

            for (int i = 1; i < message.length; i++)
               episodeResult[i - 1] = (Episode) message[i];

            itemFrame.setWatchedEpisodes(episodeResult);
            break;
         case "watchedMovies":
            int[] movieResult = new int[message.length - 1];

            for (int i = 1; i < message.length; i++)
               movieResult[i - 1] = (Integer) message[i];

            itemFrame.setWatchedMovies(movieResult);
            break;

         case "favorites":
            int[] favoriteResult = new int[message.length - 1];

            for (int i = 1; i < message.length; i++)
               favoriteResult[i - 1] = (Integer) message[i];

            itemFrame.setFavorites(favoriteResult);
            break;
         default:
            break;
      }
   }

   public Item getCurrentItem() {
      if (settingsFrame != null) return ((MovieCorner.view.settingsFrame) settingsFrame)
            .getCurrentItem();
      else return currentItem;
   }

   public Episode getCurrentEpisode() {
      if (settingsFrame != null) return ((MovieCorner.view.settingsFrame) settingsFrame)
            .getCurrentEpisode();
      else return currentEpisode;
   }

   public void setCurrentEpisode(Episode episode) {
      currentEpisode = episode;
   }

   private void viewDetails(int id) {
      if (currentData[id + 1] instanceof Item) {
         if (!itemFrameOpened) {
            itemFrameOpened = true;
            Item obj = (Item) currentData[id + 1];
            currentItem = obj;
            itemFrame = new itemFrame(obj, controller, this);
            itemFrame.start();

            itemFrame.addWindowListener(new WindowListener() {

               @Override
               public void windowActivated(WindowEvent arg0) {
                  // TODO Auto-generated method stub

               }

               @Override
               public void windowClosed(WindowEvent e) {
                  Thread t = new Thread(() -> {
                     try {
                        Thread.sleep(1000);
                     }
                     catch (Exception et) {
                        et.printStackTrace();
                     }
                     itemFrameOpened = false;
                  });
                  t.setDaemon(true);
                  t.start();

               }

               @Override
               public void windowClosing(WindowEvent e) {
                  // TODO Auto-generated method stub

               }

               @Override
               public void windowDeactivated(WindowEvent e) {
                  // TODO Auto-generated method stub

               }

               @Override
               public void windowDeiconified(WindowEvent e) {
                  // TODO Auto-generated method stub

               }

               @Override
               public void windowIconified(WindowEvent e) {
                  // TODO Auto-generated method stub

               }

               @Override
               public void windowOpened(WindowEvent e) {
                  // TODO Auto-generated method stub

               }

            });
         }
      } else if (currentData[id + 1] instanceof User) {
         if (!userFrameOpened) {
            userFrameOpened = true;
            userFrame = new UserFrame(controller, (User) currentData[id + 1],
                  this);
            ((UserFrame) userFrame).start();
            userFrame.addWindowListener(new WindowListener() {

               @Override
               public void windowActivated(WindowEvent arg0) {}

               @Override
               public void windowClosed(WindowEvent e) {
                  Thread a = new Thread(() -> {
                     try {
                        Thread.sleep(1000);
                     }
                     catch (Exception ex) {
                        ex.printStackTrace();
                     }
                     userFrameOpened = false;
                  });
                  a.setDaemon(true);
                  a.start();
               }

               @Override
               public void windowClosing(WindowEvent e) {}

               @Override
               public void windowDeactivated(WindowEvent e) {

               }

               @Override
               public void windowDeiconified(WindowEvent e) {

               }

               @Override
               public void windowIconified(WindowEvent e) {

               }

               @Override
               public void windowOpened(WindowEvent e) {

               }

            });
         }
      }
   }

   @Override
   public void startLoading(String text) {
      if (loadingFrame == null) {
         setEnabled(false);

         loadingFrame = new LoadingFrame(text);
      }
   }

   @Override
   public void finishLoading() {
      if (!isEnabled()) setEnabled(true);

      if (loadingFrame != null) {
         loadingFrame.dispose();
         loadingFrame = null;
      }
   }

}
