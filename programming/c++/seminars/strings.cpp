//
//  main3.cpp
//  First
//
//  Created by Иван Шиляев on 05.09.2018.
//  Copyright © 2018 Иван Шиляев. All rights reserved.
//

#include <iostream>
#include <cmath>
#include <stdio.h>
#include <string>
using namespace std;

// 6. В строке виедлить все слова - числовые десятичные константы языка С++

int main() {
    const int max=300;
    char chrstr[max]; //*ptr;
    char **ptr1;//=&ptr;
    string str, seps=" ,;:", word;
    int begin(0), end;
    cout << "enter string" << endl;
    getline(cin, str);
    str+=seps[0];
    while ((begin=(int)str.find_first_not_of(seps, begin)) != string::npos) {
        end=(int)str.find_first_of(seps, begin);
        word=str.substr(begin, end-begin);
        strcpy(chrstr, word.c_str());
        strtod(chrstr, ptr1);
        if (strlen(*ptr1)==0 || (strlen(*ptr1)==1 && (*ptr1[0]=='f' || *ptr1[0]=='F' || *ptr1[0]=='l' || *ptr1[0]=='L' || *ptr1[0]=='u' || *ptr1[0]=='U')))
            cout << chrstr << endl;
        begin=end;
    }
    return 0;
}

// 5. Найти слово с максимальным количеством гласных букв латирнского алфавита (последнее из них)

int main() {
    setlocale(LC_ALL, ".1251");
    string s, word, result, vowels="aeiouyAEIOUY", seps=" .,?!:;\\/-'\"";
    int begin(0), end, b(0), kol(0), max(0);
    getline(cin, s);
    if (s.empty()) {
        printf("Строка пустая!\n");
        return 1;
    }
    s+=" ";
    while ((begin=(int)s.find_first_not_of(seps, begin)) != string::npos) {
        end = (int) s.find_first_of(seps, begin);
        word = s.substr(begin, end - begin);
        kol=0;
        b=0;
        while ((b=(int)word.find_first_of(vowels, b)) != string::npos) {
            kol++;
            b++;
        }
        if (kol>=max) {
            result.clear();
            result.append(word);
            max=kol;
        }
        begin=end;
    }
    if (result.empty()) {
        printf("Нет слов с гласными буквами в строке!\n");
    }
    printf("%s\n", result.c_str());

    return 0;
}


// 4. Расположить слова по возрастанию их длин

int main() {
    setlocale(LC_ALL, ".1251");
    string s, array[100], word, tmp, seps=" .,?!:;\\/-'\"";
    int begin(0), end, n(0), k;
    getline(cin, s);
    if (s.empty()) {
        printf("Строка пустая!\n");
        return 1;
    }
    s+=" ";
    while ((begin=(int)s.find_first_not_of(seps, begin)) != string::npos) {
        end=(int)s.find_first_of(seps, begin);
        word=s.substr(begin, end-begin);
        array[n]=word;
        n++;
        begin=end;

    }
    if (array[0].empty()) {
        printf("Нет слов в строке!\n");
        return 1;
    }
    for (int i=0; i<n-1; i++)
    for (int j=i+1; j<n; j++)
    if (array[i].size()>array[j].size()) {
        tmp=array[i];
        array[i]=array[j];
        array[j]=tmp;
    }
    for (int i=0; i<n; i++)
    printf("%s\n", array[i].c_str());

    return 0;
}


// 3. Расположить слова по алфавиту

int main() {
    setlocale(LC_ALL, ".1251");
    string s, array[100], word, tmp, seps=" .,?!:;\\/-'\"";
    int begin(0), end, n(0), k;
    getline(cin, s);
    if (s.empty()) {
        printf("Строка пустая!\n");
        return 1;
    }
    s+=" ";
    while ((begin=(int)s.find_first_not_of(seps, begin)) != string::npos) {
        end=(int)s.find_first_of(seps, begin);
        word=s.substr(begin, end-begin);
        array[n]=word;
        n++;
        begin=end;

    }
    if (array[0].empty()) {
        printf("Нет слов в строке!\n");
        return 1;
    }
    for (int i=0; i<n-1; i++)
    for (int j=i+1; j<n; j++)
    if (array[i].compare(array[j])>0) {
        tmp=array[i];
        array[i]=array[j];
        array[j]=tmp;
    }
    for (int i=0; i<n; i++)
    printf("%s\n", array[i].c_str());

    return 0;
}


// 2. Функция для char и string, возврщающие последнее слово в строке

string last_word_string(string s) {
    int end = (int)s.find_last_not_of(" ");
    int begin = (int)s.find_last_of(" ", end)+1;
    return s.substr(begin, end-begin+1);
}

// доделать!!!

char* last_word_char(char *s) {
    char *token = strtok(s, " ");
    while(token) {
        cout << token << endl;
    }
    return token;
}

int main() {
    char s[100];
    cin.get(s, 100);
    cout << last_word_char(s) << endl;
}


// 1. Поменять местами слова max и min длины

int main() {
    string s, min, max, sep, word;
    int begin(0), end, i_max(0), i_min;
    cout << "enter string:" << endl;
    getline(cin, s);
    cout << "enter seps:" << endl;
    getline(cin, sep);
    s=s+sep[0];
    min = s;
    while ((begin=(int)s.find_first_not_of(sep, begin)) != -1) {
        end = (int)s.find_first_of(sep, begin);
        word = s.substr(begin, end-begin);
        if (word.length()<min.length()) {
            min=word;
            i_min=begin;
        }
        else if (word.length()>max.length()) {
            max=word;
            i_max=begin;
        }
        begin=end;
    }
    if (i_min>i_max) {
        s.replace(i_min, min.length(), max);
        s.replace(i_max, max.length(), min);
    }
    if (i_min<i_max) {
        s.replace(i_max, max.length(), min);
        s.replace(i_min, min.length(), max);
    }

    cout << s << endl;

    return 0;
}
