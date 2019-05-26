package com.workman.test;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class MethodTest {

    public static void main(String[] args) {
        //groupingtitle(null);
        test();
    }

    public static void test() {
        String dataStr = "号卡销售,号卡销售,生活号,生活号,生活号,小程序,小程序,小程序,小程序";
        String[] dataArray = dataStr.split(",");
        System.out.println("dataArray.length:" + dataArray.length);

//        int witchLineNeedMerge = 0;

        List dataMap = new ArrayList();

        System.out.println("有要合并的参数");


        int num = 0;
        for (; num < dataArray.length; ) {
            System.out.println("dataMap:" + JSON.toJSONString(dataMap));

            List sameLine = new ArrayList();
            //Set sameLine = new HashSet();
//            if (witchLineNeedMerge == 0) {
//                //没有要合并的项
//                sameLine.add(dataArray);
//                //dataMap.put(i, sameLine);
//                dataMap.add(sameLine);
//                ++num;
////                set.add(sameLine);
//            } else {
            if (num + 1 != dataArray.length) {
                sameLine.add(dataArray[num]);

                for (int j = num + 1; j < dataArray.length; j++) {


                    if (dataArray[num].equals(dataArray[j])) {
                        sameLine.add(dataArray[j]);
                        if (j == dataArray.length - 1) {
//                            sameLine.add(dataArray[j]);
                            dataMap.add(sameLine);
                            num = j;
                            break;
                        }
                    } else {
                        if (j == dataArray.length - 1) {
                            dataMap.add(sameLine);
                            sameLine = null;
                            sameLine = new ArrayList();
                            sameLine.add(dataArray[j]);
                            dataMap.add(sameLine);
                            sameLine = null;
                        } else {
                            dataMap.add(sameLine);
                            sameLine = null;
                        }
                        num = j;
                        break;
                    }
                }

            } else {
//                if (sameLine.size() > 0) {
//                    dataMap.add(sameLine);
//                }
                break;
            }
            //TODO
        }
//        }
        System.out.println(JSON.toJSONString(dataMap));

    }

    public static void test1() {
        String dataStr = "加油业务线1,加油业务线1,加油业务线2,加油业务线2,加油业务线2,加油业务线2";
        String[] dataArray = dataStr.split(",");
        System.out.println("dataArray.length:" + dataArray.length);
        List resultTitle = new ArrayList();
        int i = 0;
        for (; i < dataArray.length; i++) {
            if (i + 1 == dataArray.length) {
                Map<String, String> map = new HashMap<String, String>();
                if (!dataArray[i].equals(dataArray[i - 1])) {
                    map.put("line", String.valueOf(i));
                    map.put("title", dataArray[i]);
                    resultTitle.add(map);
//                    map.put("line", String.valueOf(dataArray.length));
//                    map.put("title", dataArray[i - 1]);
//                    resultTitle.add(map);
                    System.out.println("j + 1 == dataArray.length并且两值不相同resultTitle:" + resultTitle + ",j=" + dataArray.length);
                    break;
                } else {
                    map.put("line", String.valueOf(dataArray.length));
                    map.put("title", dataArray[i]);
                    resultTitle.add(map);
                    System.out.println("j + 1 == dataArray.length并且两值相同resultTitle:" + resultTitle + "j=" + dataArray.length);
                    break;
                }
            }
            for (int j = i + 1; j < dataArray.length; j++) {
                Map<String, String> map = new HashMap<String, String>();
                if (!dataArray[i].equals(dataArray[j])) {//当第一个值和第二个值不相同时，把第一个参数入列表
                    map.put("line", String.valueOf(i));
                    map.put("title", dataArray[i]);
                    resultTitle.add(map);
                    System.out.println("不相同时，resultTitle:" + resultTitle + ",j=" + j);
                    //i = j;
                    break;
                }
                System.out.println("j:" + j + "j + 1 == dataArray.length:" + (j + 1 == dataArray.length));

                i = j;
            }
        }

        System.out.println("resultTitle:" + JSON.toJSONString(resultTitle));
    }

    public static void groupingtitle(List list) {
        //TODO test
        String dataStr = "加油业务线1,加油业务线1,加油业务线2,加油业务线2,加油业务线2,加油业务线";
        String[] dataArray = dataStr.split(",");
        System.out.println("dataArray.length:" + dataArray.length);
        List resultTitle = new ArrayList();
        int num = 0;
        for (; num < dataArray.length; ) {
            Map<String, String> titleMap = new HashMap<String, String>();
            if (num > 0 && num <= dataArray.length) {
                titleMap.put("title", dataArray[num - 1]);
                if (num + 1 == dataArray.length && dataArray[num].equals(dataArray[dataArray.length])) {
                    titleMap.put("line", String.valueOf(num));
                } else {
                    titleMap.put("line", String.valueOf(num - 1));
                }
                resultTitle.add(titleMap);
                System.out.println("resultTitle:" + JSON.toJSONString(resultTitle));
            }

            if (num + 1 < dataArray.length) {
                for (int j = num + 1; j < dataArray.length; j++) {
                    System.out.println("num:" + dataArray[num] + ",j:" + dataArray[j]);
                    if (j + 1 == dataArray.length) {
                        num++;
                        break;
                    }
                    if (dataArray[num].equals(dataArray[j])) {
                        num = j;
                    } else {
                        num = j;
                        break;
                    }
                }
            } else {
                titleMap.put("line", String.valueOf(num));
                resultTitle.add(titleMap);
                break;
            }
        }
        System.out.println("resultTitle:" + JSON.toJSONString(resultTitle));

    }

}
