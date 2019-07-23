#pragma once
#pragma warning(disable:4996)
#include <string>
#include <iostream>
using namespace std;

enum State { best, good, requiredRepair, worst };
string writeState(State state);

class Auto {
    const int ID;
    char *model;
    const State state;
    int year;
protected:
    Auto();
    static int nextID;
    void Clone(const Auto &);
    void Erase();
public:
    Auto(char *, const State, int);
    explicit Auto(const Auto &);
    virtual ~Auto();
    
    void setModel(const char *);
    void setYear(int y) { year=y; }
    
    void getModel(char * &) const;
    State getState() const { return state; }
    int getYear() const { return year; }
    
    Auto & operator=(const Auto &);
    bool operator==(const Auto &);
    friend ostream & operator<<(ostream &, const Auto &);
};

