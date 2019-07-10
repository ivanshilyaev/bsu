//
//  main.cpp
//  laba_1
//
//  Created by Иван Шиляев on 11.09.2018.
//  Copyright © 2018 Иван Шиляев. All rights reserved.
//

#include "iostream"
#include "cmath"
#include "limits"
#include <math.h>

using namespace std;


int main(){
    setlocale(LC_ALL,".1251");
    cout << "Данная программа вычисляет приближённое значение функции, используя представление её в виде ряда Тейлора." << endl;
    cout << "Функция: y = arctg x" << endl;
    double e, x, res, a;
    // явно задаём значение k
    int k = 5;
    cout << "Введите действительное число e такое, что 0 < e < " << pow(10, -k) << endl;
    cin >> e;
    while (e<=0 || e>=pow(10, -k)) {
        cout << "Неверное значение е, повторите попытку" << endl;
        cin >> e;
    }
    cout << "Введите действительное число x, большее 1" << endl;
    cin >> x;
    while (x<=1) {
        cout << "Неверное значение x, повторите попытку" << endl;
        cin >> x;
    }
    res = M_PI/2;
    a = -1/x;
    int i = 1;
    while (abs(a) >= e) {
        res+=a;
        a*=(-1.0*(2*i-1)/(x*x*(2*i+1)));
        i++;
    }
    cout << "Полученный результат:" << endl;
    cout << res << endl;
    cout << "А теперь через функцию atan:" << endl;
    cout << atan(x) << endl;
}
