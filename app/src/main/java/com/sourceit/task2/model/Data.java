package com.sourceit.task2.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by User on 01.02.2016.
 */
public class Data implements Serializable {

    public int countFields;
    public int countWithText;
    public Map<Integer, String> childsWithText;
}
