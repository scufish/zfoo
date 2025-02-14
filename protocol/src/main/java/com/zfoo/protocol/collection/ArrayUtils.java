/*
 * Copyright (C) 2020 The zfoo Authors
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.zfoo.protocol.collection;

import com.zfoo.protocol.util.AssertionUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public abstract class ArrayUtils {
    /**
     * length
     */
    public static int length(boolean[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(byte[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(short[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(int[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(long[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(float[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(double[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(char[] array) {
        return array == null ? 0 : array.length;
    }

    public static <T> int length(T[] array) {
        return array == null ? 0 : array.length;
    }

    /**
     * toList
     */
    public static List<Boolean> toList(boolean[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        var list = new ArrayList<Boolean>();
        for (var value : array) {
            list.add(value);
        }
        return list;
    }

    public static List<Byte> toList(byte[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        var list = new ArrayList<Byte>();
        for (var value : array) {
            list.add(value);
        }
        return list;
    }

    public static List<Short> toList(short[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        var list = new ArrayList<Short>();
        for (var value : array) {
            list.add(value);
        }
        return list;
    }

    public static List<Integer> toList(int[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        var list = new ArrayList<Integer>();
        for (var j : array) {
            list.add(j);
        }
        return list;
    }

    public static List<Long> toList(long[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        var list = new ArrayList<Long>();
        for (var j : array) {
            list.add(j);
        }
        return list;
    }

    public static List<Float> toList(float[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        var list = new ArrayList<Float>();
        for (var j : array) {
            list.add(j);
        }
        return list;
    }

    public static List<Double> toList(double[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        var list = new ArrayList<Double>();
        for (var j : array) {
            list.add(j);
        }
        return list;
    }

    public static List<Character> toList(char[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        var list = new ArrayList<Character>();
        for (var j : array) {
            list.add(j);
        }
        return list;
    }

    public static <T> List<T> toList(T[] array) {
        if (CollectionUtils.isEmpty(array)) {
            return Collections.emptyList();
        }
        return Arrays.asList(array);
    }


    public static <T> T[] listToArray(List<T> list, Class<T> clazz) {
        AssertionUtils.notNull(list);
        AssertionUtils.notNull(clazz);

        var length = list.size();
        var objectArray = Array.newInstance(clazz, length);

        for (var i = 0; i < length; i++) {
            Array.set(objectArray, i, list.get(i));
        }

        return (T[]) objectArray;
    }

}
