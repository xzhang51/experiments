package com.solutions.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DatabaseDataSourceDao {
    private static final String BREACH_ACCOUNT_LOOKUP_SQL = "select old_account, new_account from breach_account_lookup";
    private static final int BATCH_SIZE = 10000;

    private PreparedStatement preStat = null;

    public List<String> loadBreachAccounts(Connection dbConn) throws Exception {
        ResultSet resultSet = null;
        try {
            preStat = dbConn.prepareStatement(BREACH_ACCOUNT_LOOKUP_SQL);
            resultSet = preStat.executeQuery();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preStat != null) {
                preStat.close();
            }
        }

        return mapResultSet(resultSet);
    }

    private List<String> mapResultSet(ResultSet resultSet) throws Exception {
        List<String> result = new ArrayList<String>();
        while (resultSet.next()) {
            String oldAcct = resultSet.getString("old_account");
            String newAcct = resultSet.getString("new_account");

            if (oldAcct != null) {
                result.add(oldAcct);
            }

            if (newAcct != null) {
                result.add(newAcct);
            }
        }

        return result;
    }
}
