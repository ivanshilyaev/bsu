#pragma once
#include "Person.hpp"

class Student: public Person {
private:
    int course;
    int group;
public:
    Student(const char *, const int, const int);
    ~Student();
    void setCourse(const int);
    int getCourse() const;
    void setGroup(const int);
    int getGroup() const;
    void display();
};
