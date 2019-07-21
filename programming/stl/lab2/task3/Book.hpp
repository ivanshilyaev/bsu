#pragma once
#include "Author.hpp"
#include <list>
#include <string>
#include <iostream>
using namespace std;

class Book {
    int numberUDC;
    list<Author> authors;
    string title;
    int year;
    
    void Clone(const Book &);
public:
    Book(int, list<Author>, string, int);
    Book(const Book &);
    string getTitle() const { return title; }
    void getAuthors(list<Author> &) const;
    void setAuthors(list<Author> &);
    Book & operator=(const Book &);
    bool operator<(const Book &) const;
    bool operator>(const Book &) const;
    bool operator==(const Book &) const;
    friend ostream & operator<<(ostream &, const Book &);
};
