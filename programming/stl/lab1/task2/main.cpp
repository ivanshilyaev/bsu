#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>
#include <fstream>
using namespace std;

struct Student {
    string fio;
    int course, group;
};

template <typename T> void print(vector <T> v) {
    if (v.size()==0)
        cout<<"Вектор пуст"<<endl;
    for_each(v.begin(), v.end(), [] (T elem) {
        cout<<elem<<endl;
    });
}

bool CompareFio(const Student &a, const Student &b) { return a.fio<b.fio; }

bool CompareCourse(const Student &a, const Student &b) {
    return (a.course<b.course) || (a.course==b.course && a.group<b.group) || (a.course==b.course && a.group==b.group && a.fio<b.fio);
}

int main() {
    vector <Student> v;
    try {
        ifstream fin("Students.txt", ios_base::in);
        if (!fin.is_open())
            throw "Error: file doesn't open";
        string line;
        fin>>line;
        if (fin.eof())
            throw "Error: file is empty";
        fin.seekg(0);
        string seps=" ;", word;
        int begin(0), end(0);
        string array[5];
        while (getline(fin, line)) {
            if (line=="")
                continue;
            int i=0; begin=0; end=0;
            line.append(" ");
            while ((begin=(int)line.find_first_not_of(seps, begin)) != string::npos) {
                end=(int)line.find_first_of(seps, begin);
                word=line.substr(begin, end-begin);
                array[i++]=word;
                begin=end;
            }
            Student stud;
            stud.fio=array[0];
            stud.fio.append(" "); stud.fio.append(array[1]);
            stud.fio.append(" "); stud.fio.append(array[2]);
            stud.course=stoi(array[3]);
            stud.group=stoi(array[4]);
            v.push_back(stud);
        }
        fin.close();
        
        vector<Student> v1(v), v2(v);
        sort(v1.begin(), v1.end(), CompareFio);
        sort(v2.begin(), v2.end(), CompareCourse);
        
        ofstream fout1("Fio.txt", ios_base::out);
        if (!fout1.is_open())
            throw "Error: file doesn't open";
        vector<Student>::iterator i;
        for (i=v1.begin(); i!=v1.end(); ++i) {
            fout1<<(*i).fio<<"; "<<(*i).course<<"; "<<(*i).group<<endl;
        }
        ofstream fout2("Groups.txt", ios_base::out);
        if (!fout2.is_open())
            throw "Error: file doesn't open";
        for (i=v2.begin(); i!=v2.end(); ++i) {
            fout2<<(*i).fio<<"; "<<(*i).course<<"; "<<(*i).group<<endl;
        }
        fout1.close();
        fout2.close();
    }
    catch (const char *e) {
        cout<<e<<endl;
    }
    catch (...) {
        cout<<"Error"<<endl;
    }
}
