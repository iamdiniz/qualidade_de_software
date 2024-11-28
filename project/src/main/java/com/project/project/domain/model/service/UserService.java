package com.project.project.domain.model.service;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.project.project.domain.model.User;

@Service
public class UserService {
    List<User> users = new ArrayList<>();

    public List<User> create(User user) {
        // Valida se o usuario existe
        if (users.contains(user))
            throw new RuntimeException();
        users.add(user);

        // Verificar se o email já está cadastrado
        if (users.stream().anyMatch(u -> u.email().equals(user.email()))) {
            throw new IllegalArgumentException("Email já cadastrado!");
        }

        // Validar a senha
        if (!isPasswordValid(user.password())) {
            throw new IllegalArgumentException("Senha inválida! Deve ter pelo menos 8 caracteres e incluir ao menos um número.");
        }

        return users;
    }

    // Validação de senha
    private boolean isPasswordValid(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*"); // Verifica se tem ao menos um número
    }
    
}
