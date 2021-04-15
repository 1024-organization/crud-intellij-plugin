package com.github.mars05.crud.intellij.plugin.wizard;

import com.github.mars05.crud.intellij.plugin.CrudBundle;
import com.github.mars05.crud.intellij.plugin.util.SelectionContext;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;

import static com.github.mars05.crud.intellij.plugin.util.SelectionContext.MYBATIS_PLUS;

/**
 * @author xiaoyu
 */
public class CrudProjectInfoStep extends ModuleWizardStep {
    private JPanel myMainPanel;
    private JTextField myGroupIdField;
    private JTextField myArtifactIdField;
    private JTextField myVersionField;
    private JTextField myPackageField;
    private JComboBox myFrameComboBox;
    private JCheckBox paginationCheckBox;
    private JCheckBox performanceCheckBox;
    private JCheckBox optimisticLockerCheckBox;
    private JLabel installPlugin;
    private JTextField author;
    private JCheckBox lombokCheckBox;
    private JLabel otherSetting;
    private JCheckBox activeRecordModel;
    private JCheckBox fillFieldCheckBox;
    private JCheckBox dockerfileCheckBox;
    private JCheckBox googleJibCheckBox;
    private JComboBox jdkComboBox;
    private JCheckBox gitInitBox;
    private JTextField gitRepUrl;


    @Override
    public JComponent getComponent() {
        return myMainPanel;
    }

    @Override
    public void updateDataModel() {

        // 下一步
    }

    public CrudProjectInfoStep() {

        // 当选中MP的时候,展示MP的插件选项
        if (null != myFrameComboBox) {
            myFrameComboBox.addActionListener((actionEvent) -> {
                if (MYBATIS_PLUS == myFrameComboBox.getSelectedIndex()) {
                    switchMybatisPlusComponentVisible(true);
                } else {
                    switchMybatisPlusComponentVisible(false);
                }
            });
        }

        // 当groupId与ArtifactId失去焦点时判断是否有值, 拼接package
        myGroupIdField.addCaretListener((actionEvent) -> fillPackageValue());
        myArtifactIdField.addCaretListener((actionEvent) -> fillPackageValue());

        // 当选中自动填充插件时, 提示
        fillFieldCheckBox.addActionListener((actionEvent) -> {
            if (fillFieldCheckBox.isSelected()) {
                int yesNoDialog = Messages.showYesNoDialog("确认每张表都含有created_at与updated_at字段, 插件不做检查", "提示", Messages.getWarningIcon());
                // 如果点击了否, 将选择状态设置为 不勾选
                if (yesNoDialog == 1) {
                    fillFieldCheckBox.setSelected(false);
                }
            }
        });
    }

    /**
     * 根据groupId和artifactId填充package值
     */
    private void fillPackageValue() {
        String groupId = myGroupIdField.getText();
        String artifactId = myArtifactIdField.getText();
        String text = "";
        if (StringUtils.isNotBlank(groupId)) {
            text += groupId;
            if (StringUtils.isNotBlank(artifactId)) {
                text = groupId + "." + artifactId;
            }
        }
        myPackageField.setText(text);
    }

    private void switchMybatisPlusComponentVisible(boolean visible) {
        paginationCheckBox.setVisible(visible);
        performanceCheckBox.setVisible(visible);
        optimisticLockerCheckBox.setVisible(visible);
        installPlugin.setVisible(visible);
        lombokCheckBox.setVisible(visible);
        activeRecordModel.setVisible(visible);
        fillFieldCheckBox.setVisible(visible);
    }

    @Override
    public boolean validate() throws ConfigurationException {
        if (StringUtil.isEmptyOrSpaces(myGroupIdField.getText())) {
            throw new ConfigurationException(CrudBundle.message("projectinfo.validate.groupid"));
        }
        if (StringUtil.isEmptyOrSpaces(myArtifactIdField.getText())) {
            throw new ConfigurationException(CrudBundle.message("projectinfo.validate.artifactid"));
        }
        if (StringUtil.isEmptyOrSpaces(myVersionField.getText())) {
            throw new ConfigurationException(CrudBundle.message("projectinfo.validate.version"));
        }
        if (StringUtil.isEmptyOrSpaces(myPackageField.getText())) {
            throw new ConfigurationException(CrudBundle.message("projectinfo.validate.package"));
        }
        if (gitInitBox.isSelected() && StringUtils.isBlank(gitRepUrl.getText())) {
            throw new ConfigurationException(CrudBundle.message("projectinfo.validate.gitRepUrl"));
        }

        SelectionContext.setGroupId(myGroupIdField.getText());
        SelectionContext.setArtifactId(myArtifactIdField.getText());
        SelectionContext.setVersion(myVersionField.getText());
        SelectionContext.setPackage(myPackageField.getText());
        SelectionContext.setOrmType(myFrameComboBox.getSelectedIndex());
        SelectionContext.setAuthor(author.getText());
        if (myFrameComboBox.getSelectedIndex() == MYBATIS_PLUS) {
            SelectionContext.setPaginationSelected(paginationCheckBox.isSelected());
            SelectionContext.setPerformanceSelected(performanceCheckBox.isSelected());
            SelectionContext.setOptimisticLockerSelected(optimisticLockerCheckBox.isSelected());
            SelectionContext.setLombokSelected(lombokCheckBox.isSelected());
            SelectionContext.setActiveRecordModelSelected(activeRecordModel.isSelected());
            SelectionContext.setFillFieldSelected(fillFieldCheckBox.isSelected());
        }
        SelectionContext.setDockerfileSelected(dockerfileCheckBox.isSelected());
        SelectionContext.setJibSelected(googleJibCheckBox.isSelected());
        SelectionContext.setJdkType(jdkComboBox.getSelectedIndex());
        SelectionContext.setGitInit(gitInitBox.isSelected());
        SelectionContext.setGitRepUrl(gitRepUrl.getText());
        return super.validate();
    }

}
