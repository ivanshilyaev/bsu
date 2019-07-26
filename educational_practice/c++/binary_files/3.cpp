#include <iostream>
#include <stdio.h>
#include <fstream>
#include <string>
#include <algorithm>
#include <exception>
using namespace std;

// 2.

// Все файлы должны быть отсортированы по возрастанию номеров зачёток

// Проверки:
// 1. Не может быть двух различных студентов с одинаковыми номерами зачеток
// 2. Номер зачётки/группы не может быть неположительным
// Во всех случаях на экран выводится сообщение "Error: invalid data"

struct student {
    long num;
    char name[90];
    int group;
};

// создание бинарного файлы на базе текстового
void txtToBin(string txtFileName, string binFileName) {
    ifstream fin(txtFileName, ios_base::in);
    FILE* out = fopen(binFileName.c_str(), "wb");
    if (!fin.is_open()) {
        throw "Error: input txt-file doesn't open";
    }

    string line;
    getline(fin, line);
    if (fin.eof()) {
        throw "Error: input txt-file is empty";
    }
    fin.seekg(0);

    // Массивы для проверки уникальности номера зачётки
    long array[100000];
    string surnames[100000];
    int n(0);

    while (!fin.eof()) {
        student one;
        fin >> one.num;
        if (one.num<=0)
            throw "Error: invalid data";

        // ФИО
        char surname[30], firstname[30], middlename[30];
        fin >> surname;
        fin >> firstname;
        fin >> middlename;
        strcpy(one.name, surname);
        strcat(one.name, " ");
        strcat(one.name, firstname);
        strcat(one.name, " ");
        strcat(one.name, middlename);

        fin >> one.group;
        if (one.group<=0)
            throw "Error: invalid data";

        array[n]=one.num;
        surnames[n]=surname; n++;
        for (int i=0; i<n-1; i++)
            if ((array[i]==one.num) && surnames[i]!=surname)
                throw "Error: invalid data";
        fwrite(&one, sizeof(student), 1, out);
    }
    fin.close();
    fclose(out);
}

// вывод бинарного файла на экран
void printList(string fileName) {
    FILE* in = fopen(fileName.c_str(), "rb");

    // проверка на пустоту файла
    student two;
    fread(&two, sizeof(student), 1, in);
    if ((strlen(two.name)==0) && feof(in)) {
        throw "Error: bin-file is empty";
    }
    fclose(in);
    in=freopen(fileName.c_str(), "rb", in);

    fread(&two, sizeof(student), 1, in);
    do {
        cout << two.num << " ";
        cout << two.name << " ";
        cout << two.group << " ";
        cout << endl;
        fread(&two, sizeof(student), 1, in);
    }
    while (!feof(in));
    cout<<endl;
    fclose(in);
}

// объединение
void merge(string file1, string file2, string result) {
    FILE* in = fopen(file1.c_str(), "rb");
    FILE* in2 = fopen(file2.c_str(), "rb");
    FILE* out = fopen(result.c_str(), "wb");
    if (in==NULL || in2==NULL) {
        throw "Error: bin file doesn't open";
    }

    // проверка на пустоту файлов
    student one;
    student two;
    fread(&one, sizeof(student), 1, in);
    fread(&two, sizeof(student), 1, in2);
    if (strlen(one.name)==0 || strlen(two.name)==0)
        throw "Error: bin-file is empty";
    fclose(in); fclose(in2);
    in = fopen(file1.c_str(), "rb");
    in2 = fopen(file2.c_str(), "rb");
    fread(&one, sizeof(student), 1, in);
    fread(&two, sizeof(student), 1, in2);

    do {
        if (one.num < two.num)
            do {
                fwrite(&one, sizeof(student), 1, out);
                fread(&one, sizeof(student), 1, in);
            } while (!feof(in) && one.num < two.num);
        else if (one.num > two.num)
            do {
                fwrite(&two, sizeof(student), 1, out);
                fread(&two, sizeof(student), 1, in2);
            } while (!feof(in2) && (one.num > two.num));
        else if (one.num == two.num)
            do {
                fwrite(&one, sizeof(student), 1, out);
                fread(&one, sizeof(student), 1, in);
                fread(&two, sizeof(student), 1, in2);
            } while (!feof(in) && !feof(in2) && one.num == two.num);
    } while (!feof(in) && !feof(in2));

    if (!feof(in)) {
        do {
            fwrite(&one, sizeof(student), 1, out);
            fread(&one, sizeof(student), 1, in);
        } while (!feof(in));
    } else if (!feof(in2)) {
        do {
            fwrite(&two, sizeof(student), 1, out);
            fread(&two, sizeof(student), 1, in2);
        } while (!feof(in2));
    }

    fclose(in);
    fclose(in2);
    fclose(out);
}

// пересечение
void intersection(string file1, string file2, string result) {
    FILE* in = fopen(file1.c_str(), "rb");
    FILE* in2 = fopen(file2.c_str(), "rb");
    FILE* out = fopen(result.c_str(), "wb");
    if (in==NULL || in2==NULL) {
        throw "Error: bin file doesn't open";
    }

    // проверка на пустоту файлов
    student one;
    student two;
    fread(&one, sizeof(student), 1, in);
    fread(&two, sizeof(student), 1, in2);
    if (strlen(one.name)==0 || strlen(two.name)==0)
        throw "Error: bin-file is empty";
    fclose(in); fclose(in2);
    in = fopen(file1.c_str(), "rb");
    in2 = fopen(file2.c_str(), "rb");
    fread(&one, sizeof(student), 1, in);
    fread(&two, sizeof(student), 1, in2);

    int tek=0;
    do {
        if (one.num == two.num)
            do {
                fwrite(&one, sizeof(student), 1, out);
                fread(&one, sizeof(student), 1, in);
                fread(&two, sizeof(student), 1, in2);
                tek++;
            } while (!feof(in) && !feof(in2) && one.num == two.num);
        else if (one.num<two.num)
            fread(&one, sizeof(student), 1, in);
        else if (one.num>two.num)
            fread(&two, sizeof(student), 1, in2);
    } while (!feof(in) && !feof(in2));

    fclose(in);
    fclose(in2);
    fclose(out);

    if (tek==0)
        throw "Error: no records common for both files";
}

// разность
void complement(string file1, string file2, string result) {
    FILE* in = fopen(file1.c_str(), "rb");
    FILE* in2 = fopen(file2.c_str(), "rb");
    FILE* out = fopen(result.c_str(), "wb");
    if (in==NULL || in2==NULL) {
        throw "Error: bin file doesn't open";
    }

    // проверка на пустоту файлов
    student one;
    student two;
    fread(&one, sizeof(student), 1, in);
    fread(&two, sizeof(student), 1, in2);
    if (strlen(one.name)==0 || strlen(two.name)==0)
        throw "Error: bin-file is empty";
    fclose(in); fclose(in2);
    in = fopen(file1.c_str(), "rb");
    in2 = fopen(file2.c_str(), "rb");
    fread(&one, sizeof(student), 1, in);
    fread(&two, sizeof(student), 1, in2);

    int tek=0;
    do {
        while (!(feof(in2)) && one.num!=two.num) {
            fread(&two, sizeof(student), 1, in2);
        }
        if (feof(in2)) {
            fwrite(&one, sizeof(student), 1, out);
            tek++;
        }
        fread(&one, sizeof(student), 1, in);
        fclose(in2);
        in2=freopen(file2.c_str(), "rb", in2);
        fread(&two, sizeof(student), 1, in2);
    } while (!feof(in));

    fclose(in);
    fclose(in2);
    fclose(out);

    if (tek==0)
        throw "Error: no records which are only in first file";
}

int main() {
    setlocale(LC_ALL, ".1251");

    try {
        // создание бинарных файлов на базе текстовых
        cout<<"Данная программа создаёт два бинарных файла на базе текстовых"<<endl;
        cout<<"и выполняет над ними соответсвующие алгебраические операции"<<endl;
        cout<<"(объединение, пересечение или разность)"<<endl;
        cout<<endl;

        string txtInput1, txtInput2;
        cout<<"Введите имя первого тексового файла:"<<endl;
        cin>>txtInput1;
        cout<<"Введите имя второго тексового файла:"<<endl;
        cin>>txtInput2;
        cout<<endl;

        string binInput1 = "input1.bin";
        txtToBin(txtInput1, binInput1);

        string binInput2 = "input2.bin";
        txtToBin(txtInput2, binInput2);

        // вывод бинарных файлов на экран
        cout << "Исходные данные:" << endl;
        cout << "Первый файл:" << endl;
        printList(binInput1);
        cout << "Второй файл:" << endl;
        printList(binInput2);

        // 2. Объединение, пересечение или разность

        string binOutput1 = "output1.bin";

        int c;
        cout<<"Какую операцию вы хотите выполнить над файлами?"<<endl;
        cout<<"1 - объединение"<<endl;
        cout<<"2 - пересечение"<<endl;
        cout<<"3 - разность"<<endl;
        cin>>c;
        while (c<1 || c>3) {
            cout<<"Ошибка. Повторите попытку"<<endl;
            cin>>c;
        }
        switch (c) {
            case 1:
                merge(binInput1, binInput2, binOutput1);
                cout << "Объединение:" << endl;
                printList(binOutput1);
                break;
            case 2:
                intersection(binInput1, binInput2, binOutput1);
                cout << "Пересечение:" << endl;
                printList(binOutput1);
                break;
            case 3:
                complement(binInput1, binInput2, binOutput1);
                cout << "Разность:" << endl;
                printList(binOutput1);
                break;
            default:
                break;
        }
        cout<<"Имя результирующего бинарного файла:"<<endl;
        cout<<binOutput1<<endl;
    } catch (const char* e) {
    }

    return 0;
}
