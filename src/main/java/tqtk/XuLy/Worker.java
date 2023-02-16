package tqtk.XuLy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import tqtk.Entity.Barrack;
import tqtk.Entity.Hero;
import tqtk.Entity.JsonObject;
import tqtk.Entity.SessionEntity;
import tqtk.Tqtk;
import tqtk.Utils.Util;
import tqtk.XuLy.login.LayThongTinSession;
import static tqtk.XuLy.XuLyPacket.GuiPacket;
import static tqtk.XuLy.XuLyPacket.GuiPacketApi;

import static tqtk.XuLy.XuLyPacket.GuiPacketKhongKQ;
import static tqtk.XuLy.XuLyPacket.GuiPacketKhongKQApi;
import tqtk.exception.JsonException;

/**
 *
 * @author Alex
 */
public class Worker extends Thread {

    SessionEntity ss;
    int Truyna = 0;
    int Chiemnha = 0;
    int Chinhchien = 0;
    int Ruong = 0;
    int Mo = 0;
    int areaId = 0;

    public static Boolean isStopApi = false;

    public Worker(SessionEntity ss) {

        this.ss = ss;
    }

    public void GuiPacketDeLogin() throws InterruptedException, IOException, Exception {
        // packet duy tri dang nhap , neu muon truy cap vao 1 acc tu` nhieu noi thi , phai co'
        // 1 noi dang nhap truoc roi , luc nay se ko can chay packet  10100
        Thread.sleep(5000);
        GuiPacketKhongKQ(ss, "10100", null);
        // packet cap nhat thong tin lien tuc tu server
        Thread.sleep(5000);
        CapNhatThongTin();
        Thread.sleep(5000);
        GuiPacketKhongKQ(ss, "52103", null);
        Thread.sleep(5000);
        GuiPacketKhongKQ(ss, "10108", null);
        Thread.sleep(5000);
        GuiPacketKhongKQ(ss, "20101", null);
        Thread.sleep(5000);
        List<String> list1 = new ArrayList<>();
        list1.add(0, "1");
        StringBuilder rs1 = GuiPacket(ss, "12200", list1);
        list1 = null;

    }

    public void NhanThuong() {
        List<String> list1 = new ArrayList<>();
        list1.add(0, "17");
        list1.add(1, "1");
        try {

            Tqtk.sendMessage("nhan thuong " + ss.getStringName());
            // vo dai

            Thread.sleep(5000);
            GuiPacket(ss, "64008", null);

            // chien tich > uydanh
            Thread.sleep(5000);
            GuiPacket(ss, "12303", null);

            // thuong uy danh
            Thread.sleep(5000);
            GuiPacket(ss, "12302", null);

            // event mo ruong
            Thread.sleep(5000);
            GuiPacket(ss, "48113", list1);

            // nhiem vu thuong ngay
            Thread.sleep(5000);
            list1.remove(1);
            list1.set(0, "1");
            StringBuilder s = GuiPacket(ss, "49007", list1);
            list1.set(0, "2");
            s = GuiPacket(ss, "49007", list1);
            list1.set(0, "3");
            s = GuiPacket(ss, "49007", list1);
            list1.set(0, "4");
            s = GuiPacket(ss, "49007", list1);
            list1.set(0, "5");
            s = GuiPacket(ss, "49007", list1);

        } catch (Exception e) {
            System.out.println("NhanThuong " + ss.getStringName() + e.getMessage());
        }
    }

    public void LuyenTuong() {
        List<String> list1 = new ArrayList<>();
        list1.add(0, "0");
        List<String> list2 = new ArrayList<>();
        list2.add(0, "0");
        list2.add(1, "1");
        list2.add(2, "1");

        try {
            StringBuilder rs1 = GuiPacket(ss, "41100", list1);
            if (rs1 != null) {
                try {
                    String[] temp = rs1.toString().split("");

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> carMap = null;
                    int h = 0;
                    for (String string : temp) {
                        carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                        });
                        h = (int) carMap.get("h");
                        if (h == Integer.parseInt("41100")) {
                            break;
                        }
                    }

                    List<Object> carMap1 = (List<Object>) ((Map<String, Object>) carMap.get("m")).get("general");
                    int idhero = 0;
                    String name = "";
                    int trainflag = 0;
                    int lvhero = 0;

                    list1.add(1, "1");
                    Tqtk.sendMessage("lt " + ss.getStringName());
                    for (Object object : carMap1) {

                        idhero = (int) ((Map<Object, Object>) object).get("generalid");
                        name = (String) ((Map<Object, Object>) object).get("generalname");
                        trainflag = (int) ((Map<Object, Object>) object).get("trainflag");
                        lvhero = (int) ((Map<Object, Object>) object).get("generallevel");
                        list1.set(0, Integer.toString(idhero));
                        list2.set(0, Integer.toString(idhero));
                        Tqtk.sendMessage("lt " + name);
                        if (name != null) {
                            if ("武田信虎--".equals(name) && trainflag == 0) {
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("井伊直盛 --".equals(name) && trainflag == 0) {
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("織田信行".equals(name) && trainflag == 0) {
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("彦坂元正 --".equals(name) && trainflag == 0) {
                                rs1 = GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                rs1 = GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("大久保忠員 --".equals(name) && trainflag == 0) {
                                rs1 = GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                rs1 = GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("飯尾田鶴 --".equals(name) && trainflag == 0) {
                                rs1 = GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                rs1 = GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("織田信行 --".equals(name) && trainflag == 0) {
                                // truong luon
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("井伊直盛 --".equals(name) && trainflag == 0) {
                                // tu vinh
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("彦坂元正 --".equals(name) && trainflag == 0) {
                                // xe nguy
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("村上義清".equals(name) && trainflag == 0) {
                                // luu bieu

                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("島津忠直".equals(name) && trainflag == 0) {
                                // y tich
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("本多正信 --".equals(name) && trainflag == 0) {
                                // bang duc
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("大久保忠員".equals(name) && trainflag == 0) {
                                // hoa ham
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("旭姫".equals(name) && trainflag == 0) {
                                // vu co
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("德川松姫".equals(name) && trainflag == 0) {
                                // thai diem
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            } else if ("内藤正成".equals(name) && trainflag == 0) {
                                // duong tu
                                GuiPacket(ss, "41101", list1);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);
                                GuiPacket(ss, "41102", list2);
                                Thread.sleep(5000);

                            }
                        }
                    }
                } catch (Exception e) {
                    throw new JsonException();
                }

            }
        } catch (Exception ex) {
            if (!(ex instanceof JsonException)) {
                System.out.println("loi LuyenTuong " + ss.getStringName() + ex.getMessage());
            } else {
                System.out.println("loi LuyenTuong json" + ss.getStringName() + ex.getMessage());
            }

        }

    }

    public void TruyNa() {
        List<String> list1 = new ArrayList<>();
        list1.add(0, "0");
        try {
            Thread.sleep(5000);
            StringBuilder rs1 = GuiPacket(ss, "60605", null);
            if (rs1 != null) {
                try {
                    String[] temp = rs1.toString().split("");

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> carMap = null;
                    int h = 0;
                    for (String string : temp) {
                        carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                        });
                        h = (int) carMap.get("h");
                        if (h == Integer.parseInt("60605")) {
                            break;
                        }
                    }

                    List<Object> carMap1 = (List<Object>) ((Map<String, Object>) carMap.get("m")).get("wantedMemberList");
                    int idarea = 0;
                    int id = 0;
                    int attactNum = 0;
                    long playerId = 0;
                    Tqtk.sendMessage("tn " + ss.getStringName());
                    for (Object object : carMap1) {
                        idarea = (int) ((Map<Object, Object>) object).get("areaId");
                        id = (int) ((Map<Object, Object>) object).get("id");
                        attactNum = (int) ((Map<Object, Object>) object).get("attactNum");
                        playerId = (long) ((Map<Object, Object>) object).get("playerId");
                        list1.set(0, Integer.toString(id));

                        //synchronized (tqtk.Tqtk.loaiTruyna) {
                        if (id != 0 && !Tqtk.loaiTruyna.contains(Long.toString(playerId)) && attactNum != 0 && idarea == -1) {
                            rs1 = GuiPacket(ss, "60606", list1);
                            Thread.sleep(5000);
                            if (rs1.toString().contains("đã thất bại khi chiến đấu")) {
                                tqtk.Tqtk.loaiTruyna.add(Long.toString(playerId));
                            }

                        }
                        //}
                    }
                } catch (Exception e) {
                    throw new JsonException();
                }

                //}
            }
        } catch (Exception ex) {
            if (!(ex instanceof JsonException)) {

                System.out.println("loi TruyNa " + ss.getStringName() + ex.getMessage());
            } else {

                System.out.println("loi TruyNa json " + ss.getStringName() + ex.getMessage());
            }
        }

    }

    public void NangNha() {
        List<String> list1 = new ArrayList<>();

        try {

            list1.add(0, "5");
            Thread.sleep(5000);
            StringBuilder rs1 = GuiPacket(ss, "12100", list1);
            Tqtk.sendMessage("nang nha " + ss.getStringName());
            // nang cua tiem
            list1.set(0, "6");
            Thread.sleep(5000);
            rs1 = GuiPacket(ss, "12100", list1);

            // nha chinh
            list1.set(0, "1");
            Thread.sleep(5000);
            rs1 = GuiPacket(ss, "12100", list1);

            list1.set(0, "3");
            Thread.sleep(5000);
            rs1 = GuiPacket(ss, "12100", list1);

            list1.set(0, "11");
            Thread.sleep(5000);
            rs1 = GuiPacket(ss, "12100", list1);

            list1.set(0, "12");
            Thread.sleep(5000);
            rs1 = GuiPacket(ss, "12100", list1);

            list1.set(0, "9");
            Thread.sleep(5000);
            rs1 = GuiPacket(ss, "12100", list1);

        } catch (Exception e) {
            System.out.println("NangNha " + ss.getStringName() + e.getMessage());
        }
    }

    public void NangKiNang() {
        List<String> list1 = new ArrayList<>();
        try {
            //binh khi , 42200 ,3
            list1.add(0, "13");
            //Thread.sleep(5000);
            //GuiPacket(ss, "42200", list1);

            Tqtk.sendMessage("nang kn " + ss.getStringName());
            // nang tran ne
            list1.set(0, "1");
            //Thread.sleep(5000);
            //GuiPacket(ss, "42200", list1);
            // nang ki nang , vu khi , 42200 ,1
            //list1.set(0, "2");
            //Thread.sleep(5000);
            //GuiPacket(ss, "42200", list1);
            // lenh ki , 42200 ,2
            //list1.set(0, "3");
            //Thread.sleep(5000);
            //GuiPacket(ss, "42200", list1);
            // lenh ki , 42200 ,2
            list1.set(0, "4");
            Thread.sleep(5000);
            GuiPacket(ss, "42200", list1);
            //  giap , 42200 ,4
            list1.set(0, "5");
            Thread.sleep(5000);
            GuiPacket(ss, "42200", list1);
            //  xung phong , 42200 ,5
            //list1.set(0, "6");
            //Thread.sleep(5000);
            //GuiPacket(ss, "42200", list1);

            //list1.set(0, "8");
            //Thread.sleep(5000);
            //GuiPacket(ss, "42200", list1);
            list1.set(0, "10");
            Thread.sleep(5000);
            GuiPacket(ss, "42200", list1);

        } catch (Exception e) {
            System.out.println("NangKiNang " + ss.getStringName() + e.getMessage());
        }
    }

    public void MuaLinh() {
        List<String> list1 = new ArrayList<>();
        try {

            list1.add(0, "1000");
            list1.add(1, "57");
            Thread.sleep(5000);
            GuiPacket(ss, "14102", list1);
            Tqtk.sendMessage("mualinh " + ss.getStringName());
            Thread.sleep(5000);
            GuiPacket(ss, "14100", null);
            Thread.sleep(5000);

        } catch (Exception e) {
            System.out.println("MuaLinh " + ss.getStringName() + e.getMessage());
        }
    }

    public void DanhQuanDoan() {
        // danh quan doan vu van thi toc
        List<String> list1 = new ArrayList<>();
        list1.add(0, "900038");
        List<String> list2 = new ArrayList<>();
        list2.add(0, "0");

        try {
            StringBuilder rs1 = GuiPacket(ss, "34100", list1);
            Thread.sleep(5000);
            if (rs1 != null) {
                try {
                    String[] temp = rs1.toString().split("");

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> carMap = null;
                    int h = 0;
                    for (String string : temp) {
                        carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                        });
                        h = (int) carMap.get("h");
                        if (h == Integer.parseInt("34100")) {
                            break;
                        }
                    }

                    List<Object> carMap1 = (List<Object>) ((Map<String, Object>) carMap.get("m")).get("team");
                    long idhero = 0;

                    Tqtk.sendMessage("qd " + ss.getStringName());

                    if (carMap1 != null && carMap1.size() > 0) {
                        Object object = carMap1.get(0);
                        idhero = (long) ((Map<Object, Object>) object).get("teamid");
                        list2.set(0, Long.toString(idhero));

                        rs1 = GuiPacket(ss, "34102", list2);
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    throw new JsonException();
                }

            }
        } catch (Exception ex) {
            if (!(ex instanceof JsonException)) {
                System.out.println("loi qd " + ss.getStringName() + ex.getMessage());
            } else {
                System.out.println("loi qd json" + ss.getStringName() + ex.getMessage());
            }

        }
    }

    public void DanhQuanDoan1() {
        if (ss != null && ss.getStringName().equals("hacklslol7@gmail.com")) {
            // danh quan doan duong binh
            List<String> list1 = new ArrayList<>();
            list1.add(0, "900016");
//            list1.add(1, "4:0;1");

            List<String> list2 = new ArrayList<>();
            list2.add(0, "0");

            try {
                StringBuilder rs1 = GuiPacket(ss, "34100", list1);
                Thread.sleep(5000);
                if (rs1 != null) {
                    try {
                        String[] temp = rs1.toString().split("");

                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> carMap = null;
                        int h = 0;
                        for (String string : temp) {
                            carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                            });
                            h = (int) carMap.get("h");
                            if (h == Integer.parseInt("34100")) {
                                break;
                            }
                        }

                        List<Object> carMap1 = (List<Object>) ((Map<String, Object>) carMap.get("m")).get("team");
                        long idhero = 0;

                        Tqtk.sendMessage("qd " + ss.getStringName());

                        if (carMap1 != null && carMap1.size() > 0) {
                            Object object = carMap1.get(0);
                            idhero = (long) ((Map<Object, Object>) object).get("teamid");
                            list2.set(0, Long.toString(idhero));

                            rs1 = GuiPacket(ss, "34102", list2);
                            Thread.sleep(5000);
                        }
                    } catch (Exception e) {
                        throw new JsonException();
                    }

                }
            } catch (Exception ex) {
                if (!(ex instanceof JsonException)) {
                    System.out.println("loi qd " + ss.getStringName() + ex.getMessage());
                } else {
                    System.out.println("loi qd json" + ss.getStringName() + ex.getMessage());
                }

            }
        }
    }

    public void DanhQuanDoan2() {

        if (ss != null && ss.getStringName().equals("hacklslol7@gmail.com")) {
            // danh quan doan duong binh
            List<String> list1 = new ArrayList<>();
            list1.add(0, "900015");
            List<String> list2 = new ArrayList<>();
            list2.add(0, "0");

            try {
                StringBuilder rs1 = GuiPacket(ss, "34100", list1);
                Thread.sleep(5000);
                if (rs1 != null) {
                    try {
                        String[] temp = rs1.toString().split("");

                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> carMap = null;
                        int h = 0;
                        for (String string : temp) {
                            carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                            });
                            h = (int) carMap.get("h");
                            if (h == Integer.parseInt("34100")) {
                                break;
                            }
                        }

                        List<Object> carMap1 = (List<Object>) ((Map<String, Object>) carMap.get("m")).get("team");
                        long idhero = 0;

                        Tqtk.sendMessage("qd " + ss.getStringName());

                        if (carMap1 != null && carMap1.size() > 0) {
                            Object object = carMap1.get(0);
                            idhero = (long) ((Map<Object, Object>) object).get("teamid");
                            list2.set(0, Long.toString(idhero));

                            rs1 = GuiPacket(ss, "34102", list2);
                            Thread.sleep(5000);
                        }
                    } catch (Exception e) {
                        throw new JsonException();
                    }

                }
            } catch (Exception ex) {
                if (!(ex instanceof JsonException)) {
                    System.out.println("loi qd " + ss.getStringName() + ex.getMessage());
                } else {
                    System.out.println("loi qd json" + ss.getStringName() + ex.getMessage());
                }

            }

        }

    }

    public void DanhQuanDoan3() {

        if ((ss != null && ss.getStringName().contains("@gmail.com") && !ss.getStringName().equals("hacklslol7@gmail.com")) || ss != null && ss.getStringName().contains("itf.edu.vn")) {
            // danh quan doan duong binh
            List<String> list1 = new ArrayList<>();
            list1.add(0, "900013");
            List<String> list2 = new ArrayList<>();
            list2.add(0, "0");

            try {
                StringBuilder rs1 = GuiPacket(ss, "34100", list1);
                Thread.sleep(5000);
                if (rs1 != null) {
                    try {
                        String[] temp = rs1.toString().split("");

                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> carMap = null;
                        int h = 0;
                        for (String string : temp) {
                            carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                            });
                            h = (int) carMap.get("h");
                            if (h == Integer.parseInt("34100")) {
                                break;
                            }
                        }

                        List<Object> carMap1 = (List<Object>) ((Map<String, Object>) carMap.get("m")).get("team");
                        long idhero = 0;

                        Tqtk.sendMessage("qd " + ss.getStringName());

                        if (carMap1 != null && carMap1.size() > 0) {
                            Object object = carMap1.get(0);
                            idhero = (long) ((Map<Object, Object>) object).get("teamid");
                            list2.set(0, Long.toString(idhero));

                            rs1 = GuiPacket(ss, "34102", list2);
                            Thread.sleep(5000);
                        }
                    } catch (Exception e) {
                        throw new JsonException();
                    }

                }
            } catch (Exception ex) {
                if (!(ex instanceof JsonException)) {
                    System.out.println("loi qd " + ss.getStringName() + ex.getMessage());
                } else {
                    System.out.println("loi qd json" + ss.getStringName() + ex.getMessage());
                }

            }

        }

    }

    public void ThuThue() {
        // danh quan doan vu van thi toc
        List<String> list1 = new ArrayList<>();
        list1.add(0, "0");
        list1.add(1, "0");
        List<String> list2 = new ArrayList<>();
        list2.add(0, "0");

        try {
            StringBuilder rs1 = GuiPacket(ss, "12401", list1);
            Thread.sleep(5000);
            if (rs1 != null) {
                try {
                    String[] temp = rs1.toString().split("");

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> carMap = null;
                    int h = 0;
                    for (String string : temp) {
                        carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                        });
                        h = (int) carMap.get("h");
                        if (h == Integer.parseInt("12401")) {
                            break;
                        }
                    }

                    Map<String, Object> carMap1 = (Map<String, Object>) ((Map<String, Object>) carMap.get("m")).get("larrydto");

                    Tqtk.sendMessage("thuthue " + ss.getStringName());

                    if (carMap1 != null) {
                        String optdisc1 = (String) carMap1.get("optdisc1");
                        String optdisc2 = (String) carMap1.get("optdisc2");

                        list2.set(0, "1");
                        rs1 = GuiPacket(ss, "12406", list2);
                        Thread.sleep(5000);

                    }
                } catch (Exception e) {
                    throw new JsonException();
                }

            }
        } catch (Exception ex) {
            if (!(ex instanceof JsonException)) {
                System.out.println("loi qd " + ss.getStringName() + ex.getMessage());
            } else {
                System.out.println("loi qd json" + ss.getStringName() + ex.getMessage());
            }

        }
    }

    public void FarmDo() {
        if (false && ss != null && ss.getStringName().equals("hacklslol7@gmail.com")) {
            // danh map ngo
            List<String> list1 = new ArrayList<>();
            list1.add(0, "2817");
            list1.add(1, "0");

            try {
                StringBuilder rs1 = GuiPacket(ss, "33101", list1);
                Thread.sleep(5000);

            } catch (Exception ex) {
                System.out.println("loi FarmDo " + ss.getStringName() + ex.getMessage());
            }
        } else if (ss != null && ss.getStringName().contains("@gmail.com") && !ss.getStringName().equals("hacklslol7@gmail.com")) {
            // danh lu bo
            List<String> list1 = new ArrayList<>();
            list1.add(0, "2216");
            list1.add(1, "0");

            try {
                StringBuilder rs1 = GuiPacket(ss, "33101", list1);
                Thread.sleep(5000);
                list1.set(0, "2529");
                GuiPacket(ss, "33101", list1);
                Thread.sleep(5000);

            } catch (Exception ex) {
                System.out.println("loi FarmDo " + ss.getStringName() + ex.getMessage());
            }
        }

    }

    public void NangItem() throws IOException, UnknownHostException, InterruptedException, Exception {
        List<String> list1 = new ArrayList<>();
        list1.add(0, "0");
        list1.add(1, "0");
        list1.add(2, "30");
        try {
            Thread.sleep(5000);
            StringBuilder rs1 = GuiPacket(ss, "39301", list1);
            if (rs1 != null && rs1.toString() != "") {
                try {
                    String[] temp = rs1.toString().split("");

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> carMap = null;
                    int h = 0;
                    for (String string : temp) {
                        carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                        });
                        h = (int) carMap.get("h");
                        if (h == Integer.parseInt("39301")) {
                            break;
                        }
                    }
                    List<Object> carMap1 = (List<Object>) ((Map<String, Object>) carMap.get("m")).get("equip");
                    Object Magic = (Object) ((Map<String, Object>) carMap.get("m")).get("magic");
                    int Upgradecdusable = (int) ((Map<String, Object>) carMap.get("m")).get("upgradecdusable");
                    int lv = 0;
                    String name = "";
                    Integer storeid = 0;
                    String generalname = "";
                    Tqtk.sendMessage("nang item " + ss.getStringName());
                    for (Object object : carMap1) {
                        lv = (int) ((Map<Object, Object>) object).get("equipAllLevel");
                        name = (String) ((Map<Object, Object>) object).get("equipname");
                        storeid = (Integer) ((Map<Object, Object>) object).get("storeid");
                        generalname = (String) ((Map<Object, Object>) object).get("generalname");
                        //Tqtk.sendMessage(lv + " " + name + " " + storeid + " " + generalname);
                        list1.set(0, Integer.toString(storeid));
                        list1.set(2, Magic.toString());
                        if (generalname != null) {
                            if ((ss != null)) {
//                                if ((int) Magic > 87 && "æ­¦ç”°ä¿¡è™Ž".equals(generalname)) {
                                if ((int) Magic > 87 && "武田信虎 --".equals(generalname)) {
                                    rs1 = GuiPacket(ss, "39302", list1);
                                    Thread.sleep(5000);
                                } else if (Upgradecdusable == 1 && (int) Magic > 87 && "織田信行".equals(generalname) && lv <= 80) {
                                    rs1 = GuiPacket(ss, "39302", list1);
                                    Thread.sleep(5000);
                                } else if (Upgradecdusable == 1 && (int) Magic > 87 && "井伊直盛".equals(generalname) && lv <= 80) {
                                    rs1 = GuiPacket(ss, "39302", list1);
                                    Thread.sleep(5000);
                                } else if (Upgradecdusable == 1 && (int) Magic > 87 && "大久保忠員 --".equals(generalname) && lv <= 80) {
                                    rs1 = GuiPacket(ss, "39302", list1);
                                    Thread.sleep(5000);
                                } else if (Upgradecdusable == 1 && (int) Magic > 87 && "飯尾田鶴".equals(generalname) && lv <= 80) {
                                    rs1 = GuiPacket(ss, "39302", list1);
                                    Thread.sleep(5000);

                                } else if (Upgradecdusable == 1 && (int) Magic > 87 && "内藤正成".equals(generalname) && lv <= 80) {
                                    rs1 = GuiPacket(ss, "39302", list1);
                                    Thread.sleep(5000);

                                }

                            }

                        }
                    }
                } catch (Exception e) {
                    throw new JsonException();
                }
            } else {
                dangNhapLayThongTin();
                GuiPacketDeLogin();
            }
        } catch (Exception ex) {
            if (!(ex instanceof JsonException)) {
                System.out.println("NangItem " + ss.getStringName() + ex.getMessage());
                dangNhapLayThongTin();
                GuiPacketDeLogin();
            } else {
                System.out.println("NangItem json " + ss.getStringName() + ex.getMessage());
            }

        }

    }

    public void GianKhoan() {
        List<String> list1 = new ArrayList<>();
        list1.add("3");
        list1.add("101");
        List<String> list2 = new ArrayList<>();
        list2.add("3");
        list2.add("101");
        list2.add("0");
        list2.add("0");
        // lay phan thuong khoan
        try {
            StringBuilder rs = GuiPacket(ss, "62007", list1);
            Thread.sleep(5000);
            if (rs != null) {
                if (!rs.toString().contains("Đang khai thác")) {
                    Tqtk.sendMessage("gian khoan " + ss.getStringName());
                    // bat dau khoan
                    GuiPacket(ss, "62006", list2);
                }
            } else {

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ChiemMo() {
        if (Mo == 1) {
            Mo = 0;
            List<String> list1 = new ArrayList<>();
            List<String> list2 = new ArrayList<>();
            List<String> list3 = new ArrayList<>();
            list3.add(0, Integer.toString(areaId));
            list3.add(1, "1");
            list2.add(0, "0");
            list2.add(1, "0");
            list2.add(2, "0");
            list1.add(0, "0");
            list1.add(1, "0");
            list1.add(2, "0");

            try {
                StringBuilder rs1 = GuiPacket(ss, "31102", list3);
                Thread.sleep(5000);
                if (rs1 != null) {
                    try {
                        String[] temp = rs1.toString().split("");

                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> carMap = null;
                        int h = 0;
                        for (String string : temp) {
                            carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                            });
                            h = (int) carMap.get("h");
                            if (h == Integer.parseInt("31102")) {
                                break;
                            }
                        }
                        List<Object> listmo = (List<Object>) ((Map<String, Object>) carMap.get("m")).get("city");

                        Tqtk.sendMessage("chiem mo " + ss.getStringName());
                        Object playerId = 0;
                        int resourceid = 0;
                        if (listmo != null) {
                            for (Object object : listmo) {
                                playerId = ((Map<Object, Object>) object).get("playerid");
                                resourceid = (int) ((Map<Object, Object>) object).get("index");
                                if (playerId instanceof Integer) {
                                    break;
                                }
                            }
                        }
                        list2.set(0, Integer.toString(areaId));
                        list2.set(1, Integer.toString(1));
                        list2.set(2, Integer.toString(resourceid));
                        GuiPacket(ss, "31107", list2);
                        list1.set(0, Integer.toString(resourceid));
                        list1.set(1, "1");
                        list1.set(2, "1");
                        GuiPacket(ss, "31109", list1);
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        throw new JsonException();
                    }

                }
            } catch (Exception ex) {
                if (!(ex instanceof JsonException)) {
                    System.out.println("loi qd " + ss.getStringName() + ex.getMessage());
                } else {
                    System.out.println("loi qd json" + ss.getStringName() + ex.getMessage());
                }

            }
        }
    }

    public void ChiemRuong() {
        if (Ruong == 1) {
            Ruong = 0;
            List<String> list1 = new ArrayList<>();
            List<String> list2 = new ArrayList<>();
            list2.add(0, "0");
            list1.add(0, "0");
            list1.add(0, "0");
            list1.add(0, "0");

            try {
                StringBuilder rs1 = GuiPacket(ss, "31103", null);
                Thread.sleep(5000);
                if (rs1 != null) {
                    try {
                        String[] temp = rs1.toString().split("");

                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> carMap = null;
                        int h = 0;
                        for (String string : temp) {
                            carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                            });
                            h = (int) carMap.get("h");
                            if (h == Integer.parseInt("31103")) {
                                break;
                            }
                        }
                        synchronized (tqtk.Tqtk.listruong) {
                            tqtk.Tqtk.listruong = (List<Object>) ((Map<String, Object>) carMap.get("m")).get("resource");

                            Tqtk.sendMessage("chiem ruong " + ss.getStringName());
                            Object playerId = 0;
                            int resourceid = 0;
                            if (tqtk.Tqtk.listruong != null) {
                                for (Object object : tqtk.Tqtk.listruong) {
                                    playerId = ((Map<Object, Object>) object).get("playerid");
                                    resourceid = (int) ((Map<Object, Object>) object).get("resourceid");
                                    if (playerId instanceof Integer) {
                                        break;
                                    }
                                }
                            }
                            list2.set(0, Integer.toString(resourceid));
                            GuiPacket(ss, "31104", list2);
                            list1.set(0, Integer.toString(resourceid));
                            list1.set(1, "2");
                            list1.set(2, "0");
                            GuiPacket(ss, "31109", list1);
                            Thread.sleep(5000);
                        }
                    } catch (Exception e) {
                        throw new JsonException();
                    }

                }
            } catch (Exception ex) {
                if (!(ex instanceof JsonException)) {
                    System.out.println("loi qd " + ss.getStringName() + ex.getMessage());
                } else {
                    System.out.println("loi qd json" + ss.getStringName() + ex.getMessage());
                }

            }
        }
    }

    public void CapNhatThongTin() throws IOException, UnknownHostException, InterruptedException, Exception {
        try {
            Thread.sleep(5000);
            String rs1 = tqtk.XuLy.XuLyPacket.GuiPacket1(ss, "11102", null);
            if (rs1 != null && rs1 != "") {
                try {
                    String[] temp = rs1.toString().split("");

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> carMap = null;
                    int h = 0;
                    for (String string : temp) {
                        carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                        });
                        h = (int) carMap.get("h");
                        if (h == Integer.parseInt("11102")) {
                            break;
                        }
                    }
                    Object player = (Object) ((Map<String, Object>) carMap.get("m")).get("player");
                    System.out.println("CapNhatThongTin " + ss.getStringName());
                    Truyna = (int) ((Map<Object, Object>) player).get("extragongji");
                    Chinhchien = (int) ((Map<Object, Object>) player).get("extrazhengzhan");
                    Ruong = (int) ((Map<Object, Object>) player).get("extranongtian");
                    Mo = (int) ((Map<Object, Object>) player).get("extrayinkuang");
                    areaId = (int) ((Map<Object, Object>) player).get("areaId");
                } catch (Exception e) {
                    throw new JsonException();
                }
            }
        } catch (Exception ex) {
            if (!(ex instanceof JsonException)) {
                System.out.println("CapNhatThongTin " + ss.getStringName() + ex.getMessage());
                dangNhapLayThongTin();
                GuiPacketDeLogin();
            } else {
                System.out.println("CapNhatThongTin " + ss.getStringName() + ex.getMessage());
            }

        }

    }

    public void dangNhapLayThongTin() throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getByName(ss.getIp()), ss.getPorts()), 7000);
        if (ss.getSocket() != null) {
            ss.getSocket().close();
        }
        ss.setSocket(socket);
    }

    public static void createSocket(SessionEntity session) throws Exception {
        Socket socket1 = new Socket();
        socket1.connect(new InetSocketAddress(InetAddress.getByName(session.getIp()), session.getPorts()), 7000);
        socket1.setSoTimeout(350000);
        try {
            session.getSocketApi().close();
        } catch (Exception e) {
        }
        session.setSocketApi(socket1);

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getByName(session.getIp()), session.getPorts()), 7000);
        socket.setSoTimeout(350000);
        try {
            session.getSocket().close();
        } catch (Exception e) {
        }
        session.setSocket(socket);
    }

    public static void createSocketApi(SessionEntity session) throws Exception {
        Socket socket1 = new Socket();
        socket1.connect(new InetSocketAddress(InetAddress.getByName(session.getIp()), session.getPorts()), 7000);
        socket1.setSoTimeout(350000);
        try {
            session.getSocketApi().close();
        } catch (Exception e) {
        }
        session.setSocketApi(socket1);

        Thread.sleep(2000);
        GuiPacketKhongKQApi(session, "11102", null);
        Thread.sleep(2000);
        GuiPacketKhongKQApi(session, "52103", null);
        Thread.sleep(2000);
        GuiPacketKhongKQApi(session, "10108", null);
        Thread.sleep(2000);
        GuiPacketKhongKQApi(session, "20101", null);
        Thread.sleep(2000);
        List<String> list1 = new ArrayList<>();
        list1.add(0, "1");
        GuiPacketKhongKQApi(session, "12200", list1);
        list1 = null;

    }

    public void createSocketApi1(SessionEntity session) throws Exception {
        Socket socket1 = new Socket();
        socket1.setSoTimeout(350000000);
        socket1.setKeepAlive(true);
        socket1.connect(new InetSocketAddress(InetAddress.getByName(session.getIp()), session.getPorts()), 7000);

        try {
            session.getSocketApi().close();
        } catch (Exception e) {
        }
        session.setSocketApi(socket1);

//        Thread.sleep(2000);
        GuiPacketKhongKQApi(session, "11102", null);
//        Thread.sleep(2000);
//        GuiPacketKhongKQApi(session, "52103", null);
//        Thread.sleep(2000);
//        GuiPacketKhongKQApi(session, "10108", null);
//        Thread.sleep(2000);
//        GuiPacketKhongKQApi(session, "20101", null);
//        Thread.sleep(2000);
//        List<String> list1 = new ArrayList<>();
//        list1.add(0, "1");
//        GuiPacketKhongKQApi(session, "12200", list1);
//        list1 = null;

    }

    public void spamApi() {

        try {
            while (true) {
                synchronized (isStopApi) {
                    if (!isStopApi) {
                        try {
                            GuiPacketApi(ss, "11102", null);
                        } catch (Exception e) {
                             System.out.println("spamApi1 " + ss.getStringName() + e.getMessage());
                        }

                    }
                }
                Thread.sleep(40000);
            }

        } catch (Exception e) {
            System.out.println("spamApi " + ss.getStringName() + e.getMessage());
        }
    }
    
    public void ChoDen() throws IOException, UnknownHostException, InterruptedException, Exception {
        List<String> list1 = new ArrayList<>();
        list1.add(0, "0");
        list1.add(1, "0");
        list1.add(2, "0");
        list1.add(3, "0");
        try {
            Thread.sleep(5000);
            StringBuilder rs1 = GuiPacket(ss, "48116", list1);
            if (rs1 != null && rs1.toString() != "") {
                try {
                    String[] temp = rs1.toString().split("");

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> carMap = null;
                    int h = 0;
                    for (String string : temp) {
                        carMap = mapper.readValue(string, new TypeReference<Map<String, Object>>() {
                        });
                        h = (int) carMap.get("h");
                        if (h == Integer.parseInt("48116")) {
                            break;
                        }
                    }
                    List<Object> carMap1 = (List<Object>) ((Map<String, Object>) carMap.get("m")).get("goods");
                       System.out.println("cho den " + ss.getStringName());
                    Integer costType = 0;
                    Integer cost = 0;
                    String name = "";
                    Integer modelId = 0;
                    Integer goodsIndex = 0;

                    Tqtk.sendMessage("cho den " + ss.getStringName());
                    list1.remove(3);
                    list1.remove(2);
                    for (Object object : carMap1) {
                        costType = (int) ((Map<Object, Object>) object).get("costType");
                        cost = (int) ((Map<Object, Object>) object).get("cost");
                        name = (String) ((Map<Object, Object>) object).get("name");
                        goodsIndex = (int) ((Map<Object, Object>) object).get("goodsIndex");
                        modelId = (int) ((Map<Object, Object>) object).get("modelId");
                        //Tqtk.sendMessage(lv + " " + name + " " + storeid + " " + generalname);
                        if (name.contains("羽")
                                && costType == 2
                                && 20000 < cost ) {
                            list1.set(0, Integer.toString(modelId));
                            list1.set(1, Integer.toString(goodsIndex));
                            GuiPacket(ss, "48118", list1);
                        }

                        if (name.contains("兜")
                                && costType == 2
                                && 20000 < cost ) {
                            list1.set(0, Integer.toString(modelId));
                            list1.set(1, Integer.toString(goodsIndex));
                            GuiPacket(ss, "48118", list1);
                        }
                    }
                } catch (Exception e) {
                    throw new JsonException();
                }
            }
        } catch (Exception ex) {
            if (!(ex instanceof JsonException)) {
                System.out.println("cho den " + ss.getStringName() + ex.getMessage());
             
            } else {
                System.out.println("cho den json " + ss.getStringName() + ex.getMessage());
            }

        }

    }

    @Override
    public void run() {
        try {
            dangNhapLayThongTin();
            GuiPacketDeLogin();
            while (true) {
                ChoDen();
                Thread.sleep(40 * 1000);
                CapNhatThongTin();
            }
        } catch (Exception ex) {
            System.out.println("all " + ss.getStringName() + ex.getMessage());
        }
    }

}
