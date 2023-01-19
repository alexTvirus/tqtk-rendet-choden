/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqtk;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import tqtk.Entity.SessionEntity;
import tqtk.Utils.Util;
import tqtk.XuLy.Worker;
import tqtk.XuLy.login.LayThongTinSession;

/**
 *
 * @author Alex
 */
public class Tqtk {

    public static List<String> loaiTruyna = new ArrayList<>();
    public static List<Object> listruong = new ArrayList<>();

    static {
        loaiTruyna = Collections.synchronizedList(loaiTruyna);
        listruong = Collections.synchronizedList(listruong);
    }

    public static void main() {
        // TODO code application logic here
        try {
//            Properties pr = Util.loadProperties("user.properties");
//            String value = "";
//            String[] temp = new String[2];
//            for (int i = 0; (value = pr.getProperty("user" + "." + i)) != null; i++) {
//                temp = value.split("\\|");
//                LayThongTinSession.getListSession().add(new SessionEntity(temp[0], temp[1]));
//            }

            LayThongTinSession.getListSession().add(new SessionEntity("n", "p"));
            
            List<SessionEntity> ss = LayThongTinSession.getListSession();

            final ScheduledExecutorService executor = Executors.newScheduledThreadPool(ss.size());
            for (int i = 0; i < ss.size(); ++i) {
                Runnable worker = new Worker(ss.get(i));
                executor.schedule(worker, 300L, TimeUnit.MILLISECONDS);
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            System.out.println("tqtk.Tqtk.main()");
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static synchronized void sendMessage(final String ms) {
        System.out.println(ms);
    }

}
