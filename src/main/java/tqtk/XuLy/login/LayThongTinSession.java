/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqtk.XuLy.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import tqtk.Utils.Util;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tqtk.Entity.SessionEntity;
import static tqtk.Tqtk.sendMessage;

/**
 *
 * @author Alex
 */
public class LayThongTinSession {

    private static List<SessionEntity> ListSession = new ArrayList<>();

    public static synchronized SessionEntity getSessionEntity(String user, String pass, int id, Util u) throws Exception {
        // login va lay thong tin session
        u.setCookie(u.msCookieManager);
//        String html = u.test2("http://api.sengokugifu.jp/home/login/login?Uname=32572016266&userid=32572016266&GameId=2001&ServerId=s17&Time=1673956339&al=1&from=mixi&siteurl=&Sign=1da179d1f5c90a6dfcf76f8a5a15139c");
        String html = u.test2("http://api.sengokugifu.jp/home/login/login?Uname=32572016266&userid=32572016266&GameId=2001&ServerId=s17&Time=1674435608&al=1&from=mixi&siteurl=&Sign=26e75a5bcb87e01104bef31dbe32d0ce");
        
        html = u.test1(html);

        String ip = Util.getInfoSocket(html, "ip");
        String ports = Util.getInfoSocket(html, "ports");
        String sessionKey = Util.getInfoSocket(html, "sessionKey");
        String userID = Util.getInfoSocket(html, "userID");
        SessionEntity ss = new SessionEntity();
        ss.setIp(ip);
        ss.setPorts(Integer.parseInt(ports));
        ss.setSessionKey(sessionKey);
        ss.setUserId(userID);
        ss.setStringName(user);
        u.setCookie(null);
        return ss;
    }

    public static List<SessionEntity> getListSession() {
        return ListSession;
    }

}
