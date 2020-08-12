package DinoText_GUI.TABLE_MODULE;

import DinoText_GUI.TABLE_MODULE.Table_Controller.Table_Controller;
import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Manager;
import DinoText_GUI.TABLE_MODULE.Table_View.Table_TabbedPane;

import javax.swing.*;

public class Table_Test
{
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //Windows Look and feel
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();


        Table_Manager model = new Table_Manager();
        Table_TabbedPane view = new Table_TabbedPane();

        Table_Controller controller = new Table_Controller(model, view);

        frame.add(view.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 580);
        frame.setTitle("Table Test");
        frame.pack();

        frame.setVisible(true);


    }


}
