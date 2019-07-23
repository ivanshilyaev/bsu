#include "SharedAuto.hpp"
#include <iostream>
using namespace std;

SharedAuto::SharedAuto() : Auto(), sharedHours(0) {}

SharedAuto::SharedAuto(char *m, const State s, int y, int h) : Auto(m, s, y), sharedHours(h) {}

void SharedAuto::Clone(const SharedAuto &A) {
    Auto::Clone(A);
    sharedHours=A.sharedHours;
}

SharedAuto::SharedAuto(const SharedAuto &A) : Auto(A), sharedHours(A.sharedHours) {}

SharedAuto & SharedAuto::operator=(const SharedAuto &A) {
    if (this!=&A) {
        Erase();
        Clone(A);
    }
    return *this;
}

ostream & operator<<(ostream &cout, const SharedAuto &A) {
    cout<<(Auto&)A<<endl;
    cout<<"Shared hours: "<<A.sharedHours;
    return cout;
}
