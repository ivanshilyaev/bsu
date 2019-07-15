#include <iostream>
#include "TTree.hpp"
#include <fstream>
using namespace std;

//callback-функция
void doSmt(int &i) {
    i++;
    i--;
}

int main() {
    TTree Tree;
    Tree.Insert(14, Tree.Root);
    Tree.Insert(7, Tree.Root);
    Tree.Insert(21, Tree.Root);
    //вывод первого дерева
    int length=0;
    Tree.preOrderTraverseToWrite(Tree.Root, length);
    T *array=new T[length];
    Tree.inOrderTraverse(*doSmt, Tree.Root, array, 0);
    cout<<"First array:"<<endl;
    for (int i=0; i<length; i++)
        cout<<array[i]<<endl;

    TTree Tree2;
    Tree2=Tree;
    //вывод второго дерева
    int length2=0;
    Tree2.preOrderTraverseToWrite(Tree2.Root, length2);
    T *array2=new T[length2];
    Tree2.inOrderTraverse(*doSmt, Tree2.Root, array2, 0);
    cout<<"Second array:"<<endl;
    for (int i=0; i<length2; i++)
        cout<<array2[i]<<endl;
}


//int main() {
//    TTree *Tree=new TTree;
//    ifstream fin("input.txt", ios_base::in);
//    ofstream fout("output.txt", ios_base::out);
//    if (!fin.is_open()) {
//        cout<<"Input file doesn't open"<<endl;
//        return 1;
//    }
//    else if (fin.eof()) {
//        cout<<"Input file is empty"<<endl;
//        return 1;
//    }
//    else if (!fout.is_open()) {
//        cout<<"Output file doesn't open"<<endl;
//        return 1;
//    }
//    char c;
//    while (fin>>c) {
//        T value; bool b;
//        switch (c) {
//            case 'I':
//            {
//                fin>>value;
//                fout<<value<<": ";
//                b=Tree->Insert(value, Tree->Root);
//                if (b)
//                    fout<<"inserted"<<endl;
//                else
//                    fout<<"not inserted"<<endl;
//                break;
//            }
//            case 'D':
//            {
//                fin>>value;
//                fout<<value<<": ";
//                b=Tree->Delete(value, Tree->Root);
//                if (b)
//                    fout<<"deleted"<<endl;
//                else
//                    fout<<"not deleted"<<endl;
//                break;
//            }
//            case 'F':
//            {
//                fin>>value;
//                fout<<value<<": ";
//                TTree::TNode *t; int comparisons=0;
//                b=Tree->Find(value, Tree->Root, t, comparisons);
//                if (b)
//                    fout<<"found after "<<comparisons<<" comparison(s)"<<endl;
//                else
//                    fout<<"not found after "<<comparisons<<" comparison(s)"<<endl;
//                break;
//            }
//            case 'L':
//            {
//                int length=0;
//                Tree->preOrderTraverseToWrite(Tree->Root, length);
//                if (length==0) {
//                    fout<<"No elements in List"<<endl; // добавил сам
//                }
//                else {
//                    T* array=new T[length];
//                    Tree->inOrderTraverse(*doSmt, Tree->Root, array, 0);
//                    fout<<"List of elements"<<endl;
//                    for (int i=0; i<length; i++)
//                        fout<<array[i]<<endl;
//                }
//                break;
//            }
//            default:
//                break;
//        }
//    }
//
//    fin.close();
//    fout.close();
//}
//

//    TTree *Tree = new TTree;
//    cout<<Tree->Insert(206, Tree->Root)<<endl;
//    cout<<Tree->Insert(813, Tree->Root)<<endl;
//    cout<<Tree->Delete(80, Tree->Root)<<endl;
//    cout<<Tree->Insert(71, Tree->Root)<<endl;
//    TTree::TNode *Node;
//    int comparisons=0;
//    cout<<Tree->Find(1000, Tree->Root, Node, comparisons)<<endl;
//    cout<<comparisons<<endl;
//    cout<<"Прямой обход первого дерева"<<endl;
//    int length=0;
//    Tree->preOrderTraverseToWrite(Tree->Root, length);
//    TTree *Tree2;
//    Tree2=Tree;
//    cout<<"Концевой обход первого дерева"<<endl;
//    T* array=new T[length];
//    Tree->inOrderTraverse(*doSmt, Tree2->Root, array, 0);
