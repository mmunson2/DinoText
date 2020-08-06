package DinoText_GUI.DIALOGUE_MODULE.DialogueView;

import javax.swing.*;

public class TestView {
    public static void main(String[] args) {
        JPanel testpanel = new JPanel();
        JButton button = new JButton();
        button.setName("butt");
        testpanel.add(button);
        System.out.println(button.getParent());
    }
}
