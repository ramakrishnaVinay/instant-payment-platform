# PaymentRepository - Negative Query Reference Guide

## 📋 Positive Query

### Method
```java
List<Payment> findByDebitAccountIdAndCreditAccountId(
    String debitAccountId, 
    String creditAccountId
);
```

### Exact MySQL Query
```sql
SELECT * FROM payments 
WHERE debit_account_id = '30-91-44/12309876' 
AND credit_account_id = '30-91-44/12309876'
```

**Result:** Payments where BOTH debit AND credit accounts match

---

## ❌ Negative Queries (NOT Logic)

### 1. BOTH NOT Matching (AND NOT)

**Method:**
```java
List<Payment> findByDebitAccountIdNotAndCreditAccountIdNot(
    String debitAccountId, 
    String creditAccountId
);
```

**Exact MySQL Query:**
```sql
SELECT * FROM payments 
WHERE debit_account_id != '30-91-44/12309876' 
AND credit_account_id != '30-91-44/12309876'
```

**Result:** Payments where debit account is NOT '30-91-44/12309876' AND credit account is NOT '30-91-44/12309876'

**Example Usage:**
```java
List<Payment> payments = paymentRepository.findByDebitAccountIdNotAndCreditAccountIdNot(
    "30-91-44/12309876", 
    "30-91-44/12309876"
);
```

---

### 2. EITHER NOT Matching (OR NOT)

**Method:**
```java
List<Payment> findByDebitAccountIdNotOrCreditAccountIdNot(
    String debitAccountId, 
    String creditAccountId
);
```

**Exact MySQL Query:**
```sql
SELECT * FROM payments 
WHERE debit_account_id != '30-91-44/12309876' 
OR credit_account_id != '30-91-44/12309876'
```

**Result:** Payments where EITHER debit OR credit account (or both) don't match

**Example Usage:**
```java
List<Payment> payments = paymentRepository.findByDebitAccountIdNotOrCreditAccountIdNot(
    "30-91-44/12309876", 
    "30-91-44/12309876"
);
```

---

### 3. Debit Account NOT Equal to Credit Account (External Payments Only)

**Method:**
```java
List<Payment> findExternalPayments();
```

**Exact MySQL Query:**
```sql
SELECT * FROM payments 
WHERE debit_account_id != credit_account_id
```

**Result:** All external payments (excludes internal transfers)

**Example Usage:**
```java
List<Payment> externalPayments = paymentRepository.findExternalPayments();
```

---

### 4. Debit Account Equals Credit Account (Internal Transfers Only)

**Method:**
```java
List<Payment> findInternalPayments();
```

**Exact MySQL Query:**
```sql
SELECT * FROM payments 
WHERE debit_account_id = credit_account_id
```

**Result:** Only internal transfers (transfers within same account)

**Example Usage:**
```java
List<Payment> internalPayments = paymentRepository.findInternalPayments();
```

---

### 5. Debit Account NOT Matching

**Method:**
```java
List<Payment> findByDebitAccountIdNot(String debitAccountId);
```

**Exact MySQL Query:**
```sql
SELECT * FROM payments 
WHERE debit_account_id != '30-91-44/12309876'
```

**Result:** Payments NOT from a specific debit account

**Example Usage:**
```java
List<Payment> payments = paymentRepository.findByDebitAccountIdNot("30-91-44/12309876");
```

---

### 6. Credit Account NOT Matching

**Method:**
```java
List<Payment> findByCreditAccountIdNot(String creditAccountId);
```

**Exact MySQL Query:**
```sql
SELECT * FROM payments 
WHERE credit_account_id != '30-91-44/12309876'
```

**Result:** Payments NOT to a specific credit account

**Example Usage:**
```java
List<Payment> payments = paymentRepository.findByCreditAccountIdNot("30-91-44/12309876");
```

---

## 📊 Quick Reference Table

| Query Method | MySQL WHERE Clause | Use Case |
|-------------|-------------------|----------|
| `findByDebitAccountIdAndCreditAccountId()` | `debit_account_id = ? AND credit_account_id = ?` | Find specific account pair payments |
| `findByDebitAccountIdNotAndCreditAccountIdNot()` | `debit_account_id != ? AND credit_account_id != ?` | Exclude specific account pair |
| `findByDebitAccountIdNotOrCreditAccountIdNot()` | `debit_account_id != ? OR credit_account_id != ?` | Exclude either account |
| `findExternalPayments()` | `debit_account_id != credit_account_id` | External transfers only |
| `findInternalPayments()` | `debit_account_id = credit_account_id` | Internal transfers only |
| `findByDebitAccountIdNot()` | `debit_account_id != ?` | Exclude debit account |
| `findByCreditAccountIdNot()` | `credit_account_id != ?` | Exclude credit account |
| `findByDebitAccountId()` | `debit_account_id = ?` | Find by debit account |
| `findByCreditAccountId()` | `credit_account_id = ?` | Find by credit account |

---

## 🔍 Operators Reference

| Operator | Meaning | SQL |
|----------|---------|-----|
| `=` | Equals | `column = value` |
| `!=` or `<>` | Not equals | `column != value` |
| `AND` | Both conditions true | `condition1 AND condition2` |
| `OR` | Either condition true | `condition1 OR condition2` |

---

## 💡 Common Scenarios

### Get all payments excluding internal transfers
```java
List<Payment> externalPayments = paymentRepository.findExternalPayments();
// SELECT * FROM payments WHERE debit_account_id != credit_account_id
```

### Get all payments excluding a specific account pair
```java
List<Payment> otherPayments = paymentRepository.findByDebitAccountIdNotAndCreditAccountIdNot(
    "30-91-44/12309876", 
    "30-91-44/12309876"
);
// SELECT * FROM payments WHERE debit_account_id != '30-91-44/12309876' AND credit_account_id != '30-91-44/12309876'
```

### Get all internal transfers
```java
List<Payment> internalTransfers = paymentRepository.findInternalPayments();
// SELECT * FROM payments WHERE debit_account_id = credit_account_id
```

### Get all payments from a specific account EXCEPT internal transfers
```java
// First approach: Get payments from account AND exclude internal transfers
List<Payment> fromAccount = paymentRepository.findByDebitAccountId("30-91-44/12309876");
List<Payment> externalOnly = fromAccount.stream()
    .filter(p -> !p.getDebitAccountId().equals(p.getCreditAccountId()))
    .collect(Collectors.toList());
```

