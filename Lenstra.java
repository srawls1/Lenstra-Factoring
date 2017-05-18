import java.math.BigInteger;
import java.util.*;

public class Lenstra
{

    static class LenstraOutputs
    {
        public BigInteger p;
        public BigInteger q;
        // n = pq

        public LenstraOutputs(BigInteger pp, BigInteger qq)
        {
            p = pp;
            q = qq;
        }
    }

    static ArrayList<Integer> getPrimesUpTo(int limit)
    {
        ArrayList<Integer> primes = new ArrayList<Integer>();
        boolean[] prime = new boolean[limit + 1];
        for (int i = 0; i < limit; ++i)
        {
            prime[i] = true;
        }
        for (int i = 2; i <= limit; ++i)
        {
            if (!prime[i])
            {
                continue;
            }
            primes.add(i);
            for (int j = i; j < limit; j += i)
            {
                prime[j] = false;
            }
        }
        return primes;
    }

    static LenstraOutputs lenstra(BigInteger n, int B)
    {
        BigInteger a, b, x, y, descriminant, g;
        Random rng = new Random(System.currentTimeMillis());
        do
        {
            a = new BigInteger(n.bitLength(), rng).mod(n);
            x = new BigInteger(n.bitLength(), rng).mod(n);
            y = new BigInteger(n.bitLength(), rng).mod(n);
            b = y.pow(2).subtract(x.pow(3)).subtract(a.multiply(x)).mod(n);
            descriminant = a.pow(3).multiply(BigInteger.valueOf(4))
                .add(b.pow(2).multiply(BigInteger.valueOf(27))).mod(n);
            g = descriminant.gcd(n);
        } while (g.equals(n));
        if (!g.equals(BigInteger.ONE))
        {
            return new LenstraOutputs(g, n.divide(g));
        }
        EllipticCurve e = new EllipticCurve(a, b, n);
        Point p = new Point(x, y);

        ArrayList<Integer> primes = getPrimesUpTo(B);
        for (int prime : primes)
        {
            int product = prime;
            while (product < B)
            {
                product *= prime;
                try
                {
                    p = e.multiplyPoint(prime, p);
                }
                catch (GCDNotOneException ex)
                {
                    return new LenstraOutputs(ex.getGCD(), n.divide(ex.getGCD()));
                }
            }
        }

        return new LenstraOutputs(n, BigInteger.ONE);
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: ./Lenstra <number to factor>");
            return;
        }

        BigInteger n = new BigInteger(args[0]);
        int B = 10000;
        int numTrials = 0;

        LenstraOutputs factors;
        do {
            factors = lenstra(n, B);
            ++numTrials;
            // We'll say increase the base every 100 tries.
            // Don't expect this case to happen often
            if (numTrials % 100 == 0)
            {
                B *= 2;
            }
        } while(factors.p.equals(n) || factors.p.equals(1));

        System.out.println(n + ": " + factors.p + ' ' + factors.q);
    }
}
