#include <iostream>
#include <time.h>
extern "C" void func1(unsigned int*, int);
extern "C" void _stdcall func2(unsigned int*, int);
extern "C" void _fastcall func3(unsigned int*, int);
using namespace std;

// Задание: изменить массив так, чтобы сначала стояли все чётные числа,
// а затем все нечётные.

int main() {
    setlocale(LC_ALL, ".1251");
    const int n = 10;
    unsigned int arr[n];
    srand(time(NULL));
    for (int i = 0; i < n; ++i)
        arr[i] = rand() % 21 - 10;
    cout << "Исходный массив:" << endl;
    for (int i = 0; i < n; ++i)
        cout << arr[i] << " ";
    cout << endl;
    
    cout << "Выберите соглашение:" << endl;
    cout << "1 - cdecl" << endl;
    cout << "2 - stdcall" << endl;
    cout << "3 - fastcall" << endl;
    int ch;
    cin >> ch;
    while (ch < 1 || ch>3) {
        cout << "Неверные данные!" << endl;
        cin>>ch;
    }
    switch (ch) {
        case 1: {
            func1(&arr[0], n);
            break;
        }
        case 2: {
            func2(&arr[0], n);
            break;
        }
        case 3: {
            func3(&arr[0], n);
            break;
        }
        default:
            break;
    }
    
    cout << "Полученный массив:" << endl;
    for (int i = 0; i < n; ++i)
        cout << arr[i] << " ";
    cout << endl;
    
    system("pause");
    return 0;
}



//_asm {
//    lea ebx, arr
//    mov ecx, n
//    xor esi, esi
//    xor edi, edi
//    dec edi
//    cycle_1 :
//    push ecx
//        mov eax, [ebx + 4 * esi]
//        mov ecx, 2
//        xor edx, edx
//        div ecx
//        cmp edx, 0
//        jne drop_1
//        // двигаем чётное число влево до упора
//        mov ecx, esi
//        push esi
//        cycle_2 :
//    dec ecx
//        cmp ecx, edi
//        je drop_2
//        mov eax, [ebx + 4 * esi]
//        mov edx, [ebx + 4 * ecx]
//        mov[ebx + 4 * esi], edx
//        mov[ebx + 4 * ecx], eax
//        dec esi
//        jmp cycle_2
//
//        drop_2 :
//    inc edi
//        pop esi
//        drop_1 :
//    inc esi
//        pop ecx
//        loop cycle_1
//}
