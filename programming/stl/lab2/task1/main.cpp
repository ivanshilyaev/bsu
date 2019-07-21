#include <iostream>
#include <set>
using namespace std;

int main() {
    int n;
    cout<<"Enter int n"<<endl;
    cin>>n;
    set<int> sieve;
    typedef set<int>::iterator iter;
    for (int i=2; i<=n; ++i)
        sieve.insert(i);
    for (iter i=sieve.begin(); i!=sieve.end(); ++i) {
        int prime=*i;
        iter del=i; del++;
        while (del!=sieve.end()) {
            if ((*del % prime)==0)
                sieve.erase(del++);
            else
                ++del;
        }
    }
    copy(sieve.begin(), sieve.end(), ostream_iterator<int>(cout, "\n"));
}
