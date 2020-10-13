package controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wsms.Test;

@Controller
public class IndexController {

    @Autowired
    Test t;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();

        mav.addObject("message",t.sayHello("cyy"));

        mav.setViewName("index");

        return mav;
    }

}
