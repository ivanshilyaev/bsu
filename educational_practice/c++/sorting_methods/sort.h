#pragma once

typedef int T;

struct Tree {
    Tree* left;
    Tree* right;
    T key;
};

struct Tree* insert(T key, Tree* tree);

int treeToArray(Tree* tree, T* array, int insertIndex); // traverse

void freeMemory(Tree* tree);

T* treeSort(T* array, int length);