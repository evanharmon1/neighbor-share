package org.evanharmon.neighborshare.controllers;

import org.evanharmon.neighborshare.models.Category;
import org.evanharmon.neighborshare.models.Item;
import org.evanharmon.neighborshare.models.User;
import org.evanharmon.neighborshare.models.repository.CategoryRepository;
import org.evanharmon.neighborshare.models.repository.ItemRepository;
import org.evanharmon.neighborshare.models.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("view")
public class ViewController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {

        User currentUser = User.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("title", "NeighborShare");
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());

        return "view/index";
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public String byCategory(@PathVariable int id, Model model) {

        Optional<Category> cat = categoryRepository.findById(id);
        Category category = cat.get();

        List<Item> allItems = itemRepository.findAll();
        ArrayList<Item> catItems = new ArrayList<>();
        for (Item item: allItems) {
            if (item.getCategory() == category) {
                catItems.add(item);
            }
        }

        User currentUser = User.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("title", "NeighborShare");
        model.addAttribute("items", catItems);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "view/by-category";

    }

    @RequestMapping(value = "/owner/{id}", method = RequestMethod.GET)
    public String byOwner(@PathVariable int id, Model model) {

        Optional<User> user = userRepository.findById(id);
        User owner = user.get();

        List<Item> allItems = itemRepository.findAll();
        ArrayList<Item> ownerItems = new ArrayList<>();
        for (Item item: allItems) {
            if (item.getUser() == owner) {
                ownerItems.add(item);
            }
        }

        User currentUser = User.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("title", "NeighborShare");
        model.addAttribute("items", ownerItems);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "view/by-owner";

    }


}