#include <iostream>
using namespace std;

int main() {
	int arr[] = { 1, 3, 5, 7, 5, 8, 1, 1, 3, 8, 8, 8 };
	const int n = 12;
	int m = n - 1;

	// part 1 - sort
	__asm {
		mov ebx, 0
	out1:
		cmp ebx, m
		je ext_out1
		mov ecx, ebx
		inc ecx
	in1:
		cmp ecx, n
		je ext_in1
		xor edx, edx
		add edx, arr[4 * ecx]
		cmp arr[4 * ebx], edx
		jle lbl1
		xor eax, eax
		add eax, arr[4 * ebx]
		mov arr[4 * ebx], edx
		mov arr[4 * ecx], eax
	lbl1:
		inc ecx
		jmp in1
	ext_in1:
		inc ebx
		jmp out1
	ext_out1:
	}
	
	// part 2 - make second array
	int brr[n];
	for (int i = 0; i < n; ++i)
		brr[i] = 0;
	_asm {
		mov ebx, 0
	out2:
		cmp ebx, n
		je ext_out2
		mov edx, ebx
	in2:
		xor eax, eax
		add eax, arr[4 * edx]
		cmp eax, arr[4 * edx + 4]
		jne ext_in2
		cmp edx, m
		je ext_in2
		inc edx
		jmp in2

	ext_in2:

		mov ecx, ebx
		mov eax, edx
		sub eax, ebx
		inc eax
	in3:
		cmp ecx, edx
		jg ext_in3
		mov brr[4 * ecx], eax
		inc ecx
		jmp in3
	ext_in3:
		mov ebx, edx
		inc ebx
		jmp out2
	ext_out2:

	}

	// part 3 - sort of two arrays
	__asm {
		mov ebx, 0
	out3:
		cmp ebx, m
		je ext_out3
		mov ecx, ebx
		inc ecx
	in4 :
		cmp ecx, n
		je ext_in4
		xor edx, edx
		add edx, brr[4 * ecx]
		cmp brr[4 * ebx], edx
		jge lbl2

		xor eax, eax
		add eax, brr[4 * ebx]
		mov brr[4 * ebx], edx
		mov brr[4 * ecx], eax

		xor edx, edx
		add edx, arr[4 * ecx]
		xor eax, eax
		add eax, arr[4 * ebx]
		mov arr[4 * ebx], edx
		mov arr[4 * ecx], eax
	lbl2 :
		inc ecx
		jmp in4
	ext_in4 :
		inc ebx
		jmp out3
	ext_out3:
	}

	for (int i = 0; i < n; ++i)
		cout << arr[i]<<" "<<brr[i] << endl;

	system("pause");
	return 0;
}






//int main() {
//    int arr[] = {1, 3, 5, 7, 5, 8, 1, 1, 3, 8, 8, 8};
//    int n = sizeof(arr)/sizeof(*arr);
//    for (int i=0; i<n-1; ++i) {
//        for (int j=i+1; j<n; ++j) {
//            if (arr[i]>arr[j]) {
//                int tmp=arr[i]; arr[i]=arr[j]; arr[j]=tmp;
//            }
//        }
//    }
//    int brr[n];
//    int i=0;
//    while (i<n) {
//        int k=i;
//        while (arr[k]==arr[k+1] && k<n-1)
//            k++;
//        for (int j=i; j<=k; ++j)
//            brr[j]=k-i+1;
//        i=k+1;
//    }
//    for (int i=0; i<n-1; ++i) {
//        for (int j=i+1; j<n; ++j) {
//            if (brr[i]<brr[j]) {
//                int tmp=arr[i]; arr[i]=arr[j]; arr[j]=tmp;
//                tmp=brr[i]; brr[i]=brr[j]; brr[j]=tmp;
//            }
//        }
//    }
//    for (int i=0; i<n; ++i)
//        cout<<arr[i]<<endl;
//}