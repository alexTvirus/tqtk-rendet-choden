// 
// Decompiled by Procyon v0.5.36
// 
package tqtk.Utils;

import java.util.ArrayList;
import java.io.Writer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GetTextFromGit {

    public static String getStringFromGithubRaw(String path) throws MalformedURLException, IOException {
        URL crunchifyUrl = new URL(path);
        HttpURLConnection crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
        crunchifyHttp.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        crunchifyHttp.setRequestProperty("Sec-Fetch-Site", "cross-site");
        crunchifyHttp.setRequestProperty("Authorization", "Token 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");
        Map<String, List<String>> crunchifyHeader = crunchifyHttp.getHeaderFields();
        for (final String header : crunchifyHeader.get(null)) {
            if (header.contains(" 302 ") || header.contains(" 301 ")) {
                path = crunchifyHeader.get("Location").get(0);
                crunchifyUrl = new URL(path);
                crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
//                crunchifyHttp.setRequestProperty("Host", "test2-6lf7.onrender.com");
                crunchifyHttp.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
                crunchifyHttp.setRequestProperty("Sec-Fetch-Site", "cross-site");
                crunchifyHttp.setRequestProperty("Authorization", "Token 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");
                crunchifyHeader = crunchifyHttp.getHeaderFields();
            }
        }
        final InputStream crunchifyStream = crunchifyHttp.getInputStream();
        final String crunchifyResponse = getStringFromStream(crunchifyStream);
        return crunchifyResponse;
    }

//    public static List<String> getStringFromGithubRaw(String path) throws MalformedURLException, IOException {
//        URL crunchifyUrl = new URL(path);
//        HttpURLConnection crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
//        crunchifyHttp.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
//        crunchifyHttp.setRequestProperty("Sec-Fetch-Site", "cross-site");
//        crunchifyHttp.setRequestProperty("Authorization", "Token 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");
//        Map<String, List<String>> crunchifyHeader = crunchifyHttp.getHeaderFields();
//        for (final String header : crunchifyHeader.get(null)) {
//            if (header.contains(" 302 ") || header.contains(" 301 ")) {
//                path = crunchifyHeader.get("Location").get(0);
//                crunchifyUrl = new URL(path);
//                crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
////                crunchifyHttp.setRequestProperty("Host", "test2-6lf7.onrender.com");
//                crunchifyHttp.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
//                crunchifyHttp.setRequestProperty("Sec-Fetch-Site", "cross-site");
//                crunchifyHttp.setRequestProperty("Authorization", "Token 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");
//                crunchifyHeader = crunchifyHttp.getHeaderFields();
//            }
//        }
//        final InputStream crunchifyStream = crunchifyHttp.getInputStream();
//        final List<String> crunchifyResponse = getListStringFromStream(crunchifyStream);
//        return crunchifyResponse;
//    }

    public static String getStringFromStream(final InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            final Writer crunchifyWriter = new StringWriter();
            final char[] crunchifyBuffer = new char[2048];
            try {
                final Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
        }
        return "No Contents";
    }

    public static List<String> getListStringFromStream(final InputStream crunchifyStream) throws IOException {
        final List<String> temps = new ArrayList<String>();
        if (crunchifyStream != null) {
            try {
                final BufferedReader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                String read = "";
                while ((read = crunchifyReader.readLine()) != null) {
                    temps.add(read);
                }
            } finally {
                crunchifyStream.close();
            }
            return temps;
        }
        return null;
    }
}
