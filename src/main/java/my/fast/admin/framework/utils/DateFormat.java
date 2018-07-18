package my.fast.admin.framework.utils;

import org.apache.commons.lang.StringUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/6 16:09
 * @Description:
 */
public class DateFormat {
    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Date currentTime_2 = strToDateLong(dateString);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        // ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = strToDate(dateString);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式 yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式 yyyyMMdd
     */
    public static String getString2Date() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时: HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格�?如果是yyyyMMdd，注意字母y不能大写�?
     *
     * @param sformat yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 二个小时的时间差,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 二个时间间的差,返回之间相差分钟数
     */
    public static int getMinuteByTwoDateTime(Date st1, Date st2) {
        long Time = st1.getTime() - st2.getTime();
        int mi = (int) (Time / 60000);
        return mi;
    }

    /**
     * 二个字符间的差,返回后一个时间到前一个时间的差值，单位秒
     */
    public static int getSecondByTwoDateTime(String st1, String st2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int se = 0;
        try {
            Date date1 = format.parse(st1);
            Date date2 = format.parse(st2);
            long Time = date2.getTime() - date1.getTime();
            se = (int) (Time / 1000);
        } catch (Exception e) {
           e.getStackTrace();
        }
        return se;
    }

    /**
     * 返回当前月的1号
     * @return
     */
    public static Date getFirstDayOfMonth() {
        Calendar cDate = Calendar.getInstance();
        cDate.set(5, 1);
        return cDate.getTime();
    }

    /**
     * 返回月份的最后一天
     * @param d
     * @return
     */
    public static Date getMaxDayOfMonth(Date d) {
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(d);
        cDate.set(5, cDate.getActualMaximum(5));
        return cDate.getTime();
    }

    /**
     * 返回月份的最后一天
     * @param d
     * @return
     */
    public static Date getMaxDayOfMonth(String d) {
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(getNowDateShort());
        cDate.set(5, cDate.getActualMaximum(5));
        return cDate.getTime();
    }
    /**
     * 获取增加月数后的日期
     * @param strDate 日期 YYYYMM或者YYYYMMDD
     * @param intDiff 增加月数
     * @return
     */
    public static String addYearMonth(String strDate, int intDiff) {
        try {
            if (StringUtils.isEmpty(strDate)) {
                return strDate;
            }

            if (intDiff == 0) {
                return strDate;
            }

            if ((strDate.length() != 6) && (strDate.length() != 8)) {
                return "";
            }

            int year = new Integer(strDate.substring(0, 4)).intValue();
            int month = new Integer(strDate.substring(4, 6)).intValue();

            String strDay = "";
            if (strDate.length() > 6) {
                strDay = strDate.substring(6, strDate.length());
            }

            if ((intDiff > 0) || (month > Math.abs(intDiff))) {
                month += intDiff;
                if (month > 12) {
                    year += month / 12;
                    month %= 12;
                    if (month == 0) {
                        year--;
                        month = 12;
                    }
                }
            } else {
                int n = Math.abs((month + intDiff) / 12) + 1;
                month = Math.abs(month + 12 + intDiff % 12) % 12;
                year -= n;
                if (month == 0) {
                    month = 12;
                }
            }

            if ((month <= 12) && (month >= 10)) {
                return year + new Integer(month).toString() + strDay;
            }
            return year + "0" + new Integer(month).toString() + strDay;
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 返回两个年月相差的月份
     * @param strDateBegin YYYYMM
     * @param strDateEnd YYYYMM
     * @return
     */
    public static int getMonthBetween(String strDateBegin, String strDateEnd) {
        int strOut = 0;
        int intMonthBegin = 0;
        int intMonthEnd = 0;
        try {
            if ((strDateBegin.equals("")) || (strDateEnd.equals("")) || (strDateBegin.length() != 6) || (strDateEnd.length() != 6)) {
                strOut = 0;
            } else {
                intMonthBegin = Integer.parseInt(strDateBegin.substring(0, 4)) * 12 + Integer.parseInt(strDateBegin.substring(4, 6));
                intMonthEnd = Integer.parseInt(strDateEnd.substring(0, 4)) * 12 + Integer.parseInt(strDateEnd.substring(4, 6));
            }
            return intMonthBegin - intMonthEnd;
        } catch (Exception e) {
        }
        return strOut;
    }

    public static void main(String[] args) {
        System.out.println(DateFormat.getString2Date());
    }
}