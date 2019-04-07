package com.tensquare.friend.controller;

import entity.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable("friendid") String friendid,@PathVariable("type") String type) {

    }
}
