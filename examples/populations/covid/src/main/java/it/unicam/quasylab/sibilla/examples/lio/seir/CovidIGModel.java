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

/**
 *
 */
package it.unicam.quasylab.sibilla.examples.lio.seir;

import it.unicam.quasylab.sibilla.core.models.Model;
import it.unicam.quasylab.sibilla.core.models.ParametricDataSet;
import it.unicam.quasylab.sibilla.core.models.pm.PopulationModelDefinition;
import it.unicam.quasylab.sibilla.core.models.pm.PopulationState;
import it.unicam.quasylab.sibilla.core.simulator.SimulationEnvironment;
import it.unicam.quasylab.sibilla.core.simulator.sampling.SamplingFunction;
import org.apache.commons.math3.random.RandomGenerator;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.function.Function;

/**
 * @author loreti
 *
 */
public class CovidIGModel {



    public final static int SAMPLINGS = 120;
    public final static double DEADLINE = 120;
    private static final int REPLICA = 10;
    private final static int TASKS = 5;


    public static void main(String[] argv) throws FileNotFoundException, InterruptedException, UnknownHostException {

        PopulationModelDefinition def = new PopulationModelDefinition(CovidAGDefinition::generatePopulationRegistry,
                CovidAGDefinition::generateRules,
                (ee,pr) -> ParametricDataSet.newStateSet(rg -> CovidAGDefinition.initialState()));
        SimulationEnvironment simulator = new SimulationEnvironment();
        Model<PopulationState> model = def.createModel();
        SamplingFunction<PopulationState> collection = model.selectSamplingFunction(SAMPLINGS,DEADLINE/SAMPLINGS);
        simulator.simulate(model,def.state(),collection::getSamplingHandler,REPLICA,DEADLINE);
        collection.printTimeSeries("data","sir_",".data");
    }


}
