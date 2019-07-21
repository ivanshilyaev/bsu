//
//  main.cpp
//  empty
//
//  Created by Иван Шиляев on 19.09.2018.
//  Copyright © 2018 Иван Шиляев. All rights reserved.


#include <iostream>
#include <cmath>
#include <stdio.h>
#include <iomanip>
#include <string.h>
using namespace std;

// лаба 6, задание 2
// В строке char к числам длины меньше 5 дописать ведущие нули и зарвернуть все слова

int main() {
    setlocale(LC_ALL, ".1251");
    const int max=100;
    char s[max];
    cin.get(s, max);
    char a[strlen(s)+1];
    strcpy(a, s);

    if (strlen(s) == 0) {
        cout << "Строка пустая!" << endl;
        return 0;
    }

    char* numbers = new char[10];
    strcat(numbers,"0123456789");
    char* alphabet = new char[52];
    strcat(alphabet, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

    char *h[max]; // массив слов
    int index[max]; //массив индексов, с которых начинаются слова в исходной строке

    char seps[] = " .,?!\'\":;-";
    char *token;
    int i=0;
    token = strtok(s, seps);
    while (token) {
        index[i]=(int) (token-s);
        h[i]=token;
        i++;
        token=strtok(NULL, seps);
    }

    if (i == 0) {
        cout << "Нет слов в строке!" << endl;
        return 0;
    }


    for (int j=i-1; j>=0; j--) { // проходимся по словам в обратном порядке
        // 1 -  дописываем ведущие нули
        if (strlen(h[j])<5) {
            char* word = h[j]; // текущее слово
            int kol=0; // количество цифр в числе
            for (int k=0; k<strlen(h[j]); k++) {
                if (strchr(numbers, word[k])) // если очередной символ строки - цифра
                    kol++;
            }
            if (kol==strlen(word)) {
                char* zeros = new char[5];
                for (int k=0; k<5-strlen(h[j]); k++)
                    strcat(zeros, "0");
                // дописываем ведущие нули к числу в исходную строку
                char tmp[max];
                strcpy(tmp, a+index[j]);
                a[index[j]]='\0';
                strcat(a, zeros);
                strcat(a, tmp);

            }
        }

        // 2 - разворачивем слова
        char* word = h[j]; // текущее слово
        int kol=0; // количество букв латинского алфавита в слове
        for (int k=0; k<strlen(h[j]); k++) {
            if (strchr(alphabet, word[k])) // если очередной символ строки - цифра
                kol++;
        }
        if (kol==strlen(word)) {
            // переворачивем слово
            for (int j=0, k=strlen(word)-1; j<k; j++,k--)
                swap(word[j], word[k]);
            // меняем слово в исходной строке
            char tmp[max];
            strcpy(tmp, a+index[j]+strlen(h[j]));
            a[index[j]]='\0';
            strcat(a, word);
            strcat(a, tmp);

        }
    }
    cout << a << endl;

    return 0;
}

// 2. Поменять местами слова max и min длины

void delstr(char *A, char *B) {
    int n=(int)strlen(B), s=0;
    while(*(A+n+s)!=NULL) {
        *(A+s)=*(A+n+s);
        s++;
    }
}

void insstr(char *s, const char *b) {
    int B=(int)strlen(b);
    int c=(int)strlen(s);
    for (int i=c-1; i>=0; i--)
        *(s+i+B)=*(s+i);
    for (int i=0; i<B; i++)
        *(s+i)=*(b+i);
}

int main() {
    const int max_kol=100;
    char s[max_kol];
    cin.get(s, max_kol);
    if (strlen(s) == 0) {
        cout << "The line is empty!" << endl;
        return 0;
    }
    char a[strlen(s)+1];
    strcpy(a, s);
    char seps[] = " .,?!\'\":;-";
    
    char *token, *min, *max;
    token=strtok(s, seps);
    min=token; max=token;
    while (token) {
        if (strlen(token)>strlen(max))
            max=token;
        if (strlen(token)<strlen(min))
            min=token;
        token=strtok(NULL, seps);
    }
    
    char *Max = new char[strlen(max)+1];
    strcpy(Max, max);
    char *Min = new char[strlen(min)+1];
    strcpy(Min, min);
    strcpy(s, a);
    
    // max - позиция максимального слова в исходной строке
    // Max - максимальное слово
    
    if (max>min) {
        delstr(max, Max);
        insstr(max, Min);
        delstr(min, Min);
        insstr(min, Max);
    }
    else if (max<min) {
        delstr(min, Min);
        insstr(min, Max);
        delstr(max, Max);
        insstr(max, Min);
    }
    
    for (int i=0; i<strlen(a); i++)
        cout << s[i];
    cout << endl;
    
    return 0;
}

// Второй способ (мой)

//int main() {
//    setlocale(LC_ALL, ".1251");
//    const int max=100;
//    char s[max];
//    cin.get(s, max);
//    char a[strlen(s)+1];
//    strcpy(a, s);
//
//    char *h[max]; // массив слов
//    int index[max]; //массив индексов, с которых начинаются слова в исходной строке
//
//    char seps[] = " .,?!\'\":;-";
//    char *token;
//    int i=0;
//    token = strtok(s, seps);
//    while (token) {
//        index[i]=(int) (token-s);
//        h[i]=token;
//        i++;
//        token=strtok(NULL, seps);
//    }
//
//    // находим слова min и max длины  и запоминаем их позиции в исходной строке
//    token=h[0];
//    char *maxtoken = h[0];
//    int min_index=index[0];
//    int max_index=index[0];
//    for (int j=0; j<i; j++) {
//        if (strlen(h[j])<strlen(token)) {
//            token=h[j];
//            min_index=index[j];
//        }
//        if (strlen(h[j])>strlen(maxtoken)) {
//            maxtoken=h[j];
//            max_index=index[j];
//        }
//    }
//
//    // меняем слова местами: сначала правое слово заменяем на левое! затем наоборот. иначе сдвинутся индексы
//    char tmp[max]; // буфер
//    if (min_index>max_index) {
//        strcpy(tmp, a+min_index+strlen(token));
//        a[min_index]='\0';
//        strcat(a, maxtoken);
//        strcat(a, tmp);
//
//        strcpy(tmp, a+max_index+strlen(maxtoken));
//        a[max_index]='\0';
//        strcat(a, token);
//        strcat(a, tmp);
//    }
//    else {
//        strcpy(tmp, a+max_index+strlen(maxtoken));
//        a[max_index]='\0';
//        strcat(a, token);
//        strcat(a, tmp);
//
//        strcpy(tmp, a+min_index+strlen(token));
//        a[min_index]='\0';
//        strcat(a, maxtoken);
//        strcat(a, tmp);
//    }
//
//    cout << a << endl;
//
//    return 0;
//}


// не доделано!!!!



//void delstr(char *A, const char *B) { //функция удаления подстроки
//    char *s, *s1;
//    s1=A;
//    int n=(int)strlen(B); //Длина подстроки
//    while((s=(char*)strstr(s1,B))!=NULL) { //Пока в строке есть заданные подстроки:
//        s1=s; //сохраняем адрес подстроки
//        while(*(s+n)!=NULL) { //Сдвигаем всю строку влево
//            *s=*(s+n); //стирая найденную подстроку
//            s++;
//        }
//        *s=NULL;   //Ограничиваем ее
//    }
//}
//
//// второй способ
//
//int main() {
//    setlocale(LC_ALL, ".1251");
//    int max=100;
//    char *s=new char(max+1);
//    cout << "Введите строку:" << endl;
//    cin.get(s, max);
//    cin.get();
//    char symbol[1];
//    cout << "Введите символ:" << endl;
//    cin >> symbol;
//    char substring[max];
//    cout << "Введите подстроку:" << endl;
//    cin >> substring;
//
//    delstr(s, substring);
//    cout << s << endl;
//
//    return 0;
//}






// 1. В строке заменить заданный символ на заданную подстроку

int main() {
    setlocale(LC_ALL, ".1251");
    int max=100;
    char *s=new char(max+1);
    cout << "Введите строку:" << endl;
    cin.get(s, max);
    cin.get();
    char symbol;
    cout << "Введите символ:" << endl;
    cin >> symbol;
    char substring[max];
    cout << "Введите подстроку:" << endl;
    cin >> substring;

    char tmp[]="";

    int i=(int)strlen(s)-1;

    while (i>=0) {
        if (s[i]==symbol) {
            strcpy(tmp, s+i+1);
            s[i]='\0';
            strcat(s, substring);
            strcat(s, tmp);
        }
        i--;
    }

    cout << s << endl;

    return 0;
}
