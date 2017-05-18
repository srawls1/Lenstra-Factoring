import java.math.BigInteger;

public class Point
{
    public BigInteger x;
    public BigInteger y;
    // (x, y)

    public Point(BigInteger xx, BigInteger yy)
    {
        x = xx;
        y = yy;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof Point))
        {
            return false;
        }
        Point op = (Point)other;
        return x.equals(op.x) && y.equals(op.y);
    }

    public static final Point ZERO = new Point(BigInteger.valueOf(0), BigInteger.valueOf(0));
}
