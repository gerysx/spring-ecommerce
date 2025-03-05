package com.curso.ecommerce.spring_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.spring_ecommerce.model.Usuario;

import java.util.Optional;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional <Usuario> findByEmail(String email);

}
