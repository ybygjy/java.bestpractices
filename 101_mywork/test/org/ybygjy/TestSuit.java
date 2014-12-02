package org.ybygjy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 测试套件
 * @author WangYanCheng
 * @version 2010-12-8
 */
@RunWith(Suite.class)
@SuiteClasses({SimpleTest.class, org.ybygjy.web.ServletTest.class, ParameterTest.class})
public class TestSuit {

}
