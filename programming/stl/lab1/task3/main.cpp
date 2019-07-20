#include <iostream>
#include "Train.hpp"
#include "Time.hpp"
#include <vector>
#include <iterator>
#include <fstream>
#include <string>
using namespace std;

// 1) каждая запись в файле должна быть в отдельной строке
// 2) время в пути не превышает 24 часа

template <typename T> void print(vector <T> v) {
    if (v.size()==0)
        cout<<"Вектор пуст"<<endl;
    for_each(v.begin(), v.end(), [] (T elem) {
        cout<<elem<<endl;
        cout<<endl;
    });
}

bool departureTimeSort(const Train &a, const Train &b) {
    return a.getDepartureTime()<b.getDepartureTime();
}

bool iequals(const string &a, const string &b) {
    return equal(a.begin(), a.end(), b.begin(), b.end(),
                 [](char a, char b) {
                     return tolower(a) == tolower(b);
                 });
}

//2
void sortByDepartureTime(vector<Train> &v) {
    sort(v.begin(), v.end(), departureTimeSort);
}

//3
bool printByTime(vector<Train> &v, Time from, Time to) {
    vector<Train>::iterator i;
    int count=0;
    for (i=v.begin(); i!=v.end(); ++i) {
        if ((*i).getDepartureTime()>=from && (*i).getDepartureTime()<=to) {
            cout<<(*i)<<endl;
            cout<<endl;
            count++;
        }
    }
    return count!=0;
}

//4
bool printByDest(vector<Train> &v, string dest) {
    vector<Train>::iterator i;
    int count=0;
    for (i=v.begin(); i!=v.end(); ++i) {
        if (iequals((*i).getDestination(), dest)) {
            cout<<*i<<endl;
            count++;
        }
    }
    return count!=0;
}

//5
bool printFastByDest(vector<Train> &v, string dest) {
    vector<Train>::iterator i;
    int count=0;
    for (i=v.begin(); i!=v.end(); ++i) {
        if ((iequals((*i).getDestination(), dest)) && ((*i).getType()==express)) {
            cout<<*i<<endl;
            count++;
        }
    }
    return count!=0;
}

//6
Train findFast(vector<Train> &v, string dest) {
    Train result(-1, "", passenger, Time(0, 0), Time(23, 59));
    vector<Train>::iterator i;
    for (i=v.begin(); i!=v.end(); ++i) {
        if (iequals((*i).getDestination(), dest) && (*i).getWayTime()<result.getWayTime()) {
            result=*i;
        }
    }
    return result;
}

int main() {
    vector<Train> v;
    try {
        ifstream fin("/Users/ivansilaev/Downloads/УНИВЕР/Прога/2 семестр Лабы/laba_5/laba_5.3/input.txt", ios_base::in);
        if (!fin.is_open())
            throw "Error: file doesn't open";
        string line;
        fin>>line;
        if (fin.eof())
            throw "Error: file is empty";
        fin.seekg(0);
        int begin(0), end(0);
        string word, array[7], seps=" :";
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
            Time dt(stoi(array[3]), stoi(array[4]));
            Time wt(stoi(array[5]), stoi(array[6]));
            Type t;
            if (array[2]=="пассажирский")
                t=passenger;
            else
                t=express;
            Train train(stoi(array[0]), array[1], t, dt, wt);
            v.push_back(train);
        }
        //2
        cout<<"Упорядочение данных по времени отправления поездов:"<<endl;
        sortByDepartureTime(v);
        print(v);
        
        //3
        cout<<"Введите диапазон времени в формате \"HH:MM-HH:MM\""<<endl;
        int i=0;
        seps=" :-"; cin>>line; line.append(" ");
        begin=0; end=0;
        while ((begin=(int)line.find_first_not_of(seps, begin)) != string::npos) {
            end=(int)line.find_first_of(seps, begin);
            word=line.substr(begin, end-begin);
            array[i++]=word;
            begin=end;
        }
        Time from(stoi(array[0]), stoi(array[1]));
        Time to(stoi(array[2]), stoi(array[3]));
        cout<<"Поезда из данного диапазона:"<<endl;
        if (!printByTime(v, from, to))
            cout<<"Список пуст!"<<endl;
        
        //4
        cout<<"Введите пункт назначения"<<endl;
        string dest;
        cin>>dest;
        cout<<"Поезда, направляющиеся в заданный пункт:"<<endl;
        if (!printByDest(v, dest))
            cout<<"Список пуст!"<<endl;
        
        //5
        cout<<"Введите пункт назначения"<<endl;
        cin>>dest;
        cout<<"Скорые поезда, направляющиеся в заданный пункт:"<<endl;
        if (!printFastByDest(v, dest))
            cout<<"Список пуст!"<<endl;
        
        //6
        cout<<"Введите пункт назначения"<<endl;
        cin>>dest;
        cout<<"Самый быстрый поезд, направляющиеся в заданный пункт:"<<endl;
        Train result=findFast(v, dest);
        if (result.getNumber()==-1)
            cout<<"Нет такого поезда!"<<endl;
        else
            cout<<result<<endl;
    }
    catch (const char *e) {
        cout<<e<<endl;
    }
    catch (...) {
        cout<<"Error"<<endl;
    }
}
