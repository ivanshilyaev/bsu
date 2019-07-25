#include <iostream>
#include <cmath>
//extern "C" void mysort(int, int*);
//extern "C" void _stdcall mysort(int, int*);
extern "C" void _fastcall mysort(int*, int);
using namespace std;

int main() {
	int *arr = new int[8]{ -1, 3, -5, 7, -7, 19, -21, 14 };
	int n = 8;

	//mysort(n, arr);
	mysort(arr, n);

	//sortByModule(arr, n);
	for (int i = 0; i < n; ++i)
		cout << arr[i] << endl;
	delete[]arr;
	system("pause");
	return 0;
}

void sortByModule(int *arr, int n) {
	for (int i = 0; i < n - 1; ++i) {
		int cindex = i;
		for (int j = i + 1; j < n; ++j)
			if (fabs(arr[j]) > fabs(arr[cindex]))
				cindex = j;
		int tmp = arr[i];
		arr[i] = arr[cindex];
		arr[cindex] = tmp;
	}
}

//_asm {
//	mov ecx, n
//	dec ecx
//	mov edx, arr // arr
//	xor edi, edi // i
//	xor esi, esi // j
//
//	my_extern :
//	mov eax, edi // cindex
//		mov esi, edi
//		my_intern :
//	inc esi
//		push ecx // сравнение по модулю
//		mov ebx, [edx][esi * 4]
//		mov ecx, [edx][eax * 4]
//		cmp ebx, 0
//		jg drop_1
//		neg ebx
//		drop_1 :
//	cmp ecx, 0
//		jg drop_2
//		neg ecx
//		drop_2 :
//	cmp ebx, ecx
//		jle drop // конец сравнения по модулю
//		mov eax, esi
//		drop :
//	pop ecx
//		cmp esi, ecx
//		jne my_intern
//
//		push ecx // обмен
//		mov ecx, [edx][eax * 4]
//
//		mov ebx, [edx][edi * 4]
//		mov[edx][edi * 4], ecx
//		mov[edx][eax * 4], ebx
//		pop ecx //конец обмена
//
//		inc edi
//		cmp edi, ecx
//		jne my_extern
//}
