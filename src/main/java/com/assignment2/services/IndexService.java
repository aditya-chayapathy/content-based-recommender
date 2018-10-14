package com.assignment2.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    public Map<String, List<String>> dataForUIRendering() {
        Map<String, List<String>> x = new HashMap<>();
        List<String> temp = new ArrayList<>();
        temp.add("1");
        temp.add("2");
        temp.add("3");
        temp.add("4");
        temp.add("5");
        temp.add("6");
        temp.add("7");
        temp.add("8");
        temp.add("9");
        temp.add("10");
        x.put("a", temp);
        x.put("b", temp);
        x.put("c", temp);
        x.put("d", temp);
        x.put("e", temp);

        return x;
    }

}
