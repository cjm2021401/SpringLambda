package WhaTap.LambdaTestApplication.Controller;

import WhaTap.LambdaTestApplication.Domain.S3;
import WhaTap.LambdaTestApplication.Domain.User;
import WhaTap.LambdaTestApplication.Service.S3Service;
import WhaTap.LambdaTestApplication.Service.UserService;
import WhaTap.LambdaTestApplication.Util.SessionUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;

@Controller
public class WhaTapController {

    private final Logger logger = LoggerFactory.getLogger(WhaTapController.class);
    private final S3Service s3Service;
    private final UserService userService;

    @Autowired
    public WhaTapController(S3Service s3service, UserService userService){
        this.s3Service=s3service;
        this.userService=userService;
    }
    @GetMapping("/")
    public String home(){return "home";}

    @PostMapping("/login")
    public String login(User user, Model model, HttpSession session){
        if(userService.login(user)){
            logger.info("Success to login and Make session for "+ user.getEmail());
            SessionUtil.setUser(session, user.getEmail());
            model.addAttribute("user", SessionUtil.getUser(session));
            model.addAttribute("s3", "whatap-build");
            model.addAttribute("key", s3Service.GetS3Object());
            return "select";
        }else{
            logger.warn("Fail to login");
            model.addAttribute("message", "fail");
            return "home";
        }
    }

   @PostMapping("select")
   public String select(S3 s3, Model model, HttpSession session){
        String url=s3Service.MakeSingedUrl(s3.getS3(), s3.getKey());
        model.addAttribute("url", url);
        userService.addCount(SessionUtil.getUser(session));
        return "result";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
