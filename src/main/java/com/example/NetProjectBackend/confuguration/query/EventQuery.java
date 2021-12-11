package com.example.NetProjectBackend.confuguration.query;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:query.properties")

public class EventQuery {
    //event
    @Value("${event.insert}")
    private String eventCreate;

    @Value("${event.update}")
    private String eventUpdate;

    @Value("${event.decline}")
    private String eventDecline;

    @Value("${event.select_page_own/member}")
    private String eventSelectPageOwmMember;

    @Value("${event.search_page_order_by_title}")
    private String eventSearchOrderByTitle;

    @Value("${event.search_page_order_by_date}")
    private String eventSearchOrderByDate;

    @Value("${event.select_by_id}")
    private String eventSelectById;

    //event member
    @Value("${event_member.insert}")
    private String eventMemberCreate;

    @Value("${event_member.update}")
    private String eventMemberUpdate;

    @Value("${event_member.delete}")
    private String eventMemberDelete;

    @Value("${user.select_from_event}")
    private String membersOfEvent;

    //event dish
    @Value("${event_dish.insert}")
    private String eventDishCreate;

    @Value("${event_dish.delete}")
    private String eventDishDelete;

    @Value("${dish.select_page_by_event_search_by_title}")
    private String dishesOfEvent;

    //message
    @Value("${message.insert}")
    private String messageCreate;

    @Value("${message.update}")
    private String messageUpdate;

    @Value("${message.delete}")
    private String messageDelete;

    @Value("${message.select_page_by_event_search_by_text}")
    private String messagesOfEvent;

    //event ingredient
    @Value("${event_ingredient.insert}")
    private String eventIngredientCreate;

    @Value("${event_ingredient.select_page_by_user_and_event}")
    private String userIngredientsOfEvent;
}
