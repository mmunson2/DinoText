package DinoText_GUI.MODEL.Table;

import java.util.ArrayList;

public class Table_Probabilities
{
    ArrayList<Double> weights = new ArrayList<>();
    double sum = 0;

    Table_Probabilities()
    {
        for(int i = 0; i < Table_Model.DEFAULT_ROWS; i++)
        {
            weights.add(i,0.0);
        }
    }

    void addWeight(double weight)
    {
        weights.add(weight);
        sum += weight;
    }

    void removeWeight(int index)
    {
        sum -= weights.get(index);
        weights.remove(index);
    }

    void updateWeight(int index, double weight)
    {
        sum -= weights.get(index);
        weights.set(index, weight);
        sum += weights.get(index);
    }

    double getProbability(int index)
    {
        if(sum == 0)
        {
            return 0;
        }

        return (weights.get(index) / sum) * 100;
    }



}
