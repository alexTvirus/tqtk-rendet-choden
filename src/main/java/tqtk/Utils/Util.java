/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqtk.Utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.lang3.StringUtils;
import tqtk.Entity.SessionEntity;

/**
 *
 * @author Alex
 */
public class Util {

    public CookieManager msCookieManager = null;

    public Util() {
        msCookieManager = new CookieManager();
    }

    public void setCookie(CookieManager msCookieManager) {
        CookieHandler.setDefault(msCookieManager);
    }

    public String getPageSource(String url) throws Exception {
        HttpURLConnection con = null;
        try {
            URL obj = new URL(url);

            String pro = obj.getProtocol();
            if (pro.equals("http")) {
                con = (HttpURLConnection) obj.openConnection();
            } else {
                con = (HttpsURLConnection) obj.openConnection();
            }
            con.setConnectTimeout(7000);
            con.setRequestMethod("GET");
//            con.setRequestProperty("Host", "app.slg.vn");
            con.setRequestProperty("Upgrade-Insecure-Requests", "1");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            con.setUseCaches(false);
            con.setDoOutput(true);

            int responseCode = con.getResponseCode();
            if (200 <= responseCode && responseCode <= 399) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();

            } else {
                return "Hệ thống đang quá tải vui lòng";
            }

        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                throw new SocketTimeoutException("");
            }
            throw e;
        }
    }

    public static String getToken(String htmlCode) {
        String prefix = "name=\"_token\" value=\"";
        int index = htmlCode.indexOf(prefix);
        if (index == -1) {
            // Could not find the prefix string.
            return null;
        }

        index += prefix.length();

        // Login token has a fixed length of 40.
        int TokenLength = 40;
        if (htmlCode.length() < index + TokenLength) {
            // Given html code is not long enough.
            return null;
        }

        String token = htmlCode.substring(index, index + TokenLength);
        return token;
    }

    public static String getRefer(String htmlCode) throws UnsupportedEncodingException {
        String prefix = "action=\"";
        int prefixIndex = htmlCode.indexOf(prefix);
        if (prefixIndex == -1) {
            // Could not find the prefix string.
            return null;
        }
        String suffix = "\" accept-charset";
        int suffixIndex = htmlCode.indexOf(suffix);

        int beginIndex = prefixIndex + prefix.length();
        int endIndex = suffixIndex;
        String address = htmlCode.substring(beginIndex, endIndex);
        String result = java.net.URLDecoder.decode(address, StandardCharsets.UTF_8.toString());
        return result.replace("&amp;", "&");
    }

    public String dangNhap(String url, String user, String pass, String token, String Refer) throws Exception {
        HttpURLConnection con = null;
        try {
            URL obj = new URL(Refer);

            String pro = obj.getProtocol();
            if (pro.equals("http")) {
                con = (HttpURLConnection) obj.openConnection();
            } else {
                con = (HttpsURLConnection) obj.openConnection();
            }
            con.setConnectTimeout(7000);
            String urlParameters = "_token=" + token + "&callback=&email=" + user + "&password=" + pass;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            con.setRequestProperty("Host", "id.slg.vn");
            con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            con.setRequestProperty("Upgrade-Insecure-Requests", "1");
            con.setRequestProperty("Referer", Refer);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.setUseCaches(false);
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            if (200 <= responseCode && responseCode <= 399) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();

            } else {
                return "Hệ thống đang quá tải vui lòng";
            }

        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                throw new SocketTimeoutException("");
            }
            throw e;
        }
    }

    public String getThongTinFrame(String url) throws Exception {
        HttpURLConnection con = null;
        try {
            URL obj = new URL(url);

            String pro = obj.getProtocol();
            if (pro.equals("http")) {
                con = (HttpURLConnection) obj.openConnection();
            } else {
                con = (HttpsURLConnection) obj.openConnection();
            }
            con.setConnectTimeout(7000);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            con.setRequestProperty("Host", "app.slg.vn");
//            con.setRequestProperty("Upgrade-Insecure-Requests", "1");
            con.setUseCaches(false);
            con.setDoOutput(true);

            int responseCode = con.getResponseCode();
            if (200 <= responseCode && responseCode <= 399) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();

            } else {
                return "Hệ thống đang quá tải vui lòng";
            }

        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                throw new SocketTimeoutException("");
            }
            throw e;
        }
    }

    public String getThongTinPort(String url) throws Exception {
        HttpURLConnection con = null;
        try {
            URL obj = new URL(url);

            String pro = obj.getProtocol();
            if (pro.equals("http")) {
                con = (HttpURLConnection) obj.openConnection();
            } else {
                con = (HttpsURLConnection) obj.openConnection();
            }
            con.setConnectTimeout(7000);
            con.setRequestMethod("GET");
//            con.setRequestProperty("Host", "app.slg.vn");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            con.setRequestProperty("Host", "api.tamquoc.slg.vn");
            con.setRequestProperty("Upgrade-Insecure-Requests", "1");
            con.setUseCaches(false);
            con.setDoOutput(true);

            int responseCode = con.getResponseCode();
            if (200 <= responseCode && responseCode <= 399) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();

            } else {
                return "Hệ thống đang quá tải vui lòng";
            }

        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                throw new SocketTimeoutException("");
            }
            throw e;
        }
    }

    public static String getFrameString(String htmlCode) throws UnsupportedEncodingException {
        String prefix = "src=\"";
        int index = htmlCode.indexOf(prefix);
        if (index == -1) {
            // Could not find the prefix string.
            return null;
        }

        index += prefix.length();
        int suffixIndex = htmlCode.indexOf("\" frameborder");
        if (suffixIndex == -1) {
            // Could not find the suffix string.
            return null;
        }

        String address = htmlCode.substring(index, suffixIndex);
        return java.net.URLDecoder.decode(address, StandardCharsets.UTF_8.toString()).replace("&amp;", "&");
    }

    public static String getInfoSocket(String htmlCode, String token) {
        token = token + " : ";
        int index = htmlCode.indexOf(token);
        if (index == -1) {
            return null;
        }

        index += token.length();
        int suffixIndex = htmlCode.indexOf(",", index);
        if (suffixIndex == -1) {
            return null;
        }

        String value = htmlCode.substring(index, suffixIndex);
        value = value.replaceAll("[\'||\"]", "");
        return value;
    }

    public static String TaoMsg(String commandId, List<String> list, SessionEntity ss) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String what_is_this = "5dcd73d391c90e8769618d42a916ea1b";
        String input = commandId + ss.getUserId();

        String msg = String.format("%s\u0001%s\t%s\u0002", commandId, ss.getUserId(), ss.getSessionKey());
        Date date = new Date();
        long millisecondsSinceEpoch = date.getTime();
        if (list != null && list.size() > 0) {
            for (String string : list) {
                input += string;
                msg += string + '\t';
            }
        }

        input += what_is_this;
        if (list != null && list.size() > 0) {
            msg = msg.substring(0, msg.length());
        }

        String checksum = hash(input);
        msg += String.format("\u0003%s\u0004%d\u0005\0", checksum, millisecondsSinceEpoch);
        return msg;
    }

    public static String hash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buffer = md.digest(input.getBytes("UTF8"));
        StringBuilder builder = new StringBuilder(input.length() * 2);
        for (byte b : buffer) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static Properties loadProperties(String file_name) {

        final Properties pr = new Properties();
        //window
//        String x = "D:\\7-Project\\Java\\1. Netbean\\2. nghien-cuu\\TQTK\\runshell\\"+file_name;
        //heroku
        String x = "/app/" + file_name;
        try {
            final InputStream fin = new FileInputStream(x);
            pr.load(fin);
            fin.close();
        } catch (final IOException ioe) {
            return null;
        }
        return pr;
    }

    public static List<String> docFileCauHoiThuThue(String file_name) {

        final Properties pr = new Properties();
        //window
//        String x = "D:\\7-Project\\Java\\1. Netbean\\2. nghien-cuu\\TQTK\\runshell\\cauhoi\\"+file_name;
        //heroku
        String x = "/app/cauhoi/" + file_name;
        try {
            return Doc_file_kieu_txt.readFile(x);
        } catch (Exception e) {
            return null;
        }
    }
    
        public String test2(String url) throws Exception {
        HttpURLConnection con = null;
        try {
            URL obj = new URL(url);

            String pro = obj.getProtocol();
            if (pro.equals("http")) {
                con = (HttpURLConnection) obj.openConnection();
            } else {
                con = (HttpsURLConnection) obj.openConnection();
            }
            con.setConnectTimeout(7000);
            con.setRequestMethod("GET");
//            con.setRequestProperty("Host", "app.slg.vn");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            con.setRequestProperty("Host", "api.tamquoc.slg.vn");
            con.setRequestProperty("Upgrade-Insecure-Requests", "1");
            con.setUseCaches(false);
            con.setDoOutput(true);

            int responseCode = con.getResponseCode();
            if (200 <= responseCode && responseCode <= 399) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return con.getHeaderField("Location");

            } else {
                return "rea2";
            }

        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                throw new SocketTimeoutException("");
            }
            throw e;
        }
    }

    public String test1(String url) throws Exception {
        HttpURLConnection con = null;
        try {
            URL obj = new URL(url);

            String pro = obj.getProtocol();
            if (pro.equals("http")) {
                con = (HttpURLConnection) obj.openConnection();
            } else {
                con = (HttpsURLConnection) obj.openConnection();
            }
            con.setConnectTimeout(7000);
            con.setRequestMethod("GET");
//            con.setRequestProperty("Host", "app.slg.vn");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            con.setRequestProperty("Host", "api.sengokugifu.jp");
            con.setRequestProperty("Upgrade-Insecure-Requests", "1");
            con.setUseCaches(false);
            con.setDoOutput(true);

            int responseCode = con.getResponseCode();
            if (200 <= responseCode && responseCode <= 399) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();

            } else {
                return "tre1";
            }

        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                throw new SocketTimeoutException("");
            }
            throw e;
        }
    }
}
