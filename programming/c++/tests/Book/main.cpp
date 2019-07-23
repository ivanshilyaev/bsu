#include <iostream>
#include "Book.hpp"
#include "Translate.hpp"
#include <algorithm>
using namespace std;

int main() {
    setlocale(LC_ALL, ".1251");
    
    Book b("Airport", narrative, 1968);
    Translate t("Аэропорт", narrative, 2019, "русский");
    Book b2("Hotel", story, 1965);
    b2 = b;
    Translate t2("Отель", story, 2015, "русский");
    t2 = t;
    
    Book *books[4];
    books[0] = &b;
    books[1] = &b2;
    books[2] = &t;
    books[3] = &t2;
    for (int i = 0; i < 4; i++) {
        if (typeid(*books[i]) == typeid(Translate))
            cout << *dynamic_cast<Translate *>(books[i]) << endl;
        else
            cout << *books[i] << endl;
        cout << endl;
    }
    
    //sorting
    for (int i=0; i<3; ++i) {
        for (int j=i+1; j<4; ++j) {
            char *c1, *c2;
            (*books[i]).getName(c1);
            (*books[j]).getName(c2);
            if (strcmp(c1, c2)>0) {
                Book *tmp=books[i];
                books[i]=books[j];
                books[j]=tmp;
            }
        }
    }

    int booksCount(0), translatesCount(0);
    
    cout<<"==================================================="<<endl;
    cout<<endl;
    cout << "After sorting" << endl;
    for (int i = 0; i < 4; i++) {
        cout<<"Class name: "<<(typeid(*books[i])).name()<<endl;
        if (typeid(*books[i]) == typeid(Translate)) {
            cout << *dynamic_cast<Translate *>(books[i]) << endl;
            translatesCount++;
        }
        else {
            cout << *books[i] << endl;
            booksCount++;
        }
        cout << endl;
    }
    cout<<"Количество объектов типа Книга: "<<booksCount<<endl;
    cout<<"Количество объектов типа Перевод: "<<translatesCount<<endl;
    
    return 0;
}
