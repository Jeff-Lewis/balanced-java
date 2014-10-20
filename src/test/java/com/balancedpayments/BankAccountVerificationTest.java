package com.balancedpayments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;

import com.balancedpayments.errors.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BankAccountVerificationTest extends BaseTest {

    @Test(expected=APIError.class)
    public void testFailedConfirm() throws CannotCreate, HTTPError {
        BankAccount ba = createBankAccount();
        ba.verify();
        ba.reload();
        BankAccountVerification bav = ba.verification;
        bav.confirm(12, 13);
    }

    @Test(expected=APIError.class)
    public void testDoubleConfirm() throws CannotCreate, HTTPError {
        BankAccount ba = createBankAccount();
        ba.verify();
        ba.reload();
        BankAccountVerification bav = ba.verification;
        bav.confirm(1, 1);
        bav.confirm(1, 1);
    }

    @Test
    public void testExhaustedConfirm() throws CannotCreate, HTTPError {
        BankAccount ba = createBankAccount();
        ba.verify();
        ba.reload();
        BankAccountVerification bav = ba.verification;
        while (bav.attempts_remaining != 1) {
            try {
                bav.confirm(12, 13);
            }
            catch (APIError e) {
                bav = new BankAccountVerification(bav.href);
                assertEquals("pending", bav.verification_status);
            }
        }
        try {
            bav.confirm(12, 13);
        }
        catch (APIError e){
            bav = new BankAccountVerification(bav.href);
            assertEquals(bav.verification_status, "failed");
        }
        assertEquals(bav.attempts_remaining.intValue(), 0);
        bav = ba.verify();
        bav.confirm(1, 1);
        assertEquals("succeeded", bav.verification_status);
        assertEquals(ba.href, bav.bank_account.href);
    }

    @Test
    public void testVerificationResourceFields() throws HTTPError {
        BankAccount bankAccount = createdAssociatedBankAccount();
        BankAccountVerification verification = bankAccount.verify();

        assertNotNull(verification.attempts);
        assertNotNull(verification.attempts_remaining);
        assertNotNull(verification.created_at);
        assertNotNull(verification.deposit_status);
        assertNotNull(verification.href);
        assertNotNull(verification.id);
        assertNotNull(verification.bank_account);
        assertNotNull(verification.meta);
        assertNotNull(verification.updated_at);
        assertNotNull(verification.verification_status);
    }
}
