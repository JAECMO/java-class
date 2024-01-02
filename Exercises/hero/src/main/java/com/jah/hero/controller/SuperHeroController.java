/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.controller;

import com.jah.hero.dao.HeroDao;
import com.jah.hero.dao.LocationDao;
import com.jah.hero.dao.SuperPowerDao;
import com.jah.hero.dto.Hero;
import com.jah.hero.dto.Location;
import com.jah.hero.dto.SuperPower;
import com.jah.hero.service.HeroService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author drjal
 */
@Controller
@ControllerAdvice
public class SuperHeroController {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileUploadError(Model model, HttpServletRequest request) {
        List<Hero> heroesByLocation = heroService.menuSelectionAndHeroList(model, request);
        List<SuperPower> superPowers = superPowerDao.getAllSuperPower();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("heroesByLocation", heroesByLocation);
        model.addAttribute("superPowers", superPowers);
        model.addAttribute("success", "Hero/Villain was NOT added.");
        model.addAttribute("fileErrorSize", " File too big.Please select an image smaller than 5MB");
        return "superHero";

    }

    @Autowired
    HeroDao heroDao;

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    HeroService heroService;

    Set<ConstraintViolation<Hero>> violations = new HashSet<>();

    @GetMapping("superHero")
    public String displaySuperPower(Model model, HttpServletRequest request) {
        List<Hero> heroesByLocation = heroService.menuSelectionAndHeroList(model, request);
        List<Location> locations = locationDao.getAllLocations();
        List<SuperPower> superPowers = superPowerDao.getAllSuperPower();

        model.addAttribute("locations", locations);
        model.addAttribute("heroesByLocation", heroesByLocation);
        model.addAttribute("superPowers", superPowers);

        if (!violations.isEmpty()) {
            violations.clear();
        }
        model.addAttribute("errors", violations);
        return "superHero";
    }

    @PostMapping("superHero")
    public String displaySuperHeroByLocation(Model model, HttpServletRequest request) {
        List<Hero> heroesByLocation = heroService.menuSelectionAndHeroList(model, request);

        List<Location> locations = locationDao.getAllLocations();
        List<SuperPower> superPowers = superPowerDao.getAllSuperPower();
        model.addAttribute("locations", locations);
        model.addAttribute("heroesByLocation", heroesByLocation);
        model.addAttribute("superPowers", superPowers);

        model.addAttribute("errors", violations);
        return "superHero";
    }

    @PostMapping("addSuperHero")
    public String addSuperHero(@RequestParam("file") MultipartFile file, Hero hero, Model model, HttpServletRequest request) throws IOException {
        List<Hero> heroesByLocation = heroService.menuSelectionAndHeroList(model, request);

        String uploadDir = "src\\main\\resources\\static\\images";
        String IMAGE_EXTENSION = ".jpg";

        //List<Hero> heroes = heroDao.getAllHeroes();
        hero = heroService.heroCreation(hero, request);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);

        if (violations.isEmpty() && isImageFile(file)) {

            heroDao.addHero(hero);

            String fileName = hero.getHeroId() + "";
            try {
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try (InputStream inputStream = file.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName + IMAGE_EXTENSION);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException ioe) {
                    throw new IOException("Could not save image file: " + fileName, ioe);

                }
            } catch (IOException ex) {
                model.addAttribute("fileError", "File could not be saved");
            }

            return "redirect:/superHero";
        } else {
            List<SuperPower> superPowers = superPowerDao.getAllSuperPower();
            List<Location> locations = locationDao.getAllLocations();
            model.addAttribute("locations", locations);
            model.addAttribute("heroesByLocation", heroesByLocation);
            model.addAttribute("superPowers", superPowers);

            if (!file.isEmpty()) {
                if (!isImageFile(file)) {
                    model.addAttribute("fileError", "Invalid file type.");
                }
            } else {
                model.addAttribute("fileError", "Please Select an image.");
            }
            model.addAttribute("errors", violations);

            return "superHero";
        }

    }

    @GetMapping("superHeroDetail")
    public String superHeroDetail(Integer id, Model model) {
        Hero hero = heroDao.getHeroById(id);
        model.addAttribute("hero", hero);
        return "superHeroDetail";
    }

    @GetMapping("deleteSuperHero")
    public String deleteSuperHero(Integer id) {
        heroDao.deleteHero(id);
        String image = id + ".jpg";
        String absolutePath = "src\\main\\resources\\static\\images" + "\\" + image;
        File imageFile = new File(absolutePath);
        imageFile.delete();
        return "redirect:/superHero";
    }

    @GetMapping("editSuperHero")
    public String editSuperHero(Integer id, Model model) {
        Hero hero = heroDao.getHeroById(id);
        try {
            Integer superPowerIdInt = hero.getSuperPower().getSuperPowerId();
            model.addAttribute("selectedSuperPowerId", superPowerIdInt);
        } catch (Exception e) {
        }
        List<SuperPower> superPowers = superPowerDao.getAllSuperPower();

        model.addAttribute("hero", hero);
        model.addAttribute("superPowers", superPowers);

        if (!violations.isEmpty()) {
            violations.clear();
        }
        model.addAttribute("errors", violations);

        return "editSuperHero";
    }

    @PostMapping("editSuperHero")
    public String performEditSuperHero(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroById(id);

        hero = heroService.heroCreation(hero, request);
        try {
            Integer superPowerIdInt = hero.getSuperPower().getSuperPowerId();
            model.addAttribute("selectedSuperPowerId", superPowerIdInt);
        } catch (Exception e) {
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);

        if (violations.isEmpty()) {
            heroDao.updateHero(hero);
            return "redirect:/superHero";
        } else {
            List<SuperPower> superPowers = superPowerDao.getAllSuperPower();
            model.addAttribute("superPowers", superPowers);
            model.addAttribute("hero", hero);
            model.addAttribute("errors", violations);
            return "editSuperHero";

        }
    }

    private boolean isImageFile(MultipartFile file) {
        // Check if the file is not empty
        if (file != null && !file.isEmpty()) {
            // Get the file name
            String fileName = file.getOriginalFilename();

            // Get the file extension
            String fileExtension = null;
            if (fileName != null && fileName.lastIndexOf('.') != -1) {
                fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            }

            // List of allowed image file extensions
            List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");

            // Check if the file extension is in the list of allowed extensions
            return fileExtension != null && allowedExtensions.contains(fileExtension);
        }

        // If the file is empty or null, it's not a valid image file
        return false;
    }

}
