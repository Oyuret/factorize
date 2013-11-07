#include <gmp.h>
#include <gmpxx.h>
#include <vector>

class primes {
    static int small_primes[9999];
    public:
    static void factor(mpz_t, std::vector<mpz_class> &, int);

};
