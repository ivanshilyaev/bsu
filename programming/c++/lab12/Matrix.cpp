#include <iostream>
#include <iomanip>
#include "Matrix.hpp"
using namespace std;

void Matrix::SetM(int am) {m=am;}

void Matrix::SetN(int an) {n=an;}

int Matrix::GetM() const {return m;}

int Matrix::GetN() const {return n;}

int Matrix::GetID() const {return ID;}

double Matrix::GetAij(int i, int j) const {
    if (i<0 || i>=m || j<0 || j>=n)
        throw "Error: index out of bound";
    return p[i][j];
}

void Matrix::SetAij(int i, int j, double value) {
    if (i<0 || i>=m || j<0 || j>=n)
        throw "Error: index out of bound";
    p[i][j]=value;
}

int Matrix::NextID=0;

Matrix::Matrix(): m(0), n(0), p(NULL), ID(++NextID) {}

Matrix::Matrix(int am, int an): ID(++NextID) {
    if (am<=0 || an<=0)
        throw "Error: index can't be less or equal then 0";
    m=am; n=an;
    p=new double*[m];
    for (int i=0; i<m; i++)
        p[i]=new double[n];
    for (int i=0; i<m; i++)
        for (int j=0; j<n; j++)
            p[i][j]=0;
}

// конструктор копирования
Matrix::Matrix(const Matrix& b): ID(++NextID) {
    m=b.m; n=b.n;
    p=new double*[m];
    for (int i=0; i<m; i++)
        p[i]=new double[n];
    for (int i=0; i<m; i++)
        for (int j=0; j<n; j++)
            p[i][j]=b.p[i][j];
}

// деструктор
Matrix::~Matrix() {
    for (int i=0; i<m; i++)
        delete []p[i];
    delete []p;
    m=0; n=0;
}

// оператор присваивания
Matrix& Matrix::operator=(const Matrix& b) {
    if (this==&b)
        return *this;
    for (int i=0; i<m; i++)
        delete []p[i];
    delete []p;
    m=b.m; n=b.n;
    p=new double*[m];
    for (int i=0; i<m; i++)
        p[i]=new double[n];
    for (int i=0; i<m; i++)
        for (int j=0; j<n; j++)
            p[i][j]=b.p[i][j];
    return *this;
}

// составные операторы присваивания
Matrix& Matrix::operator+=(const Matrix& b) {
    if (m!=b.m || n!=b.n)
        throw "Error: (sum) size of matrices must be the same";
    for (int i=0; i<m; i++)
        for (int j=0; j<n; j++)
            p[i][j]+=b.p[i][j];
    return *this;
}

Matrix& Matrix::operator-=(const Matrix& b) {
    if (m!=b.m || n!=b.n)
        throw "Error: (div) size of matrices must be the same";
    for (int i=0; i<m; i++)
        for (int j=0; j<n; j++)
            p[i][j]-=b.p[i][j];
    return *this;
}

Matrix& Matrix::operator*=(const Matrix& b) {
    if (n!=b.m)
        throw "Error: can't multiply matrices because of inappropriate size";
    Matrix result(m, b.n);
    for (int i=0; i<m; i++)
        for (int j=0; j<b.n; j++)
            for (int k=0; k<n; k++)
                result.p[i][j]+=p[i][k]*b.p[k][j];
    *this=result;
    return *this;
}

// умножение на константу справа
Matrix Matrix::operator*(double c) {
    Matrix b(*this);
    for (int i=0; i<b.m; i++)
        for (int j=0; j<b.n; j++)
            b.p[i][j]*=c;
    return b;
}

// умножение на константу слева
Matrix operator*(double c, const Matrix& a) {
    Matrix b(a);
    for (int i=0; i<b.m; i++)
        for (int j=0; j<b.n; j++)
            b.p[i][j]*=c;
    return b;
}

// умножение на константу и присваивание
Matrix& Matrix::operator*=(double c) {
    for (int i=0; i<m; i++)
        for (int j=0; j<n; j++)
            p[i][j]*=c;
    return *this;
}

// деление на константу
Matrix Matrix::operator/(double c) {
    if (c==0)
        throw "Error: division by zero";
    Matrix b(*this);
    for (int i=0; i<b.m; i++)
        for (int j=0; j<b.n; j++)
            b.p[i][j]/=c;
    return b;
}

// деление на константу и присваивание
Matrix& Matrix::operator/=(double c) {
    if (c==0)
        throw "Error: division by zero";
    for (int i=0; i<m; i++)
        for (int j=0; j<n; j++)
            p[i][j]/=c;
    return *this;
}

// сложение матриц
Matrix Matrix::operator+(const Matrix& b) {
    Matrix a(*this);
    a+=b;
    return a;
}

// вычитание матриц
Matrix Matrix::operator-(const Matrix& b) {
    Matrix a(*this);
    a-=b;
    return a;
}

// умножение матриц
Matrix Matrix::operator*(const Matrix& b) {
    Matrix a(*this);
    a*=b;
    return a;
}

// сравнение матриц
bool Matrix::operator==(const Matrix& b) const {
    if (m!=b.m || n!=b.n)
        return false;
    for (int i=0; i<m; i++)
        for (int j=0; j<n; j++)
            if (p[i][j]!=b.p[i][j])
                return false;
    return true;
}

bool Matrix::operator!=(const Matrix& b) const {
    return !(*this==b);
}

// ввод и вывод матриц
istream& operator>>(istream& cin, Matrix& b) {
    if (b.m==0 || b.n==0)
        throw "Error: size must be not 0";
    for (int i=0; i<b.m; i++)
        for (int j=0; j<b.n; j++)
            cin>>b.p[i][j];
    return cin;
}

ostream& operator<<(ostream& cout, const Matrix& b) {
    if (b.m==0 || b.n==0)
        throw "Error: size must be not 0";
    for (int i=0; i<b.m; i++) {
        for (int j=0; j<b.n; j++)
            cout<<setw(8)<<b.p[i][j];
        cout<<endl;
    }
    return cout;
}
