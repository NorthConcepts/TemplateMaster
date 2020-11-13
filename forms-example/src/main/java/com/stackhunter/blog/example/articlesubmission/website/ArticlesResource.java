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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Form;

import com.northconcepts.templatemaster.content.Content;
import com.stackhunter.blog.example.articlesubmission.model.Article;


@SuppressWarnings("deprecation")
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ArticlesResource {
    
    private static final List<Article> articles = new ArrayList<Article>();
    private static final Map<String, Article> pendingArticles = new ConcurrentHashMap<String, Article>();
    
    static {
        for (int i = 0; i < 100; i++) {
            articles.add(new Article(
                    "http://blog.stackhunter.com/2014/01/17/10-reasons-to-replace-your-jsps-with-freemarker-templates/" + i,
                    "10 Reasons to Replace Your JSPs With FreeMarker Templates" + i, 
                    "support@stackhunter.com" + i,
                    "Dele Taylor" + i)
                    .setLastUpdatedOn(new Date(2018, 1, i+1))
                    .setViews(i)
                    .setPublished(i % 2 == 0)
                    .setPriority(1.1 * i));
            articles.add(new Article(
                    "http://blog.stackhunter.com/2014/01/14/how-to-load-config-files-with-the-strategy-pattern/" + i,
                    "How to Load Config Files with the Strategy Pattern" + i, 
                    "support@stackhunter.com" + i,
                    "John Smith" + i)
                    .setLastUpdatedOn(new Date(2018, 2, 2+1))
                    .setViews(i)
                    .setPublished(i % 3 == 0)
                    .setPriority(2.1 * i));
            articles.add(new Article(
                    "http://northconcepts.com/blog/2013/01/18/6-tips-to-improve-your-exception-handling/" + i,
                    "6 Tips to Improve Your Exception Handling" + i, 
                    "support@stackhunter.com" + i,
                    "Henry Williams" + i)
                    .setLastUpdatedOn(new Date(2018, 3, 3+1))
                    .setViews(i)
                    .setPublished(i % 5 == 0)
                    .setPriority(3.1 * i));
            articles.add(new Article(
                    "http://northconcepts.com/blog/2011/07/05/use-dynamic-proxies-to-create-a-simple-powerful-event-bus-part-1/" + i,
                    "Use dynamic proxies to create a simple, powerful event bus " + i, 
                    "support@stackhunter.com" + i,
                    "Aaron Renolds" + i)
                    .setLastUpdatedOn(new Date(2018, 4, 4+1))
                    .setViews(i)
                    .setPublished(i % 7 == 0)
                    .setPriority(4.1 * i));
        }
    }
    

//    @GET
//    @Path("/")
//    public Response getHome() throws Throwable {
//        return Response.seeOther(new URI("/articles/")).build();
//    }

    @GET
    @Path("/")
    public Content getArticles() throws Throwable {
        Content content = new Content("site.html");
        content.add("body", new Content("articles/grid-articles.html"));
        content.add("articles", articles);
        return content;
    }

    @GET
    @Path("/new")
    public Content getNewArticle() throws Throwable {
        Content content = new Content("site.html");
        content.add("body", new Content("articles/form-article.html"));
        return content;
    }

    @POST
    @Path("/new")
    public Response postNewArticle(@Form Article article) throws Throwable {
        String articleId = UUID.randomUUID().toString();
        pendingArticles.put(articleId, article);
        
        return Response.seeOther(new URI("/confirm/" + articleId + "/")).build();
    }

    @GET
    @Path("/confirm/{articleId}")
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
    @Path("/confirm/{articleId}")
    public Response postConfirmNewArticle(@PathParam("articleId") String articleId) throws Throwable {
        Article article = pendingArticles.remove(articleId);
        articles.add(article);
        return Response.seeOther(new URI("/")).build();
    }

}
