#include <iostream>
#include <fstream>
#include <stdlib.h>
using namespace std;

// Исходный файл "IN5. TXT" в первой строке содержит разделители, в
// остальных - текст. В выходной файл "OUT5.TXT" в первую строку через
// запятую записать слова текста, которые являются натуральными числами.
// Проверить, являются ли эти числа совершенными ( делители в сумме
// образуют само число, исключая само число 6 = 1+2+3 ) Записать в
// выходной файл в таком формате:
//Совершенное 6=1+2+3
//Несовершенное 8

// Test
// (пробел - разделитель)
//35 -316 33550336 9318 saghjfsa  sah s 137 -3.  496
//35 94 iays -36 383 6
//28 8128
//Dag sah      37

bool isSov(int a) {
    int sum=0, b=a, i=1;
    while (i<=a/2) {
        if (a%i==0)
            sum+=i;
        i++;
    }
    return sum==b;
}

int* printDivs(int a, int& size) {
    int b=a, n=0, i=1;
    while (i<=a/2) {
        if (a%i==0)
            n++;
        i++;
    }
    int* array = new int[n];
    size=n;
    i=1;
    int j=0;
    while (i<=b/2) {
        if (b%i==0)
            array[j++]=i;
        i++;
    }
    return array;
}

int main() {
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
                int a=stoi(word);
                if (a>0)
                    array[n++]=a;
            } catch(...) {}
            begin=end;
        }
    }
    
    if (n==0) {
        cout<<"Error: no integers in the text"<<end;
    }
    else {
        for (int i=0; i<n-1; i++)
            fout<<array[i]<<" ";
        fout<<array[n-1]<<endl;
        for (int i=0; i<n; i++)
            if (isSov(array[i])) {
                fout<<"Совершенное "<<array[i]<<"=";
                int size;
                int* divs = printDivs(array[i], size);
                for (int j=0; j<size-1; j++)
                    fout<<divs[j]<<"+";
                fout<<divs[size-1]<<endl;
            }
            else {
                fout<<"Несовершенное "<<array[i]<<endl;
            }
    }
    
    fin.close();
    fout.close();
    
    //system("pause");
    return 0;
}
