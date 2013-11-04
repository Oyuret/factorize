
import java.math.BigInteger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yuri
 */
public class PollardsRho implements Factoring {

    @Override
    public void factorize(BigInteger num) {
        if(num.isProbablePrime(3)) { // 0.125 uncertainty
            System.out.println("fail\n");
            return;
        }
        
        
        
        
        
        System.out.println("test");
    }
    
}
