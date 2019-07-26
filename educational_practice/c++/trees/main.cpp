#include <iostream>
using namespace std;

struct Tree {
    int key;
    Tree* left;
    Tree* right;
};

// поиск
bool find(int elem, Tree* tree, Tree* &t) {
    if (tree==NULL) {
        t=NULL;
        return false;
    }
    t=tree;
    while (true)
    {
        if (t->key==elem)
            return true;
        if (t->key>elem) {
            if (t->left==NULL)
                return false;
            t=t->left;
        }
        else {
            if (t->right==NULL)
                return false;
            t=t->right;
        }
    }
}

// вставка
bool insert(int elem, Tree* &tree) {
    Tree *s, *q;
    if (find(elem, tree, s))
        return false;
    q=new Tree;
    q->key=elem; q->left=NULL; q->right=NULL;
    if (s==NULL)
        tree=q;
    else {
        if (s->key<elem)
            s->right=q;
        else
            s->left=q;
    }
    return true;
}

// удаление вершины по значению
bool remove(int elem, Tree* &tree) {
    bool b=false;
    if (tree==NULL)
        return false;
    if (tree->key>elem)
        b=remove(elem, tree->left);
    else if (tree->key<elem)
        b=remove(elem, tree->right);
    else {
        if (tree->left==NULL && tree->right==NULL) {
            tree=NULL;
            delete tree;
            return true;
        }
        else if (tree->left==NULL && tree->right!=NULL) {
            Tree* p=tree;
            tree=tree->right;
            delete p;
            return true;
        }
        else if (tree->left!=NULL && tree->right==NULL) {
            Tree* p=tree;
            tree=tree->left;
            delete p;
            return true;
        }
        else {
            if (tree->right->left==NULL) {
                Tree* p=tree;
                tree->right->left=tree->left;
                tree=tree->right;
                delete p;
                return true;
            }
            else {
                Tree* t=tree->right->left;
                while (t->left)
                    t=t->left;
                int k=t->key;
                remove(k, tree);
                tree->key=k;
                return true;
            }
        }

    }
    return b;
}

// концевой обход (сортировка)
void traverse(Tree* tree) {
    if (tree->left)
        traverse(tree->left);
    cout<<tree->key<<" ";
    if (tree->right)
        traverse(tree->right);
}

void printArray(int* array, int n) {
    for (int i=0; i<n; i++)
        cout<<array[i]<<" ";
    cout<<endl;
}

int main() {
    //create
    int n=0;
    cout<<"Enter n - number of elements"<<endl;
    cin>>n;
    int* array=new int[n];
    srand(time(NULL));
    for (int i=0; i<n; i++)
        array[i]=-50+rand()%101;
    cout<<"Original array:"<<endl;
    printArray(array, n);
    Tree* tree=NULL;
    cout<<"Adding element to the tree..."<<endl;
    for (int i=0; i<n; i++) {
        if (!insert(array[i], tree))
            cout<<"Error: element already exists"<<endl;
        traverse(tree);
        cout<<endl;
    }
    cout<<"Sorted tree:"<<endl;
    traverse(tree);
    cout<<endl;
    
    //insert
    int elemToInsert;
    cout<<"Enter element you'd like to insert"<<endl;
    cin>>elemToInsert;
    cout<<"Adding element to the tree..."<<endl;
    if (!insert(elemToInsert, tree))
        cout<<"Error: element already exists"<<endl;
    else {
        cout<<"Modified tree:"<<endl;
        traverse(tree);
        cout<<endl;
    }
    
    //remove
    int elemToRemove;
    cout<<"Enter element you'd like to remove"<<endl;
    cin>>elemToRemove;
    cout<<"Removing element from the tree..."<<endl;
    if (!remove(elemToRemove, tree))
        cout<<"Error: no such element"<<endl;
    else {
        cout<<"Modified tree:"<<endl;
        traverse(tree);
        cout<<endl;
    }
}
