package ru.job4j.concurrent.cas;

public class Node<T> {
    private final T value;
    private Node<T> next;

    public Node(final T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
