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
import tqtk.Utils.GetTextFromGit;
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

            List<SessionEntity> ss = LayThongTinSession.getListSession();
            String x = GetTextFromGit.getStringFromGithubRaw("https://raw.githubusercontent.com/alexTvirus/Static-Resource/main/choden");
            Util.setData1(x, ss);
            //Util.setData("user.properties", ss);

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
