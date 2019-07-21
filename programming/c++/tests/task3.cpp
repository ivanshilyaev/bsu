#include <iostream>
#include <fstream>
#include <stdlib.h>
using namespace std;

// В первой строке текстового файла подряд записаны разделители слов,
// расположенные в последующих строках. Записать в выходной файл сумму
// всех вещественных чисел, которые встречаются в каждой из последующих
// строк в формате 1 Число+2 Число+...+N Число=Сумма.
//Если чисел нет, записать в выходной файл "В строке нет чисел"
//Например:
//3.6+7.4=11.0
//в строке нет чисел
//4.6+5.4=10.0

// Test
// (пробел - разделитель)
//3.6 7.4
//Fdghjs
//Erg sakfj.   4.6 sfai    5.4
//

int main() {
    const int max=300;
    double array[max];
    int n;
    string line, seps, word;
    int begin, end(0);
    double sum;
    
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
        n=0; sum=0;
        line+=seps[0];
        while ((begin=(int)line.find_first_not_of(seps, begin)) != string::npos) {
            end=(int)line.find_first_of(seps, begin);
            word=line.substr(begin, end-begin);
            try {
                double d=stod(word);
                array[n++]=d;
                sum+=d;
            } catch(...) {}
            begin=end;
        }
        if (n==0) {
            fout<<"В строке нет чисел"<<endl;
        }
        else {
            for (int i=0; i<n-1; i++)
                fout<<array[i]<<"+";
            fout<<array[n-1]<<"="<<sum<<endl;
        }
    }
    
    fin.close();
    fout.close();
    
    //system("pause");
    return 0;
}
