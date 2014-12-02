package org.ybygjy.basic.jvm;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

/**
 * 测试动态编译，与作用域相关
 * <p>1、编译参数设置之编译后文件存储路径(见<code>options -d</code>属性)<p>
 * <p>1.1、建议此项参数规定为必选</p>
 * <p>1.2、默认编译后的class文件会存储在目录顶层</p>
 * <p>1.3、补充说明</p>
 * <table cellpadding=1 cellspacing=1 border=1 summary="Capturing group numberings">
 * <tr><th>设定值</th><th>对应输出</th></tr>
 * <tr><td>.</td><td>相对顶层目录路径进行输出，在使用命令行时可正常工作，但使用IDE运行时程序相对目录路径不正确。</td></tr>
 * <tr><td>绝对路径如(C://)</td><td>对应输出在C://，但考虑到ClassLoader加载时需要指定此目录带来不必要的麻烦，故可用性不强。</td></tr>
 * <tr><td>程序目录绝对路径如(D:\\mywork\\webRoot\\classes)</td><td>该配置也不推荐，该配置在今后代码移植时问题就会凸显</td></tr>
 * <tr><td>使用目录相对路径(./webRoot/WEB-INF/classes)</td><td>推荐在有限条件下使用此办法</td></tr>
 * <tr><td>考虑作为一个Bean的属性</td><td>此方法灵活，是解决此类问题的不二选择</td></tr>
 * </table>
 * @author WangYanCheng
 * @version 2011-2-18
 */
public class DynamicCompiler {
    private static double calculate(String exp) throws Exception {
        String className = "org.ybygjy.basic.jvm.CaculatorMain";
        String methodName = "calculate";
        String source = "package org.ybygjy.basic.jvm;class CaculatorMain {public static double calculate(){return (@EXP@);}}"
            .replaceAll("@EXP@", exp);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        StringSourceJavaObject ssjoInst = new StringSourceJavaObject(source.replaceAll("@EXP@", exp),
            className);
        Iterable fileObjects = Arrays.asList(ssjoInst);
        Iterable options = Arrays.asList("-d", /*"D:\\work\\workspace\\mywork\\webRoot\\WEB-INF\\classes"*/"./webRoot/WEB-INF/classes");
        CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObjects);
        boolean result = task.call();
        if (result) {
            ClassLoader loader = DynamicCompiler.class.getClassLoader();
            try {
                Class<?> clazz = loader.loadClass(className);
                Method method = clazz.getMethod(methodName, new Class<?>[] {});
                Object value = method.invoke(null, new Object[] {});
                return (Double) value;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    static class StringSourceJavaObject extends SimpleJavaFileObject {
        private String content;

        public StringSourceJavaObject(String content, String name) throws Exception {
            super(new URI(name), Kind.SOURCE);
            this.content = content;
        }

        /*
         * (non-Javadoc)
         * @see javax.tools.SimpleJavaFileObject#getCharContent(boolean)
         */
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return this.content;
        }
    }

    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) throws Exception {
        System.out.println(DynamicCompiler.calculate("1.1 + 1.2"));
    }
}
