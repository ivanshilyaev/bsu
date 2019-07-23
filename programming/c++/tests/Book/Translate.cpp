#include "Translate.hpp"
#include <iostream>
using namespace std;

void Translate::getLanguage(char * &l) const {
    l = new char[strlen(language) + 1];
    strcpy(l, language);
}

Translate::Translate() : Book() {}

Translate::Translate(const char *n, const Kind k, int y, const char *l) : Book(n, k, y) {
    language = new char[strlen(l) + 1];
    strcpy(const_cast<char *>(language), l);
}

Translate::Translate(const Translate &t) : Book(t) {
    language = new char[strlen(t.language) + 1];
    strcpy(const_cast<char *>(language), t.language);
}

ostream & operator<<(ostream &cout, const Translate &t) {
    cout << (Book&)t << endl;
    cout << "Language: " << t.language;
    return cout;
}
