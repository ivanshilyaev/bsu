#include "Person.hpp"
#include <iostream>
#include <cstring>
using namespace std;

int Person::nextID=0;

Person::Person(const char *f) : ID(++nextID) {
    cout<<"Constructor of Person"<<endl;
    FIO=new char[strlen(f)+1];
    strcpy(FIO, f);
}

Person::~Person() {
    cout<<"Destructor of Person"<<endl;
    delete[] FIO;
}

int Person::getID() const { return ID; }

void Person::setFIO(const char *f) {
    delete[] FIO;
    FIO=new char[strlen(f)+1];
    strcpy(FIO, f);
}

void Person::getFIO(char* &f) const {
    delete[] f;
    f=new char[strlen(FIO)+1];
    strcpy(f, FIO);
}

void Person::display() {
    cout<<FIO<<endl;
}
