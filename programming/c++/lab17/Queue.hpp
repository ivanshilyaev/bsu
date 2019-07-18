#pragma once
#include <iostream>
using namespace std;

typedef int InfoType;

//абстрактный класс Очередь
class Queue {
private:
    virtual void Erase();
    virtual void Clone(const Queue&);
public:
    Queue();
    Queue(const Queue&);
    virtual ~Queue();
    virtual Queue& operator=(const Queue&);
    
    virtual void Push(InfoType AInfo) = 0;
    virtual bool Pop() = 0;
    virtual InfoType GetFirst() const = 0;
    virtual bool IsEmpty() const = 0;
    virtual unsigned GetSize() const = 0;
    virtual InfoType& operator[](unsigned) const = 0;
    virtual const InfoType& GetByIndex(unsigned) const = 0;
    virtual void Browse(void ItemWork(InfoType)) const = 0;
    virtual void Browse(void ItemWork(InfoType&)) = 0;
};

//Очередь на списке
class LQueue: public Queue {
private:
    struct QItem {
        InfoType info;
        QItem *next;
        QItem(InfoType Ainfo): info(Ainfo), next(NULL) {}
    };
    QItem *front, *rear;
    unsigned size;
    
    void Erase();
    void Clone(const Queue&);
public:
    LQueue();
    LQueue(const LQueue&);
    ~LQueue(); // только сообщение о вызове
    LQueue& operator=(const Queue &);
    
    void Push(InfoType AInfo);
    bool Pop();
    InfoType GetFirst() const;
    bool IsEmpty() const;
    unsigned GetSize() const;
    InfoType& operator [] (unsigned) const;
    const InfoType& GetByIndex(unsigned) const;
    void Browse(void ItemWork(InfoType)) const;
    void Browse(void ItemWork(InfoType&));
};

//Очередь на массиве
class CQueue: public Queue {
private:
    InfoType* Arr;
    unsigned first, last, capacity;
    // last указывает на первую свободную позицию в очереди
    void Erase();
    void Clone(const Queue&);
public:
    CQueue(int  cpty = 100);
    CQueue(const CQueue&);
    ~CQueue(); // только сообщение о вызове
    CQueue& operator=(const Queue &);
    
    void Push(InfoType AInfo);
    bool Pop();
    InfoType GetFirst() const;
    bool IsEmpty()const;
    unsigned GetSize() const;
    InfoType& operator [] (unsigned) const;
    const InfoType& GetByIndex(unsigned) const;
    void Browse(void ItemWork(InfoType)) const;
    void Browse(void ItemWork(InfoType&));
};
