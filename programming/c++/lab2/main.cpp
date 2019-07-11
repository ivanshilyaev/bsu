//
//  main.cpp
//  laba_2
//
//  Created by Иван Шиляев on 18.09.2018.
//  Copyright © 2018 Иван Шиляев. All rights reserved.
//

#include <iostream>
#include <cmath>
using namespace std;

bool func(int i) {
    int tmp = i;
    int array[10];
    for (int j=0; j<10; j++)
        array[j]=0;
    while (tmp>0) {
        array[tmp%10]++;
        tmp/=10;
    }
    for (int j=0; j<10; j++)
        if (array[j]>1)
            return false;
    return true;
}

int main() {
    setlocale(LC_ALL, ".1251");
    cout << "Данная программа на заданном отрезке [a,b] натурального ряда чисел находит числа, у которых все цифры в записи числа различны" << endl;
    int a, b;
    cout << "Введите натуральное число a - начало отрезка" << endl;
    cin >> a;
    while (a<1) {
        cout << "Неверное значение a, повторите попытку" << endl;
        cin >> a;
    }
    cout << "Введите натуральное число b, не меньшее a" << endl;
    cin >> b;
    while (b<a) {
        cout << "Неверное значение b, повторите попытку" << endl;
        cin >> b;
    }
    for (int i=a; i<=b; i++)
        if (func(i))
            cout << i << endl;
    return 0;
}
