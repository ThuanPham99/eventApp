package com.assignment.event;

import com.assignment.event.entity.*;
import com.assignment.event.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {
    @Autowired
    private GuestService guestService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private EventService eventService;
    @Autowired
    private GuestInEventService guestInEventService;
    @GetMapping("/getListEvents")
    public HashMap<String, List<Event>> getListEvents(){
        List<Event> list = eventService.getListEvent();
        HashMap<String, List<Event>> hashMap = new HashMap<>();
        hashMap.put("Data",list);
        return hashMap;
    }
    @GetMapping("/getListEvents/{guestId}")
    public HashMap<String, List<Event>> getListEventByID(@PathVariable(name = "guestId") Integer id){
        List<Event> list = guestInEventService.getListEventByGuestID(id);
        HashMap<String, List<Event>> hashMap = new HashMap<>();
        hashMap.put("Data",list);
        return hashMap;
    }
    @PutMapping("/checkGoing/{eventId}/{guestId}")
    public Integer checkGoing(@PathVariable("eventId")Integer eventId,@PathVariable("guestId")Integer guestId){
        GuestInEvent guestInEvent = guestInEventService.getElement(guestId,eventId);
        if(guestInEvent!=null){
            String status = guestInEvent.getStatus();
            if(status.equals("INVITED")){
                guestInEvent.setStatus("GOING");
                guestInEventService.add(guestInEvent);
                return 1;
            }
            return 2;
        }
        return 3;
    }
    @GetMapping("/getListGuestInEvent/{eventId}")
    public HashMap<String,List<Guest2>> getList11(@PathVariable("eventId") Integer eventId){
        List<Guest2> guests = new ArrayList<>();
        List<GuestInEvent> list = guestInEventService.getAllListGuestinEventbyEventID(eventId);
        Guest2 guest = null;
        for (int i = 0; i < list.size(); i++){

            guest =  new Guest2(guestService.findByID(list.get(i).getGuestID()));
            guest.setCheck(list.get(i).getStatus());
            guests.add(guest);
        }
        HashMap<String,List<Guest2>>hashMap = new HashMap<>();
        hashMap.put("Data",guests);
        return hashMap;
    }
    @GetMapping("/login/{id}/{pass}")
    public Integer checkLogin(@PathVariable("id")String id,@PathVariable("pass")String pass){
        Manager manager = managerService.checkLogin(id,pass);
        if(manager != null){
            if(manager.getRole() == 2){
                return 1;
            }
        }
        return 2;
    }
    @GetMapping("loginWithGuest/{id}")
    public Integer checkLoginG(@PathVariable("id")Integer id){
        Guest guest = guestService.checkLogin(id);
        if(guest!=null){
            return 1;
        }
        return 2;
    }

    @PostMapping("/addQuestion")
    public Integer addQuestion(@RequestBody Question question){
        question.setID(null);
        question.setStatus("unActive");
        boolean check = questionService.add(question);
        if(check){
            return 1;
        }
        return 2;
    }
    @GetMapping("/getGuestsWithEvent/{eventId}")
    public HashMap<String, List<Guest>> getListGuest(@PathVariable(name = "eventId") Integer id){
        List<Guest> list = guestInEventService.getListGuestByEventID(id);
        HashMap<String, List<Guest>> hashMap = new HashMap<>();
        hashMap.put("Data",list);
        return hashMap;
    }
    @GetMapping("/getQuestionsWithEvenID/{evenID}")
    public HashMap<String, List<Question>> getQuestion1(@PathVariable(name = "evenID")Integer id){
        List<Question> list = questionService.getQuestions(id);
        HashMap<String, List<Question>> hashMap = new HashMap<>();
        hashMap.put("Data",list);
        return hashMap;
    }
    @GetMapping("/getQuestionsWithGuestID/{guestID}")
    public HashMap<String, List<Question>> getQuestion2(@PathVariable("guestID")Integer guestID){
        List<Question> list = questionService.getQuestionsWithGuestID(guestID);
        HashMap<String, List<Question>> hashMap = new HashMap<>();
        hashMap.put("Data",list);
        return hashMap;
    }
    @GetMapping("/getGuest/{guestId}")
    public HashMap<String,List<Guest>> getGuestByID(@PathVariable("guestId") Integer id){
        List<Guest> guests = new ArrayList<>();
        guests.add(guestService.findByID(id));
        HashMap<String,List<Guest>> hashMap = new HashMap<>();
        hashMap.put("Data",  guests);
        return hashMap;
    }
    @GetMapping("/getEvent/{eventId}")
    public HashMap<String,List<Event>> getEvent(@PathVariable("eventId")Integer id){
        Event event = eventService.getEvent(id);
        List<Event> list = new ArrayList<>();
        list.add(event);
        HashMap<String,List<Event>> hashMap = new HashMap<>();
        hashMap.put("Data",list);
        return hashMap;
    }
    @GetMapping("/getListQuestions")
    public HashMap<String, List<Question>> getQuestions(){
        List<Question> list = questionService.getAll();
        HashMap<String, List<Question>> hashMap = new HashMap<>();
        hashMap.put("Data",list);
        return hashMap;
    }
    @GetMapping("/getQuestionByID/{id}")
    public HashMap<String,List<Question>> getQuestion(@PathVariable("id") Integer id){
        Question question = questionService.getQuestionByID(id);
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        HashMap<String,List<Question>> hashMap = new HashMap<>();
        hashMap.put("Data",questions);
        return hashMap;
    }
    @GetMapping("/getManager/{ManagerId}")
    public HashMap<String,List<Manager>> getManagerByID(@PathVariable("ManagerId") String id){
        List<Manager> managers = new ArrayList<>();
        managers.add(managerService.getManager(id));
        HashMap<String, List<Manager>> hashMap = new HashMap<>();
        hashMap.put("Data",  managers);
        return hashMap;
    }
}
