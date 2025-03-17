package org.example;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapFactory {
    public static <K, V> Map<K, V> crearMap(int tipo) {
        switch (tipo) {
            case 1:
                return new HashMap<>();
            case 2:
                return new TreeMap<>();
            case 3:
                return new LinkedHashMap<>();
            default:
                return new HashMap<>(); // Valor por defecto
        }
    }
}