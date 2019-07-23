#include <iostream>
#include "Auto.hpp"
#include "SharedAuto.hpp"
using namespace std;

int main() {
    Auto a("Reno", best, 2019);
    cout<<a<<endl;
    Auto a2("Porshe", good, 2014);
    Auto a3("Ferrari", requiredRepair, 2010);
    a2=a3;
    cout<<a2<<endl;
    if (a==a2)
        cout<<"States are equal"<<endl;
    else
        cout<<"States are not equal"<<endl;
    Auto a4(a3);
    cout<<a4<<endl;
    
    SharedAuto sa("Mersedes", best, 2018, 50);
    cout<<sa<<endl;
    SharedAuto sa2("Mersedes", good, 2015, 100);
    SharedAuto sa3(sa2);
    cout<<sa3<<endl;
    SharedAuto sa4("Volga", worst, 1990, 5000);
    sa4=sa;
    cout<<sa4<<endl;
    
    if (sa==sa4)
        cout<<"States are equal"<<endl;
    else
        cout<<"States are not equal"<<endl;
    
    if (a==sa)
        cout<<"States are equal"<<endl;
    else
        cout<<"States are not equal"<<endl;
    
    Auto *array[8];
    array[0]=&a;
    array[1]=&a2;
    array[2]=&a3;
    array[3]=&a4;
    array[4]=&sa;
    array[5]=&sa2;
    array[6]=&sa3;
    array[7]=new SharedAuto("Volvo", best, 2017, 200);
    
    cout<<endl;
    cout<<"Autos in array:"<<endl;
    int countAutos(0), countSharedAutos(0);
    int cbest(0), cgood(0), crr(0), cworst(0);
    for (int i=0; i<8; ++i) {
        if (typeid(*array[i]) == typeid(SharedAuto)) {
            cout<< *dynamic_cast<SharedAuto *>(array[i]) <<endl<<endl;
            countSharedAutos++;
        }
        else {
            cout<< *array[i] <<endl<<endl;
            countAutos++;
        }
        switch ((*array[i]).getState()) {
            case best:
                cbest++;
                break;
            case good:
                cgood++;
                break;
            case requiredRepair:
                crr++;
                break;
            case worst:
                cworst++;
                break;
            default:
                break;
        }
    }
    
    cout<<"Autos: "<<countAutos<<endl;
    cout<<"Shared autos: "<<countSharedAutos<<endl;
    cout<<"Best: "<<cbest<<endl;
    cout<<"Good: "<<cgood<<endl;
    cout<<"Requred repair: "<<crr<<endl;
    cout<<"Worst: "<<cworst<<endl;
    
    sort(array, array+8, [](Auto * &a, Auto * &b)->bool {
        if (typeid(a)==typeid(SharedAuto) && typeid(b)==typeid(SharedAuto)) {
            return a->getYear()>b->getYear() ||
            (a->getYear()==b->getYear() && ((dynamic_cast<SharedAuto*>(a))->getSharedHours() > (dynamic_cast<SharedAuto*>(b))->getSharedHours()));
        }
        return a->getYear()>b->getYear();
    });
    cout<<endl;
    cout<<"After sorting:"<<endl;
    for (int i=0; i<8; ++i) {
        if (typeid(*array[i]) == typeid(SharedAuto))
            cout<< *dynamic_cast<SharedAuto *>(array[i]) <<endl<<endl;
        else
            cout<< *array[i] <<endl<<endl;
    }
    
    int kol;
    cout<<"Enter hours of share:"<<endl;
    cin>>kol;
    bool found=false;
    cout<<"Autos with such hours of share:"<<endl;
    for (int i=0; i<8; ++i) {
        if (typeid(*array[i]) == typeid(SharedAuto)) {
            if ((*dynamic_cast<SharedAuto *>(array[i])).getSharedHours() == kol) {
                cout<< *dynamic_cast<SharedAuto *>(array[i]) <<endl<<endl;
                found=true;
            }
        }
    }
    if (!found)
        cout<<"No such autos"<<endl;
}
