#include "String.hpp"
#include <cstring>

String::String() : repeater(new StringRepeater) {}

String::String(const char *st) : repeater(new StringRepeater) {
    repeater->s=new char[strlen(st)+1];
    strcpy(repeater->s, st);
}

String::String(const String &P) {
    Clone(P);
}

String::~String() {
    Erase();
}

void String::Clone(const String &P) {
    repeater=P.repeater;
    repeater->n++;
}

void String::Erase() {
    if (--repeater->n==0) {
        delete[] repeater->s;
        delete repeater;
    }
}

String & String::operator=(const char *st) {
    if (repeater->n > 1) {
        --repeater->n;
        repeater=new StringRepeater;
    }
    else
        delete repeater->s;
    repeater->s=new char[strlen(st)+1];
    strcpy(repeater->s, st);
    return *this;
}

String & String::operator=(const String &P) {
    if (this!=&P) {
        Erase();
        Clone(P);
    }
    return *this;
}

bool String::operator==(const String &P) const {
    return strcmp(repeater->s, P.repeater->s)==0;
}

bool String::operator!=(const String &P) const {
    return !(*this==P);
}

bool String::operator>(const String &P) const {
    return strcmp(repeater->s, P.repeater->s)>0;
}

bool String::operator<(const String &P) const {
    return strcmp(repeater->s, P.repeater->s)<0;
}

String String::operator+(const String &P) {
    String P2;
    P2.repeater->s=new char[strlen(this->repeater->s) + strlen(P.repeater->s) + 1];
    strcpy(P2.repeater->s, this->repeater->s);
    strcat(P2.repeater->s, P.repeater->s);
    return P2;
}

// +
String String::operator+(const char *c) {
    String P1(c);
    String P2;
    P2.repeater->s=new char[strlen(this->repeater->s) + strlen(P1.repeater->s) + 1];
    strcpy(P2.repeater->s, this->repeater->s);
    strcat(P2.repeater->s, P1.repeater->s);
    return P2;
}

int String::Size() const {
    return (int)strlen(repeater->s);
}

char String::operator[](int i) const {
    if (i<0 || i>=strlen(repeater->s))
        throw "Invalid index number";
    return repeater->s[i];
}

istream & operator>>(istream &cin, String &P) {
    char buffer[256];
    cin>>buffer;
    P=buffer;
    return cin;
}

ostream & operator<<(ostream &cout, const String &P) {
    cout << P.repeater->s << " [" << P.repeater->n << "]";
    return cout;
}
