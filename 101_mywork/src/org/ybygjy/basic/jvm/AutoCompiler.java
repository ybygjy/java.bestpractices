package org.ybygjy.basic.jvm;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;

/**
 * Java字节码的操纵之动态编译
 * @author WangYanCheng
 * @version 2011-2-17
 */
public class AutoCompiler {
    /** 源代码 */
    private String source;
    /** 类名称 */
    private String className;
    /** 编译输出路径 */
    private String outPath = ".";
    /** 提取包名称 */
    private Pattern packPattern = Pattern.compile("^package\\s+([a-z0-9.]+);");
    /** 提取类名称 */
    private Pattern classNamePattern = Pattern.compile("class\\s+([^{]+)");

    /**
     * Constructor
     * @param source 源代码
     * @param outPath 动态编译文件的输出路径
     */
    public AutoCompiler(String source, String outPath) {
        this.outPath = outPath;
        this.setSource(source);
    }

    /**
     * 编译
     * @return 编译结果 true/false
     * @throws Exception 抛出异常信息
     */
    private boolean doCompile() throws Exception {
        return new InnerCompiler(new URI(className), Kind.SOURCE, this.source).compile();
    }

    /**
     * 调用
     * @param methodName 方法名称
     * @return result 调用结果
     */
    public Object doInvoke(String methodName) {
        ClassLoader classLoader = InnerCompiler.class.getClassLoader();
        try {
            Class classDef = classLoader.loadClass(className);
            Method method = classDef.getMethod(methodName, new Class[] {});
            Object result = method.invoke(null, new Object[] {});
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 自动调用
     * @param methodName 方法名称
     * @return resultObj 调用结果
     * @throws Exception 忽略所有异常（建议调整）
     */
    public Object autoInvoke(String methodName) throws Exception {
        Object result = null;
        if (this.doCompile()) {
            return doInvoke(methodName);
        }
        return result;
    }

    /**
     * 设定参与编译的源代码
     * @param source compiled source
     */
    public void setSource(String source) {
        String tmpName = analyseClassName(source);
        this.className = tmpName.trim();
        this.source = source;
    }

    /**
     * 解析类名称
     * @param source 源字符串
     * @return className 类名称/空字符串
     */
    private String analyseClassName(String source) {
        String tmpName = "";
        Matcher matcher = packPattern.matcher(source);
        if (matcher.find()) {
            tmpName = (matcher.group(1)).concat(".");
        }
        matcher = classNamePattern.matcher(source);
        if (matcher.find()) {
            tmpName = tmpName.concat(matcher.group(1));
        }
        return tmpName;
    }

    /**
     * 指定类名称
     * @param className 类名称
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 设定输出路径
     * @param outPath 输出路径
     */
    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutoCompiler [className=");
        builder.append(className);
        builder.append(", source=");
        builder.append(source);
        builder.append("]");
        return builder.toString();
    }

    /**
     * 负责自动编译
     * @author WangYanCheng
     * @version 2011-2-17
     */
    class InnerCompiler extends SimpleJavaFileObject {
        /** content */
        private String content;

        /**
         * Contructor
         * @param uri uri
         * @param kind kind
         * @param content content
         */
        public InnerCompiler(URI uri, Kind kind, String content) {
            this(uri, kind);
            this.content = content;
        }

        /**
         * Constructor
         * @param uri 编译源文件路径
         * @param kind 文件类型
         */
        protected InnerCompiler(URI uri, Kind kind) {
            super(uri, kind);
        }

        /*
         * (non-Javadoc)
         * @see javax.tools.SimpleJavaFileObject#getCharContent(boolean)
         */
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return this.content;
        }

        /**
         * 编译
         * @return result 成功编译标记{true|false}
         */
        public boolean compile() {
            boolean result = false;
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> fileObject = Arrays.asList(this);
            Iterable options = Arrays.asList("-d", outPath);
            CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObject);
            result = task.call();
            return result;
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     * @throws Exception 抛出异常
     */
    public static void main(String[] args) throws Exception {
        String methodName = "compilerTest";
        StringBuilder source = new StringBuilder(
            "package org.ybygjy.basic.jvm; class TestCompiler {public static String compilerTest(){return \"HelloWorld\";}}");
        AutoCompiler dctInst = new AutoCompiler(source.toString(), "./webRoot/WEB-INF/classes");
        System.out.println(dctInst.autoInvoke(methodName));
        // if (dctInst.doCompile()) {
        // Object obj = dctInst.doInvoke(methodName);
        // System.out.println(obj);
        // }
    }
}
