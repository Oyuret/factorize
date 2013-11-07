#include <gmp.h>
#include <gmpxx.h>
#include <iostream>
#include "pollards.h"

int main() {
    mpz_t a;
    mpz_init(a);

    std::vector<mpz_class> foundPrimes;

    char string [32];
    while (scanf("%s", &string) != EOF) {
        mpz_set_str(a, string, 10);




        if (pollards::factor(a, foundPrimes)) {
            for (int i = 0; i < foundPrimes.size(); i++) {
                std::cout << foundPrimes[i] << std::endl;
            }
        }
        else
            std::cout << "fail" << std::endl;

     foundPrimes.clear();

        std::cout << std::endl;
    }
}

