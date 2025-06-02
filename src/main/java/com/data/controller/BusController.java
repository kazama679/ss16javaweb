package com.data.controller;

import com.data.dto.BusDTO;
import com.data.model.User;
import com.data.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/bus")
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping
    public String showList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        if (user == null || !user.getRole().equals("ADMIN")) {
            return "redirect:/login";
        }

        model.addAttribute("list", busService.getAll());
        return "bus/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("bus", new BusDTO());
        return "bus/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("bus") BusDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return "bus/add";
        }
        busService.save(dto);
        return "redirect:/admin/bus";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        BusDTO dto = busService.findById(id);
        model.addAttribute("bus", dto);
        return "bus/edit";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("bus") BusDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return "bus/edit";
        }
        busService.update(dto);
        return "redirect:/admin/bus";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        busService.delete(id);
        return "redirect:/admin/bus";
    }
}