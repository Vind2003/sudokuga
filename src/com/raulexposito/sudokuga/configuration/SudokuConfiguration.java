package com.raulexposito.sudokuga.configuration;

import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.GABreeder;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StockRandomGenerator;
import org.jgap.util.ICloneable;

public class SudokuConfiguration
        extends Configuration implements ICloneable {

    public SudokuConfiguration() {
        this("", "");
    }

    public SudokuConfiguration(String a_id, String a_name) {
        super(a_id, a_name);
        try {
            setBreeder(new GABreeder());
            setRandomGenerator(new StockRandomGenerator());
            setEventManager(new EventManager());
            BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(
                    this, 0.95d);
            bestChromsSelector.setDoubletteChromosomesAllowed(true);
            addNaturalSelector(bestChromsSelector, false);
            setMinimumPopSizePercent(0);
            setSelectFromPrevGen(1.0d);
            setKeepPopulationSizeConstant(true);
            setFitnessEvaluator(new DefaultFitnessEvaluator());
            setChromosomePool(new ChromosomePool());
            addGeneticOperator(new CrossoverOperator(this, 100));
            addGeneticOperator(new MutationOperator(this, 35));
            resetProperty(a_name, a_id);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(
                    "Fatal error: DefaultConfiguration class could not use its " + "own stock configuration values. This should never happen. " + "Please report this as a bug to the JGAP team.");
        }
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
