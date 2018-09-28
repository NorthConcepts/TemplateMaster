package com.stackhunter.blog.example.articlesubmission.website;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/articles")
public class BlogArticleApplication extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(ArticlesResource.class);
        return s;        
    }

}
