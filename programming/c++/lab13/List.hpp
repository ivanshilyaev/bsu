#pragma once
#include <iostream>
#include <string>
using namespace std;

struct Student {
    int num;
    string FIO;
    double averageMark;
};

class List {
protected:
    struct ListItem{
        Student Info;
        ListItem *Next;
    };
    ListItem *First;
    void Clone(const List &); // клонирование
public:
    List();
    List(const List&);
    virtual~List();
    void Erase();
    void InsertFirst(const Student &); // вставка нового элемента в начало списка
    void InsertLast(const Student &); // вставка нового элемента в конец списка
    bool DeleteFirst(); // удаление первого элемента списка
    bool DeleteLast();  // удаление последнего элемента списка
    Student Top() const; // просмотр первого элемента списка
    const List& operator=(const List &); // оператор присваивания
    void DeleteSearch (const Student&); // удаление элемента по значению
    int Search (const Student&); // поиск элемента по значению
    void ForEach (void(*Fun)(Student&)); // просмотр списка с изменением всех значений
    void ForEach (void(*Fun)(Student))const; // просмотр списка без изменения всех значений
    void ListPrint() const; // печать элементов списка
};
