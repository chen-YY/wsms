package start;

import entity.GoodName;
import entity.Kind;
import entity.Leader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import wsms.Login.LoginService;
import wsms.Test;
import wsms.homepage.LeaderhomeService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class start {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        System.out.println("consumer start");


//        Test t = (Test) context.getBean("Test");
//        if(t == null){
//            System.out.println("null");
//        }else{
//            System.out.println(t.sayHello("cyy"));
//        }
//
//        t.selectAllLeader();
//
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        LeaderhomeService leaderhomeService = (LeaderhomeService) context.getBean("LeaderhomeService");

        Map<String, List<GoodName>> name = leaderhomeService.selectAllKindOfGoods();
        List<Kind> kindList = leaderhomeService.selectAllKind();

        for(Kind k : kindList){
            List<GoodName> n = name.get(k.getKind());
            for (GoodName g : n){
                System.out.println(k.getKind()+"|"+g.getGoodName());
            }
        }

    }
}
