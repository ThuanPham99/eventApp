package com.assignment.event;

import com.assignment.event.entity.*;
import com.assignment.event.repository.EventRepository;
import com.assignment.event.repository.GuestInEventRepository;

import com.assignment.event.service.*;
import com.assignment.event.utils.Utils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class WebController {
    private Utils utils = Utils.getInstance();
    private SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private EventService eventService;
    @Autowired
    private GuestInEventService guestInEventService;
    @Autowired
    private GuestInEventRepository guestInEventRepository;

    @Autowired
    private GuestService guestService;

    @Autowired
    private QuestionService questionService;
    @GetMapping(value = {"/","/login"})
    public String loginPage(Model model)
    {
        model.addAttribute("account",new Manager());
        return "login";
    }
    @PostMapping("/login")
    public String checkLogin(Model model, @ModelAttribute Manager formlogin, HttpServletRequest request){
        String user = formlogin.getUserID();
        String password = formlogin.getPassword();
        Manager manager = managerService.checkLogin(user,password);
        if(manager != null){
            if(manager.getRole() == 1){
                HttpSession session = request.getSession();
                session.setAttribute("ACC",manager);
                List<Event> eventList = eventService.getListEvent();
                model.addAttribute("LIST_EVENT",eventList);
                return "home";
            }
            model.addAttribute("ERROR_LOGIN","Your account can't access this page");
            return "login";

        }
        model.addAttribute("ERROR_LOGIN","Your UserID or Password is wrong");
        model.addAttribute("account",new Manager());
        return "login";
    }
    @PostMapping("/removeEvent")
    public String removeEvent(Model model, @RequestParam(value = "eventId") Integer id){
        Event event = eventRepository.findByID(id);
        if(event !=null){
            event.setStatus(false);
            eventRepository.save(event);
            model.addAttribute("NOTIFI","Remove success");

        }else{
            model.addAttribute("NOTIFI","event id dont exist");
        }
        List<Event> eventList = eventService.getListEvent();
        model.addAttribute("LIST_EVENT",eventList);
        return "home";
    }
    @GetMapping("/home")
    public String homePage(Model model,HttpServletRequest request){
        HttpSession session =request.getSession();
        Manager manager = (Manager) session.getAttribute("ACC");
        if(manager == null){
            model.addAttribute("account",new Manager());
            return "login";
        }
        List<Event> eventList = eventService.getListEvent();
        model.addAttribute("LIST_EVENT",eventList);
        return "home";
    }
    @PostMapping("/detailEvent")
    public String viewDetail(Model model,@RequestParam(value = "eventId") Integer id){
        Event event = eventRepository.findByID(id);
        model.addAttribute("EVENT",event);
        return "editEvent";
    }
    @PostMapping("/updateEvent")
    public String updateEvent(Model model,@RequestParam("name") String name,
                              @RequestParam("description") String des
                              ,@RequestParam("location") String location
                              ,@RequestParam("timeStart") String time
                              ,@RequestParam("id") Integer  id
                              ,HttpServletRequest request) throws ParseException {
        HttpSession session =request.getSession();
        Manager manager = (Manager) session.getAttribute("ACC");
        if(manager == null){
            model.addAttribute("account",new Manager());
            return "login";
        }
        Date date = formatter.parse(time);
        Event event = eventRepository.findByID(id);
        event.setName(name);
        event.setDescription(des);
        event.setLocation(location);
        event.setTimeStart(date);
        eventRepository.save(event);
        model.addAttribute("NOTIFI","Update event " + event.getID() + " success");
        List<Event> eventList = eventService.getListEvent();
        model.addAttribute("LIST_EVENT",eventList);
        return "home";
    }
    @PostMapping("/viewGuests")
    public String viewGuestsInEvent(Model model,@RequestParam("eventID") Integer id,HttpServletRequest request){
        HttpSession session =request.getSession();
        Manager manager = (Manager) session.getAttribute("ACC");
        if(manager == null){
            model.addAttribute("account",new Manager());
            return "login";
        }
        List<Guest> guests = guestInEventService.getListGuestByEventID(id);
        model.addAttribute("GUESTS",guests);
        model.addAttribute("eventID",id);
        return "guestInEvent";
    }
    @GetMapping("/logout")
    public String logout(Model model,HttpServletRequest request){
        HttpSession session =request.getSession();
        if(session != null){
            session.invalidate();
        }
        model.addAttribute("account",new Manager());
        return "login";
    }
    @GetMapping("/guests")
    public String guestsPage(Model model,HttpServletRequest request){
        HttpSession session =request.getSession();
        Manager manager = (Manager) session.getAttribute("ACC");
        if(manager == null){
            model.addAttribute("account",new Manager());
            return "login";
        }
        List<Guest> guests = guestService.getListGuset();
        model.addAttribute("GUESTS",guests);
        return "guestPage";
    }
    @PostMapping("/removeGuest")
    public String removeGuest(Model model,@RequestParam("guestId") Integer id){
        guestService.deleteGuestByID(id);
        List<Guest> guests = guestService.getListGuset();
        model.addAttribute("GUESTS",guests);
        model.addAttribute("NOTIFI","Gust ID : " + id +" removed");
        return "guestPage";
    }
    @Transactional
    @PostMapping("/removeGuestFromEvent")
    public String removeGuestFromEvent(Model model,@RequestParam("eventID") Integer eventId
                                                  ,@RequestParam("guestId") Integer guestId){
        guestInEventRepository.deleteByEventIDAndGuestID(eventId,guestId);
        List<Guest> guests = guestInEventService.getListGuestByEventID(eventId);
        model.addAttribute("GUESTS",guests);
        model.addAttribute("eventID",eventId);
        model.addAttribute("NOTIFI","GuestID :" + guestId +" removed from event ID" + eventId);
        return "guestInEvent";
    }
    @PostMapping("/detailGuest")
    public String updateGuestPage(Model model,@RequestParam("guestId")Integer guestID){
        Guest guest = guestService.findByID(guestID);
        model.addAttribute("GUEST",guest);
        return "editGuest";
    }
    @PostMapping("/updateGuest")
    public String updateGuest(Model model,@RequestParam("name")String name
                                         ,@RequestParam("id")Integer id
                                         ,@RequestParam("phone")String phone
                                         ,@RequestParam("mail")String mail){
        guestService.updateGuest(id,name,phone,mail);
        List<Guest> guests = guestService.getListGuset();
        model.addAttribute("GUESTS",guests);
        model.addAttribute("NOTIFI","Gust ID : " + id +" update success !!");
        return "guestPage";
    }
    @GetMapping("/addNewEventPage")
    public String addNewEventPage(Model model,HttpServletRequest request){
        HttpSession session =request.getSession();
        Manager manager = (Manager) session.getAttribute("ACC");
        if(manager == null){
            model.addAttribute("account",new Manager());
            return "login";
        }
        model.addAttribute("EVENT",new Event());
        return "addNewEvent";
    }
    @PostMapping("/addNewEvent")
    public String addNewEvent(Model model,@ModelAttribute Event event,@RequestParam("time") String time,
                              HttpServletRequest request) throws ParseException {

        HttpSession session =request.getSession();
        Manager manager = (Manager) session.getAttribute("ACC");
        Date dateTime = formatter.parse(time);
        event.setTimeStart(dateTime);
        event.setAdminID(manager.getUserID());
        event.setStatus(true);
        if(managerService.checkExist(event.getStaffID())){
            try{
                eventService.add(event);
                model.addAttribute("NOTIFI","Add success !!");
            }catch (Exception var7){
                var7.printStackTrace();
                model.addAttribute("NOTIFI","Add Fail");

            }
            List<Event> eventList = eventService.getListEvent();
            model.addAttribute("LIST_EVENT",eventList);
            return "home";
        }
        model.addAttribute("NOTIFI","staffID don't exist");
        model.addAttribute("EVENT",new Event());
        return "addNewEvent";
    }
    @PostMapping("/addNewGuestToEvent")
    public String addNewGuestToEvent(Model model,@RequestParam("eventID")Integer eventId,HttpServletRequest request){
        HttpSession session =request.getSession();
        Manager manager = (Manager) session.getAttribute("ACC");
        if(manager == null){
            model.addAttribute("account",new Manager());
            return "login";
        }
        List<Guest> guests = guestInEventService.getList(eventId);
        model.addAttribute("EVENTID",eventId);
        model.addAttribute("GUESTS",guests);
        return "addGuestToEvent";
    }
    @PostMapping("/addToEvent")
    public String addToEvent(Model model,@RequestParam("eventId")Integer eventId,@RequestParam("guestId")Integer guestId){
        try{
            Guest guest = guestService.findByID(guestId);
            Event event = eventRepository.findByID(eventId);
            guestInEventService.add(new GuestInEvent(eventId,guestId,"INVITED"));
            utils.sendMail(guest,event);
            model.addAttribute("NOTIFI","Add success");
        }catch (Exception vr7){
            vr7.printStackTrace();
            model.addAttribute("NOTIFI","Add fail");
        }finally {
            List<Guest> guests = guestInEventService.getList(eventId);
            model.addAttribute("EVENTID",eventId);
            model.addAttribute("GUESTS",guests);
            return "addGuestToEvent";
        }
    }
    @PostMapping("/getListQuestion")
    public String getListQuestion(Model model,@RequestParam("eventID")Integer eventId,HttpServletRequest request){
        HttpSession session =request.getSession();
        Manager manager = (Manager) session.getAttribute("ACC");
        if(manager == null){
            model.addAttribute("account",new Manager());
            return "login";
        }
        List<Question> questions = questionService.getQuestions(eventId);
        model.addAttribute("QUESTIONS",questions);
        model.addAttribute("EVENTID",eventId);
        return "questions";
    }
    @PostMapping("/deleteQuestion")
    public String deleteQuestion(Model model,@RequestParam("questionId")Integer questionID,@RequestParam("eventID")Integer eventId){
        Question question = questionService.getQuestionByID(questionID);
        question.setStatus("DELETE");
        question.setAnswer("");
        if(questionService.add(question)){
            model.addAttribute("NOTIFI","Delete success");
        }else {
            model.addAttribute("NOTIFI","Delete fail");
        }
        List<Question> questions = questionService.getQuestions(eventId);
        model.addAttribute("QUESTIONS",questions);
        model.addAttribute("EVENTID",eventId);
        return "questions";
    }
    @PostMapping("/activeQuestion")
    public String activeQuestion(Model model,@RequestParam("questionId")Integer questionID,@RequestParam("eventID")Integer eventId){
        Question question = questionService.getQuestionByID(questionID);
        question.setStatus("Active");
        if(questionService.add(question)){
            model.addAttribute("NOTIFI","Active success");
        }else {
            model.addAttribute("NOTIFI","Active fail");
        }
        List<Question> questions = questionService.getQuestions(eventId);
        model.addAttribute("QUESTIONS",questions);
        return "questions";
    }
    @GetMapping("/getImpotPage")
    public String getImpotPage(Model model,HttpServletRequest request){
        HttpSession session =request.getSession();
        Manager manager = (Manager) session.getAttribute("ACC");
        if(manager == null){
            model.addAttribute("account",new Manager());
            return "login";
        }
        MyUploadForm myUploadForm = new MyUploadForm();
        model.addAttribute("file", myUploadForm);
        return "importPage";
    }
    @PostMapping({"/fileinfo"})
    public String fileinfo(@ModelAttribute("guest") MyUploadForm myUploadForm, Model model, HttpServletRequest request) {
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        model.addAttribute("fileinfo", uploadRootPath);
        File uploadRootDir = new File(uploadRootPath);
        String nameFile = null;
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }

        MultipartFile[] fileDatas = myUploadForm.getFileDatas();
        List<File> uploadedFiles = new ArrayList();
        MultipartFile[] var9 = fileDatas;
        int var10 = fileDatas.length;

        for(int var11 = 0; var11 < var10; ++var11) {
            MultipartFile fileData = var9[var11];
            nameFile = fileData.getOriginalFilename();
            System.out.println("Client File Name = " + nameFile);
            if (nameFile != null && nameFile.length() > 0) {
                try {
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + nameFile);
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    uploadedFiles.add(serverFile);
                    System.out.println("Write file: " + serverFile);
                } catch (Exception var23) {
                    var23.printStackTrace();
                    System.out.println("Error Write file: " + nameFile);
                }
            }
        }

        List<Guest> list = new ArrayList();
        String url = uploadRootPath + "\\" + nameFile;

        try {
            FileInputStream excelFile = new FileInputStream(new File(url));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = sheet.iterator();
            Row var16 = (Row)iterator.next();

            while(iterator.hasNext()) {
                Row currentRow = (Row)iterator.next();
                String name = currentRow.getCell(0).getStringCellValue();
                String phone = currentRow.getCell(1).getStringCellValue();
                String mail = currentRow.getCell(2).getStringCellValue();
                int role = Integer.parseInt(fmt.formatCellValue(currentRow.getCell(3)));
                list.add(new Guest(name, phone, mail, role,true));
            }
        } catch (FileNotFoundException var24) {
            var24.printStackTrace();
        } catch (IOException var25) {
            var25.printStackTrace();
        }
        Iterator var29 = list.iterator();

        while(var29.hasNext()) {
            Guest x = (Guest)var29.next();
            System.out.println(x.toString());
        }
        Integer success = guestService.insertListGuest(list);
        model.addAttribute("NOTIFI",success + " rows insert success");

        MyUploadForm myUploadForm2 = new MyUploadForm();
        model.addAttribute("file", myUploadForm2);
        return "importPage";
    }

}
