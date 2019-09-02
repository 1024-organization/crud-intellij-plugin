package com.github.mars05.crud.intellij.plugin.util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.github.mars05.crud.intellij.plugin.model.*;
import com.google.common.base.CaseFormat;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.mars05.crud.intellij.plugin.util.SelectionContext.*;

/**
 * @author xiaoyu
 */
public class PsiFileUtils {
    private static FreemarkerConfiguration freemarker = new FreemarkerConfiguration("/templates");

    /**
     * 一维: 模板类型
     *      0: controller
     *      1: service
     *      2: serviceImpl
     *      3: model
     *      4: dao/mapper
     *      5: mapperXml
     *
     * 二维: 模板名称
     *      0: mybatis_plus
     *      1: mybatis
     *      2: jpa
     */
    private static final String[][] TEMPLATE_ARR = new String[6][3];
    static {
        // controller
        TEMPLATE_ARR[0][0] = "/templates/controller_mybatis_plus.java";
        TEMPLATE_ARR[0][1] = "controller.ftl";
        TEMPLATE_ARR[0][2] = "controller.ftl";

        // service
        TEMPLATE_ARR[1][0] = "/templates/service_mybatis_plus.java";
        TEMPLATE_ARR[1][1] = "service.ftl";
        TEMPLATE_ARR[1][2] = "service.ftl";

        // serviceImpl
        TEMPLATE_ARR[2][0] = "/templates/service_impl_mybatis_plus.java";
        TEMPLATE_ARR[2][1] = "service_impl.ftl";
        TEMPLATE_ARR[2][2] = "service_impl.ftl";

        // model
        TEMPLATE_ARR[3][0] = "/templates/entity_mybatis_plus.java";
        TEMPLATE_ARR[3][1] = "model_mybatis.ftl";
        TEMPLATE_ARR[3][2] = "model_jpa.ftl";

        // dao/mapper
        TEMPLATE_ARR[4][0] = "/templates/mapper_mybatis_plus.java";
        TEMPLATE_ARR[4][1] = "dao_mybatis.ftl";
        TEMPLATE_ARR[4][2] = "dao_jpa.ftl";

        // mapperXml
        TEMPLATE_ARR[5][0] = "/templates/mapper_mybatis_plus.xml.ftl";
        TEMPLATE_ARR[5][1] = "mapper.ftl";
    }

    private static final String[] NAME_SUFFIX_ARR = {".java", ".java", "Impl.java", ".java", ".java", "Mapper.xml"};

    /**
     * 创建文件
     * @param fileName 创建的文件名称
     * @param templateName 模板名称
     */
    public static void createFileByFileNameAndTemplateName(Project project, VirtualFile root, Selection selection, String fileName, String templateName) throws Exception {
        VirtualFile virtualFile = root.createChildData(project, fileName);
        StringWriter sw = new StringWriter();
        Template template = freemarker.getTemplate(templateName);
        template.process(selection, sw);
        virtualFile.setBinaryContent(sw.toString().getBytes(CrudUtils.DEFAULT_CHARSET));
    }

    public static void createPomXml(Project project, VirtualFile root, Selection selection) throws Exception {
        VirtualFile virtualFile = root.createChildData(project, "pom.xml");
        StringWriter sw = new StringWriter();
        Template template = freemarker.getTemplate("pom.ftl");
        template.process(selection, sw);
        virtualFile.setBinaryContent(sw.toString().getBytes(CrudUtils.DEFAULT_CHARSET));
    }

    public static void createSwagger(Project project, VirtualFile root, Selection selection) throws Exception {
        VirtualFile virtualFile = root.createChildData(project, "Swagger2Config.java");
        StringWriter sw = new StringWriter();
        Template template = freemarker.getTemplate("Swagger2Config.ftl");
        template.process(selection, sw);
        virtualFile.setBinaryContent(sw.toString().getBytes(CrudUtils.DEFAULT_CHARSET));
        CrudUtils.addWaitOptimizeFile(virtualFile);
    }

    public static void createApplicationJava(Project project, VirtualFile root, Selection selection) throws Exception {
        VirtualFile virtualFile = root.createChildData(project, "Application.java");
        StringWriter sw = new StringWriter();
        Template template = freemarker.getTemplate("Application.java.ftl");
        template.process(selection, sw);
        virtualFile.setBinaryContent(sw.toString().getBytes(CrudUtils.DEFAULT_CHARSET));
        CrudUtils.addWaitOptimizeFile(virtualFile);
    }

    public static void createApplicationYml(Project project, VirtualFile root, Selection selection) throws Exception {
        VirtualFile virtualFile = root.createChildData(project, "application.yml");
        StringWriter sw = new StringWriter();
        Template template = freemarker.getTemplate("application.yml.ftl");
        template.process(selection, sw);
        virtualFile.setBinaryContent(sw.toString().getBytes(CrudUtils.DEFAULT_CHARSET));
    }

    /**
     * 生成 mybatis-plus 配置文件
     */
    public static void createMybatisPlusConfiguration(Project project, VirtualFile root, Selection selection) throws Exception {
        VirtualFile virtualFile = root.createChildData(project, "MybatisPlusConfig.java");
        StringWriter sw = new StringWriter();
        Template template = freemarker.getTemplate("MybatisPlusConfig.java.ftl");
        template.process(selection, sw);
        virtualFile.setBinaryContent(sw.toString().getBytes(CrudUtils.DEFAULT_CHARSET));
    }

    /**
     * 生成基础代码
     * @param base 抽象顶级父类
     * @param arrIndex 模板对应索引位置
     * @throws IOException
     * @throws TemplateException
     */
    private static void createMvc(Project project, VirtualFile packageDir, Base base, int arrIndex) throws IOException, TemplateException {
        VirtualFile virtualFile = packageDir.createChildData(project, base.getSimpleName() + NAME_SUFFIX_ARR[arrIndex]);
        StringWriter sw = new StringWriter();
        Template template = freemarker.getTemplate(TEMPLATE_ARR[arrIndex][base.getOrmType()]);
        template.process(base, sw);
        virtualFile.setBinaryContent(sw.toString().getBytes(CrudUtils.DEFAULT_CHARSET));
        CrudUtils.addWaitOptimizeFile(virtualFile);
    }

    private static VirtualFile createPackageDir(String packageName, String moduleRootPath) {
        packageName = "src/main/java/" + packageName;
        String path = FileUtil.toSystemIndependentName(moduleRootPath + "/" + StringUtil.replace(packageName, ".", "/"));
        new File(path).mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
    }

    /**
     * 生成代码的核心功能
     */
    public static void createCrud(Project project, Selection selection, String moduleRootPath) throws Exception {
        List<Table> tables = selection.getTables();
        if (selection.getOrmType() == MYBATIS_PLUS) {
            // 调用MP代码生成
            generatorMybatisPlus(project, selection, moduleRootPath, tables);
        } else {
            generatorMybatisOrJpa(project, selection, moduleRootPath, tables);
        }
    }

    private static void generatorMybatisPlus(Project project, Selection selection, String moduleRootPath, List<Table> tables) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(moduleRootPath + "/src/main/java");
        gc.setAuthor(SelectionContext.getAuthor());
        gc.setServiceName("%sService");gc.setServiceImplName("%sServiceImpl");
        gc.setActiveRecord(SelectionContext.getActiveRecordModelSelected());
        gc.setOpen(false);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://"+selection.getConn().getHost()+":3306/"+selection.getDb()+"?useUnicode=true&useSSL=false&&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(selection.getConn().getUsername());
        dsc.setPassword(selection.getConn().getPassword());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(selection.getPackage());
        pc.setController("web.controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("dao.mapper");
        pc.setEntity("pojo.entity");
        pc.setXml("xml");
        mpg.setPackageInfo(pc);

        //自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(TEMPLATE_ARR[5][0]) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return moduleRootPath + "/src/main/resources/mapper/"  + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        TemplateConfig tc = new TemplateConfig();
        tc.setController(TEMPLATE_ARR[0][0]);
        tc.setService(TEMPLATE_ARR[1][0]);
        tc.setServiceImpl(TEMPLATE_ARR[2][0]);
        tc.setEntity(TEMPLATE_ARR[3][0]);
        tc.setMapper(TEMPLATE_ARR[4][0]);
        tc.setXml(null);

        mpg.setTemplate(tc);
        mpg.setCfg(cfg);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(SelectionContext.getLombokSelected());
//        strategy.setSuperControllerClass("cn.ideamake.template.web.controller.AbstractController");
        strategy.setInclude(getTableNameList(tables));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setRestControllerStyle(true);
        //表前缀
        strategy.setTablePrefix(SelectionContext.getTablePrefix());
        // 设置表填充字段
        if (SelectionContext.getFillFieldSelected()) {
            strategy.setTableFillList(configTableFillList());
        }
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static List<TableFill> configTableFillList() {
        ArrayList<TableFill> tableFillList = new ArrayList<>();
        TableFill createField = new TableFill("created_at", FieldFill.INSERT);
        TableFill modifiedField = new TableFill("updated_at", FieldFill.INSERT_UPDATE);
        tableFillList.add(createField);
        tableFillList.add(modifiedField);
        return tableFillList;
    }

    private static String[] getTableNameList(List<Table> tables) {
        String[] result = new String[tables.size()];
        List<String> tableNameList = tables.stream().map(Table::getName).collect(Collectors.toList());
        for (int i = 0; i < tableNameList.size(); i++) {
            result[i] = tableNameList.get(i);
        }
        return result;
    }

    private static void generatorMybatisOrJpa(Project project, Selection selection, String moduleRootPath, List<Table> tables) throws Exception {
        for (Table table : tables) {
            //model生成
            List<Column> columns = table.getColumns();
            List<Field> fields = new ArrayList<>();
            for (Column column : columns) {
                Field field = new Field(column.getComment(), JavaTypeUtils.convertType(column.getType()), column.getName());
                field.setId(column.isId());
                fields.add(field);
            }
            String modelPackage = selection.getModelPackage();
            if (modelPackage == null) {
                return;
            }
            VirtualFile modelPackageDir = createPackageDir(modelPackage, moduleRootPath);
            if (!StringUtils.isBlank(modelPackage)) {
                modelPackage += ".";
            }
            Model model = new Model(table.getComment(), modelPackage + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()), table.getName(), fields);
            model.setOrmType(selection.getOrmType());
            PsiFileUtils.createMvc(project, modelPackageDir, model, 3);
            //dao生成
            String daoPackage = selection.getDaoPackage();
            if (daoPackage == null) {
                continue;
            }
            VirtualFile daoPackageDir = createPackageDir(daoPackage, moduleRootPath);
            if (!StringUtils.isBlank(daoPackage)) {
                daoPackage += ".";
            }
            Dao dao = new Dao(table.getComment(), daoPackage + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()) + "DAO", model);
            PsiFileUtils.createMvc(project, daoPackageDir, dao, 4);
            //mybatis生成mapper.xml
            if (selection.getOrmType() == MYBATIS || selection.getOrmType() == MYBATIS_PLUS) {
                String mapperDir = selection.getMapperDir();
                if (StringUtils.isNotBlank(mapperDir)) {
                    String path = FileUtil.toSystemIndependentName(mapperDir);
                    new File(path).mkdirs();
                    VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
                    PsiFileUtils.createMvc(project, virtualFile, dao, 5);
                }
            }
            //service生成
            //接口
            String servicePackage = selection.getServicePackage();
            if (servicePackage == null) {
                continue;
            }
            VirtualFile servicePackageDir = createPackageDir(servicePackage, moduleRootPath);
            if (!StringUtils.isBlank(servicePackage)) {
                servicePackage += ".";
            }
            Service service = new Service(table.getComment(), servicePackage + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()) + "Service", dao);
            PsiFileUtils.createMvc(project, servicePackageDir, service,1);
            //实现
            String serviceImplPackage = servicePackage + "impl";
            VirtualFile serviceImplPackageDir = createPackageDir(serviceImplPackage, moduleRootPath);
            service.getImports().add(service.getDao().getName());
            service.getImports().add(service.getName());
            PsiFileUtils.createMvc(project, serviceImplPackageDir, service, 2);
            //controller生成
            String controllerPackage = selection.getControllerPackage();
            if (controllerPackage == null) {
                continue;
            }
            VirtualFile controllerPackageDir = createPackageDir(controllerPackage, moduleRootPath);
            if (!StringUtils.isBlank(controllerPackage)) {
                controllerPackage += ".";
            }
            Controller controller = new Controller(table.getComment(), controllerPackage + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()) + "Controller", service);
            PsiFileUtils.createMvc(project, controllerPackageDir, controller,  0);
        }
    }

}
