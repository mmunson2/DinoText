package DinoText_GUI.MODEL.Table;

import java.util.ArrayList;

/*******************************************************************************
 * Table Probabilities
 *
 ******************************************************************************/
public class Table_Probabilities
{
    ArrayList<Double> weights = new ArrayList<>();
    double sum = 0;

    public Table_Probabilities()
    {
        for(int i = 0; i < Table_Model.DEFAULT_ROWS; i++)
        {
            weights.add(i,0.0);
        }
    }

    public Table_Probabilities(Table_Probabilities copy)
    {
        if(copy != null)
        {
            this.weights = new ArrayList<>(copy.weights);
            this.sum = copy.sum;
        }
    }

    public void addWeight(double weight)
    {
        weights.add(weight);
        sum += weight;
    }

    public void removeWeight(int index)
    {
        sum -= weights.get(index);
        weights.remove(index);
    }

    public void updateWeight(int index, double weight)
    {
        sum -= weights.get(index);
        weights.set(index, weight);
        sum += weights.get(index);
    }

    public double getProbability(int index)
    {
        if(sum == 0)
        {
            return 0;
        }

        return (weights.get(index) / sum) * 100;
    }



}
