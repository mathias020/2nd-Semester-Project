package MovieCorner.view;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class MyModel extends DefaultTableModel {
   public MyModel(Object[][] data, Object[] columns) {
      super(data, columns);
   }

   public MyModel() {
      super();
   }

   @Override
   public boolean isCellEditable(int row, int col) {
      return false;
   }
}
