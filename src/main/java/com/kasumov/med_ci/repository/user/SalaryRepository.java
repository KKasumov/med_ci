package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
}
