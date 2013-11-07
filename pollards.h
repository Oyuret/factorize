#include <gmp.h>
#include <gmpxx.h>
#include <vector>

class pollards {

    public:
     static bool factor(mpz_t, std::vector<mpz_class> &);
     static bool factor(mpz_t, std::vector<mpz_class> &, int);

};
