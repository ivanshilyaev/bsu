#include <iostream>
#include "List.hpp"
#include <iomanip>
using namespace std;

void Print(Student& b) {
    b.FIO="_"+b.FIO;
    cout<<setw(8)<<--b.averageMark<<setw(10)<<b.num<<setw(40)<<b.FIO<<endl;
}
void Print2(Student b) {
    cout<<setw(8)<<b.averageMark<<setw(10)<<b.num<<setw(40)<<b.FIO<<endl;
}

int main() {
    try {
        List L;
        //L.ListPrint(); //1-ый способ печати
        // добаление трёх элементов в начало списка
        Student a,b,c;
        int n;
        a.num=1234567; a.FIO="Shilyaev Ivan Vladimirovich"; a.averageMark=10.0;
        b.num=7654321; b.FIO = "Berkovich Pavel Aleksandrovich"; b.averageMark=9.99;
        c.num=2345678; c.FIO ="Bochkov Ilya Vitalievich"; c.averageMark=9.98;
        L.InsertLast(a);
        L.InsertLast(b);
        L.InsertLast(c);
        cout<<"List L:"<<endl;
        L.ListPrint();
        cout<<"Search by value: ";
        n=L.Search(c);
        cout<<"Number of comparisons: ";
        cout<<n<<endl;
        
        List L2;
        
        
        L2=L;
        cout << "List L2:" << endl;
        L2.ListPrint();
        cout<<"ForEach (reduce each average mark in L2):"<<endl; // изменяем значения полей на 1;
        L2.ForEach(Print);
        
        // удаление элемента из начала
        if (L.DeleteFirst()) {
            cout<<"Delete first from L:"<<endl;
            L.ListPrint();
        }
        else
            cout<<"List is empty"<<endl;
        
        // удаление элемента из конца
        if (L.DeleteLast()) {
            cout<<"Delete last from L:"<<endl;
            L.ListPrint();
        }
        else
            cout<<"List is empty"<<endl;
        
        // добаление элемента в начало списка
        Student d;
        d.num=7777777; d.FIO="Shilyaeva Anna Vladimirovna"; d.averageMark=99;
        L.InsertFirst(d);
        cout<<"List L:"<<endl;
        L.ListPrint();
        cout<<"For each (nothing changed in L2)"<<endl;
        L2.ForEach(Print2);
    }
    catch (const char* e) {
        cout<<e<<endl;
    }
}

