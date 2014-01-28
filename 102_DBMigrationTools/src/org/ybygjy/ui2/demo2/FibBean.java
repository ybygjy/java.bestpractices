package org.ybygjy.ui2.demo2;

/**
 * 数据Bean
 * @author WangYanCheng
 * @version 2012-11-1
 */
public class FibBean {
    /**输入对象Key*/
    private String inputKey;
    /**输出对象Key*/
    private String outputKey;
    /**Fibonacci项*/
    private long fibTerm;
    /**Fibonacci值*/
    private long fibValue;
    /**
     * @return the inputKey
     */
    public String getInputKey() {
        return inputKey;
    }
    /**
     * @param inputKey the inputKey to set
     */
    public void setInputKey(String inputKey) {
        this.inputKey = inputKey;
    }
    /**
     * @return the outputKey
     */
    public String getOutputKey() {
        return outputKey;
    }
    /**
     * @param outputKey the outputKey to set
     */
    public void setOutputKey(String outputKey) {
        this.outputKey = outputKey;
    }
    /**
     * @return the fibTerm
     */
    public long getFibTerm() {
        return fibTerm;
    }
    /**
     * @param fibTerm the fibTerm to set
     */
    public void setFibTerm(long fibTerm) {
        this.fibTerm = fibTerm;
    }
    /**
     * @return the fibValue
     */
    public long getFibValue() {
        return fibValue;
    }
    /**
     * @param fibValue the fibValue to set
     */
    public void setFibValue(long fibValue) {
        this.fibValue = fibValue;
    }
}
