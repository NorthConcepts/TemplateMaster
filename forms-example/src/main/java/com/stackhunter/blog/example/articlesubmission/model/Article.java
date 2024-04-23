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
package com.stackhunter.blog.example.articlesubmission.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.ws.rs.FormParam;

public class Article implements Serializable {
    
    private long id;

    @FormParam("url")
    private String url;
    
    @FormParam("title")
    private String title;
    
    @FormParam("email")
    private String email;
    
    @FormParam("name")
    private String name;
    
    @FormParam("lastUpdatedOn")
    private Date lastUpdatedOn;
    
    @FormParam("views")
    private int views;
    
    @FormParam("published")
    private boolean published;
    
    @FormParam("priority")
    private double priority;
    
    public Article() {
    }
    
    public Article(String url, String title, String email, String name) {
        this.url = url;
        this.title = title;
        this.email = email;
        this.name = name;
    }
    
    public long getId() {
        return id;
    }
    
    public Article setId(long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Article setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Article setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public Article setName(String name) {
        this.name = name;
        return this;
    }

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public Article setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public int getViews() {
        return views;
    }

    public Article setViews(int views) {
        this.views = views;
        return this;
    }

    public boolean isPublished() {
        return published;
    }

    public Article setPublished(boolean published) {
        this.published = published;
        return this;
    }

    public double getPriority() {
        return priority;
    }

    public Article setPriority(double priority) {
        this.priority = priority;
        return this;
    }

}
