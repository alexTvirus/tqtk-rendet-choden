/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqtk.XuLy;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
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
}
