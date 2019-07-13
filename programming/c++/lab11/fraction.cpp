#include <iostream>
#include <stdio.h>
#include "fraction.h"
#include "MyException.hpp"
using namespace std;

int Fraction::nod(int a, int b) {
    if (b==0)
        return a;
    else
        return nod(b, a%b);
}

void Fraction::Cannon() {
    int k=nod(abs(n), abs(d));
    n/=k; d/=k;
}

int Fraction::next_ID=0;

Fraction::Fraction() : n(0), d(1), ID(++next_ID) {}

Fraction::Fraction(int an, int ad) : ID(++next_ID) {
    if (ad==0)
        throw MyException("Error: d=0");
    n=an; d=ad;
}

//конструктор копирования
Fraction::Fraction(const Fraction& a): ID(++next_ID) {
    n=a.n;
    d=a.d;
}

int Fraction::GetN() const { return n; }

int Fraction::GetD() const { return d; }

void Fraction::SetN(int an) { n=an; Cannon(); }

void Fraction::SetD(int ad) {
    if (ad==0)
        throw MyException("Error: d=0");
    d=ad;
    Cannon();
}


int Fraction::GetID() const { return ID; }

Fraction Fraction::operator - () const { return Fraction(-n,d); }

Fraction Fraction::operator ! () const { return Fraction(d,n); }

//префиксный инкремент
Fraction& Fraction::operator ++ () {
    n+=d;
    return *this;
}

//постфиксный инкремент
Fraction Fraction::operator ++ (int a) {
    Fraction t(*this);
    n+=d;
    return t;
}

//префиксный декремент
Fraction& Fraction::operator--() {
    n-=d;
    return *this;
}

//постфиксный декремент
Fraction Fraction::operator--(int a) {
    Fraction b(*this);
    n-=d;
    return b;
}

//присваивание
Fraction& Fraction::operator = (const Fraction& a) {
    if (this != &a) {
        n=a.GetN();
        d=a.GetD();
    }
    return *this;
}

//сложение с дробью и присваивание
Fraction& Fraction::operator+=(const Fraction& a) {
    n=n*a.d+d*a.n;
    d=d*a.d;
    Cannon();
    return *this;
}

//вычитание дроби и присваивание
Fraction& Fraction::operator-=(const Fraction& a) {
    n=n*a.d-d*a.n;
    d=d*a.d;
    Cannon();
    return *this;
}

//умножение на дробь и присваивание
Fraction& Fraction::operator*=(const Fraction& a) {
    n=n*a.n;
    d=d*a.d;
    Cannon();
    return *this;
}


//деление на дробь и присваивание
Fraction& Fraction::operator/=(const Fraction& a) {
    if (a.n==0)
        throw MyException("Error: division by zero");
    n=n*a.d;
    d=d*a.n;
    Cannon();
    return *this;
}

//сложение с числом и присваивание
Fraction& Fraction::operator+=(int a) {
    n+=a*d;
    return *this;
}

//вычитание числа и присваивание
Fraction& Fraction::operator-=(int a) {
    n-=a*d;
    return *this;
}

//умножение на число и присваивание
Fraction& Fraction::operator*=(int a) {
    n*=a;
    Cannon();
    return *this;
}

//деление на число и присваивание
Fraction& Fraction::operator/=(int a) {
    d*=a;
    Cannon();
    return *this;
}

//сложение
Fraction Fraction::operator+(const Fraction& a) const {
    Fraction b(*this);
    b+=a;
    return b;
}

//дробь+число
Fraction Fraction::operator+(int a) const {
    Fraction b(*this);
    b.n+=a*d;
    return b;
}

//число+дробь
Fraction operator+(int a, const Fraction& b) {
    Fraction t(b);
    t=t+a;
    return t;
}

//вычитание
Fraction Fraction::operator-(const Fraction& a) const {
    Fraction b(*this);
    b-=a;
    return b;
}

//дробь-число
Fraction Fraction::operator-(int a) const {
    Fraction b(*this);
    b.n-=a*b.d;
    return b;
}

//число-дробь
Fraction operator-(int a, const Fraction& t) {
    Fraction b(a*t.d, t.d);
    b=b-t;
    return b;
}

//умножение
Fraction Fraction::operator*(const Fraction& a) const {
    Fraction b(*this);
    b*=a;
    return b;
}

//дробь*число
Fraction Fraction::operator*(int a) const {
    Fraction t(*this);
    t.n*=a;
    t.Cannon();
    return t;
}

//число*дробь
Fraction operator*(int a, const Fraction& t) {
    Fraction b(t);
    b=b*a;
    return b;
}

//деление
Fraction Fraction::operator/(const Fraction& a) const {
    Fraction b(*this);
    b/=a;
    return b;
}

//дробь/число
Fraction Fraction::operator/(int a) const {
    Fraction b(*this);
    b.d*=a;
    b.Cannon();
    return b;
}

//число/дробь
Fraction operator/(int a, const Fraction& t) {
    Fraction b(a*t.d, t.n);
    b.Cannon();
    return b;
}

//равно
bool Fraction::operator==(const Fraction& a) const { return (n==a.n && d==a.d); }

//не равно
bool Fraction::operator!=(const Fraction& a) const { return (n!=a.n || d!=a.d); }

//больше
bool Fraction::operator>(const Fraction& a) const { return (double)n/d>(double)a.n/a.d; }

//больше или равно
bool Fraction::operator>=(const Fraction& a) const { return (double)n/d>=(double)a.n/a.d; }

//меньше
bool Fraction::operator<(const Fraction& a) const { return (double)n/d<(double)a.n/a.d; }

//меньше или равно
bool Fraction::operator<=(const Fraction& a) const { return (double)n/d<=(double)a.n/a.d; }

// приведение к вещественному типу
Fraction::operator double() {
    return (double)n/d;
}

//ввод с потока
istream& operator>>(istream& cin, Fraction& a) {
    char c;
    int n,d=1;
    cin>>n;
    a.n=n;
    c=getchar();
    if (c=='/') {
        cin>>d;
        if (d==0)
            cin.putback(c);
    }
    else
        cin.putback(c);
    a.Cannon();
    return cin;
}

//вывод в поток
ostream& operator<<(ostream& cout, const Fraction& a) {
    cout<<a.GetN()<<'/'<<a.GetD();
    return cout;
}
