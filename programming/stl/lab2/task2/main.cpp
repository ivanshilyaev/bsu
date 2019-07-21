#include <iostream>
#include <set>
#include <map>
#include <fstream>
#include <string>
using namespace std;

// сравнение строк без учёта регистра
bool iequals(const string& a, const string& b) {
    return std::equal(a.begin(), a.end(), b.begin(), b.end(), [](char a, char b)->bool {
        return tolower(a) == tolower(b);
    });
}

int main() {
    setlocale(LC_ALL, ".1251");
    try {
        //all words in file
        set<string> words;
        typedef set<string>::iterator setIter;
        ifstream fin("input.txt", ios_base::in);
        if (!fin.is_open())
            throw "File doesnt't open";
        string line;
        fin>>line;
        if (fin.eof())
            throw "File is empty";
        fin.seekg(0);
        while (fin>>line)
            words.insert(line);
        cout<<"All the words:"<<endl;
        copy(words.begin(), words.end(), ostream_iterator<string>(cout, "\n"));
        cout<<endl;
     
        //count each word
        map<string, int> wordbook;
        typedef map<string, int>::iterator mapIter;
        for (setIter i=words.begin(); i!=words.end(); ++i) {
            bool flag=false;
            for (mapIter j=wordbook.begin(); j!=wordbook.end(); ++j) {
                if (iequals(j->first, *i)) {
                    j->second=j->second+1;
                    flag=true;
                    break;
                }
            }
            if (!flag)
                wordbook.insert(make_pair(*i, 1));
        }
        cout<<"Number of each word:"<<endl;
        for (auto i:wordbook)
            cout<<i.first<<" "<<i.second<<endl;
    }
    catch (const char * e) {
        cout<<e<<endl;
    }
}
