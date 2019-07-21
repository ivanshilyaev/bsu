#include <iostream>
#include <stdio.h>
#include <fstream>
#include <string>
using namespace std;

// 1. Скобки и смайлики

typedef char T;

struct stack {
    int top;
    int size;
    T* array;
};

void init(int len, stack &s) {
    s.size=len;
    s.array = new T[s.size];
    s.top=0;
}

void push(T data, stack &s) {
    if (s.top==s.size)
        throw "Error: stack is full";
    s.array[s.top++]=data;
}

T pop(stack &s) {
    if (s.top==0)
        throw "Error: stack is empty";
    T data=s.array[--s.top];
    return data;
}

bool empty(stack &s) {
    return s.top==0;
}

string removeAllSmiles(string line) {
    int index=0;
    while ((index=(int)line.find(":)", index)) != string::npos) {
        line.erase(index, 2);
    }
    index=0;
    while ((index=(int)line.find(":]", index)) != string::npos) {
        line.erase(index, 2);
    }
    index=0;
    while ((index=(int)line.find(":}", index)) != string::npos) {
        line.erase(index, 2);
    }
    index=0;
    while ((index=(int)line.find(":(", index)) != string::npos) {
        line.erase(index, 2);
    }
    index=0;
    while ((index=(int)line.find(":[", index)) != string::npos) {
        line.erase(index, 2);
    }
    index=0;
    while ((index=(int)line.find(":{", index)) != string::npos) {
        line.erase(index, 2);
    }
    return line;
}

char reverse(char ch) {
    if (ch=='(')
        return ')';
    else if (ch=='[')
        return ']';
    else if (ch=='{')
        return '}';
}

int main() {
    ifstream fin("smiles.in", ios_base::in);
    ofstream fout("smiles.out", ios_base::out);
    string number;
    getline(fin, number);
    int n=stoi(number);
    for (int j=0; j<n; j++) {
        stack s;
        string line;
        getline(fin, line);
        line=removeAllSmiles(line);
        init((int) line.length(), s);
        try {
            for (int i = 0; i < line.length(); i++) {
                if (line[i] == '(')
                    push(line[i], s);
                else if (line[i] == ')') {
                    if (line[i]!=reverse(pop(s)))
                        throw "2";
                }
                else if (line[i] == '[')
                    push(line[i], s);
                else if (line[i] == ']') {
                    if (line[i]!=reverse(pop(s)))
                        throw "2";
                }
                else if (line[i] == '{')
                    push(line[i], s);
                else if (line[i] == '}') {
                    if (line[i]!=reverse(pop(s)))
                        throw "2";
                }
            }
            if (!empty(s))
                fout<<3<<endl;
            else
                fout<<0<<endl;
        } catch (const char *c) {
            if (strcmp(c, "Error: stack is empty")==0)
                fout<<1<<endl;
            else if (strcmp(c, "2")==0)
                fout<<2<<endl;
        }
    }
    fin.close();
    fout.close();
    return 0;
}
