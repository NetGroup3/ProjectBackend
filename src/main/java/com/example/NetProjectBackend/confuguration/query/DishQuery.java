package com.example.NetProjectBackend.confuguration.query;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:query.properties")
public class DishQuery {
    @Value("${dish.dish.create}")
    private String create;

    @Value("${dish.dish.edit}")
    private String edit;

    @Value("${dish.dish.delete}")
    private String delete;

    @Value("${dish.dish.read.page.desc}")
    private String readPageDesc;

    @Value("${dish.dish.read.page.asc}")
    private String readPageAsc;

    @Value("${dish.dish.read.params.desc}")
    private String readParamsDesc;

    @Value("${dish.dish.read.params.asc}")
    private String readParamsAsc;

    @Value("${dish.dish.set.active}")
    private String setActive;

    @Value("${dish.dish.solo.read}")
    private String soloRead;

    @Value("${dish.dish.recommend}")
    private String recommend;

    @Value("${dish.dish.with.ingredients.left}")
    private String withIngredientsLeft;

    @Value("${dish.dish.with.ingredients.right}")
    private String withIngredientsRight;

    @Value("${dish.ingredient.check}")
    private String ingredientCheck;


    @Value("${dish.ingredient.update}")
    private String ingredientUpdate;

    @Value("${dish.ingredient.remove}")
    private String ingredientRemove;

    @Value("${dish.ingredient.create}")
    private String ingredientCreate;

    @Value("${dish.ingredient.read}")
    private String ingredientRead;

    @Value("${dish.kitchenware.check}")
    private String kitchenwareCheck;

    @Value("${dish.kitchenware.remove}")
    private String kitchenwareRemove;

    @Value("${dish.kitchenware.create}")
    private String kitchenwareCreate;

    @Value("${dish.kitchenware.read}")
    private String kitchenwareRead;

    @Value("${dish.comment.read}")
    private String commentRead;

    @Value("${dish.comment.create.check}")
    private String commentCreateCheck;

    @Value("${dish.comment.create}")
    private String commentCreate;

    @Value("${dish.comment.delete}")
    private String commentDelete;

    @Value("${dish.favourite.check}")
    private String favouriteCheck;

    @Value("${dish.favourite.add}")
    private String favouriteAdd;

    @Value("${dish.favourite.remove}")
    private String favouriteRemove;

    @Value("${dish.favourite.get}")
    private String favouriteGet;

    @Value("${dish.label.read.relation}")
    private String labelReadRelation;

    @Value("${dish.label.create}")
    private String labelCreate;

    @Value("${dish.label.get}")
    private String labelGet;

    @Value("${dish.label.edit}")
    private String labelEdit;

    @Value("${dish.label.delete}")
    private String labelDelete;

    @Value("${dish.label.add}")
    private String labelAdd;

    @Value("${dish.label.remove}")
    private String labelRemove;

    @Value("${dish.like.set.like}")
    private String likeSetLike;

    @Value("${dish.ingredient.push.list}")
    private String pushListDishIngredient;

    @Value("${dish.kitchenware.push.list}")
    private String pushListKitchenwareIngredient;

    @Value("${dish.label.push.list}")
    private String pushListLabelsIngredient;

    @Value("${dish.ingredient.delete.list}")
    private String deleteListIngredient;

    @Value("${dish.kitchenware.delete.list}")
    private String deleteListKitchenware;


    @Value("${dish.label.delete.list}")
    private String deleteListLabel;
}
