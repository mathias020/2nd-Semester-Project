package MovieCorner.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utility.collection.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import MovieCorner.controller.Controller;
import MovieCorner.model.Item;
import MovieCorner.model.Movie;
import MovieCorner.model.User;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class UserFrame extends JFrame {

   private DefaultTableModel tableModel;
   private JPanel contentPane, namePanel, emailPanel, adminPanel, buttonPanel,
         panel, panel_1;
   private JButton btnDeleteUser, btnSaveChanges, btnAddAsA;
   private Controller controller;
   private User user;
   private MovieCornerView mainFrame;
   private JTextField fieldName;
   private JTextField fieldEmail;
   private JTable table;
   private JLabel lblName, lblEmail, lblHasAdministratorPrivileges,
         lblWatchedList;
   private Object[][] rowData;
   private Object[] columnNames;
   private JScrollPane scrollPane;
   private JComboBox<Object> adminBox;
   private boolean isAdmin;
   private GroupLayout gl_panel;

   public UserFrame(Controller controller, User user, MovieCornerView mainFrame) {

      this.controller = controller;
      this.mainFrame = mainFrame;
      this.user = user;

      createComponents();
      initializeComponents();
      addComponentsToFrame();
      setupFrame();
      registerEventHandlers();
   }

   private void registerEventHandlers() {
      // Button delete
      btnDeleteUser.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             controller.execute("removeUser");
             dispose();
         }
      });
      
      // Button save
      btnSaveChanges.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             controller.execute("saveUser");
             dispose();
         }
      });
      
      // Button add as friend
      btnAddAsA.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            controller.execute("addFriend");
            dispose();
        }
     });
   }

   private void addComponentsToFrame() {
      panel.setLayout(gl_panel);
      contentPane.add(panel, BorderLayout.CENTER);
      panel_1.add(lblWatchedList, BorderLayout.NORTH);
      panel_1.add(scrollPane, BorderLayout.CENTER);
      adminPanel.add(lblHasAdministratorPrivileges, BorderLayout.WEST);
      adminPanel.add(adminBox, BorderLayout.CENTER);
      emailPanel.add(lblEmail, BorderLayout.WEST);
      emailPanel.add(fieldEmail, BorderLayout.CENTER);
      namePanel.add(lblName, BorderLayout.WEST);
      namePanel.add(fieldName, BorderLayout.CENTER);
      contentPane.add(buttonPanel, BorderLayout.SOUTH);
      buttonPanel.add(btnAddAsA);
   }

   private void initializeComponents() {
      table.setModel(tableModel);

      adminBox.setModel(new DefaultComboBoxModel<Object>(
            new String[] { "false", "true" }));
   }

   private void createComponents() {
      contentPane = new JPanel(new BorderLayout(0, 0));
      panel = new JPanel();
      buttonPanel = new JPanel();
      panel_1 = new JPanel(new BorderLayout(0, 0));
      namePanel = new JPanel(new BorderLayout(0, 0));
      adminPanel = new JPanel(new BorderLayout(0, 0));
      emailPanel = new JPanel(new BorderLayout(0, 0));
      namePanel = new JPanel(new BorderLayout(0, 0));

      btnDeleteUser = new JButton("Delete user");
      btnSaveChanges = new JButton("Save changes");

      fieldName = new JTextField();
      lblName = new JLabel("Name: ");
      fieldEmail = new JTextField();
      lblEmail = new JLabel("Email: ");

      adminBox = new JComboBox<Object>();

      columnNames = new Object[2];
      rowData = new Object[1][2];
      
      columnNames[0] = "Title";
      columnNames[1] = "Type";

      rowData[0][0] = "Loading list...";
      
      tableModel = new MyModel(rowData, columnNames);
      table = new JTable(rowData, columnNames);
      scrollPane = new JScrollPane(table);
      lblHasAdministratorPrivileges = new JLabel("Has administrator privileges: ");
      lblWatchedList = new JLabel("Watched list:");
      btnAddAsA = new JButton("Add as a friend");
      
      gl_panel = new GroupLayout(panel);
      gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
            Alignment.LEADING).addGroup(
            Alignment.TRAILING,
            gl_panel
                  .createSequentialGroup()
                  .addContainerGap()
                  .addGroup(
                        gl_panel
                              .createParallelGroup(Alignment.TRAILING)
                              .addComponent(panel_1, Alignment.LEADING,
                                    GroupLayout.DEFAULT_SIZE, 322,
                                    Short.MAX_VALUE)
                              .addComponent(namePanel,
                                    GroupLayout.DEFAULT_SIZE, 322,
                                    Short.MAX_VALUE)
                              .addComponent(adminPanel, Alignment.LEADING,
                                    GroupLayout.DEFAULT_SIZE, 322,
                                    Short.MAX_VALUE)
                              .addComponent(emailPanel,
                                    GroupLayout.DEFAULT_SIZE, 322,
                                    Short.MAX_VALUE)).addGap(19)));
      gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(
                  gl_panel
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(namePanel, GroupLayout.PREFERRED_SIZE,
                              GroupLayout.DEFAULT_SIZE,
                              GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(emailPanel, GroupLayout.PREFERRED_SIZE,
                              GroupLayout.DEFAULT_SIZE,
                              GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(adminPanel, GroupLayout.PREFERRED_SIZE,
                              18, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 203,
                              Short.MAX_VALUE).addContainerGap()));
   }

   private void setupFrame() {
      setTitle("User details");
      setSize(380, 400);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setLocationRelativeTo(null);
      setResizable(false);
      setType(JFrame.Type.UTILITY);
      setContentPane(contentPane);
   }
   
   public void start() {
      isAdmin = ((MainFrame) mainFrame).isAdmin();

      buttonPanel.add(btnDeleteUser);
      buttonPanel.add(btnSaveChanges);

      if (!isAdmin) {
         fieldEmail.setEditable(false);
         fieldName.setEditable(false);
         buttonPanel.remove(btnDeleteUser);
         buttonPanel.remove(btnSaveChanges);
         adminBox.setEnabled(false);
      }

      setupTableData(rowData);
      setVisible(true);
      setupData();
      

      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            controller.execute("checkFriend");
            controller.execute("getWatchedByUser");
         } 
      });
   }
 
   private void setupTableData(Object[][] newData) {

      if (tableModel != null) {
         for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
            tableModel.removeRow(i);

         for (int i = 0; i < newData.length; i++) {
            tableModel.addRow(newData[i]);
         }
      }

      table.getColumnModel().getColumn(0)
            .setCellRenderer(new MultiColorRenderer());
      table.getColumnModel().getColumn(1)
            .setCellRenderer(new MultiColorRenderer(true));

   }

   private void setupData() {
      fieldName.setText(user.getNickname());
      fieldEmail.setText(user.getEmail());
      if (user.isAdmin()) adminBox.setSelectedIndex(1);
   }

   public Object get(String what) {

      switch(what) {
         case "userIDforWatched":
            return user.getID();
         case "emailOfUser":
            return fieldEmail.getText();
         case "nameOfUser":
            return fieldName.getText();
         case "privilegesOfUser":
            return adminBox.getSelectedItem();
         default: break;
      }
      return null;
   }
   
   public void show(String message) {
      switch(message) {
         case "isFriend":
            btnAddAsA.setEnabled(false);
            break;
         default: JOptionPane.showMessageDialog(null, message); break;
      }
   }
   
   public void show(Object[] message)
   {
      switch (message[0].toString()) {
         case "watchedByUserID":
            Object[][] watchedData = new Object[message.length - 1][2];
            for (int i = 1; i < message.length; i++) {
               
               if (message[i] instanceof Movie) {
                  watchedData[i - 1][0] = ((Item) message[i]).getTitle();
                  watchedData[i - 1][1] = "Movie";
               } else if (message[i] instanceof ArrayList<?>) {
                  watchedData[i - 1][0] = ((ArrayList<?>) message[i]).get(0).toString();
                  watchedData[i - 1][1] = "Episode";
               } else {
                  watchedData[i - 1][0] = "?";
                  watchedData[i - 1][1] = "?";
               }   
            }

            setupTableData(watchedData);
            break;
            default: JOptionPane.showConfirmDialog(null, message[1].toString()); break;
      }
   }
}
