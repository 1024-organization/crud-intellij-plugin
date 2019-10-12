package com.github.mars05.crud.intellij.plugin.action;

import com.github.mars05.crud.intellij.plugin.util.CrudUtils;
import com.github.mars05.crud.intellij.plugin.util.PsiFileUtils;
import com.github.mars05.crud.intellij.plugin.util.Selection;
import com.github.mars05.crud.intellij.plugin.util.SelectionContext;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.texen.util.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.idea.maven.project.MavenProjectsManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author xiaoyu
 */
public class NewFileAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        VirtualFile virtualFile = e.getData(DataKeys.VIRTUAL_FILE);
        if (!virtualFile.isDirectory()) {
            virtualFile = virtualFile.getParent();
        }
        Module module = ModuleUtil.findModuleForFile(virtualFile, project);

        // 项目绝对路径
        String moduleRootPath = ModuleRootManager.getInstance(module).getContentRoots()[0].getPath();
        // 选中事件发生时的目录
        String actionDir = virtualFile.getPath();

        String str = StringUtils.substringAfter(actionDir, moduleRootPath + "/src/main/java/");
        String basePackage = getBasePackage(project, moduleRootPath);
        SelectionContext.clearAllSet();

        SelectionContext.setPackage(basePackage);
        if (StringUtils.isNotBlank(basePackage)) {
            basePackage += ".";
        }
        SelectionContext.setControllerPackage(basePackage + "web.controller");
        SelectionContext.setServicePackage(basePackage + "service");
        SelectionContext.setDaoPackage(basePackage + "dao");
        SelectionContext.setModelPackage(basePackage + "model");
        SelectionContext.setMapperDir(moduleRootPath + "/src/main/resources/mapper");

        CrudActionDialog dialog = new CrudActionDialog(project, module);
        if (!dialog.showAndGet()) {
            return;
        }
        DumbService.getInstance(project).runWhenSmart((DumbAwareRunnable) () -> new WriteCommandAction(project) {
            @Override
            protected void run(@NotNull Result result) {
                Selection selection = SelectionContext.copyToSelection();
                SelectionContext.clearAllSet();
                try {
                    PsiFileUtils.createCrud(project, selection, moduleRootPath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //解决依赖
                MavenProjectsManager.getInstance(project).forceUpdateAllProjectsOrFindAllAvailablePomFiles();
                //优化生成的所有Java类
                CrudUtils.doOptimize(project);
            }
        }.execute());
    }

    private String getBasePackage(Project project, String moduleRootPath)  {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(moduleRootPath + File.separator + "plugin.properties")));

            String groupId = properties.getProperty("groupId");
            String artifactId = properties.getProperty("artifactId");

            if (StringUtils.isNoneBlank(groupId, artifactId)) {
                return groupId + "." + artifactId;
            }
            Messages.showWarningDialog(project, "plugin.ini未配置", "警告");
            throw new RuntimeException("plugin.ini未配置");
        } catch (IOException e) {
            int yesNo = Messages.showYesNoCancelDialog(project, "读取配置文件失败, 请配置插件", "警告", null);
            if (yesNo != 1) {
                // 创建一个URI实例
                java.net.URI uri = java.net.URI.create("https://juejin.im/post/5da1853b5188251f8b550767");
                // 获取当前系统桌面扩展
                java.awt.Desktop dp = java.awt.Desktop.getDesktop();
                // 判断系统桌面是否支持要执行的功能
                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    // 获取系统默认浏览器打开链接
                    try {
                        dp.browse(uri);
                    } catch (IOException ex) {
                        Messages.showWarningDialog(project, "系统不支持打开浏览器", "错误");
                        throw new RuntimeException("系统不支持打开浏览器, 模板参考: https://juejin.im/post/5da1853b5188251f8b550767");
                    }
                }
            }
        }
        throw new RuntimeException("读取配置文件失败");
    }
}
