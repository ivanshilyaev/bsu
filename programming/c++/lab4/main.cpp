//
//  main.cpp
//  laba_4
//
//  Created by Иван Шиляев on 10/2/18.
//  Copyright © 2018 Иван Шиляев. All rights reserved.
//

// вариант 14

#include <iostream>
#include <cmath>
#include <stdio.h>
#include <iomanip>
using namespace std;

void printArray(int **array, int n) {
    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
            cout << setw(4) << array[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
}

int main() {
    
    //0. Инициализация массива
    setlocale(LC_ALL, ".1251");
    
    int n;
    cout << "Введите натуралльное число n - размерность квадратной матрицы" << endl;
    cin >> n;
    while (n<1) {
        cout << "Неверное значение n, повторите попытку" << endl;
        cin >> n;
    }
    int **array = new int*[n];
    for (int i=0; i<n; i++)
        array[i]=new int[n];
    
    int ch;
    cout << "Если хотите ввести элементы матрицы с клавиатуры, нажмите 1, в противном случае нажмите 2" << endl;
    cin >> ch;
    while (ch<1 || ch>2) {
        cout << "Ошибка при вводе данных, повторите попытку" << endl;
        cin >> ch;
    }
    switch (ch) {
        case 1:
            cout << "Введите элементы матрицы (целые числа):" << endl;
            for (int i=0; i<n; i++)
                for (int j=0; j<n; j++)
                    cin >> array[i][j];
            break;
        case 2:
            int a, b;
            cout << "Необходимо задать границы интервала, которому должны принадлежать элементы массива" << endl;
            cout << "Введите целое число a - нижнюю границу интервала" << endl;
            cin >> a;
            cout << "Введите целое число b - верхнюю границу интервала" << endl;
            cin >> b;
            while (b<a) {
                cout << "Ошибка при вводе данных, повторите попытку" << endl;
                cin >> b;
            }
            srand(time(NULL));
            for (int i=0; i<n; i++)
                for (int j=0; j<n; j++)
                    array[i][j] = a + rand()%(b-a+1);
            break;
        default:
            break;
    }
    
    cout << "Текущий массив:" << endl;
    printArray(array, n);
    
    //1. Перестановка строк матрицы
    //Алгоритм: запоминаем позицию нуля в каждой строке

    int numOfNulls[n]; //позиция нуля в каждой строке
    
    //Проверяем, что в каждой строке ноль единственный, и запоминаем его позицию
    int kol=0; //общее количество нулей
    for (int i=0; i<n; i++) {
        int k=0; //количество нулей в очередной строке
        for (int j=0; j<n; j++) {
            if (array[i][j]==0) {
                if (k==0) {
                    k++; kol++;
                    numOfNulls[i]=j;
                }
                else {
                    kol=n+1;
                    break;
                }
            }
        }
        if (kol>n || kol<i+1)
            break;
    }
    
    //Проверяем, что в каждом столбце ноль единственный
    bool b=true;
    for (int j=0; j<n; j++) {
        int k=0; //количество нулей в очередном столбце
        for (int i=0; i<n; i++) {
            if (array[i][j]==0)
                k++;
        }
        if (k>1)
            b=false;
    }
    
    if (kol==n && b) {
        for (int i=0; i<n; i++) {
            int a=numOfNulls[i];
            //меняем местами указатели
            int *ptr=array[a];
            array[a]=array[i];
            array[i]=ptr;
            //меянеям местами индексы.  В противном случае массив сортируется два раза (то есть не изменяется)
            numOfNulls[i]=i;
            numOfNulls[a]=a;
        }
        cout << "Изменённый массив:" << endl;
        printArray(array, n);
    }
    else
        cout << "Условие существования в каждой строке и в каждом столбце квад­ратной матрицы единственного нуля не выполнено" << endl;
    
    //2.
    double p=1;
    for (int i=1; i<n; i++) {
        for (int j=0; j<i; j++)
            p*=array[i][j];
    }
    if (n==1) {
        cout << "Невозможно подсчитать произведение элементов, лежащих ниже главной диагонали, так как в массиве находится один элемент" << endl;
    }
    else {
        cout << "Произведение элементов, лежащих ниже главной диагонали, равно ";
        cout << p << endl;
    }
    
    //Освобожение памяти
    for (int i=0; i<n; i++)
        delete *(array+i);
    delete []array;
    
    return 0;
}
