package com.csecu.amrit.medicalcare.helpers;

import java.util.ArrayList;

/**
 * Created by Amrit on 06/04/2018.
 */


public class TimeInterval {
    ArrayList<String> arrayList;

    public ArrayList<String> generateTags(String time1, String time2) {
        arrayList = new ArrayList<>();

        String type1 = getType(time1);
        String type2 = getType(time2);

        String newTime1 = getTime(time1);
        String newTime2 = getTime(time2);

        newTime1 = getGlobalTime(type1, newTime1);
        newTime2 = getGlobalTime(type2, newTime2);

        getIntervals(newTime1, newTime2);
        return arrayList;
    }

    private void getIntervals(String newTime1, String newTime2) {
        // TODO Auto-generated method stub
        String[] time1 = newTime1.split(":");
        String[] time2 = newTime2.split(":");

        int hour1, hour2, minute1, minute2;
        hour1 = Integer.parseInt(time1[0]);
        hour2 = Integer.parseInt(time2[0]);
        minute1 = Integer.parseInt(time1[1]);
        minute2 = Integer.parseInt(time2[1]);

        if (hour1 <= hour2) {
            getIntervals(hour1, hour2, minute1, minute2);
        } else {
            getIntervals(hour1, 23, minute1, 59);
            getIntervals(0, hour2, 0, minute2);
        }
    }

    private void getIntervals(int hour1, int hour2, int minute1, int minute2) {
        // TODO Auto-generated method stub
        while (hour1 <= hour2) {
            String tag = generateTime(hour1, minute1);
            if (hour1 == hour2) {
                int temp = minute1 + 30;
                if (temp < minute2) {
                    minute1 = temp;
                    tag = tag + " - " + generateTime(hour1, temp);
                    arrayList.add(tag);
                } else if (temp > minute2 && minute1 < minute2) {
                    minute1 = minute2 + 1;
                    tag = tag + " - " + generateTime(hour2, minute2);
                    arrayList.add(tag);
                } else {
                    break;
                }
            } else {
                minute1 = minute1 + 30;
                if (minute1 >= 60) {
                    minute1 = minute1 - 60;
                    hour1++;
                    tag = tag + " - " + generateTime(hour1, minute1);
                    arrayList.add(tag);
                } else {
                    tag = tag + " - " + generateTime(hour1, minute1);
                    arrayList.add(tag);
                }
            }
        }
    }

    private String getGlobalTime(String type1, String newTime1) {
        // TODO Auto-generated method stub
        String[] parts = newTime1.split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        if (type1.equals("PM")) {
            if (hour != 12) {
                hour = hour + 12;
            }
        }

        return generateTime(hour, minute);
    }

    private String generateTime(int hour, int minute) {
        // TODO Auto-generated method stub
        String value;
        if (hour < 10) {
            value = "0" + hour;
        } else {
            value = "" + hour;
        }

        if (minute < 10) {
            value = value + ":0" + minute;
        } else {
            value = value + ":" + minute;
        }
        return value;
    }

    private String getTime(String time1) {
        // TODO Auto-generated method stub
        time1 = time1.trim().substring(0, (time1.length() - 3));
        return time1.trim();
    }

    private String getType(String time1) {
        // TODO Auto-generated method stub
        String value = time1.trim().substring(time1.length() - 2);
        return value;
    }
}

