#include <iostream>
#include <fstream>
#include <stdlib.h>
using namespace std;

// В первой строке текстового файла записаны разделители слов,
// расположенных в последующих строках. Записать в выходной файл через
// точку с запятой все целые числа которые встречаются в тексте, найти
// сумму цифр каждого числа и в следующую строчку выходного файла вывести
// числа в порядке возрастания суммы их цифр, числа разделить запятыми.

// Test
// (пробел - разделитель)
//35 -316 9318 saghjfsa  sah s 137 -3.
//35 94 iays -36 383
//Dag sah      37

int sumOfDigits(int a) {
    if (a<0)
        a=abs(a);
    int sum=0;
    while (a>0) {
        sum+=a%10;
        a/=10;
    }
    return sum;
}

int comp(const int* i, const int* j) {
    return sumOfDigits(*i)-sumOfDigits(*j);
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
                array[n++]=stoi(word);
            } catch(...) {}
            begin=end;
        }
    }
    
    if (n==0) {
        cout<<"Error: no integers in the text"<<end;
    }
    else {
        for (int i=0; i<n-1; i++)
            fout<<array[i]<<"; ";
        fout<<array[n-1]<<endl;
        qsort(array, n, sizeof(int), (int(*) (const void *, const void *)) comp);
        for (int i=0; i<n-1; i++)
            fout<<array[i]<<", ";
        fout<<array[n-1]<<endl;
    }
    
    fin.close();
    fout.close();
    
    //system("pause");
    return 0;
}
