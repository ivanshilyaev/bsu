#pragma once
#include <iostream>
using namespace std;

class Time {
    int hh,mm;
public:
    Time(); //current time
    Time(int h, int m);
    Time(const Time &);
    Time & operator=(const Time &t);
    bool operator<(const Time &t) {
        return hh<t.hh || (hh==t.hh && mm<t.mm);
    }
    bool operator>(const Time &t) {
        return hh>t.hh || (hh==t.hh && mm>t.mm);
    }
    bool operator==(const Time &t) {
        return hh==t.hh && mm==t.mm;
    }
    bool operator<=(const Time &t) {
        return !(*this>t);
    }
    bool operator>=(const Time &t) {
        return !(*this<t);
    }
    friend ostream& operator<<(ostream&, const Time &t);
};
