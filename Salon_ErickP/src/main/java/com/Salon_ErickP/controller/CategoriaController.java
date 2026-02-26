package com.Salon_ErickP.controller;

import com.Salon_ErickP.domain.Categoria;
import com.Salon_ErickP.service.CategoriaService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // LISTADO
    @GetMapping("/listado")
    public String listado(Model model) {
        var categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        model.addAttribute("categoria", new Categoria()); // para el modal
        return "/categorias/listado";
    }

    // GUARDAR / ACTUALIZAR
    @PostMapping("/guardar")
    public String guardar(@Valid Categoria categoria,
            Errors errors,
            RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Datos inválidos, revisá el formulario"
            );
            return "redirect:/categorias/listado";
        }

        categoriaService.save(categoria);
        redirectAttributes.addFlashAttribute(
                "todoOk",
                "Categoría guardada correctamente"
        );

        return "redirect:/categorias/listado";
    }

    // ELIMINAR
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idCategoria,
            RedirectAttributes redirectAttributes) {

        try {
            categoriaService.delete(idCategoria);
            redirectAttributes.addFlashAttribute(
                    "todoOk",
                    "Categoría eliminada correctamente"
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "No se pudo eliminar la categoría"
            );
        }

        return "redirect:/categorias/listado";
    }

    // MODIFICAR
    @GetMapping("/modificar/{idCategoria}")
    public String modificar(@PathVariable Integer idCategoria,
            Model model,
            RedirectAttributes redirectAttributes) {

        Optional<Categoria> categoriaOpt = categoriaService.readAllCategorias(idCategoria);

        if (categoriaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "La categoría no existe"
            );
            return "redirect:/categorias/listado";
        }

        model.addAttribute("categoria", categoriaOpt.get());
        return "/categorias/modifica";
    }
}
