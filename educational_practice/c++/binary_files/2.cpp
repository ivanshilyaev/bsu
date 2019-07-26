#include <iostream>
#include <stdio.h>
#include <fstream>
#include <string>
using namespace std;

// 1.

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

// сортировка по номерам зачёток
int sortByZach(string input, string output) {
    long array[100000];
    int n(0);

    FILE* in = fopen(input.c_str(), "rb");
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
    in=freopen(input.c_str(), "rb", in);

    fread(&two, sizeof(student), 1, in);
    do {
        array[n]=two.num;
        n++;
        fread(&two, sizeof(student), 1, in);
    }
    while (!feof(in));
    fclose(in);

    // сортировка зачёток
    for (int i=0; i<n-1; i++)
        for (int j=i+1; j<n; j++)
            if (array[i]>array[j]) {
                long tmp=array[i];
                array[i]=array[j];
                array[j]=tmp;
            }

    // запись в бинарный файл
    in = freopen(input.c_str(), "rb", in);
    FILE* out = fopen(output.c_str(), "wb");
    two;
    for (int i=0; i<n; i++) {
        fread(&two, sizeof(student), 1, in);
        if (two.num!=array[i])
            do {
                fread(&two, sizeof(student), 1, in);
            } while (!feof(in) && two.num!=array[i]);
        fwrite(&two, sizeof(student), 1, out);
        in = freopen(input.c_str(), "rb", in);
    }
    fclose(in);
    fclose(out);

    // renaming
    rename(output.c_str(), input.c_str());

    return 0;
}

// сортировка по группам и фамилиям
int sortByGroup(string input, string output) {
    int array[100000];
    string surnames[100000];
    int n(0);

    // проверка на пустоту файла
    FILE* in = fopen(input.c_str(), "rb");
    student two;
    fread(&two, sizeof(student), 1, in);
    if ((strlen(two.name)==0) && feof(in)) {
        throw "Error: bin-file is empty";
    }
    fclose(in);
    in=freopen(input.c_str(), "rb", in);

    fread(&two, sizeof(student), 1, in);
    do {
        array[n]=two.group;

        // выделение фамилии
        string line(two.name);
        int end=(int)line.find_first_of(' ', 0);
        surnames[n]=line.substr(0, end);

        n++;
        fread(&two, sizeof(student), 1, in);
    } while(!feof(in));
    fclose(in);

    // сортировка групп и фамилий
    for (int i=0; i<n-1; i++)
        for (int j=i+1; j<n; j++) {
            if ((array[i]>array[j]) ||
                ((array[i]==array[j]) && (surnames[i].compare(surnames[j])>0))) {
                int tmp=array[i];
                array[i]=array[j];
                array[j]=tmp;

                string line=surnames[i];
                surnames[i]=surnames[j];
                surnames[j]=line;
            }
        }

    // запись в бинарный файл
    in=freopen(input.c_str(), "rb", in);
    FILE* out=fopen(output.c_str(), "wb");
    two;
    for (int i=0; i<n; i++) {
        fread(&two, sizeof(student), 1, in);

        // выделение фамилии
        string line(two.name);
        int end=(int)line.find_first_of(' ', 0);
        string tekSurname=line.substr(0, end);

        if (array[i]!=two.group || surnames[i]!=tekSurname)
            do {
                fread(&two, sizeof(student), 1, in);

                // выделение фамилии
                string line(two.name);
                int end=(int)line.find_first_of(' ', 0);
                tekSurname=line.substr(0, end);
                //cout<<tekSurname<<endl;

            } while (!feof(in) && (array[i]!=two.group || surnames[i]!=tekSurname));
        fwrite(&two, sizeof(student), 1, out);
        in=freopen(input.c_str(), "rb", in);
    }
    fclose(in);
    fclose(out);

    // renaming
    rename(output.c_str(), input.c_str());

    return 0;
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

        // 1. a)
        // сортировка по номерам зачёток


        // 1. b)
        // сортировка по группам и фамилиям

        string binOutput="output1.bin";

        int c;
        cout<<"Как вы хотите отсортировать файл?"<<endl;
        cout<<"1 - по номерам зачеток"<<endl;
        cout<<"2 - по номерам групп, а внутри групп по фамилиям студентов"<<endl;
        cin>>c;
        while (c<1 || c>2) {
            cout<<"Ошибка. Повторите попытку"<<endl;
            cin>>c;
        }
        switch (c) {
            case 1:
                cout<<"Сортировка по номерам зачёток:"<<endl;
                sortByZach(binInput, binOutput);
                printList(binInput);
                break;
            case 2:
                cout << "Сортировка по группам и фамилиям:" << endl;
                sortByGroup(binInput, binOutput);
                printList(binInput);
                break;
            default:
                break;
        }
    } catch (const char* e) {
        cout<<e<<endl;
    }

    return 0;
}
