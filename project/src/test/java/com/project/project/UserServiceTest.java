package com.project.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.project.project.domain.model.User;
import com.project.project.domain.model.service.UserService;

public class UserServiceTest {
    private UserService userService = new UserService();

    @Test
    public void deveRetornarExcecao_QuandoEmailDuplicado() {
        User user1 = new User("Diniz", "diniz@gmail.com", "xazb9182");
        User user2 = new User("OutraPessoa", "diniz@gmail.com", "abcd1234");

        userService.create(user1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user2);
        });

        assertEquals("Email já cadastrado!", exception.getMessage());
    }

    @Test
    public void deveRetornarExcecao_QuandoCriarSenhaFraca() {
        User user = new User("Diniz", "diniz@gmail.com", "short"); // Senha inválida

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user);
        });

        assertEquals("Senha inválida! Deve ter pelo menos 8 caracteres e incluir ao menos um número.", exception.getMessage());
    }

    @Test
    public void deveRetornarUsuarioLogado_QuandoFazerLoginCorretamente() {
        // Cria e adiciona um usuário
        User user = new User("diniz", "diniz@gmail.com", "Senha123");
        userService.create(user);

        // Faz login com as credenciais corretas
        User loggedInUser = userService.login("diniz@gmail.com", "Senha123");

        // Verifica se o usuário retornado é o mesmo
        assertEquals(user, loggedInUser);
    }

    @Test
    public void deveRetornarExcecao_QuandoCredenciaisInvalidas() {
        // Cria e adiciona um usuário
        User user = new User("diniz", "diniz@gmail.com", "Senha123");
        userService.create(user);

        // Tenta login com credenciais inválidas
        try {
            userService.login("diniz@gmail.com", "SenhaErrada");
            fail("Deveria lançar uma exceção para credenciais inválidas!");
        } catch (IllegalArgumentException e) {
            assertEquals("Email ou senha inválidos!", e.getMessage());
        }
    }
}
