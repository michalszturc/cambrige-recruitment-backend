package com.michal.szturc.repository;

/**
 * @author Michał Szturc
 */
public abstract class Queries {

    //Products
    static final String SELECT_ALL_PRODUCTS = """
            SELECT * FROM SHOP.PRODUCTS
            """;

    static final String SELECT_PRODUCT_BY_ID = """
            SELECT * FROM SHOP.PRODUCTS P WHERE P.ID = :ID
            """;


    // Promo codes

    static final String SELECT_PROMO_CODE_BY_CODE = """
          SELECT PS.CODE FROM SHOP.PROMO_CODES PS WHERE PS.CODE = :CODE
            """;


    // Orders
    static final String SELECT_ORDER_BY_PROMO_CODE = """
            SELECT O.PROMO_CODE FROM SHOP.ORDERS O WHERE O.PROMO_CODE = :PROMO_CODE
            """;

    static final String INSERT_INTO_ORDERS = """
            INSERT INTO SHOP.ORDERS (EMAIL, PRICE, PRODUCT_ID, PROMO_CODE, ORDER_NUMBER_SUFFIX, CREATED_AT)
            VALUES (:EMAIL, :ORDER_PRICE, :PRODUCT_ID, :PROMO_CODE, :ORDER_NUMBER_SUFFIX, :CREATED_AT);
            """;
}
