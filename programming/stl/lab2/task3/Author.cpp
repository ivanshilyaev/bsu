#include "Author.hpp"
#include <string>
using namespace std;

Author::Author(string s, string n, string m) : surname(s), name(n), middlename(m) {}

void Author::Clone(const Author &A) {
    surname=A.surname;
    name=A.name;
    middlename=A.middlename;
}

Author::Author(const Author &A) { Clone(A); }

Author & Author::operator=(const Author &A) {
    if (this!=&A)
        Clone(A);
    return *this;
}

bool Author::operator<(const Author &A) const { return surname.compare(A.surname)<0; }

bool Author::operator>(const Author &A) const { return surname.compare(A.surname)>0; }

bool Author::operator==(const Author &A) const { return surname.compare(A.surname)==0; }

ostream & operator<<(ostream &cout, const Author &A) {
    cout<<"Surname: "<<A.surname<<endl;
    cout<<"Name: "<<A.name<<endl;
    cout<<"Middle name: "<<A.middlename;
    return cout;
}
