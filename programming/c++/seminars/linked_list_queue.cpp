#include <iostream>
#include <iomanip>
using namespace std;

// 1. В прямоугольнике MxN произвольным образом вырезали k клеток. Найти кол-во частей, на которые распадётся прямоугольник

// Очередь на списке

struct Point {
    int x;
    int y;
};

struct ListItem {
    Point Info;
    ListItem* Next;
};
ListItem* First;
ListItem* Last;

void insertToEnd(Point& p) {
    ListItem* Elem=new ListItem;
    Elem->Info=p;
    Elem->Next=NULL;
    if (Last==NULL) {
        Last=Elem;
        First=Elem;
    }
    else {
        Last->Next=Elem;
        Last=Elem;
    }
}

Point deleteFromBegin() {
    Point data=First->Info;
    if (First==Last)
        Last=NULL;
    First=First->Next;
    return data;
}

void push(Point& p) {
    insertToEnd(p);
}

Point pop() {
    return deleteFromBegin();
}

bool empty() {
    return First==NULL;
}

bool find(int** matrix, int m, int n, int kol, Point& p) {
    for (int i=0; i<m; i++)
        for (int j=0; j<n; j++)
            if (matrix[i][j]==0) {
                matrix[i][j]=kol;
                p.x=i;
                p.y=j;
                return true;
            }
    return false;
}

void printMartix(int **matrix, int m, int n) {
    for (int i=0; i<m; i++) {
        for (int j=0; j<n; j++)
            cout<<setw(6)<<matrix[i][j];
        cout<<endl;
    }
}

void print() {
    ListItem* Elem=First;
    if (!Elem) {
        cout << "Список пуст" << endl;
        return;
    }
    cout<<"Список:"<<endl;
    while (Elem) {
        cout<<"x: "<<Elem->Info.x<<", y: "<<Elem->Info.y<<endl;
        Elem=Elem->Next;
    }
}

int main() {
    setlocale(LC_ALL, ".1251");
    int m,n,k,x,y;
    cout<<"Введите размеры матрицы"<<endl;
    cin>>m>>n;
    int** matrix = new int*[m];
    for (int i=0; i<m; i++)
        matrix[i]=new int[n];
    for (int i=0; i<m; i++)
        for (int j=0; j<n; j++)
            matrix[i][j]=0;
    cout<<"Введите количество вырезанных точек"<<endl;
    cin>>k;
    cout<<"Введите координаты этих точек"<<endl;
    for (int i=0; i<k; i++) {
        cin>>x>>y;
        matrix[x][y]=-1;
    }
    
    // помещение соседей
    int sx[] = {1, 0, -1, 0};
    int sy[] = {0, 1, 0, -1};
    
    Point p;
    int kol=1; // число кусков
    while (find(matrix, m, n, kol, p)) {
        push(p);
        print();
        do {
            p=pop();
            print();
            matrix[p.x][p.y]=kol;
            // пометсили точку в очередь и помещаем в очередь всех её соседей
            for (int i=0; i<4; i++)
                if ((p.x+sx[i]>=0) && (p.x+sx[i]<n) && (p.y+sy[i]>=0) && (p.y+sy[i]<m) && (matrix[p.x+sx[i]][p.y+sy[i]]==0)) {
                    Point p1;
                    p1.x=p.x+sx[i];
                    p1.y=p.y+sy[i];
                    push(p1);
                    print();
                }
        } while (!empty());
        kol++;
    }
    kol--;
    if (kol==0)
        cout<<"Не отсалось ни одного куска"<<endl;
    else
        cout<<"Количество частей, на которые распался прямоугольник: "<<kol<<""<<endl;
    
    cout<<endl;
    cout<<"Полученная матрица:"<<endl;
    printMartix(matrix, m, n);
    
    return 0;
}
