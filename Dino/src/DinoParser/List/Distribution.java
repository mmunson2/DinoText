package DinoParser.List;

import java.util.Random;


/*******************************************************************************
 * Distribution Class
 *
 * This class is used to create a weighted distribution. It takes in a double
 * array containing the weights of each element. When sampled, it returns an
 * integer representing the array position corresponding to the weight.
 *
 * @author Matthew Munson
 * Date: 7/6/2020
 * @version 0.25-alpha
 *
 ******************************************************************************/
public class Distribution
{
    private double[] indices;
    private double sum = 0;

    private Random random;

    /***************************************************************************
     * Distribution Constructor
     *
     * Takes in weights as a double array. Initializes the indices array, which
     * ranges from 0 to the sum of all weights.
     *
     * @since 0.25-alpha
     **************************************************************************/
    Distribution(double[] weights)
    {
        this.random = new Random();
        this.indices = new double[weights.length];

        for(int i = 0; i < weights.length; i++)
        {
            this.sum += weights[i];
            this.indices[i] = this.sum;
        }
    }

    /***************************************************************************
     * Sample
     *
     * Gets a random element with probability based on the inputted weights.
     *
     * @return An integer representing the array index corresponding to the
     *         input weight.
     *
     * @since 0.25-alpha
     **************************************************************************/
    int sample()
    {
        double sample = random.nextDouble();
        sample *= this.sum;

        for(int i = 0; i < this.indices.length; i++)
        {
            if(sample < this.indices[i])
            {
                return i;
            }
        }

        return -1;
    }
}
