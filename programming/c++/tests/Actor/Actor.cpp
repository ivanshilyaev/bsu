#include "Actor.hpp"
#include <cstring>
using namespace std;

int Actor::nextID=1;

void Actor::setSurname(const char *s) {
    delete surname;
    surname=new char[strlen(s)+1];
    strcpy(surname, s);
}

void Actor::getSurname(char * &s) const {
    delete[] s;
    s=new char[strlen(surname)+1];
    strcpy(s, surname);
}

Actor::Actor() : ID(nextID++), surname("Surname"), role(comedian), countOfFilms(0), yearOfBirth(2019) {}

Actor::Actor(char *s, Role r, int c, const int y) : ID(nextID++), role(r), countOfFilms(c), yearOfBirth(y) {
    surname=new char[strlen(s)+1];
    strcpy(surname, s);
}

void Actor::Clone(const Actor &A) {
    surname=new char[strlen(A.surname)+1];
    strcpy(surname, A.surname);
    role=A.role;
    countOfFilms=A.countOfFilms;
}

void Actor::Erase() {
    delete surname;
}
Actor::~Actor() { Erase(); }

Actor::Actor(const Actor &A) : ID(nextID++), yearOfBirth(A.yearOfBirth) {
    Clone(A);
}

Actor & Actor::operator=(const Actor &A) {
    if (this!=&A) {
        Erase();
        Clone(A);
    }
    return *this;
}

bool Actor::operator==(const Actor &A) { return countOfFilms==A.countOfFilms; }

bool Actor::operator<(const Actor &A) { return countOfFilms==A.countOfFilms; }

bool Actor::operator>(const Actor &A) { return countOfFilms==A.countOfFilms; }

ostream & operator<<(ostream &cout, const Actor &A) {
    cout<<"ID: "<<A.ID<<endl;
    cout<<"Фамилия: "<<A.surname<<endl;
    cout<<"Роль: "<<writeRole(A.role)<<endl;
    cout<<"Число фильмов: "<<A.countOfFilms<<endl;
    cout<<"Год рождения: "<<A.yearOfBirth;
    return cout;
}

string writeRole(Role role) {
    switch (role) {
        case tragedian:
            return "трагик";
            break;
        case comedian:
            return "комик";
            break;
        case travesty:
            return "травести";
            break;
        case ingenue:
            return "инженю";
            break;
        default:
            break;
    }
}
