#include <iostream>
#include "Matrix.hpp"
using namespace std;

int main() {
    try {
//        Matrix A(2,2), B(2,2), C(2,2), D(2,2);
//        srand(time(NULL));
//        for (int i=0; i<2; i++)
//            for (int j=0; j<2; j++) {
//                A.SetAij(i, j, 1+rand()%3);
//                B.SetAij(i, j, 1+rand()%3);
//                D.SetAij(i, j, 1+rand()%3);
//            }
//        cout<<"A:"<<endl;
//        cout<<A;
//        cout<<"B:"<<endl;
//        cout<<B;
//        cout<<"D:"<<endl;
//        cout<<D;
//        double k=1+rand()%5;
//        cout<<"k="<<k<<endl;
//        C=k*(A+B)*D+A/k;
//        cout<<"result C:"<<endl;
//        cout<<C;
        
        Matrix a(2,2);
        Matrix b(2,2); // создали две матрицы
        cout<<"Enter a"<<endl;
        cin>>a; // первую ввели с клавиатуры
        srand(time(NULL));
        for (int i=0; i<2; i++)
            for (int j=0; j<2; j++)
                b.SetAij(i, j, i*j+rand()%10); // вторую заполняем случайными числами
        cout<<"Matrix b:"<<endl;
        cout<<b;
        Matrix c = a+b; // сложили две матрицы и присвоили сумму новой матрице
        cout<<"IDs:"<<endl;
        cout<<a.GetID()<<endl;
        cout<<b.GetID()<<endl;
        cout<<c.GetID()<<endl;
        cout<<"Matrix c=a+b:"<<endl;
        cout<<c;
    }
    catch (const char* e) {
        cout<<e<<endl;
    }
}
