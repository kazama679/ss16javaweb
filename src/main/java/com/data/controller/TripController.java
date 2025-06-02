package com.data.controller;

import com.data.model.Trip;
import com.data.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TripController {
    @Autowired
    private TripService tripService;

    @GetMapping("/home")
    public String viewHome(
            @RequestParam(defaultValue = "") String departure,
            @RequestParam(defaultValue = "") String destination,
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        int size = 10;
        List<Trip> trips = tripService.getTrips(departure, destination, page, size);
        int totalPages = tripService.getTotalPages(departure, destination, size);
        model.addAttribute("trips", trips);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("departure", departure);
        model.addAttribute("destination", destination);
        return "home";
    }
}
