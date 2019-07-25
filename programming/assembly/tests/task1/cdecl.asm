.486
public _sum
.model flat
.code
_sum proc
	push ebp
	mov ebp, esp

	mov eax, [ebp+8]
	mov ecx, [ebp+12]
cycle_1:
	mov ebx, [eax]
	cmp ebx, 0
	jl drop_1
	add eax, 4
	loop cycle_1

drop_1 :
	add eax, 4
	dec ecx
	xor ebx, ebx
	xor edx, edx
cycle_2:
	push ebx
	mov ebx, [eax]
	cmp ebx, 0
	jl drop_2
	pop ebx
	add edx, [eax]
	jmp drop_3
drop_2:
	pop ebx
	add ebx, edx
	xor edx, edx
	add edx, [eax]
drop_3:
	add eax, 4
	loop cycle_2

	mov eax, ebx

	pop ebp
	ret
_sum endp
end
