.486
public @func3@8
.model flat
.code
@func3@8 proc
	push ebp
	mov ebp, esp

	
	mov ebx, ecx
	mov ecx, edx
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
	ret
@func3@8 endp
end
