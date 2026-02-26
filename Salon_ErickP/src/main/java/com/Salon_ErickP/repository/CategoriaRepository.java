/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Salon_ErickP.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Salon_ErickP.domain.Categoria;

/**
 *
 * @author porto
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
