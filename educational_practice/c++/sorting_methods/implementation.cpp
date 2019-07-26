#include <iostream>
#include "sort.h"

using namespace std;

struct Tree* insert(T key, Tree* tree) {
    if (tree==nullptr) {
        tree=new Tree;
        tree->key=key;
        tree->left=nullptr;
        tree->right=nullptr;
    }
    else
    if (key < tree->key)
        tree->left = insert(key, tree->left);
    else
        tree->right = insert(key, tree->right);
    return tree;
}

int treeToArray(Tree* tree, T* array, int insertIndex) {
    if (tree != nullptr) {
        int newInsertIndex = treeToArray(tree->left, array, insertIndex);
        array[newInsertIndex] = tree->key;
        newInsertIndex = treeToArray(tree->right, array, newInsertIndex + 1);
        return newInsertIndex;
    }
    else return insertIndex;
}

void freeMemory(Tree* tree) {
    if (tree!=nullptr) {
        freeMemory(tree->left);
        freeMemory(tree->right);
        delete tree;
    }
}

T* treeSort(T* array, int length) {
    struct Tree* tree = nullptr;

    T* sortedArray = new T[length];
    cout << "Построение дерева:" << endl;
    for (int i = 0; i < length; i++) {
        tree = insert(array[i], tree);
        treeToArray(tree, sortedArray, 0);
        for (int j=0; j<i; j++)
            cout << sortedArray[j] << " ";
        cout << endl;
    }

    treeToArray(tree, sortedArray, 0);

    freeMemory(tree);
    return sortedArray;
}