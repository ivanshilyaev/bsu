#include <iostream>
#include "RPG.hpp"
#include "RPGMage.hpp"

int main() {
    RPGCharacter c("Ivan", human, male, normal, 18, 80, 100, 1000);
    cout<<c<<endl;
    cout<<endl;
    RPGMage m("Pavel", elf, male, normal, 18, 100, 100, 100, 40, 100);
    cout<<m<<endl;
    cout<<endl;
    if (m.addHealth(c, 20))
        cout<<c<<endl;
    else
        cout<<"Not enough mana"<<endl;
    cout<<endl;
}
