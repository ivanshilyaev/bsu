#include "TTree.hpp"
#include <iostream>
using namespace std;

//конструктор без параметров
TTree::TTree() { Root=NULL; }

//конструктор копирования
TTree::TTree(const TTree& Tree) { Clone(Tree); }

//клонирование
void TTree::Clone(const TTree& Tree) {
    Root=preOrderTraverse(Tree.Root);
}

//удаление
void TTree::Erase() {
    postOrderTraverse(Root);
}

//деструктор
TTree::~TTree() { Erase(); }

//оператор присваивания
const TTree& TTree::operator=(const TTree& Tree) {
    if (this==&Tree)
        return *this; //проверка на самоприсваивание
    Erase();
    Clone(Tree);
    return *this;
}

//прямой обход дерева для клонирования
TTree::TNode* TTree::preOrderTraverse(TNode* Node) {
    if (Node) {
        TNode *t=new TNode;
        t->key=Node->key;
        TNode *tLeft=preOrderTraverse(Node->Left);
        TNode *tRight=preOrderTraverse(Node->Right);
        t->Left=tLeft; t->Right=tRight;
        return t;
    }
    else return NULL;
}

//прямой обход дерева c подсчётом количества элементов
void TTree::preOrderTraverseToWrite(TNode* Node, int &length) {
    if (Node) {
        //cout<<Node->key<<endl;
        length++;
        preOrderTraverseToWrite(Node->Left, length);
        preOrderTraverseToWrite(Node->Right, length);
    }
}

//обратный обход дерева для удаления
void TTree::postOrderTraverse(TNode* Node) {
    if (Node) {
        postOrderTraverse(Node->Left);
        postOrderTraverse(Node->Right);
        delete Node;
    }
}

//концевой обход
int TTree::inOrderTraverse(void (*func) (int&), TNode *Node, T *array, int insertIndex) {
    if (Node) {
        int newInsertIndex=inOrderTraverse(func, Node->Left, array, insertIndex);
        array[newInsertIndex] = Node->key;
        func(Node->key);
        newInsertIndex = inOrderTraverse(func, Node->Right, array, newInsertIndex+1);
        return newInsertIndex;
    }
    else return insertIndex;
}

//поиск
bool TTree::Find(T value, TNode *Node, TNode* &t, int &compariosns) {
    if (Node==NULL) {
        t=NULL;
        return false;
    }
    t=Node;
    while (true)
    {
        if (t->key==value) {
            compariosns++;
            return true;
        }
        if (t->key>value) {
            compariosns++;
            if (t->Left==NULL)
                return false;
            t=t->Left;
        }
        else {
            compariosns++;
            if (t->Right==NULL)
                return false;
            t=t->Right;
        }
    }
}

//вставка
bool TTree::Insert(T value, TNode* &Node) {
    TNode *s, *q;
    int c; //число сравнений. В данном случае не нужно
    if (Find(value, Node, s, c))
        return false;
    q=new TNode;
    q->key=value; q->Left=NULL; q->Right=NULL;
    if (s==NULL)
        Node=q;
    else {
        if (s->key<value)
            s->Right=q;
        else
            s->Left=q;
    }
    return true;
}

//удаление
bool TTree::Delete(T value, TNode* &Node) {
    bool b=false;
    if (Node==NULL)
        return false;
    if (Node->key>value)
        b=Delete(value, Node->Left);
    else if (Node->key<value)
        b=Delete(value, Node->Right);
    else {
        if (Node->Left==NULL && Node->Right==NULL) {
            Node=NULL;
            delete Node;
            return true;
        }
        else if (Node->Left==NULL && Node->Right!=NULL) {
            TNode *p=Node;
            Node=Node->Right;
            delete p;
            return true;
        }
        else if (Node->Left!=NULL && Node->Right==NULL) {
            TNode *p=Node;
            Node=Node->Left;
            delete p;
            return true;
        }
        else {
            if (Node->Right->Left==NULL) {
                TNode *p=Node;
                Node->Right->Left=Node->Left;
                Node=Node->Right;
                delete p;
                return true;
            }
            else {
                TNode *t=Node->Right->Left;
                while (t->Left)
                    t=t->Left;
                int k=t->key;
                Delete(k, Node);
                Node->key=k;
                return true;
            }
        }
    }
    return b;
}
