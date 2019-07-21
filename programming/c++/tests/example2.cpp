#include <iostream>
#include <fstream>
using namespace std;

// 2. В текстовом файле в первой строке перечислены разделители слов, в
// следующих строках записан текст. Найти среднее арифметическое
// отрицательных вещественных чисел. Целые числа не считаются
// вещественными. В выходной файл в первую строку записать все
// отрицательные вещественные числа, разделяя пробелами, во вторую строку
// - среднее арифметическое.

int main() {
    const string numbers="1234567890";
    const int max=300;
    string line, word;
    int begin, end(0);
    double* array = new double[max];
    int n=0;
    
    ifstream fin("input.txt", ios_base::in);
    ofstream fout("output.txt", ios_base::out);
    
    if (!fin.is_open()) {
        cout<<"Error"<<endl;
        return 1;
    }
    
    getline(fin, line);
    if (line.empty() && fin.eof()) {
        cout<<"Error"<<endl;
        return 1;
    }
    fin.seekg(0);
    
    string seps;
    getline(fin, seps);
    while (getline(fin, line)) {
        begin=0;
        line+=seps[0];
        while ((begin=(int)line.find_first_not_of(seps, begin)) != string::npos) {
            end=(int)line.find_first_of(seps, begin);
            word=line.substr(begin, end-begin);
            try {
                if (word.find('.') != string::npos) {
                    char* ptr;
                    char** ptr1=&ptr;
                    char num[30];
                    strcpy(num, word.c_str());
                    strtod(num, ptr1);
                    if (strlen(*ptr1)==0) {
                        double d=stod(word);
                        array[n]=d;
                        n++;
                    }
                }
            } catch (...) {
                cout<<"Error"<<endl;
            }
            begin=end;
        }
    }
    
    double sum=0, kol=0;
    for (int i=0; i<n; i++)
        if (array[i]<0) {
            fout<<array[i]<<" ";
            sum+=array[i]; kol++;
        }
    fout<<endl;
    fout<<sum/kol;
    
    delete []array;
    fin.close();
    fout.close();
}
