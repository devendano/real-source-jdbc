package com.example.realsourcejdbc.rowmapper;

import com.example.realsourcejdbc.model.Tutorial;
import org.springframework.jdbc.core.RowMapper;

public class TutorialRowMapper {

    public final static String idColumn = "t_id";
    public final static String titleColumn = "t_title";
    public final static String descriptionColumn = "t_description";
    public final static String publishedColumn = "is_published";

    public static RowMapper<Tutorial> build() {
        return (rs, rowNum) -> new Tutorial(rs.getLong(idColumn), rs.getString(titleColumn),
                rs.getString(descriptionColumn), rs.getBoolean(publishedColumn));
    }
}
