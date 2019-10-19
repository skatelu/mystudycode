package producer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 生产数据类
 */
public class ProductLog {

    private String startTime = "2018-01-01";
    private String endTime = "2019-01-01";

    //生产数据
    //用于存放随机的电话号码
    private List<String> phoneList = new ArrayList<String>();
    //电话号码与姓名的映射
    private Map<String, String> phoneNameMap = new HashMap<String, String>();

    /**
     * 初始化手机号码 与 姓名
     */
    public void initPhone(){
        phoneList.add("18742720904");
        phoneList.add("13811958264");
        phoneList.add("19089970150");
        phoneList.add("16868664637");
        phoneList.add("16635892961");
        phoneList.add("19145349791");
        phoneList.add("18102448599");
        phoneList.add("19533829665");
        phoneList.add("13657847997");
        phoneList.add("18349718826");
        phoneList.add("15725671094");
        phoneList.add("18336247427");
        phoneList.add("15695444496");
        phoneList.add("14546128631");
        phoneList.add("15798274734");
        phoneList.add("15659456362");
        phoneList.add("13357154130");
        phoneList.add("14914062673");
        phoneList.add("13739624089");
        phoneList.add("16771190044");
        phoneList.add("14537878197");
        phoneList.add("17135383073");
        phoneList.add("17647825882");
        phoneList.add("18585892024");
        phoneList.add("15225174483");

        phoneNameMap.put("18742720904", "李雁");
        phoneNameMap.put("13811958264", "卫艺");
        phoneNameMap.put("19089970150", "仰莉");
        phoneNameMap.put("16868664637", "陶欣悦");
        phoneNameMap.put("16635892961", "施梅梅");
        phoneNameMap.put("19145349791", "金虹霖");
        phoneNameMap.put("18102448599", "魏明艳");
        phoneNameMap.put("19533829665", "华贞");
        phoneNameMap.put("13657847997", "华啟倩");
        phoneNameMap.put("18349718826", "仲采绿");
        phoneNameMap.put("15725671094", "卫丹");
        phoneNameMap.put("18336247427", "戚丽红");
        phoneNameMap.put("15695444496", "何翠柔");
        phoneNameMap.put("14546128631", "钱溶艳");
        phoneNameMap.put("15798274734", "钱琳");
        phoneNameMap.put("15659456362", "缪静欣");
        phoneNameMap.put("13357154130", "焦秋菊");
        phoneNameMap.put("14914062673", "吕访琴");
        phoneNameMap.put("13739624089", "沈丹");
        phoneNameMap.put("16771190044", "褚美丽");
        phoneNameMap.put("14537878197", "孙怡");
        phoneNameMap.put("17135383073", "许婵");
        phoneNameMap.put("17647825882", "曹红恋");
        phoneNameMap.put("18585892024", "吕柔");
        phoneNameMap.put("15225174483", "冯怜云");
    }

    /**
     * 生产数据形式：14537878197,1224512142151215,17135383073,2019-01-23 08:09:10,0306
     */
    public String product(){
        String caller = "";
        String callee = "";

        String callerName = "";
        String calleeName = "";

        //生产1-25的随机数字
        int randomIndex = (int)(Math.random() * phoneList.size());
        //随机获取主叫手机号
        caller = phoneList.get(randomIndex);
        callerName = phoneNameMap.get(caller);
        //随机被叫手机号,不能与主叫手机号相同
        while(true){
            int calleeIndex = (int) (Math.random()*phoneList.size());
            callee = phoneList.get(calleeIndex);
            calleeName = phoneNameMap.get(callee);
            if(!callee.equals(caller)){
                break;
            }
        }

        String buileTime = randomBuildTmie(startTime, endTime);

        DecimalFormat df = new DecimalFormat("0000");
        String duration = df.format((int)(30 * 60 * Math.random()));

        StringBuilder sb = new StringBuilder()
                .append(caller + ",")
                .append(callee+",")
                .append(buileTime+",")
                .append(duration);

        return sb.toString();
        //System.out.println(caller + "," + callerName+"," + callee+","+calleeName+","+buileTime+","+duration);
    }

    /**
     * 根据传入的时间区间，在此范围内随机通话建立时间
     * startTimeTS + (endTimeTS-startTimeTS) * Math.random()
     * @param startTime
     * @param endTime
     */
    public String randomBuildTmie(String startTime,String endTime){

        try {
            //将传入的String类型的时间转换成Date类型的时间
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf1.parse(startTime);
            Date endDate = sdf1.parse(endTime);

            if(startDate.getTime() >= endDate.getTime()){
                return null;
            }

            long randomTS = startDate.getTime() + (long) ((endDate.getTime()-startDate.getTime())*Math.random());

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(randomTS);
            String resultTimeString = sdf2.format(date);
            return resultTimeString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将数据写入文件中
     */
    public void writeLog(String filePath){

        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
            while(true){
                Thread.sleep(500);
                String log = product() + "\n";
                System.out.println(log);
                osw.write(log);
                //一定要手动flush，才可以确保每条数据写入文件一次
                osw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws Exception{

        if(args == null || args.length <= 0){
            System.out.println("no argument");
            return;
        }
//        String logPath = "D:\\calllog.csv";
        ProductLog productLog = new ProductLog();
        productLog.initPhone();
        productLog.writeLog(args[0]);
    }

}
