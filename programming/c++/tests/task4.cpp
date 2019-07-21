//#pragma warning(disable:4996)
#include <iostream>
#include <fstream>
#include <stdlib.h>
using namespace std;

// В первой строке текстового файла записаны разделители слов,
// расположенных в последующих строках. Записать в выходной файл все
// целые десятичные числа, которые встречаются в тексте(через пробел),
// Для каждого числа найти количество различных цифр, сами эти цифры и
// расположить их в порядке возрастания, В выходной файл записать запись
// формата - Число:Количество раз,цифр:Цифры; Пример:15231:4:1,2,3,5

// Test
// ,.;!/?
//35 -3/16 93!18 saghjfsa  sah s 137 -3.
//35 923674 iays -331206 3319?833
//Dag sah      37


int comp(const int* i, const int* j) {
    return *i-*j;
}

int* getDigits(int a, int& size) {
    if (a<0)
        a=abs(a);
    int d[10], n=0;
    while (a>0) {
        int c=a%10;
        int kol=0;
        for (int i=0; i<n; i++)
            if (d[i]==c)
                kol++;
        if (kol==0)
            d[n++]=c;
        a/=10;
    }
    qsort(d, n, sizeof(int), (int(*) (const void *, const void *)) comp);
    size=n;
    int* digits = new int[n];
    for (int i=0; i<n; i++)
        digits[i]=d[i];
    return digits;
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
                array[n++]=a;
            } catch(...) {}
            begin=end;
        }
    }
    
    
    
    if (n==0) {
        fout<<"В тексте нет чисел"<<endl;
    }
    else {
        for (int i=0; i<n-1; i++)
            fout<<array[i]<<" ";
        fout<<array[n-1]<<endl;
        for (int i=0; i<n; i++) {
            int size=0;
            int* digits=getDigits(array[i], size);
            fout<<array[i]<<":"<<size<<":";
            for (int j=0; j<size-1; j++)
                fout<<digits[j]<<",";
            fout<<digits[size-1]<<endl;
            delete []digits;
        }
    }
    
    fin.close();
    fout.close();
    
    //system("pause");
    return 0;
}
