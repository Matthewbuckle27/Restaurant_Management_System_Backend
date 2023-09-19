package com.maveric.restaurantmanagementsystem.config;

public class AppConstants {

    //Controller
    public static final String ORDER_MAPPING = "/order";
    public static final String ORDER_FIND_BY_STATUS = "/{status}";
    public static final String ORDER_FIND_BY_USER_ID = "/id/{userId}";
    public static final String PRODUCT_MAPPING = "/product";
    public static final String PRODUCT_CHANGE_AVAILABILITY = "/{productId}";
    public static final String PRODUCT_CHANGE_PRICE = "/{productId}/{price}";
    public static final String PRODUCT_ADDED = "Product Added";
    public static final String PRODUCT_UPDATED = "Product Made Unavailable";
    public static final String PRODUCT_PRICE_UPDATED = "Product price updated";
    public static final String USER_MAPPING = "/user";

    //DTO Validations
    public static final String USERID_NOTNULL = "Enter userID";
    public static final String ITEMS_NOTNULL = "Please add at least one item";
    public static final String PRODUCT_NAME_NOTNULL = "Product Name is required";
    public static final String DESCRIPTION_NOTNULL = "Product Description is required";
    public static final String PRICE_MIN = "Price must be greater than zero";
    public static final String USERNAME_NOTNULL = "Username is required";
    public static final String EMAIL_NOTNULL = "Email is required";
    public static final String INVALID_EMAIL = "Invalid Email format";
    public static final String PASSWORD_NOTNULL = "Password is required";
    public static final String PASSWORD_LENGTH = "Password must be at least 8 characters";
    public static final String ROLE_NOTNULL = "Specify Role";

    //Entity
    public static final String ITEM_TABLE = "order_items";
    public static final String PRODUCT_ID = "product_id";
    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_ID_STRATEGY = "com.maveric.restaurantmanagementsystem.generator.OrderIdGenerator";
    public static final String PRODUCT_TABLE = "products";
    public static final String USER_TABLE = "user";
    public static final String ORDER_PREFIX = "ORDER";
    public static final String QUERY_GENERATE_ORDER_ID = "SELECT MAX(CAST(SUBSTRING(order_id, 6) AS UNSIGNED)) FROM orders";
    public static final String ORDER_SUFFIX = "00001";
    public static final String ORDER_SUFFIX_FORMAT = "%05d";

    //Exception
    public static final String NULL_POINTER_EXCEPTION = "Null Pointer Exception";
    public static final String ITEM_OUT_OF_STOCK_EXCEPTION = "ItemOutOfStockException";
    public static final String PRODUCT_NOT_FOUND_EXCEPTION = "ProductNotFoundException";
    public static final String USER_NOT_FOUND_EXCEPTION = "UserNotFoundException";
    public static final String NO_ORDER_EXCEPTION = "NoOrderException";
    public static final String INVALID_STATUS_EXCEPTION = "InvalidStatusException";
    public static final String SERVICE_EXCEPTION = "Service Exception";
    public static final String ITEM_EMPTY_EXCEPTION = "Empty Item Exception";

    //Service
    public static final String CREATE_ORDER_FAILURE = "Unable to create order";
    public static final String CREATE_USER_FAILURE = "Unable to create User";
    public static final String USER_NOT_FOUND = "User Not Found";
    public static final String PRODUCT_NOT_FOUND = "Product Not Found";
    public static final String PRODUCT_OUT_OF_STOCK = "Product is out of stock";
    public static final String WRONG_STATUS = "Status must be either A, C or X";
    public static final String NO_ORDERS = "There are no orders";
    public static final String GET_ORDER_FAILURE = "Unable to load orders";
    public static final String ADD_PRODUCT_FAILURE = "Unable to add Product";
    public static final String GET_PRODUCTS_FAILURE = "Unable to load products";
    public static final String UPDATE_PRODUCT_FAILURE = "Unable to update product";
    public static final String UPDATE_PRICE_FAILURE = "Unable to update product price";
    public static final String EMPTY_ITEM = "Please add one item";

}
