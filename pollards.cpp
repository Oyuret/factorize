#include "pollards.h"
#include "primes.h"
#include <iostream>

bool pollards::factor(mpz_t n, std::vector<mpz_class> & factors) {
  primes::factor(n, factors,0);
  pollards::factor(n, factors, 2);
}

bool pollards::factor(mpz_t n, std::vector<mpz_class> & factors, int initValue) {

  bool rootFound = false;

  if (mpz_cmp_si(n, 1) == 0)
    return true;

  if (mpz_sizeinbase(n, 2) >= 88) {
    return false;
  }

  int prime = mpz_probab_prime_p(n, 5);

  if (prime != 0) {

    mpz_class tmp (n);
    factors.push_back(tmp);
    return true;
  } else if(mpz_perfect_power_p(n)) {
    mpz_t sqrt;
    mpz_init(sqrt);
    mpz_sqrt(sqrt, n);

    pollards::factor(sqrt, factors);
    std::vector<mpz_class> factorTemp;
    pollards::factor(sqrt, factorTemp);

    for(int i=0; i< 2; ++i) {
      for(int j=0; j< factorTemp.size(); ++j) {
        factors.push_back(factorTemp[j]);
      }
    }
    rootFound=true;

  } /*else if(!rootFound && mpz_perfect_power_p(n)) {

    mpz_t base;
    mpz_init_set_ui(base, 2);
    int exp = 3;

    mpz_t ONE;
    mpz_init_set_ui(ONE, 1);

    do{
      bool b = mpz_root(base, n, exp);
      if(b)
        break;
      else
        ++exp;
    }
    while(mpz_cmp(base, ONE)==0);

    std::vector<mpz_class> factorTemp;
    pollards::factor(base, factorTemp);
    for(int i=0; i< exp; ++i) {
      for(int j=0; j< factorTemp.size(); j++) {
        factors.push_back(factorTemp[j]);
      }
    }

  }*/
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
