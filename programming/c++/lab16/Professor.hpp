#pragma once
#include "Person.hpp"

class Professor: public Person {
private:
    char *pulpit; //кафедра
public:
    Professor(const char*, const char*);
    ~Professor();
    void setPulpit(const char*);
    void getPulpit(char* &) const;
    void display();
};
