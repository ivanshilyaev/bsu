#include <iostream>
#include <stdio.h>
#include <fstream>
#include <string>
using namespace std;

// 0.

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

    while (!fin.eof()) {
        student one;
        fin >> one.num;

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
        fwrite(&one, sizeof(student), 1, out);
    }
    fin.close();
    fclose(out);
}

// вывод бинарного файла на экран
void printList(string fileName) {
    FILE*  in = fopen(fileName.c_str(), "rb");
    if (in==NULL) {
        throw "Error: bin file doesn't open";
    }

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

int main() {
    setlocale(LC_ALL, ".1251");

    try {
        // создание бинарного файлы на базе текстового
        string txtInput="input1.txt";
        string binInput="input1.bin";
        txtToBin(txtInput, binInput);

        // вывод бинарного файла на экран
        cout<<"Исходные данные:"<<endl;
        printList(binInput);
    } catch (const char* e) {
        cout<<e<<endl;
    }

    return 0;
}
