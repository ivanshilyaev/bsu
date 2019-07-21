#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <stdlib.h>
#include <cmath>
using namespace std;

// 1. В текстовом файле в одну или несколько строк записаны целые числа,
// разделенные пробелами. Найти среди них все простые числа и записать их
// в новый текстовый файл в одну строку через пробел, рассортировав по
// возрастанию модулей. Определение простое число или нет оформить в виде
// функции. Использовать динамический массив для хранения чисел.

int comp (const int *i, const int *j) {
    return *i - *j;
}

bool isPrime(int a) {
    int i=2;
    int kol=1;
    while (i<=sqrt(a)) {
        if (a%i==0)
            kol++;
        i++;
    }
    if (a!=1 && (kol+1)==2)
        return true;
    return false;
}

int main() {
    const int max=300;
    ifstream fin("input.txt", ios_base::in);
    ofstream fout("output.txt", ios_base::out);
    // проверка на открытие файлы
    if (!fin.is_open()) {
        cout<<"Input file doesn't open"<<endl;
        return 1;
    }
    // проверка на пустоту файла
    string line;
    getline(fin, line);
    if (line.empty() && fin.eof()) {
        cout<<"Input file is empty"<<endl;
        return 1;
    }
    fin.seekg(0);
    
    int a, i=0;
    int array[max];
    while (fin>>a) {
        array[i]=a;
        i++;
    }
    qsort(array, i, sizeof(int), (int(*) (const void *, const void *)) comp);
    for (int j=0; j<i; j++)
        if (array[j]>0 && isPrime(array[j]))
            fout<<array[j]<<" ";
    fin.close();
    fout.close();
}
