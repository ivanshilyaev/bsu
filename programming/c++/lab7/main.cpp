#include <iostream>
#include <cmath>
#include <string>
using namespace std;

// 1. Максимальная доля гласных букв - кол-во гласных / кол-во символов в слове

// альтернативы:
// 2. Максимальная доля гласных букв - кол-во гласных / кол-во согласных
// 3. Максимальная доля гласных букв - макс кол-во гласных в слове

int main() {
    setlocale(LC_ALL, ".1251");
    string s, result, vowels="aeiouyAEIOUY", word;
    string alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int begin(0), end(0), kol, b(0);
    double part(0), c_part;
    printf("Введите строку:\n");
    getline(cin, s);
    if (s.empty()) {
        printf("Строка пустая!\n");
        return 1;
    }
    s=s+" ";
    while ((begin=(int)s.find_first_of(alphabet, begin)) != string::npos) { // string::npos == -1
        end = (int)s.find_first_not_of(alphabet, begin);
        word = s.substr(begin, end-begin);
        kol=0;
        b=0;
        
        // прохождние по слову через функцию
        while ((b=(int)word.find_first_of(vowels, b)) != string::npos) {
            kol++;
            b++;
        }
        
        // посимвольное прохождение по слову
        // for (int i=0; i<word.length(); i++)
        //     if (vowels.find(word[i]) != string::npos)
        //         kol++;
        
        //c_part=round( ((double)kol/word.length()) * 1000.0 ) / 1000.0; // округление до трёх знаков после запятой
        
        c_part = (double)kol/word.length();
        if (c_part>part) {
            result.clear();
            part=c_part;
            result.append(word);
            result.append(" ");
        }
        else if (c_part==part) {
            result.append(word);
            result.append(" ");
        }
        begin=end;
    }
    if (result.empty()) {
        printf("Нет слов в строке!\n");
        return 1;
    }
    result.pop_back(); // удаляем последний пробел
    printf("%s\n", result.c_str());
    
    return 0;
}
