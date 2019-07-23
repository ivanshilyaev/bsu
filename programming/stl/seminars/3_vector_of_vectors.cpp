#include <iostream>
#include <iterator>
#include <vector>
#include <string>
#include <numeric>
using namespace std;

// I
// вектор векторов
// 1. удалить строку и столбец, на пересечении которых стоит max elem (первый)
// 2. упорядочить элементы каждой строки по возрастанию (sort)
// 3. упорядочить строки матрицы лексикографически по возрастанию (sort)
// 4. упорядочить строки матрицы по возрастанию сумм элементов строки (sort + функция)

void print (vector<vector<int> > &v) {
    for (auto a:v) {
        for (auto b:a) {
            cout<<b<<" ";
        }
        cout<<endl;
    }
}

//3
bool CompareLexicographically(vector<int> &a, vector<int> &b) {
    return to_string(a[0]).compare(to_string(b[0]))<0;
}

//4
bool CompareBySum(vector<int> &a, vector<int> &b) {
    return accumulate(a.begin(), a.end(), 0) < accumulate(b.begin(), b.end(), 0);
}

int main() {
    int m=5,n=5;
    vector<vector<int> > v;
    vector<int> tmp;
    srand(time(NULL));
    for (int i=0; i<m; ++i) {
        for (int j=0; j<n; ++j) {
            int num=rand()%100 + 1;
            tmp.push_back(num);
        }
        v.push_back(tmp);
        tmp.clear();
    }
    cout<<"0)"<<endl; print(v); cout<<endl;
    //1
    int i_index(0), j_index(0), max(v[0][0]);
    for (int i=0; i<m; ++i) {
        for (int j=0; j<n; ++j) {
            if (v[i][j]>max) {
                max=v[i][j];
                i_index=i;
                j_index=j;
            }
        }
    }
    v.erase(v.begin() + i_index); //удаление строки
    m--;
    for (int i=0; i<m; ++i)
        v[i].erase(v[i].begin() + j_index);
    n--;
    cout<<"1)"<<endl; print(v); cout<<endl;
    //2
    for (int i=0; i<m; ++i)
        sort(v[i].begin(), v[i].end());
    cout<<"2)"<<endl; print(v); cout<<endl;
    //3
    sort(v.begin(), v.end(), CompareLexicographically);
    cout<<"3)"<<endl; print(v); cout<<endl;
    //4
    sort(v.begin(), v.end(), CompareBySum);
    cout<<"4)"<<endl; print(v);
}

