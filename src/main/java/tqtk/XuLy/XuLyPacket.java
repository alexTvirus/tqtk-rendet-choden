/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqtk.XuLy;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.http.util.ByteArrayBuffer;
import tqtk.Entity.SessionEntity;
import tqtk.Utils.Util;
import java.util.ArrayList;
import tqtk.Entity.Params;

/**
 *
 * @author Alex
 */
public class XuLyPacket {

    private static Object lockPacketApi = new Object();

    public static void GuiPacketKhongKQ(SessionEntity ss, String code, List<String> list) throws UnknownHostException, IOException, InterruptedException {
        BufferedWriter wr = null;
        try {
            String message = Util.TaoMsg(code, list, ss);
            Thread.sleep(2 * 1000);
            wr = new BufferedWriter(new OutputStreamWriter(ss.getSocket().getOutputStream(), "UTF8"));
            wr.write(message);
            wr.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            if (e.getMessage().contains("socket write error")) {
            ss.resetSocket();
//            }
        }

    }

    public static void GuiPacketKhongKQApi(SessionEntity ss, String code, List<String> list) throws UnknownHostException, IOException, InterruptedException {
        BufferedWriter wr = null;
        try {
            String message = Util.TaoMsg(code, list, ss);
            wr = new BufferedWriter(new OutputStreamWriter(ss.getSocketApi().getOutputStream(), "UTF8"));
            wr.write(message);
            wr.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            if (e.getMessage().contains("socket write error")) {
            //ss.resetSocket();
//            }
        }

    }

    public static StringBuilder GuiPacket(SessionEntity ss, String code, List<String> list) throws UnknownHostException, IOException, InterruptedException {
        BufferedWriter wr = null;
        StringBuilder rp = null;
        String message = "";
        try {
            message = Util.TaoMsg(code, list, ss);
            Thread.sleep(3 * 1000);
            wr = new BufferedWriter(new OutputStreamWriter(ss.getSocket().getOutputStream(), "UTF8"));
            wr.write(message);
            wr.flush();

            rp = new StringBuilder("");
            if (ss.getSocket().isConnected()) {
                Thread.sleep(3 * 1000);
                InputStream instr = ss.getSocket().getInputStream();
                int buffSize = ss.getSocket().getReceiveBufferSize();
                if (buffSize > 0) {
                    byte[] buff = new byte[buffSize];
                    int ret_read = instr.read(buff);
                    if (ret_read != -1) {
                        rp.append(new String(buff, 0, ret_read));
                    }
                }
            }
            return rp;
        } catch (Exception e) {
            System.out.println(e.getMessage());

//            if (e.getMessage().contains("socket write error")) {
//            }
            return null;
        }

    }

    public static StringBuilder GuiPacketApi_b(SessionEntity ss, String code, List<String> list) throws UnknownHostException, IOException, InterruptedException {
        BufferedWriter wr = null;
        StringBuilder rp = null;
        String message = "";
        try {
            message = Util.TaoMsg(code, list, ss);
            Thread.sleep(3 * 1000);
            wr = new BufferedWriter(new OutputStreamWriter(ss.getSocketApi().getOutputStream(), "UTF8"));
            wr.write(message);
            wr.flush();

            rp = new StringBuilder("");
            if (ss.getSocketApi().isConnected()) {
                Thread.sleep(3 * 1000);
                InputStream instr = ss.getSocketApi().getInputStream();
                int buffSize = ss.getSocketApi().getReceiveBufferSize();
                if (buffSize > 0) {
                    byte[] buff = new byte[buffSize];
                    int ret_read = instr.read(buff);
                    if (ret_read != -1) {
                        rp.append(new String(buff, 0, ret_read));
                    }
                }
            }
            return rp;
        } catch (Exception e) {
            System.out.println(e.getMessage());

//            if (e.getMessage().contains("socket write error")) {
//            }
            return null;
        }

    }

    public static String GuiPacketHTTP(SessionEntity ss, String message) throws UnknownHostException, IOException, InterruptedException {
        BufferedWriter wr = null;
        StringBuilder rp = null;
        try {
            System.out.println("message " + message);
            wr = new BufferedWriter(new OutputStreamWriter(ss.getSocketApi().getOutputStream(), "UTF8"));
            wr.write(message);
            wr.flush();

            BufferedInputStream bis = new BufferedInputStream(ss.getSocketApi().getInputStream());
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int read = 0;
            int bufSize = 512;
            byte[] buffer = new byte[bufSize];
            rp = new StringBuilder("");
            String tmp = "";
            if (ss.getSocketApi().isConnected()) {
                while (true) {
                    read = bis.read(buffer);
                    if (read == -1) {
                        tmp = new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8);
//                        System.out.println("-1 " + tmp);
                        return tmp;
                    }

                    baf.append(buffer, 0, read);
//                    System.out.println(new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8));
                    if (baf.byteAt(baf.length() - 1) == 5) {
                        tmp = new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8);
                        return tmp;
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

//            if (e.getMessage().contains("socket write error")) {
//            }
        }
        return "";
    }

    public static String GuiPacketHTTP1(SessionEntity ss, String message) throws UnknownHostException, IOException, InterruptedException {
        BufferedWriter wr = null;
        StringBuilder rp = null;
        String message1 = "";
        try {
            System.out.println("message " + message);
            String[] tmpa = message.split("-");
            List<String> lists = new ArrayList<>();
            if (tmpa.length > 1) {
                for (String string : tmpa) {
                    lists.add(string);
                    lists.remove(0);
                }
            } else {
                lists = null;
            }

            message1 = Util.TaoMsg(tmpa[0], lists, ss);
            System.out.println("message1 " + message1);
            wr = new BufferedWriter(new OutputStreamWriter(ss.getSocketApi().getOutputStream(), "UTF8"));
            wr.write(message1);
            wr.flush();

            BufferedInputStream bis = new BufferedInputStream(ss.getSocketApi().getInputStream());
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int read = 0;
            int bufSize = 512;
            byte[] buffer = new byte[bufSize];
            rp = new StringBuilder("");
            String tmp = "";
            if (ss.getSocketApi().isConnected()) {
                while (true) {
                    read = bis.read(buffer);
                    if (read == -1) {
                        tmp = new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8);
                        System.out.println("-1 " + tmp);
                        return tmp;
                    }

                    baf.append(buffer, 0, read);
                    System.out.println(new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8));
                    if (baf.byteAt(baf.length() - 1) == 5) {
                        tmp = new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8);
                        return tmp;
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

//            if (e.getMessage().contains("socket write error")) {
//            }
        }
        return "";
    }

    public static String GuiPacketHTTP2_b(SessionEntity ss, Params p) throws UnknownHostException, IOException, InterruptedException {
        BufferedWriter wr = null;
        StringBuilder rp = null;
        String message1 = "";
        try {
            System.out.println("message ");
            List<String> lists = new ArrayList<>();
            if (p.getP1() != "") {
                lists.add(p.getP1());
            }
            if (p.getP2() != "") {
                lists.add(p.getP2());
            }
            if (p.getP3() != "") {
                lists.add(p.getP3());
            }
            if (p.getP4() != "") {
                lists.add(p.getP4());
            }

            lists = lists.size() > 0 ? lists : null;

            message1 = Util.TaoMsg(p.getCmd(), lists, ss);
            System.out.println("message1 " + message1);
            wr = new BufferedWriter(new OutputStreamWriter(ss.getSocketApi().getOutputStream(), "UTF8"));
            wr.write(message1);
            wr.flush();

            BufferedInputStream bis = new BufferedInputStream(ss.getSocketApi().getInputStream());
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int read = 0;
            int bufSize = 512;
            byte[] buffer = new byte[bufSize];
            rp = new StringBuilder("");
            String tmp = "";
            if (ss.getSocketApi().isConnected()) {
                while (true) {
                    read = bis.read(buffer);
                    if (read == -1) {
                        tmp = new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8);
//                        System.out.println("-1 " + tmp);
                        return tmp;
                    }

                    baf.append(buffer, 0, read);
//                    System.out.println(new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8));
                    if (baf.byteAt(baf.length() - 1) == 5) {
                        tmp = new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8);
                        return tmp;
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

//            if (e.getMessage().contains("socket write error")) {
//            }
        }
        return "";
    }

    public static String GuiPacket1(SessionEntity ss, String code, List<String> list) throws UnknownHostException, IOException, InterruptedException {
        BufferedWriter wr = null;
        String rp = "";
        String message = "";
        try {
            message = Util.TaoMsg(code, list, ss);
            Thread.sleep(3 * 1000);
            wr = new BufferedWriter(new OutputStreamWriter(ss.getSocket().getOutputStream(), "UTF8"));
            wr.write(message);
            wr.flush();

            if (ss.getSocket().isConnected()) {
                Thread.sleep(3 * 1000);
                BufferedInputStream bis = new BufferedInputStream(ss.getSocket().getInputStream());
                ByteArrayBuffer baf = new ByteArrayBuffer(50);
//                int buffSize = ss.getSocket().getReceiveBufferSize();
                byte[] buffer = new byte[8192];
                int read = 0;
                while (true) {
                    read = bis.read(buffer);
                    if (read == -1) {
                        break;
                    }

                    baf.append(buffer, 0, read);
                    if (buffer[read - 1] == 5) {
                        return new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8);
                    }

                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

//            if (e.getMessage().contains("socket write error")) {
//            }
            return null;
        }
        return rp;
    }

    public static String synGuiPacketApi(SessionEntity ss, String message) throws IOException {
//        synchronized (lockPacketApi) {
            BufferedWriter wr = null;
            String rp = null;
            try {
                wr = new BufferedWriter(new OutputStreamWriter(ss.getSocketApi().getOutputStream(), "UTF8"));
                wr.write(message);
                wr.flush();
                System.out.println("synGuiPacketApi " + message);
                rp = "";
//                if (ss.getSocket().isConnected()) {
//                    BufferedInputStream bis = new BufferedInputStream(ss.getSocket().getInputStream());
//                    ByteArrayBuffer baf = new ByteArrayBuffer(50);
//                    byte[] buffer = new byte[8192];
//                    int read = 0;
//                    while (true) {
//                        read = bis.read(buffer);
//                        if (read == -1) {
//                            break;
//                        }
//
//                        baf.append(buffer, 0, read);
//                        if (buffer[read - 1] == 5) {
//                            return new String(baf.buffer(), 0, baf.length(), StandardCharsets.UTF_8);
//                        }
//
//                    }
//                }
                return rp;
            } catch (Exception e) {
                System.out.println(e.getMessage());

                return null;
            }
//        }

    }

    public static String GuiPacketApi(SessionEntity ss, String code, List<String> list) throws UnknownHostException, IOException, InterruptedException {
        StringBuilder rp = null;
        try {
            String message = "";
            message = Util.TaoMsg(code, list, ss);
            return synGuiPacketApi(ss, message);
        } catch (Exception ex) {
            System.out.println("tqtk.XuLy.XuLyPacket.GuiPacketApi()");
        }
        return null;

    }

    public static String GuiPacketHTTP2(SessionEntity ss, Params p) throws UnknownHostException, IOException, InterruptedException {
        StringBuilder rp = null;
        String message1 = "";
        try {
            List<String> lists = new ArrayList<>();
            if (p.getP1() != "") {
                lists.add(p.getP1());
            }
            if (p.getP2() != "") {
                lists.add(p.getP2());
            }
            if (p.getP3() != "") {
                lists.add(p.getP3());
            }
            if (p.getP4() != "") {
                lists.add(p.getP4());
            }

            lists = lists.size() > 0 ? lists : null;

            message1 = Util.TaoMsg(p.getCmd(), lists, ss);
            System.out.println("message1 " + message1);
            return synGuiPacketApi(ss, message1);

        } catch (Exception e) {
            System.out.println(e.getMessage());

//            if (e.getMessage().contains("socket write error")) {
//            }
        }
        return "";
    }

}
