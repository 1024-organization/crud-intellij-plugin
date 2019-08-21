package com.github.mars05.crud.intellij.plugin.action;

import com.github.mars05.crud.intellij.plugin.ui.CrudConnView;
import com.github.mars05.crud.intellij.plugin.ui.CrudDbView;
import com.github.mars05.crud.intellij.plugin.ui.CrudTableView;
import com.github.mars05.crud.intellij.plugin.util.SelectionContext;
import com.github.mars05.crud.intellij.plugin.wizard.CrudConnStep;
import com.github.mars05.crud.intellij.plugin.wizard.CrudDbStep;
import com.github.mars05.crud.intellij.plugin.wizard.CrudTableStep;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.wizard.AbstractWizard;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

/**
 * @author xiaoyu
 */
public class CrudActionDialog extends AbstractWizard<ModuleWizardStep> {

    private Project myProject;
    private Module myModule;
    private CrudTableView crudTableView;

    public CrudActionDialog(Project project, Module module) {


        super("Create New Crud", project);
        crudTableView = new CrudTableView();
        myProject = project;
        myModule = module;
        ModuleWizardStep[] wizardSteps = createWizardSteps();
        for (ModuleWizardStep wizardStep : wizardSteps) {
            addStep(wizardStep);
        }
        init();
    }

    @Override
    protected void doOKAction() {
        ModuleWizardStep step = getCurrentStepObject();
        try {
            if (step.validate()) {
                super.doOKAction();
            }
        } catch (ConfigurationException e) {
            Messages.showErrorDialog(step.getComponent(), e.getMessage(), e.getTitle());
        }
    }

    @Override
    protected void doNextAction() {
        ModuleWizardStep step = getCurrentStepObject();
        try {
            if (step.validate()) {
                SelectionContext.setTablePrefix(crudTableView.getTablePrefix());
                super.doNextAction();
            }
        } catch (ConfigurationException e) {
            Messages.showErrorDialog(step.getComponent(), e.getMessage(), e.getTitle());
        }
    }

    public ModuleWizardStep[] createWizardSteps() {
        CrudTableStep tableStep = new CrudTableStep(crudTableView);
        CrudDbStep dbStep = new CrudDbStep(new CrudDbView(), tableStep);
        CrudConnStep connStep = new CrudConnStep(new CrudConnView(), dbStep);
        return new ModuleWizardStep[]{
                connStep,
                dbStep,
                tableStep,
                new CrudDirSelectInfoStep(myProject,myModule)
        };
    }

    @Nullable
    @Override
    protected String getHelpID() {
        return CrudActionDialog.class.getName();
    }
}

