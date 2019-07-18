#include "Professor.hpp"
#include <iostream>
#include <cstring>
using namespace std;

Professor::Professor(const char *f, const char *p) : Person(f) {
    cout<<"Constructor of Professor"<<endl;
    pulpit=new char[strlen(p)+1];
    strcpy(pulpit, p);
}

Professor::~Professor() {
    cout<<"Destructor of Professor"<<endl;
    delete[] pulpit;
}

void Professor::setPulpit(const char *p) {
    delete pulpit;
    pulpit=new char[strlen(p)+1];
    strcpy(pulpit, p);
}

void Professor::getPulpit(char* &p) const {
    delete[] p;
    p=new char[strlen(pulpit)+1];
    strcpy(p, pulpit);
}

void Professor::display() {
    Person::display();
    cout<<"pulpit: "<<pulpit<<endl;
}
