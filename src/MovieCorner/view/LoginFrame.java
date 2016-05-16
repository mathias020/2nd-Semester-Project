package MovieCorner.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import MovieCorner.controller.Controller;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame{
   
   private JPanel contentPane, panelLogin, panelRegister, panelButtons, panelTop, panelLoginDetails, panelWelcome, panelRegistrationDetails;
   private JTextField emailField, regNameField, regEmailField;
   private JPasswordField passwordField, pass;
   private JButton btnRegister, btnLogin;
   private JLabel lblLogo, lblEmail, lblPassword, lblWelcome, lblRegistration, lblRegPassword, lblRegEmail, lblRegName;
   
   private Controller controller;
   
   public LoginFrame(Controller controller) {
      this.controller = controller;
      
      
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Throwable e) {
         e.printStackTrace();
      }
      
      createComponents();
      initializeComponents();
      registerEventHandlers();
      addComponentsToFrame();
      
   }

   private void createComponents() {
      contentPane = new JPanel();
      panelButtons = new JPanel();
      panelTop = new JPanel();
      panelLoginDetails = new JPanel();
      panelLogin = new JPanel();
      panelRegister = new JPanel();
      panelWelcome = new JPanel();
      panelRegistrationDetails = new JPanel();
      
      btnRegister = new JButton("Register");
      btnLogin = new JButton("Login");
      
      lblLogo = new JLabel();
      lblEmail = new JLabel("Email:");
      lblPassword = new JLabel("Password:");
      lblWelcome = new JLabel("Welcome");
      lblRegistration = new JLabel("Registration");
      lblRegName = new JLabel("Nickname: ");
      lblRegEmail = new JLabel("Email: ");
      lblRegPassword = new JLabel("Password: ");
      
      emailField = new JTextField();
      regEmailField = new JTextField();
      regNameField = new JTextField();
      
      passwordField = new JPasswordField();
      pass = new JPasswordField();
   }

   private void initializeComponents() {
      // Components
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane.setLayout(new BorderLayout(0, 0));
      
      lblLogo.setIcon(new ImageIcon(getClass().getResource("/images/top.png")));
      
      panelLogin.setBorder(null);
      panelLogin.setLayout(new BorderLayout(0, 0));
      
      panelLoginDetails.setBorder(new TitledBorder(null, "Login details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      
      emailField.setColumns(10);
      
      lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
      lblWelcome.setFont(new Font("Ubuntu", Font.BOLD, 21));
      
      panelRegister.setLayout(new BorderLayout(0, 0));
      panelRegister.add(panelWelcome, BorderLayout.NORTH);
      lblRegistration.setFont(new Font("Ubuntu", Font.BOLD, 21));
      panelWelcome.add(lblRegistration);
      panelRegistrationDetails.setBorder(new TitledBorder(null, "User details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      panelRegister.add(panelRegistrationDetails, BorderLayout.CENTER);
      regNameField.setColumns(10);
      regEmailField.setColumns(10);
      
      // Set visible login panel
      panelRegister.setVisible(false);
      panelLogin.setVisible(true);
      contentPane.remove(panelRegister);
      contentPane.add(panelLogin);
      
      // Main frame
      setResizable(false);
     // setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo.png")));
      setTitle("Login");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(300, 270);
      setLocationRelativeTo(null);
      
   }

   private void registerEventHandlers() {
      // Button Login
      btnLogin.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (panelLogin == null)
            {
               panelLogin = new JPanel();
               
            } else if (!panelLogin.isVisible()) {
               contentPane.remove(panelRegister);
               contentPane.add(panelLogin, BorderLayout.CENTER);

               setSize(300, 270);
               
               panelRegister.setVisible(false);
               setTitle("Login");
               panelLogin.setVisible(true);
            } else { 
               controller.execute("Login");
            }
         }
      });
      
      getRootPane().setDefaultButton(btnLogin);
      
      // Button Register
      btnRegister.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (panelRegister == null)
            {
               panelRegister = new JPanel();
               
            } else if (!panelRegister.isVisible()) {
               contentPane.remove(panelLogin);
               contentPane.add(panelRegister, BorderLayout.CENTER);
               setSize(300, 310);
               
               panelLogin.setVisible(false);
               setTitle("Register");
               panelRegister.setVisible(true);
            } else {
               controller.execute("Register");
            }
         }
      });
      
   }

   private void addComponentsToFrame() {
      setContentPane(contentPane);
      
      panelTop.add(lblLogo);
      contentPane.add(panelButtons, BorderLayout.SOUTH);
      contentPane.add(panelButtons, BorderLayout.SOUTH);
      panelButtons.add(btnRegister);
      panelButtons.add(btnLogin);
      contentPane.add(panelTop, BorderLayout.NORTH);
      contentPane.add(panelLogin, BorderLayout.CENTER);
      
      GroupLayout gl_panelLoginDetails = new GroupLayout(panelLoginDetails);
      gl_panelLoginDetails.setHorizontalGroup(
         gl_panelLoginDetails.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panelLoginDetails.createSequentialGroup()
               .addContainerGap()
               .addGroup(gl_panelLoginDetails.createParallelGroup(Alignment.TRAILING)
                  .addComponent(lblPassword)
                  .addComponent(lblEmail))
               .addGap(5)
               .addGroup(gl_panelLoginDetails.createParallelGroup(Alignment.LEADING)
                  .addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                  .addComponent(emailField, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
               .addContainerGap())
      );
      gl_panelLoginDetails.setVerticalGroup(
         gl_panelLoginDetails.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panelLoginDetails.createSequentialGroup()
               .addGroup(gl_panelLoginDetails.createParallelGroup(Alignment.LEADING)
                  .addGroup(gl_panelLoginDetails.createSequentialGroup()
                     .addGap(8)
                     .addComponent(lblEmail))
                  .addGroup(gl_panelLoginDetails.createSequentialGroup()
                     .addGap(5)
                     .addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
               .addPreferredGap(ComponentPlacement.RELATED)
               .addGroup(gl_panelLoginDetails.createParallelGroup(Alignment.BASELINE)
                  .addComponent(lblPassword)
                  .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
               .addContainerGap(79, Short.MAX_VALUE))
      );
      
      panelLoginDetails.setLayout(gl_panelLoginDetails);
      panelLogin.add(panelLoginDetails, BorderLayout.CENTER);
      
      panelLogin.add(lblWelcome, BorderLayout.NORTH);
      
      GroupLayout gl_panelRegistrationDetails = new GroupLayout(panelRegistrationDetails);
      gl_panelRegistrationDetails.setHorizontalGroup(
         gl_panelRegistrationDetails.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panelRegistrationDetails.createSequentialGroup()
               .addContainerGap()
               .addGroup(gl_panelRegistrationDetails.createParallelGroup(Alignment.TRAILING)
                  .addComponent(lblRegPassword)
                  .addComponent(lblRegName)
                  .addComponent(lblRegEmail))
               .addPreferredGap(ComponentPlacement.RELATED)
               .addGroup(gl_panelRegistrationDetails.createParallelGroup(Alignment.LEADING, false)
                  .addComponent(regEmailField)
                  .addComponent(regNameField, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                  .addComponent(pass))
               .addGap(98))
      );
      gl_panelRegistrationDetails.setVerticalGroup(
         gl_panelRegistrationDetails.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panelRegistrationDetails.createSequentialGroup()
               .addGap(8)
               .addGroup(gl_panelRegistrationDetails.createParallelGroup(Alignment.BASELINE)
                  .addComponent(lblRegName)
                  .addComponent(regNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
               .addGap(9)
               .addGroup(gl_panelRegistrationDetails.createParallelGroup(Alignment.BASELINE)
                  .addComponent(lblRegEmail)
                  .addComponent(regEmailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
               .addGap(7)
               .addGroup(gl_panelRegistrationDetails.createParallelGroup(Alignment.BASELINE)
                  .addComponent(lblRegPassword)
                  .addComponent(pass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(ComponentPlacement.RELATED))
      );
      panelRegistrationDetails.setLayout(gl_panelRegistrationDetails);
      
   }

   @SuppressWarnings("deprecation")
   public String get(String what)
   {      
      switch (what)
      {
         case "Enter email":
            return emailField.getText();
         case "Enter password":
            return passwordField.getText();
         case "Your nickname":
            return regNameField.getText();
         case "Your email":
            return regEmailField.getText();
         case "Your password":
            return pass.getText();
         default: return JOptionPane.showInputDialog(what);
      }
   }

   public void show(String msg) {
      JOptionPane.showMessageDialog(null, msg, "Try again", JOptionPane.ERROR_MESSAGE);
   }
   
}
