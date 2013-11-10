#include <gmp.h>
#include <gmpxx.h>
#include <vector>

class primes {
    static int small_primes[9999];
    static mpz_t sqrt;	
    static mpz_t prime;
    public:
    static void factor(mpz_t, std::vector<mpz_class> &, int);
    static void factor(mpz_t, std::vector<mpz_class> &);
};
