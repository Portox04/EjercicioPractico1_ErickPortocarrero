/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Salon_ErickP.service;

import com.Salon_ErickP.domain.Categoria;
import com.Salon_ErickP.domain.Reserva;
import com.Salon_ErickP.repository.ReservaRepository;
import java.time.LocalDate;
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
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    //leer
    @Transactional(readOnly = true)
    public Optional<Reserva> readAllReservas(Integer id) {
        return reservaRepository.findById(id);
    }

    //leer por fecha
    public List<Reserva> readAllReservasForDate(LocalDate fecha) {
        return reservaRepository.findByFecha(fecha);
    }

    @Transactional(readOnly = true)
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    //Guardar
    @Transactional
    public void save(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    //Borrar
    @Transactional
    public void delete(Integer idReserva) {
        if (!reservaRepository.existsById(idReserva)) {
            throw new IllegalArgumentException("No existe la reserva con el ID: " + idReserva);
        }
        try {
            reservaRepository.deleteById(idReserva);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("No se puede eliminar la reserva aun tiene datos", e);
        }
    }

}
