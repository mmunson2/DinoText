package DinoText_GUI.MatthewTestTable;

import DinoText_GUI.TableWindow_Demo.CustomButtonRenderer;
import DinoText_GUI.TableWindow_Demo.CustomMouseListener;
import DinoText_GUI.TableWindow_Demo.TableModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class Driver
{
    public static void main(String[] args)
    {
        JFrame testFrame = new JFrame();

        TestTable_View view = new TestTable_View();
        TestTable_Model model = new TestTable_Model();
        TestTable_Controller controller = new TestTable_Controller(model, view);


        testFrame.add(view.getPanel());

        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(600, 580);
        testFrame.pack();
        testFrame.setVisible(true);
    }
}
