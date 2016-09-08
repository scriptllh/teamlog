package com.wiseach.teamlog.utils;

import com.mysql.jdbc.StringUtils;
import net.sourceforge.stripes.util.StringUtil;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sean Tan
 * Date: 14-1-22
 * Time: 上午11:18
 */
public class TeamlogUtils {
    public static final String ERROR_MESSAGE_PEOPLE_VALUE_NOT_PRESENT = "error.message.people.valueNotPresent";
    public static final String ERROR_MESSAGE_PERIOD_VALUE_NOT_PRESENT = "error.message.period.valueNotPresent";

    public static List<Long> getSplitId(String idListStr) {
        String[] ids = StringUtil.standardSplit(idListStr);
        List<Long> idList = new ArrayList<Long>();
        for (int i = 0; i < ids.length; i++) {
            String person = ids[i];
            try {
                idList.add(Long.parseLong(person));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return idList;
    }

    public static Map<String, DateTime> getSplitDate(String dateListStr) {
        String[] dates = StringUtil.standardSplit(dateListStr);
        Map<String,DateTime> dateTimeMap = new HashMap<String, DateTime>();
        dateTimeMap.put("start",DateTime.parse(dates[0]));
        dateTimeMap.put("end",DateTime.parse(dates[1]).plusDays(1));
        return dateTimeMap;
    }

    public static String checkWorklogConditions(String period, String people) {
        String message = null;
        if (StringUtils.isNullOrEmpty(period)) {
            message = ERROR_MESSAGE_PERIOD_VALUE_NOT_PRESENT;
        } else if (StringUtils.isNullOrEmpty(people)) {
            message = ERROR_MESSAGE_PEOPLE_VALUE_NOT_PRESENT;
        }

        return message;
    }

    public static Double convertDecimal(Object value) {
        return ((BigDecimal)value).doubleValue();
    }

    public static List<Long> minusList(List<Long> data, List<Long> range) {
        List<Long> rtn = new ArrayList<Long>();

        for (Long value : data) {
            for (Long rangeValue : range) {
                if (value.equals(rangeValue)) {
                    rtn.add(value);
                    break;
                }
            }
        }

        return rtn;
    }
}
