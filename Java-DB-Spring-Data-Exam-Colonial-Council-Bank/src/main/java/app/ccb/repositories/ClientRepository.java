package app.ccb.repositories;

import app.ccb.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    Optional<Client> findFirstByFullName(String name);

    @Query("SELECT c FROM Client c ORDER BY  c.bankAccount.cards.size DESC ")
    Set<Client> findClientByBankAccountCards();
}
