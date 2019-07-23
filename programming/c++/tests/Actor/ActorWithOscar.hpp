#pragma once
#include "Actor.hpp"
#include <iostream>
using namespace std;

class ActorWithOscar : public Actor {
    int countOfPrizes;
protected:
    ActorWithOscar();
public:
    ActorWithOscar(char *s, Role r, int c, const int y, int cp);
    explicit ActorWithOscar(const ActorWithOscar &);
    
    void setCountOfPrizes(int c) { countOfPrizes=c; }
    int getCountOfPrizes() const { return countOfPrizes; }
    
    ActorWithOscar & operator=(const ActorWithOscar &);
    friend ostream & operator<<(ostream &, const ActorWithOscar &);
};
