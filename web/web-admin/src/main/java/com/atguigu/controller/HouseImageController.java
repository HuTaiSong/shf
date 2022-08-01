package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author nicc
 * @version 1.0
 * @className HouseImageController
 * @description TODO
 * @date 2022-07-26 19:10
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController {
    @Reference
    private HouseImageService houseImageService;

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable Long houseId, @PathVariable Integer type, Model model){
        model.addAttribute("houseId",houseId);
        model.addAttribute("type",type);
        return "house/upload";
    }
    @RequestMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable Long houseId, @PathVariable Integer type, @RequestParam("file") MultipartFile[] files) throws IOException {
        if(files!=null && files.length>0){
            for(MultipartFile file :files){
                //上传图片到七牛云
                String originalFilename = file.getOriginalFilename();
                //获取图片后缀名
                String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString()+extName;
                QiniuUtil.upload2Qiniu(file.getBytes(),fileName);
                //添加图到数据库
                HouseImage houseImage = new HouseImage();
                houseImage.setImageUrl("http://rfmijz5u2.hb-bkt.clouddn.com/"+fileName);
                houseImage.setImageName(file.getOriginalFilename()); //写上传前图片名称，写上传后的图片名称
                houseImage.setType(type);
                houseImage.setHouseId(houseId);
                this.houseImageService.insert(houseImage);
            }
        }
        return Result.ok();
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable Long id){
        //删除七牛云上的图片
        HouseImage houseImage = this.houseImageService.getById(id);
        String imageUrl = houseImage.getImageUrl();
        imageUrl = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        QiniuUtil.deleteFileFromQiniu(imageUrl); //要写文件名，不要写外链地址
        // 删除数据库中的图片（is_deleted = 1）
        houseImageService.delete(id);

        return "redirect:/house/detail/" + houseId;
    }
}
