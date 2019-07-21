#pragma once
#include <string>
#include <iostream>
using namespace std;

class Author {
    string surname;
    string name;
    string middlename;
    void Clone(const Author &);
public:
    Author(string, string, string);
    Author(const Author&);
    string getSurname() const { return surname; }
    string getName() const { return name; }
    string getMiddlename() const { return middlename; }
    Author & operator=(const Author &);
    bool operator<(const Author &) const;
    bool operator>(const Author &) const;
    bool operator==(const Author &) const;
    friend ostream & operator<<(ostream &, const Author &);
};
