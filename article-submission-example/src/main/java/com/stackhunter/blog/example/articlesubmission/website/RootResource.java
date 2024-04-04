/*
 * Copyright (c) 2014-2018 North Concepts Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.stackhunter.blog.example.articlesubmission.website;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.resteasy.annotations.Form;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.northconcepts.templatemaster.content.Content;
import com.stackhunter.blog.example.articlesubmission.model.Article;


@Path("/")
@Produces(MediaType.TEXT_HTML)
public class RootResource {
    
    private static final List<Article> articles = new ArrayList<Article>();
    private static final Map<String, Article> pendingArticles = new ConcurrentHashMap<String, Article>();
    
    static {
        articles.add(new Article(
                "http://blog.stackhunter.com/2014/01/17/10-reasons-to-replace-your-jsps-with-freemarker-templates/",
                "10 Reasons to Replace Your JSPs With FreeMarker Templates", 
                "support@stackhunter.com",
                "Dele Taylor"));
        articles.add(new Article(
                "http://blog.stackhunter.com/2014/01/14/how-to-load-config-files-with-the-strategy-pattern/",
                "How to Load Config Files with the Strategy Pattern", 
                "support@stackhunter.com",
                "Dele Taylor"));
        articles.add(new Article(
                "http://northconcepts.com/blog/2013/01/18/6-tips-to-improve-your-exception-handling/",
                "6 Tips to Improve Your Exception Handling", 
                "support@stackhunter.com",
                "Dele Taylor"));
        articles.add(new Article(
                "http://northconcepts.com/blog/2011/07/05/use-dynamic-proxies-to-create-a-simple-powerful-event-bus-part-1/",
                "Use dynamic proxies to create a simple, powerful event bus ", 
                "support@stackhunter.com",
                "Dele Taylor"));
    }
    

    @GET
    @Path("/")
    public Response getHome() throws Throwable {
        return Response.seeOther(new URI("/articles/")).build();
    }

    @GET
    @Path("/articles")
    public Content getArticles() throws Throwable {
        Content content = new Content("site.html");
        content.add("body", new Content("articles/grid-articles.html"));
        content.add("articles", articles);
        return content;
    }

    @GET
    @Path("/articles/new")
    public Content getNewArticle() throws Throwable {
        Content content = new Content("site.html");
        content.add("body", new Content("articles/form-article.html"));
        return content;
    }

    @POST
    @Path("/articles/new")
    public Response postNewArticle(@Form Article article) throws Throwable {
        String articleId = UUID.randomUUID().toString();
        pendingArticles.put(articleId, article);
        
        return Response.seeOther(new URI("/articles/confirm/" + articleId + "/")).build();
    }

    @GET
    @Path("/articles/confirm/{articleId}")
    public Content getConfirmNewArticle(@PathParam("articleId") String articleId) throws Throwable {
        Article article = pendingArticles.get(articleId);
        
        Content content = new Content("site.html");
        
        content.add("title", "Confirm Article Submission");
        content.add("body", new Content("articles/form-article-confirmation.html"));
        content.add("body", new Content("articles/form-article.html"));
        content.add("addNewArticleFormAction", "../../new/");
        content.add("article", article);
        
        return content;
    }

    @POST
    @Path("/articles/confirm/{articleId}")
    public Response postConfirmNewArticle(@PathParam("articleId") String articleId) throws Throwable {
        Article article = pendingArticles.remove(articleId);
        articles.add(article);
        return Response.seeOther(new URI("/articles/")).build();
    }

}
