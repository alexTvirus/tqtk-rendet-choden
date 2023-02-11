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

/**
 *
 * @author Alex
 */
public class XuLyPacket {

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
}
