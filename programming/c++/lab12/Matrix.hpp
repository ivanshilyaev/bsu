#pragma once
#include <iostream>
using namespace std;

class Matrix {
    int m,n;
    double** p;
    static int NextID;
    const int ID;
public:
    Matrix();
    Matrix(int, int);
    Matrix(const Matrix&); // конструктор копирования
    ~Matrix(); // деструктор
    void SetM(int);
    void SetN(int);
    void SetAij(int, int, double);
    int GetM() const;
    int GetN() const;
    double GetAij(int, int) const;
    int GetID() const;
    Matrix& operator=(const Matrix&); // оператор присваивания
    Matrix& operator+=(const Matrix&); // составные оператор присваивания
    Matrix& operator-=(const Matrix&);
    Matrix& operator*=(const Matrix&);
    
    Matrix operator*(double); // умножение на константу справа
    friend Matrix operator*(double, const Matrix&); // умножение на константу слева
    Matrix& operator*=(double); // умножение на константу и присваивание
    Matrix operator/(double); // деление на константу
    Matrix& operator/=(double); // деление на константу и присваивание
    
    Matrix operator+(const Matrix&); // сложение матриц
    Matrix operator-(const Matrix&); // вычитание матриц
    Matrix operator*(const Matrix&); // умножение матриц
    
    bool operator==(const Matrix&) const; // сравнение матриц на равенство и неравенство
    bool operator!=(const Matrix&) const;
    
    friend istream& operator>>(istream&, Matrix&); //  ввод и вывод матрицы
    friend ostream& operator<<(ostream&, const Matrix&);
};
