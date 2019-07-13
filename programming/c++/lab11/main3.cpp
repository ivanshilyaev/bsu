#include <iostream>
#include "fraction.h"
#include "MyException.hpp"
using namespace std;

// Класс Обыкновенные дроби

int main() {
    try {
        Fraction a(27,8), b(8,11), c, e; // создаём три объекта класса дробь (три дроби)
        cout<<"Enter fraction"<<endl;
        cin>>e; // ввод дроби с клавиатуры
        c=a*b; // умножение дробей и операция присваивания
        cout<<c<<endl;// вывод дроби на экран
        double d=double(a); // приведение дроби к вещественному типу
        cout<<d<<endl;
        Fraction f; // дробь по умолчанию инициализируется нулём
        c=a/f; // выбрасывается исключение: деление на ноль
    }
    catch (MyException& e) {
        cout<<e.what()<<endl;
    }
}
