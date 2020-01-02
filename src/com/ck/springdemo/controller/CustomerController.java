package com.ck.springdemo.controller;

import com.ck.springdemo.entity.Customer;
import com.ck.springdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // Inject service layer
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model model) {
        // get customers from dao
        List<Customer> customers = customerService.getCustomers();
        // add the customers to the model
        model.addAttribute("customers", customers);
        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {

        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {

        customerService.saveCustomer(customer);
        return "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int id, Model model) {
        // get customer from service
        Customer customer = customerService.getCustomer(id);
        // set customer to model attribute to pre populate the form
        model.addAttribute("customer", customer);
        // send over to our form
        return "customer-form";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customerId") int id) {
        // delete the customer
        customerService.deleteCustomer(id);
        return "redirect:/customer/list";
    }

    @GetMapping("/search")
    public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model model) {
        List<Customer> customers = customerService.searchCustomers(theSearchName);
        // add the customers to the model
        model.addAttribute("customers", customers);
        return "list-customers";
    }
}
