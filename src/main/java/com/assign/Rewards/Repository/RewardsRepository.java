package com.assign.Rewards.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assign.Rewards.Model.Transactions;

@Repository
public interface RewardsRepository extends JpaRepository<Transactions, Long>{


    @Query("select t from Transactions t")
    List<Transactions> getAllTransactions();
    

    Optional<List<Transactions>> findBycustomerId(Long id);
}
