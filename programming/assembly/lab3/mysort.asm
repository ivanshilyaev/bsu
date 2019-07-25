.486
public _mysort
.model flat
.code
_mysort proc
		push ebp
		mov ebp, esp

		mov ecx, [ebp+8]
		dec ecx
		mov edx, [ebp+12]
		xor edi, edi
		xor esi, esi

	my_extern:
		mov eax, edi
		mov esi, edi
	my_intern:
		inc esi
		push ecx
		mov ebx, [edx][esi * 4]
		mov ecx, [edx][eax * 4]
		cmp ebx, 0
		jg drop_1
		neg ebx
	drop_1:
		cmp ecx, 0
		jg drop_2
		neg ecx
	drop_2:
		cmp ebx, ecx
		jle drop
		mov eax, esi
	drop:
		pop ecx
		cmp esi, ecx
		jne my_intern

		push ecx
		mov ecx, [edx][eax * 4]

		mov ebx, [edx][edi*4]
		mov [edx][edi * 4], ecx
		mov [edx][eax * 4], ebx
		pop ecx

		inc edi
		cmp edi, ecx
		jne my_extern

		mov esp, ebp
		pop ebp
		ret
_mysort endp
end