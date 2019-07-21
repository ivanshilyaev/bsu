#include <iostream>
#include <string>
using namespace std;

// Линейный однонаправленный список

// 1. Создать упорядоченный по возрастанию номера телефона телефонный справочник. Содержит номер телефона и ФИО

//const int max=80;

struct telfio {
    int tel;
    char FIO[80];
};

struct ListItem {
    telfio Info;
    ListItem* Next;
};
ListItem* First;

bool insert(const telfio* Elem) {
    ListItem* prev;
    ListItem* next;
    next=First;
    prev=NULL;
    while (next) {
        if (next->Info.tel==Elem->tel)
            return false;
        if (next->Info.tel>Elem->tel)
            break;
        prev=next;
        next=next->Next;
    }
    ListItem* p = new ListItem;
    p->Info.tel=Elem->tel;
    strcpy(p->Info.FIO, Elem->FIO);
    if (prev==NULL) {
        p->Next=First;
        First=p;
    }
    else {
        p->Next=prev->Next;
        prev->Next=p;
    }
    return true;
}

void print(ListItem* First) {
    ListItem* Elem=First;
    if (!Elem) {
        cout << "Список пуст" << endl;
        return;
    }
    while (Elem) {
        cout<<"Tel: "<<Elem->Info.tel<<", FIO: "<<Elem->Info.FIO<<endl;
        Elem=Elem->Next;
    }
}

int main() {
    while (true) {
        int tel;
        cout<<"Enter tel or -1 to exit"<<endl;
        cin>>tel;
        if (tel==-1)
            break;
        char* FIO = new char[80];
        cout<<"Enter FIO"<<endl;
        cin>>FIO;
        telfio* telfio1 = new telfio;
        telfio1->tel=tel;
        strcpy(telfio1->FIO, FIO);
        insert(telfio1);
    }
    
    print(First);
    
    return 0;
}
