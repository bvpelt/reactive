package com.bsoft.reactive;

import com.bsoft.reactive.util.Item;
import com.bsoft.reactive.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class LinkListTest {

    @Test
    public void createList() {
        LinkedList linkedList = new LinkedList();

        for (int i = 0; i < 10; i++) {
            Item item = new Item(i);
            linkedList.addItem(item);
        }

        Item current = linkedList.getFirst();
        while (current != null) {
            log.info("Current Item: {}", current);
            current = current.getNext();
        }

        current = linkedList.getLast();
        while (current != null) {
            log.info("Current Item: {}", current);
            current = current.getPrevious();
        }
    }
}
