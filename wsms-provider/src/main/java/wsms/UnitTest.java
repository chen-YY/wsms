package wsms;

import wsms.homepage.LeaderhomeServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UnitTest {
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(date);
        System.out.println(now);

        String uuid = UUID.randomUUID().toString().replace("-","");
        System.out.println(uuid);
        System.out.println(String.valueOf(System.currentTimeMillis()));



    }
}
