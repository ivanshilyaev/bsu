#include <iostream>
#include "String.hpp"
using namespace std;

int main() {
    try {
        String a("hello");
        String b=" world";
        String c=a+b;
        cout<<c[4]<<endl;
        cout<<c<<endl;
        String d=a;
        String e(d);
        a=a+" aaa";
        //d=d+" bbb";
        cout<<a<<endl;
        cout<<d<<endl;
        cout<<e<<endl;
    }
    catch(const char *e) {
        cout<<e<<endl;
    }
}
