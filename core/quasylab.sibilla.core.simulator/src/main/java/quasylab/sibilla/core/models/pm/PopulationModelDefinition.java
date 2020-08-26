/*
 * Sibilla:  a Java framework designed to support analysis of Collective
 * Adaptive Systems.
 *
 * Copyright (C) 2020.
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
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package quasylab.sibilla.core.models.pm;

import com.sun.source.tree.Tree;
import quasylab.sibilla.core.models.ModelDefinition;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * Instances of this class represent the definition of a population model.
 */
public abstract class PopulationModelDefinition extends AbstractModelDefinition<PopulationState> {

    private final PopulationIndex speciesIndex;

    public PopulationModelDefinition(String[] species) {
        speciesIndex = new PopulationIndex(species);
    }

    public int register(String name,int ... args) {
        return speciesIndex.registerSpecies(getAgentId(name,args));
    }

    public String getAgentId(String name, int[] args) {
        return name+(args.length==0?"": Arrays.toString(args));
    }

    public String[] getSpecies() {
        return speciesIndex.getSpecies();
    }

    public int indexOf(String name, int ... args) {
        return speciesIndex.indexOf(getAgentId(name,args));
    }

    public static double fraction(double a, double b) {
        if (b==0.0) {
            return 0.0;
        } else {
            return a/b;
        }
    }
}
