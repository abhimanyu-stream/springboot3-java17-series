package com.stream.authentication.util;



public class SQLConstants {
    public static final String SQL_CHECK_EITHER_TABLE_IS_EMPTY_OR_NOT = "SELECT COUNT(*) FROM authentication_service.role";
    public static final String SQL_DELETE_ALL_RECORDS = "DELETE FROM authentication_service.role";
    public static final String SQL_INSERT_ROLE_ADMIN = "INSERT INTO authentication_service.role(id, rolename) VALUES(?, ?)";
    public static final String SQL_INSERT_ROLE_USER = "INSERT INTO authentication_service.role(id, rolename) VALUES(?, ?)";
    public static final String SQL_INSERT_ROLE_MODERATOR = "INSERT INTO authentication_service.role(id, rolename) VALUES(?, ?)";

    public static final String SQL_FIND_EXISTING_USER = "SELECT id FROM authentication_service.user WHERE username = ? OR email = ? OR phone = ?";
    public static final String SQL_INSERT_USER = "INSERT INTO authentication_service.user (username, email, password, first_name, last_name, phone, created, modified, created_by, created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_GET_USER_ID_BY_EMAIL = "SELECT id FROM authentication_service.user WHERE email = ?";
    public static final String SQL_GET_ROLE_ID_BY_NAME = "SELECT id FROM authentication_service.role WHERE rolename = ?";
    public static final String SQL_ASSIGN_ROLE_TO_USER = "INSERT INTO authentication_service.user_roles (user_id, role_id) VALUES (?, ?)";

    // Refactored SQL to use the authentication_service schema for all related tables
    public static final String SQL_DELETE_FROM_ROLE_BY_ROLE_ID = "DELETE FROM authentication_service.role WHERE id = ?";

    // New SQL query to check if there are any user-role associations for a given role_id

    public static final String SQL_CHECK_USER_ROLE_ASSOCIATIONS = "SELECT COUNT(*) FROM authentication_service.user_roles WHERE role_id = ?";
    public static final String SQL_DELETE_FROM_USER_ROLES_BY_ROLE_ID = "DELETE FROM authentication_service.user_roles WHERE role_id = ?";


}
