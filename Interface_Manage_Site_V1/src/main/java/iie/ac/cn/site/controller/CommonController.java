package iie.ac.cn.site.controller;


import iie.ac.cn.site.common.BaseResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@Controller
@RequestMapping("/common")
public class CommonController {
    private static final Log logger = LogFactory
            .getLog(CommonController.class);


    @Value("${template.uploadDir}")
    private String uploadDir;


    /**
     * 上传本地文件
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/upload.json")
    @ResponseBody
    public BaseResult upload(@RequestParam("file") MultipartFile file, HttpServletRequest request,
                             HttpServletResponse response) {
        //设置文件存储格式为 $year/$month/$day/$uuid.$ext
        Calendar cal=Calendar.getInstance();
        //获取当前年月日
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        int day=cal.get(Calendar.DATE);
        //构造随机字符串
        String uuid= UUID.randomUUID().toString();
        String srcFileName=file.getOriginalFilename();
        int extIndex=srcFileName.lastIndexOf(".");
        //获取扩展名
        String ext=srcFileName.substring(extIndex+1);

        //构造上传之后的文件名
        String commonFileName = "YEAR/MONTH/DAY/UUID.EXT".replace("YEAR",String.valueOf(year))
                .replace("MONTH",String.valueOf(month)).replace("DAY",String.valueOf(day))
                .replace("UUID",uuid).replace("EXT",ext);
        String fileDir = this.uploadDir+commonFileName;
        BaseResult baseResult = new BaseResult();
        try {
            //创建本地文件
            File serverFile = new File(fileDir);
            //将上传后的文件保存至本地
            FileUtils.writeLines(serverFile,null);
            //本地文件填充内容
            file.transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
            baseResult.setSuccess(false);
            return baseResult;
        }
        //返回文件的路径
        baseResult.setData(fileDir);
        baseResult.setSuccess(true);
        return baseResult;

    }

}
