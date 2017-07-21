/* 
 Description:
 ZK Essentials
 History:
 Created by dennis

 Copyright (C) 2012 Potix Corporation. All Rights Reserved.
 */
package servicos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import modelo.Users;

public class UserCredential implements Serializable {

    private static final long serialVersionUID = 1L;

    String account;
    String name;

    Set<String> roles = new HashSet<String>();
Users us=new Users();
    public UserCredential(String account, String name) {
        this.account = account;
        this.name = name;

    }

    public UserCredential() {
        this.account = "anonymous";
        this.name = "";
        roles.add("anonymous");
    }

    public boolean isAnonymous() {
        return hasRole("anonymous") || "anonymous".equals(account);
    }

//    public boolean isDocente() {
//        List<Users> users = new UsersJpaController(new JPA().getEmf()).findUsersEntities();
//        List<Docente> docentes = new DocenteJpaController(new JPA().getEmf()).findDocenteEntities();
//        for (Users user : users) {
//            for (Docente docente : docentes) {
//
//                if (isAnonymous() == false) {
//                    if ((this.account == null ? user.getUtilizador() == null : this.account.equals(user.getUtilizador())) && user.getIdFuncionario() == docente.) {
//                        return true;
//                    }
//
//                }
//            }
//        }
//        return false;
//
//    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
    

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    public void addRole(String role) {
        roles.add(role);
    }

}
