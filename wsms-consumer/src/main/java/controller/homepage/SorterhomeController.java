package controller.homepage;

import Utils.SwitchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wsms.homepage.SorterhomeService;

@Controller
public class SorterhomeController {

    @Autowired
    SorterhomeService sorterhomeService;

    @RequestMapping("chuhuo")
    public ModelAndView chuhuo(String deliveryOrderId,String goodId){


        sorterhomeService.letGoodsSend(Integer.parseInt(goodId));
        sorterhomeService.updateDeliveryOrderToB(Integer.parseInt(deliveryOrderId));

        ModelAndView mav = SwitchUtils.sorter(sorterhomeService);
        return mav;

    }
}
