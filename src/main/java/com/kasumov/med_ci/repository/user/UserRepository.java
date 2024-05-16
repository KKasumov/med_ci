package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = :email")
    User getUserByEmailWithRole(String email);

    @Query("SELECT u.role.name FROM User u WHERE u.id = :id")
    String getUserRoleById(Long id);

    boolean existsByEmail(String email);

}
