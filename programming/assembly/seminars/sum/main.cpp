// На отрезке [a;b] найти число с максимальной суммой цифр.
// Для нахождения суммы цифр использовать отдельную процедуру
// внутри ассемблерного модуля.
#include <iostream>
extern "C" int  _fastcall digitsum(int, int);
using namespace std;

int main() {
    cout << digitsum(50600, 50900) << endl;
    // answer = 50899
    system("pause");
    return 0;
}

// ecx - a
// edx - b
