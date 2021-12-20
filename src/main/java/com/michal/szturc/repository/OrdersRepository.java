package com.michal.szturc.repository;

import com.michal.szturc.exceptions.CambridgeRecruitmentException;
import com.michal.szturc.model.Order;
import com.michal.szturc.model.draft.OrderDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author Michał Szturc
 */
@Repository
public class OrdersRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderMapper orderMapper;

    @Autowired
    public OrdersRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.orderMapper = new OrderMapper();
    }

    public Boolean checkIfPromoCodeWasUsed(String promoCode) {
        try {
            var code = Optional.ofNullable(jdbcTemplate.queryForObject(Queries.SELECT_ORDER_BY_PROMO_CODE,
                    Map.of("PROMO_CODE", promoCode), String.class));
            return code.isPresent();
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Transactional
    public Order save(OrderDraft draft) {
        var params = new MapSqlParameterSource()
                .addValue("EMAIL", draft.email())
                .addValue("ORDER_PRICE", draft.orderPrice())
                .addValue("PRODUCT_ID", draft.productId())
                .addValue("PROMO_CODE", draft.promoCode())
                .addValue("ORDER_NUMBER_SUFFIX", draft.orderSuffix())
                .addValue("CREATED_AT", new Date());

        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(Queries.INSERT_INTO_ORDERS, params, keyHolder);
        if (keyHolder.getKeys() == null || !keyHolder.getKeys().containsKey("ID")) {
            throw new CambridgeRecruitmentException("Null id after insert");
        }
        return new Order(((Number) keyHolder.getKeys().get("ID")).longValue(), draft);
    }

    private final class OrderMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Order(
                    rs.getLong("ID"),
                    rs.getString("EMAIL"),
                    rs.getBigDecimal("ORDER_PRICE"),
                    rs.getLong("PRODUCT_ID"),
                    rs.getString("PROMO_CODE"),
                    rs.getString("ORDER_NUMBER_SUFFIX"),
                    rs.getDate("CREATED_AT")
            );
        }
    }
}
