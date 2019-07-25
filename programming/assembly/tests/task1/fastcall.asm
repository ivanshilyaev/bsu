.486
public @sum@8
.model flat
.code
@sum@8 proc

	mov eax, ecx 
	mov ecx, edx
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

	ret

@sum@8 endp
end
