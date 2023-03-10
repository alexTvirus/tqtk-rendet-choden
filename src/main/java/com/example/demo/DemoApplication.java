package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import tqtk.Entity.SessionEntity;
import tqtk.Entity.Params;
import tqtk.Tqtk;
import tqtk.XuLy.Worker;
import static tqtk.XuLy.Worker.isStopApi;
import tqtk.XuLy.XuLyPacket;
import static tqtk.XuLy.XuLyPacket.GuiPacketApi;
import tqtk.XuLy.login.LayThongTinSession;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static int count = 0;

    public static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("exit")
    public String getExit() throws IOException {
        List<SessionEntity> ss = LayThongTinSession.getListSession();
        for (int i = 0; i < ss.size(); ++i) {
            ss.get(i).getSocket().close();
            ss.get(i).getSocketApi().close();
        }
        SpringApplication.exit(context);
        return "exit";
    }

    @GetMapping("count")
    public String getCountUsers() {
        count++;
        return "Number of users = " + count;
    }

    @GetMapping("start")
    public String start() {
        if (LayThongTinSession.getListSession().size() < 1) {
            Thread t = new Thread() {
                public void run() {
                    tqtk.Tqtk.main();
                }
            };
            t.start();
        }
        return "ok";
    }

    @RequestMapping(value = "/sendcmd", method = RequestMethod.POST)
    @ResponseBody
    public String sendcmd(@RequestBody String cmd,
            @RequestParam(value = "id", required = true) String id) throws IOException, UnknownHostException, InterruptedException {
        List<SessionEntity> ss = LayThongTinSession.getListSession();
        for (int i = 0; i < ss.size(); ++i) {
            if (id.equals(ss.get(i).getUserId())) {
                XuLyPacket.GuiPacketHTTP(ss.get(i), cmd);
                return "ok";
            }
        }
        return "not-ok";
    }

    @RequestMapping(value = "/sendcmd1", method = RequestMethod.POST)
    @ResponseBody
    public String sendcmd1(@RequestBody Params cmd,
            @RequestParam(value = "id", required = true) String id) throws IOException, UnknownHostException, InterruptedException {
//        new Thread() {
//            @Override
//            public void run() {
//                List<SessionEntity> ss = LayThongTinSession.getListSession();
//                for (int i = 0; i < ss.size(); ++i) {
//                    if (id.equals(ss.get(i).getUserId())) {
//                        try {
//                            XuLyPacket.GuiPacketHTTP2(ss.get(i), cmd);
//                        } catch (IOException ex) {
//                            Logger.getLogger(DemoApplication.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(DemoApplication.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//            }
//        }.start();
        List<SessionEntity> ss = LayThongTinSession.getListSession();
        for (int i = 0; i < ss.size(); ++i) {
            if (id.equals(ss.get(i).getUserId())) {
                try {
                    XuLyPacket.GuiPacketHTTP2(ss.get(i), cmd);
                    return "ok";
                } catch (IOException ex) {
                    Logger.getLogger(DemoApplication.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DemoApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "not-ok";
    }

    @GetMapping("createSocket")
    public String createSocket(@RequestParam(value = "id", required = true) String id) throws Exception {
        String output = "";
        List<SessionEntity> ss = LayThongTinSession.getListSession();
        for (int i = 0; i < ss.size(); ++i) {
            if (id.equals(ss.get(i).getUserId())) {
                Worker.createSocket(ss.get(i));
                return "ok";
            }
        }
        return "not-ok";
    }

    @GetMapping("createSocketapi")
    public String createSocketApi(@RequestParam(value = "id", required = true) String id) throws Exception {
        String output = "";
        List<SessionEntity> ss = LayThongTinSession.getListSession();
        for (int i = 0; i < ss.size(); ++i) {
            if (id.equals(ss.get(i).getUserId())) {
                Worker.createSocketApi(ss.get(i));
                return "ok";
            }
        }
        return "not-ok";
    }

    @GetMapping("setSessionId")
    public String setSessionId(@RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "session", required = true) String session) throws Exception {
        String output = "";
        List<SessionEntity> ss = LayThongTinSession.getListSession();
        for (int i = 0; i < ss.size(); ++i) {
            if (id.equals(ss.get(i).getUserId())) {
                ss.get(i).setSessionKey(session);
                return "ok";
            }
        }
        return "not-ok";
    }

    @GetMapping("stopApiSpam")
    public String stopApiSpam() throws Exception {
        synchronized (isStopApi) {
            isStopApi = true;
        }
        return "ok";
    }
    
      @GetMapping("startApiSpam")
    public String startApiSpam() throws Exception {
        synchronized (isStopApi) {
            isStopApi = false;
        }
        return "ok";
    }

    @GetMapping("cmd")
    public String greeding(@RequestParam(value = "cmd", required = true) String cmd) {
        String output = "";
        try {
            output = executeCommand(cmd);
            return output;
        } catch (Exception e) {
            e.getMessage();
            return e.getMessage();
        }

    }

    public String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

}
