#include <iostream>
#include <iomanip>
using namespace std;

// Кольцевая очередь на массиве

// 1. В прямоугольнике MxN произвольным образом вырезали k клеток. Найти кол-во частей, на которые распадётся прямоугольник

struct point {
    int x;
    int y;
};

struct queue {
    int size;
    int front;
    int rear;
    point* array;
};

void init(int len, queue& q) {
    q.size=len;
    q.front=0;
    q.rear=0;
    q.array = new point[len+1];
}

void del(queue& q) {
    delete [] q.array;
    q.front=0;
    q.rear=0;
    q.size=0;
}

void push(point& p, queue& q) {
    if ((q.rear+1==q.front) || ((q.front==0) && (q.rear==q.size)))
        throw "Queue is full";
    q.array[q.rear++]=p;
    if (q.rear==q.size+1)
        q.rear=0;
}

point pop(queue& q) {
    if (q.rear==q.front)
        throw "Queue is empty";
    point p = q.array[q.front++];
    if (q.front==q.size+1)
        q.front=0;
    return p;
}

bool find(int** matrix, int m, int n, int kol, point& p) {
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
    
    queue q;
    point p;
    init(m*n+1, q);
    int kol=1; // число кусков
    try {
        while (find(matrix, m, n, kol, p)) {
            push(p, q);
            do {
                p = pop(q);
                matrix[p.x][p.y]=kol;
                // пометсили точку в очередь и помещаем в очередь всех её соседей
                for (int i=0; i<4; i++)
                    if ((p.x+sx[i]>=0) && (p.x+sx[i]<n) && (p.y+sy[i]>=0) && (p.y+sy[i]<m) && (matrix[p.x+sx[i]][p.y+sy[i]]==0)) {
                        point p1;
                        p1.x=p.x+sx[i];
                        p1.y=p.y+sy[i];
                        push(p1, q);
                    }
            }
            while (q.front != q.rear);
            kol++;
        }
    }
    catch (char* s) {
        cout<<s<<endl;
    }
    kol--;
    if (kol==0)
        cout<<"Не отсалось ни одного куска"<<endl;
    else
        cout<<"Количество частей, на которые распался прямоугольник: "<<kol<<""<<endl;
    del(q);
    
    cout<<endl;
    cout<<"Полученная матрица:"<<endl;
    printMartix(matrix, m, n);
    
    for (int i=0; i<m; i++)
        delete[] matrix[i];
    delete[] matrix;
    return 0;
}
