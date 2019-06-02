package com.example.demo.controller;

import com.example.demo.model.Meet;
import com.example.demo.model.Participant;
import com.example.demo.model.User;
import com.example.demo.service.MeetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:8100", maxAge = 3600)
@RestController
@RequestMapping({"/meet"})
public class MeetController {
    @Autowired
    private MeetServiceImpl meetService;
    static Logger log = Logger.getLogger(MeetController.class.getName());

    @PostMapping("/createMeet")
    public ResponseEntity<Meet> createMeet(@RequestParam("user") Long idUser, @RequestBody Meet meet) {
        return new ResponseEntity<Meet>(meetService.createMeet(idUser, meet), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/friend/{idUser}/owner/{idOwner}",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<Meet> friend(@PathVariable Long idUser, @PathVariable Long idOwner) {
        log.info("PUSEN " + idOwner + "   " + idUser);
        return new ResponseEntity<Meet>(meetService.createMeetWithFriend(idOwner, idUser), HttpStatus.OK);
    }

    @PostMapping("/addBetToUser")
    public ResponseEntity<Participant> addBetToUser(@RequestParam("idParticipant") Long idParticipant,
                                                    @RequestParam("idMeet") Long idMeet,
                                                    @RequestParam("amount") Integer amount) {
        return new ResponseEntity<Participant>(meetService.addBetToUser(idParticipant, idMeet, amount), HttpStatus.OK);
    }


    @PostMapping("/joinMeet")
    public ResponseEntity<List<Participant>> joinUserToMeet(@RequestParam("user") Long idUser,
                                                            @RequestParam("meet") Long idMeet,
                                                            @RequestParam("userOwner") Long idOwner) { //´primera forma de unirse al meet
        return new ResponseEntity<List<Participant>>(meetService.addUserToMeet(idUser, idMeet, idOwner), HttpStatus.OK);
    }

    @GetMapping("/getMeets")
    public ResponseEntity<List<Meet>> getMeets() {
        return new ResponseEntity<>(meetService.getMeets(), HttpStatus.OK);
    }


}
