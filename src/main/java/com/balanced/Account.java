package com.balanced;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.balanced.core.Client;
import com.balanced.core.Resource;
import com.balanced.core.ResourceCollection;
import com.balanced.core.ResourceQuery;
import com.balanced.errors.HTTPError;

public class Account extends Resource {
    
    public static final String BUYER_ROLE = "buyer";
    public static final String MERCHANT_ROLE = "merchant";
    
    public Date created_at;
    public String name;
    public String email_address;
    public String roles[];
    public String bank_accounts_uri;
    public ResourceCollection<BankAccount> bank_accounts;
    public String cards_uri;
    public ResourceCollection<Card> cards;
    public String credits_uri;
    public ResourceCollection<Credit> credits;
    public String debits_uri;
    public ResourceCollection<Debit> debits;
    public String holds_uri;
    public ResourceCollection<Hold> holds;
    public Map<String, String> meta;
    
    public static Account get(String uri) throws HTTPError {
        return new Account((new Client()).get(uri));
    }
    
    public Account() {
        super();
    }
    
    public Account(Map<String, Object> payload) {
        super(payload);
    }

    public Credit credit(
            int amount,
            String description,
            String destination_uri,
            String appears_on_statement_as,
            Map<String, String> meta) throws HTTPError {
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("amount", amount);
        if (description != null)
            payload.put("description", description);
        if (destination_uri != null)
            payload.put("destination", destination_uri);
        if (appears_on_statement_as != null)
            payload.put("appears_on_statement_as", appears_on_statement_as);
        if (meta != null)
            payload.put("meta", meta);
        return credits.create(payload);
    }
    
    public Credit credit(int amount) throws HTTPError {
        return credit(amount, null, null, null, null);
    }
    
    public Debit debit(
            int amount,
            String description,
            String source_uri,
            String appears_on_statement_as,
            Map<String, String> meta) throws HTTPError {
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("amount", amount);
        if (description != null)
            payload.put("description", description);
        if (source_uri != null)
            payload.put("source", source_uri);
        if (appears_on_statement_as != null)
            payload.put("appears_on_statement_as", appears_on_statement_as);
        if (meta != null)
            payload.put("meta", meta);
        return debits.create(payload);
    }
    
    public Debit debit(int amount) throws HTTPError {
        return debit(amount, null, null, null, null);
    }
    
    public Hold hold(
            int amount,
            String description,
            String source_uri,
            Map<String, String> meta) throws HTTPError {
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("amount", amount);
        if (description != null)
            payload.put("description", description);
        if (source_uri != null)
            payload.put("source", source_uri);
        if (meta != null)
            payload.put("meta", meta);
        return holds.create(payload);
    }
    
    public Hold hold(int amount) throws HTTPError {
        return hold(amount, null, null, null);
    }
    
    public void associateBankAccount(String bank_account_uri) throws HTTPError {
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("bank_account_uri", bank_account_uri);
        Map<String, Object> response = client.put(uri, payload);
        deserialize(response);
    }
    
    public void associateCard(String card_uri) throws HTTPError {
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("card_uri", card_uri);
        Map<String, Object> response = client.put(uri, payload);
        deserialize(response);
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("name", name);
        payload.put("email_address", email_address);
        payload.put("meta", meta);
        return payload;
    }

    @Override
    public void deserialize(Map<String, Object> payload) {
        super.deserialize(payload);
        created_at = deserializeDate((String) payload.get("created_at"));
        meta = (Map<String, String>) payload.get("meta");
        name = (String) payload.get("name");
        email_address = (String) payload.get("email_address");
        roles = (String[])(((ArrayList<String>) payload.get("roles")).toArray(new String[0]));
        bank_accounts_uri = (String) payload.get("bank_accounts_uri");
        bank_accounts = new ResourceCollection<BankAccount>(BankAccount.class, bank_accounts_uri);
        cards_uri = (String) payload.get("cards_uri");
        cards = new ResourceCollection<Card>(Card.class, cards_uri);        
        credits_uri = (String) payload.get("credits_uri");
        credits = new ResourceCollection<Credit>(Credit.class, credits_uri);
        debits_uri = (String) payload.get("debits_uri");
        debits = new ResourceCollection<Debit>(Debit.class, debits_uri);
        holds_uri = (String) payload.get("holds_uri");
        holds = new ResourceCollection<Hold>(Hold.class, holds_uri);
    }
}
