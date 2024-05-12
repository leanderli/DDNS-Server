package cloud.jillion.toolkit.ddns.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leanderli
 * @see
 * @since 2020.04.16
 */
public class NetworkUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkUtils.class);

    private static final int TIME_OUT_MILLS = 3000;
    private static final String PATTERN = "\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>";

    public static boolean ping(String ipAddress) throws Exception {
        // 当返回值是true时，说明host是可用的，false则不可。
        return InetAddress.getByName(ipAddress).isReachable(TIME_OUT_MILLS);
    }

    /**
     * 获取本机的外网ip地址
     *
     * @return IP
     */
    public static String getV4Ip() {
        String ip = "";
        String chinaz = "http://ip.chinaz.com";
        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            while ((read = in.readLine()) != null) {
                inputLine.append(read).append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile(PATTERN);
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            ip = m.group(1);
        }
        if (StringUtils.isBlank(ip)) {
            LOGGER.error("Query new ip failed.Current ip is empty");
            return null;
        } else {
            return ip;
        }
    }

}
