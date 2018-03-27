package net.codejava.spring.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.codejava.spring.dao.StudentDao;
import net.codejava.spring.model.Employee;
import net.codejava.spring.model.Json;
import net.codejava.spring.model.Student;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
	

/**
 * This controller routes accesses to the application to the appropriate
 * hanlder methods. 
 * @author www.codejava.net
 *
 */
@Controller
public class HomeController {

	@Autowired
	private  StudentDao studentDao;
	
	@RequestMapping("/")
	public ModelAndView initializeForm()
	{
		ModelAndView mv = new ModelAndView("uploadFile");
		mv.addObject("json", new Json());
		return mv;
	}
	
	@RequestMapping(value = "/newContact", method = RequestMethod.GET)
	public ModelAndView newContact(ModelAndView model) {
		Student newstudent = new Student();
		model.addObject("contact", newstudent);
		model.setViewName("ContactForm");
		return model;
	}
//	
//	@RequestMapping(value = "/saveContact", method = RequestMethod.POST)
//	public ModelAndView saveContact(@ModelAttribute Student student) {
//		studentDao.saveOrUpdate(student);		
//		return new ModelAndView("redirect:/");
//	}
//	
	@RequestMapping(value = "/deleteContact", method = RequestMethod.GET)
	public ModelAndView deleteContact(HttpServletRequest request) {
		int studentId = Integer.parseInt(request.getParameter("id"));
		studentDao.delete(studentId);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/editContact", method = RequestMethod.GET)
	public ModelAndView editContact(HttpServletRequest request) {
		int contactId = Integer.parseInt(request.getParameter("id"));
		Student student = studentDao.get(contactId);
		ModelAndView model = new ModelAndView("ContactForm");
		model.addObject("student", student);
		
		return model;
	}
	
	
	
	
	/*@RequestMapping(value = "/json", method = RequestMethod.POST)
	public String submitForm(Model model, Json json) throws JSONException {
		if(file.isEmpty())
		{
			redirectAttributes.addAttribute("message", "Please add the file");
			return "redirect:uploadStatus";
		}
		try
		{
			byte[] bytes=file.getBytes();
			Path path = Paths.get(file.getOriginalFilename());
			Files.write(path, bytes);
			redirectAttributes.addAttribute("message", "Uploaded");
			insertJson();
			
			
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		

		model.addAttribute("json", json);

		return "uploadStatus";
		
	}*/
	
	@RequestMapping(value = "/uploadStatus", method = RequestMethod.POST)
	public String uploadStatus()
	{
		return "uploadStatus";
	}
	
	
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public ModelAndView showEmployeeForm() {
		Json json = new Json();

		ModelAndView mv = new ModelAndView("uploadFile");
		mv.addObject("json", json);

		return mv;
	}

	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public String submitForm(Model model, Json json) throws JSONException {

		
		 String val=json.getJson();
		 insertJson(val);
	    return "uploadStatus";

	}
	
	public  void insertJson(String val) throws JSONException
	{
		JSONParser parser = new JSONParser();
		try {
			JSONArray array=(JSONArray) parser.parse(val);
			
			for(Object o : array)
			{
				JSONObject student=(JSONObject) o;
				long id=(Long)student.get("id");
				//System.out.println("id"+id);
				 String name=(String) student.get("name");
				 long mark=(Long) student.get("mark");
				 long age=(Long) student.get("age");
				 Student studentObj= new Student(id,name,age,mark);
				 studentDao.saveOrUpdate(studentObj);
				 
			}
 
			
		}  catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
