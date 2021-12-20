package com.michal.szturc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

/**
 * @author Michał Szturc
 */
@Repository
public class PromoCodesRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PromoCodesRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Optional<String> findPromoCodeByCode(String code) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(Queries.SELECT_PROMO_CODE_BY_CODE,
                    Map.of("CODE", code), String.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

}
