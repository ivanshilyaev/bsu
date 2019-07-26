#include <iostream>
#include <cstring>
using namespace std;

//callback-функция
void doSmt(int &i) {
    i++;
}

void doSmt2(double &i) {
    i++;
}

void doSmt3(const char * &i) {
    char *tmp=new char[strlen(i+7)];
    strcpy(tmp, i);
    strcat(tmp, " hello");
    delete[] i;
    i=new char[strlen(i+7)];
    strcpy(const_cast<char *>(i), tmp);
}

//не callback-функция
void doSmt4(char * i) {
    cout<<"doSmt function for char * "<<i<<endl;
}

template <typename TInfo = int>
class TDeque {
    struct TDequeItem {
        TInfo Info;
        TDequeItem* Next;
        TDequeItem* Prev;
    };
    TDequeItem *Front, *Rear;
    int Size;
    void Erase();
    void Clone(const TDeque &);
    bool DeleteItem(TDequeItem*);
    void * PtrByIndex(unsigned) const;
public:
    TDeque();
    TDeque(const TDeque &);
    virtual ~TDeque();
    void InsFront(TInfo);
    void InsRear(TInfo);
    bool DelFront();
    bool DelRear();
    const TDeque & operator=(const TDeque &);
    TInfo & operator[](unsigned);
    const TInfo & GetByIndex(unsigned) const;
    void SetByIndex(TInfo, unsigned);
    void Browse(void(TInfo&));
    void Browse(void(TInfo)) const;
    int getSize() const { return Size; }
};

template <typename TInfo> void TDeque<TInfo>::Erase() {
    while (DeleteItem(Front));
    Size=0;
}

template <typename TInfo> void TDeque<TInfo>::Clone(const TDeque<TInfo> &TD) {
    TDequeItem *tmp=TD.Front;
    Size=TD.Size;
    for (unsigned i=0; i<Size; ++i) {
        InsRear(tmp->Info);
        tmp=tmp->Next;
    }
}

template <typename TInfo> bool TDeque<TInfo>::DeleteItem(TDequeItem *Item) {
    if (Front==NULL && Rear==NULL)
        return false;
    if (Item==Front)
        Front=Front->Next;
    else if (Item==Rear)
        Rear=Rear->Prev;
    else {
        Item->Prev->Next=Item->Next;
        Item->Next->Prev=Item->Prev;
    }
    delete Item;
    Size--;
    if (Size==0) {
        Front=NULL;
        Rear=NULL;
    }
    return true;
}

//специализация для char *
template <> bool TDeque<const char *>::DeleteItem(TDequeItem *Item) {
    if (Front==NULL && Rear==NULL)
        return false;
    if (Item==Front)
        Front=Front->Next;
    else if (Item==Rear)
        Rear=Rear->Prev;
    else {
        Item->Prev->Next=Item->Next;
        Item->Next->Prev=Item->Prev;
    }
    delete[] Item->Info;
    delete Item;
    Size--;
    if (Size==0) {
        Front=NULL;
        Rear=NULL;
    }
    return true;
}

template <typename TInfo> void * TDeque<TInfo>::PtrByIndex(unsigned n) const {
    if ((n<0) || (n>=Size))
        throw "Invalid index";
    TDequeItem *tmp=Front;
    for (unsigned i=0; i<n; ++i)
        tmp=tmp->Next;
    return tmp;
}

template <typename TInfo> TDeque<TInfo>::TDeque() { Front=NULL; Rear=NULL; }

template <typename TInfo> TDeque<TInfo>::TDeque(const TDeque &TD) {
    Clone(TD);
}

template <typename TInfo> TDeque<TInfo>::~TDeque() {
    Erase();
}

template <typename TInfo> void TDeque<TInfo>::InsFront(TInfo ainfo) {
    TDequeItem *Elem=new TDequeItem();
    Elem->Info=ainfo;
    Elem->Prev=NULL;
    if (Front!=NULL) {
        Elem->Next=Front;
        Front->Prev=Elem;
    }
    else {
        Elem->Next=NULL;
        Rear=Elem;
    }
    Front=Elem;
    Size++;
}

//специализация для char *
template <> void TDeque<const char *>::InsFront(const char * ainfo) {
    TDequeItem *Elem=new TDequeItem();
    Elem->Info=new char[strlen(ainfo)+1];
    strcpy((char *)Elem->Info, ainfo);
    Elem->Prev=NULL;
    if (Front!=NULL) {
        Elem->Next=Front;
        Front->Prev=Elem;
    }
    else {
        Elem->Next=NULL;
        Rear=Elem;
    }
    Front=Elem;
    Size++;
}

template <typename TInfo> void TDeque<TInfo>::InsRear(TInfo ainfo) {
    TDequeItem *Elem=new TDequeItem();
    Elem->Info=ainfo;
    Elem->Next=NULL;
    if (Rear!=NULL) {
        Elem->Prev=Rear;
        Rear->Next=Elem;
    }
    else {
        Elem->Prev=NULL;
        Front=Elem;
    }
    Rear=Elem;
    Size++;
}

//специализация для char *
template <> void TDeque<const char *>::InsRear(const char * ainfo) {
    TDequeItem *Elem=new TDequeItem();
    Elem->Info=new char[strlen(ainfo)+1];
    strcpy((char *)Elem->Info, ainfo);
    Elem->Next=NULL;
    if (Rear!=NULL) {
        Elem->Prev=Rear;
        Rear->Next=Elem;
    }
    else {
        Elem->Prev=NULL;
        Front=Elem;
    }
    Rear=Elem;
    Size++;
}

template <typename TInfo> bool TDeque<TInfo>::DelFront() {
    if (Size==0)
        return false;
    TDequeItem *tmp=Front;
    Front=Front->Next;
    delete tmp;
    Size--;
    if (Size==0)
        Rear=NULL;
    return true;
}

//специализация для char *
template <> bool TDeque<const char *>::DelFront() {
    if (Size==0)
        return false;
    TDequeItem *tmp=Front;
    Front=Front->Next;
    delete[] tmp->Info;
    delete tmp;
    Size--;
    if (Size==0)
        Rear=NULL;
    return true;
}

template <typename TInfo> bool TDeque<TInfo>::DelRear() {
    if (Size==0)
        return false;
    TDequeItem *tmp=Rear;
    Rear=Rear->Prev;
    delete tmp;
    Size--;
    if (Size==0)
        Front=NULL;
    return true;
}

//специализация для char *
template <> bool TDeque<const char *>::DelRear() {
    if (Size==0)
        return false;
    TDequeItem *tmp=Rear;
    Rear=Rear->Prev;
    delete[] tmp->Info;
    delete tmp;
    Size--;
    if (Size==0)
        Front=NULL;
    return true;
}

template <typename TInfo> const TDeque<TInfo> & TDeque<TInfo>::operator=(const TDeque<TInfo> &TD) {
    if (this!=&TD) {
        Erase();
        Clone(TD);
    }
    return *this;
}

template <typename TInfo> TInfo & TDeque<TInfo>::operator[](unsigned n) {
    if (typeid(TInfo)==typeid(const char *))
        throw "Using of operator[] is prohibited; use SetByIndex or GetByIndex instead";
    return (TInfo&) ((TDequeItem*)PtrByIndex(n))->Info;
}

template <typename TInfo> const TInfo & TDeque<TInfo>::GetByIndex(unsigned int n) const {
    return ((TDequeItem*)PtrByIndex(n))->Info;
}

template <typename TInfo> void TDeque<TInfo>::SetByIndex(TInfo ainfo, unsigned n) {
    ((TDequeItem*)PtrByIndex(n))->Info=ainfo;
}

//специализация для char *
template <> void TDeque<const char *>::SetByIndex(const char *ainfo, unsigned n) {
    char *curr=(char *)((TDequeItem*)PtrByIndex(n))->Info;
    delete[] curr;
    curr=new char[strlen(ainfo)+1];
    strcpy(curr, ainfo);
    ((TDequeItem*)PtrByIndex(n))->Info=curr;
}

template <typename TInfo> void TDeque<TInfo>::Browse(void ItemWork(TInfo&)) {
    TDequeItem *tmp = Front;
    for (unsigned i=0; i<Size; i++) {
        ItemWork(tmp->Info);
        tmp = tmp->Next;
    }
}

template <typename TInfo> void TDeque<TInfo>::Browse(void ItemWork(TInfo)) const {
    TDequeItem *tmp = Front;
    for (unsigned i=0; i<Size; i++) {
        ItemWork(tmp->Info);
        tmp = tmp->Next;
    }
}

int main() {
    try {
        //test for char
        TDeque<const char *> deq;
        deq.InsFront((const char *)"world");
        deq.InsFront((const char *)"ivan");
        deq.InsRear((const char *)"hello");
        deq.Browse(*doSmt3);
        deq.DelFront();
        for (int i=0; i<deq.getSize(); ++i)
            cout<<deq.GetByIndex(i)<<endl;
        
        //test for double
        //        TDeque<double> deq;
        //        deq.InsFront(14.221);
        //        deq.InsFront(18.156);
        //        deq.InsRear(28.3278);
        //        deq.Browse(*doSmt2);
        //        deq.DelFront();
        //        for (int i=0; i<deq.getSize(); ++i)
        //            cout<<deq.GetByIndex(i)<<endl;
    }
    catch (const char * e) {
        cout<<e<<endl;
    }
}
