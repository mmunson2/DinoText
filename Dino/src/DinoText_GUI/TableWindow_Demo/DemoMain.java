package DinoText_GUI.TableWindow_Demo;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;


public class DemoMain
{
    public static void main(String[] args)
    {
        JFrame testFrame = new JFrame();

        JTable table = new JTable(new TableModel());
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        TableCellRenderer buttonRenderer = new CustomButtonRenderer();
        table.getColumn("Button1").setCellRenderer(buttonRenderer);
        table.getColumn("Button2").setCellRenderer(buttonRenderer);

        table.addMouseListener(new CustomMouseListener(table));

        testFrame.add(table);

        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(600, 580);
        testFrame.pack();
        testFrame.setVisible(true);
    }




}
