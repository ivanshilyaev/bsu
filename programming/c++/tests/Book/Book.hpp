#pragma once
#include <string>
using namespace std;

enum Kind { novel, story, narrative, poetry, poem };
string writeKind(Kind kind);

class Book {
    const char *name;
    const int ID;
    const Kind kind;
    int year;
protected:
    Book();
    static int NextID;
    void Clone(const Book &);
public:
    Book(const char *, const Kind, int);
    explicit Book(const Book &);
    
    void setYear(int y);
    
    void getName(char * &) const;
    int getID() const { return ID; }
    Kind getKind() const { return kind; }
    int getYear() const { return year; }
    
    Book & operator=(const Book &);
    friend ostream & operator<<(ostream &, const Book &);
    virtual bool operator==(const Book &) const;
};
