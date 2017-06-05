/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gateway;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.database.DB;
import org.model.User;

public class UsersGateway {
    
    public List<User> read() {
        List<User> users = new ArrayList<>();
        ResultSet res = DB.getDBInstance().executeQuery("select * from users;");
        try {
            while (res.next()) {
                User user = new User();
                user.setUsername(res.getString("username"));
                user.setPassword(res.getString("password"));
                user.setType(res.getString("type"));
                user.setID(res.getInt("ID"));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }
    
    public void create(User user) {
        StringBuilder query = new StringBuilder();
        query.append("insert into users (username,password,type) values ('");
        query.append(user.getUsername());
        query.append("','");
        query.append(user.getPassword());
        query.append("','");
        query.append(user.getType());
        query.append("');");
        DB.getDBInstance().executeUpdate(query.toString());
    }
    
    public void update(User oldUser, User newUser) {
        StringBuilder query = new StringBuilder();
        query.append("update users set username='");
        query.append(newUser.getUsername());
        query.append("',password='");
        query.append(newUser.getPassword());
        query.append("',type='");
        query.append(newUser.getType());
        query.append("' where username='");
        query.append(oldUser.getUsername());
        query.append("' and password='");
        query.append(oldUser.getPassword());
        query.append("' and type='");
        query.append(oldUser.getType());
        query.append("';");
        DB.getDBInstance().executeUpdate(query.toString());
    }
    
    public void delete(User user) {
        StringBuilder query = new StringBuilder();
        query.append("delete from users where username='");
        query.append(user.getUsername());
        query.append("';");
        DB.getDBInstance().executeUpdate(query.toString());
    }
    
}
