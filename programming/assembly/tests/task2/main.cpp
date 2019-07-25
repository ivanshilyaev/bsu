#include <iostream>
#include <time.h>
using namespace std;
extern "C" int sum(int, short[10]);

int main() {
	setlocale(LC_ALL, ".1251");
	const int n = 10;
	short arr[n];
	srand(time(NULL));
	for (int i = 0; i < n; ++i)
		arr[i] = rand() % 21 -10;
	for (int i = 0; i < n; ++i)
		cout << arr[i] << endl;
	//short s(0);
	int s = sum(n, arr);

	cout << endl;
	if (s == -1)
		cout << "В массиве послденее число является отрицательным!" << endl;
	else if (s == -2)
		cout << "В массиве нет отрицательных чисел!" << endl;
	else
		cout << s << endl;

	system("pause");
	return 0;
}

//_asm {
//    xor eax, eax
//    lea edx, arr
//
//    mov esi, n
//    xor bx, bx
//    cycle_1 :
//    dec esi
//        cmp[edx][2 * esi], 0
//        jl drop_1
//        inc eax
//        add bx, [edx][2 * esi]
//        cmp esi, 0
//        jg cycle_1
//        drop_1 :
//    cmp eax, 0
//        jg drop_2
//        mov s, -1
//        jmp my_exit
//        drop_2 :
//    mov ecx, n
//        cmp eax, ecx
//        jne drop_3
//        mov s, -2
//        jmp my_exit
//        drop_3 :
//    mov s, bx
//        my_exit :
//
//}

//_asm {
//    xor eax, eax
//    lea edx, arr
//
//    mov ecx, n
//    xor bx, bx
//    cycle_1 :
//    dec ecx
//        cmp[edx][2 * ecx], 0
//        jl drop_1
//        inc eax
//        add bx, [edx][2 * ecx]
//        cmp ecx, 0
//        jg cycle_1
//        drop_1 :
//    cmp eax, 0
//        jg drop_2
//        mov s, -1
//        jmp my_exit
//        drop_2 :
//    mov ecx, n
//        cmp eax, ecx
//        jne drop_3
//        mov s, -2
//        jmp my_exit
//        drop_3 :
//    mov s, bx
//        my_exit :
//
//}
