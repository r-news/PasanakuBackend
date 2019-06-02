package com.example.demo.service;

import com.example.demo.Repositories.FriendsRepository;
import com.example.demo.controller.UserController;
import com.example.demo.model.Friendship;
import com.example.demo.model.Life;
import com.example.demo.model.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;
    @Autowired
    public FriendsRepository friendsRepository;

    static Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    public User createUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) == null) {
            return userRepository.save(user);
        } else {
            log.info("HOLA    " + user);
            User owner = userRepository.findById(user.getId());
            owner.setName(user.getName());
            owner.setEmail(user.getEmail());
            owner.setLife(user.getLife());
            owner.setPassword(user.getPassword());
            return userRepository.save(owner);
        }
    }


    public List<Friendship> getFriends(Long idUser) {
        List<Friendship> res = new ArrayList<>();
        List<Friendship> friendshipsAll = friendsRepository.findAll();

        for (Friendship friendship : friendshipsAll) {
            if (friendship.getOwner().getId() == idUser) {
                res.add(friendship);
            }
        }
        return res;
    }


    public User deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        log.info(user + "   MASSSSSSS");
        if (user != null) {
            user.setLife(Life.delete);
            return userRepository.save(user);
        }
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Friendship createLinkWithFriends(Long ownerId, Long friendId) {
        User owner = userRepository.findById(ownerId);
        User friend = userRepository.findById(friendId);

        if (owner == null) throw new RuntimeException("Owner not found");
        if (friend == null) throw new RuntimeException("Friend not found");

        Friendship newFriendship = new Friendship();
        newFriendship.setOwner(owner);
        newFriendship.setFriend(friend);

        newFriendship = friendsRepository.save(newFriendship);
        return newFriendship;
    }


    public Set<User> getFriendsOf(Long ownerId) {
        Set<User> rtn = new HashSet<>();
        User user = userRepository.findById(ownerId);
        List<Friendship> friendship = friendsRepository.findAll();
        if (user != null) {
            for (Friendship fsh : friendship) {
                if (fsh.getOwner().getEmail() == user.getEmail()) {
                    rtn.add(fsh.getFriend());
                }
            }
            return rtn;
        }
        return null;
    }

    public User getUser(String username) {
        return userRepository.findByName(username);
    }
}