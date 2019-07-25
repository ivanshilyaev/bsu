.486
public _sum
.model flat
.code
_sum proc
	push ebp
	mov ebp, esp

		xor eax, eax
		xor ebx, ebx
		lea edx, [ebp+12]

		mov esi, [ebp+8]
		cycle_1 :
			dec esi
			xor ecx, ecx
			cmp [edx][2 * esi], ecx
			jl drop_1
			inc eax
			add bx, [edx][2 * esi]
			cmp esi, 0
			jg cycle_1
			drop_1 :
		cmp eax, 0
			jg drop_2
			mov eax, -1
			jmp my_exit
			drop_2 :
		mov ecx, [ebp+8]
			cmp eax, ecx
			jne drop_3
			mov eax, -2
			jmp my_exit
			drop_3 :
		mov eax, ebx
			my_exit :

	pop ebp
	ret
_sum endp
end
