package br.ufpe.cin.algoritmos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.ufpe.cin.algoritmos.binarytree.AllTreeTests;
import br.ufpe.cin.algoritmos.list.AllListTests;

@RunWith(Suite.class)
@Suite.SuiteClasses( { AllTreeTests.class, AllListTests.class })
public class AllTests {
}
