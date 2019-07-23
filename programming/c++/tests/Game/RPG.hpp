#pragma once
#include <string>
using namespace std;

enum Race { human, orc, elf, dwarf };
string WriteRace(Race race);

enum Gender { male, female };
string WriteGender(Gender gender);

enum State { normal, weak, ill, poisoned, dead };
string WriteState(State state);

class RPGCharacter {
private:
    const int ID;
    const char *name;
    const Race race;
    const Gender gender;
    State state;
    int age;
    int health;
    int max_health;
    int experience;
protected:
    RPGCharacter(); //запрет вызова конструктора по умолчанию для пользователя
    void Clone(const RPGCharacter &c);
    static int nextID;
    void CheckState(); //изменение статуса
public:
    RPGCharacter(const char *name, const Race race, const Gender gender, const State state, const int age, const int health, const int max_health, const int experience);
    
    explicit RPGCharacter(const RPGCharacter &c); //запрет вызова КК через присваивание
    
    void setState(State s) { state=s; }
    void setAge(int a) { age=a; }
    void setHealth(int h);
    void setMaxHealth(int mh);
    void setExperience(int e) { experience=e; }
    
    int getID() const { return ID; }
    void getName(char * &) const;
    Race getRace() const { return race; }
    Gender getGender() const { return gender; }
    State getState() const { return state; }
    int getAge() const { return age; }
    int getHealth() const { return health; }
    int getMaxHealth() const { return max_health; }
    int getExperience() const { return experience; }
    
    bool operator<(const RPGCharacter &) const;
    bool operator>(const RPGCharacter &) const;
    friend ostream& operator<<(ostream &, const RPGCharacter &);
    virtual RPGCharacter & operator=(const RPGCharacter &);
};
