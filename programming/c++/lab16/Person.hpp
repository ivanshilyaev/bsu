#pragma once

class Person {
protected:
    char *FIO;
    const int ID;
    static int nextID;
public:
    Person(const char *f = "Noname");
    virtual ~Person()=0;
    int getID() const;
    void setFIO(const char*);
    void getFIO(char* &) const;
    virtual void display();
};
