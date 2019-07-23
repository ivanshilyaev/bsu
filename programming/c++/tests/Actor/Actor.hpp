#pragma once
#include <iostream>
using namespace std;

enum Role { tragedian, comedian, travesty, ingenue };
string writeRole(Role role);

class Actor {
    char *surname;
    Role role;
    int countOfFilms;
    const int yearOfBirth;
    const int ID;
protected:
    Actor();
    static int nextID;
    void Clone(const Actor &);
    void Erase();
public:
    Actor(char *, Role, int, const int);
    virtual ~Actor();
    explicit Actor(const Actor &);
    
    void setSurname(const char *s);
    void setRole(Role r) { role=r; }
    void setCountOfFilms(int c) { countOfFilms=c; }
    
    void getSurname(char * &s) const;
    Role getRole() const { return role; }
    int getCountOfFilms() const { return countOfFilms; }
    int getYearOfBirth() const { return yearOfBirth; }
    int getID() const { return ID; }
    
    Actor & operator=(const Actor &);
    bool operator==(const Actor &);
    bool operator<(const Actor &);
    bool operator>(const Actor &);
    friend ostream & operator<<(ostream &, const Actor &);
};
