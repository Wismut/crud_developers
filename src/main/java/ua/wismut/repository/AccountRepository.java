package ua.wismut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.wismut.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
