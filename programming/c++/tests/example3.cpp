#include <iostream>
#include <fstream>
using namespace std;

// 3. В текстовом файле в первой строке перечислены разделители слов, в
// следующих строках записан текст. В каждой строке файла поменять
// местами слова максимальной и минимальной длины, состоящие только из
// латинских букв.

// Test
//.,!?
//Apple Inc. is an American multinational technology company headquartered in
//Cupertino, California, that designs, develops, and sells consumer electronics,
//computer software, and online services. The company's hardware products include
//the iPhone smartphone, the iPad tablet computer, the Mac personal computer,
//the iPod portable media player, the Apple Watch smartwatch, the Apple TV digital
//media player, and the HomePod smart speaker. Apple's software includes the macOS
//and iOS operating systems, the iTunes media player, the Safari web browser, and
//the iLife and iWork creativity and productivity suites, as well as
//professional applications like Final Cut Pro, Logic Pro, and Xcode.
//Its online services include the iTunes Store, the iOS App Store and Mac App Store,
//Apple Music, and iCloud.

int main() {
    const string alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    string line, seps, word, min, max;
    int minpos, maxpos, minlen, maxlen, begin, end(0), linecounter(1);
    
    ifstream fin("input.txt", ios_base::in);
    ofstream fout("output.txt", ios_base::out);
    
    if (!fin.is_open()) {
        cout<<"Error"<<endl;
        return 1;
    }
    
    getline(fin, line);
    if (line.empty() && fin.eof()) {
        cout<<"Error"<<endl;
        return 1;
    }
    fin.seekg(0);
    
    // ?? Зачем нужны разделители, если слова только из латинских букв?
    getline(fin, seps);
    while (getline(fin, line)) {
        begin=0;
        min=""; max="";
        minlen=INT_MAX; maxlen=0;
        minpos=-1; maxpos=-1;
        line+=seps[0];
        while ((begin=(int)line.find_first_of(alphabet, begin)) != string::npos) {
            end=(int)line.find_first_not_of(alphabet, begin);
            word=line.substr(begin, end-begin);
            if (word.length()<minlen) {
                min=word;
                minlen=(int)word.length();
                minpos=begin;
            }
            else if (word.length()>maxlen && word.length()!=0) {
                max=word;
                maxlen=(int)word.length();
                maxpos=begin;
            }
            begin=end;
        }
        if (minpos==-1) {
            cout<<"Error: can't find word with min length in line "<<linecounter<<endl;
        }
        else if (maxpos==-1) {
            cout<<"Error: can't find word with max length in line "<<linecounter<<endl;
        }
        else if (end==0)
            cout<<"Error: no words in line "<<linecounter<<endl;
        else {
            if (minpos>maxpos) {
                line.erase(minpos, min.length());
                line.insert(minpos, max);
                line.erase(maxpos, max.length());
                line.insert(maxpos, min);
            }
            else if (minpos<maxpos) {
                line.erase(maxpos, max.length());
                line.insert(maxpos, min);
                line.erase(minpos, min.length());
                line.insert(minpos, max);
            }
            else cout<<"Error: all words have equal length in line"<<linecounter<<endl;
        }
        linecounter++;
        fout<<line<<endl;
    }
    
    fin.close();
    fout.close();
}
