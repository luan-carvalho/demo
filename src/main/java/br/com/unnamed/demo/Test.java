package br.com.unnamed.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Test {

    public static void main(String[] args) {
        
        String p = "1234";
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String pf = pe.encode(p);       
        System.out.println(pf); 

    }
    
}
