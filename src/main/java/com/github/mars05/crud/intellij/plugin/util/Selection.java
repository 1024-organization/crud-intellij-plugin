package com.github.mars05.crud.intellij.plugin.util;

import com.github.mars05.crud.intellij.plugin.model.Table;
import com.github.mars05.crud.intellij.plugin.setting.Conn;

import java.util.List;

/**
 * @author xiaoyu
 */
public class Selection {
    private String projectType;
    private int ormType;
    private Conn conn;
    private String db;
    private List<Table> tables;
    private String groupId;
    private String artifactId;
    private String version;
    private String pkg;

    private boolean paginationSelected;
    private boolean performanceSelected;
    private boolean optimisticLockerSelected;
    private boolean activeRecordModelSelected;
    private boolean fillFieldSelected;
    private boolean lombokSelected;
    private String author;
    private String tablePrefix;

    private String daoPackage;
    private String servicePackage;
    private String controllerPackage;
    private String mapperDir;
    private String modelPackage;
    private boolean dockerfileSelected;
    private boolean jibSelected;
    private int jdkType;

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Conn getConn() {
        return conn;
    }

    public void setConn(Conn conn) {
        this.conn = conn;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackage() {
        return pkg;
    }

    public void setPackage(String pkg) {
        this.pkg = pkg;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getMapperDir() {
        return mapperDir;
    }

    public void setMapperDir(String mapperDir) {
        this.mapperDir = mapperDir;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setOrmType(int ormType) {
        this.ormType = ormType;
    }

    public int getOrmType() {
        return ormType;
    }

    public boolean isPaginationSelected() {
        return paginationSelected;
    }

    public void setPaginationSelected(boolean paginationSelected) {
        this.paginationSelected = paginationSelected;
    }

    public boolean isPerformanceSelected() {
        return performanceSelected;
    }

    public void setPerformanceSelected(boolean performanceSelected) {
        this.performanceSelected = performanceSelected;
    }

    public boolean isOptimisticLockerSelected() {
        return optimisticLockerSelected;
    }

    public void setOptimisticLockerSelected(boolean optimisticLockerSelected) {
        this.optimisticLockerSelected = optimisticLockerSelected;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public boolean isLombokSelected() {
        return lombokSelected;
    }

    public void setLombokSelected(boolean lombokSelected) {
        this.lombokSelected = lombokSelected;
    }

    public boolean isActiveRecordModelSelected() {
        return activeRecordModelSelected;
    }

    public void setActiveRecordModelSelected(boolean activeRecordModelSelected) {
        this.activeRecordModelSelected = activeRecordModelSelected;
    }

    public boolean isFillFieldSelected() {
        return fillFieldSelected;
    }

    public void setFillFieldSelected(boolean fillFieldSelected) {
        this.fillFieldSelected = fillFieldSelected;
    }

    public void setDockerfileSelected(boolean dockerfileSelected) {
        this.dockerfileSelected = dockerfileSelected;
    }

    public boolean getDockerfileSelected() {
        return dockerfileSelected;
    }

    public boolean isJibSelected() {
        return jibSelected;
    }

    public void setJibSelected(boolean jibSelected) {
        this.jibSelected = jibSelected;
    }

    public int getJdkType() {
        return jdkType;
    }

    public void setJdkType(int jdkType) {
        this.jdkType = jdkType;
    }
}
