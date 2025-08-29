package com.bsoft.reactive.util;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"next", "previous"})
public class Item {
    private Integer value;
    private Item next = null;
    private Item previous = null;

    public Item(Integer value) {
        this.value = value;
    }

}
