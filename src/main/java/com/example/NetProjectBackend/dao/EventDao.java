package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.*;
import com.example.NetProjectBackend.models.dto.EventIngredientDto;
import com.example.NetProjectBackend.models.dto.UserEventDto;
import com.example.NetProjectBackend.models.entity.Dish;

import java.util.List;

public interface EventDao {
    List<Event> createEvent(Event event);
    void updateEvent(Event event);
    void declineEvent(int id, String status);
    List<Event> readEventSearch (int userId, boolean isOwner, String title, String sortedBy, int limit, int offset);
    Event readById (int id);

    void createEventMember (EventMember eventMember);
    void updateEventMember (String status, int userId, int eventId);
    void deleteEventMember (int userId, int eventId);
    List<UserEventDto> readUsersFromEvent (int eventId, String firstName, int limit, int offset);

    void createEventDish (EventDish eventDish);
    void deleteEventDish (int userId, int eventId, int dishId);
    List <Dish> readDishFromEvent (int event_id, String title, int limit, int offset);

    void createMessage (Message message);
    void updateMessage (Message message);
    void deleteMessage (Message message);
    List<Message> readMessageFromEvent (int eventId, String text, int limit, int offset);

    void createEventIngredient (EventIngredient eventIngredient);
    List<EventIngredientDto> readUserIngredientsFromEvent (int userId, int eventId, int limit, int offset);

}
