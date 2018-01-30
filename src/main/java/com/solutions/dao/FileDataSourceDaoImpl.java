package com.solutions.dao;

import com.solutions.model.MemberKey;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileDataSourceDaoImpl implements FilterDataSourceDao {
    private String keyFilePath;
    private List<MemberKey> oldKeys;
    private List<MemberKey> newKeys;

    public FileDataSourceDaoImpl(String keyFilePath) {
        this.keyFilePath = keyFilePath;
    }

    public Set<MemberKey> loadKeys() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(keyFilePath));

            String lineText;
            List<MemberKey> tempOldKeys = new ArrayList<MemberKey>();
            List<MemberKey> tempNewKeys = new ArrayList<MemberKey>();

            while ((lineText = reader.readLine()) != null) {
                String[] oldNewKeys = lineText.split(",");
                tempOldKeys.add(new MemberKey(oldNewKeys[0].trim()));
                tempNewKeys.add(new MemberKey(oldNewKeys[1].trim()));
            }

            this.oldKeys = tempOldKeys;
            this.newKeys = tempNewKeys;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {}
            }
        }

        Set<MemberKey> result = new HashSet<MemberKey>(oldKeys);
        result.addAll(newKeys);

        return result;
    }

    public void saveKeys() {
        BufferedWriter writer = null;
        String persisentFilePath = keyFilePath + ".save";

        try {
            writer = new BufferedWriter(new FileWriter(persisentFilePath));
            if (oldKeys == null) {
                throw new Exception("Empty key list");
            }

            for (int i = 0; i < oldKeys.size(); i++) {
                writer.write(oldKeys.get(i).getKey() + "," + newKeys.get(i).getKey() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (Exception e) {}
        }
    }
}
