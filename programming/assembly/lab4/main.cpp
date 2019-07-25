#include <iostream>
#include <cstring>
using namespace std;

int main() {
	setlocale(LC_ALL, ".1251");
	const int n = 20;
	char s1[n] = "аауубббеееоппёяяяиыэ";
	char s2[n] = "";

	_asm {
		xor esi, esi
		xor edi, edi
		mov ecx, n
		dec ecx
		mov edx, ecx
		dec edx
		dec edi

	cycle:
		inc edi
		mov al, s1[esi]
		mov s2[edi], al
		
		mov ebx, esi // j
		inc ebx
		jmp check_vw
	checked:
		dec ebx
	intern:
		inc ebx
		cmp s1[ebx], al
		jne exit_1
		cmp ebx, ecx
		jl intern
	exit_1:
		mov esi, ebx

		cmp esi, edx
		jl cycle

		mov al, s1[esi] // обработка последнего одиночного символа
		cmp al, s1[esi - 1]
		je exit_2
		inc edi
		mov s2[edi], al

	exit_2:
	}

	cout << s2 << endl;

	system("pause");
	return 0;

	// проверка на принадлежность гласным буквам
	_asm {
	check_vw :
		cmp al, 0e0h // а
		je checked

		cmp al, 0e5h // е
		je checked

		cmp al, 0b8h // ё
		je checked

		cmp al, 0e8h // и
		je checked

		cmp al, 0eeh // о
		je checked

		cmp al, 0f3h // у
		je checked

		cmp al, 0fbh // ы
		je checked

		cmp al, 0fdh // э
		je checked

		cmp al, 0feh // ю 
		je checked

		cmp al, 0ffh // я
		je checked

		jmp exit_1
	}
}



//int main() {
//	setlocale(LC_ALL, ".1251");
//	char st[10] = "ааууеееео";
//	int n = 9;
//	int i(0);
//	while (i < n - 1) {
//		cout << st[i];
//		int j = i + 1;
//		while (j < n && (st[i] == st[j]))
//			++j;
//		i = j;
//	}
//	if (st[n - 1] != st[n - 2])
//		cout << st[n - 1];
//	cout << endl;
//	system("pause");
//	return 0;
//}
