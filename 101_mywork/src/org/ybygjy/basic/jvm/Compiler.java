package org.ybygjy.basic.jvm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * 负责动态编译
 * <p>
 * 1、必须要求target文件在classpath下能找到(测试未通过)
 * </p>
 * <p>
 * 2、可通过指定target文件全路径的方式找到源文件
 * </p>
 * <p>
 * 3、注意<code>javac</code>、<code>java</code>两个常用参数：
 * </p>
 * <p>
 * 3.1、classpath：搜索类路径(趋向与class文件)，这块也可载入且编译相关java文件
 * </p>
 * <p>
 * 3.2、sourcepath: 引用源文件路径，指定编译所关联的源文件(.java)
 * </p>
 * @author WangYanCheng
 * @version 2011-2-18
 */
public class Compiler {
    /**
     * 测试入口
     * @param args args
     * @throws FileNotFoundException FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        // 注意路径使用全路径
        String fullClassPath = "d:/work/workspace/mywork/src/org/ybygjy/basic/jvm/CompilerTarget.java";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        FileOutputStream err = new FileOutputStream("C:\\err.txt");
        int compilationResult = compiler.run(null, null, err, "-sourcepath",
            "d:\\work\\workspace\\mywork\\", "-verbose", fullClassPath);
        if (compilationResult == 0) {
            System.out.println("Done.");
        } else {
            System.out.println("Fail.");
        }
    }
}
