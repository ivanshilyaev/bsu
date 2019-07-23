#include <iostream>
#include "Actor.hpp"
#include "ActorWithOscar.hpp"
using namespace std;

int main() {
    Actor a("Керри", comedian, 100, 1960);
    cout<<a<<endl<<endl;
    ActorWithOscar ao("Ди Каприо", tragedian, 200, 1970, 1);
    cout<<ao<<endl<<endl;
    Actor a1("Чан", comedian, 80, 1965);
    cout<<a1<<endl<<endl;
    ActorWithOscar ao1("Пит", tragedian, 50, 1968, 2);
    cout<<ao1<<endl<<endl;
    
    ActorWithOscar ao2(ao);
    cout<<ao2<<endl;
    
    Actor *array[4];
    array[0]=&a;
    array[1]=&ao;
    array[2]=&a1;
    array[3]=&ao1;
    
    cout<<"Все актёры в массиве:"<<endl;
    int actorCount(0), oscarCount(0), filmCount(0);
    for (int i=0; i<4; ++i) {
        if (typeid(*array[i]) == typeid(ActorWithOscar)) {
            cout<< *dynamic_cast<ActorWithOscar *>(array[i]) <<endl<<endl;
            oscarCount++;
        }
        else {
            cout<<*array[i]<<endl<<endl;
            actorCount++;
        }
        filmCount+=(*array[i]).getCountOfFilms();
    }
    cout<<"Количество актёров без оскара: "<<actorCount<<endl;
    cout<<"Количество актёров с оскаром: "<<oscarCount<<endl;
    cout<<"Общее количество фильмов у всех актёров: "<<filmCount<<endl;
    
    int prizes;
    cout<<"Введите количество призов"<<endl;
    cin>>prizes;
    bool found=false;
    cout<<"Актёры с меньшим числом призов:"<<endl;
    for (int i=0; i<4; ++i) {
        if (typeid(*array[i]) == typeid(ActorWithOscar)) {
            if ((*dynamic_cast<ActorWithOscar *>(array[i])).getCountOfPrizes()<prizes) {
                cout<< *dynamic_cast<ActorWithOscar *>(array[i]) <<endl<<endl;
                found=true;
            }
        }
    }
    if (!found)
        cout<<"Нет актёров с меньшим числом призов"<<endl;
}
