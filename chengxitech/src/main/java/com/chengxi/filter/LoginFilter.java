package com.chengxi.filter;

import com.chengxi.cache.constant.CacheConstant;
import com.chengxi.cache.service.RedisClient;
import com.chengxi.touhaowanjia.user.service.UserService;
import com.go.basetool.APIResultCode;
import com.go.basetool.bean.UserClient;
import com.go.basetool.threadstatus.DataBinderManager;
import com.go.basetool.threadstatus.ThreadConstant;
import com.go.basetool.user.AuthLoginRes;
import com.go.basetool.utils.JsonDtoWrapper;
import com.go.constant.ComYesNo;
import com.go.constant.UserRold;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Component
public class LoginFilter implements Filter {

    @Autowired
    UserService userInfoClient;

    @Autowired
    RedisClient redisClient;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取ioc容器获取redis
        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        redisClient = ctx.getBean("redisClient", RedisClient.class);
        log.info("[redis对象],初始化...");
    }

    public static List<String> setUrlReleaseRestriction = new ArrayList<>();
    public static List<String> setEpManagerInterface = new ArrayList<>();
    public static List<String> setCenterManagerInterface = new ArrayList<>();

    public static List<String> requirepPrivateKeyPWDManagerPassword = new ArrayList<>();

    //设置不拦截路径
    static {
        //setUrlReleaseRestriction.add("/notifyNode");
        setUrlReleaseRestriction.add("/login");
        //setUrlReleaseRestriction.add("/register");
        //setUrlReleaseRestriction.add("/test");

        //setEpManagerInterface.add("/commonidentifymanager/");
        //setEpManagerInterface.add("/register");
        setCenterManagerInterface.add("/register");
        //setCenterManagerInterface.add("/supercreditclient/");
        //setCenterManagerInterface.add("/managersupplyfinace/");

        //requirepPrivateKeyPWDManagerPassword.add(("/downloadFile"));
    }

    public static Boolean checkRequirepPrivateKeyPWDManagerPassword(String url) {
        for (String urlItr : requirepPrivateKeyPWDManagerPassword) {
            if (url.indexOf(urlItr) != -1) {
                return true;
            }
        }
        return false;
    }

    public static Boolean checkManagerInterface(String url) {
        for (String urlItr : setEpManagerInterface) {
            if (url.indexOf(urlItr) != -1) {
                return true;
            }
        }
        return false;
    }

    public static Boolean checkSuperManagerInterface(String url) {
        for (String urlItr : setCenterManagerInterface) {
            if (url.indexOf(urlItr) != -1) {
                return true;
            }
        }
        return false;
    }

    public static Boolean releaseRestriction(String url) {
        for (String urlItr : setUrlReleaseRestriction) {
            if (url.indexOf(urlItr) != -1) {
                return true;
            }
        }
        return false;
    }

    //判断是否登录
    public AuthLoginRes auth_login(String cookie, String uid) {

        AuthLoginRes authLoginRes = new AuthLoginRes();
        if (StringUtils.isEmpty(cookie) || StringUtils.isEmpty(uid)) {
            authLoginRes.setB(ComYesNo.no);
            return authLoginRes;
        }

        String userInfo = redisClient.get(CacheConstant.getUserInfoRrefix(uid));
        if (StringUtils.isEmpty(userInfo)) {
            authLoginRes.setB(ComYesNo.no);
            return authLoginRes;
        }
        //json序列化成UserClient
        UserClient userClient = new Gson().fromJson(userInfo, UserClient.class);
        if (null == userClient || StringUtils.isEmpty(userClient.getCookie()) || StringUtils.isEmpty(userClient.getUserID())) {
            authLoginRes.setB(ComYesNo.no);
            return authLoginRes;
        }

        if (cookie.equals(userClient.getCookie())) {
            authLoginRes.setUserClient(userClient);
            authLoginRes.setB(ComYesNo.yes);
            return authLoginRes;
        }

        authLoginRes.setB(ComYesNo.no);
        return authLoginRes;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");

        //response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token, Content-Type,my_cookie,my_uid,my_privatekeypass");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token, content-type,my_cookie,my_uid,my_privatekeypass,version,system_name,system_version,pwd,lang");
        //response.setHeader("Access-Control-Allow-Headers", "my_privatekeypass,x-auth-token,Origin,Access-Token,X-Requested-With,Content-Type, Accept,multipart/form-data,Authorization,my_cookie,my_uid,version,system_name,system_version,pwd,lang,");

        //输出当前请求的http请求内容
        Enumeration<String> headerNames = request.getHeaderNames();
        log.info("------");
        while (headerNames.hasMoreElements()) {
            String nextElement = headerNames.nextElement();
            log.error(nextElement + ":" + request.getHeader(nextElement));
        }
        log.info("------");

        //测试放行
        String cookieValue = ((HttpServletRequest) servletRequest).getHeader("my_cookie");
        String uid = ((HttpServletRequest) servletRequest).getHeader("my_uid");
        String priaveKeyPassManager = ((HttpServletRequest) servletRequest).getHeader("my_privatekeypass");

        UserClient user = new UserClient();
        user.setCookie(cookieValue);
        user.setUserID(uid);
        //设置当前线程进入设置userClient的值
        String s = redisClient.get(CacheConstant.getUserInfoRrefix(uid));
        UserClient userClient1 = new Gson().fromJson(s, UserClient.class);
        DataBinderManager.<UserClient>getDataBinder(ThreadConstant.REQUEST_USER_BINDER).put(userClient1);

        filterChain.doFilter(servletRequest, servletResponse);
        if (true){
            return;
        }

        String url = ((HttpServletRequest) servletRequest).getRequestURI();

        //判断用户是否登录
        if (releaseRestriction(url)) {
            log.info("--------enter login");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        log.info(url);



        System.out.println(cookieValue);
        System.out.println(uid);


        if (!StringUtils.isEmpty(cookieValue)) {
            //获取反序列化userClient
            Integer checkStatus = checkCookieAndPrivilege(cookieValue, uid, priaveKeyPassManager, url);
            if (checkok == checkStatus) {
                UserClient userClient = new UserClient();
                userClient.setCookie(cookieValue);
                userClient.setUserID(uid);
                System.out.println("filter: "+userClient);
                DataBinderManager.<UserClient>getDataBinder(ThreadConstant.REQUEST_USER_BINDER).put(userClient);
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (checknoprivilege == checkStatus) {
                JsonDtoWrapper<String> jo = new JsonDtoWrapper();
                jo.setCodeMsg(APIResultCode.NO_PRIVILEGE);
                servletResponse.setContentType("application/json");
                servletResponse.setCharacterEncoding("UTF-8");
                PrintWriter out = servletResponse.getWriter();
                out.print(new Gson().toJson(jo));
                out.flush();
            } else if (checkcookieinvalid == checkStatus) {
                log.error("pe_cookie == null || pe_cookie.getValue() == null");
                JsonDtoWrapper<String> jo = new JsonDtoWrapper();
                jo.setCodeMsg(APIResultCode.NOT_LOGIN);
                servletResponse.setContentType("application/json");
                servletResponse.setCharacterEncoding("UTF-8");
                PrintWriter out = servletResponse.getWriter();
                out.print(new Gson().toJson(jo));
                out.flush();
            } else if (frozened == checkStatus) {
                JsonDtoWrapper<String> jo = new JsonDtoWrapper();
                jo.setCodeMsg(APIResultCode.USER_FROZENED);
                servletResponse.setContentType("application/json");
                servletResponse.setCharacterEncoding("UTF-8");
                PrintWriter out = servletResponse.getWriter();
                out.print(new Gson().toJson(jo));
                out.flush();
            } else if (privateKeyNotRight == checkStatus) {
                JsonDtoWrapper<String> jo = new JsonDtoWrapper();
                jo.setCodeMsg(APIResultCode.PASSWORD_ERROR);
                servletResponse.setContentType("application/json");
                servletResponse.setCharacterEncoding("UTF-8");
                PrintWriter out = servletResponse.getWriter();
                out.print(new Gson().toJson(jo));
                out.flush();
            }
            return;
        }

        log.error("pe_cookie == null || pe_cookie.getValue() == null");

        JsonDtoWrapper<String> jo = new JsonDtoWrapper();
        jo.setCodeMsg(APIResultCode.NOT_LOGIN);
        servletResponse.setContentType("application/json");
        servletResponse.setCharacterEncoding("UTF-8");
        PrintWriter out = servletResponse.getWriter();
        out.print(new Gson().toJson(jo));
        out.flush();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

    public static Integer checkok = 1;//正确
    public static Integer checkcookieinvalid = 2;//cookie无效
    public static Integer checknoprivilege = 3;//没有权限
    public static Integer frozened = 4;//账户冻结
    public static Integer privateKeyNotRight = 5;//私钥不正确
    //public static Integer checknosignin = 6;//未登录


    Integer checkCookieAndPrivilege(String cookie, String uid, String priaveKeyPassManager, String urlOuter) {
        //判断cookie是否失效
        AuthLoginRes authLoginRes = auth_login(cookie, uid);
        if (ComYesNo.no == authLoginRes.getB()) {
            return checkcookieinvalid;
        }

        UserClient userClient = authLoginRes.getUserClient();
        if (UserRold.commonManager == userClient.getMyRole()) {
            if (!checkManagerInterface(urlOuter)) {
                return checknoprivilege;
            }
        }

        return checkok;
    }

    @Override
    public void destroy() {

    }
}
