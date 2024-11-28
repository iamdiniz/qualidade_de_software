package com.project.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.project.project.domain.model.User;
import com.project.project.domain.model.service.UserService;

public class UserServiceTest {
    private UserService userService = new UserService();

    @Test
    public void criando_Usuario_Com_Email_Duplicado_Deve_Retornar_Excecao() {
        User user1 = new User("Diniz", "diniz@gmail.com", "xazb9182");
        User user2 = new User("OutraPessoa", "diniz@gmail.com", "abcd1234");

        userService.create(user1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user2);
        });

        assertEquals("Email já cadastrado!", exception.getMessage());
    }

    @Test
    public void criando_Usuario_Com_Senha_Invalida_Deve_Lancar_Exception() {
        User user = new User("Diniz", "diniz@gmail.com", "short"); // Senha inválida

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user);
        });

        assertEquals("Senha inválida! Deve ter pelo menos 8 caracteres e incluir ao menos um número.", exception.getMessage());
    }
}
