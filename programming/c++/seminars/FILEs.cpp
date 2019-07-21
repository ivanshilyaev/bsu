#include <iostream>
#include <stdio.h>
#include <string>
using namespace std;

// 1. Частотный словарь заданного текста в алфавитном порядке.
// Слово - сколько раз встречалось в тексте.

int main() {
    const int max_words = 1000;
    const int max_chars_in_string = 300;

    string alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    string word;
    int begin, end(-1), i(0); // i - number of words
    string* array = new string[max_words];
    int* numbers = new int[max_words];

    // initialize k
    int k;
    printf("Enter positive number k:\n");
    scanf("%d", &k);
    while (k<1) {
        printf("Error! Number k must be positive.\n");
        scanf("%d", &k);
    }

    char* s = new char[max_chars_in_string];

    // reading words from input.txt
    FILE* in;
    if ((in = fopen("input.txt", "r")) != nullptr) {
        if (feof(in))
            printf("Input file is empty!\n");
        else while (!feof(in)) {
            fgets(s, max_chars_in_string, in);
            if(strlen(s)==0 && feof(in))
                printf("Input file is empty!\n");

            string line = s;
            //line.append(" "); // разделитель можно не добавлять, т.к. в конце строки присутствует символ '\n'
            if (feof(in))
                line.append(" "); // но в конце последней строке символа '\n' нет!!!
            begin=0;
            while ((begin = (int)line.find_first_of(alphabet, begin)) != string::npos) {
                end=(int)line.find_first_not_of(alphabet, begin);
                word=line.substr(begin, end-begin);
                transform(word.begin(), word.end(), word.begin(), ::tolower); // string to lowercase
                word[0]=toupper(word[0]); // first letter to uppercase
                bool find=false;
                for (int j=0; j<i; j++) {
                    if (word == array[j]) {
                        numbers[j]++;
                        j = i;
                        find = true;
                    }
                }
                if (!find) {
                    array[i] = word;
                    numbers[i]=1;
                    i++;
                }
                begin=end;
            }
        }
        fclose(in);
    }
    else {
        printf("Input file doesn't open!\n");
    }

    // sort in alphabetical order
    for (int j=0; j<i-1; j++) {
        for (int k=j+1; k<i; k++) {
            if (array[j].compare(array[k]) > 0) {
                string tmp = array[j];
                array[j] = array[k];
                array[k] = tmp;

                int a = numbers[j];
                numbers[j] = numbers[k];
                numbers[k] = a;
            }
        }
    }

    // writing words to output.txt
    if (end==-1) {
        printf("No words in input.txt!\n");
    }
    else {
        FILE *out;
        if ((out = fopen("output.txt", "w")) != nullptr) {
            for (int j = 0; j < i; j++) {
                if (numbers[j] >= k) {
                    word = array[j];
                    word.append(" ");
                    word.append(to_string(numbers[j]));
                    word.append("\n");
                    fputs(word.c_str(), out);
                }
            }
            fclose(out);
        } else {
            printf("Output file doesn't open!\n");
        }
    }

    return 0;
}
