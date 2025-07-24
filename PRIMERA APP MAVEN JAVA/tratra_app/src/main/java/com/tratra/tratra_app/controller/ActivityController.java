package com.tratra.tratra_app.controller;

import com.tratra.tratra_app.entity.Activity;
import com.tratra.tratra_app.entity.User;
import com.tratra.tratra_app.repository.UserRepository;
import com.tratra.tratra_app.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public String showAddForm() {
        return "add-activity";
    }

    @PostMapping("/add")
    public String addActivity(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam String date,
                              @RequestParam String activityType,
                              @RequestParam("gpxFile") MultipartFile gpxFile,
                              Principal principal) {

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        activityService.saveActivity(name, description, date, activityType, gpxFile, user);

        return "redirect:/activities/list";
    }

    @GetMapping("/list")
    public String listActivities(Model model, Principal principal) {
        String username = principal.getName();
        List<Activity> activities = activityService.findByUsername(username);
        model.addAttribute("activities", activities);
        return "list-activities";
    }

    @GetMapping("/view-gpx/{id}")
    public String viewGpxMap(@PathVariable Long id, Model model) {
        Activity activity = activityService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada con id: " + id));

        // Obtener coordenadas parseadas del archivo GPX
        List<double[]> coordinates = activityService.parseGpxFileToCoordinatesWithElevation(activity.getGpxFilePath());

        model.addAttribute("coordinates", coordinates);
        model.addAttribute("trackName", activity.getName());

        return "gpx-viewer";
    }
}
