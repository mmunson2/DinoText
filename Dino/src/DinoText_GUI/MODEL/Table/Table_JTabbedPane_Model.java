package DinoText_GUI.MODEL.Table;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class Table_JTabbedPane_Model implements SingleSelectionModel
{
    ArrayList<Table_Model> tables = new ArrayList<>();
    int currentIndex = 0;

    public Table_JTabbedPane_Model()
    {
        tables.add(new Table_Model());

    }


    @Override
    public int getSelectedIndex() {
        return currentIndex;
    }

    @Override
    public void setSelectedIndex(int index) {



    }

    @Override
    public void clearSelection() {

    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void addChangeListener(ChangeListener listener) {

    }

    @Override
    public void removeChangeListener(ChangeListener listener) {

    }
}
