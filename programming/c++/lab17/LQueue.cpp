#include "Queue.hpp"
#include "MyException.h"

//конструктор
LQueue::LQueue(): front(NULL), rear(NULL), size(0) {
    cout<<"Constructor of LQueue"<<endl;
}

//конструктор копирования
LQueue::LQueue(const LQueue &Q) {
    size=0;
    Clone(Q);
    cout<<"Copy constructor of LQueue"<<endl;
}

//деструктор
LQueue::~LQueue() {
    cout<<"Destructor of LQueue"<<endl;
    Erase();
}

//оператор присваивания
LQueue& LQueue::operator=(const Queue &Q) {
    if (this!=&Q) {
        Erase();
        Clone(Q);
    }
    cout<<"Operator = for LQueue"<<endl;
    return *this;
}

//клонирование
void LQueue::Clone(const Queue &Q) {
    const LQueue *Q2=(const LQueue*)&Q;
    const QItem *item=Q2->front;
    for (unsigned i=0; i<Q2->size; ++i) {
        Push(item->info);
        item=item->next;
    }
}

//удаление
void LQueue::Erase() {
    while(size!=0)
        Pop();
}

//вставка элемента
void LQueue::Push(InfoType AInfo) {
    QItem *item=new QItem(AInfo);
    if (size>0)
        rear->next=item;
    else
        front=item;
    rear=item;
    size++;
}

//удаление элемента
bool LQueue::Pop() {
    if (size==0)
        throw MyException("Impossible to execute Pop: queue is empty");
    QItem *item=front;
    front=front->next;
    delete item;
    size--;
    if (size==0)
        rear=NULL;
    return true;
}

//получение первого элемента
InfoType LQueue::GetFirst() const {
    if (size==0)
        throw MyException("Impossible to execute GetFirst: queue is empty");
    return front->info;
}

//проверка на пустоту
bool LQueue::IsEmpty() const { return size==0; }

//получение размера
unsigned LQueue::GetSize() const { return size; }

// оператор []
InfoType& LQueue::operator[](unsigned i) const {
    return (InfoType&) GetByIndex(i);
}

//получение элемента по индексу
const InfoType& LQueue::GetByIndex(unsigned i) const {
    if (i<0 || i>=size)
        throw MyException("Impossible to execute operator[]: invalid index");
    QItem *item=front;
    for (unsigned j=0; j<i; ++j)
        item=item->next;
    return item->info;
}

//проход по очереди с вызовов callback-функции, НЕ изменяющей значения переменной
void LQueue::Browse(void ItemWork(InfoType)) const {
    QItem *item=front;
    for (int i=0; i<size; ++i) {
        ItemWork(item->info);
        item=item->next;
    }
}

//проход по очереди с вызовов callback-функции, изменяющей значение переменной
void LQueue::Browse(void ItemWork(InfoType&)) {
    QItem *item=front;
    for (int i=0; i<size; ++i) {
        ItemWork(item->info);
        item=item->next;
    }
}
