#include "Queue.hpp"
#include "MyException.h"

//конструктор
CQueue::CQueue(int cpty) {
    capacity=cpty;
    Arr=new InfoType[capacity+1];
    first=0; last=0;
    cout<<"Constructor of CQueue"<<endl;
}

//конструктор копирования
CQueue::CQueue(const CQueue &Q) {
    capacity=0;
    Erase();
    Clone(Q);
    cout<<"Copy constructor of CQueue"<<endl;
}

//деструктор
CQueue::~CQueue() {
    cout<<"Destructor of CQueue"<<endl;
    Erase();
}

//оператор присваивания
CQueue& CQueue::operator=(const Queue &Q) {
    if (this!=&Q) {
        Erase();
        Clone(Q);
    }
    return *this;
}

//клонировние
void CQueue::Clone(const Queue &Q) {
    const CQueue *Q2=(const CQueue*)&Q;
    capacity=Q2->capacity;
    Arr=new InfoType[capacity+1];
    for (int i=0; i<capacity; ++i)
        Arr[i]=Q2->Arr[i];
    first=Q2->first; last=Q2->last;
}

//удаление
void CQueue::Erase() {
    delete[] Arr;
    first=0; last=0; capacity=0;
}

//втсавка элемента
void CQueue::Push(InfoType AInfo){
    if ((last+1==first) || ((first==0) && (last==capacity)))
        throw MyException("Impossible to execute Push: queue is full");
    Arr[last++]=AInfo;
    if (last==capacity+1)
        last=0;
}

//удаление элемента
bool CQueue::Pop() {
    if ((capacity==0) || (first==last))
        throw MyException("Impossible to execute Pop: queue is empty");
    first++;
    if (first==capacity+1)
        first=0;
    return true;
}

//получение первого элемента
InfoType CQueue::GetFirst() const {
    if ((capacity==0) || (first==last))
        throw MyException("Impossible to execute Pop: queue is empty");
    return Arr[first];
}

//проверка на пустоту
bool CQueue::IsEmpty() const { return (capacity==0 || first==last); }

//получение размера
unsigned CQueue::GetSize() const { return last-first; }

// оператор []
InfoType& CQueue::operator[](unsigned i) const {
    return (InfoType&) GetByIndex(i);
}

//получение элемента по индексу
const InfoType& CQueue::GetByIndex(unsigned i) const {
    if (first+i<first || first+i>=last)
        throw MyException("Impossible to execute operator[]: invalid index");
    return Arr[first+i];
}

//проход по очереди с вызовов callback-функции, НЕ изменяющей значения переменной
void CQueue::Browse(void ItemWork(InfoType)) const {
    for (int i=first; i<last; ++i)
        ItemWork(Arr[i]);
}

//проход по очереди с вызовов callback-функции, изменяющей значение переменной
void CQueue::Browse(void ItemWork(InfoType &)) {
    for (int i=first; i<last; ++i)
        ItemWork(Arr[i]);
}
