package org.example.ejercicio3;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class RandomNumberServiceImpl implements RandomNumberService{
    private final Random r = new Random();

    @Override
    public List<Number> getRandomNumbers() {
        List<Number> nums = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            nums.add(new Number(r.nextLong()));
        }
        return nums;
    }

    @Override
    public Number getRandomNumber(int length) {
        return new Number(r.nextLong((long)(Math.pow(10, length)-1)));
    }

    @Override
    public Number getRandomNumberOf(Number randomNumber) {
        return new Number(r.nextLong(
                (long)(Math.pow(10,(String.valueOf(randomNumber.random).length()))/10),
                (long)(Math.pow(10,(String.valueOf(randomNumber.random).length()))-1)
        ));
    }
}
