package pro.sisit.unit9.data;

import org.springframework.jdbc.core.JdbcTemplate;
import pro.sisit.unit9.entity.Book;

import java.util.List;

public class BookComplexQueryRepositoryImpl implements BookComplexQueryRepository {
    private JdbcTemplate jdbcTemplate;

    BookComplexQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> complexQueryMethod() {
        return jdbcTemplate.query("select id, title, description, year from book",
                (rs, rowNum) -> Book.builder()
                        .id(rs.getLong("id"))
                        .year(rs.getInt("year"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .build());
    }
}
