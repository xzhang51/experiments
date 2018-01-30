package com.solutions.dao;

import com.solutions.model.MemberKey;
import java.util.Set;

public interface FilterDataSourceDao {
    public Set<MemberKey> loadKeys();
    public void saveKeys();
}
