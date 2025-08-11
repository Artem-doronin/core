package ConcurrentBank;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentBank {
    private final ConcurrentMap<Long, BankAccount> accounts = new ConcurrentHashMap<>();
    private final AtomicLong accountNumberGenerator = new AtomicLong(1);

    public ConcurrentBank() {
    }

    public BankAccount createAccount(Long initialBalance) {
        Long accountId = accountNumberGenerator.incrementAndGet();
        BankAccount bankAccount = new BankAccount(accountId, initialBalance);
        accounts.put(accountId, bankAccount);
        return bankAccount;
    }

    public void transfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Счета не должны быть null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }
        if (fromAccount == toAccount) {
            throw new IllegalArgumentException("Нельзя переводить на тот же счёт");
        }
        if (fromAccount.getAccountId() > toAccount.getAccountId()) {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    performTransfer(fromAccount, toAccount, amount);
                }
            }
        } else {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    performTransfer(fromAccount, toAccount, amount);
                }
            }
        }
    }

    private void performTransfer(BankAccount from, BankAccount to, double amount) {
        if (from.getBalance() < amount) {
            throw new IllegalStateException("Недостаточно средств");
        }
        from.withdraw(amount);
        to.deposit(amount);
    }

    public double getTotalBalance() {
        return accounts.values().stream().mapToDouble(BankAccount::getBalance).sum();
    }
}
