package com.bankease.bankease.service;

import com.bankease.bankease.dto.TransactionResponse;
import com.bankease.bankease.entities.*;
import com.bankease.bankease.exceptions.InsufficientBalanceException;
import com.bankease.bankease.exceptions.ResourceNotFoundException;
import com.bankease.bankease.repository.AccountRepository;
import com.bankease.bankease.repository.TransactionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository,TransactionRepository transactionRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionResponse deposit(Long accountId, BigDecimal amount){
        Account account = getActiveLocked(accountId);
        account.setBalance(account.getBalance().add(amount));
        return save(null,account,amount,TransactionType.DEPOSIT,"Cash Deposit");
    }

    @Transactional
    public TransactionResponse withdraw( Long accountId, BigDecimal amount){
        Account account = getActiveLocked(accountId);
        checkBalance(account,amount);
        account.setBalance(account.getBalance().subtract(amount));
        return save(account,null,amount,TransactionType.WITHDRAW,"Cash Withdraw"    );
    }

    @Transactional
    public TransactionResponse transfer(Long fromId,Long toId, BigDecimal amount){
        if (fromId.equals(toId))
            throw new IllegalArgumentException("Cannot Transfer to same account");

//        Account first = getActiveLocked(Math.min(fromId,toId));
//        Account second = getActiveLocked(Math.min(fromId,toId));
//
//        Account from = first.getId().equals(fromId) ? first : second ;
//        Account to = (from == first) ? second:first ;

        Account from;
        Account to;
        if (fromId < toId) {          // always lock the smaller id first
            from = getActiveLocked(fromId);
            to = getActiveLocked(toId);
        } else {
            to = getActiveLocked(toId);
            from = getActiveLocked(fromId);
        }

        checkBalance(from,amount);

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        return save(from,to,amount,TransactionType.TRANSFER,"Fund Transfer");

    }

    @Transactional
    public List<TransactionResponse> history(Long accountId){
        if ((!accountRepository.existsById(accountId)))
            throw new ResourceNotFoundException("Account Not Found : "+accountId);

        return  transactionRepository.findByAccountId(accountId).stream()
                .map(TransactionResponse::from).toList();
    }

    private Account getActiveLocked(Long id) {
        Account account = accountRepository.findByIdForUpdate(id)
                .orElseThrow(()->new ResourceNotFoundException("Account not Found : "+id));

        if (account.getStatus() != AccountStatus.ACTIVE)
            throw new IllegalArgumentException(("Account "+ id + " is not found : "+id));
            return account;

    }

    private void checkBalance(Account account, BigDecimal amount){
        if (account.getBalance().compareTo(amount) < 0)
            throw new InsufficientBalanceException("Insufficient Balance");
    }

    private TransactionResponse save(Account from, Account to, BigDecimal amount,
                                     TransactionType type,String description){
        Transaction t = new Transaction();
        t.setTxnRef("TXN"+ UUID.randomUUID().toString().replace("-","").substring(0,16).toUpperCase());
        t.setFromAccount(from);
        t.setToAccount(to);
        t.setAmount(amount);
        t.setType(type);
        t.setStatus(TransactionStatus.SUCCESS);t.setDescription(description);

        return TransactionResponse.from(transactionRepository.save(t));
    }
}
