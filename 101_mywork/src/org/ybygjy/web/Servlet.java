package org.ybygjy.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ybygjy.web.calendar.CalendarMgr;
import org.ybygjy.web.comp.FileItem;
import org.ybygjy.web.comp.FileMgrComp;
import org.ybygjy.web.entity.UserEntity;
import org.ybygjy.web.utils.RequestUtils;
import org.ybygjy.web.utils.ResponseUtils;
import org.ybygjy.web.utils.WrapperRequest;

/**
 * servlet
 * @author WangYanCheng
 * @version 2010-1-10
 */
public class Servlet extends HttpServlet {
    /** default serial */
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        request.setCharacterEncoding("UTF-8");
        // new ExtFileUpload().doParseContent(request);
        String act = request.getParameter("act");
        if ("doLogin".equals(act)) {
            doLogin(request, response);
        } else if ("doQuery".equals(act)) {
            doQuery(request, response);
        } else if ("doTestFlex".equals(act)) {
            doTestFlex(request, response);
        } else if ("doSend".equals(act)) {
            innerReceiveData(request, response);
        } else if ("fileUpload".equals(act)) {
            fileUpload(request, response);
        } else if ("upload4PDA".equals(act)) {
            upload4PDA(request, response);
        } else if ("download4PDA".equals(act)) {
            download4PDA(response);
        } else if ("openFile".equals(act)) {
            doOpenFile(request, response);
        } else if ("calendar".equals(act)) {
            dispatchCalendar(request, response);
        } else {
            request.setAttribute("userName", "王延成");
            request.getRequestDispatcher("testRedirecter.jsp").forward(request, response);
            response.sendRedirect("testRedirecter.jsp");
        }
        // response.sendRedirect("");
        // request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * 登陆
     * @param request request4http Instance
     * @param response response4http Instance
     */
    private void doLogin(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        boolean loginFlag = false;
        Map<String, Object> loginRtnData = new HashMap<String, Object>();
        if (null == userName || "".equals(userName)) {
            loginRtnData.put("errorMessage", "登陆失败，用户名为空！");
        } else {
            String password = request.getParameter("userPW");
            if (null == password || "".equals(password)) {
                loginRtnData.put("errorMessage", "登陆失败，密码不能为空！");
            } else if ("admin".equals(userName) || "123456".equals(password)) {
                Map<String, String> tmpMap = new HashMap<String, String>();
                tmpMap.put("name", "王延成");
                tmpMap.put("age", "28");
                tmpMap.put("email", "ybygjy@gmail.com");
                loginRtnData.put("data", tmpMap);
                loginFlag = true;
                request.getSession().setAttribute("LoginUser",
                    new UserEntity("王延成", request.getContextPath()));
            }
        }
        loginRtnData.put("success", loginFlag);
        ResponseUtils.getInstance().doResponse(response, loginRtnData);
    }

    /**
     * 查询
     * @param request request
     * @param response response
     */
    private void doQuery(HttpServletRequest request, HttpServletResponse response) {
        String category = request.getParameter("category");
        if ("bbs".equals(category)) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("PK_CODE", "00001");
            resultMap.put("CONTENT", "内容_1");
            resultMap.put("TIME", "2009年12月30日");
            resultMap.put("LEVEL", "5");
            resultMap.put("PK_CODE", "00002");
            resultMap.put("CONTENT", "内容_2");
            resultMap.put("TIME", "2009年12月31日");
            resultMap.put("LEVEL", "6");
            Map<String, Object> objInst = new HashMap<String, Object>();
            objInst.put("totalCount", "2");
            objInst.put("mes", "信息");
            objInst.put("nbbs", resultMap);
            ResponseUtils.getInstance().doResponse(response, objInst);
        } else if ("getJavaScript".equals(category)) {
            String resStr = "var fourstPageObj = function(){return {onLoad:function()"
                + " {currPageObj.callBack('第四种方式加载JavaScript。。');}}}();fourstPageObj.onLoad();";
            ResponseUtils.getInstance().doResponse(response, resStr);
        } else if ("doDownload".equals(category)) {
            try {
                InputStream ins = new FileInputStream(new File("C:\\Setup.CAB"));
                OutputStream ous = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int flag = 0;
                while ((flag = ins.read(buffer)) != -1) {
                    ous.write(buffer, 0, flag);
                }
                ous.flush();
                ous.close();
                ins.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * Flex测试辅助
     * @param request 请求头
     * @param response 响应体
     */
    private void doTestFlex(HttpServletRequest request, HttpServletResponse response) {
        Map paramMap = request.getParameterMap();
        for (Iterator iterator = paramMap.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        ResponseUtils.getInstance().doResponse(response, "OK");
    }

    /**
     * 接收数据<br>
     * <i>注意：request.getInputStream()比较特殊</i> <li>
     * request.getInputStream()前不能使用request获取get方式传递</li>
     * @param request request
     * @param response response
     */
    private void innerReceiveData(HttpServletRequest request, HttpServletResponse response) {
        try {
            FileOutputStream fosInst = new FileOutputStream(new File("C:\\test.receive"));
            InputStream ins = request.getInputStream();
            byte[] buffer = new byte[1024];
            int flag = 0;
            while ((flag = ins.read(buffer)) != -1) {
                fosInst.write(buffer, 0, flag);
            }
            fosInst.flush();
            fosInst.close();
            // ins.close();
            OutputStream ous = response.getOutputStream();
            ous.write("OK".getBytes());
            ous.flush();
            ous.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 文件上传
     * @param request 请求头
     * @param response 响应头
     * @throws IOException IOException
     */
    private void fileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<FileItem> fileItemArr = null;
        String contentType = request.getContentType();
        if (null != contentType && WrapperRequest.matchFileContentType(contentType)) {
            fileItemArr = new FileMgrComp().doAnalyse(WrapperRequest.getInstance(request), response);
            for (Iterator<FileItem> iterator = fileItemArr.iterator(); iterator.hasNext();) {
                System.out.println(iterator.next());
            }
        }
    }

    /**
     * dataSwitch
     * @param request 请求头
     * @param response 响应头
     */
    private void upload4PDA(HttpServletRequest request, HttpServletResponse response) {
        InputStream ins = null;
        try {
            ins = request.getInputStream();
            ReadableByteChannel rbc = Channels.newChannel(ins);
            WritableByteChannel wbc = Channels.newChannel(System.out);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int i = -1;
            do {
                i = rbc.read(buffer);
                buffer.flip();
                wbc.write(buffer);
                buffer.clear();
            } while (i != -1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Date currDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ResponseUtils.getInstance().doResponse(response, df.format(currDate));
    }

    /**
     * 下载
     * @param response response
     */
    private void download4PDA(HttpServletResponse response) {
        OutputStream ous = null;
        try {
            ous = response.getOutputStream();
            File file = new File("F:\\tools\\java\\install\\jdk-6u23-ea-src-b01-jrl-30_aug_2010.jar");
            WritableByteChannel rbc = Channels.newChannel(ous);
            response.setContentLength((int) file.length());
            FileChannel fc = new FileInputStream(file).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(512);
            while (fc.read(buffer) != -1) {
                buffer.flip();
                rbc.write(buffer);
                buffer.clear();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (ous != null) {
                try {
                    ous.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    /**
     * 打开文件
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    private void doOpenFile(HttpServletRequest request, HttpServletResponse response) {
        File file = new File("D:\\work\\workspace\\mywork\\webRoot\\test.docx");
        try {
            FileChannel fchannel = new FileInputStream(file).getChannel();
            // response.setContentType("application/msword");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "inline; filename=abc.doc");
            OutputStream ous = response.getOutputStream();
            WritableByteChannel wbcChannel = Channels.newChannel(ous);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (fchannel.read(buffer) != -1) {
                buffer.flip();
                wbcChannel.write(buffer);
                buffer.clear();
            }
            buffer.clear();
            fchannel.close();
            wbcChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    /**
     * 负责Calendar模块逻辑处理
     * @param request 请求头
     * @param response 响应体
     */
    private void dispatchCalendar(HttpServletRequest request, HttpServletResponse response) {
        String subAct = request.getParameter("subAct");
RequestUtils.doPrintParam(request);
        if ("qryEvent".equals(subAct)) {
            //整理查询对象
            Object queryBean = new Object();
            String rtnStr = new CalendarMgr().queryEvents2JSON(queryBean);
            ResponseUtils.getInstance().doResponse(response, rtnStr);
        } else if ("addEvent".equals(subAct)) {
            ResponseUtils.getInstance().doResponse(response, "OK");
        } else if ("delEvent".equals(subAct)) {
            ResponseUtils.getInstance().doResponse(response, "OK");
        }
    }
}
