package DinoText_GUI.MODEL.Table;

import javax.swing.*;
import javax.swing.event.ChangeListener;

public class Table_JTabbedPane_Model implements SingleSelectionModel
{

    @Override
    public int getSelectedIndex() {
        return 0;
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
