#pragma once
#include <iostream>
using namespace std;

typedef int T;

class TTree {
protected:
    void Clone(const TTree&); //клонирование
    void Erase();
public:
    struct TNode{
        T key;
        TNode *Left;
        TNode *Right;
    };
    TNode *Root;
    
    TTree(); //конструктор без параметров
    ~TTree();
    TTree(const TTree&); //конструктор копирования
    const TTree& operator=(const TTree& Tree); //оператор присваивания
    TNode* preOrderTraverse(TNode*); //прямой обход дерева для клонирования
    void preOrderTraverseToWrite(TNode*, int &length); //прямой обход дерева c подсчётом количества элементов
    void postOrderTraverse(TNode* Node); //обратный обход дерева для удаления
    int inOrderTraverse(void (*func) (int&), TNode* Node, T *array, int insertIndex); //концевой обход
    bool Find(T, TNode*, TNode* &, int&); //поиск
    bool Insert(T, TNode* &); //вставка
    bool Delete(T, TNode* &); //удаление
};
