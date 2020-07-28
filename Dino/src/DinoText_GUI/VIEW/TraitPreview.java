package DinoText_GUI.VIEW;

import javax.swing.*;
import java.awt.*;

public class TraitPreview extends JLabel {
    public static final int MAX_WIDTH = 40;
    static int rectStart = 0;
    static int rectWidth = 0;


    public void paintChart(Graphics2D g) {
        int xStart = g.getFontMetrics().stringWidth(getText()) - g.getFontMetrics().stringWidth("...........");
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(2));
        g.drawLine(xStart, getHeight() / 2, xStart + MAX_WIDTH, getHeight() / 2);
        g.fillRect((int) rectStart, getHeight() / 4, (int) rectWidth, getHeight() / 2);
        super.paintComponents(g);
    }

    public void setBounds(double lower, double upper) {
        Graphics2D g = (Graphics2D) getGraphics();
        int defaultStart = g.getFontMetrics().stringWidth(getText()) - g.getFontMetrics().stringWidth("...........");

        // convert to decimal
        lower /= 100;
        upper /= 100;

        rectStart = (int) (defaultStart + lower * MAX_WIDTH);
        rectWidth = (int) ((upper - lower) * MAX_WIDTH);

        paintChart(g);
    }

    public void setTextWithChart(String text) {

        text += "              ";
        setText(text);
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel(new GridLayout(1,0));

        TraitPreview label = new TraitPreview();
        TraitPreview label2 = new TraitPreview();


        label.setTextWithChart("TRAIT");
        label2.setTextWithChart("TRAIT 2");


        panel.add(label);
        panel.add(label2);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 580);
        frame.pack();
        frame.setVisible(true);

        label.setBounds(0, 100);
        label2.setBounds(10, 60);
    }
}
