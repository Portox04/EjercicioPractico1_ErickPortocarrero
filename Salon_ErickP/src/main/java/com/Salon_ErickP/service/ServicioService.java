/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Salon_ErickP.service;

import com.Salon_ErickP.domain.Servicio;
import com.Salon_ErickP.repository.ServicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author porto
 */
@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    //leer
    @Transactional(readOnly = true)
    public Optional<Servicio> readAllServicios(Integer id) {
        return servicioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    //Guardar
    @Transactional
    public void save(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    //Borrar
    @Transactional
    public void delete(Integer idServicio) {
        if (!servicioRepository.existsById(idServicio)) {
            throw new IllegalArgumentException("No existe la servicio con el ID: " + idServicio);
        }
        try {
            servicioRepository.deleteById(idServicio);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("No se puede eliminar la servicio aun tiene datos", e);
        }
    }

}
