#include <iostream>
#include <string>
#include <map>
#include <list>
#include <iterator>
#include <fstream>
#include <set>
using namespace std;

typedef pair<string,int> Pair;
typedef list<Pair> List;
typedef map<string, List> Map;

ifstream fin1("DATA.txt", ios_base::in);
ifstream fin2("QUERY.txt", ios_base::in);
ofstream fout("ANSWER.txt", ios_base::out);

void findPatient(string name);
void findDoctor(string doctor);
void findPairs();

bool iequals(const string &a, const string &b);

void inFileNotOpen(ifstream &f);
void outFileNotOpen(ofstream &f);
void fileIsEmpty(ifstream &f);
void closeFiles();

Map Mmap;
Map::iterator iter;

int main() {
    try {
        string line;
        //
        inFileNotOpen(fin1); inFileNotOpen(fin2);
        outFileNotOpen(fout);
        fileIsEmpty(fin1); fileIsEmpty(fin2);
        //
        string name, doctor, date;
        int begin(0), end(0);
        List Llist;
        while (getline(fin1, line)) {
            if (line.empty())
                continue;
            begin=(int)line.find(";");
            name=line.substr(0, begin);
            end=(int)line.rfind(";");
            doctor=line.substr(begin+1, end-begin-1);
            date=line.substr(end+1);
            if ((iter=Mmap.find(name)) != Mmap.end()) {
                iter->second.push_back(make_pair(doctor, stoi(date)));
            }
            else {
                Llist.clear();
                Llist.push_back(make_pair(doctor, stoi(date)));
                Mmap.insert(make_pair(name, Llist));
            }
        }
        
        //обработка запросов
        while (getline(fin2, line)) {
            if (line.empty())
                break;
            //cout<<line<<endl;
            char ch=line[0];
            switch (ch) {
                case '1': {
                    findPatient(line.substr(2));
                    break;
                }
                case '2': {
                    findDoctor(line.substr(2));
                    break;
                }
                case '3': {
                    findPairs();
                    break;
                }
                default:
                    fout<<"Invalid command"<<endl;
                    break;
            }
        }
        
        closeFiles();
    }
    catch (const char *e) {
        cout<<e<<endl;
    }
    catch (...) {
        cout<<"Error"<<endl;
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

void closeFiles() { fin1.close(); fin2.close(); fout.close(); }

void findPatient(string name) {
    iter=Mmap.find(name);
    if (iter!=Mmap.end()) {
        for (auto i:iter->second)
            fout<<i.first<<"; "<<i.second<<endl;
    }
    else
        fout<<"No such patients"<<endl;
}

void findDoctor(string doctor) {
    int n(0), count(0);
    for (auto i:Mmap) {
        n+=i.second.size();
        for (auto j:i.second) {
            if (iequals(doctor, j.first))
                count++;
        }
    }
    if (count!=0)
        fout<<"Otn: "<<count<<"/"<<n<<endl;
    else
        fout<<"No such doctor"<<endl;
}

void findPairs() {
    pair<int, string> elem;
    set<pair<int, string>> Sset;
    for (auto i:Mmap) {
        for (auto j:i.second) {
            elem.first=j.second;
            elem.second=j.first;
            Sset.insert(elem);
        }
    }
    fout<<"All different pairs:"<<endl;
    for (auto i:Sset)
        fout<<i.first<<" "<< i.second<<endl;
}

bool iequals(const string &a, const string &b) {
    return equal(a.begin(), a.end(), b.begin(), b.end(), [](char aa, char bb)->bool {
        return tolower(aa)==tolower(bb);
    });
}
