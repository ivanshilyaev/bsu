#include <iostream>
#include "List.hpp"
#include <iomanip>
using namespace std;

List::List() {First=NULL;}

//конструктор копирования
List::List(const List& L) {Clone(L);}

//деструктор
List::~List() {Erase();}

//клонирование
void List::Clone(const List& L) {
    ListItem *p, *q, *r;
    r=NULL;
    p=L.First;
    while (p)
    {
        q=new ListItem;
        q->Info.averageMark=p->Info.averageMark;
        q->Info.FIO=p->Info.FIO;
        q->Info.num=p->Info.num;
        q->Next=NULL;
        if (r==NULL)
            First = q;
        else
            r->Next=q;
        r=q;
        p=p->Next;
    }
}

//удаление списка
void List::Erase() {
    ListItem *p, *q;
    p=First;
    while (p)
    {
        q=p->Next;
        delete p;
        p=q;
    }
    First=NULL;
}

//вставка нового элемента в начало списка
void List::InsertFirst(const Student& Elem) {
    ListItem* p = new ListItem;
    p->ListItem::Info.averageMark=Elem.averageMark;
    p->ListItem::Info.num=Elem.num;
    p->ListItem::Info.FIO=Elem.FIO;
    p->Next=First;
    First=p;
}

//вставка нового элемента в конец списка
void List::InsertLast(const Student& Elem) {
    ListItem* p=new ListItem;
    p->ListItem::Info.averageMark=Elem.averageMark;
    p->ListItem::Info.num=Elem.num;
    p->ListItem::Info.FIO=Elem.FIO;
    p->Next=NULL;
    ListItem* buf=First;
    if (!buf) {
        First=p;
    }
    else {
        while (buf->Next)
            buf=buf->Next;
        buf->Next=p;
    }
}

//удаление первого элемента списка
bool List::DeleteFirst() {
    if (!First)
        return false;
    ListItem* p=First;
    First=First->Next;
    delete p;
    return true;
}

//удаление последнего элемента списка
bool List::DeleteLast() {
    if (!First)
        return false;
    ListItem* buf=First;
    ListItem* prev;
    if (buf->Next==NULL) {
        delete buf;
        First=NULL;
        return true;
    }
    else {
        prev=buf;
        buf=buf->Next;
    }
    while (buf->Next!=NULL){
        buf=buf->Next;
        prev=prev->Next;
    }
    prev->Next=NULL;
    delete buf;
    return true;
}

//просмотр первого элемента списка
Student List::Top() const {
    if (!First)
        throw "Error: list is empty";
    Student buff;
    buff.averageMark=First->Info.averageMark;
    buff.FIO=First->Info.FIO;
    buff.num=First->Info.num;
    return buff;
}

//оператор присваивания
const List& List::operator=(const List& L) {
    if (this==&L)
        return *this;
    Erase();
    Clone(L);
    cout<<"========="<<endl;
    return *this;
}
//удаление элемента по значению
void List::DeleteSearch(const Student& a) {                    ///!!!!!!!!!!!!!!!!!!!!!!!!!!
    bool b=false;
    ListItem* p=First;
    ListItem* prev=NULL;
    if (!p)
        throw "Error: list is empty";
    while (p) {
        if ((p->Info.averageMark==a.averageMark)&&(p->Info.FIO==a.FIO)&&(p->Info.num==a.num)){
            cout<<"Deleting element..."<<endl;
            b=true;
            if (p==First) {
                DeleteFirst();
                break;
            }
            else {
                prev->Next=p->Next;
                delete p;
                break;
            }
        }
        else {
            prev=p;
            p=p->Next;
        }
    }
    if (!b)
        cout<<"Error: can't delete element as it is not in the list";
}

//поиск элемента по значению с подсчетом числа сравнений, выполненных в процессе поиска
int List::Search(const Student& a) {
    Student buf;
    int b=false;
    int n(0);
    ListItem* p=First;
    if (!p)
        throw "Error: list is empty";
    while (p) {
        if ((p->Info.averageMark==a.averageMark)&&(p->Info.FIO==a.FIO)&&(p->Info.num==a.num)){
            b=true;
            cout<<"Element is found"<<endl;
            return n;
        }
        else{
            n++;
            p=p->Next;
        }
    }
    if (!b)
        cout<<"Error: can't delete element as it is not in the list";
    return n;
}

//просмотр списка с изменением всех значений
void List::ForEach (void (*Fun)(Student&)) {
    ListItem* p=First;
    if (!p)
        throw "Error: list is empty";
    while (p) {
        Fun(p->Info);
        p=p->Next;
    }
}

//просмотр списка с вызовом callback-функции, которая не изменяет хранящееся значение
void List::ForEach(void (*Fun)(Student)) const {
    ListItem* p=First;
    if (!p)
        throw "Error: list is empty";
    else {
        while(p) {
            Fun(p->Info);
            p=p->Next;
        }
    }
}

// печать элементов списка
void List::ListPrint() const {
    ListItem* p=First;
    if (!p)
        throw "Error: list is empty";
    else {
        while(p) {
            cout<<setw(8)<<p->ListItem::Info.averageMark<<setw(10)<<p->ListItem::Info.num<<setw(40)<<p->ListItem::Info.FIO<<endl;
            p=p->Next;
        }
    }
}
