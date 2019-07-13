//
//  main.cpp
//  laba_5
//
//  Created by Иван Шиляев on 10/7/18.
//  Copyright © 2018 Иван Шиляев. All rights reserved.
//

#include <iostream>
#include <cmath>
#include <stdio.h>
#include <iomanip>
using namespace std;

double func1(double x) {
    return 1/(1+sqrt(2*x));
}

double func2(double x) {
    return pow(x,3)*pow(M_E,2*x);
}

double func3(double x) {
    return sqrt(pow(2,x)-1);
}

//формула трапеций
double intgrl_tr(double (*func) (double), double a, double b, double eps, int *n0) {
    if (a==b) return 0;
    double res=1;
    if (a>b) {
        swap(a, b);
        res=-1;
    }
    double s=(func(a)+func(b))*(b-a)*0.5, s2;
    int n=1;
    do {
        n*=2;
        s2=s;
        s=0.0;
        double c=(b-a)/n;
        for (int i=1; i<n; i++)
            s+=func(a+c*i);
        s+=(func(a)+func(b))*0.5;
        s*=c;
    } while (fabs(s-s2)>=eps);
    *n0=n;
    return res*s;
    
}

//формула левых прямоугольников
double intgrl_lrec(double (*func) (double), double a, double b, double eps, int *n0) {
    if (a==b) return 0;
    double res=1;
    if (a>b) {
        swap(a, b);
        res=-1;
    }
    double s=func(a)*(b-a), s2;
    int n=1;
    do {
        n*=2;
        s2=s;
        s=0.0;
        double c=(b-a)/n;
        for (int i=0; i<n; i++)
            s+=func(a+c*i)*c;
    } while (fabs(s-s2)>=eps);
    *n0=n;
    return res*s;
}

//формула Симпсона
double intgrl_sim(double (*func) (double), double a, double b, double eps, int *n0) {
    if (a==b) return 0;
    double res=1;
    if (a>b) {
        swap(a, b);
        res=-1;
    }
    double s=(b-a)*(func(a)+4*func((a+b)*0.5)+func(b))/6, s2;
    int n=1;
    do {
        n*=2;
        s2=s;
        s=0.0;
        double c=(b-a)/n;
        for (int i=0; i<n; i++)
            s+=(func(a+c*i)+4*func((a+c*i+a+c*(i+1))*0.5)+func(a+c*(i+1)));
        s*=(c/6);
    } while (fabs(s-s2)>=eps);
    *n0=n;
    return res*s;
}

int main() {
    setlocale(LC_ALL, ".1251");
    
    double a, b, eps=0.0000001;
    int n=1;
    
    //cout << "Введите пределы интегрирования и точность вычислений для первой функции" << endl;
    //cin >> a >> b >> eps;
    a=0.0; b=1.0;
//    cout << "Результат по формуле трапеций:" << endl;
//    cout << setprecision(10) << intgrl_tr(*func1, a, b, eps, &n);
//    cout << "; число точек деления отрезка [a, b] равно " << n << endl;
    cout << "Результат по формуле левых прямоугольников:" << endl;
    cout << setprecision(10) << intgrl_lrec(*func1, a, b, eps, &n);
    cout << "; число точек деления отрезка [a, b] равно " << n << endl;
    cout << "Результат по формуле Симпсона:" << endl;
    cout << setprecision(10) << intgrl_sim(*func1, a, b, eps, &n);
    cout << "; число точек деления отрезка [a, b] равно " << n << endl;
    cout << endl;
    
    n=1;
    //cout << "Введите пределы интегрирования и точность вычислений для второй функции" << endl;
    //cin >> a >> b >> eps;
    a=0.0; b=0.8;
//    cout << "Результат по формуле трапеций:" << endl;
//    cout << setprecision(10) << intgrl_tr(*func2, a, b, eps, &n);
//    cout << "; число точек деления отрезка [a, b] равно " << n << endl;
    cout << "Результат по формуле левых прямоугольников:" << endl;
    cout << setprecision(10) << intgrl_lrec(*func2, a, b, eps, &n);
    cout << "; число точек деления отрезка [a, b] равно " << n << endl;
    cout << "Результат по формуле Симпсона:" << endl;
    cout << setprecision(10) << intgrl_sim(*func2, a, b, eps, &n);
    cout << "; число точек деления отрезка [a, b] равно " << n << endl;
    cout << endl;
    
    n=1;
    //cout << "Введите пределы интегрирования и точность вычислений для третьей функции" << endl;
    //cin >> a >> b >> eps;
    a=0.3; b=1.0;
//    cout << "Результат по формуле трапеций:" << endl;
//    cout << setprecision(10) << intgrl_tr(*func3, a, b, eps, &n);
//    cout << "; число точек деления отрезка [a, b] равно " << n << endl;
    cout << "Результат по формуле левых прямоугольников:" << endl;
    cout << setprecision(10) << intgrl_lrec(*func3, a, b, eps, &n);
    cout << "; число точек деления отрезка [a, b] равно " << n << endl;
    cout << "Результат по формуле Симпсона:" << endl;
    cout << setprecision(10) << intgrl_sim(*func3, a, b, eps, &n);
    cout << "; число точек деления отрезка [a, b] равно " << n << endl;
    
    return 0;
}
