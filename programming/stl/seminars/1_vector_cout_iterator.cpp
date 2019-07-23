#include <iostream>
#include <cstring>
#include <iterator>
#include <vector>
using namespace std;

//Созать vector с координатами char, содержащий латинские буквы в алфавитном порядке

int main() {
    vector <char> v;
    for (char c='A'; c<='Z'; ++c)
        v.push_back(c);
    //1 способ
    for (int i=0; i<v.size(); ++i)
        cout<<v[i]<<" ";
    cout<<endl;
    //2 способ
    typedef vector<char>::iterator iter;
    for (iter i=v.begin(); i!=v.end(); ++i)
        cout<<*i<<" ";
    cout<<endl;
    //3 способ
    ostream_iterator<char> iter2(cout, " ");
    copy(v.begin(), v.end(), iter2);
    cout<<endl;
    //4 способ
    for (auto i:v)
        cout<<i<<" ";
    cout<<endl;
    //вывод в обратном порядке
    typedef vector<char>::reverse_iterator iter3;
    for (iter3 i=v.rbegin(); i!=v.rend(); ++i)
        cout<<*i<<" ";
    cout<<endl;
}
