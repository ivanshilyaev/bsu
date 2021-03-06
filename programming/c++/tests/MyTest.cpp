#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <string>
using namespace std;

const int max = 1000;
const string numbers = "1234567890";

// функция возвращает отсортироанный массив цирф, которые чаще всего встречаются в записи числа
int* getMaxDigits(int a, int& size) {
	if (a == 0) {
		int* digits = new int[1];
		digits[0] = 0;
		size = 1;
		return digits;
	}
	if (a < 0)
		a = fabs(a);
	int array[10];
	for (int i = 0; i < 10; i++)
		array[i] = 0;
	int b = a;
	while (a > 0) {
		int c = a % 10;
		array[c]++;
		a /= 10;
	}
	int max = array[0];
	for (int i = 1; i < 10; i++)
		if (array[i] > max)
			max = array[i];
	int kol = 0;
	for (int i = 0; i < 10; i++)
		if (array[i] == max)
			kol++;
	size = kol;
	int* digits = new int[kol];
	int pos = 0;
	for (int i=0; i<10; i++)
		if (array[i] == max)
			digits[pos++] = i;
	return digits;
}

int main() {
	int array[max];
	int n(0);
	string seps, line, word;
	int begin, end(0);

	ifstream fin("in1.txt", ios_base::in);
	// проверка на открытие файла
	if (!fin.is_open()) {
		cout << "Error: input file doesn't open" << endl;
		return 1;
	}

	// проверка на пустоту файла
	getline(fin, line);
	if (line.empty() && fin.eof()) {
		cout << "Error: input file is empty" << endl;
		return 1;
	}
	fin.seekg(0);

	getline(fin, seps);
	// проверка на наличие разделителей
	if (seps.empty()) {
		cout << "Error: no separators in first line of input file" << endl;
		return 1;
	}
	while (getline(fin, line)) {
		begin = 0;
		line += seps[0];
		while ((begin = (int)line.find_first_not_of(seps, begin)) != string::npos) {
			end = (int)line.find_first_of(seps, begin);
			word = line.substr(begin, end - begin);
			// В конце строки могут находиться символы, однако если строка начинается с числа, то функция stoi успешно переведёт строку в число.
			// Для того, чтобы выводить только числа, используется проверка, по которой все символы в строке начиная со второго должны быть цифрами.
			// Первый символ может быть '-' для отрицательных чисел.
			// Если же первый символ - не цифра и не знак '-', то функция stoi выбросит исключение, которое будет поймано в блоке catch.
			if (word.find_first_not_of(numbers, 1) == string::npos) {
				try {
					int a = stoi(word);
					array[n++] = a;
				}
				catch (...) {}
			}
			begin = end;
		}
	}
	fin.close();

	// проверка на наличие чисел
	if (n == 0) {
		cout << "Error: no integers in file" << endl;
		return 1;
	}
	else {
		ofstream fout("out1.txt", ios_base::out);
		for (int i = 0; i < n - 1; i++)
			fout << array[i] << " ";
		fout << array[n - 1] << endl;
		for (int i = 0; i < n; i++) {
			int size = 0;
			int* digits = getMaxDigits(array[i], size);
			fout << array[i] << ":";
			for (int j = 0; j < size - 1; j++)
				fout << digits[j] << ",";
			fout << digits[size - 1] << endl;
			delete[]digits;
		}
		cout << "Output file was created successfully" << endl;
		fout.close();
	}
		
    return 0;
}