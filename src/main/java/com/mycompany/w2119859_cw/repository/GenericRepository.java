package com.mycompany.w2119859_cw.repository;

import com.mycompany.w2119859_cw.model.BaseModel;
import java.util.List;

public class GenericRepository<T extends BaseModel> {
    private final List<T> items;

    public GenericRepository(List<T> items) {
        this.items = items;
    }

    public List<T> getAll() {
        return items;
    }

    public T getById(String id) {
        return items.stream()
                    .filter(item -> item.getId().equals(id))
                    .findFirst()
                    .orElse(null);
    }

    public void add(T item) {
        items.add(item);
    }

    public void delete(String id) {
        items.removeIf(item -> item.getId().equals(id));
    }
}