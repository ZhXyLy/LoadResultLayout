package com.jx.loadresultlayout.widget;

/**
 * 配置应用默认的加载结果页面（优先级最低）
 * <p>
 * 可以在xml布局中单独设置当前页面的结果布局
 *
 * @author zhaoxl
 * @date 2018/6/8
 */
public class LoadResultLayoutConfig {
    private int defaultLoadingLayoutId;
    private int defaultEmptyLayoutId;
    private int defaultErrorLayoutId;
    private int defaultReloadViewId;

    private static LoadResultLayoutConfig INSTANCE;

    public static LoadResultLayoutConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (LoadResultLayoutConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoadResultLayoutConfig();
                }
            }
        }
        return INSTANCE;
    }

    private LoadResultLayoutConfig() {
    }

    /**
     * 设置默认的Loading页面
     *
     * @param layoutId 布局ID
     */
    public void setDefaultLoadingLayout(int layoutId) {
        defaultLoadingLayoutId = layoutId;
    }

    /**
     * 设置默认的空数据页面
     *
     * @param layoutId 布局ID
     */
    public void setDefaultEmptyLayout(int layoutId) {
        defaultEmptyLayoutId = layoutId;
    }

    /**
     * 设置默认的错误页面
     *
     * @param layoutId     布局ID
     * @param reloadViewId 重新加载按钮ID
     */
    public void setDefaultErrorLayout(int layoutId, int reloadViewId) {
        defaultErrorLayoutId = layoutId;
        defaultReloadViewId = reloadViewId;
    }

    int getDefaultLoadingLayoutId() {
        return defaultLoadingLayoutId;
    }

    int getDefaultEmptyLayoutId() {
        return defaultEmptyLayoutId;
    }

    int getDefaultErrorLayoutId() {
        return defaultErrorLayoutId;
    }

    int getDefaultReloadViewId() {
        return defaultReloadViewId;
    }
}
