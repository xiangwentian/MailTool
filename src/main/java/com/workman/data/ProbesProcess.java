package com.workman.data;

import com.workman.common.ReadFile;
import com.workman.common.StringUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ProbesProcess {
    public static boolean ProbesSelect(Properties properties) throws IOException, SQLException {
        boolean noDataProvide = true;
        if (StringUtils.isEmpty(properties.getProperty("probes.sql"))) {
            //当探针sql没有配置时rclk御前
            return noDataProvide;
        }
        JdbcTool jdbcTool = new JdbcTool(properties);

        if (properties.getProperty("probes.sql").indexOf("->") != -1) {//当sql有联合sql时
            String[] unionSql = properties.getProperty("probes.sql").split("->");
            for (int i = 0; i < unionSql.length; i++) {
                List resultData = jdbcTool.getData(ReadFile.readSql(unionSql[i].trim()));//jdbcTool.getData(args[0]);
                int state = 1;
                if ((((Map) resultData.get(0)).get(1)) instanceof Double) {
                    state = ((Double) (((Map) resultData.get(0)).get(1))).intValue();
                } else if ((((Map) resultData.get(0)).get(1)) instanceof Long) {
                    state = ((Long) (((Map) resultData.get(0)).get(1))).intValue();
                } else {
                    // state = (int) ((Map) resultData.get(0)).get(1);
                    state = Integer.valueOf((String) ((Map) resultData.get(0)).get(1));
                }

                if (state != 1) {
                    //数据返回不正常
                    noDataProvide = false;
                    break;
                }
            }
        } else {
            String sql = ReadFile.readSql(properties.getProperty("probes.sql"));
            List resultData = jdbcTool.getData(sql);//jdbcTool.getData(args[0]);
            int state = 1;
            if ((((Map) resultData.get(0)).get(1)) instanceof Double) {
                state = ((Double) (((Map) resultData.get(0)).get(1))).intValue();
            } else if ((((Map) resultData.get(0)).get(1)) instanceof Long) {
                state = ((Long) (((Map) resultData.get(0)).get(1))).intValue();
            } else {
                state = Integer.valueOf((String) ((Map) resultData.get(0)).get(1));
            }

            if (state != 1) {
                //数据返回不正常
                noDataProvide = false;
            }
        }

        return noDataProvide;
    }
}
