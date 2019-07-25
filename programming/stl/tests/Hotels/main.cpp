#include <iostream>
#include <vector>
#include <map>
#include <fstream>
#include <iterator>
#include <set>
using namespace std;

typedef pair<string,string> SS;
typedef vector<SS> VSS;
typedef map<string,VSS> MSV;

void findCity(string);
void findCount(string);
void findPair();

ifstream inf("DATA.txt");
ifstream in("QUERY.txt");
ofstream out("ANSWER.txt");

void inFileNotOpen(ifstream &f);
void outFileNotOpen(ofstream &f);
void fileIsEmpty(ifstream &f);
void closeFiles();

bool iequals(const string& a, const string& b);

MSV m;
MSV::iterator it;

int main() {
    try {
        string line;
        //проверка файлов
        inFileNotOpen(inf); inFileNotOpen(in); outFileNotOpen(out);
        fileIsEmpty(inf); fileIsEmpty(in);
        //считывание данных
        string city,name,stars;
        int begin(0), end(0);
        VSS v;
        while (getline(inf, line)) {
            if (line.empty())
                continue;
            begin=(int)line.find(";");
            city=line.substr(0,begin);
            end=(int)line.rfind(";");
            name=line.substr(begin+1, end-begin-1);
            stars=line.substr(end+1);
            
            if ((it=m.find(city)) != m.end())
                it->second.push_back(make_pair(name, stars));
            else {
                v.clear();
                v.push_back(make_pair(name, stars));
                m.insert(make_pair(city, v));
            }
        }
        
        //обработка запросов
        while (getline(in, line)) {
            if (line.empty())
                break;
            //cout<<line<<endl;
            char ch=line[0];
            switch (ch) {
                case '1': {
                    findCity(line.substr(2));
                    break;
                }
                case '2': {
                    findCount(line.substr(2));
                    break;
                }
                case '3': {
                    findPair();
                    break;
                }
                default:
                    out<<"Invalid command"<<endl;
                    break;
            }
        }
        
        closeFiles();
    }
    catch (const char *e) {
        cout<<e<<endl;
        closeFiles();
    }
    catch (...) {
        cout<<"Error"<<endl;
        closeFiles();
    }
}

void inFileNotOpen(ifstream &f) {
    if (!f.is_open())
        throw "File doesn't open!";
}

void outFileNotOpen(ofstream &f) {
    if (!f.is_open())
        throw "File doesn't open!";
}

void fileIsEmpty(ifstream &f) {
    string line;
    f>>line;
    if (f.eof())
        throw "File is empty!";
    f.seekg(0);
}

void closeFiles() {
    inf.close();
    in.close();
    out.close();
}

void findCity(string city) {
    it=m.find(city);
    if (it!=m.end()) {
        for (auto i:it->second)
            out<<i.first<<"; "<<i.second<<endl;
    }
    else
        out<<"No such city"<<endl;
}

void findCount(string hotel) {
    int num(0);
    for (auto city:m) {
        for (auto h:city.second) {
            if (iequals(h.first, hotel))
                num++;
        }
    }
    out<<num<<" hotels with such name"<<endl;
}

void findPair() {
    SS elem;
    set<SS> s;
    for (auto i:m) {
        for (auto j:i.second) {
            elem.first = i.first;
            elem.second = j.second;
            s.insert(elem);
        }
    }
    out<<"All different pairs:"<<endl;
    for (auto i:s)
        out<<i.first<<" "<< i.second<<endl;
}

bool iequals(const string& a, const string& b) {
    return equal(a.begin(), a.end(), b.begin(), b.end(), [](char a, char b)->bool {
        return tolower(a) == tolower(b);
    });
}
