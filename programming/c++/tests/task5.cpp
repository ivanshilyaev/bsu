//#pragma warning(disable:4996)
#include <iostream>
#include <fstream>
#include <cmath>
#include <stdlib.h>
using namespace std;

// Близнецы

// Test
//3 5
//5 7
//6 8
//9 11
//11 13
//18 214
//-216 592318
//28 28

bool isPrime(int a) {
    int i=2, kol=1;
    while (i<=sqrt(a)) {
        if (a%i==0)
            kol++;
        i++;
    }
    return (a!=1 && kol==1);
}

int main() {
    string line;
    int a,b;
    
    ifstream fin("input.txt", ios_base::in);
    ofstream fout("output.txt", ios_base::out);
    
    if (!fin.is_open()) {
        cout<<"Error: input file doesn't open"<<endl;
        return 1;
    }
    
    getline(fin, line);
    if (line.empty() && fin.eof()) {
        cout<<"Error: input file is empty"<<endl;
        return 1;
    }
    fin.seekg(0);
    
    while (fin>>a) {
        fin>>b;
        if (abs(a-b)==2 && isPrime(a) && isPrime(b))
            fout<<a<<" "<<b<<"->"<<"близнецы"<<endl;
        else {
            fout<<a<<" "<<b<<"->"<<"нет"<<endl;
        }
    }
    
    fin.close();
    fout.close();
    
    //system("pause");
    return 0;
}
