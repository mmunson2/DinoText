package DinoText_GUI.TABLE_MODULE.Table_View.Custom_UI.TraitDisplay;

import javax.swing.*;
import java.awt.*;

public class TraitLabel extends JLabel {
    public static final int MAX_WIDTH = 40;
    int rectStart = 0;
    int rectWidth = 0;
    double lower = 0;
    double upper = 100;

    public TraitLabel(String text){
        super();
        setTextWithChart(text);
    }

    public TraitLabel(String text, double lower, double upper){
        super();
        setTextWithChart(text);
        setBounds(lower, upper);
    }

    public TraitLabel() {
        super();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        int defaultStart = g.getFontMetrics().stringWidth(getText()) - g.getFontMetrics().stringWidth("...........");


        rectStart = (int) (defaultStart + lower * MAX_WIDTH);
        rectWidth = (int) ((upper - lower) * MAX_WIDTH);

        int xStart = g.getFontMetrics().stringWidth(getText()) - g.getFontMetrics().stringWidth("...........");
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(2));
        g.drawLine(xStart, getHeight() / 2, xStart + MAX_WIDTH, getHeight() / 2);
        g.fillRect((int) rectStart, getHeight() / 4, (int) rectWidth, getHeight() / 2);
        super.paintComponent(g);
    }

    public void setBounds(double lb, double ub) {
        lower = lb;
        upper = ub;

        // convert to decimal
        lower /= 100;
        upper /= 100;
    }

    public void setTextWithChart(String text) {
        text += "              ";
        setText(text);
    }
}
