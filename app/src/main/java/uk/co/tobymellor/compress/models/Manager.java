package uk.co.tobymellor.compress.models;

import java.util.HashSet;

public interface Manager {
    HashSet<? extends Object> get();

    void add(Object obj);

    void remove(Object obj);
}
