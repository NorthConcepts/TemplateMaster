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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.northconcepts.templatemaster.content.Util;
import com.northconcepts.templatemaster.form.CrudResource;
import com.northconcepts.templatemaster.form.FormDef;
import com.northconcepts.templatemaster.rest.RequestHolder;
import com.stackhunter.blog.example.articlesubmission.model.Article;


@SuppressWarnings("deprecation")
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ArticlesResource extends CrudResource<Long, Article> {
    
    private static final FormDef formDef = new FormDef();
    
    static {
//        formDef.setPaginate(false);
//        formDef.setDefaultPageSize(10);
        formDef.setAllowSort(false);
//        formDef.setAllowView(false);
        formDef.setAllowEdit(false);
        
        formDef.add("id", "ID");
        formDef.add("url", "URL");
        formDef.add("title", "Title");
        formDef.add("email", "Email");
        formDef.add("name", "Name");
        formDef.add("lastUpdatedOn", "Last Updated");
        formDef.add("views", "Views");
        formDef.add("published", "Published");
        formDef.add("priority", "Priority");
    }
    
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
    
    public ArticlesResource() {
        super("Article", "Articles", "/articles", formDef); 
    }
    
    public String getBaseUrl() {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        String url = request.getRequestURL().toString();
        String baseUrl = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
        return baseUrl;
    }

    @Override
    protected Page<Article> getPage(String keyword, String sortField, String namedFilterCode, int pageNumber, Integer pageSize) {
        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, articles.size() - fromIndex);
        
        Sort sort = Util.isEmpty(sortField)?Sort.unsorted():Sort.by(sortField);
        
        // TODO sort articles
        
        return new PageImpl<>(articles.subList(fromIndex, toIndex), PageRequest.of(pageNumber, pageSize, sort), articles.size());
    }

    @Override
    protected Page<Article> getPage(String keyword, String sortField, int pageNumber, Integer pageSize) {
        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, articles.size() - fromIndex);

        Sort sort = Util.isEmpty(sortField)?Sort.unsorted():Sort.by(sortField);

        // TODO sort articles

        return new PageImpl<>(articles.subList(fromIndex, toIndex), PageRequest.of(pageNumber, pageSize, sort), articles.size());
    }

    @Override
    protected Integer getCurrentPageSize() {
        return formDef.getDefaultPageSize();
    }

    @Override
    public Long getId(Article record) {
        return record.getId();
    }

    @Override
    protected Article getRecord(Long id) {
        return articles.stream().filter(article -> article.getId() == id).findAny().orElse(null);
    }


    

//    @GET
//    @Path("/")
//    public Response getHome() throws Throwable {
//        return Response.seeOther(new URI("/articles/")).build();
//    }
    
/*
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
*/

}
