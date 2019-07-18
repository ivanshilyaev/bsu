#include <iostream>
#include "Queue.hpp"
#include "MyException.h"
using namespace std;

void f(InfoType A) {
    cout<<A<<endl;
}

void f2(InfoType &A) {
    A++;
}

int main() {
    try {
        //LQueue
//        LQueue q;
//        q.Push(14); q.Push(17); q.Push(21);
//        LQueue q2;
//        q2=q; //работа оператора присванивания
//        q2.Browse(f2); //изменение элементов очереди
//        cout<<"Browse:"<<endl;
//        q2.Browse(f);
//        cout<<"First: ";
//        cout<<q2.GetFirst()<<endl; //14
//        q2.Pop(); q2.Pop(); q2.Pop();
//        if (q2.IsEmpty())
//            cout<<"empty q2"<<endl; //+
//        cout<<q[2]<<endl; //21
//
//        CQueue q3(5);
        
        
        //CQueue
//        CQueue *q3=new CQueue(5);
//        for (int i=0; i<5; i++)
//            q3->Push(i);
//
//        Queue *q1, *q2;
//        q1=new CQueue();
//        q2=new CQueue();
//
//        *q1=*q3;
//        q1->Browse(f);
////        *q2=*q1;
////        q2->Browse(f);
        
        
        
        //delete q3;
        
        
        Queue *q1;
        LQueue *lq=new LQueue;
        for (int i=0; i<5; ++i)
            lq->Push(i);
        q1=lq;
        cout<<typeid(q1).name()<<endl;
        q1->Browse(f);
        LQueue *lq2=new LQueue;
        *lq2=*q1;
        lq2->Browse(f);
        cout<<typeid(lq2).name()<<endl;
        
        
        
    }
    catch(MyException &e) {
        cout<<e.what()<<endl;
    }
}
