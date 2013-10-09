package com.tuongky.backend;

import org.mindrot.BCrypt;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.tuongky.model.datastore.User;

public class UserDao extends DAOBase {

  static {
    ObjectifyService.register(User.class);
  }

  public User save(String username, String password) {
    String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
    User user = new User(username, hashed);
    ObjectifyService.begin().put(user);
    return user;
  }

  public User getById(long id) {
    return getById(id, ObjectifyService.begin());
  }

  public User getById(long id, Objectify ofy) {
    return ofy.get(User.class, id);
  }

  public User getByUsername(String username) {
    return getByUsername(username, ObjectifyService.begin());
  }

  public User getByUsername(String username, Objectify ofy) {
    return ofy.query(User.class).
        filter("username", username).
        get();
  }
}
