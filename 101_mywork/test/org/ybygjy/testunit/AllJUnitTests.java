package org.ybygjy.testunit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllJUnitTests extends TestCase {
    public AllJUnitTests(String name) {
        super(name);
    }
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(org.ybygjy.testunit.JUnit3SimpleTest.class));
        suite.addTest(new TestSuite(org.ybygjy.testunit.Junit3BasicTest.class));
        return suite;
    }
}
