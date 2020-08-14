package DinoText_GUI.TABLE_MODULE.TraitEditor;

import DinoText_GUI.TABLE_MODULE.Table_Model.Table_Probabilities;

import javax.swing.*;

public class Editor_Test
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

        Editor_View view = new Editor_View();
        Editor_Model model = new Editor_Model(new Table_Probabilities(), 0);
        Editor_Controller controller = new Editor_Controller(model, view);

        frame.add(view.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 580);
        frame.setTitle("Trait Test");
        frame.pack();

        frame.setVisible(true);
    }
}
