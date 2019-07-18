#include <iostream>
#include "Person.hpp"
#include "Student.hpp"
#include "Professor.hpp"
using namespace std;

int main() {
    setlocale(LC_ALL, ".1251");
    Person *array[50];
    bool sBool=false, pBool=false;
    int index=0;
    char c;
    cout<<"Menu:"<<endl;
    cout<<"1 - enter student"<<endl;
    cout<<"2 - enter professor"<<endl;
    cout<<"3 - display list of students"<<endl;
    cout<<"4 - display list of professors"<<endl;
    cout<<"5 - exit"<<endl;
    while (true) {
        cout<<"Enter command:"<<endl;
        cin>>c;
        switch (c) {
            case '1': {
                cout<<"Enter FIO:"<<endl;
                char *f; f=new char[300];
                while (getchar()!='\n');
                cin.getline(f, 300, '\n');
                cout<<"Enter course:"<<endl;
                int co; cin>>co;
                cout<<"Enter group:"<<endl;
                int gr; cin>>gr;
                Student *stud=new Student(f, co, gr);
                array[index++]=stud; sBool=true;
                delete f;
                break;
            }
            case '2': {
                cout<<"Enter FIO:"<<endl;
                char *f; f=new char[300];
                while (getchar()!='\n');
                cin.getline(f, 300, '\n');
                cout<<"Enter pulpit:"<<endl;
                char *p; p=new char[300];
                cin.getline(p, 300, '\n');
                Professor *prof=new Professor(f, p);
                array[index++]=prof; pBool=true;
                delete f;
                break;
            }
            case '3': {
                if (!sBool)
                    cout<<"No students in list"<<endl;
                else {
                    for (int i=0; i<index; i++) {
                        if (typeid(*array[i])==typeid(Student)) {
                            array[i]->display();
                        }
                    }
                }
                break;
            }
            case '4': {
                if (!pBool)
                    cout<<"No professors in list"<<endl;
                else {
                    for (int i=0; i<index; i++) {
                        if (typeid(*array[i])==typeid(Professor)) {
                            array[i]->display();
                        }
                    }
                }
                break;
            }
            case '5': {
                return 0;
                break;
            }
            default:
                break;
        }
    }
}

//вариант 4
//for (int i=0; i<index-1; ++i) {
//    for (int j=i+1; j<index; j++) {
//        char *f1=new char[300]; array[i]->getFIO(f1);
//        char *f2=new char[300]; array[j]->getFIO(f2);
//        if (strcmp(f1, f2)>0) {
//            Person *person=array[i];
//            array[i]=array[j];
//            array[j]=person;
//        }
//    }
//}
//for (int i=0; i<index; ++i) {
//    if (typeid(*array[i])==typeid(Professor)) {
//        char *pulpit=new char[300];
//        dynamic_cast<Professor *>(array[i])->getPulpit(pulpit);
//        if (strcmp(pulpit, "isy")==0)
//            array[i]->display();
//            }
//    }



//вариант 5
//for (int i=0; i<index; ++i) {
//    if (typeid(*array[i])==typeid(Professor)) {
//        char *pulpit=new char[300];
//        dynamic_cast<Professor *>(array[i])->getPulpit(pulpit);
//        if (strcmp(pulpit, "isy")==0) {
//            array[i]->display();
//            cout<<array[i]->getID()<<endl;
//        }
//    }
//}
