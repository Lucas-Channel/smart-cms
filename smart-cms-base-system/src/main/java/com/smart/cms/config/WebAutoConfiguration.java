package com.smart.cms.config;

import com.smart.cms.properties.CommonProperties;
import com.smart.cms.properties.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/4/28 18:05
 * @Version: 1.0
 */
@Configuration
public class WebAutoConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private CustomProperties customProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        CommonProperties common = customProperties.getCommon();
        // /displayImag
        String imageMapping = common.getImageMapping();
        // markdown
        String markdownImgPath = common.getMarkdownImgPath();
        // user icon /usericon
        String userIconImgPath = common.getUserIconImgPath();
        // 获取系统类型
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            // markdown
            registry.addResourceHandler(imageMapping + markdownImgPath + "/**")
                    .addResourceLocations("file:" + common.getWin().getBaseFilePath() + markdownImgPath + "/");
            // user icon
            registry.addResourceHandler(imageMapping + userIconImgPath + "/**")
                    .addResourceLocations("file:" + common.getWin().getBaseFilePath() + userIconImgPath + "/");
        } else {//linux 和mac
            // markdown
            registry.addResourceHandler(imageMapping + markdownImgPath + "/**")
                    .addResourceLocations("file:" + common.getLinux().getBaseFilePath() + markdownImgPath + "/");
            // user icon
            registry.addResourceHandler(imageMapping + userIconImgPath + "/**")
                    .addResourceLocations("file:" + common.getLinux().getBaseFilePath() + userIconImgPath + "/");
        }
    }
}
