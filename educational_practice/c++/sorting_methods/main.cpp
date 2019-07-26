I#include <iostream>
#include <iomanip>
#include <fstream>
#include "sort.h"
using namespace std;

T* first(int n) {
    T* array = new T[n];
    for (int i=0; i<n; i++)
        cin>>setw(1)>>array[i];
    return array;
}

T* second(int n, int a, int b) {
    T* array = new T[n];
    srand(time(NULL));
    for (int i=0; i<n; i++)
        array[i]=rand()%(b-a+1)+a;
        //array[i]=(rand()%RAND_MAX)*(b-a+1)+a;
    return array;
}

T* third(string filename, T* array, int* length) {
    int n(0);
    int number;
    //double number;


    ifstream fin(filename, ios_base::in);
    if (!fin.is_open()) {
        cout << "Файл не может быть открыт!" << endl;
        return NULL;
    }
    else if (fin.eof()) {
        cout << "Файл пуст!" << endl;
        return NULL;
    }
    else {
        while (fin>>number) { // читает до первого НЕ числа
            n++;
        }
        fin.close();
    }
    cout << "Размерность массива:" << endl;
    cout << n << endl;

    array = new T[n];
    int i=0;

    ifstream fin1(filename, ios_base::in);
    if (!fin1.is_open()) {
        cout << "Файл не может быть открыт!" << endl;
        return NULL;
    }
    else if (fin1.eof()) {
        cout << "Файл пуст!" << endl;
        return NULL;
    }
    else {
        while (fin1>>number) {
            array[i] = number;
            i++;
        }
        fin1.close();
    }

    *length=n;
    return array;
}

int main() {
    //const int max = 100;

    T* array;
    int length;

    int a, b; // границы диапазона при рандомном вводе

    string filename; // имя файла при вводе данных из файлы

    cout << "Выберите действие:" << endl;
    cout << "1 - ввести данные с клавиатуры" << endl;
    cout << "2 - сгенирировать данные при помощи датчика случайных чисел" << endl;
    cout << "3 - ввести данные из текстового файла" << endl;

    char c;
    cin>>c;
    while (c<'1' || c>'3') {
        cout << "Ошибка при вводе данных. Повторите попытку:" << endl;
        cin>>c;
    }
    switch (c) {
        case '1': {
            cout << "Введите размерность массива:" << endl;
            cin>>length;
            while (length<1) {
                cout << "Ошибка при вводе данных. Повторите попытку:" << endl;
                cin>>length;
            }
            cout << "Введите элементы массива:" << endl;
            array = first(length);
            break;
        }
        case '2': {
            cout << "Введите размерность массива:" << endl;
            cin>>length;
            while (length<1) {
                cout << "Ошибка при вводе данных. Повторите попытку:" << endl;
                cin>>length;
            }
            cout << "Введите левую границу массива:" << endl;
            cin>>a;
            cout << "Введите правую границу массива:" << endl;
            cin>>b;
            while (b<a) {
                cout << "Ошибка при вводе данных. Повторите попытку:" << endl;
                cin>>b;
            }
            array = second(length, a, b);
            cout << "Сгенерированный массив:" << endl;
            for (int i=0; i<length; i++)
                cout << array[i] << " ";
            cout << endl;
            break;
        }
        case '3': {
            cout << "Введите полный путь к файлу, из которого хотите ввести данные:" << endl;
            cin>>filename;
            array = third(filename, array, &length);
            cout << "Считанный массив:" << endl;
            for (int i=0; i<length; i++)
                cout << array[i] << " ";
            cout << endl;
            break;
        }
        default: {
            break;
        }
    }

    T* sortedArray = treeSort(array, length);

    ofstream fout("output.txt", ios_base::out);
    if (!fout.is_open()) {
        cout << "Output file doesn't open" << endl;
    }
    else {
        for (int i = 0; i < length; ++i) {
            fout << sortedArray[i] << endl;
        }
        fout.close();
    }

    delete [] array;

    return 0;
}
