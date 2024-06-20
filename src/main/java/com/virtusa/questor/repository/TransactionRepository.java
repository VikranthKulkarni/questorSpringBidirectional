package com.virtusa.questor.repository;

import com.virtusa.questor.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("select t from Transaction t where t.subscription.subscriptionId = :subscriptionId")
    List<Transaction> findBySubscriptionId(Long subscriptionId);

}
