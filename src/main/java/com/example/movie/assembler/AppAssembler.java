package com.example.movie.assembler;

import com.example.movie.controllers.AdminController;
import com.example.movie.controllers.ApiController;
import com.example.movie.controllers.AuthenController;
import com.example.movie.controllers.UserController;
import com.example.movie.models.app.AppApi;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AppAssembler implements RepresentationModelAssembler<AppApi, EntityModel<AppApi>> {
    @Override
    public EntityModel<AppApi> toModel(AppApi entity) {
        try {
            return EntityModel.of(entity,
                    // Link API Public
                    linkTo(methodOn(ApiController.class).featuredMovies()).withRel("Featured Movies"),
                    linkTo(methodOn(UserController.class).moviesByCinemaRoomSeatstatus("","",""))
                            .withRel("Movies by cinema, room, status seat"),
                    linkTo(methodOn(ApiController.class).chooseMovie()).withRel("Choose Movie"),
                    linkTo(methodOn(ApiController.class).chooseCinema(0)).withRel("Choose Cinema"),
                    linkTo(methodOn(ApiController.class).chooseRoom(0, 0)).withRel("Choose Room"),
                    linkTo(methodOn(ApiController.class).chooseSchedule(0, 0)).withRel("Choose Schedule"),
                    linkTo(methodOn(ApiController.class).chooseFood( 0)).withRel("Choose Food"),
                    linkTo(methodOn(AuthenController.class).register(null)).withRel("Register"),
                    linkTo(methodOn(AuthenController.class).confirmEmail(null)).withRel("Confirm Email"),
                    linkTo(methodOn(AuthenController.class).login(null)).withRel("Login")
                    // Link API Private User
                    ,linkTo(methodOn(UserController.class).changePassword(null)).withRel("Change Password"),
                    linkTo(methodOn(UserController.class).confirmForgotPassword(null)).withRel("Confirm forgot password"),
                    linkTo(methodOn(UserController.class).forgotPassword(null)).withRel("Forgot password"),
                    linkTo(methodOn(UserController.class).createBill(null)).withRel("Create Bill"),
                    linkTo(methodOn(UserController.class).payBill(null)).withRel("Pay Bill")
                    // Link API Private Admin
                    ,linkTo(methodOn(AdminController.class).revenue(null)).withRel("Revenue")
                    ,linkTo(methodOn(AdminController.class).featuredFoodId7Day()).withRel("Featured Food in week")
                    ,linkTo(methodOn(AdminController.class).findUserAll()).withRel("User all")
                    ,linkTo(methodOn(AdminController.class).findUserById(0)).withRel("User by id")
                    ,linkTo(methodOn(AdminController.class).addUser(null)).withRel("Add user")
                    ,linkTo(methodOn(AdminController.class).updateUser(null)).withRel("Update user")
                    ,linkTo(methodOn(AdminController.class).deleteUser(0)).withRel("Delete user")
                    ,linkTo(methodOn(AdminController.class).changeRoleUser(0, null)).withRel("Change role user")
                    ,linkTo(methodOn(AdminController.class).changeActiveUser(0, true)).withRel("Change active user")
                    //
                    );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

