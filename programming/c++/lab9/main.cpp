#include <iostream>
#include <stdio.h>
#include <fstream>
#include <string>
using namespace std;

int main() {
    const int max_strings = 10;
    const string alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    string array[max_strings];
    string line, word;
    int begin(0), end(0), k=0;
    
    int i=0, max=0, tek=0, tek_word_max=0, tek_line_max=0;
    
    // Алгоритм: делим строку на слова, ищем максимально число подряд идущих символов в слове, затем в строке,
    // затем в файле. Если найдено новое максимальное количество, то перезаписываем результирующий массив.
    
    // reading from input.txt
    ifstream fin("input.txt", ios_base::in);
    if (!fin.is_open()) {
        printf("Input file doesn't open!\n");
        return 1;
    }
    else {
        while (getline(fin, line)) {
            k++; // счётчик строк. Если он равен нулю, то файл пустой
            line.append(" ");
            begin=0;
            tek_line_max=-1;
            while ((begin=(int)line.find_first_of(alphabet, begin)) != string::npos) {
                end=(int)line.find_first_not_of(alphabet, begin);
                word=line.substr(begin, end-begin);
                tek_word_max=1;
                int j=0;
                while (j<word.length()-1) {
                    tek=1;
                    while (tolower(word[j])==tolower(word[j+1])) {
                        tek++;
                        j++;
                    }
                    if (tek>tek_word_max)
                        tek_word_max=tek;
                    j++;
                }
                if (tek_word_max>tek_line_max)
                    tek_line_max=tek_word_max;
                begin=end;
            }
            if (tek_line_max==max && i<10) {
                array[i] = line;
                i++;
            }
            else if (tek_line_max>max) {
                max=tek_line_max;
                i=0;
                array[i]=line;
                i++;
            }
        }
        if (k==0) {
            printf("Input file is empty!\n");
            fin.close();
            return 0;
        }
        fin.close();
    }
    
    if (max==0) {
        printf("No words in input.txt!\n");
    }
    
    // writing to console
    for (int j = 0; j < i; j++)
        cout << array[j] << endl;
    
    // writing to output.txt
    //    ofstream fout("output.txt", ios_base::out);
    //    if (!fout.is_open()) {
    //        printf("Output file doesn't open!\n");
    //    }
    //    else {
    //        for (int j = 0; j < i; j++) {
    //            fout << array[j] << endl;
    //        }
    //
    //        fout.close();
    //    }
    
    return 0;
}
