#pragma once
#include "RPG.hpp"

class RPGMage: public RPGCharacter {
private:
    int mana;
    int max_mana;
protected:
    RPGMage();
public:
    RPGMage(const char *name, const Race race, const Gender gender, const State state, const int age, const int health, const int max_health, const int experience, const int mana, const int max_mana);
    explicit RPGMage(const RPGMage &);
    void setMana(int m);
    void setMaxMana(int mm);
    
    int getMana() { return mana; }
    int getMaxMana() { return max_mana; }
    
    RPGMage & operator=(const RPGMage &);
    friend ostream& operator<<(ostream &o, const RPGMage &);
    
    bool addHealth(RPGCharacter &c, int h);
};
