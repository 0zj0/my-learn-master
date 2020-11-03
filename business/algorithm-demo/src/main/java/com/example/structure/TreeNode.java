package com.example.structure;

import lombok.ToString;

import java.util.Stack;

/**
 * @author zhangjie
 * @date 2020/11/3 15:39
 */
@ToString
public class TreeNode {

    /**
     * 节点值
     */
    private int data;
    /**
     * 左节点
     */
    private TreeNode leftChild;
    /**
     * 右节点
     */
    private TreeNode rightChild;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public TreeNode(int data) {
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
    }

    private static TreeNode root = null;

    /**
     * 插入 节点
     * @param value
     */
    public static void insert(int value){
        //插入的节点
        TreeNode node = new TreeNode(value);

        TreeNode curr = root;
        if(root == null || root.getData() == 0){
            root = node;
            return;
        }
        while (true){
            if(value < curr.getData()){
                if(curr.getLeftChild() == null){
                    curr.setLeftChild(node);
                    return;
                }else{
                    curr = curr.getLeftChild();
                }
            }else if(value > curr.getData()){
                if(curr.getRightChild() == null){
                    curr.setRightChild(node);
                    return;
                }else{
                    curr = curr.getRightChild();
                }
            }else{
                return;
            }
        }
    }

    /**
     * 前序(先序)遍历:根->左->右（递归实现）
     * 输出某个文件夹下所有文件名称(可以有子文件夹)---用先序遍历实现：
     *      如果是文件夹，先输出文件夹名，然后再依次输出该文件夹下的所有文件(包括子文件夹)，如果有子文件夹，
     * 则再进入该子文件夹，输出该子文件夹下的所有文件名。
     * @param tree
     */
    public static void frontOrder_Recursion(TreeNode tree){
        if(tree != null){
            System.out.print(tree.getData()+"\t");
            frontOrder_Recursion(tree.getLeftChild());
            frontOrder_Recursion(tree.getRightChild());
        }
    }

    //先序遍历（栈实现）
    public static void frontOrder_Stack(TreeNode tree){
        // 栈 ： 后进先出
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curr = tree;
        if(curr != null){
            stack.push(curr);
            while (!stack.isEmpty()){
                curr = stack.pop();
                System.out.print(curr.getData()+"\t");
                if(curr.getRightChild() != null){
                    stack.push(curr.getRightChild());
                }
                if(curr.getLeftChild() != null){
                    stack.push(curr.getLeftChild());
                }
            }
        }
    }

    /**
     * 中序遍历：左->根->右
     *  从小到大输出数据---中序遍历实现
     * @param tree
     */
    public static void MidenOrder_Recursion(TreeNode tree){
        if(tree != null){
            MidenOrder_Recursion(tree.getLeftChild());
            System.out.print(tree.getData()+"\t");
            MidenOrder_Recursion(tree.getRightChild());
        }
    }

    /**
     * 中序遍历(栈实现)：
     * @param tree
     */
    public static void MidenOrder_Stack(TreeNode tree){
        // 栈 ： 后近先出
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curr = tree;
        while (!stack.isEmpty() || curr != null){
            if(curr != null){
                stack.push(curr);
                curr = curr.getLeftChild();    //左节点
            }else{
                curr = stack.pop();                 //出栈访问
                System.out.print(curr.getData()+"\t");
                curr = curr.getRightChild();   //右节点
            }
        }
    }


    /**
     * 后序遍历（递归实现）：左->右->根
     * @param tree
     */
    public static void traversalOrder_Recursion(TreeNode tree){
        if(tree != null){
            traversalOrder_Recursion(tree.getLeftChild());
            traversalOrder_Recursion(tree.getRightChild());
            System.out.print(tree.getData()+"\t");
        }
    }

    /**
     * 后序遍历(双栈实现)：
     * @param tree
     */
    public static void traversalOrder_DoubleStack(TreeNode tree){
        // 栈 ： 后近先出
        Stack<TreeNode> stack = new Stack<TreeNode>();
        Stack<TreeNode> outStack = new Stack<TreeNode>();
        TreeNode curr = tree;
        while (!stack.isEmpty() || curr != null ){
            if(curr != null){
                outStack.push(curr);
                stack.push(curr);
                curr = curr.getRightChild();
            }else{
                curr = stack.pop();
                curr = curr.getLeftChild();
            }
        }
        while (outStack.size() > 0){
            curr = outStack.pop();
            System.out.print(curr.getData()+"\t");
        }
    }

    //搜索
    public static TreeNode search(TreeNode tree,int value){
        if(tree == null){
            return null;
        }
        if(tree.getData() == value){
            return tree;
        }
        if(value < tree.getData()){
            return search(tree.getLeftChild(),value);
        }
        if(value > tree.getData()){
            return search(tree.getRightChild(),value);
        }
        return null;
    }

    public static void main(String[] args) {
        //8-3-1-6-4-7-10-14-13
        insert(8);
        insert(3);
        insert(1);
        insert(10);
        insert(6);
        insert(7);
        insert(4);
        insert(14);
        insert(15);
        insert(13);
        /**********************************8****************
         * *******************3*****************10***********
         * **************1*********6****************14*******
         * *********************4*****7*********13*******15***
         */
        //System.out.println(root.toString());
        System.out.print("先序遍历（递归实现）：");
        frontOrder_Recursion(root);
        System.out.println();
        System.out.print("先序遍历（栈实现）：");
        frontOrder_Stack(root);
        System.out.println();

        System.out.print("中序遍历（递归实现）：");
        MidenOrder_Recursion(root);
        System.out.println();
        System.out.print("中序遍历（栈实现）：");
        MidenOrder_Stack(root);
        System.out.println();

        System.out.print("后序遍历（递归实现）：");
        traversalOrder_Recursion(root);
        System.out.println();
        System.out.print("后序遍历（双栈实现）：");
        traversalOrder_DoubleStack(root);
        System.out.println();

        TreeNode searchTree = search(root,3);
        if(searchTree == null){
            System.out.println("searchTree:null");
        }else{
            System.out.println("searchTree:"+searchTree.toString());
        }

    }

}
