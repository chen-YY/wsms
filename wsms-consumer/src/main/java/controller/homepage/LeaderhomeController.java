package controller.homepage;

import Utils.SwitchUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import entity.Kind;
import entity.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wsms.homepage.LeaderhomeService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/*
controller void方法不定义HttpServletResponse类型的入参，Spring MVC会认为@RequestMapping注解中指定的路径就是要返回的视图name，
从而访问对应的jsp文件，如果没有回报错
 */

@Controller
public class LeaderhomeController {

    @Autowired
    LeaderhomeService leaderhomeService;

    @RequestMapping("addStorage")
    public ModelAndView addStorage(String storageName,String location,HttpServletRequest request){
        //添加新的仓库信息

        leaderhomeService.addNewStorage(storageName,location);
        ModelAndView mav = SwitchUtils.leader(leaderhomeService);


        mav.setViewName("login/leaderHome");
        return mav;
    }

    @RequestMapping("addTypeandGoods")
    public ModelAndView addKindandGood(String goodsType,String goodsName){

        List<Kind> kindList = new ArrayList<>();
        kindList = leaderhomeService.selectKindByName(goodsType);

        if(kindList.size() != 0){
            //数据库中已经有这种类型
            leaderhomeService.addGoodName(kindList.get(0).getId(),goodsName);
        }else{

            //没有类型，需要添加新的类型
            leaderhomeService.addKind(goodsType);

            //添加之后，需要返回新添加的类型的id
            List<Kind> list2 = leaderhomeService.selectKindByName(goodsType);
            int id = list2.get(0).getId();

            //再添加商品
            leaderhomeService.addGoodName(id,goodsName);
        }

        ModelAndView mav = SwitchUtils.leader(leaderhomeService);
        mav.setViewName("login/leaderHome");
        return mav;
    }

    @RequestMapping("approve")
    public ModelAndView approve(String id,String result,String remark){

        int ID = Integer.parseInt(id);

        if(result.equals("yes")){
            leaderhomeService.updateStatus(ID,"B");
        }else if(result.equals("no")){
            leaderhomeService.updateStatus(ID,"E");
        }

        if(remark != null || remark != ""){
            leaderhomeService.updateRemark(ID,remark);
        }

        ModelAndView mav = SwitchUtils.leader(leaderhomeService);
        mav.setViewName("login/leaderHome");
        return mav;
    }

}
