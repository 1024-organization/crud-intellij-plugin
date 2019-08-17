package com.github.mars05.crud.intellij.plugin.ui;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoyu
 */
public class CrudTableView implements CrudView {
    private JPanel myMainPanel;
    private JList myTableList;
    private JLabel myTableLabel;
    private JLabel myPathLabel;
    private JScrollPane myScrollPane;
    private JTextField tablePrefix;

    @Override
    public CrudList getCrudList() {
        CrudList crudList = (CrudList) this.myTableList;
        crudList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        return crudList;
    }

    /**
     * todo 自动判断表前缀
     */
    private static String getPrefix(List<ListElement> selectedValuesList) {
        List<String> nameList = selectedValuesList.stream().map(ListElement::getName).collect(Collectors.toList());
        String prefix = "";
        String firstTableName = nameList.get(0);
        String tablePrefix;
        for (int i = 0; i < firstTableName.length(); i++) {
            tablePrefix = firstTableName.substring(0, i+1);
            for (String innerName : nameList) {
                if (!innerName.startsWith(tablePrefix)) {
                    return prefix;
                }
            }
            prefix = tablePrefix;
        }
        return prefix;
    }

    @Override
    public JComponent getComponent() {
        return myMainPanel;
    }

    public JLabel getPathLabel() {
        return myPathLabel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        myScrollPane = new JBScrollPane();
        myTableLabel = new JBLabel();
    }

    public String getTablePrefix() {
        return tablePrefix.getText();
    }
}
