/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Salon_ErickP.controller;

/**
 *
 * @author porto
 */
import com.Salon_ErickP.domain.Servicio;
import com.Salon_ErickP.service.ServicioService;
import com.Salon_ErickP.service.CategoriaService;
import jakarta.validation.Valid;
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
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MessageSource messageSource;

    // LISTADO
    @GetMapping("/listado")
    public String listado(Model model) {
        var servicios = servicioService.findAll();
        model.addAttribute("servicios", servicios);
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("totalServicios", servicios.size());
        model.addAttribute("servicio", new Servicio());
        return "/servicios/listado";
    }

    // GUARDAR / ACTUALIZAR
    @PostMapping("/guardar")
    public String guardar(@Valid Servicio servicio,
                          Errors errors,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            model.addAttribute("servicios", servicioService.findAll());
            model.addAttribute("categorias", categoriaService.findAll());
            return "/servicios/listado";
        }

        servicioService.save(servicio);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));

        return "redirect:/servicios/listado";
    }

    // ELIMINAR
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("idServicio") Integer idServicio,
                           RedirectAttributes redirectAttributes) {

        try {
            servicioService.delete(idServicio);
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage("mensaje.eliminado", null, Locale.getDefault()));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("servicio.error", null, Locale.getDefault()));
        }

        return "redirect:/servicios/listado";
    }

    // MODIFICAR
    @GetMapping("/modificar/{idServicio}")
    public String modificar(@PathVariable Integer idServicio,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        Optional<Servicio> servicioOpt = servicioService.readAllServicios(idServicio);

        if (servicioOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("servicio.error", null, Locale.getDefault()));
            return "redirect:/servicios/listado";
        }

        model.addAttribute("servicio", servicioOpt.get());
        model.addAttribute("categorias", categoriaService.findAll());
        return "/servicios/modifica";
    }
}
