
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
        BigInteger divisor = rho(num);
        //BigInteger divisor = brents(num);

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
            if ((new Date().getTime() - start) > 150) {
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
        return null;
    }
    
}
