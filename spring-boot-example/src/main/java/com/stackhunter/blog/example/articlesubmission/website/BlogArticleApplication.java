package com.stackhunter.blog.example.articlesubmission.website;

import java.util.HashSet;
import java.util.Set;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

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
