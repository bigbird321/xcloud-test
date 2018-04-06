package net.machtalk.xcloud;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: zhaop
 * Date: 16-1-7
 * Time: 下午1:19
 * To change this template use File | Settings | File Templates.
 */
public class TestReg {
    public static void main(String[] args){
        String sql = "select * from (\n" +
                "\t \n" +
                "        select\n" +
                "         \n" +
                "    company_id, company_name, create_time, description, company_sn\n" +
                "   \n" +
                "        from bussiness_company bc\n" +
                "        where 1=1\n" +
                "         \n" +
                "         \n" +
                "            and company_name like concat('%',?,'%')\n" +
                "         \n" +
                "         \n" +
                "         \n" +
                "         \n" +
                "            AND  bc.company_id  IN\n" +
                "             (  \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             , \n" +
                "                ?\n" +
                "             ) \n" +
                "         \n" +
                "        ORDER BY convert(company_name using gbk) COLLATE gbk_chinese_ci ASC\n" +
                "         \n" +
                "\t\t) a limit ?,?";
        String reg = "\\s\\w+\\slike\\s*('%'\\s*\\s*)?(#\\{\\w+\\})(\\s*\\s*'%')?";//"order\\s+by\\s+.+"
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        List<String> replaceEscape = new ArrayList<String>();
        List<String> replaceFiled = new ArrayList<String>();

        while (matcher.find()) {
            replaceEscape.add(matcher.group());
            int n = matcher.groupCount();
            for (int i = 0; i <= n; i++) {
                String output = matcher.group(i);
                if (2 == i && output != null) {
                    replaceFiled.add(output.trim());
                }
            }
        }
    }
}
