package com.me;

/**
 * Created by Babbaj on 3/15/2018.
 */
public interface Patterns {

    // 123, -456
    int[] PATTERN_FULL_CLOSE  = {
            1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,
            1,1,1,1,0,0,0,0,0,0,0,0,0,1,0,0,
            1,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,
            1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,
            0,1,0,1,0,0,0,0,0,0,1,1,0,0,0,0,
            0,1,0,0,0,0,1,0,0,0,0,1,0,0,1,0,
            0,0,1,1,0,1,1,0,0,0,0,1,0,1,0,1,
            0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,
            0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,
            0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,
            1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,
            0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,1,
            0,0,0,0,0,1,0,0,0,0,0,0,1,0,1,0,
            0,1,1,0,0,0,0,0,0,0,0,0,0,0,1,0
    };

    // 5311, 7188
    int[] PATTERN_FULL_MEDIUM  = {
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
            0,1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,
            1,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,
            0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,
            1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,
            0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,
            0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,
            0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,
            1,0,0,1,1,1,0,0,0,0,0,0,1,0,1,0,
            0,0,0,1,1,0,0,0,0,0,1,1,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
            0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,0,
            0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,
            1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,
            1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    };

    // 50000, -60000
    int[] PATTERN_FULL_FAR  = {
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,
            0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,
            0,0,1,0,0,1,0,0,0,1,0,0,0,0,1,0,
            0,0,0,0,0,0,0,1,1,0,0,0,0,1,0,0,
            0,0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,
            1,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,
            0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,
            0,0,1,1,1,0,0,0,0,0,0,1,0,1,1,0,
            0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,1,
            0,0,0,0,0,0,1,0,0,0,0,1,0,1,1,0,
            0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,
            1,1,1,0,0,0,0,0,0,0,1,0,0,1,0,0,
            0,1,0,0,0,0,0,1,0,0,1,0,0,1,0,0,
            0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,
            0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,
    };

    int[] PATTERN_FULL_VERY_FAR = {

    };
}