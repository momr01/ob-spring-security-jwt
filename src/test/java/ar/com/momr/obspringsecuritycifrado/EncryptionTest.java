package ar.com.momr.obspringsecuritycifrado;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class EncryptionTest {

	/**
	 * BCrypt que genera su propio salt de 16 bytes
	 * 
	 * El resultado de cifrar con bcrypt será un string de 60 caracteres:
	 * 
	 * $a versión $10 fuerza, se puede modificar (valor que va de 4 a 31, por
	 * defecto vale 10) Los 22 siguientes caracteres son el salt generado
	 * 
	 */
	@Test
	void bcryptTest() {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode("admin");
		System.out.println(hashedPassword);

		boolean matches = passwordEncoder.matches("admin", hashedPassword);
		System.out.println(matches);
	}

	@Test
	void bcryptCheckMultiplePasswords() {
		for (int i = 0; i < 30; i++) {
			System.out.println(new BCryptPasswordEncoder().encode("admin"));
		}
	}
	
	
	@Test
	void pdkdf2() {
		Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
		for (int i = 0; i < 30; i++) {
			System.out.println(passwordEncoder.encode("admin"));
		}
		
	}
	
//	@Test
//	void argon() {
//		Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
//		for (int i = 0; i < 30; i++) {
//			System.out.println(passwordEncoder.encode("admin"));
//		}
//	}
	
	@Test
	void scrypt() {
		SCryptPasswordEncoder passwordEncoder = new SCryptPasswordEncoder();
		for (int i = 0; i < 30; i++) {
			System.out.println(passwordEncoder.encode("admin"));
		}
	}
	
	@Test
	void springPasswordEncoders() {
		
		String idForEncode = "bcrypt";
		
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(idForEncode, new BCryptPasswordEncoder());
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		encoders.put("argon2", new Argon2PasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());
		
		//no seguro=
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		encoders.put("sha256", new StandardPasswordEncoder());
		
		PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
	
		String hashedPassword = passwordEncoder.encode("admin");
		System.out.println(hashedPassword);
	}

}
