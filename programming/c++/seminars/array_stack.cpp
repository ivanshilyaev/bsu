#include <iostream>
using namespace std;

// Стек на массиве

typedef char T;

struct stack {
    int top;
    int size;
    T* array;
};

void init(int len, stack &s) {
    s.size=len;
    s.array = new T[s.size];
    s.top=0;
}

void push(T data, stack &s) {
    if (s.top==s.size)
        throw "Error: stack is full";
    s.array[s.top++]=data;
}

T pop(stack &s) {
    if (s.top==0)
        throw "Error: stack is empty";
    return s.array[--s.top];
}

void del(stack& s) {
    delete s.array;
    s.size=0;
    s.top=0;
}

bool empty(stack& s) {
    return s.top==0;
}

int main() {
    stack s;
    string line;
    cout<<"Enter string"<<endl;
    cin>>line;
    init((int)line.length(), s);
    try {
        for (int i=0; i<line.length(); i++) {
            if (line[i]=='(')
                push(line[i], s);
            else if (line[i]==')')
                pop(s);
        }
    } catch (const char* c) {
        cout<<c<<endl;
        return 0;
    }
    if (empty(s))
        cout<<"Yes"<<endl;
    else cout<<"No"<<endl;
    return 0;
}
