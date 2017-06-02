package com.zhuyunjian.library;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jash
 * Date: 16-2-23
 * Time: 上午10:51
 */
public class PayResult {
    private String resultStatus;
    private String result;
    private String memo;

    public PayResult(String src) {
        Pattern pattern = Pattern.compile("([^;]*?)=\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()){
            switch (matcher.group(1)) {
                case "resultStatus":
                    resultStatus = matcher.group(2);
                    break;
                case "result":
                    result = matcher.group(2);
                    break;
                case "memo":
                    memo = matcher.group(2);
                    break;
            }
        }
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public String getResult() {
        return result;
    }

    public String getMemo() {
        return memo;
    }
}
