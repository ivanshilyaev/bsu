//
//  main.cpp
//  laba_6
//
//  Created by Иван Шиляев on 10/16/18.
//  Copyright © 2018 Иван Шиляев. All rights reserved.
//

#include <iostream>
#include <cmath>
#include <stdio.h>
#include <iomanip>
using namespace std;

int min_value(int a, int b) {
    return a<b ? a : b;
}

int max_value(int a, int b) {
    return a>b ? a : b;
}

int main() {
    setlocale(LC_ALL, ".1251");
    const int max=300;
    char string1[max], string2[max], result[2*max]="";
    // вводим строки
    cout << "Введите две строки, каждая с новой строчки. Слова в строках могут быть разделены одним или несколькими пробелами." << endl;
    cin.get(string1, max);
    cin.get();
    cin.get(string2, max);
    // проверка 1
    if (strlen(string1) == 0) {
        cout << "Первая строка пустая!" << endl;
        return 0;
    }
    if (strlen(string2) == 0) {
        cout << "Вторая строка пустая!" << endl;
        return 0;
    }
    // разделяем строки на слова
    char seps[] = " ";
    char *h1[max], *h2[max];
    char *token;
    token = strtok(string1, seps);
    int i=0;
    while (token) {
        h1[i] = token;
        i++;
        token = strtok(NULL, seps);
    }
    token = strtok(string2, seps);
    int j=0;
    while (token) {
        h2[j] = token;
        j++;
        token = strtok(NULL, seps);
    }
    // проверка 2
    if (i == 0) {
        cout << "Нет слов в первой строке!" << endl;
        return 0;
    }
    if (j == 0) {
        cout << "Нет слов во второй строке!" << endl;
        return 0;
    }
    // в новую строку добавляем слова по очереди
    int n = min_value(i,j);
    int k;
    for (k=0; k<n; k++) {
        strcat(result, h1[k]);
        strcat(result, " ");
        strcat(result, h2[k]);
        strcat(result, " ");
    }
    // добавляем отсавшиеся слова
    while (k<i) {
        strcat(result, h1[k]);
        strcat(result, " ");
        k++;
    }
    while (k<j) {
        strcat(result, h2[k]);
        strcat(result, " ");
        k++;
    }
    cout << "В новой строке слова из первых двух строк чередуются:" << endl;
    cout << result << endl;
    return 0;
}
