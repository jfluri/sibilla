/*
 * Sibilla:  a Java framework designed to support analysis of Collective
 * Adaptive Systems.
 *
 *             Copyright (C) 2020.
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package it.unicam.quasylab.sibilla.core.models.lio;

import org.apache.commons.math3.random.RandomGenerator;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 *
 */
public class LIOCountingState implements LIOState {

    private final int size;
    private final int[] occupancy;

    /**
     * Create a new state with the given occupancy.
     *
     * @param occupancy occupancy created state.
     */
    public LIOCountingState(int[] occupancy) {
        this.occupancy = Arrays.copyOf(occupancy,occupancy.length);
        this.size = IntStream.of(occupancy).sum();
    }



    @Override
    public int size() {
        return size;
    }

    @Override
    public double fractionOf(int stateIndex) {
        return ((double) occupancy[stateIndex])/size;
    }

    @Override
    public double fractionOf(IntPredicate predicate) {
        return numberOf(predicate)/size;
    }

    @Override
    public double numberOf(int stateIndex) {
        return occupancy[stateIndex];
    }

    @Override
    public double numberOf(IntPredicate predicate) {
        return IntStream.range(0, occupancy.length).filter(predicate).sum();
    }

    @Override
    public LIOState step(RandomGenerator randomGenerator, double[][] matrix) {
        int[] occupancy = new int[this.occupancy.length];
        IntStream.range(0, occupancy.length).forEach(s ->
            IntStream.range(0, this.occupancy[s]).forEach(i -> occupancy[LIOState.doSample(randomGenerator,matrix[s],s)]++)
        );
        return new LIOCountingState(occupancy);
    }
}
