package com.github.mars05.crud.intellij.plugin.setting.path;

/**
 * @author imyzt
 * @date 2020/07/30
 * @description 包路径记忆功能
 */
public class PackagePath {

    private String controller;
    private String service;
    private String dao;
    private String modal;
    private String mapper;
    private String author;
    private Boolean lombok;
    private String moduleRootPath;
    private String basePackage;

    public PackagePath() {
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDao() {
        return dao;
    }

    public void setDao(String dao) {
        this.dao = dao;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getModuleRootPath() {
        return moduleRootPath;
    }

    public void setModuleRootPath(String moduleRootPath) {
        this.moduleRootPath = moduleRootPath;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Boolean getLombok() {
        return lombok;
    }

    public void setLombok(Boolean lombok) {
        this.lombok = lombok;
    }
}