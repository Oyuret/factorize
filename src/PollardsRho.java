
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yuri
 */
public class PollardsRho implements Factoring {

    private final static BigInteger ZERO = new BigInteger("0");
    private final static BigInteger ONE = new BigInteger("1");
    private final static BigInteger TWO = new BigInteger("2");
    private final static Random random = new Random();
    LinkedList<BigInteger> factors;
    long start;

    @Override
    public void factorize(BigInteger num) {
        start = new Date().getTime();
        factors = new LinkedList<>();

        pollards(num);

        if (factors == null) {
            System.out.println("fail");
            return;
        }

        for (BigInteger factor : factors) {
            System.out.println(factor);
        }

    }

    private void pollards(BigInteger num) {
        if (num.compareTo(ONE) == 0) {
            return;
        }

        if (num.isProbablePrime(10)) {
            factors.add(num);
            return;
        }

        for (BigInteger prime : Primes.primes) {
            BigInteger div = num.mod(prime);
            if (div.compareTo(ZERO)==0 && factors != null) {
                factors.add(prime);
                pollards(num.divide(prime));
                return;
            }
        }

        //BigInteger divisor = rho(num);
        BigInteger divisor = brent(num);

        if (divisor == null) {
            factors = null;
            return;
        }

        pollards(divisor);
        pollards(num.divide(divisor));
    }

    private BigInteger rho(BigInteger n) {

        // check divisibility by 2
        if (n.mod(TWO).compareTo(ZERO) == 0) {
            return TWO;
        }

        BigInteger divisor;
        BigInteger c = new BigInteger(n.bitLength(), random);
        BigInteger a = new BigInteger(n.bitLength(), random);
        BigInteger b = a;



        do {
            if ((new Date().getTime() - start) > 200) {
                return null;
            }
            a = a.multiply(a).mod(n).add(c).mod(n);
            b = b.multiply(b).mod(n).add(c).mod(n);
            b = b.multiply(b).mod(n).add(c).mod(n);
            divisor = a.subtract(b).gcd(n);
        } while ((divisor.compareTo(ONE)) == 0);

        return divisor;
    }

    private BigInteger brent(BigInteger n) {
        if (n.mod(TWO).compareTo(ZERO) == 0) {
            return TWO;
        }

        BigInteger y = new BigInteger(n.bitLength(), random);
        BigInteger c = new BigInteger(n.bitLength(), random);
        BigInteger m = new BigInteger(n.bitLength(), random);
        BigInteger x = ONE;
        BigInteger g = BigInteger.ONE;
        BigInteger r = BigInteger.ONE;
        BigInteger q = BigInteger.ONE;
        BigInteger k;
        BigInteger ys = ONE;
        BigInteger i;

        while (g.compareTo(ONE) == 0) {
            x = y;
            for (i = BigInteger.ZERO; i.compareTo(r) < 0; i = i.add(ONE)) {
                y = ((y.multiply(y)).mod(n).add(c)).mod(n);
            }
            k = ZERO;
            while (k.compareTo(r) < 0 && g.compareTo(ONE) == 0) {
                ys = y;
                if ((new Date().getTime() - start) > 190) {
                    return null;
                }
                for (i = ZERO; i.compareTo(m.min(r.subtract(k))) < 0; i = i.add(ONE)) {
                    y = ((y.multiply(y)).mod(n).add(c)).mod(n);
                    q = q.multiply(x.subtract(y).abs()).mod(n);
                }
                g = q.gcd(n);
                k = k.add(m);
            }
            r = r.multiply(TWO);
        }

        if (g.compareTo(n) == 0) {
            while (true) {
                if ((new Date().getTime() - start) > 190) {
                    return null;
                }
                ys = ((ys.multiply(ys)).mod(n).add(c)).mod(n);
                g = (x.subtract(ys).abs()).gcd(n);
                if (g.compareTo(ONE) > 0) {
                    break;
                }
            }
        }
        return g;
    }
}
