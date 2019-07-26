#include <iostream>
#include <stdio.h>
#include <fstream>
#include <string>
using namespace std;

struct first {
    int zach;
    char surname[20];
    char name[20];
    char middlename[20];
};

struct second {
    int group;
    int zach;
    int MA;
    int GA;
    int PROG;
};

//struct test {
//    char surname[20];
//    int group;
//    int zach;
//    int MA;
//    int GA;
//    int PROG;
//};

// a
int firstTxtToBin(string txtFileName, string binFileName) {
    ifstream fin(txtFileName, ios_base::in);
    FILE* out = fopen(binFileName.c_str(), "wb");
    if (!fin.is_open() || (out==NULL)) {
        cout<<"error"<<endl;
        return -1; // error
    }
    while (!fin.eof()) {
        first one;
        fin >> one.zach;
        fin >> one.surname;
        fin >> one.name;
        fin >> one.middlename;
        fwrite(&one, sizeof(first), 1, out);
    }
    fin.close();
    fclose(out);
    return 0; // success
}

int secondTxtToBin(string txtFileName, string binFileName) {
    ifstream fin(txtFileName, ios_base::in);
    FILE* out = fopen(binFileName.c_str(), "wb");
    if (!fin.is_open() || (out==NULL)) {
        cout<<"error"<<endl;
        return -1; // error
    }
    while (!fin.eof()) {
        second one;
        fin >> one.group;
        fin >> one.zach;
        fin >> one.MA;
        fin >> one.GA;
        fin>>one.PROG;
        fwrite(&one, sizeof(second), 1, out);
    }
    fin.close();
    fclose(out);
    return 0; // success
}

// b
int append(string output1, string output2, string result) {
    FILE* fin1;
    FILE* fin2;
    FILE* fout;
    if (((fin1 = fopen(output1.c_str(), "rb")) == NULL) || ((fin2 = fopen(output2.c_str(), "rb")) == NULL)
                || ((fout = fopen(result.c_str(), "wb")) == NULL)) {
        cout<<"error"<<endl;
        return -1; // error;
    }

    struct first base1;
    struct second base2;
    fread(&base1, sizeof(first), 1, fin1);
    fread(&base2, sizeof(second), 1, fin2);
    do {
        while (base1.zach!=base2.zach) {
            fread(&base2, sizeof(second), 1, fin2);
        }
        fin2 = freopen(output2.c_str(), "rb", fin2);

        fwrite(&base1.surname, sizeof(base1.surname), 1, fout);
        fwrite(&base2.group, sizeof(int), 1, fout);
        fwrite(&base2.zach, sizeof(int), 1, fout);
        fwrite(&base2.MA, sizeof(int), 1, fout);
        fwrite(&base2.GA, sizeof(int), 1, fout);
        fwrite(&base2.PROG, sizeof(int), 1, fout);

        fread(&base1, sizeof(first), 1, fin1);
        fread(&base2, sizeof(second), 1, fin2);
    } while (!feof(fin1));

    fclose(fin1);
    fclose(fin2);
    fclose(fout);
    return 0; // success
}

int main() {
    // a
    // first file to binary
    string input1="input1.txt";
    string output1="input1.bin";
    firstTxtToBin(input1, output1);

    // second file to binary
    string input2="input2.txt";
    string output2="input2.bin";
    secondTxtToBin(input2, output2);


    //b
    string output3="output1.bin";
    append(output1, output2, output3);

    // test b
//    FILE* in = fopen("output1.bin", "rb");
//    struct test one;
//    fread(&one, sizeof(test), 1, in);
//    do {
//        cout << one.surname << endl;
//        cout << one.group << endl;
//        cout << one.zach << endl;
//        cout << one.MA << endl;
//        cout << one.GA << endl;
//        cout << one.PROG << endl;
//        cout << endl;
//        fread(&one, sizeof(test), 1, in);
//    } while (!feof(in));



    return 0;
}


// вывод структур

//        cout << base1.zach << endl;
//        cout << base1.surname << endl;
//        cout << base1.name << endl;
//        cout << base1.middlename << endl;
//        cout << endl;

//        cout << base2.group << endl;
//        cout << base2.zach << endl;
//        cout << base2.MA << endl;
//        cout << base2.GA << endl;
//        cout << base2.PROG << endl;
//        cout << endl;
