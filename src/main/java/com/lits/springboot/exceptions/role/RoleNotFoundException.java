package com.lits.springboot.exceptions.role;

public class RoleNotFoundException extends RuntimeException{

        public RoleNotFoundException(String msg) {
            super(msg);
        }
}
