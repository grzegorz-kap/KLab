package interpreter.parsing.model;

import java.util.ArrayList;
import java.util.List;

public class ExpressionTree<T> {

    private Node<T> root;

    public ExpressionTree() {
        root = new Node<>();
        root.childrens = new ArrayList<>();
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }


    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> childrens;
    }
}
