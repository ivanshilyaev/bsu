#include<iostream>
#include <cmath>
using namespace std;

int main() {
	int n, k = 2, del(0);
	cout << "Enter an integer:" << endl;
	cin >> n;
	if (n == 1)
		cout << "No prime devisors" << endl;
	else {
		int a = sqrt(n);
		__asm {
			mov ebx, k
			mov eax, n
		lbl :
			cmp ebx, a
			jg ext
			xor edx, edx
			mov ecx, eax
			div ebx
			cmp edx, 0
			jne lbl2
			mov del, ebx
			push eax
		}
		cout << del << endl;
		__asm {
			pop eax
		lbl3 :
			xor edx, edx
			mov ecx, eax
			div ebx
			cmp edx, 0
			jne lbl2
			jmp lbl3
		lbl2 : 
			mov eax, ecx
			inc ebx
			jmp lbl
		ext :
			mov n, eax
		}
		if (n != 1)
			cout << n << endl;
	}
	system("pause");
	return 0;
}