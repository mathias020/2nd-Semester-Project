package MovieCorner.view;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

import java.awt.GridLayout;

import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;

public class addTVShow extends JPanel {
   
      String type;
      private JTextField fieldTitle;
      private ArrayList<JCheckBox> boxList;
      private JPanel mainPanel, titlePanel, descriptionPanel, directorPanel, 
      genresPanel, genresBoxPanel, bottomPanel, seasonPanel, episodePanel;
      private JTextArea fieldDescription;
      private JSpinner fieldEpisode, fieldSeason;
      private JPanel durationPanel;
      private JTextField textField;
      private JLabel lblReleaseDate;
      private JTextField textField_1;
      private JPanel panel;
      private JLabel lblTvShow;
      
      
      /**
       * Create the panel.
       */
      public addTVShow() {
         this.type = "episode";
         
         setLayout(new BorderLayout(0, 0));
         
         seasonPanel = new JPanel();
         episodePanel = new JPanel();
         
         mainPanel = new JPanel();
         add(mainPanel, BorderLayout.CENTER);
         
         titlePanel = new JPanel();
         titlePanel.setLayout(new BorderLayout(0, 0));
         
         JLabel lblTitle = new JLabel("Title:");
         titlePanel.add(lblTitle, BorderLayout.WEST);
         
         fieldTitle = new JTextField();
         fieldTitle.setHorizontalAlignment(SwingConstants.LEFT);
         titlePanel.add(fieldTitle, BorderLayout.CENTER);
         fieldTitle.setColumns(10);
         
         descriptionPanel = new JPanel();
         descriptionPanel.setLayout(new BorderLayout(0, 0));
         
         JLabel lblDescription = new JLabel("Description:");
         descriptionPanel.add(lblDescription, BorderLayout.NORTH);
         
         directorPanel = new JPanel();
         directorPanel.setLayout(new BorderLayout(0, 0));
         
         genresPanel = new JPanel();
         
         durationPanel = new JPanel();
         
         JPanel releasePanel = new JPanel();
         
         panel = new JPanel();
         
         GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
         gl_mainPanel.setHorizontalGroup(
            gl_mainPanel.createParallelGroup(Alignment.LEADING)
               .addGroup(gl_mainPanel.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
                     .addComponent(panel, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                     .addGroup(gl_mainPanel.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(directorPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(titlePanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                     .addComponent(durationPanel, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                     .addComponent(releasePanel, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                     .addComponent(seasonPanel, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                     .addComponent(episodePanel, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                     .addComponent(descriptionPanel, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                  .addPreferredGap(ComponentPlacement.RELATED)
                  .addComponent(genresPanel, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                  .addContainerGap())
         );
         gl_mainPanel.setVerticalGroup(
            gl_mainPanel.createParallelGroup(Alignment.TRAILING)
               .addGroup(gl_mainPanel.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
                     .addComponent(genresPanel, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                     .addGroup(gl_mainPanel.createSequentialGroup()
                        .addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(directorPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(durationPanel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(releasePanel, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(seasonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(episodePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(descriptionPanel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(21)))
                  .addGap(49))
         );
         panel.setLayout(new BorderLayout(0, 0));
         
         lblTvShow = new JLabel("TV Show:");
         panel.add(lblTvShow, BorderLayout.WEST);
         
         JComboBox comboBox = new JComboBox();
         panel.add(comboBox, BorderLayout.CENTER);
         releasePanel.setLayout(new BorderLayout(0, 0));
         
         lblReleaseDate = new JLabel("Release date");
         releasePanel.add(lblReleaseDate, BorderLayout.WEST);
         
         textField_1 = new JTextField();
         releasePanel.add(textField_1, BorderLayout.CENTER);
         textField_1.setColumns(10);
         durationPanel.setLayout(new BorderLayout(0, 0));
         
         JLabel lblDuration = new JLabel("Duration:");
         durationPanel.add(lblDuration, BorderLayout.WEST);
         
         textField = new JTextField();
         durationPanel.add(textField, BorderLayout.CENTER);
         textField.setColumns(10);
         
         episodePanel.setLayout(new BorderLayout(0, 0));
         
         JLabel lblEpisode = new JLabel("Episode:");
         episodePanel.add(lblEpisode, BorderLayout.WEST);
         
         fieldEpisode = new JSpinner();
         episodePanel.add(fieldEpisode, BorderLayout.CENTER);
         seasonPanel.setLayout(new BorderLayout(0, 0));
         
         JLabel lblSeason = new JLabel("Season:");
         seasonPanel.add(lblSeason, BorderLayout.WEST);
         
         fieldSeason = new JSpinner();
         seasonPanel.add(fieldSeason, BorderLayout.CENTER);
         
         fieldDescription = new JTextArea();
         descriptionPanel.add(fieldDescription, BorderLayout.CENTER);
         genresPanel.setLayout(new BorderLayout(0, 0));
                  
         
         if (this.type.equals("episode")) {
            directorPanel.setVisible(false);
         }
            
         if (!this.type.equals("episode")) {
            seasonPanel.setVisible(false);
            episodePanel.setVisible(false);
            
            JLabel lblGenres = new JLabel("Genres:");
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
            
            for (JCheckBox box:boxList)
               genresBoxPanel.add(box);
         }
         mainPanel.setLayout(gl_mainPanel);
         
         bottomPanel = new JPanel();
         add(bottomPanel, BorderLayout.SOUTH);
         bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
         
         JButton btnGoBack = new JButton("Go Back");
         btnGoBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //menu();
            }
         });
         
         bottomPanel.add(btnGoBack);
         
         JButton btnAdd = new JButton("Add");
         bottomPanel.add(btnAdd);
         
         btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
               if (type.equals("movie"))
                  System.out.println("addItem");
               else if (type.equals("tvshow"))
                  System.out.println("addItem");
               else if (type.equals("episode"))
                  System.out.println("addItem");
            }
         });
         
         
   }
}
