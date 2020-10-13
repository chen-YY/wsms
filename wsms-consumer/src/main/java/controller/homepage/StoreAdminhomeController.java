package controller.homepage;

import Utils.SwitchUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wsms.homepage.LeaderhomeService;
import wsms.homepage.StorageAdminhomeService;
import wsms.homepage.StoreAdminhomeService;

import javax.jws.WebParam;
import java.nio.charset.MalformedInputException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class StoreAdminhomeController {

    @Autowired
    StoreAdminhomeService storeAdminhomeService;

    @Autowired
    StorageAdminhomeService storageAdminhomeService;

    @Autowired
    LeaderhomeService leaderhomeService;


    @RequestMapping("applayOrder")
    public ModelAndView OrderC(String type,String sponsorDep,String sponsorUser,String disposeDep,String disposeUser,String goods,String number){


        System.out.println(type);
        System.out.println(sponsorDep);
        System.out.println(sponsorUser);
        System.out.println(disposeDep);
        System.out.println(disposeUser);
        System.out.println(goods);
        System.out.println(number);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(new Date());

        System.out.println(startTime);

        storeAdminhomeService.applayOrder(type,
                sponsorDep,
                Integer.parseInt(sponsorUser),
                disposeDep,
                Integer.parseInt(disposeUser),
                goods,
                Integer.parseInt(number),
                startTime
                );

        ModelAndView mav = SwitchUtils.storeAdmin(leaderhomeService,storageAdminhomeService,storeAdminhomeService);
        return mav;
    }

    @RequestMapping("storeAdminReceive")
    public ModelAndView storeAdminReceive(String deliveryOrderId){


        storeAdminhomeService.storeReceiveGoods(Integer.parseInt(deliveryOrderId));

        ModelAndView mav = SwitchUtils.storeAdmin(leaderhomeService,storageAdminhomeService,storeAdminhomeService);
        return mav;

    }
}
