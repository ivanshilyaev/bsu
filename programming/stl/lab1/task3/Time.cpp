#include "Time.hpp"
#include <iostream>
using namespace std;

Time::Time() {
    time_t result=time(nullptr);
    struct tm *aTime = (localtime(&result));
    hh=aTime->tm_hour;
    mm=aTime->tm_min;
}

Time::Time(int h, int m) : hh((h>=0 && h<24)?h:0), mm((m>=0 && m<60)?m:0) {}

Time::Time(const Time &T) {
    hh=T.hh;
    mm=T.mm;
}

Time & Time::operator=(const Time &t) {
    if (this!=&t) {
        hh=t.hh;
        mm=t.mm;
    }
    return *this;
}

ostream& operator<<(ostream &cout, const Time &t) {
    if (t.mm<10)
        cout<<t.hh<<":0"<<t.mm;
    else
        cout<<t.hh<<":"<<t.mm;
    return cout;
}
