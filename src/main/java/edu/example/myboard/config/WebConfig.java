package edu.example.myboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String uploadImagesPath;

    public WebConfig(@Value("${img.upload-dir}") String uploadImagesPath){
        this.uploadImagesPath = uploadImagesPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resistry) {

        //ckeditor 페이지 접근 관련
        resistry.addResourceHandler("/ckeditor/**")
                .addResourceLocations("classpath:/ckeditor/")
                .setCachePeriod(20);

        //plupload 페이지 접근 관련
        resistry.addResourceHandler("/plupload/**")
                .addResourceLocations("classpath:/plupload/")
                .setCachePeriod(20);

        //로컬로 프로젝트 돌릴 때, 프로젝트 외부에 이미지 파일 업로드 시 설정 사항 (프로젝트 내부에 이미지 파일 업로드하면 외부 톰캣으로 배포시 경로가 달라질 수 있음)
        //프로젝트 내부 폴더가 외부 디렉토리를 바라보도록 설정한다.
        //나의 경우에는 C 드라이브만 존재하여 접근 제한이 걸려 사용할 수 없었다.
        /*List<String> imgFolders = Arrays.asList("uploadImages","etc");
        for(String imgFolder : imgFolders){
            resistry.addResourceHandler("/static/"+imgFolder+"/**")
                    .addResourceLocations("file:///"+uploadImagesPath+imgFolder+"/")
                    .setCachePeriod(3600)
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver());
        }*/
    }
}
