#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <fstream>
#include <set>
#include <iterator>
using namespace std;

struct SMark {
    string Subject;
    int Mark;
    SMark(string s, int m) : Subject(s), Mark(m) {}
    friend ostream & operator<<(ostream &cout, const SMark &s) {
        cout<<"Subject: "<<s.Subject<<endl;
        cout<<"Mark: "<<s.Mark;
        return cout;
    }
};

struct SStudData {
    string Name;
    int Number;
    vector <SMark> Marks;
    SStudData(string n, int num, vector<SMark> &m) : Name(n), Number(num), Marks(m) {}
    friend ostream & operator<<(ostream &cout, SStudData &s) {
        cout<<"Name: "<<s.Name<<endl;
        cout<<"Number: "<<s.Number<<endl;
        vector<SMark>::iterator iter;
        for (iter=s.Marks.begin(); iter!=s.Marks.end(); ++iter) {
            cout<<(*iter)<<endl;
        }
        return cout;
    }
};

// сравнение строк без учёта регистра
bool iequals(const string& a, const string& b) {
    return std::equal(a.begin(), a.end(), b.begin(), b.end(), [](char a, char b)->bool {
        return tolower(a) == tolower(b);
    });
}

//1 - чтение из файла
void readMap(multimap <int, SStudData> &studentsData, string fileName) {
    ifstream fin(fileName, ios_base::in);
    if (!fin.is_open())
        throw "File doesn't open";
    string line;
    fin>>line;
    if (fin.eof())
        throw "File is empty";
    fin.seekg(0);
    string word, w2, seps=",;";
    int begin(0), end(0), b2(0), e2(0);
    while(getline(fin, line)) {
        if (line.empty())
            continue;
        line.append(",");
        begin=0; end=0;
        //выделение фио
        begin=(int)line.find_first_not_of(seps, begin);
        end=(int)line.find_first_of(seps, begin);
        word=line.substr(begin, end-begin);
        string Name=word;
        begin=end;
        //выделение номера зачётки
        begin=(int)line.find_first_not_of(seps, begin);
        end=(int)line.find_first_of(seps, begin);
        word=line.substr(begin+1, end-begin);
        int Number=stoi(word);
        begin=end;
        //выделение оценок
        vector <SMark> Marks;
        while ((begin=(int)line.find_first_not_of(seps, begin)) != string::npos) {
            end=(int)line.find_first_of(seps, begin);
            word=line.substr(begin, end-begin);
            word+=" "; b2=0; e2=0;
            b2=(int)word.find_first_not_of(" ", b2);
            e2=(int)word.find_first_of(" ", b2);
            string Subject=word.substr(b2, e2-b2);
            b2=e2;
            b2=(int)word.find_first_not_of(" ", b2);
            e2=(int)word.find_first_of(" ", b2);
            int Mark=stoi(word.substr(b2, e2-b2));
            Marks.push_back(SMark(Subject, Mark));
            begin=end;
        }
        studentsData.insert(make_pair(Number, SStudData(Name, Number, Marks)));
    }
    fin.close();
}

//3 - функция вычисления среднего балла
double pointAverage(SStudData &s) {
    vector<SMark>::iterator iter2;
    int sum(0), kol(0);
    for (iter2=s.Marks.begin(); iter2!=s.Marks.end(); ++iter2) {
        sum+=(*iter2).Mark; kol++;
    }
    return (double)sum/kol;
}

//4.1 - сортировка по убыванию среднего балла
bool Greater(const pair<double, SStudData> &a, const pair<double, SStudData> &b) {
    return a.first>b.first;
}

//4.2 - сортировка по фамилиям в алфавитном порядке
bool AlphabeticalOrder(const pair<double, SStudData> &a, const pair<double, SStudData> &b) {
    return a.second.Name.compare(b.second.Name)<0;
}

//5.1
void getBorders(double &a, double &b) {
    string line;
    cout<<"Enter range"<<endl;
    cout<<"Left border: "; cin>>line; a=stoi(line);
    if (a<0||a>10) {
        do {
            cout<<"Invalid data! Try again"<<endl;
            cin>>line; a=stoi(line);
        } while (a<0||a>10);
    }
    cout<<"Right border: "; cin>>line; b=stoi(line);
    if (b<a||b>10) {
        do {
            cout<<"Invalid data! Try again"<<endl;
            cin>>line; b=stoi(line);
        } while (b<a||b>10);
    }
}

//5.2
bool listByGPA(vector <pair<double, SStudData>> &studentsVector, double &a, double &b) {
    bool found=false;
    vector <pair<double, SStudData>>::iterator iter2;
    for (iter2=studentsVector.begin(); iter2!=studentsVector.end(); ++iter2) {
        if ((*iter2).first>=a && (*iter2).first<=b) {
            cout<<(*iter2).second.Name<<endl;
            found=true;
        }
    }
    return found;
}

//6
bool listBySubject(multimap <int, SStudData> &studentsData, string &subject) {
    bool found=false;
    map<int, SStudData>::iterator iter;
    for (iter=studentsData.begin(); iter!=studentsData.end(); ++iter) {
        vector<SMark>::iterator iter2;
        for (iter2=(*iter).second.Marks.begin(); iter2!=(*iter).second.Marks.end(); ++iter2) {
            if (iequals((*iter2).Subject, subject)) {
                cout<<(*iter).second.Name<<endl; found=true;
            }
        }
    }
    return found;
}

//7
void countStudentsForEachSubject(multimap <int, SStudData> &studentsData, map<string, int> &subjects) {
    subjects.clear();
    //добавление всех предметов в map
    map<int, SStudData>::iterator iter;
    for (iter=studentsData.begin(); iter!=studentsData.end(); ++iter) {
        vector<SMark>::iterator iter2;
        for (iter2=(*iter).second.Marks.begin(); iter2!=(*iter).second.Marks.end(); ++iter2) {
            subjects.insert(make_pair((*iter2).Subject, 0));
        }
    }
    //подсчёт каждого предмета
    for (iter=studentsData.begin(); iter!=studentsData.end(); ++iter) {
        vector<SMark>::iterator iter2;
        for (iter2=(*iter).second.Marks.begin(); iter2!=(*iter).second.Marks.end(); ++iter2) {
            subjects.find((*iter2).Subject)->second++;
        }
    }
}

//8
void countGpaForEachSubject(vector <pair<double, SStudData>> &studentsVector,
                            map<string, double> &GPA, map<string, int> &subjects) {
    GPA.clear();
    //добавление всех предметов в map
    vector <pair<double, SStudData>>::iterator iter;
    for (iter=studentsVector.begin(); iter!=studentsVector.end(); ++iter) {
        vector<SMark>::iterator iter2;
        for (iter2=(*iter).second.Marks.begin(); iter2!=(*iter).second.Marks.end(); ++iter2) {
            GPA.insert(make_pair((*iter2).Subject, 0));
        }
    }
    //подсчёт среднего балла по каждому предмету
    for (iter=studentsVector.begin(); iter!=studentsVector.end(); ++iter) {
        vector<SMark>::iterator iter2;
        for (iter2=(*iter).second.Marks.begin(); iter2!=(*iter).second.Marks.end(); ++iter2) {
            GPA.find((*iter2).Subject)->second+=(*iter2).Mark;
        }
    }
    map<string, int>::iterator iter3;
    for (iter3=subjects.begin(); iter3!=subjects.end(); ++iter3) {
        GPA.find((*iter3).first)->second=GPA.find((*iter3).first)->second/(*iter3).second;
    }
}

//9
void listOfStudentsWithMaxAmountOfPoint(vector <pair<double, SStudData>> &studentsVector) {
    double max=0;
    vector <pair<double, SStudData>>::iterator iter;
    for (iter=studentsVector.begin(); iter!=studentsVector.end(); ++iter) {
        if ((*iter).first>max)
            max=(*iter).first;
    }
    for (iter=studentsVector.begin(); iter!=studentsVector.end(); ++iter) {
        if ((*iter).first==max)
            cout<<(*iter).second.Name<<endl;
    }
}

//10
bool listOfStudentsWithUnsatisfactoryMarks(vector <pair<double, SStudData>> &studentsVector) {
    bool found=false;
    vector <pair<double, SStudData>>::iterator iter;
    for (iter=studentsVector.begin(); iter!=studentsVector.end(); ++iter) {
        vector<SMark>::iterator iter2;
        for (iter2=(*iter).second.Marks.begin(); iter2!=(*iter).second.Marks.end(); ++iter2) {
            if ((*iter2).Mark<4) {
                cout<<(*iter).second.Name<<endl;
                found=true;
            }
        }
    }
    return found;
}

int main() {
    try {
        multimap <int, SStudData> studentsData;
        string fileName="input.txt";
        readMap(studentsData, fileName);
        multimap<int, SStudData>::iterator iter;
        //формирование вектора со средним баллом каждого студента
        vector <pair<double, SStudData>> studentsVector;
        for (iter=studentsData.begin(); iter!=studentsData.end(); ++iter)
            studentsVector.push_back(make_pair(pointAverage((*iter).second), (*iter).second));
        vector <pair<double, SStudData>>::iterator iter2;
        //формирование map с количеством студентов, сдававших каждый предмет
        map<string, int> subjects; map<string, int>::iterator iter3;
        //формирование map со средним баллом по каждому предмету
        map<string, double> GPA; map<string, double>::iterator iter4;
        
        
        cout<<"Menu"<<endl;
        cout<<"1 - display students"<<endl;
        cout<<"2 - sort students by record number and display"<<endl;
        cout<<"3 - count Grade point average for each student"<<endl;
        cout<<"4 - display students in alphabetical order descending GPA"<<endl;
        cout<<"5 - display students with GPA from certain range"<<endl;
        cout<<"6 - display students who passed certain subject"<<endl;
        cout<<"7 - count how many students passed each subject"<<endl;
        cout<<"8 - count GPA for each subject"<<endl;
        cout<<"9 - find all students with max amount of points"<<endl;
        cout<<"10 - find all students with unsatisfactory marks (1,2,3)"<<endl;
        cout<<"11 - exit"<<endl;
        while (true) {
            cout<<"Enter a number:"<<endl;
            string s;
            cin>>s;
            int choice=stoi(s);
            if (choice<1 || choice>11)
                continue;
            switch (choice) {
                case 1: {
                    for (iter=studentsData.begin(); iter!=studentsData.end(); ++iter)
                        cout<<(*iter).second<<endl;
                    break;
                }
                case 2: {
                    for (iter=studentsData.begin(); iter!=studentsData.end(); ++iter)
                        cout<<(*iter).second<<endl;
                    break;
                }
                case 3: {
                    cout<<"Grade point average:"<<endl;
                    for (iter=studentsData.begin(); iter!=studentsData.end(); ++iter)
                        cout<<(*iter).second.Name<<": "<<pointAverage((*iter).second)<<endl;
                    break;
                }
                case 4: {
                    cout<<"List of all students with grade point average: "<<endl;
                    sort(studentsVector.begin(), studentsVector.end(), Greater); //сортируем по убыванию среднего балла
                    sort(studentsVector.begin(), studentsVector.end(), AlphabeticalOrder); //сортируем по фамилиям в алфавитном порядке
                    int i(1);
                    for (iter2=studentsVector.begin(); iter2!=studentsVector.end(); ++iter2) {
                        cout<<i++<<", "<<(*iter2).second.Name<<", "<<(*iter2).second.Number<<", "<<(*iter2).first<<endl;
                    }
                    break;
                }
                case 5: {
                    double a, b;
                    cout<<"List of students with GPA from certain range"<<endl;
                    getBorders(a, b);
                    sort(studentsVector.begin(), studentsVector.end(), Greater); //сортируем по убыванию среднего балла
                    if (!listByGPA(studentsVector, a, b))
                        cout<<"No students with such GPA!"<<endl;
                    break;
                }
                case 6: {
                    string line;
                    cout<<"List of students who passed certain subject"<<endl;
                    cout<<"Enter a subject: "; cin>>line;
                    if (!listBySubject(studentsData, line))
                        cout<<"No student who passed this subject!"<<endl;
                    break;
                }
                case 7: {
                    countStudentsForEachSubject(studentsData, subjects);
                    for (iter3=subjects.begin(); iter3!=subjects.end(); ++iter3)
                        cout<<(*iter3).first<<" "<<(*iter3).second<<endl;
                    break;
                }
                case 8: {
                    countStudentsForEachSubject(studentsData, subjects);
                    countGpaForEachSubject(studentsVector, GPA, subjects);
                    set<pair<double, string>> setGPA;
                    for (iter4=GPA.begin(); iter4!=GPA.end(); ++iter4)
                        setGPA.insert(make_pair((*iter4).second, (*iter4).first));
                    set<pair<double, string>>::reverse_iterator iter5;
                    for (iter5=setGPA.rbegin(); iter5!=setGPA.rend(); ++iter5)
                        cout<<(*iter5).second<<" "<<(*iter5).first<<endl;
                    break;
                }
                case 9: {
                    listOfStudentsWithMaxAmountOfPoint(studentsVector);
                    break;
                }
                case 10: {
                    if (!listOfStudentsWithUnsatisfactoryMarks(studentsVector))
                        cout<<"No students with unsatisfactory marks!"<<endl;
                    break;
                }
                case 11: {
                    throw "The end!";
                    break;
                }
                default:
                    break;
            }
        }
    }
    catch(const char *e) {
        cout<<e<<endl;
    }
    catch(...) {
        cout<<"Error"<<endl;
    }
}
