//#pragma warning(disable:4996)
#include <iostream>
#include <fstream>
#include <cmath>
#include <stdlib.h>
using namespace std;

// Числа Армстронга

// Test
// (пробел - разделитель)
//35 -316 9318 saghjfsa  sah 371 s 137 -3.
//35 94 iays -36 383 407
//153 Dag sah      37

bool isArm(int a) {
    int d[10], n=0;
    int b=a;
    while (a>0) {
        int c=a%10;
        int k=0;
        for (int i=0; i<n; i++)
            if (d[i]==c)
                k++;
        if (k==0)
            d[n++]=c;
        a/=10;
    }
    int sum=0;
    for (int i=0; i<n; i++)
        sum+=pow(d[i], n);
    return b==sum;
}

int main() {
    const string numbers="0123456789";
    const int max=300;
    int array[max];
    int n(0);
    string line, seps, word;
    int begin, end(0);
    
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
    
    getline(fin, seps);
    while (getline(fin, line)) {
        begin=0;
        line+=seps[0];
        while ((begin=(int)line.find_first_not_of(seps, begin)) != string::npos) {
            end=(int)line.find_first_of(seps, begin);
            word=line.substr(begin, end-begin);
            try {
                // если последний символ - цифра
                if (numbers.find(word[word.length()-1]) != string::npos) {
                    int a=stoi(word);
                    array[n++]=a;
                }
            } catch(...) {}
            begin=end;
        }
    }
    
    
    
    if (n==0) {
        fout<<"В тексте нет чисел"<<endl;
    }
    else {
        for (int i=0; i<n-1; i++)
            fout<<array[i]<<"; ";
        fout<<array[n-1]<<endl;
        for (int i=0; i<n; i++) {
            if (isArm(array[i]))
                fout<<array[i]<<"->ДА"<<endl;
            else
                fout<<array[i]<<"->НЕТ"<<endl;
        }
    }
    
    fin.close();
    fout.close();
    
    //system("pause");
    return 0;
}
