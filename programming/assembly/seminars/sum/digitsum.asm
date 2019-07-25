.486
public @digitsum@8
.model flat
.code

	sum proc
		xor ebx, ebx
		mov ecx, 10
		cmp eax, 0
		jge cycle
		neg eax
	cycle:
		xor edx, edx
		div ecx
		add ebx, edx
		cmp eax, 0
		jg cycle
	endd:
		ret
	sum endp

@digitsum@8 proc

	xor eax, eax
	mov edi, ecx
	dec ecx

	mycycle:
		
		push eax

		inc ecx
		mov eax, ecx
		push edx
		push ecx
		call sum
		pop ecx
		pop edx

		pop eax

		cmp ebx, eax
		jl drop
		mov eax, ebx
		mov edi, ecx

	drop:

		cmp ecx, edx
		jl mycycle

		mov eax, edi

		ret
@digitsum@8 endp
end