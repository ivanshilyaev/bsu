#include "Auto.hpp"
#include <iostream>
#include <cstring>
using namespace std;

int Auto::nextID=1;

void Auto::setModel(const char *m) {
    delete[] model;
    model=new char[strlen(m)+1];
    strcpy(model, m);
}

void Auto::getModel(char * &m) const {
    delete[] m;
    m=new char[strlen(model)+1];
    strcpy(m, model);
}

Auto::Auto() : ID(nextID++), model(""), state(good), year(2019) {}

Auto::Auto(char *m, const State s, int y) : ID(nextID++), state(s), year(y) {
    model=new char[strlen(m)+1];
    strcpy(model, m);
}

void Auto::Clone(const Auto &A) {
    model=new char[strlen(A.model)+1];
    strcpy(model, A.model);
    year=A.year;
}

void Auto::Erase() { delete[] model; }

Auto::~Auto() { Erase(); }

Auto::Auto(const Auto &A) : ID(nextID++), state(A.state) {
    Clone(A);
}

Auto & Auto::operator=(const Auto &A) {
    if (this!=&A) {
        Erase();
        Clone(A);
    }
    return *this;
}

bool Auto::operator==(const Auto &A) { return state==A.state; }

ostream & operator<<(ostream &cout, const Auto &A) {
    cout<<"ID: "<<A.ID<<endl;
    cout<<"Model: "<<A.model<<endl;
    cout<<"State: "<<writeState(A.state)<<endl;
    cout<<"Year: "<<A.year;
    return cout;
}

string writeState(State state) {
    switch (state) {
        case best:
            return "best";
            break;
        case good:
            return "good";
            break;
        case requiredRepair:
            return "requiredRepair";
            break;
        case worst:
            return "worst";
            break;
        default:
            break;
    }
}
