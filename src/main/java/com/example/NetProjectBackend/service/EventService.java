package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.*;
import com.example.NetProjectBackend.models.dto.EventIngredientDto;
import com.example.NetProjectBackend.models.dto.UserEventDto;
import com.example.NetProjectBackend.models.entity.Dish;

import java.util.List;

public interface EventService {
    void createEvent(Event event);
    void createEventUsersDishes(Event event, EventMember eventMember, EventDish eventDish);
    void updateEvent(Event event);
    void declineEvent(int id);
    List<Event> readEventSearch (int user_id, boolean is_owner, String title, String sortedBy, int limit, int page);
    Event eventReadById (int id);

    void createEventMember (EventMember eventMember);
    void updateEventMember (String status, int user_id, int event_id);
    void deleteEventMember (int user_id, int event_id);
    List<UserEventDto> readUsersFromEvent (int event_id, String first_name, int limit, int page);

    void createEventDish (EventDish eventDish);
    void deleteEventDish (int user_id, int event_id, int dish_id);
    List <Dish> readDishFromEvent (int event_id, String title, int limit, int page);

    void createMessage (Message message);
    void updateMessage (Message message);
    void deleteMessage (Message message);
    List<Message> readMessageFromEvent (int event_id, String text, int limit, int page);

    void createEventIngredient (EventIngredient eventIngredient);
    List<EventIngredientDto> readUserIngredientsFromEvent (int user_id, int event_id, int limit, int page);
}
