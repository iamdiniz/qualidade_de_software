package com.project.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.project.project.domain.model.User;
import com.project.project.domain.model.service.UserService;

public class UserServiceTest {
    private UserService userService = new UserService();

    @Test
    public void deveRetornarUsuario_QuandoCriadoComSucesso() {
        User user = new User("Diniz", "diniz@gmail.com", "xazb9182");

        List<User> users = userService.create(user);

        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    public void deveLancarExcecao_QuandoEmailDuplicado() {
        User user1 = new User("Diniz", "diniz@gmail.com", "xazb9182");
        User user2 = new User("OutraPessoa", "diniz@gmail.com", "abcd1234");

        userService.create(user1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user2);
        });

        assertEquals("Email já cadastrado!", exception.getMessage());
    }

    @Test
    public void deveLancarExcecao_QuandoCriarSenhaFraca() {
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
    public void deveLancarExcecao_QuandoCredenciaisInvalidas() {
        // Cria e adiciona um usuário
        User user = new User("diniz", "diniz@gmail.com", "Senha123");
        userService.create(user);

        // Tenta login com credenciais inválidas
        try {
            userService.login("diniz@gmail.com", "SenhaErrada");
        } catch (IllegalArgumentException e) {
            assertEquals("Email ou senha inválidos!", e.getMessage());
        }
    }

    @Test
    public void deveLancarExcecao_QuandoCriarUsuarioComSenhaCurta() {
        User user = new User("diniz", "diniz@gmail.com", "12345"); // Senha curta

        try {
            userService.create(user);
        } catch (IllegalArgumentException e) {
            assertEquals("Senha inválida! Deve ter pelo menos 8 caracteres e incluir ao menos um número.", e.getMessage());
        }
    }

    @Test
    public void deveLancarExcecao_QuandoLogarComEmailNaoCadastrado() {
        // Tenta login com email que não foi cadastrado
        try {
            userService.login("nao_existe@gmail.com", "Senha123");
        } catch (IllegalArgumentException e) {
            assertEquals("Email ou senha inválidos!", e.getMessage());
        }
    }

    @Test
    public void deveLancarExcecao_QuandoCriarSenhaSemNumero() {
        User user = new User("diniz", "diniz@gmail.com", "SemNumero");

        try {
            userService.create(user);
        } catch (IllegalArgumentException e) {
            assertEquals("Senha inválida! Deve ter pelo menos 8 caracteres e incluir ao menos um número.", e.getMessage());
        }
    }

    @Test
    public void deverRetornarSucesso_QuandoCriadoMultiplosUsuarios() {
        User user1 = new User("diniz", "diniz@gmail.com", "Senha123");
        User user2 = new User("cabral", "cabral@gmail.com", "Senha456");
        User user3 = new User("guilherme", "guilherme@gmail.com", "Senha789");

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);

        List<User> users = userService.create(new User("nova", "nova@gmail.com", "Senha000"));

        assertEquals(4, users.size());
        assertEquals(user1, users.get(0));
        assertEquals(user2, users.get(1));
        assertEquals(user3, users.get(2));
    }

    @Test
    public void deveLancarExcecao_QuandoCriadoEmailIgualComMaiuscula() {
        User user1 = new User("diniz", "diniz@gmail.com", "Senha123");
        userService.create(user1);

        User user2 = new User("outro", "DINIZ@gmail.com", "Senha456");

        try {
            userService.create(user2);
        } catch (IllegalArgumentException e) {
            assertEquals("Email já cadastrado!", e.getMessage());
        }
    }
}
