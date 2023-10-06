package cc.cdtime.lifecapsule.meta.theme.dao;

import cc.cdtime.lifecapsule.meta.theme.entity.Theme;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ThemeDao {
    void createTheme(Theme theme);

    /**
     * 读取一个主题
     *
     * @param qIn themeId
     *            defaultTheme
     * @return
     */
    Theme getTheme(Map qIn);

    /**
     * 查询主题列表
     *
     * @param qIn themeType
     * @return
     */
    ArrayList<Theme> listTheme(Map qIn);

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
    void updateTheme(Map qIn);

    void setAllThemeStatusToActive(Map qIn);

    void deleteTheme(String themeId);
}
