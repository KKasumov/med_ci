package com.kasumov.med_ci.service.entity.user;

public interface UserService {

    boolean existsByEmail(String email);

    String getUserRoleById(Long id);

}
