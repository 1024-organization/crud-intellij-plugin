package com.github.mars05.crud.intellij.plugin.setting;

import com.github.mars05.crud.intellij.plugin.setting.path.PackagePath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoyu
 */
public class CrudState {

    private List<Conn> conns = new ArrayList<>();

    /**
     * 按照模块记录
     * k -> 模块名
     * v -> 最后一次生成时写入的目录
     */
    private Map<String, PackagePath> pcgConfigs = new HashMap<>();

    public Map<String, PackagePath> getPcgConfigs() {
        return pcgConfigs;
    }

    public void setPcgConfigs(Map<String, PackagePath> pcgConfigs) {
        this.pcgConfigs = pcgConfigs;
    }

    public List<Conn> getConns() {
        return conns;
    }

    public void setConns(List<Conn> conns) {
        this.conns = conns;
    }
}
