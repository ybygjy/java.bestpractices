package org.ybygjy.mvn;

import junit.framework.TestCase;

/**
 * 单元测试
 * @author YanChengWang
 */
public class HelloMavenTest extends TestCase {
	private HelloMaven helloMvnInst;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.helloMvnInst = new HelloMaven();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testShowInfo() {
		this.helloMvnInst.showInfo();
		assertTrue(true);
	}
}
