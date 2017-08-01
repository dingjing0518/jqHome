package com.by.controller;

import com.by.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminReportController {
    private final String MEMBER_REPORT = "admin/report/member";
    private final String TRADING_REPORT = "admin/report/trading";
    private final String PARKING_COUPON_REPORT = "admin/report/parkingCoupon";
    private final String GIFT_COUPON_COUPON_REPORT = "admin/report/giftCoupon";
    private final String SHOP_COUPON_COUPON_REPORT = "admin/report/shopCoupon";
    private String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
    @Autowired
    private MemberIncPerDayService service;
    @Autowired
    private TradingIncPerDayService tradingService;
    @Autowired
    private ParkingCouponInfoPerDayService parkingCouponService;
    @Autowired
    private GiftCouponInfoPerDayService giftCouponService;
    @Autowired
    private ShopCouponInfoPerDayService shopCouponService;

    @RequestMapping(value = "/admin/memberReport", method = RequestMethod.GET)
    public String memberReport() {
        return MEMBER_REPORT;
    }

    @RequestMapping(value = "/admin/memberReport/data", method = RequestMethod.GET)
    @ResponseBody
    public List<MonthMemberReport> memberReportData(@RequestParam(value = "year", required = false) Integer year) {
        Calendar today = Calendar.getInstance();
        int y = getYear(today, year);
        int m = getMonth(today, year);
        List<Object[]> lists = service.findByYearAndMonth(y, m);
        List<MonthMemberReport> results = lists.stream().map(i -> {
            Integer j = ((Integer) i[0]);
            String k = months[j - 1];
            int v = ((Long) i[1]).intValue();
            String time = (String) i[2];
            return new MonthMemberReport(k, v, time);
        }).collect(Collectors.toList());
        return results;
    }

    @RequestMapping(value = "/admin/tradingReport", method = RequestMethod.GET)
    public String tradingReport() {
        return TRADING_REPORT;
    }

    @RequestMapping(value = "/admin/tradingReport/data", method = RequestMethod.GET)
    @ResponseBody
    public List<MonthTradingReport> tradingReportData(@RequestParam(value = "year", required = false) Integer year) {
        Calendar today = Calendar.getInstance();
        int y = getYear(today, year);
        int m = getMonth(today, year);
        List<Object[]> lists = tradingService.findByYearAndMonth(y, m);
        return lists.stream().map(i -> {
            Integer j = ((Integer) i[0]);
            String month = months[j - 1];
            int number = ((Long) i[1]).intValue();
            Double amount = ((Double) i[2]);
            String time = (String) i[3];
            return new MonthTradingReport(month, amount, number, time);
        }).collect(Collectors.toList());
    }

    @RequestMapping(value = "/admin/parkingCouponReport", method = RequestMethod.GET)
    public String parkingCouponReport() {
        return PARKING_COUPON_REPORT;
    }

    @RequestMapping(value = "/admin/parkingCouponReport/data", method = RequestMethod.GET)
    @ResponseBody
    public List<MonthCouponReport> parkingCouponReportDate(
            @RequestParam(value = "year", required = false) Integer year) {
        Calendar today = Calendar.getInstance();
        int y = getYear(today, year);
        int m = getMonth(today, year);
        return toReports(parkingCouponService.findByYearAndMonth(y, m));
    }

    @RequestMapping(value = "/admin/giftCouponReport", method = RequestMethod.GET)
    public String giftCouponReport() {
        return GIFT_COUPON_COUPON_REPORT;
    }

    @RequestMapping(value = "/admin/giftCouponReport/data", method = RequestMethod.GET)
    @ResponseBody
    public List<MonthCouponReport> giftCouponReportDate(@RequestParam(value = "year", required = false) Integer year) {
        Calendar today = Calendar.getInstance();
        int y = getYear(today, year);
        int m = getMonth(today, year);
        return toReports(giftCouponService.findByYearAndMonth(y, m));
    }

    @RequestMapping(value = "/admin/shopCouponReport", method = RequestMethod.GET)
    public String shopCouponReport() {
        return SHOP_COUPON_COUPON_REPORT;
    }

    @RequestMapping(value = "/admin/shopCouponReport/data", method = RequestMethod.GET)
    @ResponseBody
    public List<MonthCouponReport> shopCouponReportData(@RequestParam(value = "year", required = false) Integer year) {
        Calendar today = Calendar.getInstance();
        int y = getYear(today, year);
        int m = getMonth(today, year);
        return toReports(shopCouponService.findByYearAndMonth(y, m));
    }

    private List<MonthCouponReport> toReports(List<Object[]> lists) {
        return lists.stream().map(i -> {
            MonthCouponReport r = new MonthCouponReport();
            int month = (int) i[0];
            Long exchange = (Long) i[1];
            Long use = (Long) i[2];
            String time = (String) i[3];
            r.setDate(month + "");
            r.setExchangeNumber(exchange.intValue());
            r.setUseNumber(use.intValue());
            r.setTime(time);
            return r;
        }).collect(Collectors.toList());
    }

    public int getYear(Calendar today, Integer year) {
        int currentYear = today.get(Calendar.YEAR);
        if (year == null) {
            return currentYear;
        } else {
            return year;
        }
    }

    public int getMonth(Calendar today, Integer year) {
        int currentYear = today.get(Calendar.YEAR);
        if (year != null && year < currentYear) {
            return 12;
        } else {
            return today.get(Calendar.MONTH) + 1;
        }
    }

    public class MonthTradingReportWithMax {
        private List<MonthTradingReport> lists;
        private long max;

        public List<MonthTradingReport> getLists() {
            return lists;
        }

        public void setLists(List<MonthTradingReport> lists) {
            this.lists = lists;
        }

        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }
    }

    public class MonthTradingReport {
        private String month;
        private double amount;
        private int number;
        private String time;

        public MonthTradingReport(String month, double amount, int number, String time) {
            this.month = month;
            this.amount = amount;
            this.number = number;
            this.time = time;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public class MonthMemberReport {
        private String key;
        private int value;
        private String time;

        public MonthMemberReport(String k, int v, String time) {
            this.key = k;
            this.value = v;
            this.time = time;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public class MonthCouponReport {
        private String date;
        private int exchangeNumber;
        private int useNumber;
        private String time;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getExchangeNumber() {
            return exchangeNumber;
        }

        public void setExchangeNumber(int exchangeNumber) {
            this.exchangeNumber = exchangeNumber;
        }

        public int getUseNumber() {
            return useNumber;
        }

        public void setUseNumber(int useNumber) {
            this.useNumber = useNumber;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

}
