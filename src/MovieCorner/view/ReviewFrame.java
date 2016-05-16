package MovieCorner.view;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import MovieCorner.controller.Controller;

import java.awt.Font;

@SuppressWarnings("serial")
public class ReviewFrame extends JFrame {
	private JTextArea textArea;
	private JComboBox<Integer> comboBox;
	
	@SuppressWarnings("unused")
   private Controller controller;
	
	public ReviewFrame(Controller controller)
	{
		super("Review");
		this.controller = controller;
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{59, 94, 0};
		gridBagLayout.rowHeights = new int[]{0, 37, 21, 31, 40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblRating = new JLabel("Rating");
		GridBagConstraints gbc_lblRating = new GridBagConstraints();
		gbc_lblRating.insets = new Insets(5, 5, 5, 5);
		gbc_lblRating.gridx = 0;
		gbc_lblRating.gridy = 1;
		getContentPane().add(lblRating, gbc_lblRating);
		
		comboBox = new JComboBox<Integer>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(5, 5, 5, 10);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboBox, gbc_comboBox);
		
		JLabel lblReview = new JLabel("Review");
		GridBagConstraints gbc_lblReview = new GridBagConstraints();
		gbc_lblReview.anchor = GridBagConstraints.NORTH;
		gbc_lblReview.insets = new Insets(5, 5, 5, 5);
		gbc_lblReview.gridx = 0;
		gbc_lblReview.gridy = 3;
		getContentPane().add(lblReview, gbc_lblReview);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(5, 5, 5, 10);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		JButton btnAddReview = new JButton("Add review");
		GridBagConstraints gbc_btnAddReview = new GridBagConstraints();
		gbc_btnAddReview.insets = new Insets(5, 5, 5, 10);
		gbc_btnAddReview.anchor = GridBagConstraints.EAST;
		gbc_btnAddReview.gridx = 1;
		gbc_btnAddReview.gridy = 4;
		getContentPane().add(btnAddReview, gbc_btnAddReview);
		
		btnAddReview.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "Please make sure your review is complete, you can not edit it after submitting it. \n\nContinue?");
				
				if(choice == JOptionPane.YES_OPTION)
					controller.execute("addReview");
			}
			
		});
		
		for(int i = 1; i <= 10; i++)
			comboBox.addItem(new Integer(i));
		
		setLocationRelativeTo(null);
		setSize(370, 400);
		setVisible(true);
		
	}
	
	public String getReview()
	{
		return textArea.getText();
	}
	
	public int getRating()
	{
		return (Integer) comboBox.getSelectedItem();
	}
}
