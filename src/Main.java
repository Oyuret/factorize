import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yuri
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        Factoring factorizer = new PollardsRho();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        String line;

        while(!br.ready());
        while (br.ready()) {
            line = br.readLine();
            factorizer.factorize(new BigInteger(line));
            System.out.println();
        } // End while
        
    } // main
}
