#include <iostream>
#include <vector>
#include <iterator>
#include <algorithm>
#include <numeric>
#include <cmath>
using namespace std;

// вывод на экран
template <typename T> void print(vector <T> v) {
    if (v.size()==0)
        cout<<"Вектор пуст"<<endl;
    for_each(v.begin(), v.end(), [] (T elem) {
        cout<<elem<<endl;
    });
}

// функциональный объект для сравнения (4)
class Comparison {
    int n;
public:
    Comparison(int an) : n(an) {}
    bool operator() (int a) { return a>n; }
};

int sumInRange=0;
// функция для изменения объектов в векторе (6)
void add(int &n) { n+=sumInRange; }

//7
bool isOdd(int a) { return  abs(a)%2==0; }

//8
void deleteByModule(vector<int> &v) {
    for (int i=0; i<v.size()-1; ++i) {
        for (int j=i+1; j<v.size(); ++j) {
            if (fabs(v[i])==fabs(v[j])) {
                v.erase(v.begin()+j);
                j--;
            }
        }
    }
}

int main() {
    setlocale(LC_ALL, ".1251");
    cout<<"Введите целые числа - элементы вектора. Для завершения считывания введите любой символ"<<endl;
    typedef vector<int>::iterator iter;
    vector<int> v;
    // ввод с клавиатуры
    string buffer;
    try {
        while (true) {
            cin>>buffer;
            int number=stoi(buffer);
            v.push_back(number);
        }
    }
    catch (...) {
        //
    }
    
    if (v.size()==0) {
        cout<<"Вектор пуст. Изменение элементов вектора невозможно"<<endl;
        return 0;
    }
    
    cout<<"Исходный вектор:"<<endl;
    print(v);
    
    //1
    int sum=accumulate(v.begin(), v.end(), 0);
    cout<<"Сумма всех чисел в контейнере:"<<endl;
    cout<<sum<<endl;
    //2
    cout<<"Общее количество чисел в контейнере:"<<endl;
    cout<<v.size()<<endl;
    //3
    cout<<"Введите целое число:"<<endl;
    int number; cin>>number;
    cout<<"Количество чисел в контейнере, равных числу "<<number<<":"<<endl;
    cout<<count(v.begin(), v.end(), number)<<endl;
    //4
    cout<<"Введите целое число, с которым необходимо произвести сравнение:"<<endl;
    cin>>number;
    cout<<"Количество чисел в контейнере, больших числа "<<number<<":"<<endl;
    cout<<count_if(v.begin(), v.end(), Comparison(number))<<endl;
    //5
    int mean=sum/v.size();
    replace(v.begin(), v.end(), 0, mean);
    cout<<"Элементы в контейнере после замены всех нулей на среднее арифметическое:"<<endl;
    print(v);
    //6
    cout<<"Введите начало и конец диапазона [a;b) вектора:"<<endl;
    int a,b;
    do {
        cin>>a>>b;
        if (!((a>=0) && (a<=v.size()) && (b>=0) && (b<=v.size()) && (a<=b)))
            cout<<"Неверные значения, повторите попытку"<<endl;
    } while (!((a>=0) && (a<=v.size()) && (b>=0) && (b<=v.size()) && (a<=b)));
    sumInRange=accumulate(v.begin()+a, v.begin()+b, 0);
    for_each(v.begin(), v.end(), add);
    cout<<"Контейнер после изменений (добавить к каждому элементу вектора сумму всех чисел из заданного интервала этого же вектора):"<<endl;
    print(v);
    //7
    iter imin=min_element(v.begin(), v.end());
    iter imax=max_element(v.begin(), v.end());
    replace_if(v.begin(), v.end(), isOdd, *imax-*imin);
    cout<<"Контейнер после изменений (заменить все числа, модуль которых есть четное число, на разность максимального и минимального элемента этого вектора):"<<endl;
    print(v);
    //8
    deleteByModule(v);
    cout<<"Контейнер после изменений (удалить из последовательности все равные по модулю числа, кроме первого из них):"<<endl;
    print(v);
}
