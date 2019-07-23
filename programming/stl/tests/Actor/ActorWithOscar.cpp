#include "ActorWithOscar.hpp"
#include <iostream>
using namespace std;

ActorWithOscar::ActorWithOscar() : Actor(), countOfPrizes(0) {}

ActorWithOscar::ActorWithOscar(char *s, Role r, int c, const int y, int cp) : Actor(s, r, c, y), countOfPrizes(cp) {}

ActorWithOscar::ActorWithOscar(const ActorWithOscar &A) : Actor(A), countOfPrizes(A.countOfPrizes) {}

ActorWithOscar & ActorWithOscar::operator=(const ActorWithOscar &A) {
    if (this!=&A) {
        Erase();
        Clone(A);
    }
    return *this;
}

ostream & operator<<(ostream &cout, const ActorWithOscar &A) {
    cout<<(Actor&)A<<endl;
    cout<<"Количество полученных призов: "<<A.getCountOfPrizes();
    return cout;
}
