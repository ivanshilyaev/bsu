#include <iostream>
#include <cmath>
#include <bitset>
using namespace std;

//шаблон
template <class T> T lessZeros(int, T *);

//специализации шаблона для int
template <> int lessZeros<int>(int size, int *array) {
    int min=INT_MAX; int result=0;
    for (int i=0; i<size; ++i) {
        size_t numOfZeros=0;
        int value=array[i];
        for (size_t j=0; j<(CHAR_BIT*sizeof(value)); ++j) {
            if ((value & (1 << j)) == 0)
                ++numOfZeros;
        }
        if (numOfZeros<min) {
            min=(int)numOfZeros;
            result=array[i];
        }
    }
    return result;
}

//специализации шаблона для double
//Алгоритм:
//1) получаем представление double из памяти
//2) каждый байт приводим к шестнадцатеричному целому числу
//3) считаем количество единиц в нём

template <> double lessZeros<double>(int size, double *array) {
    int min=INT_MAX; double result=0;
    for (int i=0; i<size; ++i) {
        unsigned char *p = (unsigned char*)&array[i];
        size_t numOfZeros=0;
        for (int k=0; k<sizeof(double); ++k) {
            int value=(int)p[k];
            for (size_t j=0; j<(CHAR_BIT*sizeof(value)); ++j) {
                if ((value & (1 << j)) == 0)
                    ++numOfZeros;
            }
        }
        if (numOfZeros<min) {
            min=(int)numOfZeros;
            result=array[i];
        }
    }
    return result;
}

//специализации для строк char*
template <> char * lessZeros<char*>(int size, char **array) {
    int min=INT_MAX; int indexOfResult=0;
    for (int i=0; i<size; ++i) {
        size_t numOfZeros=0;
        for (int j=0; j<strlen(array[i]); ++j) {
            unsigned char value=array[i][j];
            for (size_t k=0; k<(CHAR_BIT*sizeof(value)); ++k) {
                if ((value & (1 << k)) == 0)
                    ++numOfZeros;
            }
        }
        if (numOfZeros<min) {
            min=(int)numOfZeros;
            indexOfResult=i;
        }
    }
    return array[indexOfResult];
}

int main() {
    cout<<"1 - int"<<endl;
    cout<<"2 - double"<<endl;
    cout<<"3 - char *"<<endl;
    char ch;
    cin>>ch;
    switch (ch) {
        case '1': {
            int n;
            cout<<"Enter size of array:"<<endl;
            cin>>n;
            int *array=new int[n];
            cout<<"Filling array with random int numbers from 1 to 100..."<<endl;
            srand((int)time(NULL));
            for (int i=0; i<n; ++i)
                array[i]=rand()%100+1;
            cout<<"Our array:"<<endl;
            for (int i=0; i<n; ++i)
                cout<<array[i]<<endl;
            cout<<"Int with least number of zeros in binary representation: "<<endl;
            int result=lessZeros(n, array);
            cout<<result<<endl;
            break;
        }
        case '2': {
            int n;
            cout<<"Enter size of array:"<<endl;
            cin>>n;
            double *array=new double[n];
            cout<<"Filling array with random double numbers from 1 to 100..."<<endl;
            srand((int)time(NULL));
            for (int i=0; i<n; ++i) {
                //cin>>array[i];
                array[i] = 1 + (double)rand() / RAND_MAX * 100;
            }
            cout<<"Our array:"<<endl;
            for (int i=0; i<n; ++i)
                cout<<array[i]<<endl;
            cout<<"Double with least number of zeros in binary representation: "<<endl;
            double result=lessZeros(n, array);
            cout<<result<<endl;
            delete[] array;
            break;
        }
        case '3': {
            int n;
            cout<<"Enter size of array:"<<endl;
            cin>>n;
            char **array=new char*[n];
            for (int i=0; i<n; i++)
                array[i]=new char[256];
            cout<<"Enter "<<n<<" strings:"<<endl;
            while (getchar()!='\n');
            for (int i=0; i<n; ++i)
                cin.getline(array[i], 255, '\n');
            cout<<"Our array:"<<endl;
            for (int i=0; i<n; ++i)
                cout<<array[i]<<endl;
            cout<<"Char * with least number of zeros in binary representation: "<<endl;
            cout<<lessZeros(n, array)<<endl;
        
            for (int i=0; i<n; ++i)
                delete *(array+i);
            delete[] array;
            break;
        }
        default:
            cout<<"Invalid input. Try again"<<endl;
            break;
    }
}
