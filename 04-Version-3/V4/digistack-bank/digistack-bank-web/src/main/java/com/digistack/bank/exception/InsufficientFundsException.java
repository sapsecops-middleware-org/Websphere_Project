package com.digistack.bank.exception;

/**
 * InsufficientFundsException
 *
 * Thrown by AccountService.withdraw() when a withdrawal would exceed
 * the account's current balance. A checked exception (extends
 * Exception, not RuntimeException) - this forces every caller to
 * explicitly handle this case rather than letting it slip by silently,
 * which is exactly the kind of deliberate friction you want around
 * money-movement logic.
 */
public class InsufficientFundsException extends Exception {

    private static final long serialVersionUID = 1L;

    public InsufficientFundsException(String message) {
        super(message);
    }
}