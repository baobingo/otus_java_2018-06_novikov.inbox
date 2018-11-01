package ru.otus.l131;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MultithreadSortTest {

    @Test
    void sort() {
        int sizeOfArray = 10000;
        Random filler = new Random();

        int[] array = new int[sizeOfArray];
        int[] referenceArray = new int[sizeOfArray];
        int[] result;

        for(int i=0; i<sizeOfArray; i++){
            int number = filler.nextInt(sizeOfArray);
            array[i] = referenceArray[i] = number;
        }

        Arrays.sort(referenceArray);
        result = new MultithreadSort().sort(array);

        for(int i=0; i<sizeOfArray; i++){
            assertEquals(referenceArray[i], result[i]);
        }

    }
}