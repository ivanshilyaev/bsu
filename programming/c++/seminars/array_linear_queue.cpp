#include <iostream>
#include <iomanip>
#include <cmath>
using namespace std;

// Линейная очередь на массиве

typedef double T;

struct Queue {
    int size;
    int front;
    int rear;
    T* array;
};

void init(int len, Queue& q) {
    q.size=len;
    q.front=0;
    q.rear=0;
    q.array=new T[len];
}

// сдвиг
void shift(Queue& q) {
    int i;
    for (i=0; i<q.rear-q.front; i++)
        q.array[i]=q.array[q.front+i];
    q.front=0;
    q.rear=i;
}

void push(Queue& q, T elem) {
    if ((q.rear==q.size) && (q.front==0))
        throw "Error: queue is full";
    if (q.rear==q.size)
        shift(q);
    q.array[q.rear++]=elem;
}

T pop(Queue& q) {
    if (q.rear==q.front)
        throw "Error: queue is empty";
    T data=q.array[q.front++];
    return data;
}

void del(Queue& q) {
    delete []q.array;
    q.size=0;
    q.front=0;
    q.rear=0;
}

int main() {
    try {
        cout<<"Enter kol"<<endl;
        int kol;
        cin>>kol;
        Queue q;
        init(kol, q);
        srand(time(NULL));
        cout<<"Adding elements to queue..."<<endl;
        for (int i=0; i<kol; i++) {
            double d=1+(double)rand()/RAND_MAX*100;
            cout<<setprecision(5)<<d<<endl;
            push(q, d);
        }
        
        pop(q);
        push(q, 1.0);
        pop(q);
        push(q, 1.0);
        
        cout<<"Taking elements from queue..."<<endl;
        for (int i=0; i<kol; i++)
            cout<<setprecision(5)<<pop(q)<<endl;
    } catch (const char* e) {
        cout<<e<<endl;
    }
}
