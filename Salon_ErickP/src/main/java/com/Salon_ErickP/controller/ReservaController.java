/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Salon_ErickP.controller;

/**
 *
 * @author porto
 */

import com.Salon_ErickP.domain.Reserva;
import com.Salon_ErickP.service.ReservaService;
import com.Salon_ErickP.service.ServicioService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private MessageSource messageSource;

    // LISTADO
    @GetMapping("/listado")
    public String listado(@RequestParam(required = false) LocalDate fecha,
                          Model model) {

        var reservas = (fecha != null)
                ? reservaService.readAllReservasForDate(fecha)
                : reservaService.findAll();

        model.addAttribute("reservas", reservas);
        model.addAttribute("servicios", servicioService.findAll());
        model.addAttribute("totalReservas", reservas.size());
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("fecha", fecha);

        return "/reservas/listado";
    }

    // GUARDAR / ACTUALIZAR
    @PostMapping("/guardar")
    public String guardar(@Valid Reserva reserva,
                          Errors errors,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            model.addAttribute("reservas", reservaService.findAll());
            model.addAttribute("servicios", servicioService.findAll());
            return "/reservas/listado";
        }

        reservaService.save(reserva);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));

        return "redirect:/reservas/listado";
    }

    // ELIMINAR
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("idReserva") Integer idReserva,
                           RedirectAttributes redirectAttributes) {

        try {
            reservaService.delete(idReserva);
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage("mensaje.eliminado", null, Locale.getDefault()));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("reserva.error", null, Locale.getDefault()));
        }

        return "redirect:/reservas/listado";
    }

    // MODIFICAR
    @GetMapping("/modificar/{idReserva}")
    public String modificar(@PathVariable Integer idReserva,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        Optional<Reserva> reservaOpt = reservaService.readAllReservas(idReserva);

        if (reservaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("reserva.error", null, Locale.getDefault()));
            return "redirect:/reservas/listado";
        }

        model.addAttribute("reserva", reservaOpt.get());
        model.addAttribute("servicios", servicioService.findAll());
        return "/reservas/modifica";
    }
}
