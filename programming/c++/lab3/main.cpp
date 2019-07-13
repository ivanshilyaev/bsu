//
//  main.cpp
//  laba_3
//
//  Created by Иван Шиляев on 23.09.2018.
//  Copyright © 2018 Иван Шиляев. All rights reserved.
//

// вариант 14

#include <iostream>
#include <cmath>
using namespace std;

void printArray(double array[], int n) {
    for (int i=0; i<n; i++)
        cout << array[i] << " ";
    cout << endl;
}

int main() {
    //0. иницализация массива
    setlocale(LC_ALL, ".1251");
    const int max = 100;
    double array[max];
    int n;
    cout << "Ведите натуральное число n - количсевто элементов в массиве, не превосходящее " << max << endl;
    cin >> n;
    while (n<1 || n>max) {
        cout << "Неверное значение n, повторите попытку" << endl;
        cin >> n;
    }
    int ch;
    cout << "Если хотите ввести элементы массива с клавиатуры, нажмите 1, в противном случае нажмите 2" << endl;
    cin >> ch;
    while (ch<1 || ch>2) {
        cout << "Ошибка при вводе данных, повторите попытку" << endl;
        cin >> ch;
    }
    switch (ch) {
        case 1:
            cout << "Введите элементы массива (действительные числа):" << endl;
            for (int i=0; i<n; i++)
                cin >> array[i];
            break;
        case 2:
            double a, b;
            cout << "Необходимо задать границы интервала, которому должны принадлежать элементы массива" << endl;
            cout << "Введите действительное число a - нижнюю границу интервала" << endl;
            cin >> a;
            cout << "Введите действительное число b - верхнюю границу интервала" << endl;
            cin >> b;
            while (b<a) {
                cout << "Ошибка при вводе данных, повторите попытку" << endl;
                cin >> b;
            }
            srand(time(NULL));
            for (int i=0; i<n; i++) {
                array[i] = a + (rand() * (b - a)) / RAND_MAX;
            }
            break;
        default:
            break;
    }
    cout << endl;
    printArray(array, n);
    cout << endl;
    
    //1
    double p;
    cout << "Введите действительное число P" << endl;
    cin >> p;
    int kol=0;
    for (int i=0; i<n; i++)
        if (array[i]>p)
            kol++;
    cout << "Количество элементов массива, больших заданного числа Р равно ";
    cout << kol << endl;
    cout << endl;
    
    //2
    double m=array[0]; int index=0;
    for (int i=1; i<n; i++)
        if (abs(array[i])>m) {
            m=abs(array[i]);
            index=i;
        }
    if (index==(sizeof(array)*n)/(sizeof(double)*max)-1)
        cout << "Последний максимальный по модулю элемент расположен на послденем месте в массиве" << endl;
    else {
        double mult=1;
        for (int i=index+1; i<n; i++)
        mult*=array[i];
        cout << "Произведение элементов массива, расположенных после последнего максимального по модулю элемента равно ";
        cout << mult << endl;
    }
    cout << endl;
    
    //3
    for (int i=0; i<n; i++)
        for (int j=1; j<n; j++)
            if (array[j]<0 && array[j-1]>=0) {
                double tmp=array[j-1];
                array[j-1]=array[j];
                array[j]=tmp;
            }
    cout << "Преобразованный массив (сначала все отрицательные элементы, потом все остальные):" << endl;
    printArray(array, n);
    return 0;
}
