package com.solutions.filter;

import com.solutions.model.MemberKey;
import java.util.Set;

public interface FilterInterface {
    public void load();
    public void save();
    public boolean isFound(MemberKey key);
    public void addKeys(Set<MemberKey> keys);
    public void removeKeys(Set<MemberKey> keys);
    public int size();
}
