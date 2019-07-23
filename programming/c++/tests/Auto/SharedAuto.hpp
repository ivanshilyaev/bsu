#pragma once
#include "Auto.hpp"
#include <iostream>
using namespace std;

class SharedAuto : public Auto {
    int sharedHours;
protected:
    SharedAuto();
    void Clone(const SharedAuto &);
public:
    SharedAuto(char *, const State, int, int);
    explicit SharedAuto(const SharedAuto &);
    
    void setSharedHours(int hours) { sharedHours=hours; }
    int getSharedHours() const { return sharedHours; }
    
    SharedAuto & operator=(const SharedAuto &);
    friend ostream & operator<<(ostream &, const SharedAuto &);
};
