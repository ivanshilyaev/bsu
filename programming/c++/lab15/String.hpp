#pragma once
#include <iostream>
#include <cstring>
using namespace std;

// http://techn.sstu.ru/kafedri/%D0%BF%D0%BE%D0%B4%D1%80%D0%B0%D0%B7%D0%B4%D0%B5%D0%BB%D0%B5%D0%BD%D0%B8%D1%8F/1/MetMat/murashev/oop/lec/lec4.htm

// https://ru.stackoverflow.com/questions/833273/%D0%9F%D0%BE%D0%B4%D1%81%D1%87%D0%B5%D1%82-%D1%81%D1%81%D1%8B%D0%BB%D0%BE%D0%BA-%D0%B2-c

class String {
private:
    struct StringRepeater {
        char *s;
        int n;
        StringRepeater() { n=1; }
    };
    StringRepeater *repeater;
    void Clone(const String &);
    void Erase();
public:
    String();
    String(const char *);
    String(const String &);
    ~String();
    String & operator=(const char *);
    String & operator=(const String &);
    bool operator==(const String &) const;
    bool operator!=(const String &) const;
    bool operator>(const String &) const;
    bool operator<(const String &) const;
    String operator+(const String &);
    String operator+(const char *); // +
    int Size() const;
    char operator[](int) const;
    friend istream & operator>>(istream &, String &);
    friend ostream & operator<<(ostream &, const String &);
};
