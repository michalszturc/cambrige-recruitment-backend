package com.michal.szturc.repository;

import com.michal.szturc.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author Michał Szturc
 */
@Repository
public class ProductsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProductRowMapper productRowMapper;

    @Autowired
    public ProductsRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.productRowMapper = new ProductRowMapper();
    }

    public Collection<Product> findAll() {
        return jdbcTemplate.query(Queries.SELECT_ALL_PRODUCTS, this.productRowMapper);
    }
    public Optional<Product> findById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(Queries.SELECT_PRODUCT_BY_ID,
                    Map.of("ID", id), this.productRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    private final class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                    rs.getLong("ID"),
                    rs.getString("NAME"),
                    rs.getInt("MIN_QUANTITY"),
                    rs.getInt("MAX_QUANTITY"),
                    rs.getBigDecimal("UNIT_PRICE"),
                    rs.getDate("CREATED_AT")
            );
        }
    }


}
