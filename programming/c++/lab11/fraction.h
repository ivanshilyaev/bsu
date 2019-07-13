#pragma once
#include <iostream>
using namespace std;

class Fraction {
private:
    int n; // числитель
    int d; // знаменатель
    int nod(int, int);
    void Cannon();
    static int next_ID;
    const int ID;
public:
    Fraction(int, int);
    Fraction();
    Fraction (const Fraction&);
    int GetN() const;
    int GetD() const;
    void SetN(int);
    void SetD(int);
    int GetID() const;
    Fraction operator - () const; // возвращаем новую дробь
    // Fraction operator - () {n=-n; return *this;} // возвращаем эту же дробь с изменённым знаком
    
    // перегрузим отрцание как получение обратной дроби
    Fraction operator ! () const;
    
    //инкремент и декремент
    Fraction& operator ++ (); // префиксный
    Fraction operator++(int); // постфиксный
    Fraction& operator--();
    Fraction operator--(int);
    
    // присваивание
    Fraction& operator=(const Fraction&);
    // составные операторы присваивания
    Fraction& operator+=(const Fraction&);
    Fraction& operator-=(const Fraction&);
    Fraction& operator*=(const Fraction&);
    Fraction& operator/=(const Fraction&);
    // составнные операторы присваивания с числом
    Fraction& operator+=(int);
    Fraction& operator-=(int);
    Fraction& operator*=(int);
    Fraction& operator/=(int);
    
    // бинарные (арифметические) операции
    Fraction operator + (const Fraction&) const; //сложение
    Fraction operator + (int) const;
    friend Fraction operator + (int, const Fraction&);
    Fraction operator-(const Fraction&) const; //вычитание
    Fraction operator-(int) const;
    friend Fraction operator-(int, const Fraction&);
    Fraction operator*(const Fraction&) const; // умножение
    Fraction operator*(int) const;
    friend Fraction operator*(int, const Fraction&);
    Fraction operator/(const Fraction&) const; // деление
    Fraction operator/(int) const;
    friend Fraction operator/(int, const Fraction&);
    
    // операции сравнения
    bool operator==(const Fraction&) const;
    bool operator!=(const Fraction&) const;
    bool operator>(const Fraction&) const;
    bool operator>=(const Fraction&) const;
    bool operator<(const Fraction&) const;
    bool operator<=(const Fraction&) const;
    
    // приведение к вещественному типу
    operator double ();
    
    // потоковый ввод/вывод
    friend istream& operator>> (istream&, Fraction&);
    friend ostream& operator<< (ostream&, const Fraction&);
};
