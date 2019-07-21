#include <iostream>
using namespace std;

// Стек на списке

typedef char T;

struct ListItem {
    T info;
    ListItem* Next;
};
ListItem* First;

void insertToBegin(const T Elem) {
    ListItem* p = new ListItem;
    p->info=Elem;
    ListItem* tmp=First;
    First=p;
    p->Next=tmp;
}


void push(const T Elem) {
    insertToBegin(Elem);
}

T pop() {
    T data=First->info;
    ListItem* p=First;
    First=First->Next;
    delete p;
    return data;
}

bool empty() {
    return First==NULL;
}

int main() {
    string line;
    cout<<"Enter string"<<endl;
    cin>>line;
    for (int i=0;i<line.length(); i++) {
        if (line[i]=='(')
            push(line[i]);
        else if (line[i]==')')
            pop();
    }
    if (empty())
        cout<<"Yes"<<endl;
    else
        cout<<"No"<<endl;
    return 0;
}
