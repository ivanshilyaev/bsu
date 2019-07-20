#include "Train.hpp"
#include <iostream>
#include <vector>
#include <iterator>
#include <algorithm>
using namespace std;

string writeType(Type type) {
    switch (type) {
        case 0:
            return "пассажирский";
            break;
        case 1:
            return "скорый";
            break;
        default:
            break;
    }
}

Train::Train(int n, string d, Type t, Time dt, Time wt) : number(n), destination(d), type(t), departureTime(dt), wayTime(wt) {}

void Train::Clone(const Train &t) {
    number=t.number;
    destination=t.destination;
    type=t.type;
    departureTime=t.departureTime;
    wayTime=t.wayTime;
}

Train::Train(const Train &t) {
    Clone(t);
}

Train & Train::operator=(const Train &t) {
    if (this!=&t) {
        Clone(t);
    }
    return *this;
}

ostream & operator<<(ostream &cout, const Train &t) {
    cout<<"Номер: "<<t.number<<endl;
    cout<<"Пункт назначения: "<<t.destination<<endl;
    cout<<"Тип поезда: "<<writeType(t.type)<<endl;
    cout<<"Время отправления: "<<t.departureTime<<endl;
    cout<<"Время в пути: "<<t.wayTime;
    return cout;
}
