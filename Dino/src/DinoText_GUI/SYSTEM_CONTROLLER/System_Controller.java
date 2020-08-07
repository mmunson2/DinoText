package DinoText_GUI.SYSTEM_CONTROLLER;

import DinoText_GUI.DIALOGUE_MODULE.DialogueController.Dialogue_Controller;
import DinoText_GUI.DISPLAY_MODULE.DisplayController.Text_Display_Controller;
import DinoText_GUI.TABLE_MODULE.Table_Controller.Table_Controller;

public class System_Controller {
    private Dialogue_Controller dialogue_controller;
    private Text_Display_Controller text_display_controller;
    private Table_Controller table_controller;

    public System_Controller(Dialogue_Controller dialogue, Text_Display_Controller text, Table_Controller table) {
        dialogue_controller = dialogue;
        text_display_controller = text;
        table_controller = table;
    }

    public Dialogue_Controller getDialogueController() {
        return dialogue_controller;
    }

    public Text_Display_Controller getText_display_controller() {
        return text_display_controller;
    }

    public Table_Controller getTable_Controller() {
        return table_controller;
    }
}
