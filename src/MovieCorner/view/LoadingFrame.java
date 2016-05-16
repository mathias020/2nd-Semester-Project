package MovieCorner.view;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class LoadingFrame extends JFrame {
	public LoadingFrame(String text) {
		super("MovieCorner");
		
	      try {
	          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	       }
	       catch (Throwable e) {
	          e.printStackTrace();
	       }
	      
			addComponents(text);
			removeMinMaxClose(this);
			setSize(350, 90);
			setLocationRelativeTo(null);
			setResizable(false);
			setType(JFrame.Type.UTILITY);
			setVisible(true);
			
	}
	
	  private void removeMinMaxClose(Component comp)
	  {
	    if(comp instanceof AbstractButton)
	    {
	      comp.getParent().remove(comp);
	    }
	    if (comp instanceof Container)
	    {
	      Component[] comps = ((Container)comp).getComponents();
	      for(int x = 0, y = comps.length; x < y; x++)
	      {
	        removeMinMaxClose(comps[x]);
	      }
	    }
	  }
	
	private void addComponents(String text)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		
		JLabel lblNewLabel = new JLabel(text);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblNewLabel, BorderLayout.WEST);
		
		add(panel);
	}
}
