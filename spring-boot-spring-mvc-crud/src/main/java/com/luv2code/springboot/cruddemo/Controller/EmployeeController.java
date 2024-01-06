package com.luv2code.springboot.cruddemo.Controller;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    public EmployeeService employeeService;

    public EmployeeController(EmployeeService theEmployeeService){
        employeeService=theEmployeeService;
    }
    //add mapping for "/list"
    @GetMapping("/list")
  public String listEmployees(Model model){
        // get theEmployees from db
        List<Employee> theEmployees = employeeService.findAll();
        //add to the spring model
        model.addAttribute("employees",theEmployees);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        Employee theEmpoyee = new Employee();
        theModel.addAttribute("employee",theEmpoyee);
        return "employees/employee-form";

    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId,Model theModel){
        //get the Employee from the service
        Employee theEmployee = employeeService.findById(theId);
        //set the Employee in the model to prepopulate the form
        theModel.addAttribute("employee",theEmployee);
        //send over to our form
        return "employees/employee-form";
    }




    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
        //Save The Employee
employeeService.save(theEmployee);
//Use a redirect to prevent duplicate submission
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId){
        //delete the employee
        employeeService.deleteById(theId);
        //redirect to /employees/list
return "redirect:/employees/list";
    }
}
