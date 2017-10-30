package com.asgatech.sharjahmuseums.Tools;

/**
 * Created by mohamed.arafa on 10/24/2017.
 */

public class Foo<T> {

    private Class<T> type;

    public Foo(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }
}
