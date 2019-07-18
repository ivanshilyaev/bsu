#include "Student.hpp"
#include <iostream>
using namespace std;

Student::Student(const char *f, const int c, const int g) : Person(f) {
    cout<<"Constructor of Student"<<endl;
    course=c;
    group=g;
}

Student::~Student() {
    cout<<"Destructor of Student"<<endl;
}

void Student::setCourse(const int c) { course=c; }

int Student::getCourse() const { return course; }

void Student::setGroup(const int g) { group=g; }

int Student::getGroup() const { return group; }

void Student::display() {
    Person::display();
    cout<<course<<" course, "<<group<<" group"<<endl;
}
