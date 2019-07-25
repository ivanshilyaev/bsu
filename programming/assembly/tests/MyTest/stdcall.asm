.486
public _func2@8
.model flat
.code
_func2@8 proc
	push ebp
	mov ebp, esp

	
	mov ebx, [ebp+8]
	mov ecx, [ebp+12]
	xor esi, esi
	xor edi, edi
	dec edi
cycle_1:
	push ecx
	mov eax, [ebx + 4 * esi]
	mov ecx, 2
	xor edx, edx
	div ecx
	cmp edx, 0
	jne drop_1
	mov ecx, esi
	push esi
cycle_2:
	dec ecx
	cmp ecx, edi
	je drop_2
	mov eax, [ebx + 4 * esi]
	mov edx, [ebx + 4 * ecx]
	mov [ebx + 4 * esi], edx
	mov [ebx + 4 * ecx], eax
	dec esi
	jmp cycle_2

drop_2:
	inc edi
	pop esi
drop_1:
	inc esi
	pop ecx
	loop cycle_1
	

	mov esp, ebp
	pop ebp
	ret 8
_func2@8 endp
end
