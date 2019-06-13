package com.example.demo.service;

import com.example.demo.Repositories.MeetRepository;
import com.example.demo.Repositories.ParticipantRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Transactional
@Service
public class MeetServiceImpl {
    @Autowired
    private MeetRepository meetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserServiceImpl userService;

    private FactoryConcreteParticipant factoryParticipant = new FactoryConcreteParticipant();

    static Logger log = Logger.getLogger(MeetServiceImpl.class.getName());

    public Meet createMeetWithFriend(Long idOwner, Long idUser) {
        Meet currentMeet = new Meet();
        Bet bet = new Bet();
        bet.setInversion(100);
        currentMeet.setBet(bet);
        currentMeet.setState(State.START);
        currentMeet.setName(Math.random() + "");

        User user = userRepository.findById(idUser);
        User owner = userRepository.findById(idOwner);

        log.info("SAVE ");
        Participant participant = factoryParticipant.createParticipant(bet, user, currentMeet, Rol.PARTICIPANT);
        participant=participantRepository.save(participant);
        log.info("SAVEWE "+participant);

        Participant participantOwner = factoryParticipant.createParticipant(bet, owner, currentMeet, Rol.OWNER);
        participantOwner=participantRepository.save(participantOwner);
        log.info("SAVESE "+participantOwner);
        return currentMeet;
    }

    public Meet createMeet(Long idUser, Meet meet) {
        Meet currentMeet = meetRepository.findByName(meet.getName());
        User user = userRepository.findById(idUser);
        Participant participant;

        if (currentMeet == null) {
            Meet meet1 = new Meet();
            meet1.setName(meet.getName());
            Bet betMeet = new Bet();
            betMeet.setInversion(meet.getBet().getInversion());
            meet1.setBet(betMeet);
            meet1.setState(meet.getState());

            participant = factoryParticipant.createParticipant(betMeet, user, meet1, Rol.OWNER);
            participantRepository.save(participant);
            return meet1;
        } else {
            currentMeet.setName(meet.getName());
            currentMeet.setState(meet.getState());
            currentMeet.setBet(meet.getBet());
            meetRepository.save(currentMeet);
            return currentMeet;
        }
    }

    public List<Participant> addUserToMeet(Long idUser, Long idMeet, Long idOwner) {

        Meet currentMeet = meetRepository.findById(idMeet);
        List<Participant> participants = participantRepository.findByMeet(currentMeet);

        boolean flag = false;
        for (Participant participant : participants) {
            if (participant.getUser().getId() == idOwner) {
                log.info("FINA " + participant.getUser().getId());
                flag = true;
            }
        }

        User user = userRepository.findById(idUser);
        if (user.getId() != idOwner && flag) {
            Bet bet = new Bet();
            bet.setInversion(0);
            Participant participant = factoryParticipant.createParticipant(bet, user, currentMeet, Rol.PARTICIPANT);

            participantRepository.save(participant);
        }
        return participants;
    }

    public List<Meet> getMeets(Long idUser) {
        log.info("CANAL000");
        List<Friendship> friends = userService.getFriends(idUser);
        log.info("FRIENDS"+ friends);
        List<Participant> owners = getOwners();
        List<Meet> meets=new ArrayList<>();
        for (Friendship friendship : friends) {
            log.info("VIISTO"+ " *** "+friendship.getOwner().getId());
            for (Participant owner : owners) {
                log.info("VEESTO"+ owner.getUser().getId() +" *** "+friendship.getOwner().getId());
                if (owner.getUser().getId() == friendship.getOwner().getId()) {
                    meets.add(owner.getMeet());
                }
            }
        }
        log.info("TRABAJOB"+ meets);
        return meets;
    }


    public List<Participant> getOwners() {
        List<Participant> participants = participantRepository.findAll();
        List<Participant> owners = new ArrayList<>();
        for (Participant participant : participants) {
            log.info("ERES " +participant.getUser());
            if (participant.getRol() == Rol.OWNER) {
                owners.add(participant);
            }
        }

        return owners;
    }


    public Participant addBetToUser(Long idParticipant, Long idMeet, Integer amount) {
        Meet meet = meetRepository.findById(idMeet);
        List<Participant> participants = participantRepository.findByMeet(meet);
        Participant participantToMeet = new Participant();
        for (Participant participant : participants) {
            if (participant.getId() == idParticipant) {
                participantToMeet = participant;
            }
        }

        log.info("EXAGERADO " + participantToMeet.getBet());
        Bet betMeet = meet.getBet();
        if (betMeet.getInversion() >= amount + participantToMeet.getBet().getInversion()) {
            Bet bet = new Bet();
            bet.setInversion(amount + participantToMeet.getBet().getInversion());
            participantToMeet.setBet(bet);
        }
        return participantRepository.save(participantToMeet);
    }
}
