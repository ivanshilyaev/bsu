#pragma once
#include "Time.hpp"
#include <string>
#include <ctime>
#include <vector>
using namespace std;

enum Type { passenger, express };
string writeType(Type type);

class Train {
    int number;
    string destination;
    Type type;
    Time departureTime;
    Time wayTime;
protected:
    Train();
    void Clone(const Train &);
public:
    Train(int, string, Type, Time, Time);
    Train(const Train &);
    Train & operator=(const Train &);
    void setNumber(int n) { number=n; }
    void setDestination(string d) { destination=d; }
    void setType(Type t) { type=t; }
    void setDepartureTime(Time dt) { departureTime=dt; }
    void setWayTime(Time wt) { wayTime=wt; }
    int getNumber() const { return number; }
    string getDestination() const { return destination; }
    Type getType() const { return type; }
    Time getDepartureTime() const { return departureTime; }
    Time getWayTime() const { return wayTime; }
    friend ostream & operator<<(ostream &, const Train &);
};

void sortByDepartureTime(vector<Train> &);
bool printByTime(vector<Train> &, Time from, Time to);
bool printByDest(vector<Train> &, string dest);
bool printFastByDest(vector<Train> &, string dest);
Train findFast(vector<Train> &, string dest);
