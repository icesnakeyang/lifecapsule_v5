package cc.cdtime.lifecapsule.middle.theme;

import cc.cdtime.lifecapsule.meta.theme.entity.Theme;

import java.util.ArrayList;
import java.util.Map;

public interface IThemeMiddle {
    void createTheme(Theme theme) throws Exception;

    /**
     * 读取一个主题
     *
     * @param qIn themeId
     *            defaultTheme
     * @return
     */
    Theme getTheme(Map qIn, Boolean returnNull) throws Exception;

    /**
     * 查询主题列表
     *
     * @param qIn themeType
     * @return
     */
    ArrayList<Theme> listTheme(Map qIn) throws Exception;

    /**
     * 修改主题颜色
     *
     * @param qIn background
     *            blockDark
     *            blockLight
     *            textDark
     *            textLight
     *            textHolder
     *            themeName
     *            themeId
     *            colorDark2
     *            colorMedium2
     *            colorLight2
     */
    void updateTheme(Map qIn) throws Exception;

    void setAllThemeStatusToActive(Map qIn) throws Exception;

    void deleteTheme(String themeId) throws Exception;
}
