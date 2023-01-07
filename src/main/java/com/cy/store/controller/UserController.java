package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Controller
@RestController //相当于@Controller+@ResponseBody
@RequestMapping("users")
public class UserController extends BaseController{

    /**
     * @Autowired
    private IUserService userService;
    @RequestMapping("reg")
    //@ResponseBody表示此方法的响应结果以json格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user){
        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMesssge("用户注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMesssge("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMesssge("注册时产生未知异常");
        }
        return result;
    }
**/


    /**
     * 约定大于配置
     * 1.接收数据方式：请求处理方法的参数列表设置为pojo类型来接收前端的数据。
     * spring boot会将前端url地址中的参数名和pojo类的属性名进行比较，如果相同则将值注入到pojo类中对应的属性上。
     */
    @Autowired
    private IUserService userService;
    @RequestMapping("reg")
    //@ResponseBody表示此方法的响应结果以json格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    /**
     * 2.接收数据方式：请求处理方法的参数列表设置为非pojo类型来接收前端的数据。
     * spring boot会直接将请求的参数名和方法的参数名直接进行比较，如果名称相同则自动完成值的注入
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session){
        User data = userService.login(username,password);
        //向session对象中完成对象的绑定（session是全局的）
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());

        //获取session中绑定的数据
        //System.out.println(getuidFromSession(session));
        //System.out.println(getUsernameFromSession(session));

        return new JsonResult<>(OK,data);
    }


    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePssword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User date = userService.getByUid(getuidFromSession(session));
        return new JsonResult<>(OK,date);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,
                                       HttpSession session){
        //User有四部分数据：username，phone，email，gender
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }

    /** 设置上传文件的最大值 */
    public static final int AVATAR_MAX_SIZE = 10*1024*1024;

    /** 设置限制上传文件的类型 */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    //用静态库给集合初始化
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/bmp");
    }

    /**
     * MultipartFile接口是由SpringMVC提供的，为我们包装了获取文件类型的数据(任何类型文件都可以接收)
     * "@RequestParam"表示请求中的参数，将请求的参数注入请求处理方法的某个参数上，如果名称不一致则使用@RequestPara进行映射
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file){
        //判断文件是否为null
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        //文件大小判断
        if (file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超出大小限制");
        }
        //判断文件类型是否是规定的类型
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        //上传的文件.../upload/文件.jpg
        String parent = session.getServletContext().getRealPath("/upload");
        //File对象指向这个路径，判断File是否存在
        File dir = new File(parent);
        if (!dir.exists()){//检测目录是否存在
            dir.mkdirs();//创建目录
        }
        //获取当前文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("OriginalFilename="+originalFilename);
        //截取文件后缀
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);
        //再用UUID来生成新的字符串作为文件名
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        //创建以新文件名命名的空文件
        File dest = new File(dir,filename);
        //将参数file中的数据写入到这个空文件
        try {
            file.transferTo(dest);
        }catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        }catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        //调用头像修改
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        //返回头像路径：.../upload/filename
        String avatar = "/upload/"+filename;
        userService.changeAvatar(uid,
                                 avatar,
                                 username);
        //返回用户头像的路径给前端，作为前端展示头像使用
        return new JsonResult<>(OK,avatar);
    }
}
