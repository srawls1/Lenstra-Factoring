import java.math.BigInteger;

public class EllipticCurve
{
    public BigInteger a;
    public BigInteger b;
    public BigInteger p;
    // y^2 = x^3 + ax + b mod p

    public EllipticCurve(BigInteger aa, BigInteger bb, BigInteger pp)
    {
        a = aa;
        b = bb;
        p = pp;
    }

    Point addPoints(Point p1, Point p2) throws GCDNotOneException
    {
        if (p1 == Point.ZERO)    return p2;
        if (p2 == Point.ZERO)    return p1;

        BigInteger dy = (p1.equals(p2)) ? (p1.x.pow(2).multiply(BigInteger.valueOf(3)).add(a))
            : (p2.y.subtract(p1.y));
        BigInteger dx = (p1 == p2) ? (p2.y.multiply(BigInteger.valueOf(2)))
            : (p2.x.subtract(p1.x));
        dy = dy.mod(p);
        dx = dx.mod(p);

        if (dx.equals(BigInteger.ZERO))    return Point.ZERO;


        try
        {
            BigInteger m = dy.multiply(dx.modInverse(p)).mod(p);
            BigInteger x = m.pow(2).subtract(p1.x).subtract(p2.x).mod(p);
            BigInteger y = m.multiply(p1.x.subtract(x)).subtract(p1.y).mod(p);
            return new Point(x, y);
        }
        catch (ArithmeticException ex)
        {
            throw new GCDNotOneException(dx.gcd(p));
        }
    }

    public Point multiplyPoint(long k, Point p) throws GCDNotOneException
    {
        Point powOfTwo = p;
        Point result = Point.ZERO;
        while (k > 0)
        {
            if ((k & 1) != 0)
            {
                result = addPoints(result, powOfTwo);
            }
            powOfTwo = addPoints(powOfTwo, powOfTwo);
            k = k >> 1;
        }
        return result;
    }
}
