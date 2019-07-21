#include "Book.hpp"
#include <iostream>
#include <list>
using namespace std;

void Book::getAuthors(list<Author> &list) const {
    list.clear();
    list.assign(authors.begin(), authors.end());
}
void Book::setAuthors(list<Author> &list) {
    authors.clear();
    authors.assign(list.begin(), list.end());
}

Book::Book(int num, list<Author> aut, string tite, int ye) : numberUDC(num), title(tite), year(ye)
{
    authors.assign(aut.begin(), aut.end());
    authors.sort();
}

void Book::Clone(const Book &B) {
    numberUDC=B.numberUDC;
    title=B.title;
    year=B.year;
    authors.clear();
    authors.assign(B.authors.begin(), B.authors.end());
}

Book::Book(const Book &B) { Clone(B); }

Book & Book::operator=(const Book &B) {
    if (this!=&B)
        Clone(B);
    return *this;
}

bool Book::operator<(const Book &B) const { return title.compare(B.title)<0; }

bool Book::operator>(const Book &B) const { return title.compare(B.title)>0; }

bool Book::operator==(const Book &B) const { return title.compare(B.title)==0; }

ostream & operator<<(ostream &cout, const Book &B) {
    cout<<"UDC number: "<<B.numberUDC<<endl;
    cout<<"List of authors:"<<endl;
    for (auto i:B.authors)
        cout<<i<<endl;
    cout<<"Title: "<<B.title<<endl;
    cout<<"Year of publishing: "<<B.year;
    return cout;
}
