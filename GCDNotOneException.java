import java.math.BigInteger;

public class GCDNotOneException extends Exception
{

    BigInteger gcd;

    public GCDNotOneException(BigInteger gcd)
    {
        this.gcd = gcd;
    }

    public BigInteger getGCD()
    {
        return gcd;
    }
}
