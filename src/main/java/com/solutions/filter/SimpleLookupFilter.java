package com.solutions.filter;

import com.solutions.dao.FilterDataSourceDao;
import com.solutions.model.MemberKey;

import java.util.Set;

public class SimpleLookupFilter implements FilterInterface {
    private Set<MemberKey> keys;
    private FilterDataSourceDao keySourceDao;

    public boolean isFound(MemberKey key) {
        if (this.keys == null) {
            lazyLoad();
        }

        return keys.contains(key);
    }

    public void load() {
        if (this.keys == null) {
            lazyLoad();
        }
    }
    private void lazyLoad() {
        synchronized (this) {
            Set<MemberKey> temp = this.keys;
            if (temp == null) {
                this.keys = keySourceDao.loadKeys();
            }
        }
    }

    public void save() {
        keySourceDao.saveKeys();
    }

    public void addKeys(Set<MemberKey> newKeys) {
        keys.addAll(newKeys);
    }

    public void removeKeys(Set<MemberKey> keys) {
        this.keys.removeAll(keys);
    }

    public int size() {
        return this.keys.size();
    }

    public Set<MemberKey> getKeys() {
        return keys;
    }

    public void refresh() {
        // TODO
    }

    public void setKeys(Set<MemberKey> keys) {
        this.keys = keys;
    }

    public void setKeySourceDao(FilterDataSourceDao dao) {
        this.keySourceDao = dao;
    }
}
