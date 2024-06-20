package com.virtusa.questor.dao;

import com.virtusa.questor.dto.TransactionDTO;
import com.virtusa.questor.model.Subscription;
import com.virtusa.questor.model.Transaction;
import com.virtusa.questor.repository.SubscriptionRepository;
import com.virtusa.questor.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionDAO {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO){
        Transaction transaction = toModel(transactionDTO);
        Subscription subscription = subscriptionRepository.findById(transactionDTO.getSubscriptionId()).orElse(null);

        if (subscription != null) {
            transaction.setSubscription(subscription);
            transaction = transactionRepository.save(transaction);
            return toDTO(transaction);
        } else {
            throw new IllegalArgumentException("Subscription not found: " + transactionDTO.getSubscriptionId());
        }
    }

    public List<TransactionDTO> findAllTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TransactionDTO findTransactionById(Long id){
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        return toDTO(transaction);
    }

    public List<TransactionDTO> findTransactionsBySubId(Long subscriptionId){
        List<Transaction> transactions = transactionRepository.findBySubscriptionId(subscriptionId);
        return transactions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TransactionDTO updateTransaction(TransactionDTO transactionDTO){
        Transaction existingTransaction = transactionRepository.findById(transactionDTO.getTransactionId()).orElse(null);
        if (existingTransaction != null){
            Transaction updatedTransaction = toModel(transactionDTO);
            updatedTransaction = transactionRepository.save(updatedTransaction);
            return toDTO(updatedTransaction);
        } else {
            throw new IllegalArgumentException("Transaction not found: " + transactionDTO.getTransactionId());
        }
    }

    public void deleteTransaction(Long id){
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null){
            transactionRepository.delete(transaction);
        } else {
            throw new IllegalArgumentException("Transaction not found: " + id);
        }
    }

    public TransactionDTO toDTO(Transaction transaction){
        return TransactionDTO.builder()
                .transactionId(transaction.getTransactionId())
                .paymentDate(transaction.getPaymentDate())
                .amount(transaction.getAmount())
                .paymentMethod(transaction.getPaymentMethod())
                .subscriptionId(transaction.getSubscription() != null ? transaction.getSubscription().getSubscriptionId() : null)
                .build();
    }

    private Transaction toModel(TransactionDTO transactionDTO){
        Transaction transaction = Transaction.builder()
                .transactionId(transactionDTO.getTransactionId())
                .paymentDate(transactionDTO.getPaymentDate())
                .amount(transactionDTO.getAmount())
                .paymentMethod(transactionDTO.getPaymentMethod())
                .build();

        if(transactionDTO.getSubscriptionId() != null){
            Subscription subscription = subscriptionRepository.findById(transactionDTO.getSubscriptionId()).orElse(null);
            if (subscription != null){
                transaction.setSubscription(subscription);
            }
        }
        return transaction;
    }

}
