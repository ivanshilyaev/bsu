#pragma once
#include "Book.hpp"

class Translate : public Book {
    const char *language;
protected:
    Translate();
public:
    Translate(const char *, const Kind, int, const char *);
    explicit Translate(const Translate &);
    
    void getLanguage(char * &) const;
    friend ostream & operator<<(ostream &, const Translate &);
};
