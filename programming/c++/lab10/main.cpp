#include <iostream>
using namespace std;

class ComplexNumber {
private:
    double Rez, Imz; // дейстивительная и минимая часть
public:
    ComplexNumber(double=0, double=0);
    ComplexNumber(const ComplexNumber&);
    double GetRez() const;
    double GetImz() const;
    void SetRez(double r);
    void SetImz(double i);
    ComplexNumber operator + (const ComplexNumber&) const;
    ComplexNumber operator - (const ComplexNumber&) const;
    ComplexNumber operator * (const ComplexNumber&) const;
    ComplexNumber operator / (const ComplexNumber&) const;
};

ComplexNumber::ComplexNumber(double r, double i) {
    Rez=r;
    Imz=i;
}

ComplexNumber::ComplexNumber(const ComplexNumber& n) {
    Rez=n.GetRez();
    Imz=n.GetImz();
}

double ComplexNumber::GetRez() const {
    return Rez;
}

double ComplexNumber::GetImz() const {
    return Imz;
}

void ComplexNumber::SetRez(double r) {
    Rez=r;
}

void ComplexNumber::SetImz(double i) {
    Imz=i;
}

ComplexNumber ComplexNumber::operator + (const ComplexNumber& n) const {
    ComplexNumber a(*this);
    a.SetRez(a.GetRez()+n.GetRez());
    a.SetImz(a.GetImz()+n.GetImz());
    return a;
}

ComplexNumber ComplexNumber::operator - (const ComplexNumber& n) const {
    ComplexNumber a(*this);
    a.SetRez(a.GetRez()-n.GetRez());
    a.SetImz(a.GetImz()-n.GetImz());
    return a;
}

ComplexNumber ComplexNumber::operator * (const ComplexNumber& n) const {
    ComplexNumber a(*this);
    double x1=a.GetRez();
    double y1=a.GetImz();
    double x2=n.GetRez();
    double y2=n.GetImz();
    a.SetRez(x1*x2-y1*y2);
    a.SetImz(x1*y2+x2*y1);
    return a;
}

ComplexNumber ComplexNumber::operator / (const ComplexNumber& n) const {
    ComplexNumber a(*this);
    double x1=a.GetRez();
    double y1=a.GetImz();
    double x2=n.GetRez();
    double y2=n.GetImz();
    if ((x2==0) && (y2==0))
        throw "Division by zero";
    a.SetRez((x1*x2+y1*y2)/(x2*x2+y2*y2));
    a.SetImz((x2*y1-x1*y2)/(x2*x2+y2*y2));
    return a;
}

int main() {
    ComplexNumber n1(3, -2);
    ComplexNumber n2(2, 5);
    
    ComplexNumber n3=n1+n2;
    //сумма
    if (n3.GetImz()>=0)
        cout<<"n1+n2="<<n3.GetRez()<<"+"<<n3.GetImz()<<"i"<<endl;
    else
        cout<<"n1+n2="<<n3.GetRez()<<n3.GetImz()<<"i"<<endl;
    
    n3=n1-n2;
    //разность
    if (n3.GetImz()>=0)
        cout<<"n1+n2="<<n3.GetRez()<<"+"<<n3.GetImz()<<"i"<<endl;
    else
        cout<<"n1+n2="<<n3.GetRez()<<n3.GetImz()<<"i"<<endl;
    
    n3=n1*n2;
    //умножение
    if (n3.GetImz()>=0)
        cout<<"n1+n2="<<n3.GetRez()<<"+"<<n3.GetImz()<<"i"<<endl;
    else
        cout<<"n1+n2="<<n3.GetRez()<<n3.GetImz()<<"i"<<endl;
    
    n3=n1/n2;
    //деление
    if (n3.GetImz()>=0)
        cout<<"n1+n2="<<n3.GetRez()<<"+"<<n3.GetImz()<<"i"<<endl;
    else
        cout<<"n1+n2="<<n3.GetRez()<<n3.GetImz()<<"i"<<endl;
    
    return 0;
}
