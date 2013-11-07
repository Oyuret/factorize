#include "pollards.h"
#include "primes.h"
#include <iostream>

bool pollards::factor(mpz_t n, std::vector<mpz_class> & factors) {
    primes::factor(n, factors,0);
    pollards::factor(n, factors, 2);
}

bool pollards::factor(mpz_t n, std::vector<mpz_class> & factors, int initValue) {


    if (mpz_cmp_si(n, 1) == 0)
        return true;

    if (mpz_sizeinbase(n, 2) >= 85) {
        return false;
    }

    int prime = mpz_probab_prime_p(n, 5);

    if (prime != 0) {

        mpz_class tmp (n);
        factors.push_back(tmp);
        return true;
    }
    else {

        mpz_t x, y, tmp, factor;

        mpz_init_set_ui(x, initValue);
        mpz_init_set_ui(y, initValue);
        mpz_init(tmp);
        mpz_init_set_ui(factor, 1);

        int r = 1;
        int k = 1;

        while (mpz_cmp_si(factor, 1) == 0) {

            for (int i = 0; i < 14; i++) {
                if (r == k) {
                    mpz_set(x, y);
                    r *= 2;
                    k = 0;
                }

                mpz_mul(y, y, y);
                mpz_add_ui(y, y, 1);
                mpz_mod(y, y, n);
                k++;

                mpz_sub(tmp, y, x);
                mpz_abs(tmp, tmp);
                mpz_mul(factor, factor, tmp);
            }

            mpz_gcd(factor, factor, n);
        }

        if (mpz_cmp(factor, n) == 0) {
            return pollards::factor(n, factors, initValue+1);

        }
        else {
            mpz_divexact(n, n, factor);
            if (pollards::factor(n, factors) && pollards::factor(factor, factors))
                return true;
            else
                return false;
        }
    }
}
