#include "Book.hpp"
#include <cstring>
#include <iostream>
using namespace std;

int Book::NextID = 1;

void Book::setYear(int y) { year = y; }

void Book::getName(char * &n) const {
    n = new char[strlen(name) + 1];
    strcpy(n, name);
}

Book::Book() : ID(NextID++), kind(novel), year(2019) {}

Book::Book(const char *n, const Kind k, int y) : ID(NextID++), kind(k), year(y) {
    name = new char[strlen(n) + 1];
    strcpy(const_cast<char *>(name), n);
}

void Book::Clone(const Book &b) {
    year = b.year;
}

Book::Book(const Book &b) : ID(NextID++), kind(b.kind) {
    Clone(b);
    name = new char[strlen(b.name) + 1];
    strcpy(const_cast<char *>(name), b.name);
}

Book & Book::operator=(const Book &b) {
    if (this != &b) {
        Clone(b);
    }
    return *this;
}

ostream & operator<<(ostream &cout, const Book &b) {
    cout << "Name: " << b.name << endl;
    cout << "ID: " << b.ID << endl;
    cout << "Kind: " << writeKind(b.kind) << endl;
    cout << "Year: " << b.year;
    return cout;
}

bool Book::operator==(const Book &b) const {
    return (kind == b.kind);
}

string writeKind(Kind kind) {
    switch (kind)
    {
        case novel:
            return "Novel";
            break;
        case story:
            return "Story";
            break;
        case narrative:
            return "Narrative";
            break;
        case poetry:
            return "Poetry";
            break;
        case poem:
            return "Poem";
            break;
        default:
            break;
    }
}

