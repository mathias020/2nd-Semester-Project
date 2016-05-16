package MovieCorner.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class MultiColorRenderer extends DefaultTableCellRenderer {
   private boolean centerText = false;

   public MultiColorRenderer() {
      super();
   }

   public MultiColorRenderer(boolean centerText) {
      this.centerText = centerText;
   }

   public Component getTableCellRendererComponent(JTable table, Object value,
         boolean isSelected, boolean hasFocus, int row, int column) {
      Component comp = super.getTableCellRendererComponent(table, value,
            isSelected, hasFocus, row, column);

      if ((row % 2) == 0 && !isSelected) {
         comp.setBackground(new Color(0, 0, 0, 20));
      } else if ((row % 2) != 0 && !isSelected) {
         comp.setBackground(Color.white);
      } else if (isSelected) {
         comp.setBackground(new Color(0, 0, 255, 70));
      }

      if (centerText) {
         setHorizontalAlignment(JLabel.CENTER);
      }

      return comp;
   }
}
