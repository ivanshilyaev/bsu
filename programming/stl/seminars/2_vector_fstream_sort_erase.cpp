#include <iostream>
#include <string>
#include <iterator>
#include <vector>
#include <fstream>
using namespace std;

// Считать строки string из файла в vector. Отсортировать их по алфавиту. Вывести все слова на первую букву, затем удалить их

void print(vector<string> &v) {
    for (auto a:v)
        cout<<a<<endl;
}

int main() {
    ifstream fin("input.txt");
    if (!fin) {
        cout<<"File doesn'texit"<<endl;
        return 0;
    }
    string line;
    vector <string> v;
    while (fin>>line)
        v.push_back(line);
    if (v.size()==0) {
        cout<<"File is empty"<<endl;
        return 0;
    }
    sort(v.begin(), v.end());
    print(v); cout<<endl;
    typedef vector<string>::iterator iter;
    for (iter i=v.begin(); i!=v.end(); ++i) {
        if ((*i)[0]=='A')
            cout<<*i<<" ";
        else {
            cout<<endl;
            break;
        }
    }
    cout<<endl;
    for (iter i=v.begin(); i!=v.end(); ) {
        if ((*i)[0]=='B')
            i=v.erase(i);
        else
            break;
    }
    print(v);
}
