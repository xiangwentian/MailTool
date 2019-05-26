package com.workman.data;

import com.alibaba.fastjson.JSON;
import com.workman.common.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sql查询的数据处理类
 */
public class DataProcessTool {
    public static String List2Html(List title, List list) {
        String body = "";
        String titleName = "";
        //判断当标题不为空时，处理标题
        if (null != title) {
            if (title.size() > 1) {
                //标题要合并
                System.out.println("多标题合并逻辑");
                titleName = mutilTitle(title);
            } else {
                //只展示一个标题
                titleName = "<tr style='background:#95b3d7;color:#ffffff;'><th colspan='" + ((List) title.get(0)).size() + "'>" + (StringUtils.isNotEmpty(String.valueOf(((List) title.get(0)).get(0))) ? String.valueOf(((List) title.get(0)).get(0)) : "")
                        + "</th></tr>";
            }
        }

        if (JSON.toJSONString(list).contains("_LHB")) {
            //判断是否有换行符
            //执行换行拼接sql的工具方法
            Map<String, Object> hasLHBData = parse_LHB(list);
            body = hasLHBList2Html(titleName, hasLHBData);
        } else {
            List<String[]> dataList = new ArrayList<String[]>();
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                String dataStr = (String) map.get(i + 1);
                String[] dataArray = dataStr.split(",");
                dataList.add(dataArray);
            }

            int dataSize = dataList.get(0).length;

            String outDivStart = "<div class=\"kpi_table_cont\">\n" +
                    "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"dataframe\">";

            String headData = "";
            for (int i = 0; i < dataSize; i++) {
                headData += "<th>" + dataList.get(0)[i] + "</th>";
            }
            headData = "<thead>\n" + titleName +
                    "                    <tr style='background:#729aca; color:#ffffff;'>" + headData + "</tr>\n" +
                    "                </thead>";

            String contextData = "";
            if (dataSize > 1) {
                for (int i = 1; i < dataList.size(); i++) {
                    String td = "";
                    for (int j = 0; j < dataSize; j++) {
                        td += "<td style='white-space: nowrap;'>" + (StringUtils.isNotEmpty(dataList.get(i)[j]) ? dataList.get(i)[j] : "") + "</td>";
                    }
                    contextData += "<tr " + (i % 2 != 0 ? "style='background: #dce6f1;'" : "style=''") + ">" + td + "</tr>";//dataArray有多少个参数就有多少个td
                }
            }
            String context = "<tbody>" + contextData + "</tbody>";

            String outDivEnd = "            </table>\n" +
                    "        </div>";
            body = outDivStart + headData + context + outDivEnd;
        }

        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\" />\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<title></title>\n" +
                "\n" +
                "<style>\n" +
                "*{margin:0; padding:0;}\n" +
                "body{font-family: Microsoft YaHei;}\n" +
                ".kpi_content{font-size: 14px; line-height: 20px; margin-top: 10px;}\n" +
                ".kpi_title{font-size: 16px; text-align: center; color:#729aca;}\n" +
                ".kpi_main{width: 1100px;height: auto; overflow: hidden; clear: both; margin: 10px;}\n" +
                ".kpi_tips{height:auto; overflow: hidden; clear: both; line-height: 22px; font-size: 14px; margin-top: 10px;}\n" +
                "table.dataframe { border-collapse: collapse; border: 0; border-spacing: 0; width: 1100px;}\n" +
                "table.dataframe thead tr{background: #729aca;}\n" +
                "table.dataframe thead tr th{border: 0; line-height: 20px; color: #ffffff; padding:1px; vertical-align: top; line-height:20px; font-size: 14px; border-right: 1px solid #9ec0ea; border-bottom: 1px solid #9ec0ea;}\n" +
                "table.dataframe tr td {text-align: center; border: 0; padding: 0px 5px; width: 5%; border-right:1px solid #eeeeee;border-bottom:1px solid #eeeeee; line-height:20px; font-size:12px;}\n" +
                "table.dataframe tbody tr td {white-space:nowrap}\n" +
                "table.dataframe tbody tr{background:#ffffff}\n" +
                ".kpi_table_cont{height: auto; overflow: hidden; clear: both; width:1100px; border-left:1px solid #eeeeee;}\n" +
                ".kpi_table_title{background: #95b3d7; height: 30px; line-height: 30px; text-align: center; font-size: 14px; font-weight: bold; color:#ffffff; margin-right: 1px;}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>" +
                body
                + "</body>\n" +
                "</html>";
        return html;
    }

    /**
     * 处理多title参数
     *
     * @param title
     * @return
     */
    public static String mutilTitle(List title) {
        String bussinessTitle = "";
        //要合并几个大标题
        for (int i = 0; i < title.size(); i++) {
            String titleName = String.valueOf(((List) title.get(i)).get(0));
            bussinessTitle += "<th colspan='" + ((List) title.get(i)).size() + "'>" + titleName + "</th>";
        }
        return "<tr style='background:#95b3d7;color:#ffffff;'>" + bussinessTitle + "</tr>";
    }

    public static String hasLHBList2Html(String titleName, Map<String, Object> hasLHBData) {
        int lhbLine = Integer.valueOf(hasLHBData.get("LHB") != null ? hasLHBData.get("LHB").toString() : "");

        List lhbData = hasLHBData.get("data") != null ? (List) hasLHBData.get("data") : null;//lhbData是要分组的数据
        if (lhbData == null) {
            return "";
        }

        int totalLine = ((String[]) ((List) lhbData.get(0)).get(0)).length;

        //把标题数据放上去
        String headData = "";

        String contextData = "";

        for (int i = 0; i < lhbData.size(); i++) {
            //分组数据带标识分成了单独的list
            List<String[]> groupData = (List<String[]>) lhbData.get(i);//groupData 第一个分组对象
            //当是单条数据时不需要合并操作直接赋值
            if (groupData.size() == 1) {
                //当是第一条数据时
                if (i == 0) {
                    for (int j = 0; j < groupData.get(i).length; j++) {
                        headData += "<th>" + groupData.get(0)[j] + "</th>";
                    }
                    headData = "<thead>\n" + titleName +
                            "                    <tr style='background:#729aca; color:#ffffff;'>" + headData + "</tr>\n" +
                            "                </thead>";
                } else {
                    if (groupData.size() > 1) {//当分组的列表大于1条数据时

                        //当有一条记录时直接把数据放到一个tr td中即可
                        String td = "";
                        for (int j = 0; j < totalLine; j++) {
                            td += "<td style='white-space: nowrap;'  rowspan='" + totalLine + "'>" + (StringUtils.isNotEmpty(groupData.get(i)[j]) ? groupData.get(i)[j] : "") + "</td>";
                        }
                        contextData += "<tr " + (i % 2 != 0 ? "style='background: #dce6f1;'" : "style=''") + ">" + td + "</tr>";//dataArray有多少个参数就有多少个td

                    } else {
                        String td = "";
                        for (int j = 0; j < totalLine; j++) {
                            td += "<td style='white-space: nowrap;' >" + (StringUtils.isNotEmpty(groupData.get(0)[j]) ? groupData.get(0)[j] : "") + "</td>";
                        }
                        contextData += "<tr " + (i % 2 != 0 ? "style='background: #dce6f1;'" : "style=''") + ">" + td + "</tr>";//dataArray有多少个参数就有多少个td
                    }
                }
            } else {
                //多条数据要合并单元格时，做相应的处理
                if (groupData.size() > 1) {//当分组的列表大于1条数据时
                    contextData += processTrTd((List<String[]>) groupData, totalLine, lhbLine, i);
                }
            }
        }
        String resultHtml = "";
        if (StringUtils.isNotEmpty(titleName)) {
            resultHtml = "<div class=\"kpi_table_cont\">\n" +
                    "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"dataframe\">" + headData + "<tbody>" + contextData + "</tbody>" + "</table>\n" +
                    "        </div>";
        } else {
            resultHtml = "<div class=\"kpi_table_cont\">\n" +
                    "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"dataframe\">" + headData + "<tbody>" + contextData + "</tbody>" + "</table>\n" +
                    "        </div>";
        }
        return resultHtml;
    }

    /**
     * 处理tr/td
     *
     * @param dataList 要遍历的对象
     * @param dataSize 一共多少条数据
     * @param lhbLine  第几行需要分组
     * @return
     */
    private static String processTrTd(List<String[]> dataList, int dataSize, int lhbLine, int currentGroup) {
        String contextData = "";
        for (int i = 0; i < dataList.size(); i++) {
            String td = "";
            for (int j = 0; j < dataSize; j++) {
                if (i == 0 && j == lhbLine) {
                    //判断是当前数据的第一项参数
                    td += "<td style='white-space: nowrap;'" + (i == 0 ? "rowspan=" + dataList.size() : "rowspan=1") + ">" + (j == lhbLine ? dataList.get(i)[j].split("_LHB")[0] : dataList.get(i)[j]) + "</td>";
                } else if (i != 0 && j == lhbLine) {
                    //什么也不处理，留空该列
                } else {
                    td += "<td style='white-space: nowrap;'>" + (j == lhbLine ? dataList.get(i)[j].split("_LHB")[0] : (StringUtils.isNotEmpty(dataList.get(i)[j]) ? dataList.get(i)[j] : "")) + "</td>";

                }
            }
            contextData += "<tr " + (currentGroup % 2 != 0 ? "style='background: #dce6f1;'" : "style=''") + ">" + td + "</tr>";//dataArray有多少个参数就有多少个td
        }
        return contextData;
    }

    /**
     * @param title
     * @param context
     * @return
     */
    public static String List2HtmlHasTitle(List title, List context) {
        List titleGroup = groupingtitle(title);//表头分组方法
        return List2Html(titleGroup, context);
    }

    /**
     * 把title合并单元格处理
     */
    public static List groupingtitle(List list) {
        Map map = (Map) list.get(0);
        String dataStr = (String) map.get(1);
        String[] dataArray = dataStr.split(",");

        List dataMap = new ArrayList();

        System.out.println("有要合并的参数");


        int num = 0;
        for (; num < dataArray.length; ) {

            List sameLine = new ArrayList();

            if (num + 1 != dataArray.length) {
                sameLine.add(dataArray[num]);

                for (int j = num + 1; j < dataArray.length; j++) {


                    if (dataArray[num].equals(dataArray[j])) {
                        sameLine.add(dataArray[j]);
                        if (j == dataArray.length - 1) {
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
                break;
            }
        }
        return dataMap;
    }

    /**
     * 数据报表展示
     *
     * @param list
     * @return
     */
    public static Map<String, Object> parse_LHB(List list) {

        int witchLineNeedMerge = -1;

        List dataMap = new ArrayList();

        System.out.println("有要合并的参数");
        //判断是第几列要合并单元格
        if (list.size() > 1) {
            Map map = (Map) list.get(1);
            String dataStr = (String) map.get(2);
            String[] dataArray = dataStr.split(",");
            if (JSON.toJSONString(dataArray).contains("_LHB")) {
                for (int j = 0; j < dataArray.length; j++) {
                    if (dataArray[j].contains("_LHB")) {
                        witchLineNeedMerge = j;
                        break;
                    }
                }
            }
        } else {
            //当列表不超过两条数据时，按正常一条数据展示
            Map map = (Map) list.get(0);
            String dataStr = (String) map.get(1);
            String[] dataArray = dataStr.split(",");

            List<String[]> sameLine = new ArrayList<String[]>();
            sameLine.add(dataArray);
            dataMap.add(sameLine);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("data", dataMap);
            resultMap.put("LHB", witchLineNeedMerge);
            return resultMap;
        }

        int num = 0;
        for (; num < list.size(); ) {
            Map map = (Map) list.get(num);
            String dataStr = (String) map.get(num + 1);
            String[] dataArray = dataStr.split(",");

            List<String[]> sameLine = new ArrayList<String[]>();
            sameLine.add(dataArray);
            if (witchLineNeedMerge == -1) {
                //没有要合并的项
                sameLine.add(dataArray);
                dataMap.add(sameLine);
                ++num;
                break;
            } else {
                if (num + 1 != list.size()) {

                    for (int j = num + 1; j < list.size(); j++) {

                        Map mapNext = (Map) list.get(j);
                        String dataStrNext = (String) mapNext.get(j + 1);
                        String[] dataArrayNext = dataStrNext.split(",");


                        if (dataArray[witchLineNeedMerge].equals(dataArrayNext[witchLineNeedMerge])) {
                            //当带_LHB的witchLineNeedMerge值不为0时，并且下一个值相等
                            sameLine.add(dataArrayNext);
                            if (j + 1 == list.size()) {
                                //当j是最后一条数据时
                                dataMap.add(sameLine);
                                sameLine = null;
                                num = list.size();
                                break;
                            }
                        } else {
                            if (num == list.size() - 1) {
                                sameLine.add(dataArrayNext);
                            }
                            dataMap.add(sameLine);
                            sameLine = null;
                            num = j;
                            break;
                        }
                    }

                } else {
                    if (sameLine.size() > 0) {
                        dataMap.add(sameLine);
                    }
                    num++;
                    break;
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("data", dataMap);
        resultMap.put("LHB", witchLineNeedMerge);
        return resultMap;
    }

}
