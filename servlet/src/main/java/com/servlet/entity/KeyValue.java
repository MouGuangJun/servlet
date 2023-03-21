package com.servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 键值对象
 *
 * @param <K> 键
 * @param <V> 值
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyValue<K, V> {
    private K key;
    private V value;

    /**
     * 通过键、值创建对象
     *
     * @param key   键
     * @param value 值
     * @param <K>   键类型
     * @param <V>   值类型
     * @return 键值对象
     */
    public static <K, V> KeyValue<K, V> valueOf(K key, V value) {
        KeyValue<K, V> keyValue = new KeyValue<>();
        keyValue.setKey(key);
        keyValue.setValue(value);

        return keyValue;
    }
}
