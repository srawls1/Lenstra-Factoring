import java.math.BigInteger;
import java.io.*;
import java.util.*;

public class PrimeGen
{
    public static void main(String[] args)
    {
        if (args.length != 3)
        {
            System.out.println("Usage: java PrimeGen <bit length of primes> <number of primes> <output file name>");
            return;
        }
        int bitLength = Integer.parseInt(args[0]);
        int numPrimes = Integer.parseInt(args[1]);
        String fileName = args[2];
        ArrayList<BigInteger> primes = new ArrayList<BigInteger>();
        ArrayList<BigInteger> nums = new ArrayList<BigInteger>();
        Random rng = new Random(System.currentTimeMillis());
        for (int i = 0; i < numPrimes; ++i)
        {
            primes.add(new BigInteger(bitLength, 10, rng));
        }
        for (int i = 0; i < primes.size(); ++i)
        {
            for (int j = i; j < primes.size(); ++j)
            {
                nums.add(primes.get(i).multiply(primes.get(j)));
            }
        }
        try
        {
            PrintStream out = new PrintStream(new File(fileName));
            for (int i = 0; i < nums.size(); ++i)
            {
                out.println(nums.get(i));
            }
            out.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
