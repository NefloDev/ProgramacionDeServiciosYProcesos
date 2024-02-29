package org.example.ejercicio3;

import java.util.List;

public interface RandomNumberService {
    List<Number> getRandomNumbers();
    Number getRandomNumber(int length);
    Number getRandomNumberOf(Number randomNumber);
}
