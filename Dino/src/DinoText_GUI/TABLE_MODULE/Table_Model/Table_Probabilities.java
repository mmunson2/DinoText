package DinoText_GUI.TABLE_MODULE.Table_Model;

import java.util.ArrayList;

/*******************************************************************************
 * Table Probabilities
 *
 ******************************************************************************/
public class Table_Probabilities
{
    ArrayList<Double> weights = new ArrayList<>();
    double sum = 0;

    /***************************************************************************
     * Constructor
     *
     **************************************************************************/
    public Table_Probabilities()
    {
        for(int i = 0; i < Table_Model.DEFAULT_ROWS; i++)
        {
            weights.add(i,0.0);
        }
    }

    /***************************************************************************
     * Copy Constructor
     *
     **************************************************************************/
    public Table_Probabilities(Table_Probabilities copy)
    {
        if(copy != null)
        {
            this.weights = new ArrayList<>(copy.weights);
            this.sum = copy.sum;
        }
    }

    /***************************************************************************
     * Add Weight
     *
     **************************************************************************/
    public void addWeight(double weight)
    {
        weights.add(weight);
        sum += weight;
    }

    /***************************************************************************
     * Remove Weight
     *
     **************************************************************************/
    public void removeWeight(int index)
    {
        sum -= weights.get(index);
        weights.remove(index);
    }

    /***************************************************************************
     * Update Weight
     *
     **************************************************************************/
    public void updateWeight(int index, double weight)
    {
        sum -= weights.get(index);
        weights.set(index, weight);
        sum += weights.get(index);
    }

    /***************************************************************************
     * Get Probability
     *
     **************************************************************************/
    public double getProbability(int index)
    {
        if(sum == 0)
        {
            return 0;
        }

        return (weights.get(index) / sum) * 100;
    }
}
