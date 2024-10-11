package com.stream.online.payment.configuration;

import com.stream.online.payment.util.ConvertBytesToHex;
import com.stream.online.payment.util.KeyStoreUtil;
import com.stream.online.payment.util.UniqueTransactionIdGenerator;
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
}
