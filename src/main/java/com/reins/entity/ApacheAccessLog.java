package com.reins.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Slf4j
public class ApacheAccessLog implements Serializable {
    private String ipAddress;
    private String clientIdentd;
    private String userID;
    private String dateTimeString;
    private String method;
    private String endpoint;
    private String protocol;
    private int responseCode;
    private long contentSize;

    private ApacheAccessLog(String ipAddress, String clientIdentd, String userID,
                            String dateTime, String method, String endpoint,
                            String protocol, String responseCode,
                            String contentSize) {
        this.ipAddress = ipAddress;
        this.clientIdentd = clientIdentd;
        this.userID = userID;
        this.dateTimeString = dateTime;
        this.method = method;
        this.endpoint = endpoint;
        this.protocol = protocol;
        this.responseCode = Integer.parseInt(responseCode);
        this.contentSize = Long.parseLong(contentSize);
    }

    // Example Apache log line:
    //   127.0.0.1 - - [21/Jul/2014:9:55:27 -0800] "GET /home.html HTTP/1.1" 200 2048
    private static final String LOG_ENTRY_PATTERN =
            // 1:IP  2:client 3:user 4:date time                   5:method 6:req 7:proto   8:respcode 9:size
            "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+)";
    private static final Pattern PATTERN = Pattern.compile(LOG_ENTRY_PATTERN);

    public static ApacheAccessLog parseFromLogLine(String logline) {
        Matcher m = PATTERN.matcher(logline);
        if (!m.find()) {
            log.info("Cannot parse logline:{}", logline);
            throw new RuntimeException("Error parsing logline");
        }

        return new ApacheAccessLog(m.group(1), m.group(2), m.group(3), m.group(4),
                m.group(5), m.group(6), m.group(7), m.group(8), m.group(9));
    }

    @Override public String toString() {
        return String.format("%s %s %s [%s] \"%s %s %s\" %s %s",
                ipAddress, clientIdentd, userID, dateTimeString, method, endpoint,
                protocol, responseCode, contentSize);
    }
}
