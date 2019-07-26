#include <iostream>
#include <iomanip>
#include <fstream>
using namespace std;

struct point {
    int x;
    int y;
};

//кольцевая очередь на массиве
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
    q.array=new point[len+1];
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
    point p=q.array[q.front++];
    if (q.front==q.size+1)
        q.front=0;
    return p;
}

bool isEmpty(queue& q) {
    return q.rear==q.front;
}

void printMatrix(int **matrix, int n) {
    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++)
            cout<<matrix[i][j];
        cout<<endl;
    }
}

int main() {
    setlocale(LC_ALL, ".1251");
    ifstream fin("input.txt", ios_base::in);
    if (!fin.is_open()) {
        cout<<"Input file doesn't open!"<<endl;
        return 1;
    }
    int n,k,x1,y1,x2,y2;
    //cout<<"Введите размер квадратной матрицы"<<endl;
    fin>>n;
    if (fin.eof()) {
        cout<<"Input file is empty!"<<endl;
        return 1;
    }
    int **matrix=new int*[n];
    for (int i=0; i<n; i++)
        matrix[i]=new int[n];
    
    // помечаем ячейки
    int **value=new int*[n];
    for (int i=0; i<n; i++)
        value[i]=new int[n];
    for (int i=0; i<n; i++)
        for (int j=0; j<n; j++)
            value[i][j]=0;
    
    //cout<<"Введите план местности"<<endl;
    for (int i=0; i<n; i++)
        for (int j=0; j<n; j++)
            fin>>matrix[i][j];
    //cout<<"Введите величину k - максимальную крутизну подъёмов и спусков"<<endl;
    fin>>k;
    //cout<<"Введите стартовую позицию"<<endl;
    fin>>x1>>y1;
    //cout<<"Введите финишную позицию"<<endl;
    fin>>x2>>y2;
    
    // помещение соседей
    int sx[] = {1, 0, -1, 0};
    int sy[] = {0, 1, 0, -1};
    
    queue q;
    point p;
    bool found=false;
    init(n*n, q);
    p.x=x1; p.y=y1;
    int d=0;
    value[x1][y1]=d;
    push(p, q);
    try {
        while (!isEmpty(q)) {
            p=pop(q);
            if ((p.x==x2) && (p.y==y2)) {
                found=true;
                break;
            }
            for (int i=0; i<4; i++)
                if ( (p.x+sx[i]>=0) && (p.x+sx[i]<n) && (p.y+sy[i]>=0) && (p.y+sy[i]<n) &&
                    (matrix[p.x+sx[i]][p.y+sy[i]]!=-1) &&
                    ((abs(matrix[p.x+sx[i]][p.y+sy[i]]-matrix[p.x][p.y]))<=k)
                    && value[p.x+sx[i]][p.y+sy[i]]==0 ) {
                    point p1;
                    p1.x=p.x+sx[i];
                    p1.y=p.y+sy[i];
                    push(p1, q);
                    value[p1.x][p1.y]=value[p.x][p.y]+1;
                }
            matrix[p.x][p.y]=-1;
        }
    }
    catch(const char* e) {
        cout<<e<<endl;
    }
    ofstream fout("output.txt", ios_base::out);
    if (found) {
        fout<<"Маршрут:"<<endl;
        d=value[x2][y2];
        int **way=new int*[2];
        for (int i=0; i<2; i++)
            way[i]=new int[d];
        int count=0;
        way[0][count]=x1; way[1][count]=y1;
        count++;
        
        int x=x1,y=y1;
        while (!((x==x2) && (y==y2))) {
            for (int i=0; i<4; i++) {
                if ((x+sx[i]>=0) && (x+sx[i]<n) && (y+sy[i]>=0) && (y+sy[i]<n) &&
                (value[x+sx[i]][y+sy[i]]==count)) {
                    x+=sx[i]; y+=sy[i];
                    way[0][count]=x; way[1][count]=y;
                    count++;
                    break;
                }
            }
        }
        
        
        for (int i=0; i<d; i++)
            fout<<way[0][i]<<" "<<way[1][i]<<endl;
        fout<<x2<<" "<<y2<<endl;
        
        fout<<"Карта маршрута:"<<endl;
        char **map = new char*[n];
        for (int i=0; i<n; i++)
            map[i]=new char[n];
        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                map[i][j]='o';
        for (int i=0; i<d; i++) {
            x=way[0][i];
            y=way[1][i];
            map[x][y]='x';
        }
        map[x1][y1]='s'; map[x2][y2]='f';
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++)
                fout<<setw(3)<<map[i][j];
            fout<<endl;
        }
        
        for (int i=0; i<n; i++)
            delete[] map[i];
        delete[] map;
        
        for (int i=0; i<2; i++)
            delete[] way[i];
        delete[] way;
    }
    else {
        fout<<"Маршрута не существует"<<endl;
    }
    //printMatrix(value, n);
    
    del(q);
    for (int i=0; i<n; i++) {
        delete[] matrix[i];
        delete[] value[i];
    }
    delete[] matrix;
    delete[] value;
    fin.close();
    fout.close();
    return 0;
}
