#include <iostream>
#include <time.h>
//extern "C" int sum(int*, int);
//extern "C" int _stdcall sum(int*, int);
extern "C" int _fastcall sum(int*, int);
using namespace std;

// Двойные слова, динамический массив, базовая адресация со смещением.
// Найти сумму числе между первым и последним отрицательными элементами массива.
// 7-8 - соглашение stdcall
// 9-10 - все соглашения

int main() {
	const int n = 10;
	int *arr = new int[n];
	srand(time(NULL));
	for (int i = 0; i < n; i++)
		arr[i] = rand() % 21 - 10; // [-10;10]
	for (int i = 0; i < n; i++)
		cout << arr[i] << endl;

	int s = sum(arr, n);

	cout << endl;
	cout << s << endl;

	system("pause");
	return 0;
}




//_asm {
//	mov eax, arr
//	mov ecx, n
//	cycle_1 :
//	cmp[eax], 0
//		jl drop_1
//		add eax, 4
//		loop cycle_1
//
//		drop_1 :
//	add eax, 4
//		dec ecx
//		xor ebx, ebx
//		xor edx, edx
//		cycle_2 :
//	cmp[eax], 0
//		jl drop_2
//		add edx, [eax]
//		jmp drop_3
//		drop_2 :
//	add ebx, edx
//		xor edx, edx
//		add edx, [eax]
//		drop_3 :
//		add eax, 4
//		loop cycle_2
//		mov sum, ebx
//}
