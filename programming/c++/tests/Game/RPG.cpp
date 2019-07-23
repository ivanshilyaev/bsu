#include "RPG.hpp"
#include <iostream>
#include <cstring>

int RPGCharacter::nextID=1;

//конструктор без параметров
RPGCharacter::RPGCharacter(): ID(nextID++), race(human), gender(male), state(normal), age(0), health(10), max_health(100), experience(0) {}

void RPGCharacter::setHealth(int h) {
    if (h>max_health)
        health=max_health;
    else
        health=h;
    CheckState();
}

void RPGCharacter::setMaxHealth(int mh) {
    max_health=mh;
    if (health>max_health)
        health=max_health;
    CheckState();
}

void RPGCharacter::getName(char * &n) const {
    delete n;
    n=new char[strlen(n)+1];
    strcpy(n, name);
}

void RPGCharacter::CheckState() {
    if (health<0) {
        health=0;
        state=dead;
    }
    else if ((state==normal) && (health<0.1*max_health))
        state=weak;
    else if ((state==weak) && (health>=0.1*max_health))
        state=normal;
}

RPGCharacter::RPGCharacter(const char *name, const Race race, const Gender gender, const State state, const int age, const int health, const int max_health, const int experience) : ID(nextID++), race(race), gender(gender), state(state), age(age), health(health), max_health(max_health), experience(experience) {
    if (this->max_health < this->health)
        this->health = this->max_health;
    CheckState();
    this->name=new char[strlen(name)+1];
    strcpy(const_cast<char *>(this->name), name);
    //strcpy_s(const_cast<char *>(this->name), strlen(name)+1, name);
}

void RPGCharacter::Clone(const RPGCharacter &c) {
    state=c.state;
    age=c.age;
    health=c.health;
    max_health=c.max_health;
    experience=c.experience;
}

RPGCharacter::RPGCharacter(const RPGCharacter &c): ID(nextID++), race(c.race), gender(c.gender) {
    Clone(c);
    name=new char[strlen(c.name)+1];
    strcpy(const_cast<char *>(name), c.name);
    //strcpy(const_cast<char *>(name), strlen(c.name),+1, c.name);
}

bool RPGCharacter::operator<(const RPGCharacter &c) const {
    return ((experience < c.experience) && (((double)health/max_health) < ((double)c.health/c.max_health)));
}

bool RPGCharacter::operator>(const RPGCharacter &c) const {
    return c<*this;
}

RPGCharacter& RPGCharacter::operator=(const RPGCharacter &c) {
    if (this!=&c)
        Clone(c);
    return *this;
}

string WriteRace(Race race) {
    switch (race) {
        case 0:
            return "human";
            break;
        case 1:
            return "orc";
            break;
        case 2:
            return "elf";
            break;
        case 3:
            return "dwarf";
            break;
        default:
            break;
    }
}

string WriteGender(Gender gender) {
    switch (gender) {
        case 0:
            return "male";
            break;
        case 1:
            return "female";
            break;
        default:
            break;
    }
}

string WriteState(State state) {
    switch (state) {
        case 0:
            return "normal";
            break;
        case 1:
            return "weak";
            break;
        case 2:
            return "ill";
            break;
        case 3:
            return "poisoned";
            break;
        case 4:
            return "dead";
            break;
        default:
            break;
    }
}

ostream& operator<<(ostream &cout, const RPGCharacter &c) {
    cout<<"ID: "<<c.ID<<endl;
    cout<<"Name: "<<c.name<<endl;
    cout<<"Race: "<<WriteRace(c.race)<<endl;
    cout<<"Gender: "<<WriteGender(c.gender)<<endl;
    cout<<"State: "<<WriteState(c.state)<<endl;
    cout<<"Age: "<<c.age<<endl;
    cout<<"Health: "<<c.health<<endl;
    cout<<"Max health: "<<c.max_health<<endl;
    cout<<"Experience: "<<c.experience;
    return cout;
}
