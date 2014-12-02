package org.ybygjy.web.taglib;

import java.io.IOException;
import java.util.Random;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * TagLibTest
 * @author WangYanCheng
 * @version 2010-8-26
 */
public class TagLibTest extends TagSupport {
    private String userName;
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** serialId*/
    private static final long serialVersionUID = 4358924864480511152L;

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter jwInst = pageContext.getOut();
            jwInst.print("HelloWorld!".concat(new Random().nextDouble() + "").concat(this.userName));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write("你好:" + this.userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doEndTag();
    }
}
