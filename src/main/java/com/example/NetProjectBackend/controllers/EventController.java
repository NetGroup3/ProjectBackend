package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.*;
import com.example.NetProjectBackend.models.dto.EventIngredientDto;
import com.example.NetProjectBackend.models.dto.UserEventDto;
import com.example.NetProjectBackend.models.entity.Dish;
import com.example.NetProjectBackend.service.EventService;
import com.example.NetProjectBackend.service.impl.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @PostMapping("/full")
    @Secured("USER")
    public ResponseEntity<?> createEventUsersDishes(@RequestBody Event event, EventMember eventMember, EventDish eventDish) {
        return ResponseEntity.ok(eventService.createEventUsersDishes(event, eventMember, eventDish));
    }

    @PostMapping()
    @Secured("USER")
    public void createEvent(@RequestBody Event event) {
        eventService.createEvent(event);
    }

    @PutMapping()
    @Secured("USER")
    public void updateEvent(@RequestBody Event event) {
        eventService.updateEvent(event);
    }

    @PutMapping("/decline")
    @Secured("USER")
    public void declineEvent(@RequestParam int id) {
        eventService.declineEvent(id);
    }

    @GetMapping()
    @Secured("USER")
    public ResponseEntity<?> readEvents(
            @RequestParam boolean is_owner,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String sortedBy,
            @RequestParam int limit,
            @RequestParam int page) {
        int user_id = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(eventService.readEventSearch(user_id, is_owner, title, sortedBy, limit, page));
    }

    @GetMapping("/id")
    @Secured("USER")
    public ResponseEntity<?> readEventById(int id) {
        return ResponseEntity.ok(eventService.eventReadById(id));
    }

    @PostMapping("/member")
    @Secured("USER")
    public void createEventMember(@RequestBody EventMember eventMember) {
        eventService.createEventMember(eventMember);
    }

    @PutMapping("/member")
    @Secured("USER")
    public void updateEventMemberStatus(@RequestBody EventMember eventMember) {
        eventService.updateEventMember(eventMember);
    }

    @DeleteMapping("/member")
    @Secured("USER")
    public void deleteEventMember(@RequestParam int user_id,
                                  @RequestParam int event_id) {
        eventService.deleteEventMember(user_id, event_id);
    }

    @GetMapping("/member")
    @Secured("USER")
    public List<UserEventDto> readUsersFromEvent(@RequestParam int event_id,
                                                 @RequestParam(required = false) String first_name,
                                                 @RequestParam int limit,
                                                 @RequestParam int page) {
        return eventService.readUsersFromEvent(event_id, first_name, limit, page);
    }

    @PostMapping("/dish")
    @Secured("USER")
    public void createEventDish(@RequestBody EventDish eventDish) {
        eventService.createEventDish(eventDish);
    }


    @DeleteMapping("/dish")
    @Secured("USER")
    public void deleteEventDish(@RequestParam int user_id,
                                @RequestParam int event_id,
                                @RequestParam int dish_id) {
        eventService.deleteEventDish(user_id, event_id, dish_id);
    }

    @GetMapping("/dish")
    @Secured("USER")
    public List<Dish> readDishesFromEvent(@RequestParam int event_id,
                                          @RequestParam(required = false) String title,
                                          @RequestParam int limit,
                                          @RequestParam int page) {
        return eventService.readDishFromEvent(event_id, title, limit, page);
    }

    @PostMapping("/message")
    @Secured("USER")
    public void createMessage(@RequestBody Message message) {
        eventService.createMessage(message);
    }

    @PutMapping("/message")
    @Secured("USER")
    public void updateMessage(@RequestBody Message message) {
        eventService.updateMessage(message);
    }

    @DeleteMapping("/message")
    @Secured("USER")
    public void deleteMessage(@RequestBody Message message) {
        eventService.deleteMessage(message);
    }

    @GetMapping("/message")
    @Secured("USER")
    public List<Message> readMessagesFromEvent(@RequestParam int event_id,
                                                 @RequestParam(required = false) String text,
                                                 @RequestParam int limit,
                                                 @RequestParam int page) {
        return eventService.readMessageFromEvent(event_id, text, limit, page);
    }


    @PostMapping("/ingredint")
    @Secured("USER")
    public void createEventIngredient(@RequestBody EventIngredient eventIngredient) {
        eventService.createEventIngredient(eventIngredient);
    }

    @GetMapping("/ingredient")
    @Secured("USER")
    public List<EventIngredientDto> readUserIngredientsFromEvent(@RequestParam int event_id,
                                                                 @RequestParam int user_id,
                                                                 @RequestParam int limit,
                                                                 @RequestParam int page) {
        return eventService.readUserIngredientsFromEvent(event_id, user_id, limit, page);
    }


}
