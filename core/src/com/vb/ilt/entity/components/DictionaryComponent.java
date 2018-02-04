package com.vb.ilt.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import java.util.Map;

public class DictionaryComponent implements Component, Pool.Poolable{

    public Map<String, String> allWords;
    public Map<String, String> myWords;

    @Override
    public void reset() {
        allWords = null;
        myWords = null;
    }
}
