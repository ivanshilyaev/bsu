#include "Author.hpp"
#include "Book.hpp"
#include <iostream>
#include <fstream>
#include <string>
#include <list>
#include <algorithm>
using namespace std;

// 1. Во входном файле в каждой строке находится информация об одной записи
// 2. Все поля перечислены через запятую или точку с запятой
// 3. "удаление книги" - Реализовано удаление книги по имени
// 4. "поиск книг по названию" - Вывод книги с указанным названием
// 5. "поиск книг указанного автора" - Вывод всех книг указанного автора

// сравнение строк без учёта регистра
bool iequals(const string& a, const string& b) {
    return std::equal(a.begin(), a.end(), b.begin(), b.end(), [](char a, char b)->bool {
                          return tolower(a) == tolower(b);
                      });
}

// 1 - добавление книги
void addBook(list<Book> &library, Book &book) {
    library.push_back(book);
    library.sort();
}

// 2 - удаление книги (по названию)
bool deleteBook(list<Book> &library, string &title) {
    bool found=false;
    list<Book>::iterator iter;
    for (iter=library.begin(); iter!=library.end(); ++iter) {
        if (iequals((*iter).getTitle(), title)) {
            library.erase(iter++);
            found=true;
        }
    }
    return found;
}

// 3 - поиск книг по названию
bool searchByTitle(list<Book> &library, string &title) {
    bool found=false;
    list<Book>::iterator iter;
    for (iter=library.begin(); iter!=library.end(); ++iter) {
        if (iequals((*iter).getTitle(), title)) {
            cout<<*iter<<endl;
            found=true;
        }
    }
    return found;
}

// 4 - поиск книг указанного автора
bool searchByAuthor(list<Book> &library, string &surname, string &name, string &middlename) {
    bool found=false;
    list<Book>::iterator iter;
    for (iter=library.begin(); iter!=library.end(); ++iter) {
        list<Author> aList;
        (*iter).getAuthors(aList);
        for (auto elem:aList) {
            if (iequals(elem.getSurname(), surname) && iequals(elem.getName(), name) && iequals(elem.getMiddlename(), middlename)) {
                cout<<*iter<<endl<<endl;
                found=true;
            }
        }
    }
    return found;
}

// 5 - добавление автора указанной книги (по названию)
bool addAuthor(list<Book> &library, string title, Author &author) {
    bool found=false;
    list<Book>::iterator iter;
    for (iter=library.begin(); iter!=library.end(); ++iter) {
        if (iequals((*iter).getTitle(), title)) {
            list<Author> aList;
            (*iter).getAuthors(aList);
            aList.push_back(author);
            aList.sort();
            (*iter).setAuthors(aList);
            found=true;
        }
    }
    return found;
}

// 6 - удаления автора указанной книги (по названию)
bool deleteAuthor(list<Book> &library, string title, Author &author) {
    bool found=false;
    list<Book>::iterator iter;
    for (iter=library.begin(); iter!=library.end(); ++iter) {
        if (iequals((*iter).getTitle(), title)) {
            list<Author> aList;
            (*iter).getAuthors(aList);
            list<Author>::iterator iter2;
            for (iter2=aList.begin(); iter2!=aList.end(); ++iter2) {
                if (iequals((*iter2).getSurname(), author.getSurname()) &&
                    iequals((*iter2).getName(), author.getName()) &&
                    iequals((*iter2).getMiddlename(), author.getMiddlename())) {
                    aList.erase(iter2++);
                    found=true;
                }
            }
            (*iter).setAuthors(aList);
        }
    }
    return found;
}

int main() {
    list<Book> library;
    try {
        fstream fin("input.txt", ios_base::in);
        if (!fin.is_open())
            throw "File doesn't open";
        string line;
        fin>>line;
        if (fin.eof())
            throw "File is empty";
        fin.seekg(0);
        int begin(0), end(0), b2(0), e2(0);
        string word, w2, array[3], seps=",;";
        //считывание из файла построчно
        while (getline(fin, line)) {
            if (line=="")
                continue;
            begin=0; end=0;
            line.append(",");
            //выделение номера, названия и года издания
            for (int i=0; i<3; ++i) {
                (begin=(int)line.find_first_not_of(seps, begin));
                end=(int)line.find_first_of(seps, begin);
                word=line.substr(begin, end-begin);
                array[i]=word;
                begin=end;
            }
            int number=stoi(array[0]);
            string title=array[1].substr(1); //убираем пробел вначале
            int year=stoi(array[2].substr(1));
            
            //выделение списка авторов
            list<Author> authors;
            while ((begin=(int)line.find_first_not_of(seps, begin)) != string::npos) {
                end=(int)line.find_first_of(seps, begin);
                word=line.substr(begin, end-begin);
                
                b2=0; e2=0;
                //разбиение ФИО на лексемы
                for (int i=0; i<3; ++i) {
                    word+=" ";
                    (b2=(int)word.find_first_not_of(" ", b2));
                    e2=(int)word.find_first_of(" ", b2);
                    w2=word.substr(b2, e2-b2);
                    array[i]=w2;
                    b2=e2;
                }
                Author author(array[0], array[1], array[2]);
                authors.push_back(author);
                
                begin=end;
            }
            
            Book book(number, authors, title, year);
            //авторы сортируются в конструкторе при создании книги
            library.push_back(book);
        }
        fin.close();
        
        library.sort(); //сортировка книг по названию
        cout<<"All books:"<<endl;
        for (auto book:library)
            cout<<book<<endl<<endl;
        
        cout<<"Menu"<<endl;
        cout<<"1 - add a book"<<endl;
        cout<<"2 - delete a book (by name)"<<endl;
        cout<<"3 - search for a book by title"<<endl;
        cout<<"4 - search for a book by author"<<endl;
        cout<<"5 - add an author to a book"<<endl;
        cout<<"6 - delete an author from a book"<<endl;
        cout<<"7 - print all books"<<endl;
        cout<<"8 - exit"<<endl;
        while (true) {
            cout<<"•Enter a number:"<<endl;
            string s;
            cin>>s;
            int choice=stoi(s);
            if (choice<1 || choice>8)
                continue;
            switch (choice) {
                case 1: {
                    string s;
                    int number, n, year;
                    string surname, name, middlename, title;
                    cout<<"UDC number: ";
                    cin>>s; number=stoi(s);
                    cout<<"How many authors would you like to add?"<<end;
                    cin>>s; n=stoi(s);
                    cin>>n;
                    if (n<1) {
                        do {
                            cout<<"Error. Try again"<<endl;
                            cin>>s; n=stoi(s);
                        } while (n<1);
                    }
                    list<Author> authors;
                    for (int i=0; i<n; ++i) {
                        cout<<"Surname: ";
                        cin>>surname;
                        cout<<"Name: ";
                        cin>>name;
                        cout<<"Middle name: ";
                        cin>>middlename;
                        Author author(surname, name, middlename);
                        authors.push_back(author);
                    }
                    cout<<"Title: ";
                    while (getchar()!='\n');
                    getline(cin, title);
                    cout<<"Year of publishing: ";
                    cin>>s; year=stoi(s);
                    Book book(number, authors, title, year);
                    addBook(library, book);
                    break;
                }
                case 2: {
                    string title;
                    cout<<"Enter title of the book yoe'd like to delete:"<<endl;
                    while (getchar()!='\n');
                    getline(cin, title);
                    if (!deleteBook(library, title))
                        cout<<"No books with such title!"<<endl;
                    break;
                }
                case 3: {
                    string title;
                    cout<<"Enter title of the book yoe'd like to find:"<<endl;
                    while (getchar()!='\n');
                    getline(cin, title);
                    cout<<"A book with such title:"<<endl;
                    if (!searchByTitle(library, title))
                        cout<<"No books with such title!"<<endl;
                    break;
                }
                case 4: {
                    cout<<"Enter name of the author:"<<endl;
                    string surname, name, middlename;
                    cout<<"Surname: ";
                    cin>>surname;
                    cout<<"Name: ";
                    cin>>name;
                    cout<<"Middle name: ";
                    cin>>middlename;
                    cout<<"Books written by this author:"<<endl;
                    if (!searchByAuthor(library, surname, name, middlename))
                        cout<<"No books written by this author!"<<endl;
                    break;
                }
                case 5: {
                    string title, surname, name, middlename;
                    cout<<"Enter title of the book:"<<endl;
                    while (getchar()!='\n');
                    getline(cin, title);
                    cout<<"Enter name of the author:"<<endl;
                    cout<<"Surname: ";
                    cin>>surname;
                    cout<<"Name: ";
                    cin>>name;
                    cout<<"Middle name: ";
                    cin>>middlename;
                    Author author(surname, name, middlename);
                    if (!addAuthor(library, title, author))
                        cout<<"No books with such title!"<<endl;
                    break;
                }
                case 6: {
                    string title, surname, name, middlename;
                    cout<<"Enter title of the book:"<<endl;
                    while (getchar()!='\n');
                    getline(cin, title);
                    cout<<"Enter name of the author:"<<endl;
                    cout<<"Surname: ";
                    cin>>surname;
                    cout<<"Name: ";
                    cin>>name;
                    cout<<"Middle name: ";
                    cin>>middlename;
                    Author author(surname, name, middlename);
                    if (!deleteAuthor(library, title, author))
                        cout<<"No books with such title or author!"<<endl;
                    break;
                }
                case 7:
                    for (auto book:library)
                        cout<<book<<endl<<endl;
                    break;
                case 8:
                    throw "The end!";
                    break;
                default:
                    break;
            }
        }
    }
    catch (const char *e) {
        cout<<e<<endl;
    }
    catch (...) {
        cout<<"Error"<<endl;
    }
}
