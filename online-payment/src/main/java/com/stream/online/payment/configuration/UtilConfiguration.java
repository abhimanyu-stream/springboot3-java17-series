package com.stream.online.payment.configuration;

import com.stream.online.payment.util.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfiguration {

    @Bean
    public UniqueTransactionIdGenerator getUniqueTransactionIdGenerator(){
        return new UniqueTransactionIdGenerator();
    }
    @Bean
    public ConvertBytesToHex getConvertBytesToHex(){
        return new ConvertBytesToHex();
    }
    @Bean
    public KeyStoreUtil getKeyStoreUtil(){
        return new KeyStoreUtil();
    }
    @Bean
    public UniqueOrderNumber getUniqueOrderNumber(){
        return new UniqueOrderNumber();
    }
    @Bean
    public IFSCValidator getIFSCValidator(){
        return new IFSCValidator();
    }
    @Bean
    public SecureUpiMaskerAndValidator getSecureUpiMasker(){
        return new SecureUpiMaskerAndValidator();
    }
    @Bean
    public CreditCardValidatorAndMasker getCreditCardValidatorAndMasker(){
        return new CreditCardValidatorAndMasker();
    }
    @Bean
    public DebitCardValidatorAndMasker getDebitCardValidatorAndMasker(){
        return new DebitCardValidatorAndMasker();
    }
    @Bean
    public PayPalIdValidatorAndMasker getPayPalIdValidatorAndMasker(){
        return new PayPalIdValidatorAndMasker();

    }

}
