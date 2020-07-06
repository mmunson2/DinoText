package DinoParser.List;

import java.util.Random;

public class Distribution
{
    private double[] indices;
    private double sum = 0;

    private Random random;

    public Distribution(double[] weights)
    {
        this.random = new Random();
        this.indices = new double[weights.length];

        for(int i = 0; i < weights.length; i++)
        {
            this.sum += weights[i];
            this.indices[i] = this.sum;
        }
    }

    public int sample()
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


    public static void main(String[] args)
    {
        double[] weights = {1.0, 5.0, 10.0};

        Distribution testDistribution = new Distribution(weights);

        for(int i = 0; i < 10; i++)
        {
            System.out.println(testDistribution.sample());
        }

    }




}
