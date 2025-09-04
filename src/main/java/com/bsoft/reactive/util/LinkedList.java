package com.bsoft.reactive.util;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LinkedList {
    private Item current = null;
    private Item first = null;
    private Item last = null;

    public void add(Item item) {
        if (first == null) {
            first = item;
            last = item;
            current = first;
        } else {
            last.setNext(item);
            item.setPrevious(last);
            last = item;
        }
    }

    public Item getFirst() {
        current = first;
        return first;
    }

    public Item getLast() {
        current = last;
        return last;
    }

    public Item getNext() {
        Item next = current;

        if (current != null) {
            next = current.getNext(); // can be null
            current = next;
        }

        return next;
    }

    public Item getPrevious() {
        Item previous = current;

        if (current != null) {
            previous = current.getPrevious(); // can be null
            current = previous;
        }

        return current;
    }
}
