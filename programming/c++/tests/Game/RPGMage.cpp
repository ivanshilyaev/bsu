#include "RPGMage.hpp"
#include <iostream>

void RPGMage::setMana(int m) {
    if (m>max_mana)
        mana=max_mana;
    else
        mana=m;
}

void RPGMage::setMaxMana(int mm) {
    max_mana=mm;
    if (mana>max_mana)
        mana=max_mana;
}

RPGMage::RPGMage() : RPGCharacter(), mana(0), max_mana(0) { }

RPGMage::RPGMage(const char *name, const Race race, const Gender gender, const State state, const int age, const int health, const int max_health, const int experience, const int mana, const int max_mana) : RPGCharacter(name, race, gender, state, age, health, max_health, experience), mana(mana), max_mana(max_mana) { }

RPGMage::RPGMage(const RPGMage &c) : RPGCharacter(c) {
    mana=c.mana;
    max_mana=c.max_mana;
}

RPGMage & RPGMage::operator=(const RPGMage &c) {
    if (this!=&c) {
        Clone(c);
        mana=c.mana;
        max_mana=c.max_mana;
    }
    return *this;
}

bool RPGMage::addHealth(RPGCharacter &c, int h) {
    if (mana<2*h)
        return false;
    if (c.getHealth() + h > c.getMaxHealth())
        h=c.getMaxHealth()-c.getHealth();
    c.setHealth(c.getHealth() + h);
    setMana(mana - 2*h);
    return true;
}

ostream& operator<<(ostream &cout, const RPGMage &c) {
    cout<<(RPGCharacter&)c<<endl;
    cout<<"Mana: "<<c.mana<<endl;
    cout<<"Max mana: "<<c.max_mana;
    return cout;
}
