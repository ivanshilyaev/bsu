#pragma once
#include <iostream>
#include <string>
using namespace std;

class MyException : public exception {
private:
    string message;
public:
    MyException(string msg) : message(msg) {}
    const string what() {
        return message;
    }
};
