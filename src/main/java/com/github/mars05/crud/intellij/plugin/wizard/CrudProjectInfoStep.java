package com.github.mars05.crud.intellij.plugin.wizard;

import com.github.mars05.crud.intellij.plugin.CrudBundle;
import com.github.mars05.crud.intellij.plugin.util.SelectionContext;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
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
        myFrameComboBox.addActionListener((actionEvent) -> {
            if (MYBATIS_PLUS == myFrameComboBox.getSelectedIndex()) {
                switchMybatisPlusComponentVisible(true);
            } else {
                switchMybatisPlusComponentVisible(false);
            }
        });

        // 当groupId与ArtifactId失去焦点时判断是否有值, 拼接package
        myGroupIdField.addCaretListener((actionEvent) -> fillPackageValue());
        myArtifactIdField.addCaretListener((actionEvent) -> fillPackageValue());
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
        }
        return super.validate();
    }

    public JTextField getArtifactIdField() {
        return myArtifactIdField;
    }
}
