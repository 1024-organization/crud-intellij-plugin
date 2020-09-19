package com.github.mars05.crud.intellij.plugin.action;

import com.github.mars05.crud.intellij.plugin.setting.CrudSettings;
import com.github.mars05.crud.intellij.plugin.setting.path.PackagePath;
import com.github.mars05.crud.intellij.plugin.util.CrudUtils;
import com.github.mars05.crud.intellij.plugin.util.PsiFileUtils;
import com.github.mars05.crud.intellij.plugin.util.Selection;
import com.github.mars05.crud.intellij.plugin.util.SelectionContext;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.idea.maven.project.MavenProjectsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;
import static com.intellij.openapi.module.ModuleUtilCore.findModuleForFile;

/**
 * @author xiaoyu
 */
public class NewFileAction extends AnAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewFileAction.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Objects.requireNonNull(project);
        VirtualFile virtualFile = e.getData(VIRTUAL_FILE);
        Objects.requireNonNull(virtualFile);

        if (!virtualFile.isDirectory()) {
            virtualFile = virtualFile.getParent();
        }
        Module module = findModuleForFile(virtualFile, project);
        Objects.requireNonNull(module);

        // 项目绝对路径
        String moduleRootPath = ModuleRootManager.getInstance(module).getContentRoots()[0].getPath();

        String basePackage = getBasePackage(virtualFile, moduleRootPath);

        // 设置生成文件目录
        this.packagePathSettings(basePackage, moduleRootPath, module);

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
                    LOGGER.error("创建失败", ex);
                }
                //解决依赖
                MavenProjectsManager.getInstance(project).forceUpdateAllProjectsOrFindAllAvailablePomFiles();
                //优化生成的所有Java类
                CrudUtils.doOptimize(project);
                // 将包路径存储到配置文件中
                savePackagePath(module, moduleRootPath, selection, basePackage);
            }
        }.execute());
    }

    private void savePackagePath(Module module, String moduleRootPath, Selection selection, String basePackage) {

        Map<String, PackagePath> packagePathMap = Optional.ofNullable(CrudSettings.getInstance().getPckConfigs())
                .orElse(new HashMap<>(2));

        PackagePath packagePath = Optional.ofNullable(packagePathMap.get(module.getName()))
                .orElse(new PackagePath());

        // 始终会有
        packagePath.setModuleRootPath(moduleRootPath);
        packagePath.setBasePackage(basePackage);
        packagePath.setAuthor(selection.getAuthor());

        // 无需处理
        packagePath.setLombok(selection.isLombokSelected());

        // 判空, 防止将空的存进去导致记忆的内容被刷掉
        saveNotNull(selection.getControllerPackage(), packagePath::setController);
        saveNotNull(selection.getServicePackage(), packagePath::setService);
        saveNotNull(selection.getDaoPackage(), packagePath::setDao);
        saveNotNull(selection.getModelPackage(), packagePath::setModal);
        saveNotNull(selection.getMapperDir(), packagePath::setMapper);

        packagePathMap.put(module.getName(), packagePath);
    }

    private void saveNotNull(String value, Consumer<String> consumer) {
        if (StringUtils.isNotBlank(value)) {
            consumer.accept(value);
        }
    }

    private void packagePathSettings(String basePackage, String moduleRootPath, Module module) {


        // 查询配置中是否有设置, 如果有设置就使用配置中的.
        // 在生成时, 比对, 如果修改了就将新的刷新进入配置文件中

        PackagePath packagePath = Optional.ofNullable(CrudSettings.getInstance().getPckConfigs())
                .map(d -> d.get(module.getName()))
                .orElse(null);

        // 原有逻辑
        if (Objects.isNull(packagePath)) {

            SelectionContext.clearAllSet();

            SelectionContext.setPackage(basePackage);
            if (StringUtils.isNotBlank(basePackage)) {
                basePackage += ".";
            }

            SelectionContext.setControllerPackage(basePackage + "web.controller");
            SelectionContext.setServicePackage(basePackage + "service");
            SelectionContext.setDaoPackage(basePackage + "dao.mapper");
            SelectionContext.setModelPackage(basePackage + "pojo.entity");
            SelectionContext.setMapperDir(moduleRootPath + "/src/main/resources/mapper");

            return;
        }

        // 记忆路径功能
        basePackage = packagePath.getBasePackage();
        SelectionContext.clearAllSet();
        SelectionContext.setPackage(basePackage);

        SelectionContext.setControllerPackage(packagePath.getController());
        SelectionContext.setServicePackage(packagePath.getService());
        SelectionContext.setDaoPackage(packagePath.getDao());
        SelectionContext.setModelPackage(packagePath.getModal());
        SelectionContext.setMapperDir(packagePath.getMapper());
        SelectionContext.setLombokSelected(packagePath.getLombok());
        SelectionContext.setAuthor(packagePath.getAuthor());


    }

    private String getBasePackage(VirtualFile virtualFile, String moduleRootPath) {
        String actionDir = virtualFile.getPath();
        String str = StringUtils.substringAfter(actionDir, moduleRootPath + "/src/main/java/");

        return StringUtils.replace(str, "/", ".");
    }

}
